package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @desc 自由格式查询实体
 */
public class FreeFrmtQueryDTO extends ECNYReqBody {
    @NotNull
    @Length(max = 8)
    private String payDate;             //平台日期

    @Length(max = 32)
    private String paySerNo;            //平台流水

    @Length(max = 14)
    private String instgDrctPty;        //发起机构

    @Length(max = 14)
    private String instdDrctPty;        //接收机构

    @Length(max = 1)
    private String drct;                //报文方向

    @Length(max = 35)
    private String msgId;               //报文标识号

    @Length(max = 35)
    private String queryPageKey;    //翻页查询主键，翻页时必填 填写msgId

    @NotNull
    @Length(max = 4)
    @Valid
    private String count;               //查询条数

    @NotNull
    @Length(max = 1)
    @Valid
    private String queryPageFlag;       //上下翻页操作标识

    @JSONField(name = "PayDate")
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @JSONField(name = "PaySerNo")
    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    @JSONField(name = "InstgDrctPty")
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    @JSONField(name = "InstdDrctPty")
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    @JSONField(name = "Dcrt")
    public String getDrct() {
        return drct;
    }

    public void setDrct(String drct) {
        this.drct = drct;
    }

    @JSONField(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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

    @JSONField(name = "QueryPageKey")
    public String getQueryPageKey() {
        return queryPageKey;
    }

    public void setQueryPageKey(String queryPageKey) {
        this.queryPageKey = queryPageKey;
    }

    @Override
    public String toString() {
        return "FreeFrmtQueryDTO{" +
                "payDate='" + payDate + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", drct='" + drct + '\'' +
                ", msgId='" + msgId + '\'' +
                ", queryPageKey='" + queryPageKey + '\'' +
                ", count='" + count + '\'' +
                ", queryPageFlag='" + queryPageFlag + '\'' +
                '}';
    }
}
