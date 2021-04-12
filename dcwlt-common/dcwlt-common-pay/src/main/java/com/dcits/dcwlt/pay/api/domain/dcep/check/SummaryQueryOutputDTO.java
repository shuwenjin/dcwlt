package com.dcits.dcwlt.pay.api.domain.dcep.check;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.page.PageResult;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * @author
 * @Date 2021/1/14 16:58
 * @Version 1.0
 * Description:输出对账汇总查询实体
 */
public class SummaryQueryOutputDTO extends PageResult {
    //平台日期
    @Length(max = 8)
    private String payDate;
    //平台流水
    @Length(max = 32)
    private String paySerNo;
    //报文标识号
    @Length(max = 35)
    private String msgId;
    //交易批次号
    @Length(max = 13)
    private String batchId;
    //报文编号
    @Length(max = 15)
    private String msgType;
    //业务状态
    @Length(max = 4)
    private String msgBizStatus;
    //总笔数
    @Length(max = 15)
    private String msgCountNum;
    //总金额
    @Length(max = 18)
    private String msgCountAmt;
    //付款笔数
    @Length(max = 15)
    private String msgDbitCountNum;
    //付款金额
    @Length(max = 18)
    private String msgDbitCountAmt;
    //收款笔数
    @Length(max = 15)
    private String msgCdtCountNum;
    //收款金额
    @Length(max = 18)
    private String msgCrdtCountAmt;
    //对账标识
    @Length(max = 4)
    private String checkStatus;
    @Valid
    private SummaryQueryInputDTO summaryQueryInputDTO;

    public SummaryQueryInputDTO getSummaryQueryInputDTO() {
        return summaryQueryInputDTO;
    }

    public void setSummaryQueryInputDTO(SummaryQueryInputDTO summaryQueryInputDTO) {
        this.summaryQueryInputDTO = summaryQueryInputDTO;
    }

    @JSONField(name = "payDate")
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @JSONField(name = "paySerNo")
    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    @JSONField(name = "msgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JSONField(name = "batchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "msgType")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @JSONField(name = "msgBizStatus")
    public String getMsgBizStatus() {
        return msgBizStatus;
    }

    public void setMsgBizStatus(String msgBizStatus) {
        this.msgBizStatus = msgBizStatus;
    }

    @JSONField(name = "msgCountNum")
    public String getMsgCountNum() {
        return msgCountNum;
    }

    public void setMsgCountNum(String msgCountNum) {
        this.msgCountNum = msgCountNum;
    }

    @JSONField(name = "msgCountAmt")
    public String getMsgCountAmt() {
        return msgCountAmt;
    }

    public void setMsgCountAmt(String msgCountAmt) {
        this.msgCountAmt = msgCountAmt;
    }

    @JSONField(name = "msgDbitCountNum")
    public String getMsgDbitCountNum() {
        return msgDbitCountNum;
    }

    public void setMsgDbitCountNum(String msgDbitCountNum) {
        this.msgDbitCountNum = msgDbitCountNum;
    }

    @JSONField(name = "msgDbitCountAmt")
    public String getMsgDbitCountAmt() {
        return msgDbitCountAmt;
    }

    public void setMsgDbitCountAmt(String msgDbitCountAmt) {
        this.msgDbitCountAmt = msgDbitCountAmt;
    }

    @JSONField(name = "msgCrdtCountNum")
    public String getMsgCdtCountNum() {
        return msgCdtCountNum;
    }

    public void setMsgCdtCountNum(String msgCdtCountNum) {
        this.msgCdtCountNum = msgCdtCountNum;
    }

    @JSONField(name = "msgCrdtCountAmt")
    public String getMsgCrdtCountAmt() {
        return msgCrdtCountAmt;
    }

    public void setMsgCrdtCountAmt(String msgCrdtCountAmt) {
        this.msgCrdtCountAmt = msgCrdtCountAmt;
    }

    @JSONField(name = "checkStatus")
    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return "SummaryQueryOutputDTO{" +
                ", payDate='" + payDate + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", msgId='" + msgId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgBizStatus='" + msgBizStatus + '\'' +
                ", msgCountNum='" + msgCountNum + '\'' +
                ", msgCountAmt='" + msgCountAmt + '\'' +
                ", msgDbitCountNum='" + msgDbitCountNum + '\'' +
                ", msgDbitCountAmt='" + msgDbitCountAmt + '\'' +
                ", msgCdtCountNum='" + msgCdtCountNum + '\'' +
                ", msgCrdtCountAmt='" + msgCrdtCountAmt + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", summaryQueryInputDTO=" + summaryQueryInputDTO +
                '}';
    }
}
