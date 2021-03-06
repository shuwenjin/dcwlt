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
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.IEventRegisterAppService;
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

    @Autowired
    private DcepService dcepService;

    @Override
    public TxStsQrySRspDTO txStsQryNetParty(String msgId) {
        TxStsQrySRspDTO txStsQrySRspDTO;

        try {

            //??????????????????
            DCEPReqDTO<TxStsQryReqDTO> dcepReqDTO = buildDcepReqDTO(msgId);

            //????????????????????????????????? todo ???????????????????????????????????????801??????????????????
            //JSONObject rspObj = dcepSendService.sendDcep(dcepReqDTO);
            JSONObject rspObj = dcepService.receive412(dcepReqDTO);

            //??????????????????
            txStsQrySRspDTO = packRspMsg(rspObj);

        }catch (Exception e){
            logger.error("??????????????????-???????????????????????????{}", e.getMessage(), e);
            txStsQrySRspDTO = errRsp();
        }
        return txStsQrySRspDTO;
    }


    /**
     * ??????????????????
     * @param orgnlMsgId
     * @return
     */
    private DCEPReqDTO<TxStsQryReqDTO> buildDcepReqDTO(String orgnlMsgId) {
        //???????????????
        String msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP411.getCode().substring(5, 8), "");

        TxStsQryReqDTO txStsQryReqDTO = new TxStsQryReqDTO();
        GrpHdr grpHdr = GrpHdr.getInstance(msgId.substring(0, 32), AppConstant.DCEP_FINANCIAL_INSTITUTION_CD);

        //?????????????????????
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
     * ??????????????????
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
     * ?????????????????????????????????????????????
     * @param rspObj
     * @return
     */
    private TxStsQrySRspDTO setTxStsQryRspSDTO(JSONObject rspObj) {
        logger.info("???????????????{}",rspObj.toJSONString());
        TxStsQrySRspDTO txStsQryRspSDTO = new TxStsQrySRspDTO();

        DCEPRspDTO<TxStsQryRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, TxStsQryRspDTO.class);
        BizQryRef bizQryRef = dcepRspDTO.getBody().getTxStsQryRsp().getBizQryRef();

        txStsQryRspSDTO.setQryRef(bizQryRef.getQryRef());
        txStsQryRspSDTO.setQryNm(bizQryRef.getQryNm());
        txStsQryRspSDTO.setQryRs(bizQryRef.getQryRs());

        //?????????????????????PR01?????????
        if(ProcessStsCdEnum.PR01.getCode().equals(bizQryRef.getQryRs())){
            OprlErr oprlErr = dcepRspDTO.getBody().getTxStsQryRsp().getOprlErr();
            txStsQryRspSDTO.setPrcCd(oprlErr.getErr().getPrcCd());
            txStsQryRspSDTO.setRjctCd(oprlErr.getErr().getRjctCd());
            txStsQryRspSDTO.setRjctInf(oprlErr.getRjctInf());
        }

        //??????????????????PR00?????????
        if(ProcessStsCdEnum.PR00.getCode().equals(bizQryRef.getQryRs())){
            BizRpt bizRpt = dcepRspDTO.getBody().getTxStsQryRsp().getBizRpt();
            BusinessRpt businessRpt = new BusinessRpt();
            businessRpt.setTrnRs(bizRpt.getTrnRs());

            //??????????????????????????????????????????PR00???PR02???
            if(!ProcessStsCdEnum.PR00.getCode().equals(bizRpt.getTrnRs())
                    && !ProcessStsCdEnum.PR02.getCode().equals(bizRpt.getTrnRs())){
                Rsn rsn = bizRpt.getRsn();
                txStsQryRspSDTO.setPrcCd(rsn.getPrcCd());
                txStsQryRspSDTO.setRjctCd(rsn.getRjctCd());
                txStsQryRspSDTO.setRjctInf(rsn.getRjctInf());
            }

            //???????????????
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
     * ??????????????????
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
     * ??????????????????????????????
     * @param payTransDtlInfoDO
     */
    public void registerTrxStsQry(PayTransDtlInfoDO payTransDtlInfoDO) {
        Map<String, String> param = new HashMap<>();
        param.put("msgId", payTransDtlInfoDO.getPayPathSerno());
        param.put("payDate", payTransDtlInfoDO.getPayDate());
        param.put("paySerno", payTransDtlInfoDO.getPaySerno());
        param.put("coreReqDate", payTransDtlInfoDO.getCoreReqDate());
        param.put("coreReqSerno", payTransDtlInfoDO.getCoreReqSerno());
        param.put("canResn", "??????????????????????????????");

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
