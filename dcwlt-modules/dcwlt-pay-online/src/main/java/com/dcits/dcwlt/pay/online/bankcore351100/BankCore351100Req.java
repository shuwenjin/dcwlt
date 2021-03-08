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
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.pay.online.lsfk43.IBankCoreBody;


public class BankCore351100Req implements IBankCoreBody {

	@JSONField(name = "AC_TYPE1")
	private String acType1; // 借方账户类型 M

	@JSONField(name = "AC1")
	private String ac1; // 借方账号/卡号/内部户 O

	@JSONField(name = "ACNO1")
	private String acno1; // 借方卡下交易账号 O

	@JSONField(name = "RVS_NO1")
	private String rvsNo1; // 借方挂销账编号 O

	@JSONField(name = "PRDMO_CD1")
	private String prdmoCd1; // 借方产品模型 O

	@JSONField(name = "PROD_CD1")
	private String prodCd1; // 借方产品代码 O

	@JSONField(name = "EVENT_CD1")
	private String eventCd1; // 借方事件代码 O

	@JSONField(name = "PRDGL_CD1")
	private String prdglCd1; // 借方核算要素 O

	@JSONField(name = "ACCT_BR1_01")
	private String acctBr101; // 借方核算机构1 O

	@JSONField(name = "ACCT_BR1_02")
	private String acctBr102; // 借方核算机构2 O

	@JSONField(name = "ACCT_BR1_03")
	private String acctBr103; // 借方核算机构3 O

	@JSONField(name = "ACCT_BR1_04")
	private String acctBr104; // 借方核算机构4 O

	@JSONField(name = "ACCT_BR1_05")
	private String acctBr105; // 借方核算机构5 O

	@JSONField(name = "DR_AMT_NO1")
	private String drAmtNo1; // 借方核算金额指针1 O

	@JSONField(name = "DR_AMT_VAL1")
	private String drAmtVal1; // 借方核算金额数值1 O

	@JSONField(name = "DR_AMT_NO2")
	private String drAmtNo2; // 借方核算金额指针2 O

	@JSONField(name = "DR_AMT_VAL2")
	private String drAmtVal2; // 借方核算金额数值2 O

	@JSONField(name = "DR_AMT_NO3")
	private String drAmtNo3; // 借方核算金额指针3 O

	@JSONField(name = "DR_AMT_VAL3")
	private String drAmtVal3; // 借方核算金额数值3 O

	@JSONField(name = "DR_AMT_NO4")
	private String drAmtNo4; // 借方核算金额指针4 O

	@JSONField(name = "DR_AMT_VAL4")
	private String drAmtVal4; // 借方核算金额数值4 O

	@JSONField(name = "DR_AMT_NO5")
	private String drAmtNo5; // 借方核算金额指针5 O

	@JSONField(name = "DR_AMT_VAL5")
	private String drAmtVal5; // 借方核算金额数值5 O

	@JSONField(name = "DR_AMT_NO6")
	private String drAmtNo6; // 借方核算金额指针6 O

	@JSONField(name = "DR_AMT_VAL6")
	private String drAmtVal6; // 借方核算金额数值6 O

	@JSONField(name = "DR_BUSI_TYPE1")
	private String drBusiType1; // 借方业务分类 O

	@JSONField(name = "DR_BUSI_TYPE2")
	private String drBusiType2; // 借方票据验票标识 O

	@JSONField(name = "DR_BUSI_TYPE3")
	private String drBusiType3; // 借方业务控制标识 O

	@JSONField(name = "DR_BR_FLG1")
	private String drBrFlg1; // 借方介质标识 O

	@JSONField(name = "DR_BR1")
	private String drBr1; // 预留栏位 O

	@JSONField(name = "CHK_PSWD_ID")
	private String chkPswdId; // 借方校验密码标识 O

	@JSONField(name = "PSWD")
	private String pswd; // 密码 O

	@JSONField(name = "CHK_MAG_ID")
	private String chkMagId; // 借方磁条校验标识 O

	@JSONField(name = "TRK2_DAT")
	private String trk2Dat; // 第二磁道信息 O

	@JSONField(name = "TRK3_DAT")
	private String trk3Dat; // 第三磁道信息 O

	@JSONField(name = "CHK_NAME_FLG1")
	private String chkNameFlg1; // 借方户名检查标识 O

	@JSONField(name = "NAME1")
	private String name1; // 借方户名 O

	@JSONField(name = "CHK_ID_FLG1")
	private String chkIdFlg1; // 借方证件检查标识 O

	@JSONField(name = "ID_TYPE1")
	private String idType1; // 证件类型 O

	@JSONField(name = "ID_NO1")
	private String idNo1; // 证件号码 O

	@JSONField(name = "CHQ_NO")
	private String chqNo; // 支票号码 O

	@JSONField(name = "CHQ_PSWD")
	private String chqPswd; // 支票支付密码 O

