package com.dcits.dcwlt.pay.batch.service;

import java.util.List;
import com.dcits.dcwlt.pay.batch.domain.PayCommCerts;

/**
 * 证书管理Service接口
 * 
 * @author dcwlt
 * @date 2021-04-25
 */
public interface IPayCommCertsService 
{
    /**
     * 查询证书管理
     * 
     * @param id 证书管理ID
     * @return 证书管理
     */
    public PayCommCerts selectPayCommCertsById(Long id);

    /**
     * 查询证书管理列表
     * 
     * @param payCommCerts 证书管理
     * @return 证书管理集合
     */
    public List<PayCommCerts> selectPayCommCertsList(PayCommCerts payCommCerts);

    /**
     * 新增证书管理
     * 
     * @param payCommCerts 证书管理
     * @return 结果
     */
    public int insertPayCommCerts(PayCommCerts payCommCerts);

    /**
     * 修改证书管理
     * 
     * @param payCommCerts 证书管理
     * @return 结果
     */
    public int updatePayCommCerts(PayCommCerts payCommCerts);

    /**
     * 批量删除证书管理
     * 
     * @param ids 需要删除的证书管理ID
     * @return 结果
     */
    public int deletePayCommCertsByIds(Long[] ids);

    /**
     * 删除证书管理信息
     * 
     * @param id 证书管理ID
     * @return 结果
     */
    public int deletePayCommCertsById(Long id);
}
