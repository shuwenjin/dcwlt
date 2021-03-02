/*********************************************
 * Copyright (c) 2019 LI-ECS.
 * All rights reserved.
 * Created on 2019年12月23日
 *
 * Contributors:
 *     ECS - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.exception;

/**
 * 平台级错误码
 *
 */
public class TransError {
	
	/**
	 *	成功返回码 
	 */
	public static final TransError SUCCESS = new TransError("00000000","成功");
	public static final TransError SYSTEM_ERROR = new TransError("BE999999","系统异常");
	public static final TransError OTHER_BUSI_ERROR = new TransError("BE999998","其他业务异常");
	public static final TransError DATABASE_EXECUTE_ERROR = new TransError("BE000001", "数据库异常");
	public static final TransError LOADCLASS_ERROR = new TransError("BE000002", "加载class异常");
	public static final TransError SM3_ENCRYPT_ERROR = new TransError("BE000003", "获取SM3散列值失败");
	public static final TransError SOCKET_CONN_ERROR = new TransError("BE000004","socket连接异常");
	public static final TransError SOCKET_SEND_ERROR = new TransError("BE000005","socket发送异常");
	public static final TransError SOCKET_RECEIVE_ERROR = new TransError("BE000006","socket接收异常");
	public static final TransError AMOUNT_ERROR = new TransError("BE000007", "金额非法");
	public static final TransError IO_CHECK_ERROR = new TransError("BE000008", "IO校验异常");
	
	private String errorCode;
	private String errorMsg;

	public TransError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
