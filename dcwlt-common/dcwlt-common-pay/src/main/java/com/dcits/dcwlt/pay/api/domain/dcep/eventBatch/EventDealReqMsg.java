package com.dcits.dcwlt.pay.api.domain.dcep.eventBatch;

/**
 * 异常事件请求信息
 *
 * @author
 */
public class EventDealReqMsg {
    private String exceptEventCode;        // 异常事件代码
    private String exceptEventMsgTag;      // 异常事件消息标签
    private String exceptEventSeqNo;       // 事件业务流水
    private String exceptEventContext;     // 异常事件请求参数

    public EventDealReqMsg() {
    }

    public EventDealReqMsg(String exceptEventCode) {
        this.exceptEventCode = exceptEventCode;
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

    public String getExceptEventContext() {
        return exceptEventContext;
    }

    public void setExceptEventContext(String exceptEventContext) {
        this.exceptEventContext = exceptEventContext;
    }

    public String getExceptEventMsgTag() {
        return exceptEventMsgTag;
    }

    public void setExceptEventMsgTag(String exceptEventMsgTag) {
        this.exceptEventMsgTag = exceptEventMsgTag;
    }

    @Override
    public String toString() {
        return "EventDealReqMsg [exceptEventCode=" + exceptEventCode + ", exceptEventSeqNo=" + exceptEventSeqNo
                + ", exceptEventContext=" + exceptEventContext + "]";
    }
}
