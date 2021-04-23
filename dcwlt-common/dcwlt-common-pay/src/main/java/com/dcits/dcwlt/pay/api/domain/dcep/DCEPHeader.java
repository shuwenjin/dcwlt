package com.dcits.dcwlt.pay.api.domain.dcep;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MesgDirectionEnum;
import com.dcits.dcwlt.common.pay.enums.MesgPriorityEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.DateTime;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 互联互通报文头格式
 */
public class DCEPHeader {
/***************************
 *  DCEP HEAD start 互联互通报文头
 ***************************/
//    /**
//     * 版本号
//     */
//    @NotBlank
//    @Length(max = 2)
//    private String ver;
//
//    /**
//     * 报文发送时间
//     */
//    @NotBlank
//    @ISODateTime
//    private String sndDtTm;
//
//    /**
//     * 报文编号
//     */
//    @NotBlank
//    @Length(max = 15)
//    private String msgTp;
//
//    /**
//     * 通讯级标识号
//     */
//    @NotBlank
//    @Length(max = 40)
//    private String msgSn;
//
//    /**
//     * 发起方机构
//     */
//    @NotBlank
//    @Length(max = 14)
//    private String sender;
//
//    /**
//     * 接收方机构
//     */
//    @NotBlank
//    @Length(max = 14)
//    private String receiver;
//
    /**
     * 签名证书序列号
     */
    private String signSN;

    /**
     * 加密证书序列号
     */
    private String ncrptnSN;

    /**
     * 数字信封
     */
    private String dgtlEnvlp;

/***************************
 *  DCPS HEAD end 互联互通报文头
 ***************************/
/***************************
 *  DCPS HEAD start 城银清报文头
 ***************************/
    /**
     * 版本号
     */
    @Length(max = 2)
    @NotBlank
    private String versionID;
    /**
     * 报文发起人
     */
    @Length(max = 14)
    @NotBlank
    private String origSender;
    /**
     * 发送系统号
     */
    @Length(max = 4)
    private String origSenderSID;
    /**
     * /**
     * 报文接收人
     */
    @Length(max = 14)
    @NotBlank
    private String origReceiver;
    /**
     * 接收系统号
     */
    @Length(max = 4)
    private String origReceiverSID;
    /**
     * 报文发起日期
     */
    @Length(max = 8)
    @DateTime(pattern = "yyyyMMdd")
    private String origSendDate;
    /**
     * 报文发起时间
     */
    @Length(max = 6)
    @DateTime(pattern = "HHMMSS")
    private String origSendTime;
    /**
     * 格式类型
     * XML
     */
    @Length(max = 3)
    private String structType;
    /**
     * 报文类型代码
     */
    @Length(max = 20)
    @NotBlank
    private String mesgType;
    /**
     * 通信级标识号
     */
    @Length(max = 40)
    @NotBlank
    private String mesgID;
    /**
     * 通信级参考号
     */
    @Length(max = 40)
    private String mesgRefID;
    /**
     * 报文优先级
     * 1：特急
     * 2：紧急
     * 3：普通
     */
    @Length(max = 1)
    private String mesgPriority;
    /**
     * 报文传输方向
     * 由参与机构发出：U
     * 由城银清算发出：D
     */
    @Length(max = 1)
    private String mesgDirection;
    /**
     * 发起人保留域
     */
    @Length(max = 10)
    private String sderReserved;
    /**
     * 接收人保留域
     */
    @Length(max = 10)
    private String rcverReserved;
    /**
     * 城银清算保留域
     */
    @Length(max = 10)
    private String centerReserved;
    /**
     * 保留域
     */
    @Length(max = 9)
    private String reserve;

/***************************
 *  DCPS HEAD start 城银清报文头
 ***************************/
    /**
     * 获取响应报文头
     *
     * @param reqHead
     * @param msgTp
     * @return
     */
    public static DCEPHeader getDefaultRspHead(DCEPHeader reqHead, String msgTp) {
        DCEPHeader rspHead = new DCEPHeader();
//        String receiver = reqHead.getReceiver();
//        String sender = reqHead.getSender();
//        rspHead.setMsgTp(msgTp);
//        rspHead.setMsgSn(reqHead.getMsgSn());
//        rspHead.setDgtlEnvlp(reqHead.getDgtlEnvlp());
//        rspHead.setNcrptnSN(reqHead.getNcrptnSN());
//        rspHead.setReceiver(sender);
//        rspHead.setSender(receiver);
//        rspHead.setSignSN(reqHead.getSignSN());
//        rspHead.setSndDtTm(DateUtil.getISODateTime());
//        rspHead.setVer(reqHead.getVer());
        //=========================================================
        rspHead.setVersionID(reqHead.getVersionID());
        rspHead.setStructType(reqHead.getStructType());

        rspHead.setMesgID(reqHead.getMesgID());
        rspHead.setCenterReserved(reqHead.getCenterReserved());
        rspHead.setMesgDirection(reqHead.getMesgDirection());
        rspHead.setMesgPriority(reqHead.getMesgPriority());
        rspHead.setMesgRefID(reqHead.getMesgRefID());
        rspHead.setMesgType(msgTp);
        rspHead.setOrigReceiver(reqHead.getOrigReceiver());
        rspHead.setOrigSendDate(reqHead.getOrigSendDate());
        rspHead.setOrigReceiverSID(reqHead.getOrigReceiverSID());
        rspHead.setOrigSender(reqHead.getOrigSender());
        rspHead.setOrigSenderSID(reqHead.getOrigSenderSID());
        rspHead.setOrigSendTime(reqHead.getOrigSendTime());
        rspHead.setRcverReserved(reqHead.getRcverReserved());
        rspHead.setSderReserved(reqHead.getSderReserved());
        rspHead.setReserve(reqHead.getReserve());
        return rspHead;
    }

