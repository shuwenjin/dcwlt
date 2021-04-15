/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月30日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/


package com.dcits.dcwlt.pay.online.baffle.dcep.impl;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqHeader;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.service.impl.CoreTradeTypeRepository;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import com.dcits.dcwlt.pay.online.service.impl.HostEngCfgRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 核心接口服务层
 */

@Service
public class BankCoreImplDubboService {

    private static final Logger logger = LoggerFactory.getLogger(BankCoreImplDubboService.class);

    @Autowired
    private BankCoreDubboServiceImpl bankCoreDubboService;

    @Autowired
    private CoreTradeTypeRepository coreTradeTypeRepository;

    @Autowired
    private HostEngCfgRepository hostEngCfgRepository;
//    @Autowired
//    private CoreServiceSend coreServiceSend;
    @Autowired
    private DcepSendService dcepSendService;

/**
 * 推断核心状态
 *
 * @param rspHead           服务化响应报文头
 * @param bankCoreRspHeader 服务化核心响应报文头
 * @return
 *//*

    private String getCoreStatus(Head rspHead, BankCoreRspHeader bankCoreRspHeader) {
        if (Constant.SERVER_SUCC_RSPCODE.equals(rspHead.getRetCode())) {                                // 交易成功
            return Constant.CORESTATUS_SUCCESS;
        } else if (rspHead.getRetCode().startsWith("SCGW") || rspHead.getRetCode().startsWith("IG")) {  // 网关或核心前置异常
            return Constant.CORESTATUS_ABEND;
        } else {
            if (null == bankCoreRspHeader) {                                                            //未返回核心响应报文头
                return Constant.CORESTATUS_ABEND;
            }
            //核心响应头判断规则
            if ("N".equals(bankCoreRspHeader.getRetStatus())) {                                         // 交易成功
                return Constant.CORESTATUS_SUCCESS;
            } else if ("E".equals(bankCoreRspHeader.getRetStatus())) {                                  // 交易明确失败
                return Constant.CORESTATUS_FAILED;
            } else {    // 交易状态不明
                return Constant.CORESTATUS_ABEND;
            }
        }
    }

    */

    /**
     * 核心记账服务
     *
     * @param bankCoreReqHeader      核心报文头，用于透传渠道信息
     * @param bankCore351100InnerReq 351100接口内部请求实体
     * @return
     */

