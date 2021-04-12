package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 * 兑出请求报文
 * 
 *
 *
 */
public class PayConvertReqDTO extends DCEPReqBody {

	@Valid
	private PayConvertReq convertReq;

	public PayConvertReqDTO(GrpHdr grpHdr, TrxInf trxInf, DbtrInf dbtrInf, CdtrInf cdtrInf) {
		PayConvertReq convertReq = new PayConvertReq();
		convertReq.setGrpHdr(grpHdr);
		convertReq.setTrxInf(trxInf);
		convertReq.setDbtrInf(dbtrInf);
		convertReq.setCdtrInf(cdtrInf);
		this.convertReq = convertReq;
	}

	public PayConvertReqDTO() {

	}

	@JSONField(name = "ConvertReq")
	public PayConvertReq getConvertReq() {
		return convertReq;
	}

	public void setConvertReq(PayConvertReq convertReq) {
		this.convertReq = convertReq;
	}

	@Override
	public String toString() {
		return "PayConvertReqDTO{" +
				"payConvertReq=" + convertReq +
				'}';
	}
}
