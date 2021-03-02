package com.dcits.dcwlt.pay.api.domain.dcep.convert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


/**
 * 兑出响应
 *
 *
 */
public class ConvertRspDTO extends DCEPRspBody {

    private ConvertRsp convertRsp;

    public ConvertRspDTO(GrpHdr grpHdr, OrgnlGrpHdr orgnlGrpHdr, BankAttRspsnInf rspsnInf) {
        ConvertRsp convertRsp = new ConvertRsp();
        convertRsp.setGrpHdr(grpHdr);
        convertRsp.setOrgnlGrpHdr(orgnlGrpHdr);
        convertRsp.setRspsnInf(rspsnInf);
        this.convertRsp = convertRsp;
    }

    @JSONField(name = "ConvertRsp")
    public ConvertRsp getConvertRsp() {
        return convertRsp;
    }

    public void setConvertRsp(ConvertRsp convertRsp) {
        this.convertRsp = convertRsp;
    }

    @Override
    public String toString() {
        return "ConvertRspDTO{" +
                "convertRsp=" + convertRsp +
                '}';
    }
}
