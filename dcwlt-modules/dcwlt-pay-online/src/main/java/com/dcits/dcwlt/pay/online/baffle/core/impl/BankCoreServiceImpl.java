package com.dcits.dcwlt.pay.online.baffle.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqHeader;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqMessage;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreRspMessage;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Req;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.util.APPUtil;
import com.dcits.dcwlt.pay.online.baffle.core.IBankCoreService;
//import com.dcits.dcwlt.pay.online.service.CoreServiceSend;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCoreServiceImpl implements IBankCoreService {

//    @Autowired
//    private CoreServiceSend coreServiceSend;

    @Autowired
    DcepSendService dcepSendService;
    /**
     * 回查核心状态
     *
     * @param reqSysDate 原交易请求日期
     * @param reqSysJrn  原交易请求流水
     * @return
     */
    public BankCore996666Rsp queryCoreStatus(String reqSysDate, String reqSysJrn) {
        return queryCoreStatus("", "", reqSysDate, reqSysJrn);
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

//        // 获取服务化调用请求流水``
//        String seqNo = generateCodeService.generateCoreReqSerno();
//
//        //构造服务化报文头
//        // Head head = bankCoreDubboService.bulidServerHead(seqNo, Constant.SRVCCODE_996666, srcCnsmrSysId, srcCnsmrSysSeqNo);
//
//        // 构造核心请求报文头
//        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_QUERYSTATUS_CODE, Constant.MASTERBANK);

        // 构造核心请求报文体
        BankCore998889Req req = new BankCore998889Req ();
        req.setReqSysId(APPUtil.getAppId());
        req.setReqSysDate(reqSysDate);
        req.setReqSysJrn(reqSysJrn);

//        // 构造服务化请求报文
//        BankCoreReqMessage msg = bankCoreDubboServiceImpl.buildBankCoreMessage(coreHead, req, null);
//
//        // 请求核心服务
//        BankCoreRspMessage rsp = bankCoreDubboServiceImpl.bankCoreRequests(msg, BankCore998889Req.class, null);

        //通过调用核心服务测试挡板返回
        String trid="996666";
//        JSONObject RESULT=coreServiceSend.result(trid);
        JSONObject RESULT=dcepSendService.getNonce(trid);
        BankCore996666Rsp bankCore996666Rsp =JSONObject.parseObject(RESULT.toString(),BankCore996666Rsp.class);

        // 后续补充检查
//        BankCore996666Rsp bankCore996666Rsp = new BankCore996666Rsp();
//        bankCore996666Rsp.setHostJrnno(req.getReqSysJrn());
//        bankCore996666Rsp.setHostAcdate(req.getReqSysDate());
//        bankCore996666Rsp.setTxnSts("1");
//        bankCore996666Rsp.setCoreRetCode("000000");
//        bankCore996666Rsp.setCoreRetMsg("交易成功");
        return bankCore996666Rsp;
    }
}
