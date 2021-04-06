package com.dcits.dcwlt.pay.api.domain.dcep.check;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @Date 2021/1/16 9:55
 * @Version 1.0
 * Description:明细对账结果查询条件输入
 */
public class CheckWrongQueryReqDTO extends ECNYReqBody {
    //平台日期
    @NotNull(message = "平台日期不能为空")
    @Length(max = 8)
    @Valid
    private String payDate;
    //交易批次号
    @Length(max = 13)
    @Valid
    private String batchId;
    //报文编号
    @Length(max = 15)
    @Valid
    private String msgType;
    //报文标识号
    @Length(max = 35)
    @Valid
    private String msgId;
    //对账标识
    @Length(max = 4)
    @Valid
    private String checkStatus;
    //差错处理状态
    @Length(max = 4)
    @Valid
    private String procStatus;
    //查询条数 -------不超过30条
    @NotNull(message = "查询条数不能为空")
    @Length(max = 4)
    @Valid
    private String count;
    //上下翻页操作---0首页，1上翻，2下翻，只按升序排序
    @NotNull(message = "上下翻页操作不能为空")
    @Length(max = 1)
    @Valid
    private String queryPageFlag;
    //翻页主键
    @Length(max = 35)
    @Valid
    private String queryPageKey;
    
//    @JSONField(name = "PAYDATE")
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
    
    @JSONField(name = "msgId")
    public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@JSONField(name = "checkStatus")
    public String getCheckStatus() {
        return checkStatus;
    }
    
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    @JSONField(name = "procStatus")
    public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
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
    
    @JSONField(name = "queryPageKey")
    public String getQueryPageKey() {
		return queryPageKey;
	}

	public void setQueryPageKey(String queryPageKey) {
		this.queryPageKey = queryPageKey;
	}

	@Override
    public String toString() {
        return "CheckDetailQueryInputDTO{" +
                "payDate='" + payDate + '\'' +
                ", batchId='" + batchId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgId='" + msgId + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", procStatus='" + procStatus + '\'' +
                ", count='" + count + '\'' +
                ", queryPageFlag='" + queryPageFlag + '\'' +
                ", queryPageKey='" + queryPageKey + "\''" +
                '}';
    }
}
