package com.dcits.dcwlt.pay.api.model;

public class StateMachine {

    private String preTrxStatus;// 业务状态

    private String preCoreProcStatus;// 核心处理状态

    private String prePathProcStatus;// 通道处理状态

    public String getPreTrxStatus() {
        return preTrxStatus;
    }

    public void setPreTrxStatus(String preTrxStatus) {
        this.preTrxStatus = preTrxStatus;
    }

    public String getPreCoreProcStatus() {
        return preCoreProcStatus;
    }

    public void setPreCoreProcStatus(String preCoreProcStatus) {
        this.preCoreProcStatus = preCoreProcStatus;
    }

    public String getPrePathProcStatus() {
        return prePathProcStatus;
    }

    public void setPrePathProcStatus(String prePathProcStatus) {
        this.prePathProcStatus = prePathProcStatus;
    }

    @Override
    public String toString() {
        return "OrigStatusInfoDO{" +
                "preTrxStatus='" + preTrxStatus + '\'' +
                ", preCoreProcStatus='" + preCoreProcStatus + '\'' +
                ", prePathProcStatus='" + prePathProcStatus + '\'' +
                '}';
    }
}
