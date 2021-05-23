package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.CertTypeEnum;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cert.CertNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.cert.CertNtfctnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cert.CertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.model.PayCertInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IPayCertInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数字证书绑定
 */
@Configuration
public class Cert903RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(Cert903RTradeFlow.class);

    private static final String CERT_TRADE_FLOW = "Cert903RTradeFlow";

    private static final String ORIGINAL_MESSAGE_TYPE = "msgTp";

    @Autowired
    private IPayCertInfoService payCertInfoService;


    @Bean(name = CERT_TRADE_FLOW)
    public TradeFlow certTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)      //初始化响应报文
                .process(this::process)        // 处理
                .errHandler(this::errHandler)
                .build();
    }

    /**
     * 初始化返回报文
     *
     * @param context
     */
    public void initRspMsg(TradeContext<?, ?> context) {

        DCEPReqDTO<CertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //初始化返回报文
        CertReqDTO reqBody = reqMsg.getBody();
        CertNtfctn reqDTO = reqBody.getCertNtfctn();

        //设置原始报文编号，响应结果中需要添加该参数
        String msgId = reqMsg.getBody().getCertNtfctn().getGrpHdr().getMsgId();
        context.getTempCtx().put(ORIGINAL_MESSAGE_TYPE, msgId);

        //业务头组件
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getCertNtfctn().getGrpHdr());

        //响应信息
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, reqMsg.getDcepHead(), null);

        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 密钥处理
     *
     * @param context
     */
    private void process(TradeContext<?, ?> context) {

        //获取上下文中的报文数据
        DCEPReqDTO<CertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        logger.info("开始处理数字证书，报文标识号：{}", context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE));
        try {
            CertReqDTO certReqDTO = reqMsg.getBody();
            CertNtfctn certReq = certReqDTO.getCertNtfctn();

            CertNtfctnInf certNtfctnInf = certReq.getCertNtfctnInf();

            String certType = certNtfctnInf.getCertTp();
            //拆分certType
            String realCertType = certType.substring(0, 4);

            //转换certType为人民币系统内部certType
            if (CertTypeEnum.CS00.getCode().equals(realCertType)) {
                realCertType = CertTypeEnum.CT04.getCode();
            } else if (CertTypeEnum.CS01.getCode().equals(realCertType)) {
                realCertType = CertTypeEnum.CT05.getCode();
            }
            //获取证书
            String certInfo = certType.substring(4);

            //解析公钥
            // ...
            String dn = "";
            String sn = "";
            String publickey = "";

            //判断变更类型
            if (ChangeCdEnum.CC00.getCode().equals(certNtfctnInf.getChgTp())) {
                //新增或更新证书
                PayCertInfoDO payCertInfoDO = new PayCertInfoDO();
                payCertInfoDO.setCerttype(realCertType);
                payCertInfoDO.setCertinfo(certInfo);
                payCertInfoDO.setPublickey(publickey);
                payCertInfoDO.setDn(dn);
                payCertInfoDO.setSn(sn);
                payCertInfoService.addOrUpdateCert(payCertInfoDO);
            } else if (ChangeCdEnum.CC02.getCode().equals(certNtfctnInf.getChgTp())) {
                //置为无效
                PayCertInfoDO payCertInfoDO = new PayCertInfoDO();
                payCertInfoDO.setCerttype(realCertType);
                payCertInfoDO.setSn(sn);
                payCertInfoDO.setStatus("0");
                payCertInfoService.updateCert(payCertInfoDO);
            }
        } catch (Throwable e) {
            logger.warn("报文标识号：{}数字证书处理失败,失败原因:{}",
                    context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE),
                    e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 异常处理
     *
     * @param context
     * @param e
     */
    private void errHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<CertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //生成911丢弃报文，在变更执行失败后
        FaultDTO faultDTO = new FaultDTO();
        Fault fault = new Fault();
        faultDTO.setFault(fault);

        // 错误码映射
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(e);

        //设置911报文体数据
        //业务故障信息, 911失败时，响应机构编码
        fault.setFaultActor(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);        //业务故障信息
        fault.setFaultCode(rspCodeMapDO.getDestRspCode());                    //业务故障代码
        fault.setFaultString(rspCodeMapDO.getRspCodeDsp());                   //业务故障说明

        //响应实体
        DCEPRspDTO dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, Constant.DCEP_911, faultDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
        logger.warn("报文文标识号：{}机构状态变更处理失败，响应报文{}", context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE), faultDTO);
    }
}
