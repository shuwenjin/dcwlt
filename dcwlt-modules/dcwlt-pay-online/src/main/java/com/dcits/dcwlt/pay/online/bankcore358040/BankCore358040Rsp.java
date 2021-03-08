package com.dcits.dcwlt.pay.online.bankcore358040;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;

public class BankCore358040Rsp{
	private String ac;							   //卡号账号
	private String acNo;                           //账号                
	private String acSeq;                          //序号                
	private String ccy;                            //币别                
	private String ciNo;                           //客户号              
	private String ciSts;                          //客户状态            
	private String type;                           //客户类型            
	private String idType;                         //证件类型            
	private String idNo;                           //证件号码            
	private String idAvalDt;                       //开户证件有效日期    
	private String ciName;                         //客户名称            
	private String ciLvl;                          //客户级别            
	private String sex;                            //性别                
	private String telNo;                          //手机号码            
	private String addrType;                       //地址类型            
	private String addrFlg;                        //默认地址标识        
	private String addrNm;                         //地址详细信息        
	private String addrCntyCd;                     //国家代码            
	private String addrPostCd;                     //邮政编码            
	private String acPrdCode;                      //账户产品码          
	private String acPrdName;                      //账户产品名称        
	private String acAttr;                         //账户类型            
	private String acType;                         //账户性质            
	private String acSts;                          //账户状态            
	private String acStsw;                         //账户状态字          
	private String acBlockSts;                     //账户冻结状态        
	private String amtHoldSts;                     //金额冻结状态        
	private String acBr;                           //开户网点            
	private String acFh;                           //开户分行            
	private String openDate;                       //开户日期            
	private String lastDate;                       //最后交易日期        
	private String bookBr;                         //账务行              
	private String domBr;                          //管理行              
	private String term;                           //存期                
	private String instrMth;                       //到期指示            
	private String ccyTyp;                         //钞汇标志            
	private String intRat;                         //利率值              
	private String acEname;                        //账户英文名称        
	private String acCname;                        //账户中文名称        
	private String ledgerBal;                      //账面余额            
	private String availBal;                       //可用余额            
	private String holdBal;                        //冻结金额            
	private String agrDepFlg;                      //协定存款标志        
	private String agrRat;                         //协定利率            
	private String agrAmt;                         //协定金额            
	private String drawMth;                        //支取方式            
	private String drcrFlg;                        //借贷记标志          
	private String crosDrFlg;                      //通兑标志            
	private String crosCrFlg;                      //通存标志            
	private String pbSts;                          //存折挂失状态        
	private String cardMedi ;                      //卡介质类型          
	private String cardSts;                        //卡介质状态          
	private String cardStsw;                       //卡使用状态          
	private String cardProd;                       //卡产品              
	private String cardProdNm;                     //卡产品名称          
	private String cardJointTyp;                   //卡联名标志          
	private String cardPcTyp;                      //卡归属类别          
	private String cardPvTyp;                      //卡物理属性          
	private String cardMobiTyp;                    //支持移动支付标识    
	private String cardLinkTyp;                    //主副卡标志          
	private String cardCatlg;                      //卡类                
	private String issueBr;                        //开卡网点            
	private String brNm;                           //开卡网点名称        
	private String issueDt;                        //开卡日期            
	private String expDt;                          //失效日期            
	private String item;                           //所属科目            
	private String stdAcFlg;                       //账号标志            
	private String nonStdActyp;                    //非标准账号类型      
	private String mainAc;                         //主账号              
	private String stdAc;                          //标准账号            
	private String spcKind;                        //专用户资金性质      
	private String frgInd;                         //外汇属性标注        
	private String yyzz;                           //企业营业执照        
	private String ciStsw;                         //客户状态字          
	private String socialFlg;                      //社保金融卡标识      
	private String acPurp;                         //账户用途            
	private String relType;                        //对公关联人类型      
	private String relName;                        //对公关联人名称      
	private String flg1;                           //定期账户凭证类型    
	private String flg2;                           //定期存单质押状态    
	private String flg3;                           //卡定向账户交易标志  
	private String flg4;                           //货币标识            
	private String flg5;                           //预留标识位5         
	private String reserved;                       //预留栏位            
	private String frgType;                        //对公外汇账户类型    
	private String frgCode;                        //对公外汇账户性质代码
	private String acClass;                        //账户分类            
	private String ftfFlg;                         //面签标识            
	private String rmk;                            //预留栏位            
	private String nonStdAcCnt;                    //非标账号个数        

