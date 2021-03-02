package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Null;

/**
 * 期数信息
 *
 *
 * @date 2020/12/28
 */
public class NbInf {
    public static final int MAX_CHNG_NB = 99999999;     //变更期数最大99999999

    @Valid
    @Max(value = 99999999, message = "变更期数最大99999999")
    @JSONField(name = "ChngNb")
    private long chngNb;      //变更期数

    @Valid
    @Null
    @Max(value = 99999999, message = "变更期数最大99999999")
    @JSONField(name = "ChngRcrdNb")
    private Long chngRcrdNb;  //变更记录数目

    public long getChngNb() {
        return chngNb;
    }

    public void setChngNb(long chngNb) {
        this.chngNb = chngNb;
    }

    public Long getChngRcrdNb() {
        return chngRcrdNb;
    }

    public void setChngRcrdNb(Long chngRcrdNb) {
        this.chngRcrdNb = chngRcrdNb;
    }
}
