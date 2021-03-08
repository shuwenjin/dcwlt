/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年3月31日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.channel.bankcore.IBankCoreBody;

public class BankCore996666Req implements IBankCoreBody {

    private String reqSysId;   //    外围系统标识
    private String reqSysDate;   //    交易日期
    private String reqSysJrn;   //    外围系统交易流水号


    @JSONField(name = "REQ_SYS_ID")
    public String getReqSysId() {
        return reqSysId;
    }

    public void setReqSysId(String reqSysId) {
        this.reqSysId = reqSysId;
    }

    @JSONField(name = "REQ_SYS_DATE")
    public String getReqSysDate() {
        return reqSysDate;
    }

    public void setReqSysDate(String reqSysDate) {
        this.reqSysDate = reqSysDate;
    }

    @JSONField(name = "REQ_SYS_JRN")
    public String getReqSysJrn() {
        return reqSysJrn;
    }

    public void setReqSysJrn(String reqSysJrn) {
        this.reqSysJrn = reqSysJrn;
    }

    @Override
    public String toString() {
        return "BankCore998889Req [reqSysId=" + reqSysId + ", reqSysDate=" + reqSysDate + ", reqSysJrn=" + reqSysJrn
                + "]";
    }


}