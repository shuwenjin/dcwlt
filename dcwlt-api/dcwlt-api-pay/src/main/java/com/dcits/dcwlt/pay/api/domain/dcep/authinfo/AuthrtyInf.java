package com.dcits.dcwlt.pay.api.domain.dcep.authinfo;


import com.alibaba.fastjson.annotation.JSONField;

import com.dcits.dcwlt.pay.api.domain.dcep.chngctrl.ChngCtrl;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 * @desc 参与机构权限清单
 */
public class AuthrtyInf {
    /*
     * 数据变更组件
     * */
    @Valid
    private ChngCtrl chngCtrl;
    /*
     * 机构编码
     * */
    @NotBlank(message = "机构编码不能为空")
    @Length(max = 14)
    private String ptcpt;

    /*
     * 业务权限清单
     * */
    @Valid
    private List<BizAuthrtyInf> bizAuthrtyInf;

    @JSONField(name = "ChngCtrl")
    public ChngCtrl getChngCtrl() {
        return chngCtrl;
    }

    public void setChngCtrl(ChngCtrl chngCtrl) {
        this.chngCtrl = chngCtrl;
    }

    @JSONField(name = "Ptcpt")
    public String getPtcpt() {
        return ptcpt;
    }

    public void setPtcpt(String ptcpt) {
        this.ptcpt = ptcpt;
    }

    @JSONField(name = "BizAuthrtyInf")
    public List<BizAuthrtyInf> getBizAuthrtyInf() {
        return bizAuthrtyInf;
    }

    public void setBizAuthrtyInf(List<BizAuthrtyInf> bizAuthrtyInf) {
        this.bizAuthrtyInf = bizAuthrtyInf;
    }

    @Override
    public String toString() {
        return "AuthrtyInf{" +
                "bizAuthrtyInf=" + bizAuthrtyInf +
                ", chngCtrl=" + chngCtrl +
                ", Ptcpt='" + ptcpt + '\'' +
                '}';
    }
}

