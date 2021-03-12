/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月30日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.*;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.*;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040ArrayInfoRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Req;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Req;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.util.APPUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.IOCheckUtil;
import com.dcits.dcwlt.pay.api.model.CoreTradeTypeDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心接口服务层
 *
 * @author zhanguohai
 */
@Service
public class BankCoreImplDubboServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(BankCoreImplDubboServiceImpl.class);

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private BankCoreDubboServiceImpl bankCoreDubboServiceImpl;

    @Autowired
    private CoreTradeTypeRepository coreTradeTypeRepository;

    @Autowired
    private HostEngCfgRepository hostEngCfgRepository;

    /**
     * 账户查询接口，只传账号
     *
     * @param acc 账号
     * @return
     */
    public BankCoreRspMessage queryAccount(String acc) {
        return queryAccount("", "", acc);
    }


    /**
     * 账户查询接口，只传账号
     *
     * @param srcCnsmrSysId    源交易请求系统标识
     * @param srcCnsmrSysSeqNo 源交易请求系统流水
     * @param acc              账号
     * @return
     */
    public BankCoreRspMessage queryAccount(String srcCnsmrSysId, String srcCnsmrSysSeqNo, String acc) {
        // 获取服务化调用请求流水
        String seqNo = generateCodeService.generateCoreReqSerno();

        //构造服务化报文头
       // Head head = bankCoreDubboService.bulidServerHead(seqNo, Constant.SRVCCODE_358040, srcCnsmrSysId, srcCnsmrSysSeqNo);

        // 构造核心报文体
        BankCore358040Req req = new BankCore358040Req();
        req.setAc(acc);

        // 请求核心返回响应报文实体
        return queryAccount(req.toString());
    }


    /**
     * 账户查询接口，只传账号
     *
     * @param req  核心请求体
     * @return
     */
    public BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> queryAccount(BankCore358040Req req) {
        // 构造核心请求报文头
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_QUERYACC_CODE, Constant.MASTERBANK);

        //设置校验户名
        if (StringUtil.isNotBlank(req.getName())) {
            req.setChkName(Constant.CHECK_FLAG_Y);
        }

        //设置校验证件号码
        if (StringUtil.isNotBlank(req.getIdNo())) {
            req.setChkId(Constant.CHECK_FLAG_Y);
        }

        //设置校验手机号
        if (StringUtil.isNotBlank(req.getTelNo())) {
            req.setChkTel(Constant.CHECK_FLAG_Y);
        }

        if (StringUtil.isNotBlank(req.getCvn2())) {
            req.setChkCvn2(Constant.CHECK_FLAG_Y);
        }

        // 构造核心请求报文
        BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> msg = bankCoreDubboServiceImpl.buildBankCoreMessages(coreHead, req, null);
       // return msg;
        // 请求核心返回响应报文实体
        return bankCoreDubboServiceImpl.bankCoreRequest(msg, BankCore358040Rsp.class, BankCore358040ArrayInfoRsp.class);
    }

    /**
     * 回查核心状态
     *
     * @param reqSysDate 原交易请求日期
     * @param reqSysJrn  原交易请求流水
     * @return
     */
    public BankCore996666Rsp queryCoreStatus(String reqSysDate, String reqSysJrn) {
        return queryCoreStatus("", "");
    }

    /**
     * 回查核心状态
     *
     * @param srcCnsmrSysId    源交易请求系统标识
     * @param srcCnsmrSysSeqNo 源交易请求系统流水
     * @param reqSysDate       原交易请求日期
     * @param reqSysJrn        原交易请求流水
     * @return
     */
    public BankCore996666Rsp queryCoreStatus(String srcCnsmrSysId, String srcCnsmrSysSeqNo, String reqSysDate, String reqSysJrn) {

        // 获取服务化调用请求流水``
        String seqNo = generateCodeService.generateCoreReqSerno();

        //构造服务化报文头
       // Head head = bankCoreDubboService.bulidServerHead(seqNo, Constant.SRVCCODE_996666, srcCnsmrSysId, srcCnsmrSysSeqNo);

        // 构造核心请求报文头
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_QUERYSTATUS_CODE, Constant.MASTERBANK);

        // 构造核心请求报文体
        BankCore998889Req  req = new BankCore998889Req ();
        req.setReqSysId(APPUtil.getAppId());
        req.setReqSysDate(reqSysDate);
        req.setReqSysJrn(reqSysJrn);

        // 构造服务化请求报文
        BankCoreReqMessage msg = bankCoreDubboServiceImpl.buildBankCoreMessage(coreHead, req, null);

        // 请求核心服务
        BankCoreRspMessage rsp = bankCoreDubboServiceImpl.bankCoreRequests(msg, BankCore998889Req.class, null);

        // 后续补充检查
        return (BankCore996666Rsp) rsp.getBody();
    }

    /**
     * 核心冲正接口
     *
     * @param seqNo      冲正请求流水
     * @param reqSysDate 原交易请求日期
     * @param reqSysJrn  原交易请求流水
     * @param canResn    冲正原因
     * @return
     */
    public BankCore998889Rsp bankRev(String seqNo, String reqSysDate, String reqSysJrn, String canResn) {

        // 构造核心请求报文头
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_BANKREV_CODE, Constant.MASTERBANK);

        return bankRev(coreHead,seqNo,reqSysDate,reqSysJrn,canResn);
    }

    /**
     * 核心冲正接口，核心头外传
     *
     * @param coreHead
     * @param seqNo
     * @param reqSysDate
     * @param reqSysJrn
     * @param canResn
     * @return
     */
    public BankCore998889Rsp bankRev(BankCoreReqHeader coreHead, String seqNo, String reqSysDate, String reqSysJrn, String canResn) {

        //构造服务化报文头
        //Head head = bankCoreDubboService.bulidServerHead(seqNo, Constant.SRVCCODE_998889, APPUtil.getAppId(), seqNo);

        // 构造请求报文
        BankCore998889Req req = new BankCore998889Req();
        req.setReqSysId(APPUtil.getAppId());
        req.setReqSysDate(reqSysDate);
        req.setReqSysJrn(reqSysJrn);
        req.setCanResn(canResn);

        // 构造服务化报文
        BankCoreReqMessage msg = bankCoreDubboServiceImpl.buildBankCoreMessage(coreHead, req, null);

        // 发送核心请构造响应实体
        //BankCoreRspMessage<BankCore998889Rsp, IBankCoreBodyArrayInfo> rsp = bankCoreDubboService.bankCoreRequest(msg, BankCore998889Rsp.class, null);

        //处理响应报文
        return null;
        //return dealRspReverseCoreMsg(rsp);
    }

    /**
     * 处理冲正应答后处理
     *
     * @param rsp
     * @return
     */
    private BankCore998889Rsp dealRspReverseCoreMsg(BankCoreRspMessage<BankCore998889Rsp, IBankCoreBodyArrayInfo> rsp) {
        //推断核心状态
       // Head rspHead = rsp.getHead();
        BankCoreRspHeader bankCoreHeader = rsp.getBankCoreHeader();
        String coreProcStatus = getCoreStatus(bankCoreHeader);

        //设置冲正处理响应报文
        BankCore998889Rsp rspMsg = new BankCore998889Rsp();
        //rspMsg.setReqCoreDate(rspHead.getTranDate());
        //rspMsg.setReqCoreSerno(rspHead.getSeqNo());
        rspMsg.setCoreStatus(coreProcStatus);

        //核心响应报文头不为NULL时赋值
        if (null != bankCoreHeader) {
          //  rspMsg.setErrorCode(rspHead.getRetCode());
            //rspMsg.setErrorMsg(rspHead.getRetInfo());
            rspMsg.setRspCoreDate(bankCoreHeader.getAcDate());
            rspMsg.setRspCoreSerno(bankCoreHeader.getJrnNo());
        } else {
            rspMsg.setErrorCode(PlatformError.COMM_ABEND.getErrorCode());
            rspMsg.setErrorMsg(PlatformError.COMM_ABEND.getErrorMsg());
        }
        return rspMsg;
    }



    /**
     * 推断核心状态
     *
     * @param bankCoreRspHeader 服务化核心响应报文头
     * @return
     */
    private String getCoreStatus(BankCoreRspHeader bankCoreRspHeader) {
//        if (Constant.SERVER_SUCC_RSPCODE.equals(rspHead.getRetCode())) {                                // 交易成功
            return Constant.CORESTATUS_SUCCESS;
//        } else if (rspHead.getRetCode().startsWith("SCGW") || rspHead.getRetCode().startsWith("IG")) {  // 网关或核心前置异常
//            return Constant.CORESTATUS_ABEND;
//        } else {
//            if (null == bankCoreRspHeader) {                                                            //未返回核心响应报文头
//                return Constant.CORESTATUS_ABEND;
//            }
//            //核心响应头判断规则
//            if ("N".equals(bankCoreRspHeader.getRetStatus())) {                                         // 交易成功
//                return Constant.CORESTATUS_SUCCESS;
//            } else if ("E".equals(bankCoreRspHeader.getRetStatus())) {                                  // 交易明确失败
//                return Constant.CORESTATUS_FAILED;
//            } else {    // 交易状态不明
//                return Constant.CORESTATUS_ABEND;
//            }
//        }
    }

    /**
     * 核心记账服务
     *
     * @param bankCoreReqHeader      核心报文头，用于透传渠道信息
     * @param bankCore351100InnerReq 351100接口内部请求实体
     * @return
     */
    public BankCore351100InnerRsp coreServer(BankCoreReqHeader bankCoreReqHeader, BankCore351100InnerReq bankCore351100InnerReq) {
        // 核心请求日期
//        String coreReqDate = bankCore351100InnerReq.getCoreReqDate();
//        // 获取服务化调用请求流水 --核心请求流水
//        String seqNo = bankCore351100InnerReq.getCoreReqSerno();
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
//                bankCore351100InnerReq.getSrcCnsmrSysId(), bankCore351100InnerReq.getSrcCnsmrSysSeqNo();
//
//        应用设置重发标识为Y或者Q的时候则上送resdFlag,核心根据Y则做重发，Q则做查询
//        if (StringUtils.equalsAny(bankCore351100InnerReq.getResendFlag(), "Q")) {
//            head.setResdFlag(bankCore351100InnerReq.getResendFlag());
//        }
//
//         //获取核心请求报文
//        BankCoreReqMessage msg = bankCoreDubboService.buildBankCoreMessage(bankCoreReqHeader, bankCore351100Req, null);
//
//         //请求核心并返回响应报文实体
//        BankCoreRspMessage<BankCore351100Rsp, IBankCoreBodyArrayInfo> rspMsg = bankCoreDubboService.bankCoreRequest(msg,
//                BankCore351100Rsp.class, BankCore351100ArrayInfoReq.class);
//
//         //核心网关超时 核心不返回请求日期、流水 需手动先设置
//        Head rspHead = rspMsg.getHead();
//
//        rspHead.setTranDate(coreReqDate);
//        rspHead.setSeqNo(seqNo);
//
//         //处理返回结果
//        return dealRspCoreMsg(rspMsg, bankCore351100InnerReq.getReqType());
        BankCore351100InnerRsp bankCore351100InnerRsp = new BankCore351100InnerRsp();
        bankCore351100InnerRsp.setCoreRspSerno("12334");
        bankCore351100InnerRsp.setCoreStatus("1");
        bankCore351100InnerRsp.setErrorCode("CI0016");
        bankCore351100InnerRsp.setErrorMsg("客户名称不一致");
        bankCore351100InnerRsp.setCoreRspDate(DateUtil.getDefaultDate());
        return  bankCore351100InnerRsp;

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
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_CRCMPRHACCTNG_CODE,
                StringUtils.isNotBlank(bankCore351100InnerReq.getBrno())?bankCore351100InnerReq.getBrno():"");

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
        if(StringUtil.isNotBlank(bankCore351100InnerReq.getReqChnl2())){
            coreHead.setReqChnl2(bankCore351100InnerReq.getReqChnl2());
        }
        return coreHead;
    }


    /**
     * 扣帐、入账 核心应答后处理
     *
     * @param rspMsg  核心351100响应报文体
     * @param reqType 预入账标识
     * @return
     */
    private BankCore351100InnerRsp dealRspCoreMsg(BankCoreRspMessage rspMsg, String reqType) {
        //获取核心状态
       // Head rspHead = rspMsg.getHead();
        BankCoreRspHeader bankCoreHeader = rspMsg.getBankCoreHeader();
        String coreProcStatus = getCoreStatus(bankCoreHeader);

        //实例化响应报文
        BankCore351100InnerRsp core351100InnerRsp = new BankCore351100InnerRsp();
        core351100InnerRsp.setCoreStatus(coreProcStatus);
//        core351100InnerRsp.setCoreReqDate(rspHead.getTranDate());
//        core351100InnerRsp.setCoreReqSerno(rspHead.getSeqNo());

        //核心响应报文头不为NULL时赋值
        if (null != bankCoreHeader) {
            core351100InnerRsp.setCoreRspDate(bankCoreHeader.getAcDate());
            core351100InnerRsp.setCoreRspSerno(bankCoreHeader.getJrnNo());
            core351100InnerRsp.setHostDate(bankCoreHeader.getHostDate());
//            core351100InnerRsp.setErrorCode(rspHead.getRetCode());
//            core351100InnerRsp.setErrorMsg(rspHead.getRetInfo());
        } else {
            core351100InnerRsp.setErrorCode(PlatformError.COMM_ABEND.getErrorCode());
            core351100InnerRsp.setErrorMsg(PlatformError.COMM_ABEND.getErrorMsg());
        }
        return core351100InnerRsp;
    }

    /**
     * 返回错误赋值
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    private Map<String, Object> returnFail(String errorCode, String errorMsg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.CONSTANT_ERRORCODE, errorCode);
        resultMap.put(Constant.CONSTANT_ERRORMSG, errorMsg);
        return resultMap;
    }

    /**
     * @param creditEntity
     * @return
     */
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
            req.setAc2(StringUtils.isNotBlank(creditEntity.getRealPayeeAcct())?creditEntity.getRealPayeeAcct():"");
            req.setName2(StringUtils.isNotBlank(creditEntity.getRealPayeeName())?creditEntity.getRealPayeeName():"");
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

    /**
     * @param debitEntity
     * @return
     */
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
            req.setAc1(StringUtils.isNoneBlank(debitEntity.getRealPayerAcct())?debitEntity.getRealPayerAcct():"");
            req.setName1(StringUtils.isNoneBlank(debitEntity.getRealPayerName())?debitEntity.getRealPayerName():"");
            req.setAmt1(debitEntity.getAmount());
            req.setCcy1(debitEntity.getCurrency());
            req.setPrdmoCd1("");
            req.setEventCd1("");
            req.setDrAmtNo1("");
            req.setDrAmtVal1("");
            req.setRvsNo1(debitEntity.getSuspSerno());
            req.setMmo1(debitEntity.getSummary());
            req.setDrBusiType2(StringUtils.isNotBlank(debitEntity.getCusVouchChkInd())?debitEntity.getCusVouchChkInd(): "N");
                   // + StringUtils.isNotBlank(debitEntity.getCusVouchPwdInd())?debitEntity.getCusVouchPwdInd():"");
            req.setChqType(debitEntity.getCusVouchType());
            req.setChqNo(debitEntity.getCusVouchno());
            req.setChqIssueDt(debitEntity.getCusVouchDate());
            req.setChqPswd(debitEntity.getCusVouchPwd());
            req.setDrBusiType1(StringUtils.isNotBlank(debitEntity.getTransType())?debitEntity.getTransType(): "N");
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

    /**
     * 初始化请求报文信息
     *
     * @param reqCore351100
     * @return
     */
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

}
