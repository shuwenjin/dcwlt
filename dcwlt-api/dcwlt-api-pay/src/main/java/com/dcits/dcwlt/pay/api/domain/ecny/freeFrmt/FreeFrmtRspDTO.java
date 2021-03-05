package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * @author zhanguohai
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:自由格式报文服务化接口响应
 */
public class FreeFrmtRspDTO extends ECNYRspBody {
    private String processCode;

    public FreeFrmtRspDTO() {
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    @Override
    public String toString() {
        return "FreeFrmtRspDTO{" +
                "processCode='" + processCode + '\'' +
                '}';
    }
}
