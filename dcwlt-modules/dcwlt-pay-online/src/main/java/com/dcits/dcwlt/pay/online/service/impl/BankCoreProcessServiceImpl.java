package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.redis.service.RedisService;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.baffle.core.impl.BankCoreImplDubboServiceImpl;
import com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service("bankCoreProcessService")
public class BankCoreProcessServiceImpl implements ICoreProcessService {

    private static final Logger logger = LoggerFactory.getLogger(BankCoreProcessServiceImpl.class);

    private static final String CHECK_NAME_FLG = "Y";
    private static final String TRANS_TYPE_CREDIT = "04";

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;


    @Autowired
    private BankCoreImplDubboServiceImpl bankCoreImplDubboServiceImpl;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private CoreEventServiceImpl coreEventServiceImpl;

    @Autowired
    private RedisService redisService;
    /**
     * ???????????????????????????
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public BankCore351100InnerReq initBankCore351100InnerReq(PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("initBankCore351100InnerReq: ?????????????????????????????????,???????????????{}??????????????????{}",  payTransDtlInfoDO.getPaySerno(),payTransDtlInfoDO.getPayDate());
        BankCore351100InnerReq reqCore351100 = new BankCore351100InnerReq();
        reqCore351100.setPaySerno(payTransDtlInfoDO.getPaySerno());
        reqCore351100.setPayDate(payTransDtlInfoDO.getPayDate());

        reqCore351100.setPayPath(Constant.ECNY_SYS_ID);
        reqCore351100.setAcctMeth("DJ050001");
        reqCore351100.setBookType(Constant.BANKCORE_CREDIT);
        reqCore351100.setBrno(Constant.MASTERBANK);
        reqCore351100.setCoreSysId(Constant.DEFAULT_BANKSYSID);
        reqCore351100.setRevTranFlag(Constant.REVTRANFLAG_POSITIVE);
        reqCore351100.setServerId(Constant.P_BANKCORE_CREDIT);
        reqCore351100.setAmount(payTransDtlInfoDO.getAmount());
        reqCore351100.setCurrency(Constant.CURRENCY_CODE_156);

        //???????????????
        reqCore351100.setPayerAcct(payTransDtlInfoDO.getPayerWalletId());
        reqCore351100.setPayerName(payTransDtlInfoDO.getPayerWalletName());
        reqCore351100.setPayerBank(payTransDtlInfoDO.getPayerPtyId());
        reqCore351100.setPayerBankName("");

        //???????????????
        reqCore351100.setPayeeAcct(payTransDtlInfoDO.getPayeeAcct());
        reqCore351100.setRealPayeeAcct(payTransDtlInfoDO.getPayeeAcct());
        reqCore351100.setPayeeName(payTransDtlInfoDO.getPayeeName());
        reqCore351100.setRealPayeeName(payTransDtlInfoDO.getPayeeName());
        reqCore351100.setAcctBrno(Constant.MASTERBANK);

        //???????????????
        reqCore351100.setPayeeBank(payTransDtlInfoDO.getInstdPty());
        reqCore351100.setPayeeBankName("");
        reqCore351100.setChkNameFlg2(CHECK_NAME_FLG);
        reqCore351100.setSummary(payTransDtlInfoDO.getSummary());
        reqCore351100.setTransType(TRANS_TYPE_CREDIT);// ?????? 04-?????? ????????????
        reqCore351100.setSuspSerno("");
        reqCore351100.setPayerMediaType(Constant.PAYER_MEDIA_TYPE_CARD);// 0-??? 1-??? 2-????????? ????????????
        reqCore351100.setCoreTrxCode("");
        reqCore351100.setCoreTrxType(Constant.BANKTRXTYPE_CREDIT);

        logger.info("initBankCore351100InnerReq: ?????????????????????????????????,???????????????{}??????????????????{}",  payTransDtlInfoDO.getPaySerno(),payTransDtlInfoDO.getPayDate());
        return reqCore351100;
    }

    /**
     * ?????????????????????
     * @param payTransDtlInfoDO
     * @param bankCore996666Rsp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void qryCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp, StateMachine stateMachine) {

        String coreReqDate = payTransDtlInfoDO.getCoreReqDate();
        String coreReqSerno = payTransDtlInfoDO.getCoreReqSerno();


        //??????????????????
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        String coreProcStatus = bankCoreAccTxnServiceImpl.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setCoreRetCode(bankCore996666Rsp.getCoreRetCode());
        updateDO.setCoreRetMsg( bankCore996666Rsp.getCoreRetMsg());
        updateDO.setCoreAcctDate(bankCore996666Rsp.getHostAcdate());
        updateDO.setCoreSerno( bankCore996666Rsp.getHostJrnno());
        updateDO.setCoreProcStatus(coreProcStatus);
        updateDO.setLastUpDate(DateUtil.getDefaultDate());
        updateDO.setLastUpTime(DateUtil.getDefaultTime());

        try {
            //?????????????????????
            int updateNum = bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
            if(updateNum != 1){
                logger.error("??????????????????????????????????????????,???????????????{}??????????????????{}",
                        updateDO.getPayDate(),updateDO.getPaySerno() );
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }

            updateNum = payTransDtlInfoService.update(updateDO, stateMachine);
            if(updateNum != 1){
                logger.error("????????????????????????????????????,???????????????{}??????????????????{}",
                        updateDO.getPayDate(),updateDO.getPaySerno() );
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        }catch (Exception e){
            logger.error("?????????????????????????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    @Override
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq) {

        logger.info("???????????????????????????????????????{}????????????????????????{}",
                bankCore351100InnerReq.getCoreReqDate(),bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp;

        try {
            bankCore351100InnerRsp = bankCoreImplDubboServiceImpl.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {

            //2021-04-14 ????????????????????????????????????MQ
            PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(bankCore351100InnerReq.getPayDate(),bankCore351100InnerReq.getPaySerno());
            coreEventServiceImpl.registerCoreQry(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ReCreditCoreQryCallBack.class);

            logger.error("?????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.GATEWAY_REQUEST_ERROR);
        }

        return bankCore351100InnerRsp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void qryBankCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp, StateMachine stateMachine) {
        String coreReqDate = payTransDtlInfoDO.getCoreReqDate();
        String coreReqSerno = payTransDtlInfoDO.getCoreReqSerno();

        //?????????????????????
        bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);

        //??????????????????
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setCoreRetCode(bankCore996666Rsp.getCoreRetCode());
        updateDO.setCoreRetMsg( bankCore996666Rsp.getCoreRetMsg());
        updateDO.setCoreAcctDate(bankCore996666Rsp.getHostAcdate());
        updateDO.setCoreSerno( bankCore996666Rsp.getHostJrnno());
        updateDO.setLastUpDate(DateUtil.getDefaultDate());
        updateDO.setLastUpTime(DateUtil.getDefaultTime());
        //?????????????????????
        updateDO.setTrxStatus(payTransDtlInfoDO.getTrxStatus());
        updateDO.setCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        updateDO.setPathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        try {
            int updateNum = payTransDtlInfoService.update(updateDO, stateMachine);
            if(updateNum != 1){
                logger.error("??????????????????????????????????????????????????????????????????,???????????????{}??????????????????{}",
                        updateDO.getPayDate(),updateDO.getPaySerno() );
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        }catch (Exception e){
            logger.error("?????????????????????????????????????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }

    }

    /**
     * ???????????????????????????????????????
     * @param payTransDtlInfoDO
     * @param bankCore996666Rsp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dsptCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp,StateMachine stateMachine) {

        String coreReqDate = payTransDtlInfoDO.getPayDate();
        String coreReqSerno = payTransDtlInfoDO.getCoreReqSerno();

        //?????????????????????
        bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);

        //??????????????????
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        String coreProcStatus = bankCoreAccTxnServiceImpl.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setCoreRetCode(bankCore996666Rsp.getCoreRetCode());
        updateDO.setCoreRetMsg( bankCore996666Rsp.getCoreRetMsg());
        updateDO.setCoreAcctDate(bankCore996666Rsp.getHostAcdate());
        updateDO.setCoreSerno( bankCore996666Rsp.getHostJrnno());
        updateDO.setCoreProcStatus(coreProcStatus);
        if (AppConstant.TRXSTATUS_FAILED.equals(coreProcStatus) || AppConstant.TRXSTATUS_SUCCESS.equals(coreProcStatus)) {
            updateDO.setTrxStatus(coreProcStatus);
            updateDO.setPathProcStatus(coreProcStatus);
        }
        updateDO.setLastUpDate(DateUtil.getDefaultDate());
        updateDO.setLastUpTime(DateUtil.getDefaultTime());

        try {
            int updateNum = payTransDtlInfoService.update(updateDO, stateMachine);
            if(updateNum != 1){
                logger.error("????????????????????????????????????????????????,???????????????{}??????????????????{}",
                        updateDO.getPayDate(),updateDO.getPaySerno() );
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        }catch (Exception e){
            logger.error("???????????????????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }


    /**
     * ???????????????????????????
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public BankCore351100InnerReq sendCoreDebitInit(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("?????????????????????????????????????????????{}??????????????????{}",payTransDtlInfoDO.getPayDate(),payTransDtlInfoDO.getPaySerno());
        BankCore351100InnerReq bankCore351100InnerReq = new BankCore351100InnerReq();
        bankCore351100InnerReq.setPaySerno(payTransDtlInfoDO.getPaySerno());
        bankCore351100InnerReq.setPayDate(payTransDtlInfoDO.getPayDate());
        bankCore351100InnerReq.setPayerAcct(payTransDtlInfoDO.getPayerAcct());
        bankCore351100InnerReq.setPayerName(payTransDtlInfoDO.getPayerName());
        bankCore351100InnerReq.setPayeeAcct(payTransDtlInfoDO.getPayeeWalletId());
        bankCore351100InnerReq.setPayeeName(payTransDtlInfoDO.getPayeeWalletName());
        bankCore351100InnerReq.setPayeeBank(payTransDtlInfoDO.getPayeePtyId());
        bankCore351100InnerReq.setPayPath(Constant.ECNY_SYS_ID);
        bankCore351100InnerReq.setAcctMeth("DJ010011");
        bankCore351100InnerReq.setServerId(Constant.P_BANKCORE_DEBIT);
        bankCore351100InnerReq.setAmount(payTransDtlInfoDO.getAmount());
        bankCore351100InnerReq.setCurrency(Constant.CURRENCY_CODE_156);
        bankCore351100InnerReq.setBookType(Constant.BANKCORE_DEBIT);
        bankCore351100InnerReq.setCoreSysId(Constant.DEFAULT_BANKSYSID);
        bankCore351100InnerReq.setRevTranFlag(Constant.REVTRANFLAG_POSITIVE);
        bankCore351100InnerReq.setCoreTrxType(Constant.BANKTRXTYPE_DEBIT);
        bankCore351100InnerReq.setAcctBrno(payTransDtlInfoDO.getAcctBrno());
        bankCore351100InnerReq.setBrno(payTransDtlInfoDO.getBrno());
        bankCore351100InnerReq.setClearDate(payTransDtlInfoDO.getPayDate());
        bankCore351100InnerReq.setSummary(payTransDtlInfoDO.getSummary());
        bankCore351100InnerReq.setChkNameFlg1("Y");
        return bankCore351100InnerReq;
    }


    /**
     * ?????????-???????????????
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCoreDebitPre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq,StateMachine stateMachine) {

        logger.info("?????????-???????????????");
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        // ??????????????????,??????221
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);

        // ????????????????????????
        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        try {
            // ?????????????????????
            bankCoreAccTxnServiceImpl.insertCoreFlow(bankCore351100InnerReq, coreReqSerno, coreReqDate);
            // ?????????????????????
            int retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ???????????????????????????
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCoreDebitDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {

        logger.info("???????????????????????????");

        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        // ??????????????????
        setTradeResultDebit(payTransDtlInfoDO, bankCore351100InnerRsp);
        try {
            // ?????????????????????
            int retCount = bankCoreAccTxnServiceImpl.updateCoreAccFlow(bankCore351100InnerRsp);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
            // ?????????????????????
            retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void setTradeResultDebit(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {

        logger.info("??????????????????????????????:{}, ????????????:{}", bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        // ????????????????????????
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
        payTransDtlInfoDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());

        // ????????????
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
        } else {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
        }
    }


    /**
     * ???????????????????????????????????????????????????????????????????????????,????????????????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq, StateMachine stateMachine) {
        logger.info("sendCorePre: ???????????????????????????????????????????????????????????????????????????{}??????????????????{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        try {
            bankCoreAccTxnServiceImpl.insertCoreFlow(bankCore351100InnerReq, bankCore351100InnerReq.getCoreReqSerno(), bankCore351100InnerReq.getCoreReqDate());
            int updateNum = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("???????????????????????????");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }

            logger.info("sendCorePre: ???????????????????????????????????????????????????????????????,???????????????{}??????????????????{} ",
                    payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        } catch (Exception e) {
            logger.error("sendCorePre: ????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp, StateMachine stateMachine) {
        try {

            int updateNum = bankCoreAccTxnServiceImpl.updateCoreAccFlow(bankCore351100InnerRsp);
            if (updateNum != 1) {
                logger.error("???????????????????????????");
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

            updateNum = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("???????????????????????????");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        } catch (Exception e) {
            logger.error("sendCoreDone???????????????????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    @Override
    public void cacheLimitAmount(String key, String amountVal,boolean isAdd) {
        BigDecimal payerAmount = redisService.getCacheObject(key);
        long timeout = DateUtil.getRemainSecondsOneDay(new Date());
        if(null == payerAmount){
            payerAmount = new BigDecimal(AmountUtil.fenToYuan(amountVal));
            redisService.setCacheObject(key,payerAmount,timeout, TimeUnit.SECONDS);
        }else{
            BigDecimal amount = new BigDecimal(AmountUtil.fenToYuan(amountVal));
            BigDecimal resultAmount = null;
            if(isAdd){
                resultAmount = payerAmount.add(amount);
            }else{
                resultAmount = payerAmount.subtract(amount);
            }
            redisService.setCacheObject(key,resultAmount,timeout, TimeUnit.SECONDS);
            //       redisService.expire(key,10);
            logger.info("?????????????????????{},????????????{}",resultAmount.toString(),redisService.getTimeoutVal(key));
        }
    }

}
