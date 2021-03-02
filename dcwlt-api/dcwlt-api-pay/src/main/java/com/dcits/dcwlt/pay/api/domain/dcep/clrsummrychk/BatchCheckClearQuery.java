package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @desc 资金调整汇总核对查询
 */
public class BatchCheckClearQuery {
    @Length(max = 8)
    private String clearDate;           //清算日期

    @Length(max = 35)
    private String msgId;               //核对报文标识号

    @Length(max = 35)
    private String clearMsgId;          //清算报文标识号

    @Length(max = 13)
    private String batchId;             //批次号

    @NotNull
    @Length(max = 4)
    @Valid
    private String count;               //查询条数

    @NotNull
    @Length(max = 1)
    @Valid
    private String queryPageFlag;        //上下翻页操作标识

    @JSONField(name = "ClearDate")
    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    @JSONField(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JSONField(name = "ClearMsgId")
    public String getClearMsgId() {
        return clearMsgId;
    }

    public void setClearMsgId(String clearMsgId) {
        this.clearMsgId = clearMsgId;
    }

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "Count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @JSONField(name = "QueryPageFlag")
    public String getQueryPageFlag() {
        return queryPageFlag;
    }

    public void setQueryPageFlag(String queryPageFlag) {
        this.queryPageFlag = queryPageFlag;
    }

    @Override
    public String toString() {
        return "BatchCheckClearQuery{" +
                "clearDate='" + clearDate + '\'' +
                ", msgId='" + msgId + '\'' +
                ", clearMsgId='" + clearMsgId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", count='" + count + '\'' +
                ", queryPageFlag='" + queryPageFlag + '\'' +
                '}';
    }
}
