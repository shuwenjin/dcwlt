package com.dcits.dcwlt.pay.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

/**
 * @desc 监控实体
 */
public class MonitorDO {
    @Length(max = 8)
    private String exceptDate;          //异常登记日期

    @Length(max = 32)
    private String exceptSerNO;         //异常登记流水

    @Length(max = 19)
    private String exceptTime;         //异常交易时间

    @Length(max = 35)
    private String exceptScenario;      //异常交易场景

    @Length(max = 120)
    private String excepParams;         //异常参数

    @Length(max = 1200)
    private String excepContext;        //异常内容

    @Length(max = 8)
    private String lastUpDate;          //异常事件最后处理日期

    @Length(max = 6)
    private String lastUpTime;          //异常事件最后处理时间

    @JSONField
    public String getExceptDate() {
        return exceptDate;
    }

    public void setExceptDate(String exceptDate) {
        this.exceptDate = exceptDate;
    }

    @JSONField
    public String getExceptSerNO() {
        return exceptSerNO;
    }

    public void setExceptSerNO(String exceptSerNO) {
        this.exceptSerNO = exceptSerNO;
    }

    @JSONField
    public String getExceptTime() {
        return exceptTime;
    }

    public void setExceptTime(String exceptTime) {
        this.exceptTime = exceptTime;
    }

    @JSONField
    public String getExceptScenario() {
        return exceptScenario;
    }

    public void setExceptScenario(String exceptScenario) {
        this.exceptScenario = exceptScenario;
    }

    @JSONField
    public String getExcepParams() {
        return excepParams;
    }

    public void setExcepParams(String excepParams) {
        this.excepParams = excepParams;
    }

    @JSONField
    public String getExcepContext() {
        return excepContext;
    }

    public void setExcepContext(String excepContext) {
        this.excepContext = excepContext;
    }

    @JSONField
    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    @JSONField
    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    @Override
    public String toString() {
        return "MonitorDO{" +
                "exceptDate='" + exceptDate + '\'' +
                ", exceptSerNO='" + exceptSerNO + '\'' +
                ", exceptTime='" + exceptTime + '\'' +
                ", exceptScenario='" + exceptScenario + '\'' +
                ", excepParams='" + excepParams + '\'' +
                ", excepContext='" + excepContext + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