	@JSONField(name = "CHQ_TYPE")
	private String chqType; // 支票类型 O

	@JSONField(name = "CHQ_ISSUE_DT")
	private String chqIssueDt; // 支票出票日期 O

	@JSONField(name = "HLD_REF")
	private String hldRef; // 冻结编号 O

	@JSONField(name = "RHLD_AMT")
	private String rhldAmt; // 解冻金额 O

	@JSONField(name = "CASH_FLG1")
	private String cashFlg1; // 借方钞汇标识 O

	@JSONField(name = "CCY1")
	private String ccy1; // 借方货币 M

	@JSONField(name = "AMT1")
	private String amt1; // 借方金额 O

	@JSONField(name = "MMO1")
	private String mmo1; // 借方摘要代码 O

	@JSONField(name = "IB_REF_NO1")
	private String ibRefNo1; // 借方交易参考号 O

	@JSONField(name = "IB_TX_ID1")
	private String ibTxId1; // 借方交易代码 O

	@JSONField(name = "D_PVAL1")
	private String dPval1; // 券别1 O

	@JSONField(name = "D_NUM1")
	private String dNum1; // 张数1 O

	@JSONField(name = "D_PVAL2")
	private String dPval2; // 券别2 O

	@JSONField(name = "D_NUM2")
	private String dNum2; // 张数2 O

	@JSONField(name = "D_PVAL3")
	private String dPval3; // 券别3 O

	@JSONField(name = "D_NUM3")
	private String dNum3; // 张数3 O

	@JSONField(name = "D_PVAL4")
	private String dPval4; // 券别4 O

	@JSONField(name = "D_NUM4")
	private String dNum4; // 张数4 O

	@JSONField(name = "D_PVAL5")
	private String dPval5; // 券别5 O

	@JSONField(name = "D_NUM5")
	private String dNum5; // 张数5 O

	@JSONField(name = "AC_TYPE2")
	private String acType2; // 贷方账户类型 O

	@JSONField(name = "AC2")
	private String ac2; // 贷方账号/卡号/内部户 O

	@JSONField(name = "ACNO2")
	private String acno2; // 贷方卡下交易账号 O

	@JSONField(name = "RVS_NO2")
	private String rvsNo2; // 贷方挂销账编号 O

	@JSONField(name = "PRDMO_CD2")
	private String prdmoCd2; // 贷方产品模型 O

	@JSONField(name = "PROD_CD2")
	private String prodCd2; // 贷方产品代码 O

	@JSONField(name = "EVENT_CD2")
	private String eventCd2; // 贷方事件代码 O

	@JSONField(name = "PRDGL_CD2")
	private String prdglCd2; // 贷方核算要素 O

	@JSONField(name = "ACCT_BR2_01")
	private String acctBr201; // 贷方核算机构1 O

	@JSONField(name = "ACCT_BR2_02")
	private String acctBr202; // 贷方核算机构2 O

	@JSONField(name = "ACCT_BR2_03")
	private String acctBr203; // 贷方核算机构3 O

	@JSONField(name = "ACCT_BR2_04")
	private String acctBr204; // 贷方核算机构4 O

	@JSONField(name = "ACCT_BR2_05")
	private String acctBr205; // 贷方核算机构5 O

	@JSONField(name = "CR_AMT_NO1")
	private String crAmtNo1; // 贷方核算金额指针1 O

	@JSONField(name = "CR_AMT_VAL1")
	private String crAmtVal1; // 贷方核算金额数值1 O

	@JSONField(name = "CR_AMT_NO2")
	private String crAmtNo2; // 贷方核算金额指针2 O

	@JSONField(name = "CR_AMT_VAL2")
	private String crAmtVal2; // 贷方核算金额数值2 O

	@JSONField(name = "CR_AMT_NO3")
	private String crAmtNo3; // 贷方核算金额指针3 O

	@JSONField(name = "CR_AMT_VAL3")
	private String crAmtVal3; // 贷方核算金额数值3 O

	@JSONField(name = "CR_AMT_NO4")
	private String crAmtNo4; // 贷方核算金额指针4 O

	@JSONField(name = "CR_AMT_VAL4")
	private String crAmtVal4; // 贷方核算金额数值4 O

	@JSONField(name = "CR_AMT_NO5")
	private String crAmtNo5; // 贷方核算金额指针5 O

	@JSONField(name = "CR_AMT_VAL5")
	private String crAmtVal5; // 贷方核算金额数值5 O

	@JSONField(name = "CR_AMT_NO6")
	private String crAmtNo6; // 贷方核算金额指针6 O

	@JSONField(name = "CR_AMT_VAL6")
	private String crAmtVal6; // 贷方核算金额数值6 O

	@JSONField(name = "CR_BUSI_TYPE1")
	private String crBusiType1; // 贷方业务分类 O

	@JSONField(name = "CR_BUSI_TYPE2")
	private String crBusiType2; // 预留栏位 O

