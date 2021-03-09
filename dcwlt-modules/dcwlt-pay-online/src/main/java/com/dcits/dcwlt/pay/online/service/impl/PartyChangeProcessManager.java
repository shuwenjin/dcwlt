package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.common.pay.enums.EffectiveCdEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;
import com.dcits.dcwlt.pay.online.service.PartyChangeProcess;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 生效类型处理管理类
 *
 * @author majun
 * @date 2020/12/30
 */
@Service("effectiveCdProcessManager")
public class PartyChangeProcessManager implements PartyChangeProcess {
    private static final Logger logger = LoggerFactory.getLogger(PartyChangeProcessManager.class);

    @Resource(name = "eF00FctvTpProcess")
    @Autowired
    private PartyChangeProcess ef00;

    @Resource(name = "eF01FctvTpProcess")
    @Autowired
    private PartyChangeProcess ef01;

    @Override
    public void doChange(Party party, TradeContext context) {
        //  即时生效（EF00）
        //  定时生效（EF01）
        if (EffectiveCdEnum.EF00.getCode().equals(party.getChngCtrl().getFctvTp())) {
            ef00.doChange(party, context);
        } else if (EffectiveCdEnum.EF01.getCode().equals(party.getChngCtrl().getFctvTp())) {
            ef01.doChange(party, context);
        } else {
            logger.info("生效类型参数非法，当前{}参数不支持", party.getChngCtrl().getChngTp());
            throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
        }
    }
}
