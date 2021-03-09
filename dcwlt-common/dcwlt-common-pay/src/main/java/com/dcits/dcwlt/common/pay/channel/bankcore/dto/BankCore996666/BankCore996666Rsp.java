/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年4月3日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.channel.bankcore.IBankCoreBody;

public class BankCore996666Rsp implements IBankCoreBody {
    private String txnSts;          // 交易状态
    private String hostJrnno;      // 主机交易流水
    private String hostAcdate;      // 主机日期

    private String coreRetCode;   // 原正交易的核心处理码
    private String coreRetMsg;      // 原正交易的核心处理信息


    @JSONField(name = "TXN_STS")
    public String getTxnSts() {
        return txnSts;
    }

    public void setTxnSts(String txnSts) {
        this.txnSts = txnSts;
    }

    @JSONField(name = "HOST_JRNNO")
    public String getHostJrnno() {
        return hostJrnno;
    }

    public void setHostJrnno(String hostJrnno) {
        this.hostJrnno = hostJrnno;
    }

    @JSONField(name = "HOST_ACDATE")
    public String getHostAcdate() {
        return hostAcdate;
    }

    public void setHostAcdate(String hostAcdate) {
        this.hostAcdate = hostAcdate;
    }

    public String getCoreRetCode() {
        return coreRetCode;
    }

    public void setCoreRetCode(String coreRetCode) {
        this.coreRetCode = coreRetCode;
    }

    public String getCoreRetMsg() {
        return coreRetMsg;
    }

    public void setCoreRetMsg(String coreRetMsg) {
        this.coreRetMsg = coreRetMsg;
    }

    @Override
    public String toString() {
        return "BankCore998889Rsp [txnSts=" + txnSts + ", hostJrnno=" + hostJrnno + ", hostAcdate=" + hostAcdate + "]";
    }


}