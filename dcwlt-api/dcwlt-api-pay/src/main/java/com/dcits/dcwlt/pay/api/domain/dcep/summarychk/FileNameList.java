package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import java.util.List;

public class FileNameList {
    @Valid
    private List<FileName> fileName;

    @JSONField(name = "FileName")
    public List<FileName> getFileName() {
        return fileName;
    }

    public void setFileName(List<FileName> fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileNameList{" +
                "fileName=" + fileName +
                '}';
    }
}
