package com.dcits.dcwlt.pay.api.domain.ecny.dspt;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:差错贷记调账接口请求
 */
public class DsptChnlReqDTO extends ECNYReqBody {

    /**
     * 平台日期
     */
    @Length(max = 8)
    private String payDate;

    /**
     * 平台流水
     */
    @Length(max = 32)
    private String paySerno;

    /**
     * 报文标识号
     */
    private String msgId;

    /**
     * 对账标识
     */
    private String checkStatus;

    /**
     * 差错类型
     */
    @Length(max = 4)
    private String operType;

    /**
     * 差错贷记调整原因码
     */
    @Length(max = 4)
    private String disputeReasonCode;

    /**
     * 差错原因说明
     */
    @Length(max = 64)
    private String disputeReason;

    /**
     * 原发起机构
     */
    private String instgPty;

    /**
     * 报文编号
     */
    private String msgTp;

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

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getDisputeReason() {
        return disputeReason;
    }

    public void setDisputeReason(String disputeReason) {
        this.disputeReason = disputeReason;
    }

    public String getDisputeReasonCode() {
        return disputeReasonCode;
    }

    public void setDisputeReasonCode(String disputeReasonCode) {
        this.disputeReasonCode = disputeReasonCode;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getInstgPty() {
        return instgPty;
    }

    public void setInstgPty(String instgPty) {
        this.instgPty = instgPty;
    }

    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    @Override
    public String toString() {
        return "DsptChnlReqDTO{" +
                "payDate='" + payDate + '\'' +
                ", paySerno='" + paySerno + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", operType='" + operType + '\'' +
                ", disputeReasonCode='" + disputeReasonCode + '\'' +
                ", disputeReason='" + disputeReason + '\'' +
                ", msgId='" + msgId + '\'' +
                ", instgPty='" + instgPty + '\'' +
                ", msgTp='" + msgTp + '\'' +
                '}';
    }
}
