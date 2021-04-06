package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.PartyTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.util.DateCompareUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn.StsInf;
import com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn.TrblNtfctnDTO;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.online.mapper.PartyInfoMapper;
import com.dcits.dcwlt.pay.online.service.INotificationProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构状态变更逻辑实现
 *
 * @author
 * @date 2020/12/31
 */
@Service
public class NotificationProcess implements INotificationProcess {

    @Autowired
    private PartyInfoMapper partyInfoMapper;

    @Override
    public void doProcess(TrblNtfctnDTO trblNtfctnDTO, TradeContext context) {
        boolean needInsert = false;
        //拿到变更数据
        StsInf stsInf = trblNtfctnDTO.getTrblNtfctn().getStsInf();

        //拿到机构数据
        PartyInfoDO partyInfoDO = partyInfoMapper.queryParty(stsInf.getPtyId());

        //如果机构在库中不存在，已现有的数据为基础，将机构入库
        if(partyInfoDO == null){//空处理
            partyInfoDO = new PartyInfoDO();
            partyInfoDO.setPartyID(stsInf.getPtyId());
            needInsert = true;
        }
        //设置机构为已生效状态
        partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
        partyInfoDO.setPartyType(PartyTpCdEnum.valueOf(stsInf.getPtyTp()));
        partyInfoDO.setPartyStatus(StatusTpCdEnum.valueOf(stsInf.getTp()));

        //防止原有机构已经失效，更改为生效状态不生效
        String inEffectiveDate = partyInfoDO.getInEffectiveDate();
        if(inEffectiveDate != null && DateCompareUtil.nowGTEDateTime(inEffectiveDate)){
            partyInfoDO.setInEffectiveDate(null);
        }

        //更新数据库
        if(needInsert){
            partyInfoMapper.insertParty(partyInfoDO);
        }else {

            partyInfoMapper.updateParty(partyInfoDO);
        }
    }
}
