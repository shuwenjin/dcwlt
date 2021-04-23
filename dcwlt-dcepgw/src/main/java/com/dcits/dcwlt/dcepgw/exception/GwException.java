/*********************************************
 * Copyright (c) 2019 LI-ECS.
 * All rights reserved.
 * Created on 2019年12月23日
 *
 * Contributors:
 *     ECS - initial implementation
 *********************************************/

package com.dcits.dcwlt.dcepgw.exception;


public class GwException extends RuntimeException{
	private static final long serialVersionUID = 4757383577388804739L;
	String errorCode;
	String errorMsg;

	public static final String CODE_ENCRYPT = "GW-0001";
	public static final String CODE_PACK = "GW-0002";
	public static final String CODE_SIGN = "GW-0003";
	public static final String CODE_CALL = "GW-0004";
	public GwException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public static GwException getInstance(String errorCode, String errorMsg) {
	    return new GwException(errorCode, errorMsg);
	}
	
	public static GwException getInstance(Exception ex) {
        if (ex instanceof GwException) {
            // 标准错误直接返回
            return (GwException)ex;
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
            return GwException.getInstance("999999", errorMsg);
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
