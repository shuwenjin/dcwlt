package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.pay.online.bankcore351100.BankCore351100InnerRsp;

public interface ICoreProcessService {
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


}
