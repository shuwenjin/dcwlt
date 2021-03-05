package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;

import java.util.List;

/**
 * 非金融登记簿Mapper接口
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
public interface PayPayTransdtlNonfMapper 
{
    /**
     * 查询非金融登记簿
     * 
     * @param msgid 非金融登记簿ID
     * @return 非金融登记簿
     */
    public PayTransDtlNonfDO selectPayPayTransdtlNonfById(String msgid);

    /**
     * 查询非金融登记簿列表
     * 
     * @param payTransDtlNonfDO 非金融登记簿
     * @return 非金融登记簿集合
     */
    public List<PayTransDtlNonfDO> selectPayPayTransdtlNonfList(PayTransDtlNonfDO payTransDtlNonfDO);

    /**
     * 新增非金融登记簿
     * 
     * @param payTransDtlNonfDO 非金融登记簿
     * @return 结果
     */
    public int insertPayPayTransdtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);

    /**
     * 修改非金融登记簿
     * 
     * @param payTransDtlNonfDO 非金融登记簿
     * @return 结果
     */
    public int updatePayPayTransdtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);

    /**
     * 删除非金融登记簿
     * 
     * @param msgid 非金融登记簿ID
     * @return 结果
     */
    public int deletePayPayTransdtlNonfById(String msgid);

    /**
     * 批量删除非金融登记簿
     * 
     * @param msgids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayPayTransdtlNonfByIds(String[] msgids);
}
