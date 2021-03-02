package com.dcits.dcwlt.pay.api.domain.dcep.cmonconf;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


import javax.validation.Valid;

/**
 *
 * @desc 通用处理响应体
 */
public class CmonConfDTO extends DCEPRspBody {

    @Valid
    private CmonConf cmonConf;

    @JSONField(name = "CmonConf")
    public CmonConf getCmonConf() {
        return cmonConf;
    }

    public void setCmonConf(CmonConf cmonConf) {
        this.cmonConf = cmonConf;
    }

    @Override
    public String toString() {
        return "CmonConfDTO{" +
                "cmonConf=" + cmonConf +
                '}';
    }
}
