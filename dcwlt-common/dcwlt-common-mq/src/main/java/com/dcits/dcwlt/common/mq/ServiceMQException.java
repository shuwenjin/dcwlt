package com.dcits.dcwlt.common.mq;

public class ServiceMQException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1320111664884462380L;
	
	
	public ServiceMQException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ServiceMQException(String message) {
        super(message);
    }

}
