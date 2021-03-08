package com.dcits.dcwlt.pay.api.domain.dcep.eventBatch;

/**
 * @author zhanguohai
 * @Time 2020/10/29 10:55
 * @Version 1.0
 * Description:异常事件配置表
 */
public class EventConfigDO {
    private String exceptEventCode;               // 异常事件代码
    private String exceptDealMode;                // 异常处理模式
    private String exceptEventTrxCode;            // 异常处理交易码
    private String exceptEventDealMaxCount;       // 异常处理最大尝试次数
    private String exceptEventDealType;           // 异常处理类型
    private String exceptEventDealIntervalMin;    // 处理时间间隔
    private String exceptEventRemark;             // 异常事件描述

    public String getExceptEventCode() {
        return exceptEventCode;
    }

    public void setExceptEventCode(String exceptEventCode) {
        this.exceptEventCode = exceptEventCode;
    }

    public String getExceptDealMode() {
        return exceptDealMode;
    }

    public void setExceptDealMode(String exceptDealMode) {
        this.exceptDealMode = exceptDealMode;
    }

    public String getExceptEventTrxCode() {
        return exceptEventTrxCode;
    }

    public void setExceptEventTrxCode(String exceptEventTrxCode) {
        this.exceptEventTrxCode = exceptEventTrxCode;
    }

    public String getExceptEventDealMaxCount() {
        return exceptEventDealMaxCount;
    }

    public void setExceptEventDealMaxCount(String exceptEventDealMaxCount) {
        this.exceptEventDealMaxCount = exceptEventDealMaxCount;
    }

    public String getExceptEventDealType() {
        return exceptEventDealType;
    }

    public void setExceptEventDealType(String exceptEventDealType) {
        this.exceptEventDealType = exceptEventDealType;
    }

    public String getExceptEventDealIntervalMin() {
        return exceptEventDealIntervalMin;
    }

    public void setExceptEventDealIntervalMin(String exceptEventDealIntervalMin) {
        this.exceptEventDealIntervalMin = exceptEventDealIntervalMin;
    }

    public String getExceptEventRemark() {
        return exceptEventRemark;
    }

    public void setExceptEventRemark(String exceptEventRemark) {
        this.exceptEventRemark = exceptEventRemark;
    }

    @Override
    public String toString() {
        return "EventConfigDO [exceptEventCode=" + exceptEventCode + ", exceptDealMode=" + exceptDealMode
                + ", exceptEventTrxCode=" + exceptEventTrxCode + ", exceptEventDealMaxCount=" + exceptEventDealMaxCount
                + ", exceptEventDealType=" + exceptEventDealType + ", exceptEventDealIntervalMin="
                + exceptEventDealIntervalMin + ", exceptEventRemark=" + exceptEventRemark + "]";
    }

}
