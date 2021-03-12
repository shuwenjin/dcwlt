package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;


/**
 * 兑回业务响应报文
 *
 */
public class ReconvertRspDTO extends DCEPRspBody {

    private ReconvertRsp reconvertRsp;

    public ReconvertRspDTO(GrpHdr grpHdr, OrgnlGrpHdr orgnlGrpHdr, RspsnInf rspsnInf) {
        ReconvertRsp reconvertRsp = new ReconvertRsp();
        reconvertRsp.setGrpHdr(grpHdr);
        reconvertRsp.setOrgnlGrpHdr(orgnlGrpHdr);
        reconvertRsp.setRspsnInf(rspsnInf);
        this.reconvertRsp = reconvertRsp;
    }

    @JSONField(name = "ReconvertRsp")
    public ReconvertRsp getReconvertRsp() {
        return reconvertRsp;
    }

    public void setReconvertRsp(ReconvertRsp reconvertRsp) {
        this.reconvertRsp = reconvertRsp;
    }

    @Override
    public String toString() {
        return "ReconvertRspDTO{" +
                "reconvertRsp=" + reconvertRsp +
                '}';
    }
}
