package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.mapper.PayPayTransdtlNonfMapper;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 非金融登记簿Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@Service
public class PayPayTransdtlNonfServiceImpl implements IPayPayTransdtlNonfService
{
    @Autowired
    private PayPayTransdtlNonfMapper payPayTransdtlNonfMapper;

    /**
     * 查询非金融登记簿
     * 
     * @param msgid 非金融登记簿ID
     * @return 非金融登记簿
     */
    @Override
    public PayTransDtlNonfDO selectPayPayTransdtlNonfById(String msgid)
    {
        return payPayTransdtlNonfMapper.selectPayPayTransdtlNonfById(msgid);
    }

    /**
     * 查询非金融登记簿列表
     * 
     * @param payPayTransdtlNonf 非金融登记簿
     * @return 非金融登记簿
     */
    @Override
    public List<PayTransDtlNonfDO> selectPayPayTransdtlNonfList(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        return payPayTransdtlNonfMapper.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
    }

    /**
     * 新增非金融登记簿
     * 
     * @param payPayTransdtlNonf 非金融登记簿
     * @return 结果
     */
    @Override
    public int insertPayPayTransdtlNonf(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        return payPayTransdtlNonfMapper.insertPayPayTransdtlNonf(payPayTransdtlNonf);
    }

    /**
     * 修改非金融登记簿
     * 
     * @param payPayTransdtlNonf 非金融登记簿
     * @return 结果
     */
    @Override
    public int updatePayPayTransdtlNonf(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        return payPayTransdtlNonfMapper.updatePayPayTransdtlNonf(payPayTransdtlNonf);
    }

    /**
     * 批量删除非金融登记簿
     * 
     * @param msgids 需要删除的非金融登记簿ID
     * @return 结果
     */
    @Override
    public int deletePayPayTransdtlNonfByIds(String[] msgids)
    {
        return payPayTransdtlNonfMapper.deletePayPayTransdtlNonfByIds(msgids);
    }

    /**
     * 删除非金融登记簿信息
     * 
     * @param msgid 非金融登记簿ID
     * @return 结果
     */
    @Override
    public int deletePayPayTransdtlNonfById(String msgid)
    {
        return payPayTransdtlNonfMapper.deletePayPayTransdtlNonfById(msgid);
    }
}
