package com.dcits.dcwlt.pay.online.flow.receive;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConf;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ConfInf;
import com.dcits.dcwlt.pay.api.domain.dcep.convert.ConvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.payconvert.PayConvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt.TxEndNtfcntReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt.TxEndNtfctn;
import com.dcits.dcwlt.pay.api.model.PayNotifyDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IPayNotifyService;
import com.dcits.dcwlt.pay.online.service.impl.TxEndNtfcntHandleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交易终态通知
 * @author chenyanchun
 */
@Configuration
public class TxEndNtfcnt909RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(TxEndNtfcnt909RTradeFlow.class);

    private static final String TXENDNTFCNT_TRADE_FLOW = "TxEndNtfcnt909RTradeFlow";

    @Autowired
    private IPayNotifyService payTxEndMsgSerivce;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private TxEndNtfcntHandleServiceImpl txEndNtfcntHandleServiceImpl;

    @Bean(name = TXENDNTFCNT_TRADE_FLOW)
    public TradeFlow txEndNtfcntTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::packRspMsg)
                .process(this::saveTxEndMsg)
                .process(this::TxEndNtfcntHandle)
                .errHandler(this::txEndNtfcntErrHandler)
                .build();
    }

    /**
     * 终态通知请求登记表入库
     * @param context
     */
    private void saveTxEndMsg(TradeContext<?,?> context){
        DCEPReqDTO<TxEndNtfcntReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        TxEndNtfctn txEndNtfctn = reqMsg.getBody().getTxEndNtfctn();


        PayNotifyDO payNotifyDO = new PayNotifyDO();
        payNotifyDO.setTxEndDate(DateUtil.getDefaultDate());
        payNotifyDO.setTxEndSerno(generateCodeService.generatePlatformFlowNo());

        //终态通知请求基本信息
        payNotifyDO.setTxEndMsgId(txEndNtfctn.getGrpHdr().getMsgId());
        payNotifyDO.setTxEndMsgType(MsgTpEnum.DCEP909.getCode());
        payNotifyDO.setTxEndInstgpty(txEndNtfctn.getGrpHdr().getInstgPty().getInstgDrctPty());
        payNotifyDO.setTxEndInstdpty(txEndNtfctn.getGrpHdr().getInstdPty().getInstdDrctPty());
        payNotifyDO.setRemark(txEndNtfctn.getGrpHdr().getRmk());

        //终态信息
        payNotifyDO.setProcessStatus(txEndNtfctn.getOrgnlMsgCntt().getPrcSts());
        payNotifyDO.setProcessCode(txEndNtfctn.getOrgnlMsgCntt().getPrcCd());
        payNotifyDO.setRejectCode(txEndNtfctn.getOrgnlMsgCntt().getRjctCd());
        payNotifyDO.setRejectInf(txEndNtfctn.getOrgnlMsgCntt().getRjctInf());

        //原报文信息
        payNotifyDO.setMsgId(txEndNtfctn.getOrgnlGrpHdr().getOrgnlMsgId());
        payNotifyDO.setMsgType(txEndNtfctn.getOrgnlGrpHdr().getOrgnlMT());
        payNotifyDO.setInstgpty(txEndNtfctn.getOrgnlGrpHdr().getOrgnlInstgPty());

        //根据报文编号转换获取原报文信息
        String orgnlMT = txEndNtfctn.getOrgnlGrpHdr().getOrgnlMT();
        String cntt = txEndNtfctn.getOrgnlMsgCntt().getCntt();

        //通过原报文编号区分原报文类型：兑回、兑出、汇款兑出还是贷记调整
        if(MsgTpEnum.DCEP221.getCode().equals(orgnlMT)){

            logger.info("终态通知原报文对象转换：兑回");
            JSONObject jsonObj = JSONObject.parseObject(cntt);
            ReconvertReqDTO reconvertReqDTO = JSONObject.toJavaObject(jsonObj.getJSONObject("Body"), ReconvertReqDTO.class);
            payNotifyDO.setInstdpty(reconvertReqDTO.getReconvertReq().getGrpHdr().getInstdPty().getInstdDrctPty());
            payNotifyDO.setAmount(AmountUtil.yuanToFen(reconvertReqDTO.getReconvertReq().getTrxInf().getTrxAmt().getValue()));
            payNotifyDO.setCcy(reconvertReqDTO.getReconvertReq().getTrxInf().getTrxAmt().getCcy());

        }else if(MsgTpEnum.DCEP225.getCode().equals(orgnlMT)){

            logger.info("终态通知原报文对象转换：兑出");
            JSONObject jsonObj = JSONObject.parseObject(cntt);
            ConvertReqDTO convertReqDTO = JSONObject.toJavaObject(jsonObj.getJSONObject("body"), ConvertReqDTO.class);
            payNotifyDO.setInstdpty(convertReqDTO.getConvertReq().getGrpHdr().getInstdPty().getInstdDrctPty());
            payNotifyDO.setAmount(AmountUtil.yuanToFen(convertReqDTO.getConvertReq().getTrxInf().getTrxAmt().getValue()));
            payNotifyDO.setCcy(convertReqDTO.getConvertReq().getTrxInf().getTrxAmt().getCcy());

        }else if(MsgTpEnum.DCEP227.getCode().equals(orgnlMT)){

            logger.info("终态通知原报文对象转换：汇款兑出");
            JSONObject jsonObj = JSONObject.parseObject(cntt);
            PayConvertReqDTO payConvertReqDTO = JSONObject.toJavaObject(jsonObj.getJSONObject("body"), PayConvertReqDTO.class);
            payNotifyDO.setInstdpty(payConvertReqDTO.getConvertReq().getGrpHdr().getInstdPty().getInstdDrctPty());
            payNotifyDO.setAmount(AmountUtil.yuanToFen(payConvertReqDTO.getConvertReq().getTrxInf().getTrxAmt().getValue()));
            payNotifyDO.setCcy(payConvertReqDTO.getConvertReq().getTrxInf().getTrxAmt().getCcy());

        }else if(MsgTpEnum.DCEP801.getCode().equals(orgnlMT)){

            logger.info("终态通知原报文对象转换：贷记调整");
            JSONObject jsonObj = JSONObject.parseObject(cntt);
            DsptReqDTO dsptReqDTO = JSONObject.toJavaObject(jsonObj.getJSONObject("body"), DsptReqDTO.class);
            payNotifyDO.setInstdpty(dsptReqDTO.getDsptReq().getGrpHdr().getInstdPty().getInstdDrctPty());
            payNotifyDO.setAmount(AmountUtil.yuanToFen(dsptReqDTO.getDsptReq().getDsptInf().getDsptAmt().getValue()));
            payNotifyDO.setCcy(dsptReqDTO.getDsptReq().getDsptInf().getDsptAmt().getCcy());

        }

        try {
            payTxEndMsgSerivce.insert(payNotifyDO);
        } catch (Exception e) {
            logger.error("终态通知请求登记表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

    }

    /**
     * 终态通知处理
     * @param context
     */
    private void TxEndNtfcntHandle(TradeContext<?,?> context) {
        DCEPReqDTO<TxEndNtfcntReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        TxEndNtfctn txEndNtfctn = reqMsg.getBody().getTxEndNtfctn();
        txEndNtfcntHandleServiceImpl.txEndNtfcntHandle(txEndNtfctn);
    }

    /**
     * 响应报文处理
     * @param context
     */
    private void packRspMsg(TradeContext<?,?> context) {
        DCEPReqDTO<TxEndNtfcntReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //设置响应报文
        TxEndNtfctn txEndNtfctn = reqMsg.getBody().getTxEndNtfctn();
        DCEPHeader dcepReqHeader = reqMsg.getDcepHead();
        ComConfDTO comConfDTO = new ComConfDTO();
        ComConf comConf = new ComConf();
        ConfInf confInf = new ConfInf();
        confInf.setOrgnlInstgPty(txEndNtfctn.getGrpHdr().getInstgPty().getInstgDrctPty());
        confInf.setOrgnlMsgId(txEndNtfctn.getGrpHdr().getMsgId());
        confInf.setOrgnlMT(dcepReqHeader.getMsgTp());
        confInf.setOrigSndDtTm(dcepReqHeader.getSndDtTm());
        confInf.setPrcSts(ProcessStsCdEnum.PR00.getCode());
        comConf.setConfInf(confInf);
        comConfDTO.setComConf(comConf);

        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 终态通知异常处理
     * @param context
     * @param exception
     */
    private void txEndNtfcntErrHandler(TradeContext<?, ?> context, Throwable exception) {
        logger.error("终态通知异常处理");
        packRspMsg(context);
    }

}
