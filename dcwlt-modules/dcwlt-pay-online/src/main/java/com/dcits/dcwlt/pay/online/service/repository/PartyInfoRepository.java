package com.dcits.dcwlt.pay.online.service.repository;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.online.mapper.PartyMapper;
import com.dcits.dcwlt.pay.online.service.IPartyInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构信息数据库处理组件
 *
 * @author majun
 * @date 2020/12/30
 */
@Service
public class PartyInfoRepository implements IPartyInfoRepository {
    private static final Logger logger = LoggerFactory.getLogger(PartyInfoRepository.class);

    /**
     * 添加机构信息
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public int addParty(PartyInfoDO partyInfoDO) {
//        partyInfoDO.setLastUpTime(DateUtil.getDefaultTime());
//        partyInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        return insertPartyInfo(partyInfoDO);
    }

    /**
     * 添加或更新
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public int addOrUpdateParty(PartyInfoDO partyInfoDO) {
        PartyInfoDO oldParty = queryPartyInfoByPartyId(partyInfoDO.getPartyID());

//        partyInfoDO.setLastUpTime(DateUtil.getDefaultTime());
//        partyInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        if (oldParty != null) {
            return updateParty(partyInfoDO);
        } else {
            return addParty(partyInfoDO);
        }
    }

    /**
     * 判断机构是否存在
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public boolean partyExist(PartyInfoDO partyInfoDO) {
        List<PartyInfoDO> partyInfoDOS = queryPartyInfo(partyInfoDO);
        return partyInfoDOS != null && !partyInfoDOS.isEmpty();
    }

    /**
     * 更新机构信息
     *
     * @param partyInfoDO
     * @return
     */
    public int updateParty(PartyInfoDO partyInfoDO) {
//        partyInfoDO.setLastUpTime(DateUtil.getDefaultTime());
//        partyInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        return updatePartyInfo(partyInfoDO);
    }

    /**
     * 查询机构信息
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public List<PartyInfoDO> queryParty(PartyInfoDO partyInfoDO) {
        return queryPartyInfo(partyInfoDO);
    }

    /**
     * 机构是否可用
     * 一般正常情况下，机构可以接受和发送报， 否则机构会拒绝报文
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public boolean isAvailable(PartyInfoDO partyInfoDO) {
        if (partyInfoDO == null) {
            logger.info("机构信息为空，不可用");
            return false;
        } else {
            if (partyInfoDO.getPartyStatus() == null) {
                logger.info("机构状态为空，不可用");
                return false;
            /*} else {
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
                }*/
            }
            return false;
        }
    }

    /**
     * 机构是否可用
     * 一般正常情况下，机构可以接受和发送报， 否则机构会拒绝报文
     *
     * @param partyId
     * @return
     */
    @Override
    public boolean isAvailable(String partyId) {
        PartyInfoDO partyInfoDO = queryPartyInfoByPartyId(partyId);
        if (partyInfoDO != null) {
            return isAvailable(partyInfoDO);
        }
        //todo
        //先改为true，后面逻辑没问题再改为false
//        return false;
        return true;
    }

    /**
     * 获取机构信息
     *
     * @param partyId
     * @return
     */
    @Override
    public PartyInfoDO getPartyInfo(String partyId) {
        PartyInfoDO partyInfoDO = queryPartyInfoByPartyId(partyId);
        if (partyInfoDO == null
                || AppConstant.EFFECTIVE_STATUS_REVOKE.equals(partyInfoDO.getStatus())) {
            return null;
        }
        return partyInfoDO;
    }

    /**
     * 根据机构指定信息查询信息
     * 查询条件可以有： 机构类型
     * 机构状态
     * 生效状态
     * 生效日期
     * 失效日期
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public List<PartyInfoDO> getEffectivePartyByCondition(PartyInfoDO partyInfoDO) {
        return queryEffectivePartyByCondition(partyInfoDO);
    }


    private List<PartyInfoDO> queryEffectivePartyByCondition(PartyInfoDO partyInfoDO) {
//        return DBUtil.selectList(QUERY_EFFECTIVE_URL, partyInfoDO);
        return new ArrayList<>();
    }

    /**
     * 判断机构是否可以发送或接收报文
     *
     * @param partyId
     * @return
     */
    @Override
    public boolean sendReceiveAble(String partyId) {
        PartyInfoDO partyInfoDO = getPartyInfo(partyId);
        return sendReceiveAble(partyInfoDO);
    }

    /**
     * 判断机构是否可以发送或接收报文
     *
     * @param partyInfoDO
     * @return
     */
    @Override
    public boolean sendReceiveAble(PartyInfoDO partyInfoDO) {
        return isAvailable(partyInfoDO) && (StatusTpCdEnum.ST02.equals(partyInfoDO.getPartyStatus()) ||
                StatusTpCdEnum.ST01.equals(partyInfoDO.getPartyStatus()));
    }

    /**
     * 插入机构信，对库操作
     *
     * @param partyInfoDO
     * @return
     */
    private int insertPartyInfo(PartyInfoDO partyInfoDO) {
//        return DBUtil.insert(INSERT_URL, partyInfoDO);
        return 1;
    }

    /**
     * 更新机构信息，对库操作
     *
     * @param partyInfoDO
     * @return
     */
    private int updatePartyInfo(PartyInfoDO partyInfoDO) {
//        return DBUtil.update(UPDATE_URL, partyInfoDO);
        return 1;
    }



    /**
     * 获取已经失效的机构信息， 将这部分数据移动到临时表中
     *
     * @return
     */
    @Override
    public List<PartyInfoDO> getInEffectiveParty() {
        return queryLoseEffectiveParty();
    }

    private List<PartyInfoDO> queryLoseEffectiveParty() {
        //拿到今天日期， 查询今天以前失效的
//        String inEffectiveDate = DateUtil.getSysDate();
//        return DBUtil.selectList(QUERY_INEFFECTIVE_URL, inEffectiveDate);
        return new ArrayList<>();
    }

    @Autowired
    private PartyMapper partyMapper;

    /**
     * 查询机构信息，对库操作
     *
     * @param partyInfoDO
     * @return
     */
    private List<PartyInfoDO> queryPartyInfo(PartyInfoDO partyInfoDO) {
        return partyMapper.selectList(partyInfoDO);
    }

    /**
     * 根据id查询机构信息
     *
     * @param partyId
     * @return
     */
    public PartyInfoDO queryPartyInfoByPartyId(String partyId) {
        PartyInfoDO partyInfoDO = new PartyInfoDO();
        partyInfoDO.setPartyID(partyId);
        List<PartyInfoDO> partyInfoDOS = queryPartyInfo(partyInfoDO);

        if (partyInfoDOS == null || partyInfoDOS.isEmpty()) {
            return null;
        }
        return partyInfoDOS.get(0);
    }
}
