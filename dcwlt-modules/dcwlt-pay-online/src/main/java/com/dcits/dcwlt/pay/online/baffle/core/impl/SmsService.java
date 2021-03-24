package com.dcits.dcwlt.pay.online.baffle.core.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsService {
    /**
     *
     * @param phoneNum    手机号码
     * @param tempCode    短信模板代码
     * @param smsId       短信业务代码
     * @param channelCode 请求渠道号
     * @param sendBranch  发送行所
     * @param msgMap      模板参数
     * @param isSync      true为同步发送，false为异步发送
     * @return            true 发送成功。false 发送失败
     */
    public boolean sendMsg(String phoneNum, String tempCode, String smsId, String channelCode, String sendBranch, Map<String, String> msgMap, boolean isSync) {

        return true;
        //todo
//        return SmsUtils.sendMsg(phoneNum,tempCode,smsId,channelCode,sendBranch,msgMap,isSync);
    }


    /**
     * 验证短信验证码有效性
     * @param authMsg       验证码
     * @param oriEncAuthMsg 原交易验证码（加密）
     * @param msgSendTime   验证码发送时间
     */
    public void verifyAuthMsg(String authMsg, String oriEncAuthMsg, String msgSendTime) {
//        //判断短信验证码是否一致
//        SmsFactory factory = SmsFactory.getInstance(Constant.SM3);
//        if (!factory.verifySmsCode(authMsg, oriEncAuthMsg)) {
//            logger.error("短信验证码不符！");
//            throw new EcnyTransException(EcnyTransError.AUTH_MSG_ERROR);
//        }
//
//        String authMsgCtive = RtpUtil.getInstance().getProperty("sms.authMsg.timeOut", "120");//短信超时时间
//        //校验验证码是否过期
//        if (!factory.verifySmsCodeTime(msgSendTime, DateTimeUtil.getCurrentDateTimeStr(), Integer.parseInt(authMsgCtive))) {
//            logger.error("短信验证码已过期！");
//            throw new EcnyTransException(EcnyTransError.AUTH_MSG_TIMEOUT);
//        }
//
//    }
        return;

    }
}
