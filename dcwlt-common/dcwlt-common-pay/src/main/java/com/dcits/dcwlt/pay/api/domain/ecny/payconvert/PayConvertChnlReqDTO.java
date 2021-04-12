package com.dcits.dcwlt.pay.api.domain.ecny.payconvert;

import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class PayConvertChnlReqDTO extends ECNYReqBody {

    @NotBlank(message = "业务类型不能为空")
    @Length(max = 4)
    private String busiType;         //业务类型

    @NotBlank(message = "业务种类编码不能为空")
    @Length(max = 5)
    private String busiKind;         //业务种类

    @NotBlank(message = "交易金额不能为空")
    @Length(max = 18)
    private String amount;           //交易金额

    @NotBlank(message = "币种不能为空")
    @Length(max = 3)
    private String currency;         //币种

    @NotBlank(message = "收款人行号不能为空")
    @Length(max = 14)
    private String payeeBank;        //收款人行号

    @NotBlank(message = "收款人账户账号不能为空")
    @Length(max = 68)
    private String payeeAcct;        //收款人账户账号

    private String walletLevel;      //收款人钱包等级有误

    private String walletType;       //收款人钱包类型有误

    @NotBlank(message = "收款人钱包名称不能为空")
    @Length(max = 60)
    private String walletName;       //收款人钱包类型有误

    @NotBlank(message = "付款人行号不能为空")
    @Length(max = 14)
    private String payerBank;        //付款人行号

    @NotBlank(message = "付款人账户账号不能为空")
    @Length(max = 64)
    private String payerAcct;        //付款人账户账号

    @NotBlank(message = "付款人名称不能为空")
    @Length(max = 240)
    private String payerName;        //付款人名称

    @Length(max = 4)
    private String summary;          //摘要码

    @Length(max = 120)
    private String narraTive;        //附言

    @Length(max = 240)
    private String remark;           //备注

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getBusiKind() {
        return busiKind;
    }

    public void setBusiKind(String busiKind) {
        this.busiKind = busiKind;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayeeBank() {
        return payeeBank;
    }

    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank;
    }

    public String getPayeeAcct() {
        return payeeAcct;
    }

    public void setPayeeAcct(String payeeAcct) {
        this.payeeAcct = payeeAcct;
    }

    public String getWalletLevel() {
        return walletLevel;
    }

    public void setWalletLevel(String walletLevel) {
        this.walletLevel = walletLevel;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank;
    }

    public String getPayerAcct() {
        return payerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        this.payerAcct = payerAcct;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNarraTive() {
        return narraTive;
    }

    public void setNarraTive(String narraTive) {
        this.narraTive = narraTive;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PayConvertReq{" +
                "busiType='" + busiType + '\'' +
                ", busiKind='" + busiKind + '\'' +
                ", amount='" + HiddenUtil.amtHidden(amount) + '\'' +
                ", currency='" + currency + '\'' +
                ", payeeBank='" + payeeBank + '\'' +
                ", payeeAcct='" + HiddenUtil.acHidden(payeeAcct) + '\'' +
                ", walletLevel='" + walletLevel + '\'' +
                ", walletType='" + walletType + '\'' +
                ", walletName='" + HiddenUtil.acNameHidden(walletName) + '\'' +
                ", payerBank='" + payerBank + '\'' +
                ", payerAcct='" + HiddenUtil.acHidden(payerAcct) + '\'' +
                ", payerName='" + HiddenUtil.acNameHidden(payerName) + '\'' +
                ", summary='" + summary + '\'' +
                ", narraTive='" + narraTive + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
