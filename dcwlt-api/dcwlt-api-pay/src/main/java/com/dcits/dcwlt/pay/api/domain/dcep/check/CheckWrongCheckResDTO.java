package com.dcits.dcwlt.pay.api.domain.dcep.check;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * @author maozewu
 * @Date 2021/1/26
 * @Version 1.0
 * Description:对账不平结果重对服务化接口响应
 */
public class CheckWrongCheckResDTO extends ECNYRspBody {
    private String procResult;   //处理结果

    public CheckWrongCheckResDTO() {
    }

	public String getProcResult() {
		return procResult;
	}

	public void setProcResult(String procResult) {
		this.procResult = procResult;
	}

	@Override
	public String toString() {
		return "CheckWrongCheckResDTO [procResult=" + procResult + "]";
	}

    
}
