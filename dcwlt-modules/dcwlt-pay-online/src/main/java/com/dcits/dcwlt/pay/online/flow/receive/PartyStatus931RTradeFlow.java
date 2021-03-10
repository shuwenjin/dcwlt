package com.dcits.dcwlt.pay.online.flow.receive;


import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctnDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn.TrblNtfctnDTO;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.chnldto.party.trblntfctn.INotificationProcess;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 机构状态变更处理配置
 *
 * @author majun
 */
@Configuration
public class PartyStatus931RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(PartyStatus931RTradeFlow.class);

    public static final String PARTY_TRADE_SIGN = "PartyStatus931RTradeFlow";
    //原报文编号占位符
    private static final String ORIGINAL_MESSAGE_TYPE = "msgTp";

    @Autowired
    private INotificationProcess notificationProcess;

    @Bean(name = PARTY_TRADE_SIGN)
    public TradeFlow partyTradeSign() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)
                .process(this::process) //交易处理，可以多个处理
                .errHandler(this::errHandler)
                .build();
    }


    public void initRspMsg(TradeContext<?, ?> context) {
        DCEPReqDTO<TrblNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //设置原始报文编号，响应结果中需要添加该参数
        String msgId = reqMsg.getBody().getTrblNtfctn().getGrpHdr().getMsgId();
        context.getTempCtx().put(ORIGINAL_MESSAGE_TYPE, msgId);

        //生成902响应实体
        ComConfDTO comConfDTO = ComConfDTO.newInstance(reqMsg.getBody().getTrblNtfctn().getGrpHdr(), reqMsg.getDcepHead());
        DCEPRspDTO dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, Constant.DCEP_902, comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    //交易逻辑处理
    public void process(TradeContext<?, ?> context) {
        DCEPReqDTO<TrblNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        logger.info("开始处理机构状态变更，报文标识号：{}", reqMsg.getBody().getTrblNtfctn().getGrpHdr().getMsgId());
        TrblNtfctnDTO trblNtfctn = (TrblNtfctnDTO)reqMsg.getBody();
        try {
            //处理变更，将机构变更状态更新，如果发现当前机构不在库，那么使用请求报文中现有的数据新插入一条数据
            notificationProcess.process(trblNtfctn, context);
        } catch (Throwable e) {
            logger.warn("报文标识号：{}机构状态变更处理失败,失败原因:{}",
                    context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE),
                    e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 错误响应，进行报文丢弃911响应
     *
     * @return
     */
    public void errHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //生成911丢弃报文，在变更执行失败后
        FaultDTO faultDTO = new FaultDTO();
        Fault fault = new Fault();
        faultDTO.setFault(fault);

        // 错误码映射
//        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);
//
//        //设置911报文体数据
//        //业务故障信息, 911失败时，响应机构编码
//        fault.setFaultActor(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);        //业务故障信息
//        fault.setFaultCode(rspCodeMapDO.getDestRspCode());                    //业务故障代码
//        fault.setFaultString(rspCodeMapDO.getRspCodeDsp());                   //业务故障说明

        //响应实体
        DCEPRspDTO dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, Constant.DCEP_911, faultDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
        logger.warn("表文标识号：{}机构状态变更处理失败，响应报文{}", context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE), faultDTO);
    }
}
