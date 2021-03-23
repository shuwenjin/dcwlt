package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
public class FileName {
    @NotNull(message = "文件名称不能为空")
    @Length(max = 64)
    private String fileName;

    @JSONField(name = "FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileName{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
