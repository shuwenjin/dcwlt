package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.pay.util.HiddenUtil;

/**
 * 
 * @version 1.0.0
 * <p>协议流水表实体</p>
 * @date 2020/12/30
 */
public class SignJrnDO {

    /**
     * 平台日期
     */
    private String payDate;

    /**
     * 平台流水
     */
    private String paySerNo;

    /**
     * 平台时间
     */
    private String payTime;

    /**
     * 报文标识号
     */
    private String msgId;

    /**
     * 发起机构
     */
    private String instGpTy;

    /**
     * 接收机构
     */
    private String instDpTy;

    /**
     * 往来方向 SEND(发送) RECV(接收）
     */
    private String direct;

    /**
     * 管理类型：MT01(身份认证),MT02(身份确认),MT03(解约申请)
     */
    private String manageType;

    /**
     * 签约类型 SG00(不签约),SG01(签约)
     */
    private String signType;

    /**
     * 挂接协议号
     */
    private String signNo;

    /**
     * 动态关联码：MSG+应答报文流水
     */
    private String msgSendCode;

    /**
     *动态验证码：SM4加密存储
     */
    private String msgVerifyCode;

    /**
     * 业务处理状态 0-失败 1-成功 2-处理中
     */
    private String trxStatus;

    /**
     * 业务处理码
     */
    private String trxRetCode;

    /**
     * 业务处理信息
     */
    private String trxRetMsg;

    /**
     * 应答报文标识号
     */
    private String rspMsgId;

    /**
     * 应答回执状态
     */
    private String rspStatus;

    /**
     * 应答业务处理码
     */
    private String rspCode;

    /**
     * 应答业务处理信息
     */
    private String rspMsg;

    /**
     * 签约人银行账户所属机构
     */
    private String acctPtyId;

    /**
     * 签约人银行账户类型
     */
    private String acctType;

    /**
     * 签约人银行账户账号
     */
    private String acctId;

    /**
     * 签约人银行账户户名
     */
    private String acctName;

    /**
     * 签约人证件类型
     */
    private String idType;

    /**
     * 签约人证件号码
     */
    private String idNo;

    /**
     * 银行预留手机号码
     */
    private String telephone;

    /**
     * 钱包开立所属机构编码
     */
    private String walletPtyId;

    /**
     * 钱包ID
     */
    private String walletId;

    /**
     * 钱包类型：WT01(个人钱包),WT02(子个人钱包),WT03(硬件钱包),WT09(对公钱包),WT10(子对公钱包)
     */
    private String walletType;

    /**
     * 钱包等级：WL01(一类钱包),WL02(二类钱包),WL03(三类钱包),WL04(四类钱包)
     */
    private String walletLevel;

    /**
     * 最后更新流水
     */
    private String lastUpJrnNo;

    /**
     * 最后更新日期
     */
    private String lastUpDate;

    /**
     * 最后更新时间
     */
    private String lastUpTime;

    /**
     * 备注
     */
    private String remark;


    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getInstGpTy() {
        return instGpTy;
    }

    public void setInstGpTy(String instGpTy) {
        this.instGpTy = instGpTy;
    }

    public String getInstDpTy() {
        return instDpTy;
    }

    public void setInstDpTy(String instDpTy) {
        this.instDpTy = instDpTy;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getMsgSendCode() {
        return msgSendCode;
    }

    public void setMsgSendCode(String msgSendCode) {
        this.msgSendCode = msgSendCode;
    }

    public String getMsgVerifyCode() {
        return msgVerifyCode;
    }

    public void setMsgVerifyCode(String msgVerifyCode) {
        this.msgVerifyCode = msgVerifyCode;
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

    public String getRspMsgId() {
        return rspMsgId;
    }

    public void setRspMsgId(String rspMsgId) {
        this.rspMsgId = rspMsgId;
    }

    public String getRspStatus() {
        return rspStatus;
    }

    public void setRspStatus(String rspStatus) {
        this.rspStatus = rspStatus;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getAcctPtyId() {
        return acctPtyId;
    }

    public void setAcctPtyId(String acctPtyId) {
        this.acctPtyId = acctPtyId;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWalletPtyId() {
        return walletPtyId;
    }

    public void setWalletPtyId(String walletPtyId) {
        this.walletPtyId = walletPtyId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletLevel() {
        return walletLevel;
    }

    public void setWalletLevel(String walletLevel) {
        this.walletLevel = walletLevel;
    }

    public String getLastUpJrnNo() {
        return lastUpJrnNo;
    }

    public void setLastUpJrnNo(String lastUpJrnNo) {
        this.lastUpJrnNo = lastUpJrnNo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SignJrnDo{" +
                "payDate='" + payDate + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", payTime='" + payTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", instGpTy='" + instGpTy + '\'' +
                ", instDpTy='" + instDpTy + '\'' +
                ", direct='" + direct + '\'' +
                ", manageType='" + manageType + '\'' +
                ", signType='" + signType + '\'' +
                ", signNo='" + signNo + '\'' +
                ", msgSendCode='" + msgSendCode + '\'' +
                ", msgVerifyCode='" + msgVerifyCode + '\'' +
                ", trxStatus='" + trxStatus + '\'' +
                ", trxRetCode='" + trxRetCode + '\'' +
                ", trxRetMsg='" + trxRetMsg + '\'' +
                ", rspMsgId='" + rspMsgId + '\'' +
                ", rspStatus='" + rspStatus + '\'' +
                ", rspCode='" + rspCode + '\'' +
                ", rspMsg='" + rspMsg + '\'' +
                ", acctPtyId='" + acctPtyId + '\'' +
                ", acctType='" + acctType + '\'' +
                ", acctId='" + HiddenUtil.acHidden(acctId) + '\'' +
                ", acctName='" + HiddenUtil.acNameHidden(acctName) + '\'' +
                ", idType='" + idType + '\'' +
                ", idNo='" + HiddenUtil.certIdHidden(idNo) + '\'' +
                ", telephone='" + HiddenUtil.telNoHidden(telephone) + '\'' +
                ", walletPtyId='" + walletPtyId + '\'' +
                ", walletId='" + HiddenUtil.acHidden(walletId) + '\'' +
                ", walletType='" + walletType + '\'' +
                ", walletLevel='" + walletLevel + '\'' +
                ", lastUpJrnNo='" + lastUpJrnNo + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
