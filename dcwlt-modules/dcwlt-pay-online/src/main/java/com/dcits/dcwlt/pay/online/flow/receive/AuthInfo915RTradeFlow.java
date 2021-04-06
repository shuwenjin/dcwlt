package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthInfoReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyInf;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.impl.AuthInfoServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author
 * @desc 业务权限变更配置类
 */

@Configuration
public class AuthInfo915RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(AuthInfo915RTradeFlow.class);

    public static final String CONVER_TRADE_FLOW = "authInfo915TradeFlow";

    @Autowired
    private AuthInfoServiceimpl authInfoServiceimpl;

    @Bean(name = CONVER_TRADE_FLOW)
    public TradeFlow authInfo915TradeFlow() {

        return EcnyTradeFlowBuilder.get()
                .process(this::authInfoProcess)                 //处理业务权限报文
                .response(this::packComConf)                    //响应902
                .errHandler(this::authInfoTradeErrHandler)      //异常处理
                .build();
    }


    /*
     * 接收业务权限变更通知报文
     * */
    public void authInfoProcess(TradeContext<?, ?> context) {
        DCEPReqDTO<AuthInfoReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        AuthInfoReqDTO authInfoReqDTO = reqMsg.getBody();
        AuthrtyChngNtfctn authrtyChngNtfctn = authInfoReqDTO.getAuthrtyChngNtfctn();

        List<AuthrtyInf> authrtyInfList = authrtyChngNtfctn.getAuthrtyInf();
        if (authrtyInfList != null && authrtyInfList.size() != 0) {
            //新增或更新业务权限信息 得到执行失败条数
            int faultCount = authInfoServiceimpl.replaceAuthInfo(authrtyChngNtfctn);
            if (faultCount > 0) {
                logger.info("共有:" + faultCount + "数据插入失败");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        } else {
            logger.error("业务权限列表为空");
            throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
        }
    }


    /*
     * 组装响应902报文
     * */
    public void packComConf(TradeContext<?, ?> context) {
        DCEPReqDTO<AuthInfoReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        AuthInfoReqDTO authInfoReqDTO = reqMsg.getBody();
        //报文头
        GrpHdr grpHdr = authInfoReqDTO.getAuthrtyChngNtfctn().getGrpHdr();
        DCEPHeader dcepReqHeader = reqMsg.getDcepHead();
        //设置响应报文
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, dcepReqHeader);
        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);

    }

    /*
     * 异常处理
     * */
    public void authInfoTradeErrHandler(TradeContext<?, ?> context, Throwable e) {
        logger.error("进入业务权限异常处理模块！");
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);
        logger.error("映射后错误码错误信息：" + rspCodeMapDO);
        DCEPReqDTO<AuthInfoReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        //设置911响应报文
        FaultDTO faultDTO = new FaultDTO();
        Fault fault = new Fault();
        //业务故障信息:发生错误的机构编码
        fault.setFaultActor(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        //业务故障代码:业务拒绝码
        fault.setFaultCode(rspCodeMapDO.getDestRspCode());
        //业务故障说明
        fault.setFaultString(rspCodeMapDO.getRspCodeDsp());
        faultDTO.setFault(fault);
        //封装响应报文
        DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP911.getCode(), faultDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }
}