	@JSONField(name = "CR_BUSI_TYPE3")
	private String crBusiType3; // 预留栏位 O

	@JSONField(name = "CR_BR_FLG2")
	private String crBrFlg2; // 费用优惠标识 O

	@JSONField(name = "CR_BR2")
	private String crBr2; // 预留栏位 O

	@JSONField(name = "CHK_NAME_FLG2")
	private String chkNameFlg2; // 贷方户名检查标识 O

	@JSONField(name = "NAME2")
	private String name2; // 贷方户名 O

	@JSONField(name = "CHK_ID_FLG2")
	private String chkIdFlg2; // 贷方证件检查标识 O

	@JSONField(name = "ID_TYPE2")
	private String idType2; // 证件类型 O

	@JSONField(name = "ID_NO2")
	private String idNo2; // 证件号码 O

	@JSONField(name = "CASH_FLG2")
	private String cashFlg2; // 贷方钞汇标识 O

	@JSONField(name = "CCY2")
	private String ccy2; // 贷方货币 O

	@JSONField(name = "AMT2")
	private String amt2; // 贷方金额 O

	@JSONField(name = "MMO2")
	private String mmo2; // 贷方摘要代码 O

	@JSONField(name = "HLD_FLG")
	private String hldFlg; // 冻结标志 O

	@JSONField(name = "HLD_AMT")
	private String hldAmt; // 冻结金额 O

	@JSONField(name = "HLD_EFF_DT")
	private String hldEffDt; // 冻结生效日期 O

	@JSONField(name = "HLD_EXP_DT")
	private String hldExpDt; // 冻结失效日期 O

	@JSONField(name = "HLD_RMK")
	private String hldRmk; // 冻结原因 O

	@JSONField(name = "IB_REF_NO2")
	private String ibRefNo2; // 贷方交易参考号 O

	@JSONField(name = "IB_TX_ID2")
	private String ibTxId2; // 贷方交易代码 O

	@JSONField(name = "C_PVAL1")
	private String cPval1; // 券别1 O

	@JSONField(name = "C_NUM1")
	private String cNum1; // 张数1 O

	@JSONField(name = "C_PVAL2")
	private String cPval2; // 券别2 O

	@JSONField(name = "C_NUM2")
	private String cNum2; // 张数2 O

	@JSONField(name = "C_PVAL3")
	private String cPval3; // 券别3 O

	@JSONField(name = "C_NUM3")
	private String cNum3; // 张数3 O

	@JSONField(name = "C_PVAL4")
	private String cPval4; // 券别4 O

	@JSONField(name = "C_NUM4")
	private String cNum4; // 张数4 O

	@JSONField(name = "C_PVAL5")
	private String cPval5; // 券别5 O

	@JSONField(name = "C_NUM5")
	private String cNum5; // 张数5 O

	@JSONField(name = "CHK_SAME_CI")
	private String chkSameCi; // 借贷方客户一致性检查标识 O

	@JSONField(name = "VAL_DT")
	private String valDt; // 起息日期 O

	@JSONField(name = "TX_REF")
	private String txRef; // 附言 O

	@JSONField(name = "REMARKS")
	private String remarks; // 备注 O

	@JSONField(name = "AMT_SIGN")
	private String amtSign; // 金额符号 O

	@JSONField(name = "OTH_AC_NO")
	private String othAcNo; // 对方账号 O

	@JSONField(name = "OTH_AC_NAME")
	private String othAcName; // 对方户名 O

	@JSONField(name = "OTH_BK")
	private String othBk; // 对方联行号 O

	@JSONField(name = "OTH_BKNM")
	private String othBknm; // 对方联行名 O

	@JSONField(name = "EX_GRP")
	private String exGrp; // 兑换组 O

	@JSONField(name = "EX_RATE")
	private String exRate; // 汇率 O

	@JSONField(name = "EX_TIME")
	private String exTime; // 预留栏位 O

	@JSONField(name = "LN_AMT")
	private String lnAmt; // 信贷类资金 O

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

	public String getRvsNo1() {
		return rvsNo1;
	}

	public void setRvsNo1(String rvsNo1) {
		this.rvsNo1 = rvsNo1;
	}

	public String getPrdmoCd1() {
		return prdmoCd1;
	}

	public void setPrdmoCd1(String prdmoCd1) {
		this.prdmoCd1 = prdmoCd1;
	}

	public String getProdCd1() {
		return prodCd1;
	}

	public void setProdCd1(String prodCd1) {
		this.prodCd1 = prodCd1;
	}

	public String getEventCd1() {
		return eventCd1;
	}

	public void setEventCd1(String eventCd1) {
		this.eventCd1 = eventCd1;
	}

	public String getPrdglCd1() {
		return prdglCd1;
	}

	public void setPrdglCd1(String prdglCd1) {
		this.prdglCd1 = prdglCd1;
	}

	public String getAcctBr101() {
		return acctBr101;
	}

