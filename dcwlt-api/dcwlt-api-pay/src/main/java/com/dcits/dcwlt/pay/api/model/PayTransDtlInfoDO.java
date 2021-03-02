/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月29日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.pay.util.HiddenUtil;

public class PayTransDtlInfoDO implements EcnyBaseDO {

    private String payDate;          //平台日期
    private String paySerno;         //平台流水
    private String payTime;          //平台时间
    private String direct;           //往来标识
    private String payFlag;          //收付标识
    private String operStep;         //操作步骤
    private String operStatus;       //操作状态
    private String trxStatus;         //业务状态
    private String trxRetCode;       //业务处理码
    private String trxRetMsg;        //业务处理信息
    private String coreProcStatus;   //核心处理状态
    private String coreReqDate;      //核心请求日期
    private String coreReqSerno;     //核心请求流水
    private String coreAcctDate;     //核心返回日期
    private String coreSerno;        //核心返回流水
    private String coreRetCode;      //核心拒绝码
    private String coreRetMsg;       //核心拒绝信息
    private String payPathSerno;     //通道流水
    private String payPathDateTime;  //通道日期时间
    private String pathProcStatus;   //通道状态
    private String payPathRspStatus;   //通道回执业务状态
    private String payPathRetCode;   //通道返回码
    private String payPathRetMsg;   //通道返回信息
    private String payPathRetDate;   //通道返回日期
    private String payPathRetSerno;  //通道返回流水
    private String batchId;          //交易批次号
    private String busiChnl;         //渠道大类
    private String busiChnl2;        //渠道中类
    private String busiSysDate;      //渠道日期
    private String busiSysSerno;     //渠道流水
    private String busiSysTime;      //渠道时间
    private String msgType;          //报文编号
    private String busiType;         //业务类型
    private String busiKind;         //业务种类
    private String instgPty;         //发起机构
    private String instdPty;        //接收机构
    private String amount;           //交易金额
    private String tradeFundSource;  //交易资金来源
    private String tradePurpose;     //交易用途
    private String payerPtyId;       //付款人钱包所属机构
    private String payerName;        //付款人名称
    private String payerAcctType;    //付款人账户类型
    private String payerAcct;        //付款人账户账号
    private String payerWalletId;    //付款人钱包ID
    private String payerWalletName;  //付款人钱包名称
    private String payerWalletLv;    //付款人钱包等级
    private String payerWalletType;  //付款人钱包类型
    private String payeePtyId;       //收款人账户所属机构
    private String payeeName;        //收款人名称
    private String payeeAcctType;    //收款人账户类型
    private String payeeAcct;        //收款人账户账号
    private String payeeWalletId;    //收款人钱包ID
    private String payeeWalletName;  //收款人钱包名称
    private String payeeWalletLv;    //收款人钱包等级
    private String payeeWalletType;  //收款人钱包类型
    private String protocolNum;      //挂接协议号
    private String ccy;              //币种
    private String tellerNo;         //柜员号
    private String zoneNo;           //分行号
    private String brno;             //交易行所号
    private String acctBrno;         //账务行所
    private String origChnl;         //源发起渠道大类
    private String origChnl2;        //源发起渠道中类
    private String origChnlDtl;      //源发起渠道细分
    private String origMsgType;      //原报文编号
    private String origPayPathDate;  //原业务通道日期
    private String origPayPathSerno; //原业务通道流水
    private String summary;          //摘要码
    private String endToEndID;       //端对端标志
    private String lastUpJrnno;      //最后更新流水
    private String lastUpDate;       //最后更新日期
    private String lastUpTime;       //最后更新时间
    private String narraTive;        //附言
    private String remark;           //备注

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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public String getOperStep() {
        return operStep;
    }

