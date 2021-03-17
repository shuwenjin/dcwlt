package com.dcits.dcwlt.pay.api.domain.ecny.dspt;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * @author zhanguohai
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:差错调账接口响应
 */
public class DsptChnlRspDTO extends ECNYRspBody {
    /**
     * 处理结果
     */
    private String procResult;

    public String getProcResult() {
        return procResult;
    }

    public void setProcResult(String procResult) {
        this.procResult = procResult;
    }

    @Override
    public String toString() {
        return "DsptSRspDTO{" +
                "procResult='" + procResult + '\'' +
                '}';
    }
}
