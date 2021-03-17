package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCoreAccTxnServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(BankCoreAccTxnServiceImpl.class);

    @Autowired
    private AccFlowServiceImpl accFlowServiceImpl;

    /**
     * 登记核心流水表
     *
     * @param data         请求实体
     * @param coreReqDate  请求核心日期
     * @param coreReqSerno 请求核心流水号
     * @return
     */
    public int insertCoreFlow(BankCore351100InnerReq data, String coreReqSerno, String coreReqDate) {
        AccFlowDO accFlowDO = new AccFlowDO();
        accFlowDO.setCoreReqDate(coreReqDate);
        accFlowDO.setCoreReqSerno(coreReqSerno);
        accFlowDO.setBookType(data.getBookType());
        accFlowDO.setPayDate(data.getPayDate());
        accFlowDO.setPaySerno(data.getPaySerno());
        accFlowDO.setBrno(data.getBrno());
        accFlowDO.setTellerNo(data.getTellerNo());
        accFlowDO.setAcctBrno(data.getAcctBrno());
        accFlowDO.setCurrency(data.getCurrency());
        accFlowDO.setAmount(data.getAmount());
        accFlowDO.setCoreSysId(data.getCoreSysId());
        accFlowDO.setRevTranFlag(data.getRevTranFlag());
        accFlowDO.setCoreTime(DateUtil.getDefaultTime());
        accFlowDO.setCoreTrxType(data.getCoreTrxType());
        accFlowDO.setCoreTrxCode(data.getCoreTrxCode());
        accFlowDO.setCoreProcStatus(Constant.CORESTATUS_ABEND);
        accFlowDO.setPayerAcct(data.getPayerAcct());
        if (data.getPayerAcct()==null) {
            accFlowDO.setPayerAcct("6214622121003305144");
        }
        accFlowDO.setPayerName(data.getPayerName());
        accFlowDO.setPayeeAcct(data.getPayeeAcct());
        accFlowDO.setPayeeName(data.getPayeeName());
        accFlowDO.setOrigPayDate(data.getPayDate());
        accFlowDO.setOrigPaySerno(data.getPaySerno());
        return accFlowServiceImpl.insert(accFlowDO);
    }

    /**
     * 核心后处理
     *
     * @param bankCore351100InnerRsp
     */
    public int updateCoreAccFlow(BankCore351100InnerRsp bankCore351100InnerRsp) {
        AccFlowDO accFlowEnDO = new AccFlowDO();
        accFlowEnDO.setCoreReqDate(bankCore351100InnerRsp.getCoreReqDate());
        accFlowEnDO.setCoreReqSerno(bankCore351100InnerRsp.getCoreReqSerno());
        accFlowEnDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        accFlowEnDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        accFlowEnDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        accFlowEnDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        accFlowEnDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());

        return accFlowServiceImpl.update(accFlowEnDO.getCoreReqDate(), accFlowEnDO.getCoreReqSerno(), accFlowEnDO);
    }



    /**
     * 冲正登记账务流水表
     *
     * @param data         账务流水表实体
     * @param coreReqDate  请求核心日期
     * @param coreReqSerno 请求核心流水
     * @return
     */
    public int insertReverseCoreFlow(AccFlowDO data, String coreReqDate, String coreReqSerno) {
        AccFlowDO accFlowDO = new AccFlowDO();
        accFlowDO.setCoreReqDate(coreReqDate);
        accFlowDO.setCoreReqSerno(coreReqSerno);
        accFlowDO.setBookType("BANKREV");
        accFlowDO.setPayDate(data.getPayDate());
        accFlowDO.setPaySerno(data.getPaySerno());
        accFlowDO.setBrno(data.getBrno());
        accFlowDO.setTellerNo(data.getTellerNo());
        accFlowDO.setAcctBrno(data.getAcctBrno());
        accFlowDO.setCurrency(data.getCurrency());
        accFlowDO.setAmount(data.getAmount());
        accFlowDO.setCoreSysId(data.getCoreSysId());
        accFlowDO.setRevTranFlag("0");
        accFlowDO.setCoreTime(DateUtil.getDefaultTime());
        accFlowDO.setCoreTrxType(data.getCoreTrxType());
        accFlowDO.setCoreTrxCode(data.getCoreTrxCode());
        accFlowDO.setCoreProcStatus(Constant.CORESTATUS_ABEND);
        accFlowDO.setPayerAcct(data.getPayerAcct());
        accFlowDO.setPayerName(data.getPayerName());
        accFlowDO.setPayeeAcct(data.getPayeeAcct());
        accFlowDO.setPayeeName(data.getPayeeName());
        accFlowDO.setOrigCoreReqDate(data.getCoreReqDate());
        accFlowDO.setOrigCoreReqSerno(data.getCoreReqSerno());
        accFlowDO.setOrigPayDate(data.getPayDate());
        accFlowDO.setOrigPaySerno(data.getPaySerno());
        return accFlowServiceImpl.insert(accFlowDO);
    }

    /**
     * 根据核心请求日期和请求流水获取 记账实体
     *
     * @param coreReqDate
     * @param coreReqSerno
     * @return
     */
    public AccFlowDO selectByOrigInfo(String coreReqDate, String coreReqSerno) {
        AccFlowDO entity = accFlowServiceImpl.query(coreReqDate, coreReqSerno);
        if (null == entity) {
            logger.error("原交易不存在，coreReqDate：{}， coreReqSerno：{}", coreReqDate, coreReqSerno);
            throw new PlatformException(PlatformError.EMPTY_RECORDS);
        }
        return entity;
    }



    /**
     * 更新冲正结果
     *
     * @param rspMsg
     * @return
     */
    public int updateReverseCoreFlow(BankCore998889Rsp rspMsg) {
        AccFlowDO accFlowDO = new AccFlowDO();
        accFlowDO.setCoreReqDate(rspMsg.getReqCoreDate());
        accFlowDO.setCoreReqSerno(rspMsg.getReqCoreSerno());
        accFlowDO.setCoreRetCode(rspMsg.getErrorCode());
        accFlowDO.setCoreRetMsg(rspMsg.getErrorMsg());
        accFlowDO.setCoreProcStatus(rspMsg.getCoreStatus());
        accFlowDO.setCoreAcctDate(rspMsg.getRspCoreDate());
        accFlowDO.setCoreSerno(rspMsg.getRspCoreSerno());
        return accFlowServiceImpl.update(accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno(), accFlowDO);
    }





    /**
     * 更新回查结果
     *
     * @param bankCore996666Rsp
     * @return
     */
    public int updateQryTradeRet(String coreReqDate, String coreReqSerno, BankCore996666Rsp bankCore996666Rsp) {
        AccFlowDO accFlowDO = new AccFlowDO();
        String coreProcStatus = getCoreStatusMap(bankCore996666Rsp.getTxnSts());
        accFlowDO.setCoreReqDate(coreReqDate);
        accFlowDO.setCoreReqSerno(coreReqSerno);
        accFlowDO.setCoreProcStatus(coreProcStatus);
        accFlowDO.setCoreAcctDate(bankCore996666Rsp.getHostAcdate());
        accFlowDO.setCoreSerno(bankCore996666Rsp.getHostJrnno());
        accFlowDO.setCoreRetCode(bankCore996666Rsp.getCoreRetCode());
        accFlowDO.setCoreRetMsg(bankCore996666Rsp.getCoreRetMsg());
        return accFlowServiceImpl.update(accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno(), accFlowDO);
    }


    /**
     * 获取核心状态映射
     *
     * @param status
     * @return
     */
    public String getCoreStatusMap(String status) {
//        0-处理中
//        1-成功
//        2-被拒纳
//        3-已冲正
//        N-记录不存在
//        5-返回授权 （交易没有成功）
//        6-半自动收费（交易没有成功）
        if (status.equals("3")) {
            return Constant.CORESTATUS_REVERSED;
        } else if (StringUtils.equalsAny(status, "1")) {
            return Constant.CORESTATUS_SUCCESS;
        } else if (StringUtils.equalsAny(status, "2", "5", "6")) {
            return Constant.CORESTATUS_FAILED;
        } else {
            return Constant.CORESTATUS_ABEND;
        }
    }

    /**
     * 根据平台日期平台流水获取 记账实体
     *
     * @param payDate
     * @param paySerno
     * @return
     */
    public AccFlowDO selectByPayInfo(String payDate, String paySerno) {
        return accFlowServiceImpl.selectCoreReqSerno(payDate, paySerno);
    }

    public int updateCoreAccFlowStatus(AccFlowDO accFlowEnDO) {
        return accFlowServiceImpl.updateCoreStatus(accFlowEnDO);
    }



}