	public void setAcctBr101(String acctBr101) {
		this.acctBr101 = acctBr101;
	}

	public String getAcctBr102() {
		return acctBr102;
	}

	public void setAcctBr102(String acctBr102) {
		this.acctBr102 = acctBr102;
	}

	public String getAcctBr103() {
		return acctBr103;
	}

	public void setAcctBr103(String acctBr103) {
		this.acctBr103 = acctBr103;
	}

	public String getAcctBr104() {
		return acctBr104;
	}

	public void setAcctBr104(String acctBr104) {
		this.acctBr104 = acctBr104;
	}

	public String getAcctBr105() {
		return acctBr105;
	}

	public void setAcctBr105(String acctBr105) {
		this.acctBr105 = acctBr105;
	}

	public String getDrAmtNo1() {
		return drAmtNo1;
	}

	public void setDrAmtNo1(String drAmtNo1) {
		this.drAmtNo1 = drAmtNo1;
	}

	public String getDrAmtVal1() {
		return drAmtVal1;
	}

	public void setDrAmtVal1(String drAmtVal1) {
		this.drAmtVal1 = drAmtVal1;
	}

	public String getDrAmtNo2() {
		return drAmtNo2;
	}

	public void setDrAmtNo2(String drAmtNo2) {
		this.drAmtNo2 = drAmtNo2;
	}

	public String getDrAmtVal2() {
		return drAmtVal2;
	}

	public void setDrAmtVal2(String drAmtVal2) {
		this.drAmtVal2 = drAmtVal2;
	}

	public String getDrAmtNo3() {
		return drAmtNo3;
	}

	public void setDrAmtNo3(String drAmtNo3) {
		this.drAmtNo3 = drAmtNo3;
	}

	public String getDrAmtVal3() {
		return drAmtVal3;
	}

	public void setDrAmtVal3(String drAmtVal3) {
		this.drAmtVal3 = drAmtVal3;
	}

	public String getDrAmtNo4() {
		return drAmtNo4;
	}

	public void setDrAmtNo4(String drAmtNo4) {
		this.drAmtNo4 = drAmtNo4;
	}

	public String getDrAmtVal4() {
		return drAmtVal4;
	}

	public void setDrAmtVal4(String drAmtVal4) {
		this.drAmtVal4 = drAmtVal4;
	}

	public String getDrAmtNo5() {
		return drAmtNo5;
	}

	public void setDrAmtNo5(String drAmtNo5) {
		this.drAmtNo5 = drAmtNo5;
	}

	public String getDrAmtVal5() {
		return drAmtVal5;
	}

	public void setDrAmtVal5(String drAmtVal5) {
		this.drAmtVal5 = drAmtVal5;
	}

	public String getDrAmtNo6() {
		return drAmtNo6;
	}

	public void setDrAmtNo6(String drAmtNo6) {
		this.drAmtNo6 = drAmtNo6;
	}

	public String getDrAmtVal6() {
		return drAmtVal6;
	}

	public void setDrAmtVal6(String drAmtVal6) {
		this.drAmtVal6 = drAmtVal6;
	}

	public String getDrBusiType1() {
		return drBusiType1;
	}

	public void setDrBusiType1(String drBusiType1) {
		this.drBusiType1 = drBusiType1;
	}

	public String getDrBusiType2() {
		return drBusiType2;
	}

	public void setDrBusiType2(String drBusiType2) {
		this.drBusiType2 = drBusiType2;
	}

	public String getDrBusiType3() {
		return drBusiType3;
	}

	public void setDrBusiType3(String drBusiType3) {
		this.drBusiType3 = drBusiType3;
	}

	public String getDrBrFlg1() {
		return drBrFlg1;
	}

	public void setDrBrFlg1(String drBrFlg1) {
		this.drBrFlg1 = drBrFlg1;
	}

	public String getDrBr1() {
		return drBr1;
	}

	public void setDrBr1(String drBr1) {
		this.drBr1 = drBr1;
	}

	public String getChkPswdId() {
		return chkPswdId;
	}

