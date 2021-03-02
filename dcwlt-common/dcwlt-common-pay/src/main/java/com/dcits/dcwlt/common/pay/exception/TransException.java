/*********************************************
 * Copyright (c) 2019 LI-ECS.
 * All rights reserved.
 * Created on 2019年12月23日
 *
 * Contributors:
 *     ECS - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.exception;


public class TransException extends RuntimeException{
	private static final long serialVersionUID = 4757383577388804739L;
	String errorCode;
	String errorMsg;
	public TransException(TransError error) {
		this.errorCode = error.getErrorCode();
		this.errorMsg = error.getErrorMsg();
	}


	public TransException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public static TransException getInstance(String errorCode, String errorMsg) {
	    return new TransException(errorCode, errorMsg);
	}
	
	public static TransException getInstance(Exception ex) {
        if (ex instanceof TransException) {
            // 标准错误直接返回
            return (TransException)ex;
        }
        else {
            // 返回系统错误
            String errorMsg = ex.getMessage();
            if (errorMsg == null) {
                errorMsg = ex.toString();
            }
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 200);
            }
            return TransException.getInstance(TransError.SYSTEM_ERROR.getErrorCode(), errorMsg);
        }
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
