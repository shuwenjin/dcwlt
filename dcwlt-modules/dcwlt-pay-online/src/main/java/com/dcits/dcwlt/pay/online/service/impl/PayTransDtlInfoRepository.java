/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月29日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("payTransDtlInfoRepository")
public class PayTransDtlInfoRepository implements IPayTransDtlInfoRepository {

    private static final String INSERT_PAYTRANSDTL_INFO_SQL = "payTransDtlInfoMapper.insert";
    private static final String UPDATE_PAYTRANSDTL_INFO_SQL = "payTransDtlInfoMapper.update";
    private static final String UPDATE_PAYTRANSDTL_DIRECT_SQL = "payTransDtlInfoMapper.updateDirect";
    private static final String QUERY_PAYTRANSDTL_BY_MSGID_SQL = "payTransDtlInfoMapper.queryByMsgId";
    private static final String QUERY_PAYTRANSDTL_BY_PAYINFO_SQL = "payTransDtlInfoMapper.queryByPayInfo";
    private static final String QUERY_PAYTRANSDTL_BY_BUSISYSSERNO_SQL = "payTransDtlInfoMapper.queryByBusiSysSerno";
    private static final String QUERY_PAYTRANSDTL_BY_ORIGSERNO_SQL = "payTransDtlInfoMapper.queryByOrigSerno";

    /**
     * 金融交易信息入流水库
     *
     * @param payTransDtlInfoDO 金融信息流水表实体
     * @return
     */
    public int insert(PayTransDtlInfoDO payTransDtlInfoDO) {
//        return DBUtil.insert(INSERT_PAYTRANSDTL_INFO_SQL, payTransDtlInfoDO);
        return 1;
    }

    /**
     * 金融交易流水表更新
     * @param payTransDtlInfoDO
     * @return
     */
    public int update(PayTransDtlInfoDO payTransDtlInfoDO) {
        //补充更新字段
//        payTransDtlInfoDO.setLastUpDate(DateTimeUtil.getCurrentDateStr());
//        payTransDtlInfoDO.setLastUpTime(DateTimeUtil.getCurrentTimeStr());
//
//        return DBUtil.update(UPDATE_PAYTRANSDTL_DIRECT_SQL, payTransDtlInfoDO);
        return 1;
    }

    /**
     * 金融信息表更新
     * @param payTransDtlInfoDO
     * @param stateMachine
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public int update(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //补充更新字段
        payTransDtlInfoDO.setLastUpDate(DateUtil.getSysDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getCurTime());

//        Map<String, String> param = BeanUtils.describe(payTransDtlInfoDO);
//        if (null != stateMachine) {
//            param.putAll(BeanUtils.describe(stateMachine));
//        }
//        return DBUtil.update(UPDATE_PAYTRANSDTL_INFO_SQL, param);
        return 1;
    }

    /**
     * 根据平台流水和日期查询金融交易流水表
     *
     * @param payDate,paySerno
     * @return
     */
    public PayTransDtlInfoDO query(String payDate, String paySerno) {
        Map<String, String> param = new HashMap<>();
        param.put("payDate", payDate);
        param.put("paySerno", paySerno);
//        return DBUtil.selectOne(QUERY_PAYTRANSDTL_BY_PAYINFO_SQL, param);
        return new PayTransDtlInfoDO();
    }

    /**
     * 根据报文标识号查询金融交易流水表
     *
     * @param msgId
     * @return
     */
    public PayTransDtlInfoDO query(String msgId) {
//        return DBUtil.selectOne(QUERY_PAYTRANSDTL_BY_MSGID_SQL, msgId);
        return new PayTransDtlInfoDO();
    }

    /**
     * 根据渠道请求流水查询金融信息表原交易信息
     *
     * @param busiSysSerno
     * @return
     */
    public PayTransDtlInfoDO queryOriTxn(String busiSysSerno) {
//        return DBUtil.selectOne(QUERY_PAYTRANSDTL_BY_BUSISYSSERNO_SQL, busiSysSerno);
        return new PayTransDtlInfoDO();
    }

    /**
     * 根据原交易流水查询异常交易
     *
     * @param origPayPathSerno
     * @return
     */
    @Override
    public List<PayTransDtlInfoDO> queryList(String origPayPathSerno) {
//        return DBUtil.selectList(QUERY_PAYTRANSDTL_BY_ORIGSERNO_SQL, origPayPathSerno);
       return new ArrayList<PayTransDtlInfoDO>();
    }
}