    public BankCore351100InnerRsp coreServer(BankCoreReqHeader bankCoreReqHeader, BankCore351100InnerReq bankCore351100InnerReq) {
       // 核心请求日期
        String coreReqDate = bankCore351100InnerReq.getCoreReqDate();
        // 获取服务化调用请求流水 --核心请求流水
        String seqNo = bankCore351100InnerReq.getCoreReqSerno();
        // todo 核心请求暂时注掉
//        Map<String, Object> map = initBankCore351100ReqMsg(bankCore351100InnerReq);
//        BankCore351100Req bankCore351100Req = null;
//        if (!map.isEmpty()) {
//            if ("0000".equals(map.get(Constant.CONSTANT_ERRORCODE))) {
//                bankCore351100Req = (BankCore351100Req) map.get("entity");
//            } else {
//                return new BankCore351100InnerRsp(Constant.CORESTATUS_FAILED, (String) map.get(Constant.CONSTANT_ERRORCODE),
//                        (String) map.get(Constant.CONSTANT_ERRORMSG));
//            }
//        } else {
//            return new BankCore351100InnerRsp(Constant.CORESTATUS_FAILED, "9999", "调用核心服务赋值失败");
//        }
//
//        // 构造服务化报文头
//        Head head = bankCoreDubboService.bulidServerHead(seqNo, Constant.SRVCCODE_351100,
//                bankCore351100InnerReq.getSrcCnsmrSysId(), bankCore351100InnerReq.getSrcCnsmrSysSeqNo());
//
//        //应用设置重发标识为Y或者Q的时候则上送resdFlag,核心根据Y则做重发，Q则做查询
//        if (StringUtils.equalsAny(bankCore351100InnerReq.getResendFlag(), Head.REQ_RESD_FLAG, "Q")) {
//            head.setResdFlag(bankCore351100InnerReq.getResendFlag());
//        }
//
//        // 获取核心请求报文
//        BankCoreReqMessage msg = bankCoreDubboService.buildBankCoreMessage(head, bankCoreReqHeader, bankCore351100Req, null);
//
//        // 请求核心并返回响应报文实体
//        BankCoreRspMessage<BankCore351100Rsp, IBankCoreBodyArrayInfo> rspMsg = bankCoreDubboService.bankCoreRequest(msg,
//                BankCore351100Rsp.class, BankCore351100ArrayInfoReq.class);
//
//        // 核心网关超时 核心不返回请求日期、流水 需手动先设置
//        Head rspHead = rspMsg.getHead();
//
//        rspHead.setTranDate(coreReqDate);
//        rspHead.setSeqNo(seqNo);

        String trid="351100";
//        JSONObject RESULT=coreServiceSend.result(trid);
        JSONObject RESULT=dcepSendService.getNonce(trid);
        BankCore351100InnerRsp bankCore351100InnerRsp=JSONObject.parseObject(RESULT.toString(),BankCore351100InnerRsp.class);
        bankCore351100InnerRsp.setCoreReqDate(coreReqDate);
        bankCore351100InnerRsp.setCoreReqSerno(seqNo);
        return bankCore351100InnerRsp;
        // 处理返回结果
//        return dealRspCoreMsg(coreReqDate,seqNo,bankCore351100InnerReq.getReqType());

    }


    /**
     * 核心记账服务
     *
     * @param bankCore351100InnerReq 351100接口内部请求实体
     * @return
     */

    public BankCore351100InnerRsp coreServer(BankCore351100InnerReq bankCore351100InnerReq) {
        // 获取核心请求报文头
        BankCoreReqHeader coreHead = buildBankCoreReqHeader(bankCore351100InnerReq);

        //请求核心
        return coreServer(coreHead, bankCore351100InnerReq);
    }

    /**
     * 构建核心请求报文头
     *
     * @param bankCore351100InnerReq
     * @return
     */

    private BankCoreReqHeader buildBankCoreReqHeader(BankCore351100InnerReq bankCore351100InnerReq) {
        // 获取核心请求报文头
        BankCoreReqHeader coreHead = bankCoreDubboService.buildDefaultBankCoreHeader(Constant.BANKCORE_CRCMPRHACCTNG_CODE,
                StringUtils.defaultIfEmpty(bankCore351100InnerReq.getBrno(), Constant.MASTERBANK));

        //设置清算日期
        if (StringUtil.isNotBlank(bankCore351100InnerReq.getClearDate())) {
            coreHead.setClearDate(bankCore351100InnerReq.getClearDate());
        }

        // 预入账标识
        if ("F".equals(bankCore351100InnerReq.getReqType())) {
            coreHead.setReqType(bankCore351100InnerReq.getReqType());
        }

        //源发起系统渠道大类，由核心系统预先分配
        if (StringUtil.isNotBlank(bankCore351100InnerReq.getReqChnl())) {
            coreHead.setReqChnl(bankCore351100InnerReq.getReqChnl());
        }
        //源发起系统渠道中类，由核心系统预先分配
        if (StringUtil.isNotBlank(bankCore351100InnerReq.getReqChnl2())) {
            coreHead.setReqChnl2(bankCore351100InnerReq.getReqChnl2());
        }
        return coreHead;
    }


    /**
     * 扣帐、入账 核心应答后处理
     *
     * @param reqType 预入账标识
     * @return
     */

