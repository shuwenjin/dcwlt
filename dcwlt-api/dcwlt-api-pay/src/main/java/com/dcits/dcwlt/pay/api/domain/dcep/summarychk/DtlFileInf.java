package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @Time 2021/1/3 14:57
 * @Version 1.0
 * Description: 对账明细文件信息
 */
public class DtlFileInf {
    /*
     * 对账明细文件总文件数
     * */
    @Length(max = 10)
    private String fileInfNb;
    /*
     * 对账明细文件列表
     * */
    @Valid
    private List<FileInf> fileInfList;

    @JSONField(name = "FileInfNb")
    public String getFileInfNb() {
        return fileInfNb;
    }

    public void setFileInfNb(String fileInfNb) {
        this.fileInfNb = fileInfNb;
    }

    @JSONField(name = "FileInfList")
    public List<FileInf> getFileInfList() {
        return fileInfList;
    }

    public void setFileInfList(List<FileInf> fileInfList) {
        this.fileInfList = fileInfList;
    }

    @Override
    public String toString() {
        return "DtlFileInf{" +
                "fileInfNb='" + fileInfNb + '\'' +
                ", fileInfList=" + fileInfList +
                '}';
    }
}
