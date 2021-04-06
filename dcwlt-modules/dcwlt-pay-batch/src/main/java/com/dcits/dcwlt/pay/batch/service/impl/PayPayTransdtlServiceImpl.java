package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.batch.mapper.PayPayTransdtlMapper;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 金融交易登记Service业务层处理
 * 
 * @author 
 * @date 2021-03-03
 */
@Service
public class PayPayTransdtlServiceImpl implements IPayPayTransdtlService
{

    @Autowired
    public PayPayTransdtlMapper payPayTransdtlMapper;

    /**
     * 查询金融交易登记列表
     *
     * @param payPayTransdtl 金融交易登记
     * @return 金融交易登记
     */
    @Override
    public List<PayTransDtlInfoDO> selectPayPayTransdtlList(PayTransDtlInfoDO payPayTransdtl)
    {
        return payPayTransdtlMapper.selectPayPayTransdtlList(payPayTransdtl);
    }
}
