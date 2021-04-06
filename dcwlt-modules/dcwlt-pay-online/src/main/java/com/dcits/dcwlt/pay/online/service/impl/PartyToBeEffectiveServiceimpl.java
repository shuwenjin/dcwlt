package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.service.IPartyToBeEffectiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时机构信息数据库处理组件
 *
 * @author
 * @date 2020/12/30
 */
@Service
public class PartyToBeEffectiveServiceimpl implements IPartyToBeEffectiveService {
    private static final Logger logger = LoggerFactory.getLogger(PartyToBeEffectiveServiceimpl.class);

    private static final String INSERT_URL = "party.insertPartyTmp";
    private static final String UPDATE_URL = "party.updatePartyTmp";
    private static final String QUERY_URL = "party.queryPartyTmp";
    private static final String DELETE_URL = "party.deletePartyTmp";
    private static final String QUERY_EFFECIVE_URL = "party.selectPartyTmpOfEffectiveToDay";

    /**
     * 添加临时机构数据
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    @Override
    public int addPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO) {
//        partyToBeEffectiveDO.setLastUpTime(DateUtil.getDefaultTime());
//        partyToBeEffectiveDO.setLastUpDate(DateUtil.getDefaultDate());
        return insertPartyTmpInfo(partyToBeEffectiveDO);
    }

    /**
     * 添加或更新临时机构数据
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    @Override
    public int addOrUpdatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        PartyToBeEffectiveDO oldParty = queryPartyTmpByPartyId(partyToBeEffectiveDO.getPartyID());

        if (oldParty != null) {
            return updatePartyTmpInfo(partyToBeEffectiveDO);
        } else {
            return addPartyTmp(partyToBeEffectiveDO);
        }
    }

    /**
     * 是否存在更新的期数
     *
     * @param oldParty
     * @param newParty
     * @return
     */
    private boolean timeLimit(PartyToBeEffectiveDO oldParty, PartyToBeEffectiveDO newParty) {
        if (oldParty == null) {
            return false;
        }
        //判断变更期数是否小于当前已存在期数
        if (newParty.getChangeCircleTimes() != Long.MAX_VALUE
                && newParty.getChangeNumber() < oldParty.getChangeNumber()) {
            return true;
        }
        return false;
    }

    /**
     * 判断临时机构是否存在
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    @Override
    public boolean partyTmpExist(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        List<PartyToBeEffectiveDO> partyToBeEffectiveDOS = queryPartyInfoTmp(partyToBeEffectiveDO);
        if (partyToBeEffectiveDOS == null || partyToBeEffectiveDOS.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 更新临时机构表
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    @Override
    public int updatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        return updatePartyTmpInfo(partyToBeEffectiveDO);
    }

    /**
     * 查询临时机构
     */
    @Override
    public List<PartyToBeEffectiveDO> queryPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        return queryPartyInfoTmp(partyToBeEffectiveDO);
    }

    /**
     * 通过机构id查询临时机构表数据
     *
     * @param partyId
     * @return
     */
    @Override
    public PartyToBeEffectiveDO queryPartyTmpByPartyId(String partyId) {
        PartyToBeEffectiveDO partyToBeEffectiveDO = new PartyToBeEffectiveDO();
        partyToBeEffectiveDO.setPartyID(partyId);

        List<PartyToBeEffectiveDO> partyToBeEffectiveDOS = queryPartyInfoTmp(partyToBeEffectiveDO);
        if (partyToBeEffectiveDOS != null && partyToBeEffectiveDOS.size() > 0) {
            return partyToBeEffectiveDOS.get(0);
        }
        return null;
    }

    @Override
    public int deletePartyTemp(String partyId) {
        return deleteByPartyId(partyId);
    }

    /**
     * 查询指定日期生效的临时记录数据
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    public List<PartyToBeEffectiveDO> queryPartyTmpByEffectiveDate(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        return selectPartyTmpOfEffectiveToDay(partyToBeEffectiveDO);
    }

    /**
     * 机构临时实体入库
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    private int insertPartyTmpInfo(PartyToBeEffectiveDO partyToBeEffectiveDO) {
//        return DBUtil.insert(INSERT_URL, partyToBeEffectiveDO);
        return 1;
    }

    /**
     * 更新临时机构入库
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    private int updatePartyTmpInfo(PartyToBeEffectiveDO partyToBeEffectiveDO) {
//        partyToBeEffectiveDO.setLastUpTime(DateUtil.getDefaultTime());
//        partyToBeEffectiveDO.setLastUpDate(DateUtil.getDefaultDate());
//        return DBUtil.update(UPDATE_URL, partyToBeEffectiveDO);
        return 1;
    }

    /**
     * 临时机构数码获取
     *
     * @param partyToBeEffectiveDO
     * @return
     */
    private List<PartyToBeEffectiveDO> queryPartyInfoTmp(PartyToBeEffectiveDO partyToBeEffectiveDO) {
//        return DBUtil.selectList(QUERY_URL, partyToBeEffectiveDO);
        return new ArrayList<>();
    }

    private int deleteByPartyId(String partyId) {
//        return DBUtil.delete(DELETE_URL, partyId);
        return 1;
    }

    private List<PartyToBeEffectiveDO> selectPartyTmpOfEffectiveToDay(PartyToBeEffectiveDO partyToBeEffectiveDO) {
//        return DBUtil.selectList(QUERY_EFFECIVE_URL, partyToBeEffectiveDO);
        return new ArrayList<>();
    }
}
