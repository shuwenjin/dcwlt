package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author
 * @date  2021/1/5
 * @version 1.0.0
 * <p>重发申请请求报文</p>
 */
public class ReSendApyReqDTO extends ECNYReqBody {

    /**
     * 报文编号
     * "dcep.711.001.01","机构对账汇总核对报文编号"
     * "dcep.713.001.01","资金调整汇总核对报文编号"
     */
    @NotBlank(message = "报文编号不能为空")
    @Pattern(regexp = "dcep.71[13].001.01",message = "报文编号错误")
    @Length(max = 15)
    private String msgType;



    /**
     * 当申请报文为机构对账汇总核对报文编号（dcep.711.001.01）时填写对账日期
     */
    private String checkDate;

    /**
     * 当申请报文为机构对账汇总核对报文编号（dcep.711.001.01）时填写对账日期或者交易批次号
     */
    private String batchId;

    /**
     *  当申请报文编号为资金调整汇总核对报文编号（dcep.713.001.01）时，填写”清算日期“
     */
    private String clearDate;

    @JSONField(name = "msgType")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @JSONField(name = "checkDate")
    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    @JSONField(name = "batchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "clearDate")
    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    @Override
    public String toString() {
        return "ReSendApyReqDTO{" +
                "msgType='" + msgType + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", batchId='" + batchId + '\'' +
                ", clearDate='" + clearDate + '\'' +
                '}';
    }
}
