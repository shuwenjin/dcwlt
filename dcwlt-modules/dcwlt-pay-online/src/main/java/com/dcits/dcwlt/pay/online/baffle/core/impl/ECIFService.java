package com.dcits.dcwlt.pay.online.baffle.core.impl;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.pay.online.service.impl.ECNYSerNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ECIF 接口
 *
 * @author luchao01
 */
@Service
public class ECIFService {
    private static final Logger logger = LoggerFactory.getLogger(ECIFService.class);

    @Autowired
    private ECNYSerNoService ecnySerNoService;


    private static final String flagY = "Y";

    private static final String flagN = "N";


    /**
     * 检查ECIF9要素信息
     * @param bankCore358040Rsp BankCore358040Rsp
     */
    public void checkAcctInfoByEcif(BankCore358040Rsp bankCore358040Rsp){



//        logger.info("————————————————————————————————————————————————————————————————调用ECIFTR100300----------------------------------------------------");
//        GatewaySoapTransformer trans = new GatewaySoapTransformer();
//        TlphnLabel tlphnLabel = new TlphnLabel();
//        List<LACK_Label> lackLabels = new ArrayList<LACK_Label>();
//        List<ArrLabel> arrLabels = new ArrayList<ArrLabel>();
//        try{
//            String ecifReq = buildEcifTr100300Req(bankCore358040Rsp);
//            logger.debug("-----请求ECIF报文：{}",ecifReq);
//            String ecifResp = invokeECIFTR100300(ecifReq);
//            logger.debug("-----ECIF返回报文：{}",ecifResp);
//            if(StringUtil.isBlank(ecifResp)){
//                logger.error("ECIF九要素检查失败：查无客户信息");
//                throw new EcnyTransException(EcnyTransError.SIGN_ECIF_LACK);
//            }
//            // 1、组装响应报文对象
//            IndividualDetailInfoResponse res = new IndividualDetailInfoResponse();
//            SOAPMessage responseSoap = new SOAPMessage(new GatewayHeader(), res);
//
//            // 2、使用转换器将响应报文字符串转成java对象
//            responseSoap = (SOAPMessage)trans.unmarshal(ecifResp, responseSoap);
//            tlphnLabel = res.getEcifBody().getTlphnLabel();
//            lackLabels = res.getEcifBody().getLackLabel();
//            arrLabels = res.getEcifBody().getArrLabel();
//        }catch (Exception e){
//            logger.error("invokeECIFTR100300返回xml转json异常"+e.getMessage());
//            throw new EcnyTransException(EcnyTransError.PARSE_RSP_ERROR);
//        }
//
//        //证件过期提示
//        if(null != arrLabels && arrLabels.size()>0){
//            ArrLabel arrLabel = arrLabels.get(0);
//            if(null != arrLabel){
//                //过期标记不为空则抛出过期异常
//                if(!StringUtil.isBlank(arrLabel.getExpryDtSts())){
//                    logger.error("ECIF校验失败--账户证件到期");
//                    throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ACCT_CERT_EXPIRED);
//                }
//            }
//        }
//
//        //多人共用手机号标签信息
//        if(null != tlphnLabel){
//            if (flagN.equals(tlphnLabel.getNameFlag())) {
//                //若“NameFlag是否白名单”标识为“N”且“TELEPHONE_FLAG是否联络手机号码共用”或“CELLPHONE_FLAG是否安全手机号码共用”其中一个为“Y，则拒绝交易并返回对应的错误信息”
//                if (flagY.equals(tlphnLabel.getTelephoneFlag())) {
//                    logger.error("ECIF九要素检查失败：联络手机号码共用");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CON_TEL_SHARED);
//                } else if (flagY.equals(tlphnLabel.getCellphoneFlag())) {
//                    logger.error("ECIF九要素检查失败：安全手机号码共用");
//                    throw new EcnyTransException(EcnyTransError.SIGN_SAFE_TEL_SHARED);
//                }
//            }
//        }
//        //九大项信息缺失标签信息
//        if(null != lackLabels && lackLabels.size()>0){
//            LACK_Label lackLabel = lackLabels.get(0);
//            if(null == lackLabel){
//                logger.error("ECIF九要素检查失败：查无客户lackLabel信息");
//                throw new EcnyTransException(EcnyTransError.SIGN_ECIF_LACK);
//            }
//            if(flagN.equals(lackLabel.getExemptFlag())){
//                if (flagY.equals(lackLabel.getRgstrtnTpFalg())) {
//                    logger.error("ECIF九要素检查失败：证件类型缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CERT_TYPE_LACK);
//                } else if (flagY.equals(lackLabel.getRgstrtnNoFalg())) {
//                    logger.error("ECIF九要素检查失败：证件号码缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CERT_NO_LACK);
//                } else if (flagY.equals(lackLabel.getRgstrtnNmFalg())) {
//                    logger.error("ECIF九要素检查失败：证件名称缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CERT_NAME_LACK);
//                } else if (flagY.equals(lackLabel.getExpryDtFlag())) {
//                    logger.error("ECIF九要素检查失败：证件有效期缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CERT_EXPIRY_DATE_LACK);
//                } else if (flagY.equals(lackLabel.getPrmryCntryFlag())) {
//                    logger.error("ECIF九要素检查失败：国籍缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_NATIONALITY_LACK);
//                } else if (flagY.equals(lackLabel.getGndrFlag())) {
//                    logger.error("ECIF九要素检查失败：性别缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_SEX_LACK);
//                } else if (flagY.equals(lackLabel.getPrfsnFlag())) {
//                    logger.error("ECIF九要素检查失败：职业缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_PROFESSION_LACK);
//                } else if (flagY.equals(lackLabel.getCboeTeleFlag())) {
//                    logger.error("ECIF九要素检查失败：国内核心电话缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CORE_TEL_LACK);
//                } else if (flagY.equals(lackLabel.getCboeAddrFlag())) {
//                    logger.error("ECIF九要素检查失败：国内核心地址缺失");
//                    throw new EcnyTransException(EcnyTransError.SIGN_CORE_ADDRESS_LACK);
//                }
//            }
//        }else{
//            logger.error("ECIF九要素检查失败：查无客户lackLabel信息");
//            throw new EcnyTransException(EcnyTransError.SIGN_ECIF_LACK);
//        }
//    }

}}

