/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年3月31日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.bankcore351100;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.online.lsfk43.IBankCoreBody;

public class BankCore351100Rsp implements IBankCoreBody {
	
	@JSONField(name = "AC_TYPE1")       
	private String acType1    ;     //  借方账户类型           
	    
	@JSONField(name = "AC1")            
	private String ac1         ;     //  借方账号/卡号/内部户   
	    
	@JSONField(name = "ACNO1")          
	private String acno1       ;     //  借方卡下交易账号       
	    
	@JSONField(name = "NAME1")          
	private String name1       ;     //  借方户名               
	    
	@JSONField(name = "CCY1")           
	private String ccy1        ;     //  借方货币               
	    
	@JSONField(name = "AMT1")           
	private String amt1        ;     //  借方金额               
	    
	@JSONField(name = "AC_TYPE2")       
	private String acType2    ;     //  贷方账户类型           
	    
	@JSONField(name = "AC2")            
	private String ac2         ;     //  贷方账号/卡号/内部户   
	    
	@JSONField(name = "ACNO2")          
	private String acno2       ;     //  贷方卡下交易账号       
	    
	@JSONField(name = "NAME2")          
	private String name2       ;     //  贷方户名               
	    
	@JSONField(name = "CCY2")           
	private String ccy2        ;     //  贷方货币               
	    
	@JSONField(name = "AMT2")           
	private String amt2        ;     //  贷方金额               
	    
	@JSONField(name = "EX_RATE")        
	private String exRate     ;     //  汇率                   
	    
	@JSONField(name = "AC1_BR")         
	private String ac1Br      ;     //  借方账号开户行         
	    
	@JSONField(name = "AC1_FH")         
	private String ac1Fh      ;     //  借方账号开户分行       
	    
	@JSONField(name = "AC1_BAL")        
	private String ac1Bal     ;     //  借方账号余额           
	    
	@JSONField(name = "AC1_AVABAL")       
	private String ac1Avabal  ;     //  借方账号可用余额       
	    
	@JSONField(name = "AC2_BR")         
	private String ac2Br      ;     //  贷方账号开户行         
	    
	@JSONField(name = "AC2_FH")         
	private String ac2Fh      ;     //  贷方账号开户分行       
	    
	@JSONField(name = "AC2_BAL")        
	private String ac2Bal     ;     //  贷方账号余额           
	    
	@JSONField(name = "AC2_AVABAL")       
	private String ac2Avabal  ;     //  贷方账号可用余额       
	    
	@JSONField(name = "VAL_DT")         
	private String valDt      ;     //  起息日期               
	    
	@JSONField(name = "AC1_BBR")        
	private String ac1Bbr     ;     //  借方账号账务行         
	    
	@JSONField(name = "AC2_BBR")        
	private String ac2Bbr     ;     //  贷方账号账务行         
	    
	@JSONField(name = "AC1_DBR")        
	private String ac1Dbr     ;     //  借方账号管理行         
	    
	@JSONField(name = "AC2_DBR")        
	private String ac2Dbr     ;     //  贷方账号管理行         
	    
	@JSONField(name = "HLD_REF")        
	private String hldRef     ;     //  冻结编号               
	    
	@JSONField(name = "AC1_RVS_NO")     
	private String ac1RvsNo  ;     //  借方挂账编号           
	    
	@JSONField(name = "AC2_RVS_NO")     
	private String ac2RvsNo  ;     //  贷方挂账编号      

	public String getAcType1() {
		return acType1;
	}

	public void setAcType1(String acType1) {
		this.acType1 = acType1;
	}

	public String getAc1() {
		return ac1;
	}

	public void setAc1(String ac1) {
		this.ac1 = ac1;
	}

	public String getAcno1() {
		return acno1;
	}

	public void setAcno1(String acno1) {
		this.acno1 = acno1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getCcy1() {
		return ccy1;
	}

	public void setCcy1(String ccy1) {
		this.ccy1 = ccy1;
	}

	public String getAmt1() {
		return amt1;
	}

	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}

	public String getAcType2() {
		return acType2;
	}

	public void setAcType2(String acType2) {
		this.acType2 = acType2;
	}

	public String getAc2() {
		return ac2;
	}

	public void setAc2(String ac2) {
		this.ac2 = ac2;
	}

	public String getAcno2() {
		return acno2;
	}

