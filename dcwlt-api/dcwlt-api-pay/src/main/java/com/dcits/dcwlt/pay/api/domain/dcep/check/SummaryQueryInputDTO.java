package com.dcits.dcwlt.pay.api.domain.dcep.check;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author weimeiyuan
 * @Date 2021/1/16 9:55
 * @Version 1.0
 * Description:汇总对账查询条件输入
 */
public class SummaryQueryInputDTO extends ECNYReqBody {
    //平台日期
    @NotNull(message = "平台日期不能为空")
    @Length(max = 8)
    private String payDate;
    //交易批次号
    @Length(max = 13)
    private String batchId;
    //报文标识号
    @Length(max = 15)
    private String msgType;
    //业务状态
    @Length(max = 4)
    private String msgBizStatus;
    /**
     * 对账标识
     * SAME：对平
     * MORE：行内大额多
     * LESS：核对报文多
     * DIFF：要素不符
     * EXPT: 状态不符，即异常
     * INIT：未对账
     */
    @Length(max = 4)
    private String checkStatus;
    //查询条数 -------不超过30条
    @NotNull(message = "查询条数不能为空")
    @Length(max = 4)
    private String count;
    //上下翻页操作---0首页，1上翻，2下翻，只按升序排序
    @NotNull(message = "上下翻页操作不能为空")
    @Length(max = 1)
    private String queryPageFlag;
    //翻页主键
    @Length(max = 32)
    private String queryPageKey;

    @JSONField(name = "payDate")
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
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

    @JSONField(name = "checkStatus")
    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    @JSONField(name = "count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @JSONField(name = "queryPageFlag")
    public String getQueryPageFlag() {
        return queryPageFlag;
    }

    public void setQueryPageFlag(String queryPageFlag) {
        this.queryPageFlag = queryPageFlag;
    }

    @JSONField(name="queryPageKey")
    public String getQueryPageKey() {
        return queryPageKey;
    }

    public void setQueryPageKey(String queryPageKey) {
        this.queryPageKey = queryPageKey;
    }

    @Override
    public String toString() {
        return "SummaryQueryInputDTO{" +
                "payDate='" + payDate + '\'' +
                ", batchId='" + batchId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgBizStatus='" + msgBizStatus + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", count='" + count + '\'' +
                ", queryPageFlag='" + queryPageFlag + '\'' +
                ", queryPageKey='" + queryPageKey + '\'' +
                '}';
    }
}
