package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.AccTpCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.pay.online.bankcore358040.BankCore358040Req;
import com.dcits.dcwlt.pay.online.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.lsfk43.LSFK43ReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwang
 * @version 1.0.0
 * <p>银行账户校验服务</p>
 * @date 2021/1/2
 */
@Service
public class BankAccountVerifyService {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountVerifyService.class);
    private static final String ERRORCODE = "errorCode";

    private static final String ERRORMSG = "errorMsg";

    private static final String flagY = "Y";

    private static final String flagN = "N";

    private static final String LSFK_SUCCESS_CODE = "0000";

    private static final String[] LSFK_BLACK_LIST = new String[]{"F001", "F002", "F003", "F004", "F005", "F006", "F007", "F008", "F009", "F010", "F011"};

//    @Autowired
//    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    /**
     * 反洗钱接口调用
     *
     * @param custidNo 客户证件号
     * @param custType 客户类型 1-个人，2-公司客户，3-同业客户
     * @param custName 客户名称 交易的客户名称，若存在多个客户名称时，送开户客户名称
     * @param payDate
     * @param payTime
     * @param paySerno
     * @return
     */
    public Map<String, String> geteWayLSFK43Imp(String custidNo, String custType, String custName, String payDate, String payTime, String paySerno) {

        logger.info("\n------------反洗钱接口调用--------------");
        String senderSn = Constant.ECNY_SYS_ID + "_" + payDate + payTime + paySerno.substring(6);
        String tradeTime = payDate + payTime;
        String searchParams = "U^NI^" + custName + "," + custidNo;
        Map<String, String> resultMap = new HashMap<>();
//        GateWayMessage<LSFK43ReqMsg> sendMsg = GateWayMessage.newInstance(Constant.ECNY_SYS_ID, SequenceUtil.getSeq(), "BDP1", "LSFK43");
//        sendMsg.getHead().setVersionNo("2.0");
        LSFK43ReqMsg body = new LSFK43ReqMsg();
        body.setSenderSn(senderSn);
        body.setChannel(Constant.ECNY_SYS_ID);
        body.setTradeTime(tradeTime);
        body.setAlertType("3");
        body.setCustType(custType);
        body.setCustName(custName);
        body.setAlertReason("交易主体");
        body.setRtType("3012");
        body.setCustIdNo(custidNo);
        body.setSysFlag(Constant.ECNY_SYS_ID);
        body.setSearchParams(searchParams);
//        sendMsg.setBody(body);
        String[] arr = new String[]{"F001", "F002", "F003", "F004", "F005", "F006", "F007", "F008", "F009", "F010", "F011"};
        try {
//            GateWayMessage<LSFK43RspMsg> respMsg = GwHttpUtil.sendXml(sendMsg, LSFK43RspMsg.class);
//            String chkMsg = respMsg.getBody().getSearchResult();
            String errorCode = "";
            String errorMsg = "";
            String msc = "";
//            errorCode = respMsg.getBody().getResultCode();
            if (LSFK_SUCCESS_CODE.equals(errorCode)) {
                errorMsg = "账号检查正常";
            } else {
                errorCode = AppConstant.LSFK43_ACCOUNT_ERROR;
                errorMsg = "账户为银行黑名单账户或因风控拒绝交易";
            }
//            for (String chkArr : arr) {
//                if (chkMsg.indexOf(chkArr) > -1) {
//                    errorCode = AppConstant.LSFK43_ACCOUNT_AUTH_ERROR;
//                    msc += chkArr;
//                    errorMsg = "尊敬的客户，您的身份信息存在异常，根据监管规定，我行需进一步核实，暂时无法提供服务";
//                }
//            }
            resultMap.put(ERRORCODE, errorCode);
            resultMap.put(ERRORMSG, errorMsg);
            return resultMap;
        } catch (Exception e) {
            resultMap.put(ERRORCODE, EcnyTransError.GATEWAY_REQUEST_ERROR.getErrorCode());
            resultMap.put(ERRORMSG, "调用反洗钱接口失败");
            return resultMap;
        }
    }

    /**
     * 服务化调用反洗钱
     *
     * @param custidNo 客户证件号
     * @param custType 客户类型 1-个人，2-公司客户，3-同业客户
     * @param custName 客户名称 交易的客户名称，若存在多个客户名称时，送开户客户名称
     * @param payDate  交易日期
     * @param payTime  交易时间
     * @param paySerno 序列号
     * @return Map
     */
    public Map<String, String> invokeLSFK43(String custidNo, String custType, String custName, String payDate, String payTime, String paySerno) {
        logger.info("\n------------反洗钱接口调用--------------");

        Map<String, String> resultMap = new HashMap<>();
//        LSFK43ReqDTO reqDTO = buildLSFKReqDTO(custidNo, custType, custName, payDate, payTime, paySerno);
//        String resp = RpcHttpJsonUtil.executeExt(ApiConstant.LSFK43_SERVER_NAME, JsonUtil.toJSONString(reqDTO));
//        JSONObject respJson = JSONObject.parseObject(resp);
//        logger.info("请求零售风控报文：{}", reqDTO);
//        logger.debug("零售风控返回报文： {}"+resp);
        //错误码、错误信息
        String errorCode = "";
        String errorMsg = "";
        try {
            //LSFK返回的错误码
//            errorCode = respJson.getJSONObject("dataDto").getString("resultCode");
//            //LSFK返回结果
//            String searchRet = respJson.getJSONObject("dataDto").getString("searchResult");
            if (LSFK_SUCCESS_CODE.equals(errorCode)) {
                errorMsg = "账号检查正常";
            } else {
                errorCode = AppConstant.LSFK43_ACCOUNT_ERROR;
                errorMsg = "账户为银行黑名单账户或因风控拒绝交易";
            }

//            if(StringUtils.isEmpty(searchRet)){
//                searchRet = "";
//            }

//            // 是否命中黑名单
//            boolean hit = Arrays.stream(LSFK_BLACK_LIST).anyMatch(searchRet::contains);
//            logger.info("命中黑名单的类型：{}",searchRet);

//            if(hit){
//                errorCode = AppConstant.LSFK43_ACCOUNT_AUTH_ERROR;
//                errorMsg = "尊敬的客户，您的身份信息存在异常，根据监管规定，我行需进一步核实，暂时无法提供服务";
//            }

            resultMap.put(ERRORCODE, errorCode);
            resultMap.put(ERRORMSG, errorMsg);

        } catch (Exception e) {
            resultMap.put(ERRORCODE, EcnyTransError.PARSE_RSP_ERROR.getErrorCode());
            resultMap.put(ERRORMSG, "调用反洗钱接口失败");
        }
        return resultMap;
    }


    /**
     * 行内账户与平台账户类型转换
     *
     * @param bankCore358040Rsp
     * @return
     */
    public AccTpCdEnum transAcctTp(BankCore358040Rsp bankCore358040Rsp) {
        try {
            //1.客户类型TYPE为2-对公、3-同业，
            //2、账户类型不为M-保证金户，
            //3、AC_ATTR账户类型为11-活期或者26-借记卡
            //映射为AT03-单位银行结算账户
            if (StringUtils.equalsAny(bankCore358040Rsp.getType(), "2", "3")
                    && (!"M".equals(bankCore358040Rsp.getAcType()))
                    && (StringUtils.equalsAny(bankCore358040Rsp.getAcAttr(), "11", "26"))
            ) {
                return AccTpCdEnum.AT03;
            } else if ("C".equals(bankCore358040Rsp.getAcType())) {
                // AC_TYPE为C-基本存款账户,映射为AT04-基本存款账户
                return AccTpCdEnum.AT04;
            } else if ("D".equals(bankCore358040Rsp.getAcType())) {
                // AC_TYPE为D-一般存款账户,映射为AT05-一般存款账户
                return AccTpCdEnum.AT05;
            } else if (StringUtils.equalsAny(bankCore358040Rsp.getAcType(), "H", "I", "J")) {
                // AC_TYPE为H-临时机构户,I-非临时机构户,J-增资验资户，映射为AT06-临时存款账户
                return AccTpCdEnum.AT06;
            } else if ("1".equals(bankCore358040Rsp.getAcClass())) {
                // I类户，映射为AT00-个人银行借记账户
                return AccTpCdEnum.AT00;
            } else {
                logger.info("账户类型不支持");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UN_SUPPORTED_ACC_TP);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 账户验证
     *
     * @param msgType 交易类型
     * @param req     账户验证银行核心请求报文体
     * @return BankCore358040Rsp
     */
    public BankCore358040Rsp queryAccount(String msgType, BankCore358040Req req) {
//        logger.info("开始校验账户信息");
//        //构建服务化报文头
//        Head head = HeadUtil.newHead(Constant.SRVCCODE_358040, Constant.CORE_SYS_ID, generateCodeService.generateCoreReqSerno());
//        //请求核心账户验证接口
//        BankCoreRspMessage<BankCore358040Rsp, BankCore358040ArrayInfoRsp> bankCoreRspMessage = bankCoreImplDubboService.queryAccount(head, req);
//        //处理响应报文
//        String retCode = bankCoreRspMessage.getHead().getRetCode();
//        if (!retCode.equals(Constant.SERVER_SUCC_RSPCODE) || !bankCoreRspMessage.getBankCoreHeader().getRetStatus().equals("N")) {
//            logger.info("校验账户信息结果：{}-{}", bankCoreRspMessage.getHead().getRetCode(), bankCoreRspMessage.getHead().getRetInfo());
//            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, AppConstant.CRPM_SYS_ID, retCode, bankCoreRspMessage.getHead().getRetInfo());
//        }
//        BankCore358040Rsp body = bankCoreRspMessage.getBody();
        return null;
    }

}

