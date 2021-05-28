package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStatusCodeEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstdPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstgPty;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmt;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtInf;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.IPartyService;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import com.dcits.dcwlt.pay.online.service.impl.FreeFormatServiceimpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc 自由格式发送配置类
 */
@Configuration
public class    FreeFrmt401STradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(DcepTransInTradeFlow.class);

    private static final String ECNY_HEAD = "dcepHead";
    private static final String HEAD = "head";
    private static final String MSG_TYPE = "MesgType";
    public static final String FREEFRMT_TRADE_FLOW = "FreeFrmt401STradeFlow";
    private static FreeFrmt freeFrmt = new FreeFrmt();
    private String tlrNo;

    //报文标识号
    private String msgId;
    //返回信息
    private String retCode = EcnyTransError.SUCCESS.getErrorCode();
    private String retMsg = EcnyTransError.SUCCESS.getErrorMsg();

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private FreeFormatServiceimpl freeFormatServiceimpl;

    @Autowired
    private MonitorMapper monitorMapper;

    @Autowired
    private IAuthInfoService authInfoService;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private DcepSendService dcepSendService;

    //@Autowired
    //private DcepSendService dcepSendService;

    @Autowired
    private DcepService dcepService;

    @Bean(name = FREEFRMT_TRADE_FLOW)
    public TradeFlow freeFrmtTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initTxn)                         //初始化请求报文
                .process(this::sendReqMsg)                      //发送请求报文
                .response(this::packRspMsg)                     //处理响应报文
                .errHandler(this::freeFrmtTradeErrHandler)      //异常处理
                .build();
    }

    /*
     * 初始化请求报文
     * */
    public void initTxn(TradeContext<?, ?> context) {
        //初始化报文标识号
        msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.InnerOrg, MsgTpEnum.DCEP401.getCode().substring(5, 8), "");
        String msgSn = generateCodeService.generateMsgSN(msgId);

        //发起机构是否有发送权限
        boolean sendAuth = authInfoService.validateAuthInfo(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, Constant.DCEP_401, "", AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            //初始化异常内容
            retCode = EcnyTransError.SEND_PARTY_AUTH_ERROR.getErrorCode();
            retMsg = EcnyTransError.SEND_PARTY_AUTH_ERROR.getErrorMsg();
            logger.error("发起机构无发送权限,{}", AppConstant.BANK_FINANCIAL_INSTITUTION_CD);
            throw new EcnyTransException(EcnyTransError.ORGAN_POWER_ERROR);
        }
        ECNYReqDTO<FreeFrmtReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FreeFrmtReqDTO reqDTO = reqMsg.getBody();

        //获取接收机构
        String partyId = reqDTO.getInstdDrctPty();
        if (StringUtils.isBlank(partyId)) {
            //初始化异常内容
            retCode = EcnyTransError.RECV_PARTY_ERROR.getErrorCode();
            retMsg = EcnyTransError.RECV_PARTY_ERROR.getErrorMsg();
            logger.error("接收机构为空->{}", partyId);
            throw new EcnyTransException(EcnyTransError.PARAMS_INVALID);
        }

        //判断接收机构状态
        boolean partyFlag = partyService.isAvailable(partyId);
        if (!partyFlag) {
            //初始化异常内容
            retCode = EcnyTransError.RECV_PARTY_STATUS_ERROR.getErrorCode();
            retMsg = EcnyTransError.RECV_PARTY_STATUS_ERROR.getErrorMsg();
            logger.error("接收机构状态异常,{}", partyId);
            throw new EcnyTransException(EcnyTransError.ORGAN_STATUS_ERROR);
        }
        //判断接收机构权限
        boolean recvFlag = authInfoService.validateAuthInfo(partyId, Constant.DCEP_401, "", AuthInfoDrctEnum.recvAuth);
        if (!recvFlag) {
            //初始化异常内容
            retCode = EcnyTransError.RECV_PARTY_AUTH_ERROR.getErrorCode();
            retMsg = EcnyTransError.RECV_PARTY_AUTH_ERROR.getErrorMsg();
            logger.error("接收机构无接收权限,{}", partyId);
            throw new EcnyTransException(EcnyTransError.ORGAN_POWER_ERROR);
        }
        //消息内容是否合法
        String msgContext = reqDTO.getMsgContext();
        if (StringUtils.isBlank(msgContext) || msgContext.length() > AppConstant.MESSAGE_SIZE) {
            //初始化异常内容
            retCode = EcnyTransError.CONTEXT_ILLEGAL_ERROR.getErrorCode();
            retMsg = EcnyTransError.CONTEXT_ILLEGAL_ERROR.getErrorMsg();
            logger.error("消息内容不合法->{}", msgContext);
            throw new EcnyTransException(EcnyTransError.PARAMS_INVALID);
        }
        //获取柜员号
        tlrNo = reqDTO.getTlrNO();

        //封装请求参数
        FreeFrmtDTO freeFrmtDTO = new FreeFrmtDTO();
        FreeFrmtInf freeFrmtInf = new FreeFrmtInf();
        GrpHdr grpHdr = new GrpHdr();
        //发起机构--广发银行
        InstgPty instgPty = new InstgPty(AppConstant.BANK_FINANCIAL_INSTITUTION_CD);
        //接收机构
        InstdPty instdPty = new InstdPty(partyId);
        //自由格式内容
        freeFrmtInf.setMsgCnt(msgContext);
        //报文发送时间
        grpHdr.setCreDtTm(DateUtil.getISODateTime());
        grpHdr.setMsgId(msgId);

        grpHdr.setInstgPty(instgPty);
        grpHdr.setInstdPty(instdPty);
        freeFrmt.setFreeFrmtInf(freeFrmtInf);
        freeFrmt.setGrpHdr(grpHdr);

        //封装请求报文
        freeFrmtDTO.setFreeFrmt(freeFrmt);
        DCEPReqDTO<FreeFrmtDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP401.getCode(), msgSn, partyId, freeFrmtDTO);
        logger.info("封装请求报文");
        EcnyTradeContext.getTempContext(context).put("dcepReqDTO", dcepReqDTO);

        //报文合法,插入数据库
        try {
            freeFormatServiceimpl.insertOrUpdateFreeFormat(freeFrmt, AppConstant.DIRECT_SEND, tlrNo, AppConstant.TRXSTATUS_INIT);
        } catch (Exception e) {
            //初始化异常内容
            retCode = EcnyTransError.INSERT_DATABASE_ERROR.getErrorCode();
            retMsg = EcnyTransError.INSERT_DATABASE_ERROR.getErrorMsg();
            logger.error("发送自由格式信息存库失败,错误信息:{}", e.getMessage());
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /*
     * 发送请求报文到互联互通
     * */
    public void sendReqMsg(TradeContext<?, ?> context) {
        DCEPReqDTO<FreeFrmtDTO> dcepReqDTO = (DCEPReqDTO<FreeFrmtDTO>) EcnyTradeContext.getTempContext(context).get("dcepReqDTO");
        //发送请求报文,接收响应 TODO 互联互通返回假数据
        JSONObject rspObj = dcepSendService.sendDcep(dcepReqDTO);
        //JSONObject rspObj = dcepService.receive902From401(dcepReqDTO);
        EcnyTradeContext.getTempContext(context).put("rspObj", rspObj);
    }

    /*
     *封装响应报文
     * */
    public void packRspMsg(TradeContext<?, ?> context) {
        //封装五羊支付响应头
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        ECNYReqDTO<FreeFrmtReqDTO> freeFrmtReqDTO = EcnyTradeContext.getReqMsg(context);
        FreeFrmtRspDTO freeFrmtRspDTO = new FreeFrmtRspDTO();
        //获取互联互通响应信息
        JSONObject rspObj = (JSONObject) EcnyTradeContext.getTempContext(context).get("rspObj");


        logger.info("互联互通的响应repObj===>{}",rspObj);
//        String code = (String) rspObj.getJSONObject(HEAD).get("retCode");
//        //平台响应是否成功
//        if (!Constant.SERVER_SUCC_RSPCODE.equals(code)) {
//            //初始化异常内容
//            retCode = code;
//            retMsg = EcnyTransError.RESPOSE_ERROR.getErrorMsg();
//            logger.error("响应报文失败,平台返回码为:{}", code);
//            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
//        }


        //解析 DCEPhead
        DCEPHeader dcepHeader = JSONObject.toJavaObject(rspObj.getJSONObject(AppConstant.DCEP_HEAD), DCEPHeader.class);
        if (null == dcepHeader) {
            //响应头缺失
            throw new EcnyTransException(EcnyTransError.ECNY_CHECK_DCEPHEAD_ERROR);
        }

        //平台响应成功,处理响应报文
        //获取响应报文编号
        String msgTp =rspObj.getJSONObject(AppConstant.DCEP_HEAD).getString(AppConstant.MSG_TYPE);
        if (msgTp.equals(MsgTpEnum.DCEP902.getCode())) {
            //若响应为902报文,解析封装响应报文
            DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, ComConfDTO.class);
            String processStatus = dcepRspDTO.getBody().getComConf().getConfInf().getPrcSts();
            //封装返回结果
            freeFrmtRspDTO.setProcResult(processStatus);
            ecnyRspHead.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        } else if (msgTp.equals(MsgTpEnum.DCEP900.getCode())) {
            //响应报文为900
            DCEPRspDTO<CmonConfDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, CmonConfDTO.class);
            String processStatus = dcepRspDTO.getBody().getCmonConf().getCmonConfInf().getPrcSts();
            //封装返回结果
            freeFrmtRspDTO.setProcResult(processStatus);
            ecnyRspHead.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
        } else if (msgTp.equals(MsgTpEnum.DCEP911.getCode())) {//响应为失败报文
            //响应报文为911
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, FaultDTO.class);
            FaultDTO faultDTO = dcepRspDTO.getBody();
            Fault fault = faultDTO.getFault();
            //详细错误信息
            String detail = fault.getDetail();
            //故障代码
            String faultCode = fault.getFaultCode();
            //业务故障说明
            String faultString = fault.getFaultString();
            //封装返回结果
            retCode = faultCode;
            retMsg = faultString + ":" + detail;
            logger.error("互联互通响应为丢弃报文,错误信息为", retMsg);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);

        }
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(freeFrmtReqDTO, ecnyRspHead, freeFrmtRspDTO, retCode, retMsg);
        EcnyTradeContext.setRspMsg(context, ecnyRspDTO);

        //更新交易状态
        try {
            freeFormatServiceimpl.insertOrUpdateFreeFormat(freeFrmt, AppConstant.DIRECT_SEND, tlrNo, AppConstant.TRXSTATUS_SUCCESS);
        } catch (Exception exception) {
            logger.error("自由格式报文更新失败:{}", freeFrmt);
        }

    }


    /*
     * 异常处理
     * */
    public void freeFrmtTradeErrHandler(TradeContext<?, ?> context, Throwable e) {
        logger.error("进入异常处理模块！");
        if (e instanceof EcnyTransException) {
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);
            logger.error("映射后错误码错误信息：" + rspCodeMapDO);
        }

        //处理失败响应失败报文
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        ECNYReqDTO<FreeFrmtReqDTO> freeFrmtReqDTO = EcnyTradeContext.getReqMsg(context);
        FreeFrmtRspDTO freeFrmtRspDTO = new FreeFrmtRspDTO();
        ecnyRspHead.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        StringBuilder sb = new StringBuilder();
        sb.append(ProcessStatusCodeEnum.PR01.getValue());
        sb.append(retMsg);
        freeFrmtRspDTO.setProcResult(sb.toString());
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(freeFrmtReqDTO, ecnyRspHead, freeFrmtRspDTO, retCode, retMsg);
        EcnyTradeContext.setRspMsg(context, ecnyRspDTO);

        //更新交易状态
        try {
            freeFormatServiceimpl.insertOrUpdateFreeFormat(freeFrmt, AppConstant.DIRECT_SEND, tlrNo, AppConstant.TRXSTATUS_FAILED);
        } catch (Exception exception) {
            logger.error("自由格式报文更新失败:{}", freeFrmt);
        }
        try {
            DCEPReqDTO<FreeFrmtDTO> dcepReqDTO = (DCEPReqDTO<FreeFrmtDTO>) EcnyTradeContext.getTempContext(context).get("dcepReqDTO");
            logger.info("将异常信息存入异常事件表:{}", dcepReqDTO);
            //封装异常信息登记入库
            MonitorDO monitorDO = new MonitorDO();
            //异常登记日期
            monitorDO.setExceptDate(DateUtil.getDefaultDate());
            //异常登记流水
            monitorDO.setExceptSerNO(generateCodeService.generatePlatformFlowNo());
            //异常交易时间
            monitorDO.setExceptTime(DateUtil.getISODateTime());
            //异常交易场景
            monitorDO.setExceptScenario(MsgTpEnum.DCEP401.getCode());
            //异常参数
            StringBuilder stringBuilder = new StringBuilder("msgId:");
            monitorDO.setExcepParams(stringBuilder.append(msgId).toString());
            //异常内容
            monitorDO.setExcepContext(retMsg);
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
                //插入监控数据
                monitorMapper.insertMonitorData(monitorDO);
            }
        } catch (Exception exception) {
            logger.error("异常消息入库失败,异常信息:{}", exception.getMessage());
        }
    }
}
