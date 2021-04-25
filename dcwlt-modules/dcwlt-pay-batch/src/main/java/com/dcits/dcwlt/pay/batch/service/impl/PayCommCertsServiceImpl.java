package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.PayCommCertsMapper;
import com.dcits.dcwlt.pay.batch.domain.PayCommCerts;
import com.dcits.dcwlt.pay.batch.service.IPayCommCertsService;

/**
 * 证书管理Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-04-25
 */
@Service
public class PayCommCertsServiceImpl implements IPayCommCertsService 
{
    @Autowired
    private PayCommCertsMapper payCommCertsMapper;

    /**
     * 查询证书管理
     * 
     * @param id 证书管理ID
     * @return 证书管理
     */
    @Override
    public PayCommCerts selectPayCommCertsById(String id)
    {
        return payCommCertsMapper.selectPayCommCertsById(id);
    }

    /**
     * 查询证书管理列表
     * 
     * @param payCommCerts 证书管理
     * @return 证书管理
     */
    @Override
    public List<PayCommCerts> selectPayCommCertsList(PayCommCerts payCommCerts)
    {
        return payCommCertsMapper.selectPayCommCertsList(payCommCerts);
    }

    /**
     * 新增证书管理
     * 
     * @param payCommCerts 证书管理
     * @return 结果
     */
    @Override
    public int insertPayCommCerts(PayCommCerts payCommCerts)
    {
        payCommCerts.setCreateTime(DateUtils.getNowDate());
        return payCommCertsMapper.insertPayCommCerts(payCommCerts);
    }

    /**
     * 修改证书管理
     * 
     * @param payCommCerts 证书管理
     * @return 结果
     */
    @Override
    public int updatePayCommCerts(PayCommCerts payCommCerts)
    {
        payCommCerts.setUpdateTime(DateUtils.getNowDate());
        return payCommCertsMapper.updatePayCommCerts(payCommCerts);
    }

    /**
     * 批量删除证书管理
     * 
     * @param ids 需要删除的证书管理ID
     * @return 结果
     */
    @Override
    public int deletePayCommCertsByIds(String[] ids)
    {
        return payCommCertsMapper.deletePayCommCertsByIds(ids);
    }

    /**
     * 删除证书管理信息
     * 
     * @param id 证书管理ID
     * @return 结果
     */
    @Override
    public int deletePayCommCertsById(String id)
    {
        return payCommCertsMapper.deletePayCommCertsById(id);
    }
}
