/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月29日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IPayTransDtlInfoRepository {

    int insert(PayTransDtlInfoDO payTransDtlInfoDO);

    int update(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    int update(PayTransDtlInfoDO payTransDtlInfoDO);
    
    PayTransDtlInfoDO query(String payDate, String paySerno);

    PayTransDtlInfoDO query(String msgId);

    PayTransDtlInfoDO queryOriTxn(String busiSysSerno);

    List<PayTransDtlInfoDO> queryList(String origPayPathSerno);
}
