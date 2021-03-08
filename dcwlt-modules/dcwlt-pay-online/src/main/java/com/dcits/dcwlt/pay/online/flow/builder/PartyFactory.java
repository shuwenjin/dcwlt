package com.dcits.dcwlt.pay.online.flow.builder;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.EffectiveCdEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.chngctrl.ChngCtrl;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 机构报文转换工厂
 *
 * @author majun
 * @date 2020/12/30
 */
public final class PartyFactory {
    private static final Logger logger = LoggerFactory.getLogger(PartyFactory.class);
    private PartyFactory() {
    }

    /**
     * 从机构信息实体中获取机构报文对象
     *
     * @param party
     * @return
     */
    public static PartyToBeEffectiveDO fromParty(Party party, NbInf nbInf) {

        PartyToBeEffectiveDO partyToBeEffectiveDO = new PartyToBeEffectiveDO();
        partyToBeEffectiveDO.setPartyID(party.getPtyId());
        partyToBeEffectiveDO.setPartyType(party.getPtyTp());
        partyToBeEffectiveDO.setPartyName(party.getPtyNm());
        partyToBeEffectiveDO.setPartyAlias(party.getPtyAli());
        partyToBeEffectiveDO.setPartyStatus(party.getPtySts());
        partyToBeEffectiveDO.setContact(party.getContact());
        partyToBeEffectiveDO.setTelephone(party.getTel());
        partyToBeEffectiveDO.setMail(party.getMail());
        partyToBeEffectiveDO.setFax(party.getFax());
//        partyToBeEffectiveDO.setChangeNumber(nbInf.getChngNb() % NbInf.MAX_CHNG_NB);
//        //设置变更循环标识
//        if (nbInf.getChngNb() >= NbInf.MAX_CHNG_NB) {
//            partyToBeEffectiveDO.setChangeCircleTimes(Long.MAX_VALUE);
//        }
        //设置生失效日期
        ChngCtrl chngCtrl = party.getChngCtrl();
        try {
            if (chngCtrl.getFctvDt() != null) {
                partyToBeEffectiveDO.setEffectDate(DateUtil.formatISODateTimeToDate(chngCtrl.getFctvDt()));
            }
            if (chngCtrl.getIfctvDt() != null) {
                partyToBeEffectiveDO.setInEffectiveDate(DateUtil.formatISODateTimeToDate(chngCtrl.getIfctvDt()));
            }
        }catch (Exception e) {
            logger.warn("机构报文中日期格式不对，报文要求传入ISODateTime格式数据", e);
            throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
        }

        //设置生效状态
        if (chngCtrl.getFctvTp() == EffectiveCdEnum.EF00.getCode()) {
            //即时生效，设置为已生效， 如果当前变更类型为撤销， 那么由具体的业务逻辑控制
            partyToBeEffectiveDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
        } else {
            //设置为已撤销状态，等待定时任务将临时表转到生效表时调整状态
            partyToBeEffectiveDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
        }

        partyToBeEffectiveDO.setChangeType(chngCtrl.getChngTp());
        partyToBeEffectiveDO.setEffectiveType(chngCtrl.getFctvTp());
        //更新时间设置
        partyToBeEffectiveDO.setLastUpDate(DateUtil.getDefaultDate());
        partyToBeEffectiveDO.setLastUpTime(DateUtil.getDefaultTime());

        return partyToBeEffectiveDO;
    }
}