	@JSONField(name = "AC")
	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	@JSONField(name = "AC_NO")
	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	@JSONField(name = "AC_SEQ")
	public String getAcSeq() {
		return acSeq;
	}

	public void setAcSeq(String acSeq) {
		this.acSeq = acSeq;
	}

	@JSONField(name = "CCY")
	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	@JSONField(name = "CI_NO")
	public String getCiNo() {
		return ciNo;
	}

	public void setCiNo(String ciNo) {
		this.ciNo = ciNo;
	}

	@JSONField(name = "CI_STS")
	public String getCiSts() {
		return ciSts;
	}

	public void setCiSts(String ciSts) {
		this.ciSts = ciSts;
	}

	@JSONField(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSONField(name = "ID_TYPE")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@JSONField(name = "ID_NO")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@JSONField(name = "ID_AVAL_DT")
	public String getIdAvalDt() {
		return idAvalDt;
	}

	public void setIdAvalDt(String idAvalDt) {
		this.idAvalDt = idAvalDt;
	}

	@JSONField(name = "CI_NAME")
	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}

	@JSONField(name = "CI_LVL")
	public String getCiLvl() {
		return ciLvl;
	}

	public void setCiLvl(String ciLvl) {
		this.ciLvl = ciLvl;
	}

	@JSONField(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JSONField(name = "TEL_NO")
	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	@JSONField(name = "ADDR_TYPE")
	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	@JSONField(name = "ADDR_FLG")
	public String getAddrFlg() {
		return addrFlg;
	}

	public void setAddrFlg(String addrFlg) {
		this.addrFlg = addrFlg;
	}

	@JSONField(name = "ADDR_NM")
	public String getAddrNm() {
		return addrNm;
	}

	public void setAddrNm(String addrNm) {
		this.addrNm = addrNm;
	}

	@JSONField(name = "ADDR_CNTY_CD")
	public String getAddrCntyCd() {
		return addrCntyCd;
	}

	public void setAddrCntyCd(String addrCntyCd) {
		this.addrCntyCd = addrCntyCd;
	}

	@JSONField(name = "ADDR_POST_CD")
	public String getAddrPostCd() {
		return addrPostCd;
	}

	public void setAddrPostCd(String addrPostCd) {
		this.addrPostCd = addrPostCd;
	}

	@JSONField(name = "AC_PRD_CODE")
	public String getAcPrdCode() {
		return acPrdCode;
	}

	public void setAcPrdCode(String acPrdCode) {
		this.acPrdCode = acPrdCode;
	}

	@JSONField(name = "AC_PRD_NAME")
	public String getAcPrdName() {
		return acPrdName;
	}

	public void setAcPrdName(String acPrdName) {
		this.acPrdName = acPrdName;
	}

	@JSONField(name = "AC_ATTR")
	public String getAcAttr() {
		return acAttr;
	}

	public void setAcAttr(String acAttr) {
		this.acAttr = acAttr;
	}

	@JSONField(name = "AC_TYPE")
	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	@JSONField(name = "AC_STS")
	public String getAcSts() {
		return acSts;
	}

	public void setAcSts(String acSts) {
		this.acSts = acSts;
	}

	@JSONField(name = "AC_STSW")
	public String getAcStsw() {
		return acStsw;
	}

	public void setAcStsw(String acStsw) {
		this.acStsw = acStsw;
	}

	@JSONField(name = "AC_BLOCK_STS")
	public String getAcBlockSts() {
		return acBlockSts;
	}

	public void setAcBlockSts(String acBlockSts) {
		this.acBlockSts = acBlockSts;
	}

	@JSONField(name = "AMT_HOLD_STS")
	public String getAmtHoldSts() {
		return amtHoldSts;
	}

	public void setAmtHoldSts(String amtHoldSts) {
		this.amtHoldSts = amtHoldSts;
	}

	@JSONField(name = "AC_BR")
	public String getAcBr() {
		return acBr;
	}

	public void setAcBr(String acBr) {
		this.acBr = acBr;
	}

	@JSONField(name = "AC_FH")
	public String getAcFh() {
		return acFh;
	}

	public void setAcFh(String acFh) {
		this.acFh = acFh;
	}

	@JSONField(name = "OPEN_DATE")
	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	@JSONField(name = "LAST_DATE")
	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	@JSONField(name = "BOOK_BR")
	public String getBookBr() {
		return bookBr;
	}

	public void setBookBr(String bookBr) {
		this.bookBr = bookBr;
	}

	@JSONField(name = "DOM_BR")
	public String getDomBr() {
		return domBr;
	}

	public void setDomBr(String domBr) {
		this.domBr = domBr;
	}

	@JSONField(name = "TERM")
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	@JSONField(name = "INSTR_MTH")
	public String getInstrMth() {
		return instrMth;
	}

	public void setInstrMth(String instrMth) {
		this.instrMth = instrMth;
	}

	@JSONField(name = "CCY_TYP")
	public String getCcyTyp() {
		return ccyTyp;
	}

	public void setCcyTyp(String ccyTyp) {
		this.ccyTyp = ccyTyp;
	}

	@JSONField(name = "INT_RAT")
	public String getIntRat() {
		return intRat;
	}

	public void setIntRat(String intRat) {
		this.intRat = intRat;
	}

	@JSONField(name = "AC_ENAME")
	public String getAcEname() {
		return acEname;
	}

	public void setAcEname(String acEname) {
		this.acEname = acEname;
	}

	@JSONField(name = "AC_CNAME")
	public String getAcCname() {
		return acCname;
	}

	public void setAcCname(String acCname) {
		this.acCname = acCname;
	}

	@JSONField(name = "LEDGER_BAL")
	public String getLedgerBal() {
		return ledgerBal;
	}

	public void setLedgerBal(String ledgerBal) {
		this.ledgerBal = ledgerBal;
	}

	@JSONField(name = "AVAIL_BAL")
	public String getAvailBal() {
		return availBal;
	}

	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}

