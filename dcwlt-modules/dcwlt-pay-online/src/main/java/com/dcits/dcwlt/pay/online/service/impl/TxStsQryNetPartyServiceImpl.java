package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqry.BusinessRpt;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqry.TxStsQrySRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.BizQryRef;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.BizRpt;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.OprlErr;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.OrgnlTxInf;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.Rsn;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.TxStsQryOrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.TxStsQryReq;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.TxStsQryReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.TxStsQryRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.service.IEventRegisterAppService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.ITxStsQryNetPartyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TxStsQryNetPartyServiceImpl implements ITxStsQryNetPartyService {

    private static final Logger logger = LoggerFactory.getLogger(ReCreditCoreQryCallBack.class);

    private static final String EVENT_PAYPATH_STATUS_QRY_CODE = "EVENT_PAYPATH_STATUS_QRY";
    private static final String EVENT = "EVENT";

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    IGenerateCodeService generateCodeService;

    @Autowired
    private IEventRegisterAppService eventRegisterAppService;

    @Override
    public TxStsQrySRspDTO txStsQryNetParty(String msgId) {
        TxStsQrySRspDTO txStsQrySRspDTO;

        try {

            //拼装请求报文
            DCEPReqDTO<TxStsQryReqDTO> dcepReqDTO = buildDcepReqDTO(msgId);

            //发送请求到互联互通平台 todo 发送互联互通，测试暂时返回801查寻交易状态
            //JSONObject rspObj = dcepSendService.sendDcep(dcepReqDTO);
            JSONObject rspObj =
            JSONObject.parseObject("{\n" +
                    "\t\"ecnyHead\": {\n" +
                    "\t\t\"Sender\": \"C1030644021075\",\n" +
                    "\t\t\"SignSN\": \"01\",\n" +
                    "\t\t\"Ver\": \"01\",\n" +
                    "\t\t\"Receiver\": \"C1010311000014\",\n" +
                    "\t\t\"MsgSN\": \"20210113106040120333044574013001\",\n" +
                    "\t\t\"SndDtTm\": \"2021-01-13T20:37:31\",\n" +
                    "\t\t\"MsgTp\": \"dcep.412.001.01\"\n" +
                    "\t},\n" +
                    "\t\"body\": {\n" +
                    "\t\t\"TxStsQryRsp\": {\n" +
                    "\t\t\t\"GrpHdr\": {\n" +
                    "\t\t\t\t\"CreDtTm\": \"2021-01-13T20:33:33\",\n" +
                    "\t\t\t\t\"InstdPty\": {\n" +
                    "\t\t\t\t\t\"InstdDrctPty\": \"C1010311000014\"\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\"InstgPty\": {\n" +
                    "\t\t\t\t\t\"InstgDrctPty\": \"C1030644021075\"\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\"MsgId\": \"20210113106040120333044574013001\"\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\"BizQryRef\": {\n" +
                    "\t\t\t\t\"QryRef\": \"11\",\n" +
                    "\t\t\t\t\"QryNm\": \"111\",\n" +
                    "\t\t\t\t\"QryRs\": \"PR00\"\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\"BizRpt\": {\n" +
                    "\t\t\t\t\"TrnRs\": \"PR00\",\n" +
                    "\t\t\t\t\"Rsn\": {\n" +
                    "\t\t\t\t\t\"PrcCd\": \"11\",\n" +
                    "\t\t\t\t\t\"RjctCd\": \"11\",\n" +
                    "\t\t\t\t\t\"RjctInf\": \"111\"\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\"OrgnlTxInf\": {\n" +
                    "\t\t\t\t\t\"OrgnlMsgId\": \"11\",\n" +
                    "\t\t\t\t\t\"OrgnlInstgPty\": \"22\",\n" +
                    "\t\t\t\t\t\"OrgnlMT\": \"dcep.801.001.01\",\n" +
                    "\t\t\t\t\t\"OrgnlBizTp\": \"11\"\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\"OprlErr\": {\n" +
                    "\t\t\t\t\"RjctInf\": \"11\",\n" +
                    "\t\t\t\t\"Err\": {\n" +
                    "\t\t\t\t\t\"PrcCd\": \"11\",\n" +
                    "\t\t\t\t\t\"RjctCd\": \"222\"\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}");

            //拼装响应报文
            txStsQrySRspDTO = packRspMsg(rspObj);

        }catch (Exception e){
            logger.error("银行回查平台-交易状态查询异常：{}", e.getMessage(), e);
            txStsQrySRspDTO = errRsp();
        }
        return txStsQrySRspDTO;
    }


    /**
     * 拼装请求报文
     * @param orgnlMsgId
     * @return
     */
    private DCEPReqDTO<TxStsQryReqDTO> buildDcepReqDTO(String orgnlMsgId) {
        //报文标识号
        String msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP411.getCode().substring(5, 8), "");

        TxStsQryReqDTO txStsQryReqDTO = new TxStsQryReqDTO();
        GrpHdr grpHdr = GrpHdr.getInstance(msgId.substring(0, 32), AppConstant.DCEP_FINANCIAL_INSTITUTION_CD);

        //原报文主键组件
        TxStsQryOrgnlGrpHdr orgnlGrpHdr = new TxStsQryOrgnlGrpHdr();
        orgnlGrpHdr.setOrgnlMsgId(orgnlMsgId);
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(orgnlMsgId);
        if(payTransDtlInfoDO != null){
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getInstgPty())) {
                orgnlGrpHdr.setOrgnlInstgPty(payTransDtlInfoDO.getInstgPty());
            }
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getMsgType())) {
                orgnlGrpHdr.setOrgnlMT(payTransDtlInfoDO.getMsgType());
            }
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getBusiType())) {
                orgnlGrpHdr.setOrgnlBizTp(payTransDtlInfoDO.getBusiType());
            }
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getBusiKind())) {
                orgnlGrpHdr.setOrgnlBizKind(payTransDtlInfoDO.getBusiKind());
            }
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getPayerWalletId())){
                orgnlGrpHdr.setOrgnlDbtrWltId(payTransDtlInfoDO.getPayerWalletId());
            }
            if(StringUtils.isNotBlank(payTransDtlInfoDO.getPayeeWalletId())){
                orgnlGrpHdr.setOrgnlCdtrWltId(payTransDtlInfoDO.getPayeeWalletId());
            }
       }

        TxStsQryReq txStsQryReq = TxStsQryReq.getInstance(grpHdr,orgnlGrpHdr);
        txStsQryReqDTO.setTxStsQryReq(txStsQryReq);

        String msgSn = generateCodeService.generateMsgSN(msgId);

        DCEPReqDTO<TxStsQryReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP411.getCode(), msgSn , AppConstant.DCEP_FINANCIAL_INSTITUTION_CD, txStsQryReqDTO);
        return dcepReqDTO;
    }


    /**
     * 响应保存封装
     * @param rspObj
     */
    private TxStsQrySRspDTO packRspMsg(JSONObject rspObj) {
        TxStsQrySRspDTO txStsQryRspSDTO;

        if(null == rspObj || null == rspObj.getJSONObject("ecnyHead")){
            txStsQryRspSDTO = errRsp();
        }else {
            String msgTp = rspObj.getJSONObject("ecnyHead").getString("MsgTp");
            if (msgTp.equals(MsgTpEnum.DCEP412.getCode())) {
                txStsQryRspSDTO = setTxStsQryRspSDTO(rspObj);
            } else {
                txStsQryRspSDTO = errRsp();
            }
        }
        return txStsQryRspSDTO;
    }

    /**
     * 互联互通响应报文转换成应答报文
     * @param rspObj
     * @return
     */
    private TxStsQrySRspDTO setTxStsQryRspSDTO(JSONObject rspObj) {
        logger.info("应答报文：{}",rspObj.toJSONString());
        TxStsQrySRspDTO txStsQryRspSDTO = new TxStsQrySRspDTO();

        DCEPRspDTO<TxStsQryRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, TxStsQryRspDTO.class);
        BizQryRef bizQryRef = dcepRspDTO.getBody().getTxStsQryRsp().getBizQryRef();

        txStsQryRspSDTO.setQryRef(bizQryRef.getQryRef());
        txStsQryRspSDTO.setQryNm(bizQryRef.getQryNm());
        txStsQryRspSDTO.setQryRs(bizQryRef.getQryRs());

        //应答拒绝信息，PR01时返回
        if(ProcessStsCdEnum.PR01.getCode().equals(bizQryRef.getQryRs())){
            OprlErr oprlErr = dcepRspDTO.getBody().getTxStsQryRsp().getOprlErr();
            txStsQryRspSDTO.setPrcCd(oprlErr.getErr().getPrcCd());
            txStsQryRspSDTO.setRjctCd(oprlErr.getErr().getRjctCd());
            txStsQryRspSDTO.setRjctInf(oprlErr.getRjctInf());
        }

        //原业务信息，PR00时返回
        if(ProcessStsCdEnum.PR00.getCode().equals(bizQryRef.getQryRs())){
            BizRpt bizRpt = dcepRspDTO.getBody().getTxStsQryRsp().getBizRpt();
            BusinessRpt businessRpt = new BusinessRpt();
            businessRpt.setTrnRs(bizRpt.getTrnRs());

            //原交易原因：当原业务状态为非PR00或PR02时
            if(!ProcessStsCdEnum.PR00.getCode().equals(bizRpt.getTrnRs())
                    && !ProcessStsCdEnum.PR02.getCode().equals(bizRpt.getTrnRs())){
                Rsn rsn = bizRpt.getRsn();
                txStsQryRspSDTO.setPrcCd(rsn.getPrcCd());
                txStsQryRspSDTO.setRjctCd(rsn.getRjctCd());
                txStsQryRspSDTO.setRjctInf(rsn.getRjctInf());
            }

            //原业务信息
            OrgnlTxInf orgnlTxInf = bizRpt.getOrgnlTxInf();
            businessRpt.setOrgnlMsgId(orgnlTxInf.getOrgnlMsgId());
            businessRpt.setOrgnlInstgPty(orgnlTxInf.getOrgnlInstgPty());
            businessRpt.setOrgnlMT(orgnlTxInf.getOrgnlMT());
            businessRpt.setOrgnlBizTp(orgnlTxInf.getOrgnlBizTp());
            businessRpt.setOrgnlBizKind(orgnlTxInf.getOrgnlBizKind());
            businessRpt.setOrgnlBatchId(orgnlTxInf.getOrgnlBatchId());
            businessRpt.setDbtrBankId(orgnlTxInf.getDbtrBankId());
            businessRpt.setCdtrBankId(orgnlTxInf.getCdtrBankId());
            businessRpt.setdC(orgnlTxInf.getdC());
            txStsQryRspSDTO.setBizRpt(businessRpt);
        }
        return txStsQryRspSDTO;
    }

    /**
     * 异常响应失败
     * @return
     */
    private TxStsQrySRspDTO errRsp() {
        TxStsQrySRspDTO txStsQryRspSDTO = new TxStsQrySRspDTO();
        txStsQryRspSDTO.setQryRs(ProcessStsCdEnum.PR01.getCode());
        txStsQryRspSDTO.setPrcCd(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());
        txStsQryRspSDTO.setRjctInf(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        return txStsQryRspSDTO;
    }

    /**
     * 注册通道异常回查事件
     * @param payTransDtlInfoDO
     */
    public void registerTrxStsQry(PayTransDtlInfoDO payTransDtlInfoDO) {
        Map<String, String> param = new HashMap<>();
        param.put("msgId", payTransDtlInfoDO.getPayPathSerno());
        param.put("payDate", payTransDtlInfoDO.getPayDate());
        param.put("paySerno", payTransDtlInfoDO.getPaySerno());
        param.put("coreReqDate", payTransDtlInfoDO.getCoreReqDate());
        param.put("coreReqSerno", payTransDtlInfoDO.getCoreReqSerno());
        param.put("canResn", "查询互联互通交易状态");

        String paramStr = JSON.toJSONString(param,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue);

        EventDealReqMsg eventDealReqMsg = new EventDealReqMsg();
        eventDealReqMsg.setExceptEventCode(EVENT_PAYPATH_STATUS_QRY_CODE);
        eventDealReqMsg.setExceptEventSeqNo(payTransDtlInfoDO.getPayPathSerno());
        eventDealReqMsg.setExceptEventContext(paramStr);

        eventRegisterAppService.registerEvent(eventDealReqMsg, EVENT);

    }

}
