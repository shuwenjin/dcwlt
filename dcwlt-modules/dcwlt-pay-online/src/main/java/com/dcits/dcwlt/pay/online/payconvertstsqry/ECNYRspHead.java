package com.dcits.dcwlt.pay.online.payconvertstsqry;

/**
 * @author zhanguohai
 * @Time 2021/1/3 15:46
 * @Version 1.0
 * Description:五羊支付响应报文头
 */
public class ECNYRspHead {
    private String trxStatus;      //交易状态


    public ECNYRspHead() {
    }

    public ECNYRspHead(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    public String getTrxStatus() {
        return trxStatus;
    }

    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }


    @Override
    public String toString() {
        return "ECNYRspHead{" +
                "trxStatus='" + trxStatus + '\'' +
                '}';
    }
}