	@JSONField(name = "HOLD_BAL")
	public String getHoldBal() {
		return holdBal;
	}

	public void setHoldBal(String holdBal) {
		this.holdBal = holdBal;
	}

	@JSONField(name = "AGR_DEP_FLG")
	public String getAgrDepFlg() {
		return agrDepFlg;
	}

	public void setAgrDepFlg(String agrDepFlg) {
		this.agrDepFlg = agrDepFlg;
	}

	@JSONField(name = "AGR_RAT")
	public String getAgrRat() {
		return agrRat;
	}

	public void setAgrRat(String agrRat) {
		this.agrRat = agrRat;
	}

	@JSONField(name = "AGR_AMT")
	public String getAgrAmt() {
		return agrAmt;
	}

	public void setAgrAmt(String agrAmt) {
		this.agrAmt = agrAmt;
	}

	@JSONField(name = "DRAW_MTH")
	public String getDrawMth() {
		return drawMth;
	}

	public void setDrawMth(String drawMth) {
		this.drawMth = drawMth;
	}

	@JSONField(name = "DRCR_FLG")
	public String getDrcrFlg() {
		return drcrFlg;
	}

	public void setDrcrFlg(String drcrFlg) {
		this.drcrFlg = drcrFlg;
	}

	@JSONField(name = "CROS_DR_FLG")
	public String getCrosDrFlg() {
		return crosDrFlg;
	}

