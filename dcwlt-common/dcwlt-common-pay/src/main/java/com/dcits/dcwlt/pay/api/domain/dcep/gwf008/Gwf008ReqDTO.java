package com.dcits.dcwlt.pay.api.domain.dcep.gwf008;

import com.alibaba.fastjson.annotation.JSONField;
import javax.validation.constraints.NotNull;

/**
 * GWF003-下载商户文件请求报文
 *
 * @author
 * @date 2021/1/5
 */
public class Gwf008ReqDTO extends CommonReqBody {

    private static final String DEFAULT_SRC_MER_ID = "000000";

    @NotNull
    private String merId;       //目标系统商户号

    @NotNull
    private String appId;       //目标系统APPID  目标系统在开放平台的系统标识

    @NotNull
    @JSONField(name = "srcMerid")
    private String srcMerId = DEFAULT_SRC_MER_ID;    //源系统商户号 默认值：000000

    @NotNull
    private String channel;     //文件类型

    @NotNull
    private String fileType;    //文件类型

    @NotNull
    private String fileName;    //文件名

    @NotNull
    private String filePath;    //商户地址

    @NotNull
    private String transDate;   //业务日期

    private String overWrite;   //文件覆盖标识 //1：覆盖 用于文件有修改但是文件名重复时不可下载场景，添加此标识可重新下载文件

    @NotNull
    @JSONField(name = "exchange_key")
    private String exChangeKey;  //数字信封   由711报文中的数字信封字段决定，五羊支付提供

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSrcMerId() {
        return srcMerId;
    }

    public void setSrcMerId(String srcMerId) {
        this.srcMerId = srcMerId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getOverWrite() {
        return overWrite;
    }

    public void setOverWrite(String overWrite) {
        this.overWrite = overWrite;
    }

    public String getExChangeKey() {
        return exChangeKey;
    }

    public void setExChangeKey(String exChangeKey) {
        this.exChangeKey = exChangeKey;
    }

    @Override
    public String toString() {
        return "Gwf008ReqDTO{" +
                "merId='" + merId + '\'' +
                ", appId='" + appId + '\'' +
                ", srcMerId='" + srcMerId + '\'' +
                ", channel='" + channel + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", transDate='" + transDate + '\'' +
                ", overWrite='" + overWrite + '\'' +
                ", exChangeKey='" + exChangeKey + '\'' +
                '}';
    }
}
