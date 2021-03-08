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
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoDOMapper;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoMapper;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("payTransDtlInfoRepository")
public class PayTransDtlInfoRepository implements IPayTransDtlInfoRepository {

    @Autowired
    private PayTransDtlInfoDOMapper payTransDtlInfoDOMapper;

    @Autowired
    private PayTransDtlInfoMapper payTransDtlInfoMapper;

    /**
     * 金融交易信息入流水库
     *
     * @param payTransDtlInfoDO 金融信息流水表实体
     * @return
     */
    @Override
    public int insert(PayTransDtlInfoDO payTransDtlInfoDO) {
//       return payTransDtlInfoDOMapper.insert(payTransDtlInfoDO);
        return payTransDtlInfoMapper.insert(payTransDtlInfoDO);
    }

    /**
     * 金融交易流水表更新
     * @param payTransDtlInfoDO
     * @return
     */
    @Override
    public int update(PayTransDtlInfoDO payTransDtlInfoDO) {
        //补充更新字段
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        return payTransDtlInfoMapper.updateDirect(payTransDtlInfoDO);
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
    @Override
    public int update(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //补充更新字段
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        Map<String, String> param = BeanUtils.describe(payTransDtlInfoDO);
        if (null != stateMachine) {
            param.putAll(BeanUtils.describe(stateMachine));
        }
        return payTransDtlInfoMapper.update(param);
    }

    /**
     * 根据平台流水和日期查询金融交易流水表
     *
     * @param payDate,paySerno
     * @return
     */
    @Override
    public PayTransDtlInfoDO query(String payDate, String paySerno) {
        Map<String, String> param = new HashMap<>();
        param.put("payDate", payDate);
        param.put("paySerno", paySerno);
        return payTransDtlInfoMapper.queryByPayInfo(param);
    }

    /**
     * 根据报文标识号查询金融交易流水表
     *
     * @param msgId
     * @return
     */
    @Override
    public PayTransDtlInfoDO query(String msgId) {
        return payTransDtlInfoMapper.queryByMsgId(msgId);
    }

    /**
     * 根据渠道请求流水查询金融信息表原交易信息
     *
     * @param busiSysSerno
     * @return
     */
    @Override
    public PayTransDtlInfoDO queryOriTxn(String busiSysSerno) {
        return payTransDtlInfoMapper.queryByBusiSysSerno(busiSysSerno);
    }

    /**
     * 根据原交易流水查询异常交易
     *
     * @param origPayPathSerno
     * @return
     */
    @Override
    public List<PayTransDtlInfoDO> queryList(String origPayPathSerno) {
        return payTransDtlInfoMapper.queryByOrigSerno(origPayPathSerno);
    }
}
