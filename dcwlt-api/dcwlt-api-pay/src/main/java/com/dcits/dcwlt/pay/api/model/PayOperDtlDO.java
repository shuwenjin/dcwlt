package com.dcits.dcwlt.pay.api.model;

public class PayOperDtlDO {
    private String payDate;          //平台日期
    private String paySerno;         //平台流水
    private String operDate;         //操作日期
    private String operSerno;        //操作流水
    private String operStep;         //操作步骤
    private String operStatus;       //操作状态
    private String operTime;         //操作时间
    private String operMicroSecond;  //操作微秒
    private String operRetCode;      //操作处理码
    private String operRetMsg;       //操作处理信息
    private String msgType;          //报文类型
    private String msgId;            //报文流水
    private String tradeCode;        //交易代码
    private String logFileName;      //日志文件名称
    private String lastUpdate;       //最后更新日期
    private String lastUptime;       //最后更新时间
    private String lastMicroSecond;   //最后更新时间毫秒

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

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getOperSerno() {
        return operSerno;
    }

    public void setOperSerno(String operSerno) {
        this.operSerno = operSerno;
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

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public String getOperMicroSecond() {
        return operMicroSecond;
    }

    public void setOperMicroSecond(String operMicroSecond) {
        this.operMicroSecond = operMicroSecond;
    }

    public String getOperRetCode() {
        return operRetCode;
    }

    public void setOperRetCode(String operRetCode) {
        this.operRetCode = operRetCode;
    }

    public String getOperRetMsg() {
        return operRetMsg;
    }

    public void setOperRetMsg(String operRetMsg) {
        this.operRetMsg = operRetMsg;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUptime() {
        return lastUptime;
    }

    public void setLastUptime(String lastUptime) {
        this.lastUptime = lastUptime;
    }

    public String getLastMicroSecond() {
        return lastMicroSecond;
    }

    public void setLastMicroSecond(String lastMicroSecond) {
        this.lastMicroSecond = lastMicroSecond;
    }

    @Override
    public String toString() {
        return "PayOperdtlDO [" +
                "payDate='" + payDate + '\'' +
                ", paySerno='" + paySerno + '\'' +
                ", operDate='" + operDate + '\'' +
                ", operSerno='" + operSerno + '\'' +
                ", operStep='" + operStep + '\'' +
                ", operStatus='" + operStatus + '\'' +
                ", operTime='" + operTime + '\'' +
                ", operMicroSecond='" + operMicroSecond + '\'' +
                ", operRetCode='" + operRetCode + '\'' +
                ", operRetMsg='" + operRetMsg + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgId='" + msgId + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", logFileName='" + logFileName + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", lastUptime='" + lastUptime + '\'' +
                ", lastMicroSecond='" + lastMicroSecond + '\'' +
                "]";
    }
}
