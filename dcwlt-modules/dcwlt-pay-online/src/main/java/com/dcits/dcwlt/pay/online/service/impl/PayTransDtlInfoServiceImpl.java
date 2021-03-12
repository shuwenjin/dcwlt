package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.RejectCdEnum;
import com.dcits.dcwlt.common.pay.enums.SummaryCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertCdtrInf;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertDbtrInf;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReq;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.TrxInfReconvert;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfo1Service;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("payTransDtlInfoServiceImpl")
public class PayTransDtlInfoServiceImpl implements IPayTransDtlInfoService {

    private static final Logger logger = LoggerFactory.getLogger(PayTransDtlInfoServiceImpl.class);


    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IPayTransDtlInfo1Service payTransDtlInfoRepository;

    @Override
    public PayTransDtlInfoDO init(ReconvertReq reconvertReqDTO) {

        logger.info("兑回-初始化交易登记簿，报文标识号{}", reconvertReqDTO.getGrpHdr().getMsgId());

        GrpHdr grpHdr = reconvertReqDTO.getGrpHdr();
        TrxInfReconvert trxInf = reconvertReqDTO.getTrxInf();
        ReconvertDbtrInf dbtrInf = reconvertReqDTO.getDbtrInf();
        ReconvertCdtrInf cdtrInf = reconvertReqDTO.getCdtrInf();

        //初始化流水信息
        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
        payTransDtlInfoDO.setPayDate(DateUtil.getSysDate());
        payTransDtlInfoDO.setPaySerno(generateCodeService.generatePlatformFlowNo());
        payTransDtlInfoDO.setLastUpJrnno(payTransDtlInfoDO.getPaySerno());

        payTransDtlInfoDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setDirect(AppConstant.DIRECT_RECV);
        payTransDtlInfoDO.setPayFlag(AppConstant.IDENTIFICATION_PAYEE);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_INIT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);

