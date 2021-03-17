package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReq;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface IPayTransDtlInfoService {
    /**
     * 兑回初始化金融交易簿
     */
    PayTransDtlInfoDO init(ReconvertReq reconvertReqDTO);

    /**
     * 更新200为终态000
     * @param payTransDtlInfoDO
     */
    int updateFinalStatusFail(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     *  更新211为终态111
     * @param payTransDtlInfoDO
     */
    int updateFinalStatusSucc(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新110为终态210
     * @param payTransDtlInfoDO
     * @return
     */
    int updateFinalStatus110To210(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新290为终态000  
     * @param payTransDtlInfoDO
     * @return
     */
    int updateFinalStatus290To000(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新001为终态221
     * @param payTransDtlInfoDO
     * @return
     */
    int updateFinalStatus001To221(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新X90为终态090
     * @param payTransDtlInfoDO
     * @param stateMachine
     * @return
     */
    int updateFinalStatusX90To090(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine);

    /**
     * 更新207为终态000
     * @param payTransDtlInfoDO
     * @return
     */
    int updateFinalStatus207To000(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新217为终态111
     * @param payTransDtlInfoDO
     * @return
     */
    int updateFinalStatus217To111(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 判断原交易是否可更新为终态
     * @param payTransDtlInfoDO
     * @return
     */
    int updatePayTransDtlFinal(PayTransDtlInfoDO payTransDtlInfoDO);

    /**
     * 更新通道状态
     * @param payTransDtlInfoDO
     * @param payPathStatus
     * @return
     */
    int updatePathStatus(PayTransDtlInfoDO payTransDtlInfoDO, String payPathStatus);

    /**
     * 更新X91为终态191
     * @param payTransDtlInfoDO
     * @param stateMachine
     * @return
     */
    int updateFinalStatusX91To191(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine);

    /**
     * 交易类型判断：兑回、兑出、汇款兑出、差错往账
     * @param payTransDtlInfoDO
     * @return
     */
    boolean checkMsgType(PayTransDtlInfoDO payTransDtlInfoDO);


    //----------------------------数据库交互-----------------------------------------------
    int insert(PayTransDtlInfoDO payTransDtlInfoDO);

    int update(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    int update(PayTransDtlInfoDO payTransDtlInfoDO);

    PayTransDtlInfoDO query(String payDate, String paySerno);

    PayTransDtlInfoDO query(String msgId);

    PayTransDtlInfoDO queryOriTxn(String busiSysSerno);

    List<PayTransDtlInfoDO> queryList(String origPayPathSerno);
}
