package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;
import com.dcits.dcwlt.pay.online.flow.builder.PartyFactory;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.party.chng.FinCdChngNtfctnDTO;
import com.dcits.dcwlt.pay.online.mapper.PartyInfoMapper;
import com.dcits.dcwlt.pay.online.service.PartyChangeProcess;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 即时生效处理
 *
 * @author majun
 * @date 2020/12/30
 */
@Component("eF00FctvTpProcess")
public class EF00FctvTpProcess implements PartyChangeProcess {

//    @Autowired
//    private IPartyInfoRepository partyInfoRepository;

    @Autowired
    private PartyInfoMapper partyMapper;

    @Override
    public void doChange(Party party, TradeContext context)  {
        boolean needInsert = false;
        //报文获取
        DCEPReqDTO<FinCdChngNtfctnDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        FinCdChngNtfctnDTO finCdChngNtfctnDTO = reqMsg.getBody();

        FinCdChngNtfctn finCdChngNtfctn = finCdChngNtfctnDTO.getFinCdChngNtfctn();

        NbInf nbInf = finCdChngNtfctn.getNbInf();
        String changeCdEnum = party.getChngCtrl().getChngTp();
        PartyInfoDO partyInfoDO = PartyFactory.fromParty(party, nbInf);

        //变更类型处理，如果新增或变更，设置状态为生效中， 撤销操作则设置状态为已撤销
        if(ChangeCdEnum.CC02.getCode().equals(changeCdEnum)){
            //撤销状态处理， 设置机构生效状态为已撤销
            partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
        }else {
            //新增，变更处理， 设置机构生效状态为生效中
            partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
        }
        PartyInfoDO oldPartyInfo = partyMapper.queryParty(party.getPtyId());
        if(oldPartyInfo != null){
            //如果原来变更期数为99999999， 不进行比较，否则进行比较
            if(oldPartyInfo.getChangeNumber() >= NbInf.MAX_CHNG_NB){
                partyInfoDO.setChangeNumber(nbInf.getChngNb());
                partyInfoDO.setChangeCircleTimes(oldPartyInfo.getChangeCircleTimes() + 1);
            }else {
                //如果现有期数小于原有期数， 不允许操作
                if(oldPartyInfo.getChangeNumber() > nbInf.getChngNb()){
                    throw new EcnyTransException(EcnyTransError.ECNY_CHNG_NUM_ERROR);
                }
                partyInfoDO.setChangeNumber(nbInf.getChngNb());
                partyInfoDO.setChangeCircleTimes(oldPartyInfo.getChangeCircleTimes());
            }
        }else {
            partyInfoDO.setChangeNumber(nbInf.getChngNb());
            needInsert = true;
        }
        //更新入库
        if(needInsert){
            partyMapper.insertParty(partyInfoDO);
        }else {
            partyMapper.updateParty(partyInfoDO);
        }
    }
}