	public void setAcno2(String acno2) {
		this.acno2 = acno2;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getCcy2() {
		return ccy2;
	}

	public void setCcy2(String ccy2) {
		this.ccy2 = ccy2;
	}

	public String getAmt2() {
		return amt2;
	}

	public void setAmt2(String amt2) {
		this.amt2 = amt2;
	}

	public String getExRate() {
		return exRate;
	}

	public void setExRate(String exRate) {
		this.exRate = exRate;
	}

	public String getAc1Br() {
		return ac1Br;
	}

	public void setAc1Br(String ac1Br) {
		this.ac1Br = ac1Br;
	}

	public String getAc1Fh() {
		return ac1Fh;
	}

	public void setAc1Fh(String ac1Fh) {
		this.ac1Fh = ac1Fh;
	}

	public String getAc1Bal() {
		return ac1Bal;
	}

	public void setAc1Bal(String ac1Bal) {
		this.ac1Bal = ac1Bal;
	}

	public String getAc1Avabal() {
		return ac1Avabal;
	}

	public void setAc1Avabal(String ac1Avabal) {
		this.ac1Avabal = ac1Avabal;
	}

	public String getAc2Br() {
		return ac2Br;
	}

	public void setAc2Br(String ac2Br) {
		this.ac2Br = ac2Br;
	}

	public String getAc2Fh() {
		return ac2Fh;
	}

	public void setAc2Fh(String ac2Fh) {
		this.ac2Fh = ac2Fh;
	}

	public String getAc2Bal() {
		return ac2Bal;
	}

	public void setAc2Bal(String ac2Bal) {
		this.ac2Bal = ac2Bal;
	}

	public String getAc2Avabal() {
		return ac2Avabal;
	}

	public void setAc2Avabal(String ac2Avabal) {
		this.ac2Avabal = ac2Avabal;
	}

	public String getValDt() {
		return valDt;
	}

	public void setValDt(String valDt) {
		this.valDt = valDt;
	}

	public String getAc1Bbr() {
		return ac1Bbr;
	}

	public void setAc1Bbr(String ac1Bbr) {
		this.ac1Bbr = ac1Bbr;
	}

	public String getAc2Bbr() {
		return ac2Bbr;
	}

	public void setAc2Bbr(String ac2Bbr) {
		this.ac2Bbr = ac2Bbr;
	}

	public String getAc1Dbr() {
		return ac1Dbr;
	}

	public void setAc1Dbr(String ac1Dbr) {
		this.ac1Dbr = ac1Dbr;
	}

	public String getAc2Dbr() {
		return ac2Dbr;
	}

	public void setAc2Dbr(String ac2Dbr) {
		this.ac2Dbr = ac2Dbr;
	}

	public String getHldRef() {
		return hldRef;
	}

	public void setHldRef(String hldRef) {
		this.hldRef = hldRef;
	}

	public String getAc1RvsNo() {
		return ac1RvsNo;
	}

	public void setAc1RvsNo(String ac1RvsNo) {
		this.ac1RvsNo = ac1RvsNo;
	}

	public String getAc2RvsNo() {
		return ac2RvsNo;
	}

	public void setAc2RvsNo(String ac2RvsNo) {
		this.ac2RvsNo = ac2RvsNo;
	}

	@Override
	public String toString() {
		return "BankCore351100Rsp [acType1=" + acType1 + ", ac1=" + ac1 + ", acno1=" + acno1 + ", name1=" + name1
				+ ", ccy1=" + ccy1 + ", amt1=" + amt1 + ", acType2=" + acType2 + ", ac2=" + ac2 + ", acno2=" + acno2
				+ ", name2=" + name2 + ", ccy2=" + ccy2 + ", amt2=" + amt2 + ", exRate=" + exRate + ", ac1Br=" + ac1Br
				+ ", ac1Fh=" + ac1Fh + ", ac1Bal=" + ac1Bal + ", ac1Avabal=" + ac1Avabal + ", ac2Br=" + ac2Br
				+ ", ac2Fh=" + ac2Fh + ", ac2Bal=" + ac2Bal + ", ac2Avabal=" + ac2Avabal + ", valDt=" + valDt
				+ ", ac1Bbr=" + ac1Bbr + ", ac2Bbr=" + ac2Bbr + ", ac1Dbr=" + ac1Dbr + ", ac2Dbr=" + ac2Dbr
				+ ", hldRef=" + hldRef + ", ac1RvsNo=" + ac1RvsNo + ", ac2RvsNo=" + ac2RvsNo + "]";
	}
	
		
}