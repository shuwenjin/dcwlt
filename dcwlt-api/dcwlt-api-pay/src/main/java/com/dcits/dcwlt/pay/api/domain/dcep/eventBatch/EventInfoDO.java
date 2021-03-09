package com.dcits.dcwlt.pay.api.domain.dcep.eventBatch;

/**
 * @author zhanguohai
 * @Time 2020/10/29 10:55
 * @Version 1.0
 * Description:异常事件登记流水表
 */
public class EventInfoDO {
    private String exceptEventDate;				// 异常事件登记日期
    private String exceptEventSerno;			// 异常事件登记流水
    private String exceptEventTime;				// 异常事件登记时间
    private String exceptEventCode;				// 异常事件代码
    private String exceptEventSeqNo;			// 渠道系统流水
    private String exceptEventDealCount;		// 异常事件当前处理次数
    private String exceptEventSysStatus;		// 异常事件处理状态
    private String exceptEventErrorCode;		// 异常处理业务错误码
    private String exceptEventErrorMsg;			// 异常处理业务错误信息
    private String exceptEventDealPath;			// 异常事件的处理路径，登记处理服务器
    private String exceptEventContext;			// 异常事件参数
    private String lastUpDate;					// 异常事件最后处理日期
    private String lastUpTime;					// 异常事件最后处理时间

    public String getExceptEventDate() {
        return exceptEventDate;
    }

    public void setExceptEventDate(String exceptEventDate) {
        this.exceptEventDate = exceptEventDate;
    }

    public String getExceptEventSerno() {
        return exceptEventSerno;
    }

    public void setExceptEventSerno(String exceptEventSerno) {
        this.exceptEventSerno = exceptEventSerno;
    }

    public String getExceptEventTime() {
        return exceptEventTime;
    }

    public void setExceptEventTime(String exceptEventTime) {
        this.exceptEventTime = exceptEventTime;
    }

    public String getExceptEventCode() {
        return exceptEventCode;
    }

    public void setExceptEventCode(String exceptEventCode) {
        this.exceptEventCode = exceptEventCode;
    }

    public String getExceptEventSeqNo() {
        return exceptEventSeqNo;
    }

    public void setExceptEventSeqNo(String exceptEventSeqNo) {
        this.exceptEventSeqNo = exceptEventSeqNo;
    }

    public String getExceptEventDealCount() {
        return exceptEventDealCount;
    }

    public void setExceptEventDealCount(String exceptEventDealCount) {
        this.exceptEventDealCount = exceptEventDealCount;
    }

    public String getExceptEventSysStatus() {
        return exceptEventSysStatus;
    }

    public void setExceptEventSysStatus(String exceptEventSysStatus) {
        this.exceptEventSysStatus = exceptEventSysStatus;
    }

    public String getExceptEventErrorCode() {
        return exceptEventErrorCode;
    }

    public void setExceptEventErrorCode(String exceptEventErrorCode) {
        this.exceptEventErrorCode = exceptEventErrorCode;
    }

    public String getExceptEventErrorMsg() {
        return exceptEventErrorMsg;
    }

    public void setExceptEventErrorMsg(String exceptEventErrorMsg) {
        this.exceptEventErrorMsg = exceptEventErrorMsg;
    }

    public String getExceptEventDealPath() {
        return exceptEventDealPath;
    }

    public void setExceptEventDealPath(String exceptEventDealPath) {
        this.exceptEventDealPath = exceptEventDealPath;
    }

    public String getExceptEventContext() {
        return exceptEventContext;
    }

    public void setExceptEventContext(String exceptEventContext) {
        this.exceptEventContext = exceptEventContext;
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
        return "EventInfoDO [exceptEventDate=" + exceptEventDate + ", exceptEventSerno=" + exceptEventSerno
                + ", exceptEventTime=" + exceptEventTime + ", exceptEventCode=" + exceptEventCode
                + ", exceptEventSeqNo=" + exceptEventSeqNo + ", exceptEventDealCount=" + exceptEventDealCount
                + ", exceptEventSysStatus=" + exceptEventSysStatus + ", exceptEventErrorCode=" + exceptEventErrorCode
                + ", exceptEventErrorMsg=" + exceptEventErrorMsg + ", exceptEventDealPath=" + exceptEventDealPath
                + ", exceptEventContext=" + exceptEventContext + ", lastUpDate=" + lastUpDate + ", lastUpTime="
                + lastUpTime + "]";
    }

}
