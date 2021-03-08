/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年4月6日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.bankcore351100;

import com.dcits.dcwlt.common.pay.util.HiddenUtil;

import javax.validation.constraints.NotBlank;

public class BankCore351100InnerReq {
    @NotBlank(message = "平台日期不能为空")
    private String payDate;                                 // 平台日期

    @NotBlank(message = "平台流水不能为空")
    private String paySerno;                                // 平台流水

    @NotBlank(message = "通道代码不能为空")
    private String payPath;                                 // 通道代码

    @NotBlank(message = "核算方式不能为空")
    private String acctMeth;                                // 核算方式

    private String bookType;                                // 记账类型

    private String brno;                                    // 行所号

    private String tellerNo;                                // 柜员号

    private String coreSysId;                               // 核心系统标识

    private String revTranFlag;                             // 反交易标志

    @NotBlank(message = "服务方式不能为空")
    private String serverId;                                // 服务方式

    @NotBlank(message = "金额不能为空")
    private String amount;                                  // 金额

    @NotBlank(message = "币种不能为空")
    private String currency;                                // 币种

    @NotBlank(message = "付款人账号不能为空")
    private String payerAcct;                               // 付款账号

    @NotBlank(message = "付款人名称不能为空")
    private String payerName;                               // 付款人姓名

    private String realPayerAcct;                           // 实际付款账号

    private String realPayerName;                           // 实际付款户名

    @NotBlank(message = "付款行号不能为空")
    private String payerBank;                               // 付款行号

    private String payerBankName;                           // 付款行名称

    private String realPayeeAcct;                           // 收款人真实账号

    @NotBlank(message = "收款人账号不能为空")
    private String payeeAcct;                               // 收款账号

    private String realPayeeName;                           // 收款人真实姓名

    @NotBlank(message = "收款人姓名不能为空")
    private String payeeName;                               // 收款人姓名

    private String payeeBank;                               // 收款行号

    private String payeeBankName;                           // 收款行名称

    private String acctBrno;                                // 账务行所

    private String summary;                                 // 贷方摘要代码

    private String transType;                               // 贷方业务分类

    private String suspSerno;                               // 借方挂销账编号

    private String payerMediaType;                          // 借方介质标识

    private String coreTrxType;                             // 核心交易类型

    private String coreTrxCode;                             // 核心交易代码

    private String origCoreReqDate;                         // 原请求核心日期

    private String origCoreReqSerno;                        // 原请求核心流水

    @NotBlank(message = "请求核心日期不能为空")
    private String coreReqDate;                             // 请求核心日期

    @NotBlank(message = "请求核心流水不能为空")
    private String coreReqSerno;                            // 请求核心流水

    private String origPayDate;                             // 原业务日期

    private String origPaySerno;                            // 原业务流水

    private String password;                                // 密码

    private String narrative;                               // 附言

    private String cusVouchChkInd;                          // 是否验证凭证 付款方涉及票据时必送。Y-校验，送核心校验凭证有效期；N-不校验；

    private String cusVouchPwdInd;                          // 支付密码校验标识

    private String cusVouchPwd;                             // 支付密码

    private String cusVouchType;                            // 付款人凭证类型

    private String cusVouchno;                              // 付款人凭证号码

    private String cusVouchDate;                            // 付款人凭证日期

    private String cashExInd;                               // 钞汇标识

    private String remark;                                  // 备注

    private String reqType;                                 // 预入账标识填F

    private String srcCnsmrSysId;                           // 源交易发起系统标识

    private String srcCnsmrSysSeqNo;                        // 源交易发起系统流水

    private String chkNameFlg1;                             //借方户名校验标识 Y - 需要检查

    private String chkNameFlg2;                             //贷方户名校验标识 Y - 需要检查

    private String freezeSerno;                             //冻结编号

    private String clearDate;                               //清算日期，对应在核心报文头

    /**
     * 重发标识Y,如果是链路问题影响，重发后能正常处理，如果链路正常，原本就是业务处理失败，那就会返回原交易报错信息
     * 如果通过核心请求流水，之前入账成功，再重复标识Y,不会重复入账，如果核心处理过了，就会将交易结果返回出来
     */
    private String resendFlag;                              //重发标识，默认为N，不送Y，则不上送N

    private String reqChnl;                                 //源发起系统渠道大类，由核心系统预先分配
    private String reqChnl2;                                //源发起系统渠道中类，由核心系统预先分配


    public String getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(String resendFlag) {
        this.resendFlag = resendFlag;
    }

    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaySerno() {
        return paySerno;
    }

    public void setPaySerno(String paySerno) {
        this.paySerno = paySerno;
    }

    public String getPayPath() {
        return payPath;
    }

    public void setPayPath(String payPath) {
        this.payPath = payPath;
    }

    public String getAcctMeth() {
        return acctMeth;
    }

