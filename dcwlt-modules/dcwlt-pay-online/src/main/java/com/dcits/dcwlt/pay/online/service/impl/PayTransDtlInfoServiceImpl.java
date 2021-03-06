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
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoMapper;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Service("payTransDtlInfoServiceImpl")
public class PayTransDtlInfoServiceImpl implements IPayTransDtlInfoService {

    private static final Logger logger = LoggerFactory.getLogger(PayTransDtlInfoServiceImpl.class);


    @Autowired
    private IGenerateCodeService generateCodeService;


    @Override
    public PayTransDtlInfoDO init(ReconvertReq reconvertReqDTO) {

        logger.info("??????-??????????????????????????????????????????{}", reconvertReqDTO.getGrpHdr().getMsgId());

        GrpHdr grpHdr = reconvertReqDTO.getGrpHdr();
        TrxInfReconvert trxInf = reconvertReqDTO.getTrxInf();
        ReconvertDbtrInf dbtrInf = reconvertReqDTO.getDbtrInf();
        ReconvertCdtrInf cdtrInf = reconvertReqDTO.getCdtrInf();

        //?????????????????????
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

        //????????????
        payTransDtlInfoDO.setPayPathDateTime(grpHdr.getCreDtTm());
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        payTransDtlInfoDO.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setRemark(grpHdr.getRmk());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP221.getCode());

