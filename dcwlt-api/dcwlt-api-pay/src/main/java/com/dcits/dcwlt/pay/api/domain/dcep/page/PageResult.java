package com.dcits.dcwlt.pay.api.domain.dcep.page;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

import java.util.List;

/**
 * @desc 分页查询返回对象
 *
 * @author wanyangwei
 *
 */
public class PageResult<T> extends ECNYRspBody {
    private String firstPageFlag;       //首页标识
    private String lastPageFlag;        //尾页标识
    private String returnCount;         //返回条数
    private List<T> list;               //返回数据对象集合

    public PageResult() {
    }

    public PageResult(String firstPageFlag, String lastPageFlag, String returnCount, List<T> list) {
        this.firstPageFlag = firstPageFlag;
        this.lastPageFlag = lastPageFlag;
        this.returnCount = returnCount;
        this.list = list;
    }

    public String getFirstPageFlag() {

        return firstPageFlag;
    }

    public void setFirstPageFlag(String firstPageFlag) {
        this.firstPageFlag = firstPageFlag;
    }

    public String getLastPageFlag() {
        return lastPageFlag;
    }

    public void setLastPageFlag(String lastPageFlag) {
        this.lastPageFlag = lastPageFlag;
    }

    public String getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(String returnCount) {
        this.returnCount = returnCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "firstPageFlag='" + firstPageFlag + '\'' +
                ", lastPageFlag='" + lastPageFlag + '\'' +
                ", returnCount='" + returnCount + '\'' +
                ", list=" + list +
                '}';
    }
}
