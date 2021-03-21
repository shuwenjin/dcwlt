package com.dcits.dcwlt.common.pay.exception;


public class EventDealException extends RuntimeException {


    private static final long serialVersionUID = 6577386989048409669L;

    private String errorMsg;
    private String errorCode;
    private String currentData;

    public EventDealException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.currentData = "";
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public EventDealException(String errorCode, String errorMsg, Throwable ex) {
        super(errorMsg, ex);
        this.currentData = "";
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public EventDealException(EventDealError error) {
        this(error.getErrorCode(), error.getErrorMsg());
    }

    public EventDealException(EventDealError error, Throwable ex) {
        this(error.getErrorCode(), error.getErrorMsg(), ex);
    }

}
