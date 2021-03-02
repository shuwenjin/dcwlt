package com.dcits.dcwlt.pay.api.domain.dcep.party;







import com.dcits.dcwlt.common.pay.enums.PartyTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.chngctrl.ChngCtrl;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 运营机构变更信息
 *
 *
 * @date 2020/12/28
 */
@Validated
public class Party {

    @Valid
    @NotNull
    private ChngCtrl chngCtrl;          //数组变更组件

    private PartyTpCdEnum ptyTp;        //机构类型

    @NotBlank
    @Size(max=14,message="机构编码机构编码14位")
    private String ptyId;               //机构编码

    @NotBlank
    @Size(max = 60, message = "机构名称最大60位")
    private String ptyNm;               //机构名称

    @NotBlank
    @Size(max = 6, message = "机构标识最大6位")
    private String ptyAli;              //机构标识

    @Valid
    @NotNull
    private StatusTpCdEnum ptySts;      //机构状态

    @NotBlank
    @Size(max = 60, message = "联系人最大60位")
//    @Sensitive(type = FilterType.CI_NAME)
    private String contact;             //联系人

    @NotBlank
    @Size(max = 30, message = "电话最大30位")
//    @Sensitive(type = FilterType.TEL_NO)
    private String tel;                 //电话

    @NotBlank
    @Email(message = "不符合邮件格式")
    @Size(max = 256, message = "邮件最大256位")
//    @Sensitive(type = FilterType.EMAIL)
    private String mail;                //邮件

    @NotBlank
    @Size(max = 30, message = "传真号最大30位")
    private String fax;                 //传真号

    public ChngCtrl getChngCtrl() {
        return chngCtrl;
    }

    public void setChngCtrl(ChngCtrl chngCtrl) {
        this.chngCtrl = chngCtrl;
    }

    public PartyTpCdEnum getPtyTp() {
        return ptyTp;
    }

    public void setPtyTp(PartyTpCdEnum ptyTp) {
        this.ptyTp = ptyTp;
    }

    public String getPtyId() {
        return ptyId;
    }

    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
    }

    public String getPtyNm() {
        return ptyNm;
    }

    public void setPtyNm(String ptyNm) {
        this.ptyNm = ptyNm;
    }

    public String getPtyAli() {
        return ptyAli;
    }

    public void setPtyAli(String ptyAli) {
        this.ptyAli = ptyAli;
    }

    public StatusTpCdEnum getPtySts() {
        return ptySts;
    }

    public void setPtySts(StatusTpCdEnum ptySts) {
        this.ptySts = ptySts;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String toString() {
        return "ChngInf{" +
                "chngCtrl=" + chngCtrl +
                ", ptyTp=" + ptyTp +
                ", ptyId='" + ptyId + '\'' +
                ", ptyNm='" + ptyNm + '\'' +
                ", ptyAli='" + ptyAli + '\'' +
                ", ptySts=" + ptySts +
                ", contact='" + HiddenUtil.acNameHidden(contact) + '\'' +
                ", tel='" + HiddenUtil.telNoHidden(tel) + '\'' +
                ", mail='" + HiddenUtil.emailHidden(tel) + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
