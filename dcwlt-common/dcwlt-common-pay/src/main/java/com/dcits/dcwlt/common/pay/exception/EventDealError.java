package com.dcits.dcwlt.common.pay.exception;

/**
 * 事件处理错误枚举
 * @author liuyuanhui
 *
 */
public enum EventDealError {
	
	/**
	 * 成功
	 */
	SUCC("00", "成功"),
	
	/**
	 * 参数错误
	 */
	PARAM_ERR("01", "参数错误"),
	
	/**
	 * 关联交易查询异常
	 */
	REL_ERR("02", "关联交易查询异常"),
	
	/**
	 * 系统异常
	 */
	SYS_ERR("03", "系统异常"),
	
	/**
	 * 已达到最大处理次数
	 */
	REACH_MAX_DEAL("04", "已达到最大处理次数"),
	
	/**
	 * 手工处理
	 */
	MANU_DEAL("05", "手工处理");

	EventDealError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	private String errorCode;
	private String errorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
