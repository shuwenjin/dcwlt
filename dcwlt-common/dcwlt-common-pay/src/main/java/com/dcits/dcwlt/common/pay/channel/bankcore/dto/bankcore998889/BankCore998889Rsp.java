/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年4月3日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889;


import com.dcits.dcwlt.common.pay.channel.bankcore.dto.IBankCoreBody;

public class BankCore998889Rsp implements IBankCoreBody {

	private String errorCode       ;   // 核心返回码       M
	
	private String errorMsg    ;   // 核心返回信息   M
	
	private String reqCoreDate    ;    //主机请求日期
	
	private String reqCoreSerno   ;   // 主机请求流水       M

	private String rspCoreDate    ;   //主机返回日期
	
	private String rspCoreSerno   ;   // 主机返回流水       M
	
	private String coreStatus ;   //核心返回状态

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

	public String getReqCoreSerno() {
		return reqCoreSerno;
	}

	public void setReqCoreSerno(String reqCoreSerno) {
		this.reqCoreSerno = reqCoreSerno;
	}

	public String getRspCoreSerno() {
		return rspCoreSerno;
	}

	public void setRspCoreSerno(String rspCoreSerno) {
		this.rspCoreSerno = rspCoreSerno;
	}

	public String getCoreStatus() {
		return coreStatus;
	}

	public void setCoreStatus(String coreStatus) {
		this.coreStatus = coreStatus;
	}
    
	public String getReqCoreDate() {
		return reqCoreDate;
	}

	public void setReqCoreDate(String reqCoreDate) {
		this.reqCoreDate = reqCoreDate;
	}

	public String getRspCoreDate() {
		return rspCoreDate;
	}

	public void setRspCoreDate(String rspCoreDate) {
		this.rspCoreDate = rspCoreDate;
	}

	@Override
	public String toString() {
		return "BankCore998889Rsp [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", reqCoreDate=" + reqCoreDate
				+ ", reqCoreSerno=" + reqCoreSerno + ", rspCoreDate=" + rspCoreDate + ", rspCoreSerno=" + rspCoreSerno
				+ ", coreStatus=" + coreStatus + "]";
	}


	
	

	
}
