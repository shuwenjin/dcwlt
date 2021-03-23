package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import java.util.List;

public class FileInfList {
    @Valid
    private List<FileInf> fileInf;

    @JSONField(name = "FileInf")
    public List<FileInf> getFileInf() {
        return fileInf;
    }

    public void setFileInf(List<FileInf> fileInf) {
        this.fileInf = fileInf;
    }

    @Override
    public String toString() {
        return "FileInfList{" +
                "fileInf=" + fileInf +
                '}';
    }
}
