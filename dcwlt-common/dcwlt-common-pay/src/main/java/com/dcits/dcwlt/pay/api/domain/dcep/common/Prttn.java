package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;

/**
 * 报文分片组件
 *
 *
 * @date 2020/12/28
 */
public class Prttn {

    @NotBlank
    @DecimalMax(value = "99999999", message = "总记录数不能9999999")
    @JSONField(name = "TtlNb")
    private String ttlNb;     //总记录数

    @NotBlank
    @DecimalMax(value = "99999999", message = "本报文启始记录序号最大99999999")
    @JSONField(name = "StartNb")
    private String startNb;   //本报文记录启始序号

    @NotBlank
    @DecimalMax(value = "99999999", message = "本报文记录截止序号最大99999999")
    @JSONField(name = "EndNb")
    private String endNb;     //本报文记录截止序号

    public String getTtlNb() {
        return ttlNb;
    }

    public void setTtlNb(String ttlNb) {
        this.ttlNb = ttlNb;
    }

    public String getStartNb() {
        return startNb;
    }

    public void setStartNb(String startNb) {
        this.startNb = startNb;
    }

    public String getEndNb() {
        return endNb;
    }

    public void setEndNb(String endNb) {
        this.endNb = endNb;
    }

    @Override
    public String toString() {
        return "Prttn{" +
                "ttlNb=" + ttlNb +
                ", startNb=" + startNb +
                ", endNb=" + endNb +
                '}';
    }
}
