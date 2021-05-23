package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.PayCashboxPartyDO;
import com.dcits.dcwlt.pay.online.mapper.PayCashboxPartyMapper;
import com.dcits.dcwlt.pay.online.service.IPayCashboxPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱柜机构参数服务
 *
 * @author
 * @date 2021/04/28
 */
@Service
public class PayCashboxPartyImpl implements IPayCashboxPartyService {
    private static final Logger logger = LoggerFactory.getLogger(PayCashboxPartyImpl.class);

    @Autowired
    private PayCashboxPartyMapper payCashboxPartyMapper;


    /**
     * 添加钱柜机构参数
     *
     * @param payCashboxPartyDO
     * @return
     */
    @Override
    public int add(PayCashboxPartyDO payCashboxPartyDO) {
        payCashboxPartyDO.setLastUpTime(DateUtil.getDefaultTime());
        payCashboxPartyDO.setLastUpDate(DateUtil.getDefaultDate());
        return payCashboxPartyMapper.insert(payCashboxPartyDO);
    }

    /**
     * 更新钱柜机构参数
     *
     * @param payCashboxPartyDO
     * @return
     */
    @Override
    public int update(PayCashboxPartyDO payCashboxPartyDO) {
        payCashboxPartyDO.setLastUpTime(DateUtil.getDefaultTime());
        payCashboxPartyDO.setLastUpDate(DateUtil.getDefaultDate());
        return payCashboxPartyMapper.update(payCashboxPartyDO);
    }

    /**
     * 查询钱柜机构参数
     *
     * @param payCashboxPartyDO
     * @return
     */
    @Override
    public List<PayCashboxPartyDO> query(PayCashboxPartyDO payCashboxPartyDO) {
        return payCashboxPartyMapper.query(payCashboxPartyDO);
    }
}