        //通道信息
        payTransDtlInfoDO.setPayPathDateTime(grpHdr.getCreDtTm());
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        payTransDtlInfoDO.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setRemark(grpHdr.getRmk());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP221.getCode());

        //交易信息
        payTransDtlInfoDO.setBatchId(trxInf.getBatchId());
        payTransDtlInfoDO.setBusiType(trxInf.getTrxBizTp());
        payTransDtlInfoDO.setBusiKind(trxInf.getTrxCtgyPurpCd());
        payTransDtlInfoDO.setCcy(trxInf.getTrxAmt().getCcy());
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(trxInf.getTrxAmt().getValue()));
        payTransDtlInfoDO.setTradeFundSource(trxInf.getTrxFndSrc());
        payTransDtlInfoDO.setBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setAcctBrno(Constant.MASTERBANK);

        //付款人信息
        payTransDtlInfoDO.setPayerPtyId(dbtrInf.getDbtrPtyId());
        payTransDtlInfoDO.setPayerName(dbtrInf.getDbtrNm());
        payTransDtlInfoDO.setPayerWalletId(dbtrInf.getDbtrWltId());
        payTransDtlInfoDO.setPayerWalletName(dbtrInf.getDbtrWltNm());
        payTransDtlInfoDO.setPayerWalletLv(dbtrInf.getDbtrWltLvl());
        payTransDtlInfoDO.setPayerWalletType(dbtrInf.getDbtrWltTp());

        //收款人信息
        payTransDtlInfoDO.setPayeePtyId(cdtrInf.getCdtrPtyId());
        payTransDtlInfoDO.setPayeeName(cdtrInf.getCdtrNm());
        payTransDtlInfoDO.setPayeeAcctType(cdtrInf.getCdtrAcctTp());
        payTransDtlInfoDO.setPayeeAcct(cdtrInf.getCdtrAcct());
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSH.getCode());
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        return payTransDtlInfoDO;
    }


    /**
     * 更新200为终态000
     *
     * @param payTransDtlInfoDO
     */
    @Override
    public int updateFinalStatusFail(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("更新金融交易簿通道状态从200为终态000，平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        payTransDtlInfoDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
        payTransDtlInfoDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
        try {
            return payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("更新金融交易簿状态200 --》 000 异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新211为终态111
     *
     * @param payTransDtlInfoDO
     */
    @Override
    public int updateFinalStatusSucc(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("更新金融交易簿通道状态从211为终态111，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
        payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
        try {
            return payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("更新金融交易簿通道状态211 --》111 异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新110为210
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus110To210(PayTransDtlInfoDO payTransDtlInfoDO) {
        //业务状态失败存在记录110，更新为210
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        // 原状态机（110）
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        try {
            return payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("终态通知-更新金融交易簿通道状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新290为000
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus290To000(PayTransDtlInfoDO payTransDtlInfoDO) {
        //290更新为000
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        // 原状态机（227）  
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        try {
            return payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("终态通知-更新金融交易簿通道状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新001为221
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus001To221(PayTransDtlInfoDO payTransDtlInfoDO) {
        //有记录（001）更新金融登记簿状态为221
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        // 原状态机
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_FAILED);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        try {
            return payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("终态通知-更新金融交易簿通道状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新290为终态090
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatusX90To090(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) {

        logger.info("更新金融交易簿通道状态从290为终态090，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        try {
            int updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("更新金融交易簿通道状态从290为终态090失败,平台日期：{}，平台流水：{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("终态通知-更新金融交易簿状态290--》090 异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新207为终态000
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus207To000(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("更新金融交易簿通道状态从207为终态000，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //状态机赋值
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        //更新登记簿信息
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        updateDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
        updateDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
        updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        updateDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());

        //更新通知应答信息
        if (StringUtils.isNotBlank(payTransDtlInfoDO.getCoreRetCode())) {
//            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, AppConstant.DCEP_SYS_ID, payTransDtlInfoDO.getCoreRetCode(), payTransDtlInfoDO.getCoreRetMsg());
//            updateDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
//            updateDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }

        try {
            return payTransDtlInfoRepository.update(updateDO, stateMachine);
        } catch (Exception e) {
            logger.error("更新金融交易簿状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 更新217为终态111
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus217To111(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("更新金融交易簿通道状态从217为终态111，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //状态机赋值
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        //更新登记簿信息
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        updateDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
        updateDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
        updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        updateDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
        updateDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
        updateDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());

        try {
            return payTransDtlInfoRepository.update(updateDO, stateMachine);
        } catch (Exception e) {
            logger.error("更新金融交易簿状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    @Override
    public int updatePathStatus(PayTransDtlInfoDO payTransDtlInfoDO, String payPathStatus) {

        logger.info("更新通道状态，平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //状态机赋值
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        stateMachine.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        stateMachine.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());

        payTransDtlInfoDO.setPathProcStatus(payPathStatus);

        try {
            int updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("更新核心流水表失败,平台日期：{}，平台流水：{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("更新金融交易簿通道状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    @Override
    public int updatePayTransDtlFinal(PayTransDtlInfoDO payTransDtlInfoDO) {

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate, paySerno);

        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        logger.info("判断原交易是否可更新为终态，平台日期：{}，平台流水：{}，交易类型：{}，业务状态：{}，核心状态：{}，通道状态：{}",
                payDate, paySerno, payTransDtlInfoDO.getMsgType(), trxStatus, coreProcStatus, pathProcStatus);

        //交易状态X11，排除111
        boolean statusX11 = (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_SUCCESS.equals(trxStatus)));

        //交易状态X00，排除000
        boolean statusX00 = (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_FAILED.equals(trxStatus)));

        //交易状态X30，排除330
        boolean statusX30 = (AppConstant.CORESTATUS_REVERSED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_REVERSED.equals(trxStatus)));

        //X90（兑回、兑出、汇款兑出、差错往账），排除090
        boolean endStatusX90 = (StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_INIT)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_FAILED)
                && !(AppConstant.TRXSTATUS_FAILED.equals(trxStatus))
                && (checkMsgType(payTransDtlInfoDO)));

        //交易状态X11、X00、X30可更新为终态
        if (statusX11 || statusX00 || statusX30 || endStatusX90) {

            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(trxStatus);
            stateMachine.setPreCoreProcStatus(coreProcStatus);
            stateMachine.setPrePathProcStatus(pathProcStatus);

            //更新X11为终态111
            if (statusX11) {
                logger.info("更新登记簿状态X11为终态111,平台日期：{}，平台流水：{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            }

            //更新X00为终态000
            if (statusX00) {
                logger.info("更新登记簿状态X00为终态000,平台日期：{}，平台流水：{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
                payTransDtlInfoDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
            }

            //更新X30为终态330
            if (statusX30) {
                logger.info("更新登记簿状态X30为终态330,平台日期：{}，平台流水：{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_REVERSED);
            }

            //更新X90（兑回、兑出、汇款兑出、差错往账）为090
            if (endStatusX90) {
                logger.info("更新登记簿状态X90为终态090,平台日期：{}，平台流水：{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            }

            try {
                int updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
                if (updateNum != 1) {
                    logger.error("更新登记簿状态失败,平台日期：{}，平台流水：{}",
                            payDate, paySerno);
                    throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
                }
                return updateNum;
            } catch (Exception e) {
                logger.error("更新金融交易簿通道状态 异常： {}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

        }

        return 0;
    }

    /**
     * 更新X91为终态191
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatusX91To191(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) {

        logger.info("更新金融交易簿通道状态从X91为终态191，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        try {
            int updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("更新金融交易簿通道状态从X91为终态191失败,平台日期：{}，平台流水：{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("更新金融交易簿状态291--》191 异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

    }


    /**
     * 交易类型判断：兑回、兑出、汇款兑出、差错往账
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public boolean checkMsgType(PayTransDtlInfoDO payTransDtlInfoDO) {

        String msgType = payTransDtlInfoDO.getMsgType();

        //交易类型兑回、兑出、汇款兑出
        boolean msgTypeFlag = StringUtils.equalsAny(msgType, MsgTpEnum.DCEP221.getCode(),
                MsgTpEnum.DCEP225.getCode(), MsgTpEnum.DCEP227.getCode());

        //差错贷记调账往账
        boolean dcepSent801 = (StringUtils.equals(MsgTpEnum.DCEP801.getCode(), msgType)
                && StringUtils.equals(AppConstant.CGB_FINANCIAL_INSTITUTION_CD, payTransDtlInfoDO.getInstgPty()));

        boolean flag = msgTypeFlag || dcepSent801;

        logger.info("平台日期：{}，平台流水：{}，交易类型判断：兑回、兑出、汇款兑出、差错往账,结果：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), flag);

        return flag;
    }
}
