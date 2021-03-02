package com.dcits.dcwlt.pay.api.domain.dcep.authinfo;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;
import java.util.List;

/**
 *
 * @desc 业务权限
 */
public class AuthrtyChngNtfctn {
    /*
     * 业务头组件
     * */
    @Valid
    private GrpHdr grpHdr;
    /*
     * 参与机构权限清单
     * */
    @Valid
    private List<AuthrtyInf> authrtyInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "AuthrtyInf")
    public List<AuthrtyInf> getAuthrtyInf() {
        return authrtyInf;
    }

    public void setAuthrtyInf(List<AuthrtyInf> authrtyInf) {
        this.authrtyInf = authrtyInf;
    }

    @Override
    public String toString() {
        return "AuthrtyChngNtfctn{" +
                "grpHdr=" + grpHdr +
                ", authrtyInf=" + authrtyInf +
                '}';
    }
}
