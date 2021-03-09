//package com.dcits.dcwlt.pay.online.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dcits.dcwlt.common.pay.constant.AppConstant;
//import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
//import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @author zhanguohai
// * @Time 2021/1/3 17:18
// * @Version 1.0
// * Description:主动请求互联互通服务组件
// */
//@Service
//public class DcepSendService {
//    private static final Logger logger = LoggerFactory.getLogger(DcepSendService.class);
//
//
//    @Autowired
//    private TransInService transInService;
//
//    /**
//     * 请求互联互通
//     *
//     * @param reqDTO
//     * @param <T>
//     * @return
//     */
//    public <T extends DCEPReqBody> JSONObject sendDcep(DCEPReqDTO<T> reqDTO) {
//        logger.info("请求报文：{}" + reqDTO.toString());
//
//        //1、交易记录
//        if (null != reqDTO.getDcepHead()) {
//            transInService.saveMsg(reqDTO.getDcepHead(), AppConstant.DIRECT_SEND);
//        }
//
////        //2、调用金融开发平台服务化接口，请求互联互通
////        String srvcCode = reqDTO.getHead().getSrvcCode();
////        String rspMsg = RpcHttpJsonUtil.executeExt(srvcCode, JsonUtil.toJSONString(reqDTO));
////
////        //3、响应
//        return null;
//    }
//
//}
