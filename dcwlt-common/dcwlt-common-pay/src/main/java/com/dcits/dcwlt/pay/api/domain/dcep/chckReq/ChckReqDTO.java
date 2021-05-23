package com.dcits.dcwlt.pay.api.domain.dcep.chckReq;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;

/**
 * HTTP通信级探测报文
 */
public class ChckReqDTO extends DCEPReqBody {

    @Valid
    private CheckReq checkReq;

    public static ChckReqDTO newInstance(String reqNdNm) {
        ChckReqDTO chckReqDTO = new ChckReqDTO();
        CheckReq checkReq = new CheckReq();
        ChckInf chckInf = new ChckInf();
        chckInf.setInstgDrctPty(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        chckInf.setReqDt(DateUtil.formatSeconds());
        chckInf.setReqNdNm(reqNdNm);
        checkReq.setChckInf(chckInf);
        chckReqDTO.setCheckReq(checkReq);
        return chckReqDTO;
    }

    @JSONField(name = "CheckReq")
    public CheckReq getCheckReq() {
        return checkReq;
    }

    public void setCheckReq(CheckReq checkReq) {
        this.checkReq = checkReq;
    }

    @Override
    public String toString() {
        return "ChckReqDTO{" +
                "checkReq=" + checkReq +
                '}';
    }
}
