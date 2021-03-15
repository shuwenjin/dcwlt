package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.ClrSummryChk;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.ClrSummryChkDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.online.service.IClrSummryChkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanyangwei
 * @desc 资金调整汇总核对交易配置
 */
@Configuration
public class CapitalCheckClear713RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(CapitalCheckClear713RTradeFlow.class);
    public static final String CLRSUMMRYCHK_TRADE_FLOW = "CapitalCheckClear713RTradeFlow";
    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IClrSummryChkService clrSummryChkService;
    @Autowired
    private MonitorMapper monitorMapper;


    @Bean(name = CLRSUMMRYCHK_TRADE_FLOW)
    public TradeFlow capitalCheckClear713RTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .response(this::packComConf)                //初始化响应报文
                .process(this::initClrSummryChk)            //处理请求报文
                .errHandler(this::txStsQryErrHandler)       //异常处理
                .build();
    }

    /*
     * 资金调整汇总核对报文处理
     * */
    public void initClrSummryChk(TradeContext<?, ?> context) {
        DCEPReqDTO<ClrSummryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        ClrSummryChkDTO clrSummryChkDTO = reqMsg.getBody();
        ClrSummryChk clrSummryChk = clrSummryChkDTO.getClrSummryChk();
        try {
            clrSummryChkService.addClrSummryChk(clrSummryChk);
        } catch (Exception e) {
            logger.error("资金调整汇总核对输入失败,异常信息:{}", e.getMessage());
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /*
     * 初始化902响应报文
     * */
    public void packComConf(TradeContext<?, ?> context) {
        DCEPReqDTO<ClrSummryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        ClrSummryChkDTO clrSummryChkDTO = reqMsg.getBody();
        //获取头信息
        DCEPHeader dcepReqHeader = reqMsg.getDcepHead();
        GrpHdr grpHdr = clrSummryChkDTO.getClrSummryChk().getGrpHdr();
        //封装902响应报文
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, dcepReqHeader);
        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }


    /*
     * 异常处理
     * */
    private void txStsQryErrHandler(TradeContext<?, ?> context, Throwable exception) {
        logger.error("进入业务权限异常处理模块,注册异常事件");
        if (exception instanceof EcnyTransException) {
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
            logger.error("映射后错误码错误信息：" + rspCodeMapDO);
        }

        try {
            DCEPReqDTO<ClrSummryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
            //封装异常信息登记入库
            MonitorDO monitorDO = new MonitorDO();
            ClrSummryChkDTO clrSummryChkDTO = reqMsg.getBody();
            ClrSummryChk clrSummryChk = clrSummryChkDTO.getClrSummryChk();
            //异常交易时间
            monitorDO.setExceptTime(DateUtil.getISODateTime());
            //异常参数
            StringBuilder stringBuilder = new StringBuilder("msgId:");
            monitorDO.setExcepParams(stringBuilder.append(clrSummryChk.getGrpHdr().getMsgId()).toString());
            //异常内容
            monitorDO.setExcepContext(EcnyTransError.INSERT_DATABASE_ERROR.getErrorMsg());
            //异常登记日期
            monitorDO.setExceptDate(DateUtil.getDefaultDate());
            //异常登记流水
            monitorDO.setExceptSerNO(generateCodeService.generatePlatformFlowNo());
            //异常交易场景
            monitorDO.setExceptScenario(MsgTpEnum.DCEP713.getCode());
            //异常事件最后处理日期
            monitorDO.setLastUpDate(DateUtil.getDefaultDate());
            //异常事件最后处理时间
            monitorDO.setLastUpTime(DateUtil.getDefaultTime());

            logger.info("异常事件:{},五羊支付应用:{}.流水信息:{},请联系管理员及研发处理", monitorDO.getExceptTime(), monitorDO.getExcepContext(), monitorDO.getExceptSerNO());
            //将异常信息存入数据库
            logger.info("异常消息存入数据库:{}", monitorDO.toString());
            //先更新监控表,若无数据则插入新数据
            int row = monitorMapper.updateMonitorData(monitorDO);
            if (row == 0) {
                //插入新监控数据
                monitorMapper.insertMonitorData(monitorDO);
            }
        } catch (Exception e) {
            logger.error("异常消息入库失败,异常信息:{}", e.getMessage());
        }
    }
}
