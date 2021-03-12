package com.dcits.dcwlt.pay.api.domain.dcep.check;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;

/**
 * @author maozewu
 * @Date 2021/1/26
 * @Version 1.0
 * Description:对账不平结果重对账服务化接口请求
 */
public class CheckWrongCheckReqDTO extends ECNYReqBody {
    private String applyType;        //申请类型
    private String batchId;          //批次号
    private String checkDay;         //对账日期

    public CheckWrongCheckReqDTO() {
    }

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(String checkDay) {
		this.checkDay = checkDay;
	}

	@Override
	public String toString() {
		return "CheckWrongCheckReqDTO [applyType=" + applyType + ", batchId=" + batchId + ", checkDay=" + checkDay
				+ "]";
	}

    
}
