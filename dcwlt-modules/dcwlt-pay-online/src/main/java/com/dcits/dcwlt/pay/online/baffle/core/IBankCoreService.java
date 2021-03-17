package com.dcits.dcwlt.pay.online.baffle.core;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqHeader;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqMessage;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreRspMessage;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Req;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.util.APPUtil;

public interface IBankCoreService {


    /**
     * 回查核心状态
     *
     * @param reqSysDate 原交易请求日期
     * @param reqSysJrn  原交易请求流水
     * @return
     */
    public BankCore996666Rsp queryCoreStatus(String reqSysDate, String reqSysJrn);
    /**
     * 回查核心状态
     *
     * @param srcCnsmrSysId    源交易请求系统标识
     * @param srcCnsmrSysSeqNo 源交易请求系统流水
     * @param reqSysDate       原交易请求日期
     * @param reqSysJrn        原交易请求流水
     * @return
     */
    public BankCore996666Rsp queryCoreStatus(String srcCnsmrSysId, String srcCnsmrSysSeqNo, String reqSysDate, String reqSysJrn);
}
