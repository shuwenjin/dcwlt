package com.dcits.dcwlt.pay.online.service;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqry.TxStsQrySRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathQryService implements IEventService {

    private static final Logger logger = LoggerFactory.getLogger(PathQryService.class);

    @Autowired
    private ITxStsQryNetPartyService txStsQryNetPartyService;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private BankRevService bankRevService;

    @Override
    public EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg) {
        //1、获取异常事件配置
        String msgId = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("msgId");
        String payDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("payDate");
        String paySerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("paySerno");
        String coreReqDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("coreReqDate");
        String coreReqSerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("coreReqSerno");

        //2、初始化返回信息
        EventDealRspMsg eventDealRspMsg = initEventRspMsg(eventDealReqMsg);

        try {
            //3、发起交易查询
            TxStsQrySRspDTO txStsQrySRspDTO = txStsQryNetPartyService.txStsQryNetParty(msgId);
            //4、查询后处理
            statusQryDone(txStsQrySRspDTO, payDate, paySerno, coreReqDate, coreReqSerno, eventDealRspMsg);
        } catch (Exception e) {
            logger.error("发送互联互通交易状态查询异常");
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
        }

        return eventDealRspMsg;
    }

    /**
     * 初始化异常事件响应信息
     *
     * @param eventDealReqMsg
     * @return
     */
    private EventDealRspMsg initEventRspMsg(EventDealReqMsg eventDealReqMsg) {
        EventDealRspMsg eventDealRspMsg = new EventDealRspMsg();
        eventDealRspMsg.setExceptEventCode(eventDealReqMsg.getExceptEventCode());
        eventDealRspMsg.setExceptEventSeqNo(eventDealReqMsg.getExceptEventSeqNo());
        return eventDealRspMsg;
    }

    /**
     * 查询后处理
     * @param txStsQrySRspDTO
     * @param payDate
     * @param paySerno
     * @param eventDealRspMsg
     */
    private void statusQryDone(TxStsQrySRspDTO txStsQrySRspDTO, String payDate, String paySerno, String coreReqDate, String coreReqSerno, EventDealRspMsg eventDealRspMsg) {
        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();

        payTransDtlInfoDO.setPayDate(payDate);
        payTransDtlInfoDO.setPaySerno(paySerno);
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);

        PayTransDtlInfoDO oldPayTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(oldPayTransDtlInfoDO.getTrxStatus());
        stateMachine.setPreCoreProcStatus(oldPayTransDtlInfoDO.getCoreProcStatus());
        stateMachine.setPrePathProcStatus(oldPayTransDtlInfoDO.getPathProcStatus());

        Boolean isFailed = false;
        // 查询成功，更新信息
        if (ProcessStsCdEnum.PR00.getCode().equals(txStsQrySRspDTO.getQryRs())) {
            payTransDtlInfoDO.setPayPathRspStatus(txStsQrySRspDTO.getBizRpt().getTrnRs());
            payTransDtlInfoDO.setPayPathRetCode(txStsQrySRspDTO.getBizRpt().getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(txStsQrySRspDTO.getBizRpt().getRjctInf());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            
            // 原交易成功
            if (StringUtils.equalsAny(txStsQrySRspDTO.getBizRpt().getTrnRs(), ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())) {
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
                // 当801差错已经终态，不往下执行
                if (check801Status(oldPayTransDtlInfoDO,txStsQrySRspDTO)) {
                    return;
                }
            } else if (StringUtils.equalsAny(txStsQrySRspDTO.getBizRpt().getTrnRs(), ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode())) {
                // 原交易失败
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);

                payTransDtlInfoDO.setTrxRetCode(txStsQrySRspDTO.getBizRpt().getRjctCd());
                payTransDtlInfoDO.setTrxRetMsg(txStsQrySRspDTO.getBizRpt().getRjctInf());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

                //原交易为汇款兑出
                if(MsgTpEnum.DCEP227.getCode().equals(txStsQrySRspDTO.getBizRpt().getOrgnlMT())){
                    //汇款兑出，如果交易为终态330时，不更新交易状态
                    boolean status330 = (StringUtils.equals(oldPayTransDtlInfoDO.getTrxStatus(), AppConstant.TRXSTATUS_REVERSED)
                            && StringUtils.equals(oldPayTransDtlInfoDO.getCoreProcStatus(), AppConstant.CORESTATUS_REVERSED)
                            && StringUtils.equals(oldPayTransDtlInfoDO.getPathProcStatus(), AppConstant.PAYPATHSTATUS_FAILED));
                    if(status330){
                        logger.info("汇款兑出已到终态，回查不做处理");
                        return;
                    }
                    payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                    isFailed = true;
                }

                //原交易为差错贷记调账
                if (MsgTpEnum.DCEP801.getCode().equals(txStsQrySRspDTO.getBizRpt().getOrgnlMT())) {
                    //差错贷记调账，如果交易为终态090时，不更新交易状态
                    boolean status090 = (AppConstant.TRXSTATUS_FAILED.equals(payTransDtlInfoDO.getTrxStatus())
                            && AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                            && AppConstant.PAYPATHSTATUS_FAILED.equals(payTransDtlInfoDO.getPathProcStatus()));
                    if(status090){
                        logger.info("差错贷记调账已到终态，回查不做处理");
                        return;
                    }
                    payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                }
            } else {
                // 原交易处理中，保持原状态
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            }
        } else {
            // 查询失败，更新信息，继续回查
            payTransDtlInfoDO.setPayPathRetCode(txStsQrySRspDTO.getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(txStsQrySRspDTO.getRjctInf());
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
        }

        try {
            payTransDtlInfoService.update(payTransDtlInfoDO,stateMachine);
        } catch (Exception e) {
            logger.error("更新金融交易簿状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

        // 若交易失败，即时冲正
        if (isFailed) {
            // 即时冲正
            bankRevService.bankRevOnTime(payTransDtlInfoDO);
        }
    }

    private boolean check801Status(PayTransDtlInfoDO oldPayTransDtlInfoDO,TxStsQrySRspDTO txStsQrySRspDTO){
        if (MsgTpEnum.DCEP801.getCode().equals(txStsQrySRspDTO.getBizRpt().getOrgnlMT())) {
            boolean is191 = (StringUtils.equals(oldPayTransDtlInfoDO.getTrxStatus(), AppConstant.TRXSTATUS_SUCCESS)
                    && StringUtils.equals(oldPayTransDtlInfoDO.getCoreProcStatus(), AppConstant.CORESTATUS_INIT)
                    && StringUtils.equals(oldPayTransDtlInfoDO.getPathProcStatus(), AppConstant.PAYPATHSTATUS_SUCCESS));
            if (is191) {
                logger.info("差错贷记调账已做终态，原交易状态：{}-{}-{}",oldPayTransDtlInfoDO.getTrxStatus(),
                        oldPayTransDtlInfoDO.getCoreProcStatus(),oldPayTransDtlInfoDO.getPathProcStatus());
                return true;
            }
            // 非191,查询需要调账的交易
            PayTransDtlInfoDO PayTransDtlInfo221 = payTransDtlInfoService.query(oldPayTransDtlInfoDO.getOrigPayPathSerno());
            if (null == PayTransDtlInfo221) {
                logger.error("差错贷记调账原交易{}不存在，不做更新",oldPayTransDtlInfoDO.getOrigPayPathSerno());
            } else {
                boolean isA01 = (StringUtils.equals(PayTransDtlInfo221.getTrxStatus(), AppConstant.TRXSTATUS_PRECREDITSUCCESS)
                        && StringUtils.equals(PayTransDtlInfo221.getCoreProcStatus(), AppConstant.CORESTATUS_FAILED)
                        && StringUtils.equals(PayTransDtlInfo221.getPathProcStatus(), AppConstant.PAYPATHSTATUS_SUCCESS));
                if (isA01) {
                    // 221已经更新成A01,801未更新成191，继续执行
                    return false;
                }
                // 更新221兑回的交易状态为A01
                StateMachine stateMachine = new StateMachine();
                stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
                stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

                try {
                    PayTransDtlInfo221.setTrxStatus(AppConstant.TRXSTATUS_PRECREDITSUCCESS);
                    logger.error("更新原交易{}，状态{}-{}-{}为A01",PayTransDtlInfo221.getPayPathSerno(),
                            PayTransDtlInfo221.getTrxStatus(),
                            PayTransDtlInfo221.getCoreProcStatus(),PayTransDtlInfo221.getPathProcStatus());
                    payTransDtlInfoService.update(PayTransDtlInfo221,stateMachine);
                } catch (Exception e) {
                    logger.error("更新金融交易簿状态异常： {}-{}", e.getMessage(), e);
                    throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
                }
            }
        }
        return false;
    }
}
