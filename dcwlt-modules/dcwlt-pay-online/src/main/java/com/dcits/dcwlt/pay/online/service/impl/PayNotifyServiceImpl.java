package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.PayNotifyDO;
import com.dcits.dcwlt.pay.online.mapper.PayNotifyMapper;
import com.dcits.dcwlt.pay.online.service.IPayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 终态通知请求登记表持久层入口
 */
@Repository
public class PayNotifyServiceImpl implements IPayNotifyService {

    @Autowired
    private PayNotifyMapper payNotifyMapper;

    private static final String INSERT_PAY_NOTIFY_SQL = "payNotifyMapper.insert";
    private static final String QUERY_PAY_NOTIFY_BY_MSGID_SQL = "payNotifyMapper.queryByMsgId";

    @Override
    public int insert(PayNotifyDO payNotifyDO) {
        payNotifyDO.setLastUpDate(DateUtil.getDefaultDate());
        payNotifyDO.setLastUpTime(DateUtil.getDefaultTime());
        payNotifyDO.setLastMicroSecond(DateUtil.formatMilliTime());
        return payNotifyMapper.insert(INSERT_PAY_NOTIFY_SQL, payNotifyDO);
    }

    @Override
    public PayNotifyDO queryByMsgId(String msgId) {
        return payNotifyMapper.queryByMsgId(QUERY_PAY_NOTIFY_BY_MSGID_SQL, msgId);
    }
}
