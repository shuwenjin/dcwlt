package com.dcits.dcwlt.pay.api.domain.dcep.eventBatch;

/**
 * 异常事件响应信息
 * 
 * @author
 *
 */
public class EventDealRspMsg {
	private String exceptEventCode; 	// 异常事件代码
	private String exceptEventSeqNo; 	// 异常事件业务流水
	private String retryFlag;			// 枚举值：Y-重试、N-不重试，应用送重试标识为N则直接落地，不再重试处理，若返回非00000000且重试标识不为N，则发起重试，具体的重试次数以异常事件表配置为准
	private String respCode; 			// 成功为00000000，其他应用自行定义
	private String respMsg; 			// 异常事件响应信息

	public String getRetryFlag() {
		return retryFlag;
	}

	public void setRetryFlag(String retryFlag) {
		this.retryFlag = retryFlag;
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

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	@Override
	public String toString() {
		return "EventDealRspMsg [exceptEventCode=" + exceptEventCode + ", exceptEventSeqNo=" + exceptEventSeqNo
				+ ", retryFlag=" + retryFlag + ", respCode=" + respCode + ", respMsg=" + respMsg + "]";
	}

}
