package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.PayCashboxPartyDO;

import java.util.List;

/**
 * 钱柜机构参数服务
 *
 * @author
 * @date 2020/12/30
 */
public interface IPayCashboxPartyService {

    int add(PayCashboxPartyDO payCashboxPartyDO);

    int update(PayCashboxPartyDO payCashboxPartyDO);

    List<PayCashboxPartyDO> query(PayCashboxPartyDO payCashboxPartyDO);

}
