/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年3月31日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.channel.bankcore.IBankCoreBodyArrayInfo;

public class BankCore351100ArrayInfoReq implements IBankCoreBodyArrayInfo {
    private String feeFlg;    //手续费标志
    private String feeAc;    //费用账号
    private String feeCd;    //费用种类
    private String feeAmt;    //费用金额
    private String feeCcy;    //费用货币
    private String feeBrFlg;    //费用归属标识
    private String feeBr;    //费用归属行所号

    @JSONField(name = "FEE_FLG")
    public String getFeeFlg() {
        return feeFlg;
    }

    public void setFeeFlg(String feeFlg) {
        this.feeFlg = feeFlg;
    }

    @JSONField(name = "FEE_AC")
    public String getFeeAc() {
        return feeAc;
    }

    public void setFeeAc(String feeAc) {
        this.feeAc = feeAc;
    }

    @JSONField(name = "FEE_CD")
    public String getFeeCd() {
        return feeCd;
    }

    public void setFeeCd(String feeCd) {
        this.feeCd = feeCd;
    }

    @JSONField(name = "FEE_AMT")
    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    @JSONField(name = "FEE_CCY")
    public String getFeeCcy() {
        return feeCcy;
    }

    public void setFeeCcy(String feeCcy) {
        this.feeCcy = feeCcy;
    }

    @JSONField(name = "FEE_BR_FLG")
    public String getFeeBrFlg() {
        return feeBrFlg;
    }

    public void setFeeBrFlg(String feeBrFlg) {
        this.feeBrFlg = feeBrFlg;
    }

    @JSONField(name = "FEE_BR")
    public String getFeeBr() {
        return feeBr;
    }

    public void setFeeBr(String feeBr) {
        this.feeBr = feeBr;
    }

    @Override
    public String toString() {
        return "BankCore351100ArrayInfoRsp [feeFlg=" + feeFlg + ", feeAc=" + feeAc + ", feeCd=" + feeCd + ", feeAmt="
                + feeAmt + ", feeCcy=" + feeCcy + ", feeBr_Flg=" + feeBrFlg + ", feeBr=" + feeBr + ", getFeeFlg()="
                + getFeeFlg() + ", getFeeAc()=" + getFeeAc() + ", getFeeCd()=" + getFeeCd() + ", getFeeAmt()="
                + getFeeAmt() + ", getFeeCcy()=" + getFeeCcy() + ", getFeeBr_Flg()=" + getFeeBrFlg() + ", getFeeBr()="
                + getFeeBr() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}
