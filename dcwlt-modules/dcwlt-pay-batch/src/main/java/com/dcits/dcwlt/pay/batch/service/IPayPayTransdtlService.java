package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;

import java.util.List;

/**
 * 金融交易登记Service接口
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
public interface IPayPayTransdtlService 
{
    /**
     * 查询金融交易登记列表
     * 
     * @param payPayTransdtl 金融交易登记
     * @return 金融交易登记集合
     */
    List<PayTransDtlInfoDO> selectPayPayTransdtlList(PayTransDtlInfoDO payPayTransdtl);


}
