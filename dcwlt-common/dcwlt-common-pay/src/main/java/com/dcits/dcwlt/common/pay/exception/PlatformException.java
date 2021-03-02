package com.dcits.dcwlt.common.pay.exception;


/**
 * @Time 2020/9/17 13:53
 * @Version 1.0
 * Description:平台异常类
 */
public class PlatformException extends RuntimeException {
    private static final long serialVersionUID = 1387324682256522262L;
    private String errorMsg;
    private String errorCode;
    private String currentData;

    public PlatformException() {
        this.currentData = "";
    }

    public PlatformException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.currentData = "";
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public PlatformException(String errorCode, String errorMsg, Throwable ex) {
        super(errorMsg, ex);
        this.currentData = "";
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }


    public PlatformException(PlatformError error) {
        this(error.getErrorCode(), error.getErrorMsg());
    }

    public PlatformException(PlatformError error, Throwable ex) {
        this(error.getErrorCode(), error.getErrorMsg(), ex);
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getCurrentData() {
        return this.currentData;
    }

    public void setCurrentData(String curData) {
        this.currentData = curData;
    }

    public static PlatformException getInstance(String errorCode, String errorMsg) {
        return new PlatformException(errorCode, errorMsg);
    }

    public static PlatformException getInstance(Exception ex) {
        if (ex instanceof PlatformException) {
            // 标准错误直接返回
            return (PlatformException) ex;
        } else {
            // 返回系统错误
            String errorMsg = ex.getMessage();
            if (errorMsg == null) {
                errorMsg = ex.toString();
            }
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 200);
            }
            return PlatformException.getInstance(PlatformError.SYSTEM_ERROR.getErrorCode(), errorMsg);
        }
    }
}