	public void setCrosDrFlg(String crosDrFlg) {
		this.crosDrFlg = crosDrFlg;
	}

	@JSONField(name = "CROS_CR_FLG")
	public String getCrosCrFlg() {
		return crosCrFlg;
	}

	public void setCrosCrFlg(String crosCrFlg) {
		this.crosCrFlg = crosCrFlg;
	}

	@JSONField(name = "PB_STS")
	public String getPbSts() {
		return pbSts;
	}

	public void setPbSts(String pbSts) {
		this.pbSts = pbSts;
	}

	@JSONField(name = "CARD_MEDI")
	public String getCardMedi() {
		return cardMedi;
	}

	public void setCardMedi(String cardMedi) {
		this.cardMedi = cardMedi;
	}

	@JSONField(name = "CARD_STS")
	public String getCardSts() {
		return cardSts;
	}

	public void setCardSts(String cardSts) {
		this.cardSts = cardSts;
	}

	@JSONField(name = "CARD_STSW")
	public String getCardStsw() {
		return cardStsw;
	}

	public void setCardStsw(String cardStsw) {
		this.cardStsw = cardStsw;
	}

	@JSONField(name = "CARD_PROD")
	public String getCardProd() {
		return cardProd;
	}

	public void setCardProd(String cardProd) {
		this.cardProd = cardProd;
	}

	@JSONField(name = "CARD_PROD_NM")
	public String getCardProdNm() {
		return cardProdNm;
	}

	public void setCardProdNm(String cardProdNm) {
		this.cardProdNm = cardProdNm;
	}

	@JSONField(name = "CARD_JOINT_TYP")
	public String getCardJointTyp() {
		return cardJointTyp;
	}

	public void setCardJointTyp(String cardJointTyp) {
		this.cardJointTyp = cardJointTyp;
	}

	@JSONField(name = "CARD_PC_TYP")
	public String getCardPcTyp() {
		return cardPcTyp;
	}

	public void setCardPcTyp(String cardPcTyp) {
		this.cardPcTyp = cardPcTyp;
	}

	@JSONField(name = "CARD_PV_TYP")
	public String getCardPvTyp() {
		return cardPvTyp;
	}

	public void setCardPvTyp(String cardPvTyp) {
		this.cardPvTyp = cardPvTyp;
	}

	@JSONField(name = "CARD_MOBI_TYP")
	public String getCardMobiTyp() {
		return cardMobiTyp;
	}

	public void setCardMobiTyp(String cardMobiTyp) {
		this.cardMobiTyp = cardMobiTyp;
	}

	@JSONField(name = "CARD_LINK_TYP")
	public String getCardLinkTyp() {
		return cardLinkTyp;
	}

	public void setCardLinkTyp(String cardLinkTyp) {
		this.cardLinkTyp = cardLinkTyp;
	}

	@JSONField(name = "CARD_CATLG")
	public String getCardCatlg() {
		return cardCatlg;
	}

	public void setCardCatlg(String cardCatlg) {
		this.cardCatlg = cardCatlg;
	}

	@JSONField(name = "ISSUE_BR")
	public String getIssueBr() {
		return issueBr;
	}

	public void setIssueBr(String issueBr) {
		this.issueBr = issueBr;
	}

