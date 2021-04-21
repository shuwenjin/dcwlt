package com.dcits.dcwlt.pay.api.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.mapper.PayPayTransdtlNonfMapper;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.api.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易重发申请Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-04-21
 */
@Service
public class PayPayTransdtlNonfServiceImpl implements IPayPayTransdtlNonfService
{
    @Autowired
    private PayPayTransdtlNonfMapper payPayTransdtlNonfMapper;

    /**
     * 查询交易重发申请列表
     * 
     * @param payPayTransdtlNonf 交易重发申请
     * @return 交易重发申请
     */
    @Override
    public List<PayTransDtlNonfDO> selectPayPayTransdtlNonfList(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        return payPayTransdtlNonfMapper.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
    }
}
