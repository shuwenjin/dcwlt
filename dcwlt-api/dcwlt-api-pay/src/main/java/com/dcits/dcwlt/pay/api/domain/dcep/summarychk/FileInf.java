package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FileInf {
    /*
     *文件路径
     * */
    @Length(max = 64)
    @NotNull(message = "文件路径不能为空")
    private String filePath;
    /*
     * 文件名称列表
     * */
    private List<String> fileNameList;

    @JSONField(name = "FilePath")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @JSONField(name = "FileNameList")
    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    @Override
    public String toString() {
        return "FileInfList{" +
                "filePath='" + filePath + '\'' +
                ", fileNameList=" + fileNameList +
                '}';
    }
}
