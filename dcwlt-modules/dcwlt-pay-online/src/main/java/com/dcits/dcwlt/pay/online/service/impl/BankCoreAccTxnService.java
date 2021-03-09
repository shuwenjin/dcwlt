package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCoreAccTxnService {
    private static final Logger logger = LoggerFactory.getLogger(BankCoreAccTxnService.class);
    @Autowired
    private AccFlowRepository accFlowRepository;

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
        accFlowDO.setPayerName(data.getPayerName());
        accFlowDO.setPayeeAcct(data.getPayeeAcct());
        accFlowDO.setPayeeName(data.getPayeeName());
        accFlowDO.setOrigPayDate(data.getPayDate());
        accFlowDO.setOrigPaySerno(data.getPaySerno());
        return accFlowRepository.insert(accFlowDO);
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

        return accFlowRepository.update(accFlowEnDO.getCoreReqDate(), accFlowEnDO.getCoreReqSerno(), accFlowEnDO);
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
        accFlowDO.setCoreTime(DateUtil.getDefaultDate());
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
        return accFlowRepository.insert(accFlowDO);
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
        return accFlowRepository.update(accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno(), accFlowDO);
    }

    /**
     * 根据核心请求日期和请求流水获取 记账实体
     *
     * @param coreReqDate
     * @param coreReqSerno
     * @return
     */
    public AccFlowDO selectByOrigInfo(String coreReqDate, String coreReqSerno) {
        AccFlowDO entity = accFlowRepository.query(coreReqDate, coreReqSerno);
        if (null == entity) {
            logger.error("原交易不存在，coreReqDate：{}， coreReqSerno：{}", coreReqDate, coreReqSerno);
            throw new PlatformException(PlatformError.EMPTY_RECORDS);
        }
        return entity;
    }
}
