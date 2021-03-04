package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.GenerateCodeUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmt;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.online.service.impl.FreeFormatServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc 自由格式报文配置类
 */
@Configuration
public class FreeFrmt401RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(FreeFrmt401RTradeFlow.class);


    public static final String FREEFRMT_TRADE_FLOW = "FreeFrmt401RTradeFlow";

    @Autowired
    private FreeFormatServiceimpl freeFormatService;

    @Autowired
    private MonitorMapper monitorMapper;


    @Bean(name = FREEFRMT_TRADE_FLOW)
    public TradeFlow freeFrmtTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initFreeFrmt)                    //封装保存自由格式报文
                .response(this::packComConf)                    //响应902
                .errHandler(this::freeFrmtTradeErrHandler)      //异常处理
                .build();
    }

    /*
     * 处理自由格式报文
     * */
    public void initFreeFrmt(TradeContext<?, ?> context) {
        //获取自由格式报文内容
        DCEPReqDTO<FreeFrmtDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FreeFrmtDTO freeFrmtDTO = reqMsg.getBody();
        FreeFrmt freeFrmt = freeFrmtDTO.getFreeFrmt();
        try {
            //封装自由格式 存入payTransDtlNonf表
            freeFormatService.insertOrUpdateFreeFormat(freeFrmt, AppConstant.DIRECT_RECV, "", AppConstant.TRXSTATUS_INIT);
        } catch (Exception e) {
            logger.error("自由格式操作状态异常:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    /*
     * 异常处理
     * */
    public void freeFrmtTradeErrHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<FreeFrmtDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        logger.error("进入异常处理模块！");

        //更新交易状态
        try {
            freeFormatService.insertOrUpdateFreeFormat(reqMsg.getBody().getFreeFrmt(), AppConstant.DIRECT_RECV, "", AppConstant.TRXSTATUS_FAILED);
        } catch (Exception exception) {
            logger.error("自由格式报文更新失败:{}");
        }
        try {
            // TODO 错误码映射暂时写死
            //RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);
            //异常返回911报文
            Fault fault = new Fault();
            FaultDTO faultDTO = new FaultDTO();
            //业务故障信息:发生错误的机构编码
            fault.setFaultActor(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
            //业务故障代码:业务拒绝码
            fault.setFaultCode("ECNY000");
            //业务故障说明
            fault.setFaultString("异常");
            faultDTO.setFault(fault);
            //封装响应报文
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP911.getCode(), faultDTO);
            EcnyTradeContext.setRspMsg(context, dcepRspDTO);


            //
            //封装异常信息登记入库
            MonitorDO monitorDO = new MonitorDO();
            FreeFrmtDTO freeFrmtDTO = reqMsg.getBody();
            FreeFrmt freeFrmt = freeFrmtDTO.getFreeFrmt();
            //异常交易时间
            monitorDO.setExceptTime(freeFrmt.getGrpHdr().getCreDtTm());
            //异常参数
            StringBuilder stringBuilder = new StringBuilder("msgId:");
            monitorDO.setExcepParams(stringBuilder.append(freeFrmt.getGrpHdr().getMsgId()).toString());
            //异常内容
            monitorDO.setExcepContext(EcnyTransError.INSERT_DATABASE_ERROR.getErrorMsg());
            //异常登记日期
            monitorDO.setExceptDate(DateUtil.getDefaultDate());
            //异常登记流水
            monitorDO.setExceptSerNO(GenerateCodeUtil.generatePlatformFlowNo());
            //异常交易场景
            monitorDO.setExceptScenario(MsgTpEnum.DCEP401.getCode());

            //异常事件最后处理日期
            monitorDO.setLastUpDate(DateUtil.getDefaultDate());
            //异常事件最后处理时间
            monitorDO.setLastUpTime(DateUtil.getDefaultTime());

            logger.info("异常事件:{},流水信息:{},请联系管理员及研发处理", monitorDO.getExceptTime(), monitorDO.getExceptSerNO());
            //将异常信息存入数据库

            logger.info("异常消息存入数据库:{}", monitorDO.toString());
            //先更新监控表,若无数据则插入新数据
            int row = monitorMapper.updateMonitorData(monitorDO);
            if (row == 0) {
                //插入新监控数据
                monitorMapper.insertMonitorData(monitorDO);
            }
        } catch (Exception exception) {
            logger.error("异常消息入库失败,异常信息:{}", exception.getMessage());
        }
    }

    /*
     * 响应902通信级确认报文
     * */
    public void packComConf(TradeContext<?, ?> context) {
        DCEPReqDTO<FreeFrmtDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FreeFrmtDTO freeFrmtDTO = reqMsg.getBody();
        GrpHdr grpHdr = freeFrmtDTO.getFreeFrmt().getGrpHdr();
        DCEPHeader dcepReqHeader = reqMsg.getDcepHead();
        //设置响应报文
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, dcepReqHeader);
        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
        try {
            //封装自由格式 存入payTransDtlNonf表
            freeFormatService.insertOrUpdateFreeFormat(freeFrmtDTO.getFreeFrmt(), AppConstant.DIRECT_RECV, "", AppConstant.TRXSTATUS_SUCCESS);
        } catch (Exception e) {
            logger.info("更新自由格式报文失败:{}", freeFrmtDTO.getFreeFrmt());
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }
}
