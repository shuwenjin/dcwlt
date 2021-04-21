package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.pay.enums.LoginOperationTpCdEnum;

/**
 * 非金融报文登记表实体
 *
 *
 * @date 2020/12/29
 */
public class PayTransDtlNonfDO {

    @Excel(name = "报文标识号")
    private String msgId;                           //报文标识号
    @Excel(name = "平台日期")
    private String payDate;                         //平台日期
    @Excel(name = "平台时间")
    private String payTime;                         //平台时间
    @Excel(name = "平台流水")
    private String paySerNo;                        //平台流水
    @Excel(name = "报文编号")
    private String pkgNo;                           //报文编号
    @Excel(name = "报文方向")
    private String drct;                            //报文方向
    @Excel(name = "交易状态")
    private String tradeStatus;                     //交易状态
    @Excel(name = "报文发送时间")
    private String senderDateTime;                  //报文发送时间
    @Excel(name = "发起机构")
    private String instgDrctPty;                    //发起机构
    @Excel(name = "接收机构")
    private String instdDrctPty;                    //接收机构
    @Excel(name = "操作类型")
    private LoginOperationTpCdEnum opterationType;  //操作类型
    @Excel(name = "业务处理状态")
    private String procStatus;                      //业务处理状态
    @Excel(name = "业务拒绝码")
    private String rejectCode;                      //业务拒绝码
    @Excel(name = "业务拒绝信息")
    private String rejectInfo;                      //业务拒绝信息
    @Excel(name = "柜员号")
    private String tlrNo;                           //柜员号
    @Excel(name = "备注")
    private String remark;                          //备注
    @Excel(name = "信息内容")
    private String messageContext;                  //信息内容
    @Excel(name = "最后更新日期")
    private String lastUpDate;                      //最后更新日期
    @Excel(name = "最后更新时间")
    private String lastUpTime;                      //最后更新时间


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public String getDrct() {
        return drct;
    }

    public void setDrct(String drct) {
        this.drct = drct;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getSenderDateTime() {
        return senderDateTime;
    }

    public void setSenderDateTime(String senderDateTime) {
        this.senderDateTime = senderDateTime;
    }

    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    public LoginOperationTpCdEnum getOpterationType() {
        return opterationType;
    }

    public void setOpterationType(LoginOperationTpCdEnum opterationType) {
        this.opterationType = opterationType;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    public String getRejectInfo() {
        return rejectInfo;
    }

    public void setRejectInfo(String rejectInfo) {
        this.rejectInfo = rejectInfo;
    }

    public String getTlrNo() {
        return tlrNo;
    }

    public void setTlrNo(String tlrNo) {
        this.tlrNo = tlrNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
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

    @Override
    public String toString() {
        return "PayTransDtlNonfDO{" +
                "msgId='" + msgId + '\'' +
                ", payDate='" + payDate + '\'' +
                ", payTime='" + payTime + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", pkgNo='" + pkgNo + '\'' +
                ", drct='" + drct + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", senderDateTime='" + senderDateTime + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", opterationType=" + opterationType +
                ", procStatus='" + procStatus + '\'' +
                ", rejectCode='" + rejectCode + '\'' +
                ", rejectInfo='" + rejectInfo + '\'' +
                ", tlrNo='" + tlrNo + '\'' +
                ", remark='" + remark + '\'' +
                ", messageContext='" + messageContext + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
