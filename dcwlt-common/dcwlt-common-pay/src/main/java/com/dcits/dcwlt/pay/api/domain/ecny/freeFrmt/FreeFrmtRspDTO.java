package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * @author
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:自由格式报文服务化接口响应
 */
public class FreeFrmtRspDTO extends ECNYRspBody {
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
        return "FreeFrmtRspDTO{" +
                "procResult='" + procResult + '\'' +
                '}';
    }
}
