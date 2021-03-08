/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年4月6日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100;

public class BankCore351100InnerRsp {
    private String coreStatus;          // 核心状态
    private String errorCode;           // 处理码
    private String errorMsg;            // 处理信息
    private String coreReqDate;         // 核心请求日期
    private String coreReqSerno;        // 核心请求流水
    private String coreRspDate;         // 核心应答日期（核心会计日期）
    private String coreRspSerno;        // 核心应答流水（核心会计流水）
    private String ac2RvsNo;            // 贷方挂账编号
    private String hostDate;            // 核心处理日期（核心生成的流水文件，以该日期取数）


    public BankCore351100InnerRsp() {
    }

    public BankCore351100InnerRsp(String coreStatus, String errorCode, String errorMsg) {
        this.coreStatus = coreStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getCoreStatus() {
        return coreStatus;
    }

    public void setCoreStatus(String coreStatus) {
        this.coreStatus = coreStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCoreReqDate() {
        return coreReqDate;
    }

    public void setCoreReqDate(String coreReqDate) {
        this.coreReqDate = coreReqDate;
    }

    public String getCoreReqSerno() {
        return coreReqSerno;
    }

    public void setCoreReqSerno(String coreReqSerno) {
        this.coreReqSerno = coreReqSerno;
    }

    public String getCoreRspSerno() {
        return coreRspSerno;
    }

    public void setCoreRspSerno(String coreRspSerno) {
        this.coreRspSerno = coreRspSerno;
    }

    public String getCoreRspDate() {
        return coreRspDate;
    }

    public void setCoreRspDate(String coreRspDate) {
        this.coreRspDate = coreRspDate;
    }

    public String getAc2RvsNo() {
        return ac2RvsNo;
    }

    public void setAc2RvsNo(String ac2RvsNo) {
        this.ac2RvsNo = ac2RvsNo;
    }

    public String getHostDate() {
        return hostDate;
    }

    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    @Override
    public String toString() {
        return "BankCore351100InnerRsp [coreStatus=" + coreStatus + ", errorCode=" + errorCode + ", errorMsg="
                + errorMsg + ", coreReqDate=" + coreReqDate + ", coreReqSerno=" + coreReqSerno + ", coreRspDate="
                + coreRspDate + ", coreRspSerno=" + coreRspSerno + ", hostDate=" + hostDate + ", ac2RvsNo=" + ac2RvsNo + "]";
    }

}
