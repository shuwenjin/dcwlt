package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;

public interface ICoreProcessService {
    /**
     * 初始化核心请求参数
     *
     * @param payTransDtlInfoDO
     * @return
     */
    BankCore351100InnerReq initBankCore351100InnerReq(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 回查核心后处理，不带状态机更新
     *
     * @param payTransDtlInfoDO
     * @param bankCore996666Rsp
     */
    void qryCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp, StateMachine stateMachine);

    /**
     * 上核心处理
     *
     * @param bankCore351100InnerReq
     * @return
     */
    BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq);

    /**
     * 回查核心后处理，不带状态机更新
     *
     * @param payTransDtlInfoDO
     * @param bankCore996666Rsp
     */
    void qryBankCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp, StateMachine stateMachine);

    /**
     * 差错贷记调账回查核心后处理，不带状态机更新
     *
     * @param payTransDtlInfoDO
     * @param bankCore996666Rsp
     */
    void dsptCoreStsRetDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore996666Rsp bankCore996666Rsp, StateMachine stateMachine);

    /**
     * 入账上核心前处理
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     * @param stateMachine
     */
    void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq, StateMachine stateMachine);

    /**
     * 入账上核心后更新登记簿和账户流水表
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     * @param stateMachine
     */
    void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp, StateMachine stateMachine);

    /**
     * 初始化扣款核心请求报文
     * @param payTransDtlInfoDO
     * @return
     */
    BankCore351100InnerReq sendCoreDebitInit(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 上核心扣款前处理
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     * @param stateMachine
     */
    void sendCoreDebitPre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq, StateMachine stateMachine);

    /**
     * 上核心补扣款后处理
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    void sendCoreDebitDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp);

    /**
     * 日累计交易金额处理
     *
     */
    void cacheLimitAmount(String key, String amount,boolean isAdd);
}
