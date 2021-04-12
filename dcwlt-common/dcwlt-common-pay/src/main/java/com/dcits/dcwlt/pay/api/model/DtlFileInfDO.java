package com.dcits.dcwlt.pay.api.model;


/**
 *
 * @version 1.0.0
 * Description:对账汇总文件明细数据库实体
 * @Date 2021/1/5 14:57
 */
public class DtlFileInfDO {
    // 报文标识
    private String msgId;
    //交易批次号
    private String batchId;
    //源文件路径
    private String srcFilePath;
    //文件名
    private String fileName;
    //最后更新日期
    private String lastUpDate;
    //最后更新时间
    private String lastUpTime;
    //init:未下载proc:下载中succ:已下载
    private String fileProcStatus;
    //本地文件路径
    private String localFilePath;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getFileProcStatus() {
        return fileProcStatus;
    }

    public void setFileProcStatus(String fileProcStatus) {
        this.fileProcStatus = fileProcStatus;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    @Override
    public String toString() {
        return "CheckFilelistDO{" +
                "msgId='" + msgId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", srcFilePath='" + srcFilePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }



}
