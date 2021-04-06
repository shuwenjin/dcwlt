package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.PayNotifyDO;

/**
 * 终态通知请求登记表
 * @author
 */
public interface IPayNotifyService {

    /**
     * 插入新数据
     * @param payNotifyDO
     * @return
     */
    int insert(PayNotifyDO payNotifyDO);

    /**`
     * 根据报文标识号查询终态通知请求登记表
     * @param msgId
     * @return
     */
    public PayNotifyDO queryByMsgId(String msgId);
}