	public void setChkPswdId(String chkPswdId) {
		this.chkPswdId = chkPswdId;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getChkMagId() {
		return chkMagId;
	}

	public void setChkMagId(String chkMagId) {
		this.chkMagId = chkMagId;
	}

	public String getTrk2Dat() {
		return trk2Dat;
	}

	public void setTrk2Dat(String trk2Dat) {
		this.trk2Dat = trk2Dat;
	}

	public String getTrk3Dat() {
		return trk3Dat;
	}

	public void setTrk3Dat(String trk3Dat) {
		this.trk3Dat = trk3Dat;
	}

	public String getChkNameFlg1() {
		return chkNameFlg1;
	}

	public void setChkNameFlg1(String chkNameFlg1) {
		this.chkNameFlg1 = chkNameFlg1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getChkIdFlg1() {
		return chkIdFlg1;
	}

	public void setChkIdFlg1(String chkIdFlg1) {
		this.chkIdFlg1 = chkIdFlg1;
	}

	public String getIdType1() {
		return idType1;
	}

	public void setIdType1(String idType1) {
		this.idType1 = idType1;
	}

	public String getIdNo1() {
		return idNo1;
	}

	public void setIdNo1(String idNo1) {
		this.idNo1 = idNo1;
	}

	public String getChqNo() {
		return chqNo;
	}

	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}

	public String getChqPswd() {
		return chqPswd;
	}

	public void setChqPswd(String chqPswd) {
		this.chqPswd = chqPswd;
	}

	public String getChqType() {
		return chqType;
	}

	public void setChqType(String chqType) {
		this.chqType = chqType;
	}

	public String getChqIssueDt() {
		return chqIssueDt;
	}

	public void setChqIssueDt(String chqIssueDt) {
		this.chqIssueDt = chqIssueDt;
	}

	public String getHldRef() {
		return hldRef;
	}

	public void setHldRef(String hldRef) {
		this.hldRef = hldRef;
	}

	public String getRhldAmt() {
		return rhldAmt;
	}

	public void setRhldAmt(String rhldAmt) {
		this.rhldAmt = rhldAmt;
	}

	public String getCashFlg1() {
		return cashFlg1;
	}

	public void setCashFlg1(String cashFlg1) {
		this.cashFlg1 = cashFlg1;
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

	public String getMmo1() {
		return mmo1;
	}

	public void setMmo1(String mmo1) {
		this.mmo1 = mmo1;
	}

	public String getIbRefNo1() {
		return ibRefNo1;
	}

	public void setIbRefNo1(String ibRefNo1) {
		this.ibRefNo1 = ibRefNo1;
	}

	public String getIbTxId1() {
		return ibTxId1;
	}

	public void setIbTxId1(String ibTxId1) {
		this.ibTxId1 = ibTxId1;
	}

	public String getdPval1() {
		return dPval1;
	}

	public void setdPval1(String dPval1) {
		this.dPval1 = dPval1;
	}

	public String getdNum1() {
		return dNum1;
	}

	public void setdNum1(String dNum1) {
		this.dNum1 = dNum1;
	}

	public String getdPval2() {
		return dPval2;
	}

	public void setdPval2(String dPval2) {
		this.dPval2 = dPval2;
	}

	public String getdNum2() {
		return dNum2;
	}

	public void setdNum2(String dNum2) {
		this.dNum2 = dNum2;
	}

	public String getdPval3() {
		return dPval3;
	}

	public void setdPval3(String dPval3) {
		this.dPval3 = dPval3;
	}

	public String getdNum3() {
		return dNum3;
	}

	public void setdNum3(String dNum3) {
		this.dNum3 = dNum3;
	}

	public String getdPval4() {
		return dPval4;
	}

	public void setdPval4(String dPval4) {
		this.dPval4 = dPval4;
	}

	public String getdNum4() {
		return dNum4;
	}

	public void setdNum4(String dNum4) {
		this.dNum4 = dNum4;
	}

	public String getdPval5() {
		return dPval5;
	}

	public void setdPval5(String dPval5) {
		this.dPval5 = dPval5;
	}

	public String getdNum5() {
		return dNum5;
	}

	public void setdNum5(String dNum5) {
		this.dNum5 = dNum5;
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

	public String getRvsNo2() {
		return rvsNo2;
	}

	public void setRvsNo2(String rvsNo2) {
		this.rvsNo2 = rvsNo2;
	}

	public String getPrdmoCd2() {
		return prdmoCd2;
	}

	public void setPrdmoCd2(String prdmoCd2) {
		this.prdmoCd2 = prdmoCd2;
	}

	public String getProdCd2() {
		return prodCd2;
	}

	public void setProdCd2(String prodCd2) {
		this.prodCd2 = prodCd2;
	}

	public String getEventCd2() {
		return eventCd2;
	}

	public void setEventCd2(String eventCd2) {
		this.eventCd2 = eventCd2;
	}

	public String getPrdglCd2() {
		return prdglCd2;
	}

	public void setPrdglCd2(String prdglCd2) {
		this.prdglCd2 = prdglCd2;
	}

	public String getAcctBr201() {
		return acctBr201;
	}

	public void setAcctBr201(String acctBr201) {
		this.acctBr201 = acctBr201;
	}

	public String getAcctBr202() {
		return acctBr202;
	}

	public void setAcctBr202(String acctBr202) {
		this.acctBr202 = acctBr202;
	}

	public String getAcctBr203() {
		return acctBr203;
	}

	public void setAcctBr203(String acctBr203) {
		this.acctBr203 = acctBr203;
	}

	public String getAcctBr204() {
		return acctBr204;
	}

	public void setAcctBr204(String acctBr204) {
		this.acctBr204 = acctBr204;
	}

	public String getAcctBr205() {
		return acctBr205;
	}

	public void setAcctBr205(String acctBr205) {
		this.acctBr205 = acctBr205;
	}

	public String getCrAmtNo1() {
		return crAmtNo1;
	}

	public void setCrAmtNo1(String crAmtNo1) {
		this.crAmtNo1 = crAmtNo1;
	}

	public String getCrAmtVal1() {
		return crAmtVal1;
	}

	public void setCrAmtVal1(String crAmtVal1) {
		this.crAmtVal1 = crAmtVal1;
	}

	public String getCrAmtNo2() {
		return crAmtNo2;
	}

	public void setCrAmtNo2(String crAmtNo2) {
		this.crAmtNo2 = crAmtNo2;
	}

	public String getCrAmtVal2() {
		return crAmtVal2;
	}

	public void setCrAmtVal2(String crAmtVal2) {
		this.crAmtVal2 = crAmtVal2;
	}

	public String getCrAmtNo3() {
		return crAmtNo3;
	}

	public void setCrAmtNo3(String crAmtNo3) {
		this.crAmtNo3 = crAmtNo3;
	}

	public String getCrAmtVal3() {
		return crAmtVal3;
	}

	public void setCrAmtVal3(String crAmtVal3) {
		this.crAmtVal3 = crAmtVal3;
	}

	public String getCrAmtNo4() {
		return crAmtNo4;
	}

	public void setCrAmtNo4(String crAmtNo4) {
		this.crAmtNo4 = crAmtNo4;
	}

	public String getCrAmtVal4() {
		return crAmtVal4;
	}

	public void setCrAmtVal4(String crAmtVal4) {
		this.crAmtVal4 = crAmtVal4;
	}

	public String getCrAmtNo5() {
		return crAmtNo5;
	}

	public void setCrAmtNo5(String crAmtNo5) {
		this.crAmtNo5 = crAmtNo5;
	}

	public String getCrAmtVal5() {
		return crAmtVal5;
	}

	public void setCrAmtVal5(String crAmtVal5) {
		this.crAmtVal5 = crAmtVal5;
	}

	public String getCrAmtNo6() {
		return crAmtNo6;
	}

	public void setCrAmtNo6(String crAmtNo6) {
		this.crAmtNo6 = crAmtNo6;
	}

	public String getCrAmtVal6() {
		return crAmtVal6;
	}

	public void setCrAmtVal6(String crAmtVal6) {
		this.crAmtVal6 = crAmtVal6;
	}

	public String getCrBusiType1() {
		return crBusiType1;
	}

	public void setCrBusiType1(String crBusiType1) {
		this.crBusiType1 = crBusiType1;
	}

	public String getCrBusiType2() {
		return crBusiType2;
	}

	public void setCrBusiType2(String crBusiType2) {
		this.crBusiType2 = crBusiType2;
	}

	public String getCrBusiType3() {
		return crBusiType3;
	}

	public void setCrBusiType3(String crBusiType3) {
		this.crBusiType3 = crBusiType3;
	}

	public String getCrBrFlg2() {
		return crBrFlg2;
	}

	public void setCrBrFlg2(String crBrFlg2) {
		this.crBrFlg2 = crBrFlg2;
	}

	public String getCrBr2() {
		return crBr2;
	}

	public void setCrBr2(String crBr2) {
		this.crBr2 = crBr2;
	}

	public String getChkNameFlg2() {
		return chkNameFlg2;
	}

	public void setChkNameFlg2(String chkNameFlg2) {
		this.chkNameFlg2 = chkNameFlg2;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getChkIdFlg2() {
		return chkIdFlg2;
	}

	public void setChkIdFlg2(String chkIdFlg2) {
		this.chkIdFlg2 = chkIdFlg2;
	}

	public String getIdType2() {
		return idType2;
	}

	public void setIdType2(String idType2) {
		this.idType2 = idType2;
	}

	public String getIdNo2() {
		return idNo2;
	}

	public void setIdNo2(String idNo2) {
		this.idNo2 = idNo2;
	}

	public String getCashFlg2() {
		return cashFlg2;
	}

	public void setCashFlg2(String cashFlg2) {
		this.cashFlg2 = cashFlg2;
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

	public String getMmo2() {
		return mmo2;
	}

	public void setMmo2(String mmo2) {
		this.mmo2 = mmo2;
	}

	public String getHldFlg() {
		return hldFlg;
	}

	public void setHldFlg(String hldFlg) {
		this.hldFlg = hldFlg;
	}

	public String getHldAmt() {
		return hldAmt;
	}

	public void setHldAmt(String hldAmt) {
		this.hldAmt = hldAmt;
	}

	public String getHldEffDt() {
		return hldEffDt;
	}

	public void setHldEffDt(String hldEffDt) {
		this.hldEffDt = hldEffDt;
	}

	public String getHldExpDt() {
		return hldExpDt;
	}

	public void setHldExpDt(String hldExpDt) {
		this.hldExpDt = hldExpDt;
	}

	public String getHldRmk() {
		return hldRmk;
	}

	public void setHldRmk(String hldRmk) {
		this.hldRmk = hldRmk;
	}

	public String getIbRefNo2() {
		return ibRefNo2;
	}

	public void setIbRefNo2(String ibRefNo2) {
		this.ibRefNo2 = ibRefNo2;
	}

	public String getIbTxId2() {
		return ibTxId2;
	}

	public void setIbTxId2(String ibTxId2) {
		this.ibTxId2 = ibTxId2;
	}

	public String getcPval1() {
		return cPval1;
	}

	public void setcPval1(String cPval1) {
		this.cPval1 = cPval1;
	}

	public String getcNum1() {
		return cNum1;
	}

	public void setcNum1(String cNum1) {
		this.cNum1 = cNum1;
	}

	public String getcPval2() {
		return cPval2;
	}

	public void setcPval2(String cPval2) {
		this.cPval2 = cPval2;
	}

	public String getcNum2() {
		return cNum2;
	}

	public void setcNum2(String cNum2) {
		this.cNum2 = cNum2;
	}

	public String getcPval3() {
		return cPval3;
	}

	public void setcPval3(String cPval3) {
		this.cPval3 = cPval3;
	}

	public String getcNum3() {
		return cNum3;
	}

	public void setcNum3(String cNum3) {
		this.cNum3 = cNum3;
	}

	public String getcPval4() {
		return cPval4;
	}

	public void setcPval4(String cPval4) {
		this.cPval4 = cPval4;
	}

	public String getcNum4() {
		return cNum4;
	}

	public void setcNum4(String cNum4) {
		this.cNum4 = cNum4;
	}

	public String getcPval5() {
		return cPval5;
	}

	public void setcPval5(String cPval5) {
		this.cPval5 = cPval5;
	}

	public String getcNum5() {
		return cNum5;
	}

	public void setcNum5(String cNum5) {
		this.cNum5 = cNum5;
	}

	public String getChkSameCi() {
		return chkSameCi;
	}

	public void setChkSameCi(String chkSameCi) {
		this.chkSameCi = chkSameCi;
	}

	public String getValDt() {
		return valDt;
	}

	public void setValDt(String valDt) {
		this.valDt = valDt;
	}

	public String getTxRef() {
		return txRef;
	}

	public void setTxRef(String txRef) {
		this.txRef = txRef;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAmtSign() {
		return amtSign;
	}

	public void setAmtSign(String amtSign) {
		this.amtSign = amtSign;
	}

	public String getOthAcNo() {
		return othAcNo;
	}

	public void setOthAcNo(String othAcNo) {
		this.othAcNo = othAcNo;
	}

	public String getOthAcName() {
		return othAcName;
	}

	public void setOthAcName(String othAcName) {
		this.othAcName = othAcName;
	}

	public String getOthBk() {
		return othBk;
	}

	public void setOthBk(String othBk) {
		this.othBk = othBk;
	}

	public String getOthBknm() {
		return othBknm;
	}

	public void setOthBknm(String othBknm) {
		this.othBknm = othBknm;
	}

	public String getExGrp() {
		return exGrp;
	}

	public void setExGrp(String exGrp) {
		this.exGrp = exGrp;
	}

	public String getExRate() {
		return exRate;
	}

	public void setExRate(String exRate) {
		this.exRate = exRate;
	}

	public String getExTime() {
		return exTime;
	}

	public void setExTime(String exTime) {
		this.exTime = exTime;
	}

	public String getLnAmt() {
		return lnAmt;
	}

	public void setLnAmt(String lnAmt) {
		this.lnAmt = lnAmt;
	}

	@Override
	public String toString() {
		return "BankCore351100Req [acType1=" + acType1 + ", ac1=" + HiddenUtil.acHidden(ac1) + ", acno1=" + acno1 + ", rvsNo1=" + rvsNo1
				+ ", prdmoCd1=" + prdmoCd1 + ", prodCd1=" + prodCd1 + ", eventCd1=" + eventCd1 + ", prdglCd1="
				+ prdglCd1 + ", acctBr101=" + acctBr101 + ", acctBr102=" + acctBr102 + ", acctBr103=" + acctBr103
				+ ", acctBr104=" + acctBr104 + ", acctBr105=" + acctBr105 + ", drAmtNo1=" + drAmtNo1 + ", drAmtVal1="
				+ drAmtVal1 + ", drAmtNo2=" + drAmtNo2 + ", drAmtVal2=" + drAmtVal2 + ", drAmtNo3=" + drAmtNo3
				+ ", drAmtVal3=" + drAmtVal3 + ", drAmtNo4=" + drAmtNo4 + ", drAmtVal4=" + drAmtVal4 + ", drAmtNo5="
				+ drAmtNo5 + ", drAmtVal5=" + drAmtVal5 + ", drAmtNo6=" + drAmtNo6 + ", drAmtVal6=" + drAmtVal6
				+ ", drBusiType1=" + drBusiType1 + ", drBusiType2=" + drBusiType2 + ", drBusiType3=" + drBusiType3
				+ ", drBrFlg1=" + drBrFlg1 + ", drBr1=" + drBr1 + ", chkPswdId=" + chkPswdId + ", pswd=" + pswd
				+ ", chkMagId=" + chkMagId + ", trk2Dat=" + trk2Dat + ", trk3Dat=" + trk3Dat + ", chkNameFlg1="
				+ chkNameFlg1 + ", name1=" + HiddenUtil.acNameHidden(name1) + ", chkIdFlg1=" + chkIdFlg1 + ", idType1=" + idType1 + ", idNo1="
				+ idNo1 + ", chqNo=" + chqNo + ", chqPswd=" + chqPswd + ", chqType=" + chqType + ", chqIssueDt="
				+ chqIssueDt + ", hldRef=" + hldRef + ", rhldAmt=" + rhldAmt + ", cashFlg1=" + cashFlg1 + ", ccy1="
				+ ccy1 + ", amt1=" + amt1 + ", mmo1=" + mmo1 + ", ibRefNo1=" + ibRefNo1 + ", ibTxId1=" + ibTxId1
				+ ", dPval1=" + dPval1 + ", dNum1=" + dNum1 + ", dPval2=" + dPval2 + ", dNum2=" + dNum2 + ", dPval3="
				+ dPval3 + ", dNum3=" + dNum3 + ", dPval4=" + dPval4 + ", dNum4=" + dNum4 + ", dPval5=" + dPval5
				+ ", dNum5=" + dNum5 + ", acType2=" + acType2 + ", ac2=" + HiddenUtil.acHidden(ac2) + ", acno2=" + acno2 + ", rvsNo2="
				+ rvsNo2 + ", prdmoCd2=" + prdmoCd2 + ", prodCd2=" + prodCd2 + ", eventCd2=" + eventCd2 + ", prdglCd2="
				+ prdglCd2 + ", acctBr201=" + acctBr201 + ", acctBr202=" + acctBr202 + ", acctBr203=" + acctBr203
				+ ", acctBr204=" + acctBr204 + ", acctBr205=" + acctBr205 + ", crAmtNo1=" + crAmtNo1 + ", crAmtVal1="
				+ crAmtVal1 + ", crAmtNo2=" + crAmtNo2 + ", crAmtVal2=" + crAmtVal2 + ", crAmtNo3=" + crAmtNo3
				+ ", crAmtVal3=" + crAmtVal3 + ", crAmtNo4=" + crAmtNo4 + ", crAmtVal4=" + crAmtVal4 + ", crAmtNo5="
				+ crAmtNo5 + ", crAmtVal5=" + crAmtVal5 + ", crAmtNo6=" + crAmtNo6 + ", crAmtVal6=" + crAmtVal6
				+ ", crBusiType1=" + crBusiType1 + ", crBusiType2=" + crBusiType2 + ", crBusiType3=" + crBusiType3
				+ ", crBrFlg2=" + crBrFlg2 + ", crBr2=" + crBr2 + ", chkNameFlg2=" + chkNameFlg2 + ", name2=" + HiddenUtil.acNameHidden(name2)
				+ ", chkIdFlg2=" + chkIdFlg2 + ", idType2=" + idType2 + ", idNo2=" + HiddenUtil.certIdHidden(idNo2) + ", cashFlg2=" + cashFlg2
				+ ", ccy2=" + ccy2 + ", amt2=" + amt2 + ", mmo2=" + mmo2 + ", hldFlg=" + hldFlg + ", hldAmt=" + hldAmt
				+ ", hldEffDt=" + hldEffDt + ", hldExpDt=" + hldExpDt + ", hldRmk=" + hldRmk + ", ibRefNo2=" + ibRefNo2
				+ ", ibTxId2=" + ibTxId2 + ", cPval1=" + cPval1 + ", cNum1=" + cNum1 + ", cPval2=" + cPval2 + ", cNum2="
				+ cNum2 + ", cPval3=" + cPval3 + ", cNum3=" + cNum3 + ", cPval4=" + cPval4 + ", cNum4=" + cNum4
				+ ", cPval5=" + cPval5 + ", cNum5=" + cNum5 + ", chkSameCi=" + chkSameCi + ", valDt=" + valDt
				+ ", txRef=" + txRef + ", remarks=" + remarks + ", amtSign=" + amtSign + ", othAcNo=" + HiddenUtil.acHidden(othAcNo)
				+ ", othAcName=" + HiddenUtil.acNameHidden(othAcName) + ", othBk=" + othBk + ", othBknm=" + othBknm + ", exGrp=" + exGrp
				+ ", exRate=" + exRate + ", exTime=" + exTime + ", lnAmt=" + lnAmt + "]";
	}	
	
}