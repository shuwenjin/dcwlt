package com.dcits.dcwlt.pay.api.service;

import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;

import java.util.List;

/**
 * 交易重发申请Service接口
 * 
 * @author dcwlt
 * @date 2021-04-21
 */
public interface IPayPayTransdtlNonfService 
{

    /**
     * 查询交易重发申请列表
     * 
     * @param payPayTransdtlNonf 交易重发申请
     * @return 交易重发申请集合
     */
    public List<PayTransDtlNonfDO> selectPayPayTransdtlNonfList(PayTransDtlNonfDO payPayTransdtlNonf);

}
