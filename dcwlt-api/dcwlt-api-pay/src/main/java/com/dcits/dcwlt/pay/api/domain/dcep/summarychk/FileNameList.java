package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class FileNameList {
    private List<String> fileName;

    @JSONField(name = "FileName")
    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(List<String> fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileNameList{" +
                "fileName=" + fileName +
                '}';
    }
}
