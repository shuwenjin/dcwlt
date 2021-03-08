package com.dcits.dcwlt.pay.online.lsfk43;

/**
 * @author zhangwang
 * @date 2021/1/21
 * @version 1.0
 * <p>反洗錢服務化接口信息</p>
 */
public class LSFK43InfoDTO  {


    private static LSFK43InfoDTO instance = new LSFK43InfoDTO();

    static {
        instance.setVersionNo("2.0");
        instance.setTradeCode("LSFK43");
    }

    /**
     * 饿汉式单例模式
     * @return LSFK43InfoDTO
     */
    public static LSFK43InfoDTO getInstance(){
        return instance;
    }



    /**
     * 版本
     */
    private String versionNo;

    /**
     * 接口编码
     * 网关或非金融交换接入时不需要；大数据内部系统接入前置必须添加此报文头，并使用有前置应用分配的接口编码。
     */
    private String tradeCode;

    /**
     * 源发起方流水
     * 网关或非金融交换接入时不需要，对大数据内部加上
     */
    private String senderSN;


    /**
     * 源发起方日期
     * 交易发起方日期,格式:YYYYMMDD
     */
    private String orSenderDate;

    /**
     * 源发起方时间
     * 交易发起方时间,格式:hhmmss
     */
    private String orSenderTime;

    /**
     * 预留字段
     */
    private String remark;


    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getSenderSN() {
        return senderSN;
    }

    public void setSenderSN(String senderSN) {
        this.senderSN = senderSN;
    }

    public String getOrSenderDate() {
        return orSenderDate;
    }

    public void setOrSenderDate(String orSenderDate) {
        this.orSenderDate = orSenderDate;
    }

    public String getOrSenderTime() {
        return orSenderTime;
    }

    public void setOrSenderTime(String orSenderTime) {
        this.orSenderTime = orSenderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "LSFK43InfoDTO{" +
                "versionNo='" + versionNo + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", senderSN='" + senderSN + '\'' +
                ", orSenderDate='" + orSenderDate + '\'' +
                ", orSenderTime='" + orSenderTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

}
