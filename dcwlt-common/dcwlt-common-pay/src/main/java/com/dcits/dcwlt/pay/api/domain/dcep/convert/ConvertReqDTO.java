package com.dcits.dcwlt.pay.api.domain.dcep.convert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 兑出请求报文
 * 
 *
 *
 */
public class ConvertReqDTO extends DCEPReqBody {

	@Valid
	private ConvertReq convertReq;

	@JSONField(name = "ConvertReq")
	public ConvertReq getConvertReq() {
		return convertReq;
	}

	public void setConvertReq(ConvertReq convertReq) {
		this.convertReq = convertReq;
	}

	@Override
	public String toString() {
		return "ConvertReqDTO{" +
				"convertReq=" + convertReq +
				'}';
	}
}
