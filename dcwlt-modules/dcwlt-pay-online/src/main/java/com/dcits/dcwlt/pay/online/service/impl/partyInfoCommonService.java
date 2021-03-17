package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.common.pay.util.DateCompareUtil;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import com.dcits.dcwlt.pay.online.mapper.PartyInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class partyInfoCommonService {
    private static final Logger logger = LoggerFactory.getLogger(DcepTransInTradeFlow.class);

    @Autowired
    private PartyInfoMapper partyInfoMapper;

    public boolean sendReceiveAble(String partyId) {
        PartyInfoDO partyInfoDO = getPartyInfo(partyId);
        return sendReceiveAble(partyInfoDO);
    }

    public PartyInfoDO getPartyInfo(String partyId) {
        PartyInfoDO partyInfoDO = partyInfoMapper.queryParty(partyId);
        if (partyInfoDO == null
                || AppConstant.EFFECTIVE_STATUS_REVOKE.equals(partyInfoDO.getStatus())) {
            return null;
        }
        return partyInfoDO;
    }


    public boolean sendReceiveAble(PartyInfoDO partyInfoDO) {
        return isAvailable(partyInfoDO) && (StatusTpCdEnum.ST02.equals(partyInfoDO.getPartyStatus()) ||
                StatusTpCdEnum.ST01.equals(partyInfoDO.getPartyStatus()));
    }

    public boolean isAvailable(PartyInfoDO partyInfoDO) {
        if (partyInfoDO == null) {
            logger.info("机构信息为空，不可用");
            return false;
        } else {
            if (partyInfoDO.getPartyStatus() == null) {
                logger.info("机构状态为空，不可用");
                return false;
            } else {
                if (!StatusTpCdEnum.ST02.equals(partyInfoDO.getPartyStatus())
                        && !StatusTpCdEnum.ST01.equals(partyInfoDO.getPartyStatus())) {
                    logger.info("机构id:{}机构处于{}状态，只有{}/{}状态可用",
                            partyInfoDO.getPartyID(), partyInfoDO.getPartyStatus(),
                            StatusTpCdEnum.ST02.getCode(), StatusTpCdEnum.ST01.getCode());
                    return false;
                } else {
                    //如果已经撤销， 标识不可用
                    if (AppConstant.EFFECTIVE_STATUS_REVOKE.equals(partyInfoDO.getStatus())) {
                        logger.info("机构已经撤销，不可用");
                        return false;
                    }

                    //状态为已生效， 判断是否在生效期
                    return DateCompareUtil.atTimeFrame(partyInfoDO.getEffectDate(), partyInfoDO.getInEffectiveDate());
                }
            }
        }
    }
    public boolean isAvailable(String partyId) {
        PartyInfoDO partyInfoDO = partyInfoMapper.queryParty(partyId);
        if (partyInfoDO != null) {
            return isAvailable(partyInfoDO);
        }
        return false;
    }
}
