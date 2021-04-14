package com.dcits.dcwlt.pay.api.domain.dcep.gwf008;


import javax.validation.constraints.NotNull;

/**
 *GWF003-下载商户文件请求响应报文
 *
 * @author
 * @date 2021/1/5
 */
public class Gwf008RspDTO extends CommonRspBody {

    @NotNull
    private String errorCode;       //响应码 //000000：成功
    private String errorMsg;        //响应信息
    private String fileName;        //通过文服推送返回给调用方的文件名
    private String filePath;        //通过文服保存的路径

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

    @Override
    public String toString() {
        return "Gwf008RspDTO{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
