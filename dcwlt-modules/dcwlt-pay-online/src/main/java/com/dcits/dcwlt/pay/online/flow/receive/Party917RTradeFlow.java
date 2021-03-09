package com.dcits.dcwlt.pay.online.flow.receive;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.enums.EffectiveCdEnum;
import com.dcits.dcwlt.common.pay.enums.LogMonitorLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateCompareUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctnDTO;
import com.dcits.dcwlt.pay.online.service.PartyChangeProcess;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 机构变更处理配置
 *
 * @author majun
 */
@Configuration
public class Party917RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(Party917RTradeFlow.class);

    private static final String PARTY_TRADE_FLOW = "Party917RTradeFlow";    //机构变更流程实例名
    private static final String ORIGINAL_MSG_ID = "msgId";            //原报文标识号占位符

    @Resource(name = "effectiveCdProcessManager")
    @Autowired
    private PartyChangeProcess effectiveCdProcess;

    @Bean(name = PARTY_TRADE_FLOW)
    public TradeFlow partyTradeSign() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)       //初始化响应报文
                .process(this::process)             //交易处理，可以多个处理
                .errHandler(this::errHandler)
                .build();
    }


    public void initRspMsg(TradeContext<?, ?> context) {
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //设置报文标识号
        String msgId = reqMsg.getBody().getFinCdChngNtfctn().getGrpHdr().getMsgId();
        context.getTempCtx().put(ORIGINAL_MSG_ID, msgId);

        //生成902响应报文实体
        ComConfDTO comConfDTO = ComConfDTO.newInstance(reqMsg.getBody().getFinCdChngNtfctn().getGrpHdr(), reqMsg.getDcepHead());

        //响应实体
        DCEPRspDTO dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    public void process(TradeContext<?, ?> context) {

        //获取上下文中的报文数据
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FinCdChngNtfctnDTO partyChng = reqMsg.getBody();
        FinCdChngNtfctn finCdChngNtfctn = partyChng.getFinCdChngNtfctn();

        List<Party> partyList = finCdChngNtfctn.getChngInf();
        //标识是否存在变更处理失败
        boolean existProcessFailed = false;
        //失败异常
        Exception ex = null;
        //统计变更处理失败条数统计
        int failedCount = 0;

        //变了机构变更集合，处理机构变更，当存在处理失败，最后响应911报文，但是后续机构变更继续处理
        logger.info("报文标识号：{}开始处理机构变更，变更总数:{}", context.getTempCtx().get(ORIGINAL_MSG_ID), partyList.size());
        for (int i = 0; i < partyList.size(); i++) {
            Party party = partyList.get(i);
            try {
                //机构报文信息核查
                if (!checkPackageParty(party, (String) context.getTempCtx().get(ORIGINAL_MSG_ID))) {
                    logger.warn("报文标识号：{}请求数据格式检查存在错误", context.getTempCtx().get(ORIGINAL_MSG_ID));
                    throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
                }

                //机构变更业务处理逻辑入口
                effectiveCdProcess.change(party, context);
            } catch (Exception e) {
                //标识处理失败
                existProcessFailed = true;
                failedCount++;
                ex = e;
                logger.warn("报文标识号：{}，处理第{}条机构变更失败，变更机构信息{},失败原因{}", context.getTempCtx().get(ORIGINAL_MSG_ID), i, party,
                        e.getMessage(), e);
            }
        }
        logger.info("报文标识号：{}，开始处理机构变更，变更总数:{}", context.getTempCtx().get(ORIGINAL_MSG_ID), partyList.size());
        //存在处理失败，组装911报文
        if (existProcessFailed) {
            errorHandler(context, ex);
            logger.warn("报文标识号：{}，机构变更处理结束，变更总数：{}， 处理失败总数：{}", context.getTempCtx().get(ORIGINAL_MSG_ID), partyList.size(), failedCount);
        }
    }

    /**
     * 检查机构报文是否正常
     *
     * @param party
     * @param msgId
     * @return
     */
    private boolean checkPackageParty(Party party, String msgId) {
        if (party == null) {
            logger.warn("报文标识号：{}，报文格式存在问题，机构必要参数检测不通过，传入机构信息为null", msgId);
            return false;
        }
        //机构变更类型是否正确
        if (party.getChngCtrl().getChngTp() == null) {
            logger.warn("报文标识号：{}，报文格式存在问题，机构必要参数检测不通过，机构变更类型未传入或传入值无法解析", msgId);
            return false;
        }
        //定时生效检查
        if (EffectiveCdEnum.EF01.getCode().equals(party.getChngCtrl().getFctvTp())) {

            //撤销类型
            if (ChangeCdEnum.CC02.getCode().equals(party.getChngCtrl().getChngTp())) {

                //检查失效时间
                if (StringUtil.isBlank(party.getChngCtrl().getIfctvDt())) {
                    logger.warn("报文标识号：{}，报文格式存在问题，定时生效类型中撤销机构变更未提供失效时间", msgId);
                    return false;
                }
            } else {
                //新增，变更，检查生效时间
                if (StringUtil.isBlank(party.getChngCtrl().getFctvDt())) {
                    logger.warn("报文标识号：{}，报文格式存在问题，定时生效类型中新增、变更机构变更未提供失效时间", msgId);
                    return false;
                }
            }
        }

        //生效日期失效日期校验
        if (party.getChngCtrl().getFctvDt() != null && party.getChngCtrl().getIfctvDt() != null) {
            try {
                String fctvDt = DateUtil.formatStandardDateTime(party.getChngCtrl().getFctvDt());
                String ifctvDt = DateUtil.formatStandardDateTime(party.getChngCtrl().getIfctvDt());
                if (DateCompareUtil.compareDate(fctvDt, ifctvDt) > 0) {
                    logger.warn("生效日期{}大于失效日期{}", party.getChngCtrl().getFctvDt(), party.getChngCtrl().getIfctvDt());
                    return false;
                }
            } catch (ParseException e) {
                logger.warn("生失效日期格式错误");
                return false;
            }
        }
        return true;
    }

    /**
     * 错误响应，进行报文丢弃911响应
     *
     * @return
     */
    public void errHandler(TradeContext<?, ?> context, Throwable e) {
        errorHandler(context, e);
    }

    private void errorHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //生成911丢弃报文，在变更执行失败后
        FaultDTO faultDTO = new FaultDTO();
        Fault fault = new Fault();            //911响应报文
        faultDTO.setFault(fault);

        // 错误码映射
//        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);

        //设置911报文体数据
        //业务故障信息, 911失败时，响应机构编码
        fault.setFaultActor(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);        //业务故障信息
//        fault.setFaultCode(rspCodeMapDO.getDestRspCode());                    //业务故障代码
//        fault.setFaultString(rspCodeMapDO.getRspCodeDsp());                   //业务故障说明


        //响应实体
        DCEPRspDTO dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP911.getCode(), faultDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);

        logger.warn("报文标识号：{}, 处理结束，响应报文{}",
                LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_WARNING,
                context.getTempCtx().get(ORIGINAL_MSG_ID), context.getReqMsg());
    }
}
