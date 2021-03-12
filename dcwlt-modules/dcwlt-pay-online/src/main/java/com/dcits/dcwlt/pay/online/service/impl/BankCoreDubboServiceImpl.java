package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.*;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040ArrayInfoRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Req;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Req;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhanguohai
 * @Time 2020年3月30日下午4:24:19
 * @Version <p> Description: 核心通讯服务 </p>
 */
@Service
public class BankCoreDubboServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(BankCoreDubboServiceImpl.class);

    private static final String CORE_HEAD_TAG = "coreHead";

    private static final String CORE_BODY_TAG = "coreBodyInfo";

    private static final String CORE_BODY_ARRAY_TAG = "coreBodyArrayInfo";

    @Value("${rtp.crpm.connectTimeOut:3000}")
    private String connectTimeOut;

    @Value("${rtp.crpm.readTimeOut:3000}")
    private String readTimeOut;

    /**
     * 构造核心请求报文头
     *
     * @param tradeCode
     * @param brno
     * @return
     */
    public BankCoreReqHeader buildDefaultBankCoreHeader(String tradeCode, String brno) {
        BankCoreReqHeader coreHeader = new BankCoreReqHeader();
        coreHeader.setReqType("T");
        coreHeader.setTrId(tradeCode);
        coreHeader.setReqChnl(Constant.REQ_CHNL);
        coreHeader.setReqChnlTate(DateUtil.getDefaultDate());
        coreHeader.setReqChnlTime(DateUtil.getDefaultTime());
        coreHeader.setTermId(Constant.REQ_TERMID + tradeCode);            //渠道系统终端号
        coreHeader.setTrBank(Constant.REQ_TR_BANK);                       //交易银行号
        coreHeader.setTrBranch(brno);                                     //交易机构
        coreHeader.setTlId(Constant.REQ_TL_ID);                           //柜员
        //coreHeader.setMercId(RtpUtil.getInstance().getProperty("app.mercId", ""));
        return coreHeader;
    }


    /**
     * 构造核心请求实体
     *
     * @param codeHeadInfo 核心报文头
     * @param body         核心请求体
     * @param arrayBody    核心请求报文循环数组
     * @return
     */
    public <T extends IBankCoreBody, A extends IBankCoreBodyArrayInfo> BankCoreReqMessage<T, A> buildBankCoreMessage(BankCoreReqHeader codeHeadInfo, BankCore998889Req body, List<A> arrayBody) {

        return (BankCoreReqMessage<T, A>) BankCoreReqMessage.newInstance(codeHeadInfo, body, arrayBody);
    }


    /**
     * 构建服务化报文头
     *
     * @param seqNo            服务化请求流水
     * @param srvccode         服务码
     * @param srcCnsmrSysId    源交易请求系统标识
     * @param srcCnsmrSysSeqNo 源交易请求系统流水
     * @return
     */
//    public Head bulidServerHead(String seqNo, String srvccode, String srcCnsmrSysId, String srcCnsmrSysSeqNo) {
//        Head head = HeadUtil.newHead(srvccode, Constant.CORE_SYS_ID, seqNo);
//
//        //源交易请求系统流水为空则上送本系统请求流水
//        if (StringUtil.isBlank(srcCnsmrSysId)) {
//            srcCnsmrSysId = APPUtil.getAppId();
//        }
//        head.setSrcCnsmrSysId(srcCnsmrSysId);
//
//        //源交易请求系统流水为空则上送本系统请求流水
//        if (StringUtil.isBlank(srcCnsmrSysSeqNo)) {
//            srcCnsmrSysSeqNo = seqNo;
//        }
//        head.setSrcCnsmrSysSeqNo(srcCnsmrSysSeqNo);
//        return head;
//    }

    /**
     * @param msg        请求报文实体对象
     * @param clazz      返回实体对象类型
     * @param arrayClazz 返回实体数组对象类型
     * @return
     * @Description: 服务化调用请求核心
     */
    //public <T extends IBankCoreBody, A extends IBankCoreBodyArrayInfo> BankCoreRspMessage<T, A> bankCoreRequest(BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> msg, Class<BankCore358040Rsp> clazz, Class<BankCore358040ArrayInfoRsp> arrayClazz) {

        //获取服务化报文头
