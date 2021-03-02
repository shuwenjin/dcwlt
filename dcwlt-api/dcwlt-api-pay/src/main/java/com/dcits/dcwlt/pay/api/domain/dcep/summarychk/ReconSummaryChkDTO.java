package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 *
 * @Time 2021/1/3 14:20
 * @Version 1.0
 * Description:机构汇总对账功能报文实体类
 */
public class ReconSummaryChkDTO extends DCEPReqBody {

    @Valid
    private ReconSummaryChk reconSummaryChk;

    @JSONField(name = "ReconSummaryChk")
    public ReconSummaryChk getReconSummaryChk() {
        return reconSummaryChk;
    }

    public void setReconSummaryChk(ReconSummaryChk reconSummaryChk) {
        this.reconSummaryChk = reconSummaryChk;
    }

    @Override
    public String toString() {
        return "ReconSummaryChkDTO{" +
                "reconSummaryChk=" + reconSummaryChk +
                '}';
    }
}
