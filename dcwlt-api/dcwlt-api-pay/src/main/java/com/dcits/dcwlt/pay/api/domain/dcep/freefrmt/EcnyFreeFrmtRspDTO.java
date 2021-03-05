package com.dcits.dcwlt.pay.api.domain.dcep.freefrmt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author wanyangwei
 * @desc 自由格式响应互联互通DTO
 */
public class EcnyFreeFrmtRspDTO extends ECNYRspBody {
    @NotNull
    @Length(max = 64)
    @Valid
    private String procResult;          //处理结果

    @JSONField(name = "ProcResult")
    public String getProcResult() {
        return procResult;
    }

    public void setProcResult(String procResult) {
        this.procResult = procResult;
    }

    @Override
    public String toString() {
        return "EcnyFreeFrmtRspDTO{" +
                "procResult='" + procResult + '\'' +
                '}';
    }
}