//        String srvcCode = msg.getHead().getSrvcCode();
//        logger.info("核心接口[{}], 请求报文：{}", srvcCode, msg);

        //设置通讯超时时间，
//        HttpEntity[] httpEntities = new HttpEntity[]{
//                new HttpEntity(readTimeOut),
//                new HttpEntity(connectTimeOut, DynamicEntity.DType.cTimeout)
//        };

        //服务化调用
       // String retStr = RpcHttpJsonUtil.executeExt(srvcCode, JsonUtil.toJSONString(msg), httpEntities);

        //获取返回json报文
        //JSONObject retObj = JSONObject.parseObject(retStr);

        // 构造返回实体
//        BankCoreRspMessage rsp = jsonToBankCoreMessage( null,clazz, arrayClazz);
//        logger.info("核心接口[{}], 响应报文:{}",  rsp);
//        return rsp;
//    }

    /**
     * @param retObj 返回消息体
     * @param clazz  返回实体类对象类型
     * @param clazz  返回数组实体类对象类型
     * @return
     * @throws DocumentException
     * @Description:构造返回实体类对象
     */
    private <T extends IBankCoreBody, A extends IBankCoreBodyArrayInfo> BankCoreRspMessage<T, A> jsonToBankCoreMessage(
            JSONObject retObj, Class<T> clazz, Class<A> arrayClazz) {
        // 获取服务网关头，报文体 json-->String
        JSONObject headObj = retObj.getJSONObject(Constant.HEAD_TAG);
       // Head head = JSONObject.parseObject(headObj.toJSONString(), Head.class);

        //获取核心响应报文头
        JSONObject coreHeadObj = retObj.getJSONObject(CORE_HEAD_TAG);
        BankCoreRspHeader coreHead = null;
        if (null != coreHeadObj) {
            coreHead = JSONObject.parseObject(coreHeadObj.toJSONString(), BankCoreRspHeader.class);
        }

        //核心返回报文体对象
        JSONObject body = retObj.getJSONObject(CORE_BODY_TAG);
        T codeBodyInfo = null;
        if (null != body) {
            codeBodyInfo = JSONObject.parseObject(body.toJSONString(), clazz);
        }

        //核心返回报文体数组对象
        List<A> codeBodyArrayInfo = null;
        JSONArray arrayBody = retObj.getJSONArray(CORE_BODY_ARRAY_TAG);
        if (null != arrayBody) {
            codeBodyArrayInfo = jsonArrayToJavaObject(arrayBody, arrayClazz);
        }

        // 构造BankCoreMessage实体对象
        return BankCoreRspMessage.newInstance( coreHead, codeBodyInfo, codeBodyArrayInfo);
    }

    /**
     * json数组转java对象
     *
     * @param array
     * @param arrayClazz
     * @return
     */
    public <A extends IBankCoreBodyArrayInfo> List<A> jsonArrayToJavaObject(JSONArray array, Class<A> arrayClazz) {
        List<A> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(JSONObject.parseObject(array.getString(i), arrayClazz));
        }
        return list;

    }


    public BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> bankCoreRequest(BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> msg, Class<BankCore358040Rsp> bankCore358040RspClass, Class<BankCore358040ArrayInfoRsp> bankCore358040ArrayInfoRspClass) {
        return msg;
    }

    public BankCoreReqMessage<IBankCoreBody, IBankCoreBodyArrayInfo> buildBankCoreMessages(BankCoreReqHeader coreHead, BankCore358040Req req, Object o) {
        return buildBankCoreMessages(coreHead,req,null);
    }

    public BankCoreRspMessage bankCoreRequests(BankCoreReqMessage msg, Class<BankCore998889Req> bankCore998889ReqClass, Object o) {
        return bankCoreRequests(msg,bankCore998889ReqClass,o);
    }
}
