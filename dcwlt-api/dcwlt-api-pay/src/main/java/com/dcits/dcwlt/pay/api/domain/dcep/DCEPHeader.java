package com.dcits.dcwlt.pay.api.domain.dcep;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.ISODateTime;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 互联互通报文头格式
 *
 *
 */
public class DCEPHeader {

    /**
     * 版本号
     */
    @NotBlank
    @Length(max = 2)
    private String ver;

    /**
     * 报文发送时间
     */
    @NotBlank
    @ISODateTime
    private String sndDtTm;

    /**
     * 报文编号
     */
    @NotBlank
    @Length(max = 15)
    private String msgTp;

    /**
     * 通讯级标识号
     */
    @NotBlank
    @Length(max = 40)
    private String msgSn;

    /**
     * 发起方机构
     */
    @NotBlank
    @Length(max = 14)
    private String sender;

    /**
     * 接收方机构
     */
    @NotBlank
    @Length(max = 14)
    private String receiver;

    /**
     * 签名证书序列号
     */
    @NotBlank
    @Length(max = 10)
    private String signSN;

    /**
     * 加密证书序列号
     */
    private String ncrptnSN;

    /**
     * 数字信封
     */
    private String dgtlEnvlp;


    /**
     * 获取响应报文头
     *
     * @param reqHead
     * @param msgTp
     * @return
     */
    public static DCEPHeader getDefaultRspHead(DCEPHeader reqHead, String msgTp) {
        DCEPHeader rspHead = new DCEPHeader();
        String receiver = reqHead.getReceiver();
        String sender = reqHead.getSender();
        rspHead.setMsgTp(msgTp);
        rspHead.setMsgSn(reqHead.getMsgSn());
        rspHead.setDgtlEnvlp(reqHead.getDgtlEnvlp());
        rspHead.setNcrptnSN(reqHead.getNcrptnSN());
        rspHead.setReceiver(sender);
        rspHead.setSender(receiver);
        rspHead.setSignSN(reqHead.getSignSN());
        rspHead.setSndDtTm(DateUtil.getISODateTime());
        rspHead.setVer(reqHead.getVer());
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
        reqHead.setVer("01");
        reqHead.setSndDtTm(DateUtil.getISODateTime());
        reqHead.setSender(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        reqHead.setReceiver(receiver);
        reqHead.setMsgSn(msgSn);
        reqHead.setMsgTp(msgTp);
        return reqHead;
    }

    @JSONField(name = "Ver")
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @JSONField(name = "SndDtTm")
    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    @JSONField(name = "MsgTp")
    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    @JSONField(name = "MsgSN")
    public String getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(String msgSn) {
        this.msgSn = msgSn;
    }

    @JSONField(name = "Sender")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @JSONField(name = "Receiver")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

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

    @Override
    public String toString() {
        return "DCEPHeader [ver=" + ver + ", sndDtTm=" + sndDtTm + ", msgTp=" + msgTp + ", msgSn=" + msgSn + ", sender="
                + sender + ", receiver=" + receiver + ", signSN=" + signSN + ", ncrptnSN=" + ncrptnSN + ", dgtlEnvlp="
                + dgtlEnvlp + "]";
    }

}
