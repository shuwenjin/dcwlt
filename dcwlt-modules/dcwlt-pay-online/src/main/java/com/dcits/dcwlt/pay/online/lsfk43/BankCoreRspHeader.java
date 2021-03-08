package com.dcits.dcwlt.pay.online.lsfk43;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhanguohai
 * @Time 2020年3月30日下午7:31:05
 * @Version v1.0
 *          <p>
 * 			Description: 核心服务化响应报文头
 *          </p>
 */
public class BankCoreRspHeader {
	@NotBlank
	@Length(max = 1, min = 1)
	private String retStatus;// 交易状态

	@NotBlank
	@Length(max = 8)
	private String hostDate;// 核心系统处理日期

	@NotBlank
	@Length(max = 6)
	private String hostTime;// 核心系统处理时间

	@NotBlank
	@Length(max = 8)
	private String acDate;// 服务入账日期

	@NotBlank
	@Length(max = 12)
	private String jrnNo;// 核心日志号

	@Length(max = 12)
	private String vchNo;// 核心传票号

	@NotBlank
	@Length(max = 3, min = 1)
	private String trBank;// 交易银行号

	@Length(max = 6)
	private String trBranch;// 交易机构号

	@Length(max = 32)
	private String syncId;// 同步标识

	@Length(max = 16)
	private String mac;// MAC校验值

	@JSONField(name = "RET_STATUS")
	public String getRetStatus() {
		return retStatus;
	}

	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}

	@JSONField(name = "HOST_DATE")
	public String getHostDate() {
		return hostDate;
	}

	public void setHostDate(String hostDate) {
		this.hostDate = hostDate;
	}

	@JSONField(name = "HOST_TIME")
	public String getHostTime() {
		return hostTime;
	}

	public void setHostTime(String hostTime) {
		this.hostTime = hostTime;
	}

	@JSONField(name = "AC_DATE")
	public String getAcDate() {
		return acDate;
	}

	public void setAcDate(String acDate) {
		this.acDate = acDate;
	}

	@JSONField(name = "JRN_NO")
	public String getJrnNo() {
		return jrnNo;
	}

	public void setJrnNo(String jrnNo) {
		this.jrnNo = jrnNo;
	}

	@JSONField(name = "VCH_NO")
	public String getVchNo() {
		return vchNo;
	}

	public void setVchNo(String vchNo) {
		this.vchNo = vchNo;
	}

	@JSONField(name = "TR_BANK")
	public String getTrBank() {
		return trBank;
	}

	public void setTrBank(String trBank) {
		this.trBank = trBank;
	}

	@JSONField(name = "TR_BRANCH")
	public String getTrBranch() {
		return trBranch;
	}

	public void setTrBranch(String trBranch) {
		this.trBranch = trBranch;
	}

	@JSONField(name = "SYNC_ID")
	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}

	@JSONField(name = "MAC")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "BankCoreRspHeader [retStatus=" + retStatus + ", hostDate=" + hostDate + ", hostTime=" + hostTime
				+ ", acDate=" + acDate + ", jrnNo=" + jrnNo + ", vchNo=" + vchNo + ", trBank=" + trBank + ", trBranch="
				+ trBranch + ", syncId=" + syncId + ", mac=" + mac + "]";
	}

}
