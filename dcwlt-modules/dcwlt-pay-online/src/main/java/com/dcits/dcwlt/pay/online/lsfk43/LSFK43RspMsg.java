package com.dcits.dcwlt.pay.online.lsfk43;


public class LSFK43RspMsg{

    private String senderSN;                  // 发起方请求流水号
    private String resultCode;                // 接收结果
    private String searchResult;              // 查询结果

    public String getSenderSN() {
        return senderSN;
    }

    public void setSenderSN(String senderSN) {
        this.senderSN = senderSN;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public String toString() {
        return "LSFK43RspMsg [senderSN=" + senderSN + ", resultCode=" + resultCode + ", searchResult=" + searchResult
                + "]";
    }
}