    private BankCore351100InnerRsp dealRspCoreMsg(String coreReqDate,String seqNo,String reqType) {
        //获取核心状态
//        Head rspHead = rspMsg.getHead();
//        BankCoreRspHeader bankCoreHeader = rspMsg.getBankCoreHeader();
//        String coreProcStatus = getCoreStatus(rspHead, bankCoreHeader);

        String trid="351100";
//        JSONObject RESULT=coreServiceSend.result(trid);
        JSONObject RESULT=dcepSendService.getNonce(trid);
        BankCore351100InnerRsp core351100InnerRsp=JSONObject.parseObject(RESULT.toString(),BankCore351100InnerRsp.class);

//        //实例化响应报文 todo 核心应答暂时写死
//        BankCore351100InnerRsp core351100InnerRsp = new BankCore351100InnerRsp();
//        core351100InnerRsp.setCoreStatus("1");
//        core351100InnerRsp.setCoreReqDate(coreReqDate);
//        core351100InnerRsp.setCoreReqSerno(seqNo);
//
//        //核心响应报文头不为NULL时赋值
//        core351100InnerRsp.setCoreRspDate("202011");
//        core351100InnerRsp.setCoreRspSerno("1");
//        core351100InnerRsp.setHostDate("2");
//        core351100InnerRsp.setErrorCode("4");
//        core351100InnerRsp.setErrorMsg("5");
//        core351100InnerRsp.setCoreStatus("1");
        return core351100InnerRsp;
    }


/**
 * 返回错误赋值
 *
 * @param errorCode
 * @param errorMsg
 * @return
 *//*

    private Map<String, Object> returnFail(String errorCode, String errorMsg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.CONSTANT_ERRORCODE, errorCode);
        resultMap.put(Constant.CONSTANT_ERRORMSG, errorMsg);
        return resultMap;
    }

    */
/**
 * @param creditEntity
 * @return
 *//*

    private Map<String, Object> initCreditMsg(BankCore351100InnerReq creditEntity) {

        Map<String, Object> resultMap = new HashMap<>();
        BankCore351100Req req = new BankCore351100Req();
        // 检验实体参数
        IOCheckUtil.verify(creditEntity);

        // 获取核心会计引擎
        List<CoreTradeTypeDO> acctMethCfg = coreTradeTypeRepository.query(creditEntity.getPayPath(), creditEntity.getAcctMeth());
        logger.info("会计引擎参数:" + acctMethCfg);

        if (acctMethCfg.size() < 2) {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "缺少会计引擎参数配置");
        }
        CoreTradeTypeDO acctMethCfg1 = acctMethCfg.get(0);
        CoreTradeTypeDO acctMethCfg2 = acctMethCfg.get(1);
        // 借方信息赋值
        if (Constant.COREACCMODE_PROD.equals(acctMethCfg1.getCoreAccMode())) {
            req.setAcType1(acctMethCfg1.getCoreAccMode());
            req.setAc1("");
            req.setAmt1(creditEntity.getAmount());
            req.setCcy1(creditEntity.getCurrency());
            req.setPrdmoCd1(acctMethCfg1.getCorePrdMode());
            req.setProdCd1(acctMethCfg1.getCorePrdCode());
            req.setEventCd1(acctMethCfg1.getCoreEventCode());
            req.setDrAmtNo1(acctMethCfg1.getCoreAccAmountPtr1());
            req.setDrAmtVal1(creditEntity.getAmount());
            req.setOthAcNo(creditEntity.getPayerAcct());
            req.setOthAcName(creditEntity.getPayerName());
            req.setOthBk(creditEntity.getPayerBank());
            req.setTxRef(creditEntity.getNarrative());
            req.setRemarks(creditEntity.getRemark());
            // 判断借方机构类型
            if (Constant.COREACCBRNOTYPE_TABLE.equals(acctMethCfg1.getCoreAccBrnoType1())) {
                req.setAcctBr101(acctMethCfg1.getCoreAccBrno1());
            } else if (Constant.COREACCBRNOTYPE_CHANL.equals(acctMethCfg1.getCoreAccBrnoType1())) {
                req.setAcctBr101(creditEntity.getAcctBrno());
            }
        } else {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "借方会计参数配置错误");
        }


        // 贷方信息赋值
        if (Constant.COREACCMODE_ACCT.equals(acctMethCfg2.getCoreAccMode())) {
            // 贷方户名校验
            req.setChkNameFlg2(creditEntity.getChkNameFlg2());

            req.setAcType2(acctMethCfg2.getCoreAccMode());
            req.setAc2(StringUtils.defaultIfEmpty(creditEntity.getRealPayeeAcct(), creditEntity.getPayeeAcct()));
            req.setName2(StringUtils.defaultIfEmpty(creditEntity.getRealPayeeName(), creditEntity.getPayeeName()));
            req.setAmt2(creditEntity.getAmount());
            req.setCcy2(creditEntity.getCurrency());
            req.setPrdmoCd2("");
            req.setProdCd2(acctMethCfg2.getCorePrdCode());
            req.setEventCd2(acctMethCfg2.getCoreEventCode());
            req.setCrAmtNo1("");
            req.setCrAmtVal1("");
            req.setMmo2(creditEntity.getSummary());
            req.setCrBusiType1(creditEntity.getTransType());
            // 判断贷方机构类型
            if (Constant.COREACCBRNOTYPE_TABLE.equals(acctMethCfg2.getCoreAccBrnoType1())) {
                req.setAcctBr201(acctMethCfg2.getCoreAccBrno1());
            } else if (Constant.COREACCBRNOTYPE_CHANL.equals(acctMethCfg1.getCoreAccBrnoType1())) {
                req.setAcctBr201(creditEntity.getAcctBrno());
            }
        } else {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "贷方会计参数配置错误");
        }


        resultMap.put(Constant.CONSTANT_ERRORCODE, "0000");
        resultMap.put(Constant.CONSTANT_ERRORMSG, "处理成功");
        resultMap.put("entity", req);
        return resultMap;
    }

    */
/**
 * @param debitEntity
 * @return
 *//*

    private Map<String, Object> initDebitMsg(BankCore351100InnerReq debitEntity) {
        Map<String, Object> resultMap = new HashMap<>();
        List<CoreTradeTypeDO> coreTradeList = coreTradeTypeRepository.query(debitEntity.getPayPath(), debitEntity.getAcctMeth());
        if (coreTradeList.size() < 2) {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "缺少会计引擎参数配置");
        }
        BankCore351100Req req = new BankCore351100Req();

        CoreTradeTypeDO acctMethCfg1 = coreTradeList.get(0);
        CoreTradeTypeDO acctMethCfg2 = coreTradeList.get(1);
        // 借方赋值
        if (Constant.COREACCMODE_ACCT.equals(acctMethCfg1.getCoreAccMode())) {
            // 借方户名校验
            req.setChkNameFlg1(debitEntity.getChkNameFlg1());

            req.setAcType1(acctMethCfg1.getCoreAccMode());
            req.setProdCd1(acctMethCfg1.getCorePrdCode());
            req.setAc1(StringUtils.defaultIfEmpty(debitEntity.getRealPayerAcct(), debitEntity.getPayerAcct()));
            req.setName1(StringUtils.defaultIfEmpty(debitEntity.getRealPayerName(), debitEntity.getPayerName()));
            req.setAmt1(debitEntity.getAmount());
            req.setCcy1(debitEntity.getCurrency());
            req.setPrdmoCd1("");
            req.setEventCd1("");
            req.setDrAmtNo1("");
            req.setDrAmtVal1("");
            req.setRvsNo1(debitEntity.getSuspSerno());
            req.setMmo1(debitEntity.getSummary());
            req.setDrBusiType2(StringUtils.defaultIfEmpty(debitEntity.getCusVouchChkInd(), "N")
                    + StringUtils.defaultIfEmpty(debitEntity.getCusVouchPwdInd(), "N"));
            req.setChqType(debitEntity.getCusVouchType());
            req.setChqNo(debitEntity.getCusVouchno());
            req.setChqIssueDt(debitEntity.getCusVouchDate());
            req.setChqPswd(debitEntity.getCusVouchPwd());
            req.setDrBusiType1(StringUtils.defaultIfEmpty(debitEntity.getTransType(), "N"));
            req.setCashFlg1(debitEntity.getCashExInd());

            // 核算机构配置
            // 核算机构类型 0 从数据库取核算机构 1 从核心查询核算机构
            if (Constant.COREACCBRNOTYPE_TABLE.equals(acctMethCfg1.getCoreAccBrnoType1())) {
                req.setAcctBr101(acctMethCfg1.getCoreAccBrno1());
            } else if (Constant.COREACCBRNOTYPE_CHANL.equals(acctMethCfg1.getCoreAccBrnoType1())) {
                req.setAcctBr101(debitEntity.getAcctBrno());
            }
        } else {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "借方会计参数配置错误");
        }

        // 贷方赋值
        if (Constant.COREACCMODE_PROD.equals(acctMethCfg2.getCoreAccMode())) {

            req.setAcType2(acctMethCfg2.getCoreAccMode());
            req.setProdCd2(acctMethCfg2.getCorePrdCode());
            req.setAc2("");
            req.setAmt2(debitEntity.getAmount());
            req.setCcy2(debitEntity.getCurrency());
            req.setPrdmoCd2(acctMethCfg2.getCorePrdMode());
            req.setEventCd2(acctMethCfg2.getCoreEventCode());
            req.setCrAmtNo1(acctMethCfg2.getCoreAccAmountPtr1());
            req.setCrAmtVal1(debitEntity.getAmount());
            req.setOthAcNo(debitEntity.getPayeeAcct());
            req.setOthAcName(debitEntity.getPayeeName());
            req.setOthBk(debitEntity.getPayeeBank());
            req.setOthBknm(debitEntity.getPayeeBankName());
            req.setTxRef(debitEntity.getNarrative());
            req.setRemarks(debitEntity.getRemark());

            // 判断机构类型
            if (Constant.COREACCBRNOTYPE_TABLE.equals(acctMethCfg2.getCoreAccBrnoType1())) {
                req.setAcctBr201(acctMethCfg2.getCoreAccBrno1());
            } else if (Constant.COREACCBRNOTYPE_CHANL.equals(acctMethCfg2.getCoreAccBrnoType1())) {
                req.setAcctBr201(debitEntity.getAcctBrno());
            }
        } else {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "贷方会计参数配置错误");
        }

        resultMap.put(Constant.CONSTANT_ERRORCODE, "0000");
        resultMap.put(Constant.CONSTANT_ERRORMSG, "处理成功");
        resultMap.put("entity", req);
        // 需要返回上层调用者调用状态
        return resultMap;
    }

    */
/**
 * 初始化请求报文信息
 *
 * @param reqCore351100
 * @return
 */

/*
    private Map<String, Object> initBankCore351100ReqMsg(BankCore351100InnerReq reqCore351100) {
        // 获取会计引擎方式
        String mode = hostEngCfgRepository.query(reqCore351100.getServerId(), reqCore351100.getAcctMeth());
        if (StringUtil.isBlank(mode)) {
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "缺少会计引擎参数配置");
        }

        // 根据核算方式和账务方向初始化报文信息
        if ("CREDIT".equals(mode)) {
            return initCreditMsg(reqCore351100);
        } else if ("DEBIT".equals(mode)) {
            return initDebitMsg(reqCore351100);
        } else {
            logger.info("配置不存在");
            return returnFail(PlatformError.INPARAMS_INVALID.getErrorCode(), "缺少会计引擎参数配置");
        }
    }
*/

}
