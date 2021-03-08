package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;


/**
 * 差错调账响应报文
 */
public class DsptRspDTO extends DCEPRspBody {

    private DsptRsp dsptRsp;

    public static DsptRspDTO getInstance(GrpHdr grpHdr, OrgnlGrpHdr orgnlGrpHdr, RspsnInf rspsnInf) {
        DsptRspDTO dsptRspDTO = new DsptRspDTO();
        DsptRsp dsptRsp = new DsptRsp();
        dsptRsp.setGrpHdr(grpHdr);
        dsptRsp.setOrgnlGrpHdr(orgnlGrpHdr);
        dsptRspDTO.setDsptRsp(dsptRsp);
        return dsptRspDTO;
    }

    @JSONField(name = "DsptRsp")
    public DsptRsp getDsptRsp() {
        return dsptRsp;
    }

    public void setDsptRsp(DsptRsp dsptRsp) {
        this.dsptRsp = dsptRsp;
    }

    @Override
    public String toString() {
        return "DsptRspDTO{" +
                "dsptRsp=" + dsptRsp +
                '}';
    }
}