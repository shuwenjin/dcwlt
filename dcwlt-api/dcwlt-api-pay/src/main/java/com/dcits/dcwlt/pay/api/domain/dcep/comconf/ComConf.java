package com.dcits.dcwlt.pay.api.domain.dcep.comconf;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;

/**
 *
 * @desc 通信级确认
 */
public class ComConf {
    /*
     * 通信级确认信息
     * */
    @Valid
    private ConfInf confInf;


    @JSONField(name = "ConfInf")
    public ConfInf getConfInf() {
        return confInf;
    }

    public void setConfInf(ConfInf confInf) {
        this.confInf = confInf;
    }


    @Override
    public String toString() {
        return "ComConf{" +
                "confInf=" + confInf +
                '}';
    }
}