	@JSONField(name = "BR_NM")
	public String getBrNm() {
		return brNm;
	}

	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}

	@JSONField(name = "ISSUE_DT")
	public String getIssueDt() {
		return issueDt;
	}

	public void setIssueDt(String issueDt) {
		this.issueDt = issueDt;
	}

	@JSONField(name = "EXP_DT")
	public String getExpDt() {
		return expDt;
	}

	public void setExpDt(String expDt) {
		this.expDt = expDt;
	}

	@JSONField(name = "ITEM")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@JSONField(name = "STD_AC_FLG")
	public String getStdAcFlg() {
		return stdAcFlg;
	}

	public void setStdAcFlg(String stdAcFlg) {
		this.stdAcFlg = stdAcFlg;
	}

	@JSONField(name = "NON_STD_ACTYP")
	public String getNonStdActyp() {
		return nonStdActyp;
	}

	public void setNonStdActyp(String nonStdActyp) {
		this.nonStdActyp = nonStdActyp;
	}

	@JSONField(name = "MAIN_AC")
	public String getMainAc() {
		return mainAc;
	}

	public void setMainAc(String mainAc) {
		this.mainAc = mainAc;
	}

	@JSONField(name = "STD_AC")
	public String getStdAc() {
		return stdAc;
	}

	public void setStdAc(String stdAc) {
		this.stdAc = stdAc;
	}

	@JSONField(name = "SPC_KIND")
	public String getSpcKind() {
		return spcKind;
	}

	public void setSpcKind(String spcKind) {
		this.spcKind = spcKind;
	}

	@JSONField(name = "FRG_IND")
	public String getFrgInd() {
		return frgInd;
	}

	public void setFrgInd(String frgInd) {
		this.frgInd = frgInd;
	}

	@JSONField(name = "YYZZ")
	public String getYyzz() {
		return yyzz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	@JSONField(name = "CI_STSW")
	public String getCiStsw() {
		return ciStsw;
	}

	public void setCiStsw(String ciStsw) {
		this.ciStsw = ciStsw;
	}

	@JSONField(name = "SOCIAL_FLG")
	public String getSocialFlg() {
		return socialFlg;
	}

	public void setSocialFlg(String socialFlg) {
		this.socialFlg = socialFlg;
	}

	@JSONField(name = "AC_PURP")
	public String getAcPurp() {
		return acPurp;
	}

	public void setAcPurp(String acPurp) {
		this.acPurp = acPurp;
	}

	@JSONField(name = "REL_TYPE")
	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	@JSONField(name = "REL_NAME")
	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	@JSONField(name = "FLG1")
	public String getFlg1() {
		return flg1;
	}

	public void setFlg1(String flg1) {
		this.flg1 = flg1;
	}

	@JSONField(name = "FLG2")
	public String getFlg2() {
		return flg2;
	}

	public void setFlg2(String flg2) {
		this.flg2 = flg2;
	}

	@JSONField(name = "FLG3")
	public String getFlg3() {
		return flg3;
	}

	public void setFlg3(String flg3) {
		this.flg3 = flg3;
	}

	@JSONField(name = "FLG4")
	public String getFlg4() {
		return flg4;
	}

	public void setFlg4(String flg4) {
		this.flg4 = flg4;
	}

	@JSONField(name = "FLG5")
	public String getFlg5() {
		return flg5;
	}

	public void setFlg5(String flg5) {
		this.flg5 = flg5;
	}

	@JSONField(name = "RESERVED")
	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@JSONField(name = "FRG_TYPE")
	public String getFrgType() {
		return frgType;
	}

	public void setFrgType(String frgType) {
		this.frgType = frgType;
	}

	@JSONField(name = "FRG_CODE")
	public String getFrgCode() {
		return frgCode;
	}

	public void setFrgCode(String frgCode) {
		this.frgCode = frgCode;
	}

	@JSONField(name = "AC_CLASS")
	public String getAcClass() {
		return acClass;
	}

	public void setAcClass(String acClass) {
		this.acClass = acClass;
	}

	@JSONField(name = "FTF_FLG")
	public String getFtfFlg() {
		return ftfFlg;
	}

	public void setFtfFlg(String ftfFlg) {
		this.ftfFlg = ftfFlg;
	}

	@JSONField(name = "RMK")
	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	@JSONField(name = "NON_STD_AC_CNT")
	public String getNonStdAcCnt() {
		return nonStdAcCnt;
	}

	public void setNonStdAcCnt(String nonStdAcCnt) {
		this.nonStdAcCnt = nonStdAcCnt;
	}

	@Override
	public String toString() {
		return "BankCore358040Rsp [ac=" + HiddenUtil.acHidden(ac) + ", acNo=" + HiddenUtil.acHidden(acNo) + ", acSeq=" + acSeq + ", ccy=" + ccy + ", ciNo=" + ciNo
				+ ", ciSts=" + ciSts + ", type=" + type + ", idType=" + idType + ", idNo=" + HiddenUtil.certIdHidden(idNo) + ", idAvalDt="
				+ idAvalDt + ", ciName=" + HiddenUtil.acNameHidden(ciName) + ", ciLvl=" + ciLvl + ", sex=" + sex + ", telNo=" + HiddenUtil.telNoHidden(telNo)
				+ ", addrType=" + addrType + ", addrFlg=" + addrFlg + ", addrNm=" + HiddenUtil.acHidden(addrNm) + ", addrCntyCd="
				+ addrCntyCd + ", addrPostCd=" + addrPostCd + ", acPrdCode=" + acPrdCode + ", acPrdName=" + acPrdName
				+ ", acAttr=" + acAttr + ", acType=" + acType + ", acSts=" + acSts + ", acStsw=" + acStsw
				+ ", acBlockSts=" + acBlockSts + ", amtHoldSts=" + amtHoldSts + ", acBr=" + acBr + ", acFh=" + acFh
				+ ", openDate=" + openDate + ", lastDate=" + lastDate + ", bookBr=" + bookBr + ", domBr=" + domBr
				+ ", term=" + term + ", instrMth=" + instrMth + ", ccyTyp=" + ccyTyp + ", intRat=" + intRat
				+ ", acEname=" + acEname + ", acCname=" + HiddenUtil.acNameHidden(acCname) + ", ledgerBal=" + ledgerBal + ", availBal="
				+ availBal + ", holdBal=" + holdBal + ", agrDepFlg=" + agrDepFlg + ", agrRat=" + agrRat + ", agrAmt="
				+ agrAmt + ", drawMth=" + drawMth + ", drcrFlg=" + drcrFlg + ", crosDrFlg=" + crosDrFlg + ", crosCrFlg="
				+ crosCrFlg + ", pbSts=" + pbSts + ", cardMedi=" + cardMedi + ", cardSts=" + cardSts + ", cardStsw="
				+ cardStsw + ", cardProd=" + cardProd + ", cardProdNm=" + cardProdNm + ", cardJointTyp=" + cardJointTyp
				+ ", cardPcTyp=" + cardPcTyp + ", cardPvTyp=" + cardPvTyp + ", cardMobiTyp=" + cardMobiTyp
				+ ", cardLinkTyp=" + cardLinkTyp + ", cardCatlg=" + cardCatlg + ", issueBr=" + issueBr + ", brNm="
				+ brNm + ", issueDt=" + issueDt + ", expDt=" + expDt + ", item=" + item + ", stdAcFlg=" + stdAcFlg
				+ ", nonStdActyp=" + nonStdActyp + ", mainAc=" + mainAc + ", stdAc=" + stdAc + ", spcKind=" + spcKind
				+ ", frgInd=" + frgInd + ", yyzz=" + yyzz + ", ciStsw=" + ciStsw + ", socialFlg=" + socialFlg
				+ ", acPurp=" + acPurp + ", relType=" + relType + ", relName=" + relName + ", flg1=" + flg1 + ", flg2="
				+ flg2 + ", flg3=" + flg3 + ", flg4=" + flg4 + ", flg5=" + flg5 + ", reserved=" + reserved
				+ ", frgType=" + frgType + ", frgCode=" + frgCode + ", acClass=" + acClass + ", ftfFlg=" + ftfFlg
				+ ", rmk=" + rmk + ", nonStdAcCnt=" + nonStdAcCnt + "]";
	}
	
	
}