    public void setOperStep(String operStep) {
        this.operStep = operStep;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public String getTrxStatus() {
        return trxStatus;
    }

    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    public String getTrxRetCode() {
        return trxRetCode;
    }

    public void setTrxRetCode(String trxRetCode) {
        this.trxRetCode = trxRetCode;
    }

    public String getTrxRetMsg() {
        return trxRetMsg;
    }

    public void setTrxRetMsg(String trxRetMsg) {
        this.trxRetMsg = trxRetMsg;
    }

    public String getCoreProcStatus() {
        return coreProcStatus;
    }

    public void setCoreProcStatus(String coreProcStatus) {
        this.coreProcStatus = coreProcStatus;
    }

    public String getCoreReqSerno() {
        return coreReqSerno;
    }

    public void setCoreReqSerno(String coreReqSerno) {
        this.coreReqSerno = coreReqSerno;
    }

    public String getCoreAcctDate() {
        return coreAcctDate;
    }

    public void setCoreAcctDate(String coreAcctDate) {
        this.coreAcctDate = coreAcctDate;
    }

    public String getCoreSerno() {
        return coreSerno;
    }

    public void setCoreSerno(String coreSerno) {
        this.coreSerno = coreSerno;
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

    public String getPayPathSerno() {
        return payPathSerno;
    }

    public void setPayPathSerno(String payPathSerno) {
        this.payPathSerno = payPathSerno;
    }

    public String getPathProcStatus() {
        return pathProcStatus;
    }

    public void setPathProcStatus(String pathProcStatus) {
        this.pathProcStatus = pathProcStatus;
    }

    public String getPayPathRetCode() {
        return payPathRetCode;
    }

    public void setPayPathRetCode(String payPathRetCode) {
        this.payPathRetCode = payPathRetCode;
    }

    public String getPayPathRetMsg() {
        return payPathRetMsg;
    }

    public void setPayPathRetMsg(String payPathRetMsg) {
        this.payPathRetMsg = payPathRetMsg;
    }

    public String getPayPathRetDate() {
        return payPathRetDate;
    }

    public void setPayPathRetDate(String payPathRetDate) {
        this.payPathRetDate = payPathRetDate;
    }

    public String getPayPathRetSerno() {
        return payPathRetSerno;
    }

    public void setPayPathRetSerno(String payPathRetSerno) {
        this.payPathRetSerno = payPathRetSerno;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBusiChnl() {
        return busiChnl;
    }

    public void setBusiChnl(String busiChnl) {
        this.busiChnl = busiChnl;
    }

    public String getBusiChnl2() {
        return busiChnl2;
    }

    public void setBusiChnl2(String busiChnl2) {
        this.busiChnl2 = busiChnl2;
    }

    public String getBusiSysDate() {
        return busiSysDate;
    }

    public void setBusiSysDate(String busiSysDate) {
        this.busiSysDate = busiSysDate;
    }

    public String getBusiSysSerno() {
        return busiSysSerno;
    }

    public void setBusiSysSerno(String busiSysSerno) {
        this.busiSysSerno = busiSysSerno;
    }

    public String getBusiSysTime() {
        return busiSysTime;
    }

    public void setBusiSysTime(String busiSysTime) {
        this.busiSysTime = busiSysTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getBusiKind() {
        return busiKind;
    }

    public void setBusiKind(String busiKind) {
        this.busiKind = busiKind;
    }

    public String getInstgPty() {
        return instgPty;
    }

    public void setInstgPty(String instgPty) {
        this.instgPty = instgPty;
    }

    public String getInstdPty() {
        return instdPty;
    }

    public void setInstdPty(String instdPty) {
        this.instdPty = instdPty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeFundSource() {
        return tradeFundSource;
    }

    public void setTradeFundSource(String tradeFundSource) {
        this.tradeFundSource = tradeFundSource;
    }

    public String getTradePurpose() {
        return tradePurpose;
    }

    public void setTradePurpose(String tradePurpose) {
        this.tradePurpose = tradePurpose;
    }

    public String getPayerPtyId() {
        return payerPtyId;
    }

    public void setPayerPtyId(String payerPtyId) {
        this.payerPtyId = payerPtyId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerAcctType() {
        return payerAcctType;
    }

    public void setPayerAcctType(String payerAcctType) {
        this.payerAcctType = payerAcctType;
    }

    public String getPayerAcct() {
        return payerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        this.payerAcct = payerAcct;
    }

    public String getPayerWalletId() {
        return payerWalletId;
    }

    public void setPayerWalletId(String payerWalletId) {
        this.payerWalletId = payerWalletId;
    }

    public String getPayerWalletName() {
        return payerWalletName;
    }

    public void setPayerWalletName(String payerWalletName) {
        this.payerWalletName = payerWalletName;
    }

    public String getPayerWalletLv() {
        return payerWalletLv;
    }

    public void setPayerWalletLv(String payerWalletLv) {
        this.payerWalletLv = payerWalletLv;
    }

    public String getPayerWalletType() {
        return payerWalletType;
    }

    public void setPayerWalletType(String payerWalletType) {
        this.payerWalletType = payerWalletType;
    }

    public String getPayeePtyId() {
        return payeePtyId;
    }

    public void setPayeePtyId(String payeePtyId) {
        this.payeePtyId = payeePtyId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAcctType() {
        return payeeAcctType;
    }

    public void setPayeeAcctType(String payeeAcctType) {
        this.payeeAcctType = payeeAcctType;
    }

    public String getPayeeAcct() {
        return payeeAcct;
    }

    public void setPayeeAcct(String payeeAcct) {
        this.payeeAcct = payeeAcct;
    }

    public String getPayeeWalletId() {
        return payeeWalletId;
    }

    public void setPayeeWalletId(String payeeWalletId) {
        this.payeeWalletId = payeeWalletId;
    }

    public String getPayeeWalletName() {
        return payeeWalletName;
    }

    public void setPayeeWalletName(String payeeWalletName) {
        this.payeeWalletName = payeeWalletName;
    }

    public String getPayeeWalletLv() {
        return payeeWalletLv;
    }

    public void setPayeeWalletLv(String payeeWalletLv) {
        this.payeeWalletLv = payeeWalletLv;
    }

    public String getPayeeWalletType() {
        return payeeWalletType;
    }

    public void setPayeeWalletType(String payeeWalletType) {
        this.payeeWalletType = payeeWalletType;
    }

    public String getProtocolNum() {
        return protocolNum;
    }

    public void setProtocolNum(String protocolNum) {
        this.protocolNum = protocolNum;
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getBrno() {
        return brno;
    }

    public void setBrno(String brno) {
        this.brno = brno;
    }

    public String getAcctBrno() {
        return acctBrno;
    }

    public void setAcctBrno(String acctBrno) {
        this.acctBrno = acctBrno;
    }

    public String getOrigChnl() {
        return origChnl;
    }

    public void setOrigChnl(String origChnl) {
        this.origChnl = origChnl;
    }

    public String getOrigChnl2() {
        return origChnl2;
    }

    public void setOrigChnl2(String origChnl2) {
        this.origChnl2 = origChnl2;
    }

    public String getOrigChnlDtl() {
        return origChnlDtl;
    }

    public void setOrigChnlDtl(String origChnlDtl) {
        this.origChnlDtl = origChnlDtl;
    }

    public String getOrigMsgType() {
        return origMsgType;
    }

    public void setOrigMsgType(String origMsgType) {
        this.origMsgType = origMsgType;
    }

    public String getOrigPayPathDate() {
        return origPayPathDate;
    }

    public void setOrigPayPathDate(String origPayPathDate) {
        this.origPayPathDate = origPayPathDate;
    }

    public String getOrigPayPathSerno() {
        return origPayPathSerno;
    }

    public void setOrigPayPathSerno(String origPayPathSerno) {
        this.origPayPathSerno = origPayPathSerno;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEndToEndID() {
        return endToEndID;
    }

    public void setEndToEndID(String endToEndID) {
        this.endToEndID = endToEndID;
    }

    public String getLastUpJrnno() {
        return lastUpJrnno;
    }

    public void setLastUpJrnno(String lastUpJrnno) {
        this.lastUpJrnno = lastUpJrnno;
    }

    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getNarraTive() {
        return narraTive;
    }

    public void setNarraTive(String narraTive) {
        this.narraTive = narraTive;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getCoreReqDate() {
        return coreReqDate;
    }

    public void setCoreReqDate(String coreReqDate) {
        this.coreReqDate = coreReqDate;
    }

    public String getPayPathDateTime() {
        return payPathDateTime;
    }

    public void setPayPathDateTime(String payPathDateTime) {
        this.payPathDateTime = payPathDateTime;
    }

    @Override
    public String toString() {
        return "PayTransDtlInfoDO{" +
                "payDate='" + payDate + '\'' +
                ", paySerno='" + paySerno + '\'' +
                ", payTime='" + payTime + '\'' +
                ", direct='" + direct + '\'' +
                ", payFlag='" + payFlag + '\'' +
                ", operStep='" + operStep + '\'' +
                ", operStatus='" + operStatus + '\'' +
                ", trxStatus='" + trxStatus + '\'' +
                ", trxRetCode='" + trxRetCode + '\'' +
                ", trxRetMsg='" + trxRetMsg + '\'' +
                ", coreProcStatus='" + coreProcStatus + '\'' +
                ", coreReqDate='" + coreReqDate + '\'' +
                ", coreReqSerno='" + coreReqSerno + '\'' +
                ", coreAcctDate='" + coreAcctDate + '\'' +
                ", coreSerno='" + coreSerno + '\'' +
                ", coreRetCode='" + coreRetCode + '\'' +
                ", coreRetMsg='" + coreRetMsg + '\'' +
                ", payPathSerno='" + payPathSerno + '\'' +
                ", payPathDateTime='" + payPathDateTime + '\'' +
                ", pathProcStatus='" + pathProcStatus + '\'' +
                ", payPathRspStatus='" + payPathRspStatus + '\'' +
                ", payPathRetCode='" + payPathRetCode + '\'' +
                ", payPathRetMsg='" + payPathRetMsg + '\'' +
                ", payPathRetDate='" + payPathRetDate + '\'' +
                ", payPathRetSerno='" + payPathRetSerno + '\'' +
                ", batchId='" + batchId + '\'' +
                ", busiChnl='" + busiChnl + '\'' +
                ", busiChnl2='" + busiChnl2 + '\'' +
                ", busiSysDate='" + busiSysDate + '\'' +
                ", busiSysSerno='" + busiSysSerno + '\'' +
                ", busiSysTime='" + busiSysTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", busiType='" + busiType + '\'' +
                ", busiKind='" + busiKind + '\'' +
                ", instgPty='" + instgPty + '\'' +
                ", instdPty='" + instdPty + '\'' +
                ", amount='" + HiddenUtil.amtHidden(amount) + '\'' +
                ", tradeFundSource='" + tradeFundSource + '\'' +
                ", tradePurpose='" + tradePurpose + '\'' +
                ", payerPtyId='" + payerPtyId + '\'' +
                ", payerName='" + payerName + '\'' +
                ", payerAcctType='" + payerAcctType + '\'' +
                ", payerAcct='" + HiddenUtil.acHidden(payerAcct) + '\'' +
                ", payerWalletId='" + HiddenUtil.acHidden(payerWalletId) + '\'' +
                ", payerWalletName='" + HiddenUtil.acNameHidden(payerWalletName) + '\'' +
                ", payerWalletLv='" + payerWalletLv + '\'' +
                ", payerWalletType='" + payerWalletType + '\'' +
                ", payeePtyId='" + payeePtyId + '\'' +
                ", payeeName='" + HiddenUtil.acNameHidden(payeeName) + '\'' +
                ", payeeAcctType='" + payeeAcctType + '\'' +
                ", payeeAcct='" + HiddenUtil.acHidden(payeeAcct) + '\'' +
                ", payeeWalletId='" + HiddenUtil.acHidden(payeeWalletId) + '\'' +
                ", payeeWalletName='" + HiddenUtil.acNameHidden(payeeWalletName) + '\'' +
                ", payeeWalletLv='" + payeeWalletLv + '\'' +
                ", payeeWalletType='" + payeeWalletType + '\'' +
                ", protocolNum='" + protocolNum + '\'' +
                ", ccy='" + ccy + '\'' +
                ", tellerNo='" + tellerNo + '\'' +
                ", zoneNo='" + zoneNo + '\'' +
                ", brno='" + brno + '\'' +
                ", acctBrno='" + acctBrno + '\'' +
                ", origChnl='" + origChnl + '\'' +
                ", origChnl2='" + origChnl2 + '\'' +
                ", origChnlDtl='" + origChnlDtl + '\'' +
                ", origMsgType='" + origMsgType + '\'' +
                ", origPayPathDate='" + origPayPathDate + '\'' +
                ", origPayPathSerno='" + origPayPathSerno + '\'' +
                ", summary='" + summary + '\'' +
                ", endToEndID='" + endToEndID + '\'' +
                ", lastUpJrnno='" + lastUpJrnno + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", narraTive='" + narraTive + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getPayPathRspStatus() {
        return payPathRspStatus;
    }

    public void setPayPathRspStatus(String payPathRspStatus) {
        this.payPathRspStatus = payPathRspStatus;
    }

}
