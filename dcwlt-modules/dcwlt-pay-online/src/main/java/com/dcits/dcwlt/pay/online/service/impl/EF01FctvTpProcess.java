package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;
import com.dcits.dcwlt.pay.online.flow.builder.PartyFactory;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctnDTO;
import com.dcits.dcwlt.pay.online.mapper.PartyInfoOTMapper;
import com.dcits.dcwlt.pay.online.service.PartyChangeProcess;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * 定时生效处理
 *
 * @author majun
 * @date 2020/12/30
 */
@Component("eF01FctvTpProcess")
public class EF01FctvTpProcess implements PartyChangeProcess {

//    @Autowired
//    private IPartyToBeEffectiveRepository partyInfoTmpRepository;

    @Autowired
    private PartyInfoOTMapper partyInfoOTMapper;

    /**
     * 执行定时生效变更逻辑
     * @param party
     * @param context
     * @throws ParseException
     */
    @Override
    public void doChange(Party party, TradeContext context) {
        boolean needInsert = false;

        //拿到变更报文信息
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FinCdChngNtfctnDTO finCdChngNtfctnDTO = reqMsg.getBody();
        FinCdChngNtfctn finCdChngNtfctn = finCdChngNtfctnDTO.getFinCdChngNtfctn();

        NbInf nbInf = finCdChngNtfctn.getNbInf();
        //将网络机构变更实体对象转为数据库机构变更存储实体
        PartyToBeEffectiveDO partyToBeEffectiveDO = PartyFactory.fromParty(party, nbInf);
        //定时生效，设置生效状态为已撤销
        partyToBeEffectiveDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
        PartyToBeEffectiveDO partyToBeEffectiveDOo = new PartyToBeEffectiveDO();
        partyToBeEffectiveDOo.setPartyID(party.getPtyId());
        List<PartyToBeEffectiveDO> oldPartyInfoList = partyInfoOTMapper.queryPartyTmp(partyToBeEffectiveDOo);
        PartyToBeEffectiveDO oldPartyInfo = oldPartyInfoList.get(0);
        if(oldPartyInfo != null){
            //如果原来变更期数为99999999， 不进行比较，否则进行比较
            if(oldPartyInfo.getChangeNumber() >= NbInf.MAX_CHNG_NB){
                partyToBeEffectiveDO.setChangeNumber(nbInf.getChngNb());
                partyToBeEffectiveDO.setChangeCircleTimes(oldPartyInfo.getChangeCircleTimes() + 1);
            }else {
                //如果现有期数小于原有期数， 不允许操作
                if(oldPartyInfo.getChangeNumber() > nbInf.getChngNb()){
                    throw new EcnyTransException(EcnyTransError.ECNY_CHNG_NUM_ERROR);
                }
                partyToBeEffectiveDO.setChangeNumber(nbInf.getChngNb());
                partyToBeEffectiveDO.setChangeCircleTimes(oldPartyInfo.getChangeCircleTimes());
            }
        }else {
            partyToBeEffectiveDO.setChangeNumber(nbInf.getChngNb());
            needInsert = true;
        }

        if(needInsert){
            partyInfoOTMapper.insertPartyTmp(partyToBeEffectiveDO);
        }else {
            partyInfoOTMapper.updatePartyTmp(partyToBeEffectiveDO);
        }
    }
}
