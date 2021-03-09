package com.dcits.dcwlt.pay.online.flow.receive;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventBatchDetailParam;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventBatchTotalParam;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.ReconSummaryChk;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.ReconSummaryChkDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.SummaryChkInf;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.SummaryHdr;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author weimeiyuan
 * @Time 2021/1/3 17:40
 * @Version 1.0
 * Description:机构对账汇总流程  711报文
 */
@Configuration
public class ReconSummaryChk711TradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(ReconSummaryChk711TradeFlow.class);
    private static final String SUMMARY_TRADE_FLOW = "ReconSummaryChk711TradeFlow";
    private static final String INSERT_SQL = "对账汇总711报文数据库新增失败";
    @Autowired
    private ReconSummaryChkimpl reconSummaryChkRepository;
    @Autowired
    private DtlFileInfServiceimpl dtlFileInfRepository;
    @Autowired
    private GenerateCodeServiceImpl generateCodeService;
    @Autowired
    private SummaryInfoService summaryInfoRepository;
    @Autowired
    private EventRegisterAppServiceimpl eventRegisterAppServiceimpl;
    @Autowired
    private EventInfoServiceimpl eventInfoRepository;
    @Autowired
    private MonitorService monitorRepository;
    @Autowired
    private ReconSummaryChkServiceImpl reconSummaryChkService;

    @Bean(name = SUMMARY_TRADE_FLOW)
    public TradeFlow summaryChkTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .response(this::response) //成功响应902
                .process(this::saveSummary)
                .errHandler(this::tradeErrHandler) //异常监控处理
                .build();
    }

    /**
     * 机构汇总711报文业务处理（入库->生产消息）
     * @param tradeContext
     */
    public void saveSummary(TradeContext<?, ?> tradeContext) {
        logger.info("机构对账汇总流程开始-{}");
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO reconSummaryChkDTO = reqMsg.getBody();
        // 接收机构对账汇总数据入库处理
        int count = reconSummaryChkService.saveSummary(tradeContext);
        //step4:消息中心 生产者代码 汇总信息全部成功入库发送消息
        if (count> 0) {
            eventSummary(tradeContext);
        }
        logger.info("机构对账汇总711报文入库流程完成{}", DateUtil.getDefaultTime(), reconSummaryChkDTO.getReconSummaryChk().getGrpHdr().getMsgId());
    }
    /**
     * step4:消息中心，消息汇总
     */
    private void eventSummary(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO summaryChkDTO = reqMsg.getBody();
        ReconSummaryChk reconSummaryChk = summaryChkDTO.getReconSummaryChk();
        //对账汇总核对信息
        SummaryChkInf summaryChkInf = reconSummaryChk.getSummaryChkInf();
        //对账汇总消息头
        SummaryHdr summaryHdr = summaryChkInf.getSummaryHdr();
        String batchDate = summaryHdr.getBatchId().substring(1, 9);
        try {
            EventDealReqMsg msg = new EventDealReqMsg();
            msg.setExceptEventCode("EVENTNCHECKSUMMARY");
         //   msg.setExceptEventSeqNo(SequenceUtil.getSeq());
            //获取批次日期
            EventBatchTotalParam eventBatchTotalParam = new EventBatchTotalParam();
            eventBatchTotalParam.setBatchDate(batchDate);
            eventBatchTotalParam.setBatchId(summaryHdr.getBatchId());
            msg.setExceptEventContext(JSONObject.toJSONString(eventBatchTotalParam));
            eventRegisterAppServiceimpl.registerEvent(msg, "BATCH");
            logger.info("机构对账汇总信息消息入库成功！！事件业务流水:{}", msg.getExceptEventSeqNo());

            EventDealReqMsg msgDetial = new EventDealReqMsg();
            msgDetial.setExceptEventCode("EVENTNCHECKDETIAL");
        //    msgDetial.setExceptEventSeqNo(SequenceUtil.getSeq());
            EventBatchDetailParam eventBatchDetailParam = new EventBatchDetailParam();
            eventBatchDetailParam.setBatchDate(batchDate);
            eventBatchDetailParam.setBatchId(summaryHdr.getBatchId());
            eventBatchDetailParam.setDigitalEnvelope(reqMsg.getDcepHead().getDgtlEnvlp());
            msgDetial.setExceptEventContext(JSONObject.toJSONString(eventBatchDetailParam));
            eventRegisterAppServiceimpl.registerEvent(msgDetial, "BATCH");
            logger.info("明细对账消息发送成功！！！事件业务流水:{}", msgDetial.getExceptEventSeqNo());
        }catch (EcnyTransException e){
            logger.error("消息发送失败{}",e);
            throw new EcnyTransException(e.getErrorMsg(),e.getErrorCode());
        }
    }

    /**
     * step5:异常处理，记录异常登记入库 异常监控
     * @param context
     * @param e
     */
    public void tradeErrHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);
        ReconSummaryChkDTO summaryChkDTO = reqMsg.getBody();
        ReconSummaryChk reconSummaryChk = summaryChkDTO.getReconSummaryChk();
        logger.info("对账汇总异常信息登记入库监控开始msgId={}", reconSummaryChk.getGrpHdr().getMsgId());
        //
        MonitorDO monitorDO = new MonitorDO();
        //异常登记日期
        monitorDO.setExceptDate(DateUtil.getDefaultDate());
        ////异常交易时间 iso
        monitorDO.setExceptTime(reconSummaryChk.getGrpHdr().getCreDtTm());
        //异常登记流水
        monitorDO.setExceptSerNO(generateCodeService.generatePlatformFlowNo());
        //异常内容 汇总对账报文711入CNAP_ONLINE_CHECKPATH失败
        monitorDO.setExcepContext(rspCodeMapDO.getRspCodeDsp());
        //异常交易场景
        monitorDO.setExceptScenario(INSERT_SQL);
        // 异常参数 异常事件请求参数
        monitorDO.setExcepParams(reconSummaryChk.getGrpHdr().getMsgId());
        //异常事件最后处理日期
        monitorDO.setLastUpDate(DateUtil.getDefaultDate());
        //异常事件最后处理时间
        monitorDO.setLastUpTime(DateUtil.getDefaultTime());
        //将异常信息存入数据库
        try {
            logger.info("异常消息存入数据库:{}", monitorDO.toString());
            //先更新监控表,若无数据则插入新数据
            int row = monitorRepository.updateMonitorData(monitorDO);
            if (row == 0) {
                //插入新监控数据
                monitorRepository.insertMonitorData(monitorDO);
            }
        } catch (Exception exception) {
            logger.error("异常消息入库失败,异常信息:{}", exception.getMessage());
        }
        logger.info("异常监控信息入库完成，异常信息：{}", monitorDO);
    }


    /**
     * step6:711机构对账汇总 响应902通信级确认报文
     * @param context
     */
    public void response(TradeContext<?, ?> context) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        //设置响应报文
        ComConfDTO comConfDTO = ComConfDTO.newInstance(reqMsg.getBody().getReconSummaryChk().getGrpHdr(), reqMsg.getDcepHead());
        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
        logger.info("711报文响应902通讯级响应报文成功，响应报文为：{}", dcepRspDTO);
    }

}
