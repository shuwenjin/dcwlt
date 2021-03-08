/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年3月31日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.bankcore998889;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.online.lsfk43.IBankCoreBody;

/**
 * 
 * @author luojie03
 *
 */
public class BankCore998889Req implements IBankCoreBody {

	@JSONField(name = "REQ_SYS_ID")
	private String reqSysId; 			// 外围系统标识

	@JSONField(name = "REQ_SYS_DATE")
	private String reqSysDate; 				// 交易日期

	@JSONField(name = "REQ_SYS_JRN")
	private String reqSysJrn; 			// 外围系统交易流水号

	@JSONField(name = "CAN_RESN")
	private String canResn;			// 冲正原因

	public String getReqSysId() {
		return reqSysId;
	}

	public void setReqSysId(String reqSysId) {
		this.reqSysId = reqSysId;
	}

	public String getReqSysDate() {
		return reqSysDate;
	}

	public void setReqSysDate(String reqSysDate) {
		this.reqSysDate = reqSysDate;
	}

	public String getReqSysJrn() {
		return reqSysJrn;
	}

	public void setReqSysJrn(String reqSysJrn) {
		this.reqSysJrn = reqSysJrn;
	}

	public String getCanResn() {
		return canResn;
	}

	public void setCanResn(String canResn) {
		this.canResn = canResn;
	}

	@Override
	public String toString() {
		return "BankCore998889Req [reqSysId=" + reqSysId + ", reqSysDate=" + reqSysDate + ", reqSysJrn=" + reqSysJrn
				+ ", canResn=" + canResn + "]";
	}
	
}
