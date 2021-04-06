package com.dcits.dcwlt.common.pay.exception;

import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;

/**
 * 清算批次异常类
 *
 * @author
 */
public class SettleTaskException extends TransException {

	private String errorCode;
	private String errorMsg;

	/**
	 *
	 */
	private static final long serialVersionUID = 1870185530184821479L;

	public SettleTaskException(SettleTaskErrorEnum settleTaskErrorEnum) {
		super(settleTaskErrorEnum.getCode(), settleTaskErrorEnum.getDesc());
		this.errorCode = settleTaskErrorEnum.getCode();
		this.errorMsg = settleTaskErrorEnum.getDesc();
	}

	public SettleTaskException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public SettleTaskException(String errorCode, String errorMsg, Exception cause) {
		this(errorCode, errorMsg);
		this.initCause(cause);
	}

	public static SettleTaskException getError(SettleTaskException error) {
		return new SettleTaskException(error.errorCode, error.errorMsg);
	}


	public SettleTaskException copyException() {
		return new SettleTaskException(errorCode, errorMsg);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String getMessage() {
		return errorCode + "-" + errorMsg;
	}
}