        //????????????
        payTransDtlInfoDO.setBatchId(trxInf.getBatchId());
        payTransDtlInfoDO.setBusiType(trxInf.getTrxBizTp());
        payTransDtlInfoDO.setBusiKind(trxInf.getTrxCtgyPurpCd());
        payTransDtlInfoDO.setCcy(trxInf.getTrxAmt().getCcy());
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(trxInf.getTrxAmt().getValue()));
        payTransDtlInfoDO.setTradeFundSource(trxInf.getTrxFndSrc());
        payTransDtlInfoDO.setBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setAcctBrno(Constant.MASTERBANK);

        //???????????????
        payTransDtlInfoDO.setPayerPtyId(dbtrInf.getDbtrPtyId());
        payTransDtlInfoDO.setPayerName(dbtrInf.getDbtrNm());
        payTransDtlInfoDO.setPayerWalletId(dbtrInf.getDbtrWltId());
        payTransDtlInfoDO.setPayerWalletName(dbtrInf.getDbtrWltNm());
        payTransDtlInfoDO.setPayerWalletLv(dbtrInf.getDbtrWltLvl());
        payTransDtlInfoDO.setPayerWalletType(dbtrInf.getDbtrWltTp());

        //???????????????
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
     * ??????200?????????000
     *
     * @param payTransDtlInfoDO
     */
    @Override
    public int updateFinalStatusFail(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("????????????????????????????????????200?????????000??????????????????{}??????????????????{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        payTransDtlInfoDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
        payTransDtlInfoDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
        try {
            return this.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("???????????????????????????200 --??? 000 ????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????211?????????111
     *
     * @param payTransDtlInfoDO
     */
    @Override
    public int updateFinalStatusSucc(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("????????????????????????????????????211?????????111??????????????????{}??????????????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
        payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
        try {
            return this.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("?????????????????????????????????211 --???111 ????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????110???210
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus110To210(PayTransDtlInfoDO payTransDtlInfoDO) {
        //??????????????????????????????110????????????210
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        // ???????????????110???
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        try {
            return this.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("????????????-?????????????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????290???000
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus290To000(PayTransDtlInfoDO payTransDtlInfoDO) {
        //290?????????000
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        // ???????????????227???  
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        try {
            return this.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("????????????-?????????????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????001???221
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus001To221(PayTransDtlInfoDO payTransDtlInfoDO) {
        //????????????001?????????????????????????????????221
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        // ????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_FAILED);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        try {
            return this.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("????????????-?????????????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????290?????????090
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatusX90To090(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) {

        logger.info("????????????????????????????????????290?????????090??????????????????{}??????????????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        try {
            int updateNum = this.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("????????????????????????????????????290?????????090??????,???????????????{}??????????????????{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("????????????-???????????????????????????290--???090 ????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????207?????????000
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus207To000(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("????????????????????????????????????207?????????000??????????????????{}??????????????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //???????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        //?????????????????????
        PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
        updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
        updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        updateDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        updateDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
        updateDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
        updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        updateDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());

        //????????????????????????
        if (StringUtils.isNotBlank(payTransDtlInfoDO.getCoreRetCode())) {
//            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, AppConstant.DCEP_SYS_ID, payTransDtlInfoDO.getCoreRetCode(), payTransDtlInfoDO.getCoreRetMsg());
//            updateDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
//            updateDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }

        try {
            return this.update(updateDO, stateMachine);
        } catch (Exception e) {
            logger.error("???????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????217?????????111
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatus217To111(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("????????????????????????????????????217?????????111??????????????????{}??????????????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //???????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        //?????????????????????
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
            return this.update(updateDO, stateMachine);
        } catch (Exception e) {
            logger.error("???????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    @Override
    public int updatePathStatus(PayTransDtlInfoDO payTransDtlInfoDO, String payPathStatus) {

        logger.info("????????????????????????????????????{}??????????????????{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        //???????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        stateMachine.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        stateMachine.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());

        payTransDtlInfoDO.setPathProcStatus(payPathStatus);

        try {
            int updateNum = this.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("???????????????????????????,???????????????{}??????????????????{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("?????????????????????????????????????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    @Override
    public int updatePayTransDtlFinal(PayTransDtlInfoDO payTransDtlInfoDO) {

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        payTransDtlInfoDO = this.query(payDate, paySerno);

        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        logger.info("?????????????????????????????????????????????????????????{}??????????????????{}??????????????????{}??????????????????{}??????????????????{}??????????????????{}",
                payDate, paySerno, payTransDtlInfoDO.getMsgType(), trxStatus, coreProcStatus, pathProcStatus);

        //????????????X11?????????111
        boolean statusX11 = (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_SUCCESS.equals(trxStatus)));

        //????????????X00?????????000
        boolean statusX00 = (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_FAILED.equals(trxStatus)));

        //????????????X30?????????330
        boolean statusX30 = (AppConstant.CORESTATUS_REVERSED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                && !(AppConstant.TRXSTATUS_REVERSED.equals(trxStatus)));

        //X90????????????????????????????????????????????????????????????090
        boolean endStatusX90 = (StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_INIT)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_FAILED)
                && !(AppConstant.TRXSTATUS_FAILED.equals(trxStatus))
                && (checkMsgType(payTransDtlInfoDO)));

        //????????????X11???X00???X30??????????????????
        if (statusX11 || statusX00 || statusX30 || endStatusX90) {

            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(trxStatus);
            stateMachine.setPreCoreProcStatus(coreProcStatus);
            stateMachine.setPrePathProcStatus(pathProcStatus);

            //??????X11?????????111
            if (statusX11) {
                logger.info("?????????????????????X11?????????111,???????????????{}??????????????????{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            }

            //??????X00?????????000
            if (statusX00) {
                logger.info("?????????????????????X00?????????000,???????????????{}??????????????????{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setTrxRetCode(payTransDtlInfoDO.getCoreRetCode());
                payTransDtlInfoDO.setTrxRetMsg(payTransDtlInfoDO.getCoreRetMsg());
            }

            //??????X30?????????330
            if (statusX30) {
                logger.info("?????????????????????X30?????????330,???????????????{}??????????????????{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_REVERSED);
            }

            //??????X90??????????????????????????????????????????????????????090
            if (endStatusX90) {
                logger.info("?????????????????????X90?????????090,???????????????{}??????????????????{}", payDate, paySerno);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            }

            try {
                int updateNum = this.update(payTransDtlInfoDO, stateMachine);
                if (updateNum != 1) {
                    logger.error("???????????????????????????,???????????????{}??????????????????{}",
                            payDate, paySerno);
                    throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
                }
                return updateNum;
            } catch (Exception e) {
                logger.error("????????????????????????????????? ????????? {}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

        }

        return 0;
    }

    /**
     * ??????X91?????????191
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int updateFinalStatusX91To191(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) {

        logger.info("????????????????????????????????????X91?????????191??????????????????{}??????????????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        try {
            int updateNum = this.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("????????????????????????????????????X91?????????191??????,???????????????{}??????????????????{}",
                        payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            return updateNum;
        } catch (Exception e) {
            logger.error("???????????????????????????291--???191 ????????? {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

    }


    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public boolean checkMsgType(PayTransDtlInfoDO payTransDtlInfoDO) {

        String msgType = payTransDtlInfoDO.getMsgType();

        //??????????????????????????????????????????
        boolean msgTypeFlag = StringUtils.equalsAny(msgType, MsgTpEnum.DCEP221.getCode(),
                MsgTpEnum.DCEP225.getCode(), MsgTpEnum.DCEP227.getCode());

        //????????????????????????
        boolean dcepSent801 = (StringUtils.equals(MsgTpEnum.DCEP801.getCode(), msgType)
                && StringUtils.equals(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, payTransDtlInfoDO.getInstgPty()));

        boolean flag = msgTypeFlag || dcepSent801;

        logger.info("???????????????{}??????????????????{}?????????????????????????????????????????????????????????????????????,?????????{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), flag);

        return flag;
    }




    //----------------------------------???????????????-------------------------------------------------
    @Autowired
    private PayTransDtlInfoMapper payTransDtlInfoMapper;
    /**
     * ??????????????????????????????
     *
     * @param payTransDtlInfoDO ???????????????????????????
     * @return
     */
    @Override
    public int insert(PayTransDtlInfoDO payTransDtlInfoDO) {
        return payTransDtlInfoMapper.insert(payTransDtlInfoDO);
    }

    /**
     * ???????????????????????????
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int update(PayTransDtlInfoDO payTransDtlInfoDO) {
        //??????????????????
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        return payTransDtlInfoMapper.updateDirect(payTransDtlInfoDO);
    }

    /**
     * ?????????????????????
     * @param payTransDtlInfoDO
     * @param stateMachine
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Override
    public int update(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //??????????????????
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        Map<String, String> param = BeanUtils.describe(payTransDtlInfoDO);
        if (null != stateMachine) {
            param.putAll(BeanUtils.describe(stateMachine));
        }
        return payTransDtlInfoMapper.update(param);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param payDate,paySerno
     * @return
     */
    @Override
    public PayTransDtlInfoDO query(String payDate, String paySerno) {
        Map<String, String> param = new HashMap<>();
        param.put("payDate", payDate);
        param.put("paySerno", paySerno);
        return payTransDtlInfoMapper.queryByPayInfo(param);
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param msgId
     * @return
     */
    @Override
    public PayTransDtlInfoDO query(String msgId) {
        return payTransDtlInfoMapper.queryByMsgId(msgId);
    }

    /**
     * ????????????????????????????????????????????????????????????
     *
     * @param busiSysSerno
     * @return
     */
    @Override
    public PayTransDtlInfoDO queryOriTxn(String busiSysSerno) {
        return payTransDtlInfoMapper.queryByBusiSysSerno(busiSysSerno);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param origPayPathSerno
     * @return
     */
    @Override
    public List<PayTransDtlInfoDO> queryList(String origPayPathSerno) {
        return payTransDtlInfoMapper.queryByOrigSerno( origPayPathSerno);
    }
}