    /**
     * 获取请求报文头
     *
     * @param msgSn
     * @param receiver
     * @param msgTp
     * @return
     */
    public static DCEPHeader getDefaultReqHead(String msgSn, String receiver, String msgTp) {
        DCEPHeader reqHead = new DCEPHeader();
//        reqHead.setVer("01");
//        reqHead.setSndDtTm(DateUtil.getISODateTime());
//        reqHead.setSender(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
//        reqHead.setReceiver(receiver);
//        reqHead.setMsgSn(msgSn);
//        reqHead.setMsgTp(msgTp);
//=========================================================
        reqHead.setVersionID("04");
        reqHead.setOrigSenderSID(AppConstant.DCEP_SYS_ID);
        reqHead.setOrigSender(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        reqHead.setOrigReceiver(receiver);
        reqHead.setOrigReceiverSID(AppConstant.DCEP_SYS_ID);
        reqHead.setStructType("XML");
        reqHead.setMesgPriority(MesgPriorityEnum.MSGP3.getCode());
        reqHead.setMesgDirection(MesgDirectionEnum.SEND.getCode());
        reqHead.setOrigSendDate(DateUtil.getDefaultDate());
        reqHead.setOrigSendTime(DateUtil.getDefaultTime());
        reqHead.setMesgType(msgTp);
        reqHead.setMesgID(msgSn);

        return reqHead;
    }

//    @JSONField(name = "Ver")
//    public String getVer() {
//        return ver;
//    }
//
//    public void setVer(String ver) {
//        this.ver = ver;
//    }

    @JSONField(name = "SndDtTm")
    public String getSndDtTm() {
        return DateUtil.formatIsoDateTime(origSendDate + origSendTime);
    }

//    public void setSndDtTm(String sndDtTm) {
//        this.sndDtTm = sndDtTm;
//    }

    @JSONField(name = "MsgTp")
    public String getMsgTp() {
//        return msgTp;
        return mesgType;
    }
//
//    public void setMsgTp(String msgTp) {
//        this.msgTp = msgTp;
//    }
//
//    @JSONField(name = "MsgSN")
    public String getMsgSn() {
//        return msgSn;
        return mesgID;
    }
//
//    public void setMsgSn(String msgSn) {
//        this.msgSn = msgSn;
//    }
//
//    @JSONField(name = "Sender")
    public String getSender() {
//        return sender;
        return origSender;
    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }
//
//    @JSONField(name = "Receiver")
    public String getReceiver() {
//        return receiver;
        return origReceiver;
    }

//    public void setReceiver(String receiver) {
//        this.receiver = receiver;
//    }

    @JSONField(name = "SignSN")
    public String getSignSN() {
        return signSN;
    }

    public void setSignSN(String signSN) {
        this.signSN = signSN;
    }

    @JSONField(name = "NcrptnSN")
    public String getNcrptnSN() {
        return ncrptnSN;
    }

    public void setNcrptnSN(String ncrptnSN) {
        this.ncrptnSN = ncrptnSN;
    }

    @JSONField(name = "DgtlEnvlp")
    public String getDgtlEnvlp() {
        return dgtlEnvlp;
    }

    public void setDgtlEnvlp(String dgtlEnvlp) {
        this.dgtlEnvlp = dgtlEnvlp;
    }

//    @Override
//    public String toString() {
//        return "DCEPHeader [ver=" + ver + ", sndDtTm=" + sndDtTm + ", msgTp=" + msgTp + ", msgSn=" + msgSn + ", sender="
//                + sender + ", receiver=" + receiver + ", signSN=" + signSN + ", ncrptnSN=" + ncrptnSN + ", dgtlEnvlp="
//                + dgtlEnvlp + "]";
//    }


    @JSONField(name = "VersionID")
    public String getVersionID() {
        return versionID;
    }

    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    @JSONField(name = "OrigSender")
    public String getOrigSender() {
        return origSender;
    }

    public void setOrigSender(String origSender) {
        this.origSender = origSender;
    }

    @JSONField(name = "OrigSenderSID")
    public String getOrigSenderSID() {
        return origSenderSID;
    }

    public void setOrigSenderSID(String origSenderSID) {
        this.origSenderSID = origSenderSID;
    }

    @JSONField(name = "OrigReceiver")
    public String getOrigReceiver() {
        return origReceiver;
    }

    public void setOrigReceiver(String origReceiver) {
        this.origReceiver = origReceiver;
    }

    @JSONField(name = "OrigReceiverSID")
    public String getOrigReceiverSID() {
        return origReceiverSID;
    }

    public void setOrigReceiverSID(String origReceiverSID) {
        this.origReceiverSID = origReceiverSID;
    }

    @JSONField(name = "OrigSendDate")
    public String getOrigSendDate() {
        return origSendDate;
    }

    public void setOrigSendDate(String origSendDate) {
        this.origSendDate = origSendDate;
    }

    @JSONField(name = "OrigSendTime")
    public String getOrigSendTime() {
        return origSendTime;
    }

    public void setOrigSendTime(String origSendTime) {
        this.origSendTime = origSendTime;
    }

    @JSONField(name = "StructType")
    public String getStructType() {
        return structType;
    }

    public void setStructType(String structType) {
        this.structType = structType;
    }

    @JSONField(name = "MesgType")
    public String getMesgType() {
        return mesgType;
    }

    public void setMesgType(String mesgType) {
        this.mesgType = mesgType;
    }

    @JSONField(name = "MesgID")
    public String getMesgID() {
        return mesgID;
    }

    public void setMesgID(String mesgID) {
        this.mesgID = mesgID;
    }

    @JSONField(name = "MesgRefID")
    public String getMesgRefID() {
        return mesgRefID;
    }

    public void setMesgRefID(String mesgRefID) {
        this.mesgRefID = mesgRefID;
    }

    @JSONField(name = "MesgPriority")
    public String getMesgPriority() {
        return mesgPriority;
    }

    public void setMesgPriority(String mesgPriority) {
        this.mesgPriority = mesgPriority;
    }

    @JSONField(name = "MesgDirection")
    public String getMesgDirection() {
        return mesgDirection;
    }

    public void setMesgDirection(String mesgDirection) {
        this.mesgDirection = mesgDirection;
    }

    @JSONField(name = "SderReserved")
    public String getSderReserved() {
        return sderReserved;
    }

    public void setSderReserved(String sderReserved) {
        this.sderReserved = sderReserved;
    }

    @JSONField(name = "RcverReserved")
    public String getRcverReserved() {
        return rcverReserved;
    }

    public void setRcverReserved(String rcverReserved) {
        this.rcverReserved = rcverReserved;
    }

    @JSONField(name = "CenterReserved")
    public String getCenterReserved() {
        return centerReserved;
    }

    public void setCenterReserved(String centerReserved) {
        this.centerReserved = centerReserved;
    }

    @JSONField(name = "Reserve")
    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "DCEPHeader{" +
                "VersionID='" + versionID + '\'' +
                ", OrigSender='" + origSender + '\'' +
                ", OrigSenderSID='" + origSenderSID + '\'' +
                ", OrigReceiver='" + origReceiver + '\'' +
                ", OrigReceiverSID='" + origReceiverSID + '\'' +
                ", OrigSendDate='" + origSendDate + '\'' +
                ", OrigSendTime='" + origSendTime + '\'' +
                ", StructType='" + structType + '\'' +
                ", MesgType='" + mesgType + '\'' +
                ", MesgID='" + mesgID + '\'' +
                ", MesgRefID='" + mesgRefID + '\'' +
                ", MesgPriority='" + mesgPriority + '\'' +
                ", MesgDirection='" + mesgDirection + '\'' +
                ", SderReserved='" + sderReserved + '\'' +
                ", RcverReserved='" + rcverReserved + '\'' +
                ", CenterReserved='" + centerReserved + '\'' +
                ", Reserve='" + reserve + '\'' +
                '}';
    }
}