    public void setAcctMeth(String acctMeth) {
        this.acctMeth = acctMeth;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerAcct() {
        return payerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        this.payerAcct = payerAcct;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getRealPayeeAcct() {
        return realPayeeAcct;
    }

    public void setRealPayeeAcct(String realPayeeAcct) {
        this.realPayeeAcct = realPayeeAcct;
    }

    public String getPayeeAcct() {
        return payeeAcct;
    }

    public void setPayeeAcct(String payeeAcct) {
        this.payeeAcct = payeeAcct;
    }

    public String getRealPayeeName() {
        return realPayeeName;
    }

    public void setRealPayeeName(String realPayeeName) {
        this.realPayeeName = realPayeeName;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBank() {
        return payeeBank;
    }

    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank;
    }

    public String getPayeeBankName() {
        return payeeBankName;
    }

    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    public String getAcctBrno() {
        return acctBrno;
    }

    public void setAcctBrno(String acctBrno) {
        this.acctBrno = acctBrno;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSuspSerno() {
        return suspSerno;
    }

    public void setSuspSerno(String suspSerno) {
        this.suspSerno = suspSerno;
    }

    public String getPayerMediaType() {
        return payerMediaType;
    }

    public void setPayerMediaType(String payerMediaType) {
        this.payerMediaType = payerMediaType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBrno() {
        return brno;
    }

    public void setBrno(String brno) {
        this.brno = brno;
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo;
    }

    public String getCoreSysId() {
        return coreSysId;
    }

    public void setCoreSysId(String coreSysId) {
        this.coreSysId = coreSysId;
    }

    public String getRevTranFlag() {
        return revTranFlag;
    }

    public void setRevTranFlag(String revTranFlag) {
        this.revTranFlag = revTranFlag;
    }

    public String getCoreTrxType() {
        return coreTrxType;
    }

    public void setCoreTrxType(String coreTrxType) {
        this.coreTrxType = coreTrxType;
    }

    public String getCoreTrxCode() {
        return coreTrxCode;
    }

    public void setCoreTrxCode(String coreTrxCode) {
        this.coreTrxCode = coreTrxCode;
    }

    public String getOrigCoreReqDate() {
        return origCoreReqDate;
    }

    public void setOrigCoreReqDate(String origCoreReqDate) {
        this.origCoreReqDate = origCoreReqDate;
    }

    public String getOrigCoreReqSerno() {
        return origCoreReqSerno;
    }

    public void setOrigCoreReqSerno(String origCoreReqSerno) {
        this.origCoreReqSerno = origCoreReqSerno;
    }

    public String getOrigPayDate() {
        return origPayDate;
    }

    public void setOrigPayDate(String origPayDate) {
        this.origPayDate = origPayDate;
    }

    public String getOrigPaySerno() {
        return origPaySerno;
    }

    public void setOrigPaySerno(String origPaySerno) {
        this.origPaySerno = origPaySerno;
    }

    public String getRealPayerAcct() {
        return realPayerAcct;
    }

    public void setRealPayerAcct(String realPayerAcct) {
        this.realPayerAcct = realPayerAcct;
    }

    public String getRealPayerName() {
        return realPayerName;
    }

    public void setRealPayerName(String realPayerName) {
        this.realPayerName = realPayerName;
    }

    public String getCusVouchChkInd() {
        return cusVouchChkInd;
    }

    public void setCusVouchChkInd(String cusVouchChkInd) {
        this.cusVouchChkInd = cusVouchChkInd;
    }

    public String getCusVouchPwdInd() {
        return cusVouchPwdInd;
    }

    public void setCusVouchPwdInd(String cusVouchPwdInd) {
        this.cusVouchPwdInd = cusVouchPwdInd;
    }

    public String getCusVouchPwd() {
        return cusVouchPwd;
    }

    public void setCusVouchPwd(String cusVouchPwd) {
        this.cusVouchPwd = cusVouchPwd;
    }

    public String getCusVouchType() {
        return cusVouchType;
    }

    public void setCusVouchType(String cusVouchType) {
        this.cusVouchType = cusVouchType;
    }

    public String getCusVouchno() {
        return cusVouchno;
    }

    public void setCusVouchno(String cusVouchno) {
        this.cusVouchno = cusVouchno;
    }

    public String getCusVouchDate() {
        return cusVouchDate;
    }

    public void setCusVouchDate(String cusVouchDate) {
        this.cusVouchDate = cusVouchDate;
    }

    public String getCashExInd() {
        return cashExInd;
    }

    public void setCashExInd(String cashExInd) {
        this.cashExInd = cashExInd;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getSrcCnsmrSysId() {
        return srcCnsmrSysId;
    }

    public void setSrcCnsmrSysId(String srcCnsmrSysId) {
        this.srcCnsmrSysId = srcCnsmrSysId;
    }

    public String getSrcCnsmrSysSeqNo() {
        return srcCnsmrSysSeqNo;
    }

    public void setSrcCnsmrSysSeqNo(String srcCnsmrSysSeqNo) {
        this.srcCnsmrSysSeqNo = srcCnsmrSysSeqNo;
    }

    public String getChkNameFlg1() {
        return chkNameFlg1;
    }

    public void setChkNameFlg1(String chkNameFlg1) {
        this.chkNameFlg1 = chkNameFlg1;
    }

    public String getCoreReqDate() {
        return coreReqDate;
    }

    public void setCoreReqDate(String coreReqDate) {
        this.coreReqDate = coreReqDate;
    }

    public String getCoreReqSerno() {
        return coreReqSerno;
    }

    public void setCoreReqSerno(String coreReqSerno) {
        this.coreReqSerno = coreReqSerno;
    }

    public String getChkNameFlg2() {
        return chkNameFlg2;
    }

    public void setChkNameFlg2(String chkNameFlg2) {
        this.chkNameFlg2 = chkNameFlg2;
    }

    public String getFreezeSerno() {
        return freezeSerno;
    }

    public void setFreezeSerno(String freezeSerno) {
        this.freezeSerno = freezeSerno;
    }

    public String getReqChnl() {
        return reqChnl;
    }

    public void setReqChnl(String reqChnl) {
        this.reqChnl = reqChnl;
    }

    public String getReqChnl2() {
        return reqChnl2;
    }

    public void setReqChnl2(String reqChnl2) {
        this.reqChnl2 = reqChnl2;
    }

    @Override
    public String toString() {
        return "BankCore351100InnerReq{" +
                "payDate='" + payDate + '\'' +
                ", paySerno='" + paySerno + '\'' +
                ", payPath='" + payPath + '\'' +
                ", acctMeth='" + acctMeth + '\'' +
                ", bookType='" + bookType + '\'' +
                ", brno='" + brno + '\'' +
                ", tellerNo='" + tellerNo + '\'' +
                ", coreSysId='" + coreSysId + '\'' +
                ", revTranFlag='" + revTranFlag + '\'' +
                ", serverId='" + serverId + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", payerAcct='" + HiddenUtil.acHidden(payerAcct) + '\'' +
                ", payerName='" + HiddenUtil.acNameHidden(payerName) + '\'' +
                ", realPayerAcct='" + HiddenUtil.acHidden(realPayerAcct) + '\'' +
                ", realPayerName='" + HiddenUtil.acNameHidden(realPayerName) + '\'' +
                ", payerBank='" + payerBank + '\'' +
                ", payerBankName='" + payerBankName + '\'' +
                ", realPayeeAcct='" + HiddenUtil.acHidden(realPayeeAcct) + '\'' +
                ", payeeAcct='" + HiddenUtil.acHidden(payeeAcct) + '\'' +
                ", realPayeeName='" + HiddenUtil.acNameHidden(realPayeeName) + '\'' +
                ", payeeName='" + HiddenUtil.acNameHidden(payeeName) + '\'' +
                ", payeeBank='" + payeeBank + '\'' +
                ", payeeBankName='" + payeeBankName + '\'' +
                ", acctBrno='" + acctBrno + '\'' +
                ", summary='" + summary + '\'' +
                ", transType='" + transType + '\'' +
                ", suspSerno='" + suspSerno + '\'' +
                ", payerMediaType='" + payerMediaType + '\'' +
                ", coreTrxType='" + coreTrxType + '\'' +
                ", coreTrxCode='" + coreTrxCode + '\'' +
                ", origCoreReqDate='" + origCoreReqDate + '\'' +
                ", origCoreReqSerno='" + origCoreReqSerno + '\'' +
                ", coreReqDate='" + coreReqDate + '\'' +
                ", coreReqSerno='" + coreReqSerno + '\'' +
                ", origPayDate='" + origPayDate + '\'' +
                ", origPaySerno='" + origPaySerno + '\'' +
                ", password='" + password + '\'' +
                ", narrative='" + narrative + '\'' +
                ", cusVouchChkInd='" + cusVouchChkInd + '\'' +
                ", cusVouchPwdInd='" + cusVouchPwdInd + '\'' +
                ", cusVouchPwd='" + cusVouchPwd + '\'' +
                ", cusVouchType='" + cusVouchType + '\'' +
                ", cusVouchno='" + cusVouchno + '\'' +
                ", cusVouchDate='" + cusVouchDate + '\'' +
                ", cashExInd='" + cashExInd + '\'' +
                ", remark='" + remark + '\'' +
                ", reqType='" + reqType + '\'' +
                ", srcCnsmrSysId='" + srcCnsmrSysId + '\'' +
                ", srcCnsmrSysSeqNo='" + srcCnsmrSysSeqNo + '\'' +
                ", chkNameFlg1='" + chkNameFlg1 + '\'' +
                ", chkNameFlg2='" + chkNameFlg2 + '\'' +
                ", freezeSerno='" + freezeSerno + '\'' +
                ", clearDate='" + clearDate + '\'' +
                ", resendFlag='" + resendFlag + '\'' +
                ", reqChnl='" + reqChnl + '\'' +
                ", reqChnl2='" + reqChnl2 + '\'' +
                '}';
    }
}
