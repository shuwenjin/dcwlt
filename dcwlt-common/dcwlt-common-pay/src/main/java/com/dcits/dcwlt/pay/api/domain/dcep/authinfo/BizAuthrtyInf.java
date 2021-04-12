package com.dcits.dcwlt.pay.api.domain.dcep.authinfo;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.AuthoritySignCodeEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *
 * @desc 业务权限清单
 */
public class BizAuthrtyInf {
    /*
     * 报文编号
     * */
    @NotBlank(message = "报文编号不能为空")
    @Length(max = 15)
    private String MT;
    /*
     * 业务类型编码,业务类型不存在是该字段为空
     * */
    @Length(max = 4)
    private String TrdCtgyCd;
    /*
     * 发起权限标识
     * */
    @NotBlank(message = "发起权限标识不能为空")
    @EnumValue(value = AuthoritySignCodeEnum.class, message = "发起权限标识类型有误")
    private String InitAuthrtySgnCd;
    /*
     * 接收权限标识
     * */
    @NotBlank(message = "接收权限标识不能为空")
    @EnumValue(value = AuthoritySignCodeEnum.class, message = "接收权限标识类型有误")
    private String RcvAuthrtySgnCd;

    @JSONField(name = "MT")
    public String getMT() {
        return MT;
    }

    public void setMT(String MT) {
        this.MT = MT;
    }

    @JSONField(name = "TrdCtgyCd")
    public String getTrdCtgyCd() {
        return TrdCtgyCd;
    }

    public void setTrdCtgyCd(String trdCtgyCd) {
        TrdCtgyCd = trdCtgyCd;
    }

    @JSONField(name = "InitAuthrtySgnCd")
    public String getInitAuthrtySgnCd() {
        return InitAuthrtySgnCd;
    }

    public void setInitAuthrtySgnCd(String initAuthrtySgnCd) {
        InitAuthrtySgnCd = initAuthrtySgnCd;
    }

    @JSONField(name = "RcvAuthrtySgnCd")
    public String getRcvAuthrtySgnCd() {
        return RcvAuthrtySgnCd;
    }

    public void setRcvAuthrtySgnCd(String rcvAuthrtySgnCd) {
        RcvAuthrtySgnCd = rcvAuthrtySgnCd;
    }

    @Override
    public String toString() {
        return "BizAuthrtyInf{" +
                "MT='" + MT + '\'' +
                ", TrdCtgyCd='" + TrdCtgyCd + '\'' +
                ", InitAuthrtySgnCd=" + InitAuthrtySgnCd +
                ", RcvAuthrtySgnCd=" + RcvAuthrtySgnCd +
                '}';
    }
}
