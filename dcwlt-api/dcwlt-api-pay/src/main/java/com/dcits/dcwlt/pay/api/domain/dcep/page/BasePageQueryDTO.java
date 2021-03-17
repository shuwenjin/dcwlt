package com.dcits.dcwlt.pay.api.domain.dcep.page;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;

/**
 * 分页查询基础参数类
 */
public class BasePageQueryDTO extends ECNYReqBody {

    //上下翻页操作， 0首页，1上翻页，2 下翻， 3尾页
    protected String queryPageFlag;

    //查询条数
    protected String count;

    public String getQueryPageFlag() {
        return queryPageFlag;
    }

    public void setQueryPageFlag(String queryPageFlag) {
        this.queryPageFlag = queryPageFlag;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BasePageQueryDTO{" +
                "queryPageFlag='" + queryPageFlag + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
