package com.dcits.dcwlt.pay.api.domain.dcep.cert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;

/**
 * 数字证书绑定通知报文请求报文
 *
 */
public class CertReqDTO extends DCEPReqBody {

    @Valid
    private CertNtfctn certNtfctn;

    @JSONField(name = "CertNtfctn")
    public CertNtfctn getCertNtfctn() {
        return certNtfctn;
    }

    public void setCertNtfctn(CertNtfctn certNtfctn) {
        this.certNtfctn = certNtfctn;
    }

    @Override
    public String toString() {
        return "CertReqDTO{" +
                "certNtfctn=" + certNtfctn +
                '}';
    }
}
