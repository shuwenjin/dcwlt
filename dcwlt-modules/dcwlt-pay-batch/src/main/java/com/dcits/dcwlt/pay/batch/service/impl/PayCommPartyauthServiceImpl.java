package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.batch.mapper.PayCommPartyauthMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommPartyauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 业务权限变更信息Service业务层处理
 * 
 * @author 
 * @date 2021-03-03
 */
@Service
public class PayCommPartyauthServiceImpl implements IPayCommPartyauthService
{
    @Autowired
    private PayCommPartyauthMapper payCommPartyauthMapper;

    /**
     * 查询业务权限变更信息
     * 
     * @param partyid 业务权限变更信息ID
     * @return 业务权限变更信息
     */
    @Override
    public AuthInfoDO selectPayCommPartyauthById(String partyid)
    {
        return payCommPartyauthMapper.selectPayCommPartyauthById(partyid);
    }

    /**
     * 查询业务权限变更信息列表
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 业务权限变更信息
     */
    @Override
    public List<AuthInfoDO> selectPayCommPartyauthList(AuthInfoDO payCommPartyauth)
    {
        return payCommPartyauthMapper.selectPayCommPartyauthList(payCommPartyauth);

//        return payCommPartyauthMapper.selectPayCommPartyauthList(payCommPartyauth);
    }

    /**
     * 新增业务权限变更信息
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 结果
     */
    @Override
    public int insertPayCommPartyauth(AuthInfoDO payCommPartyauth)
    {
        return payCommPartyauthMapper.insertPayCommPartyauth(payCommPartyauth);
    }

    /**
     * 修改业务权限变更信息
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 结果
     */
    @Override
    public int updatePayCommPartyauth(AuthInfoDO payCommPartyauth)
    {
        return payCommPartyauthMapper.updatePayCommPartyauth(payCommPartyauth);
    }

    /**
     * 批量删除业务权限变更信息
     * 
     * @param partyids 需要删除的业务权限变更信息ID
     * @return 结果
     */
    @Override
    public int deletePayCommPartyauthByIds(String[] partyids)
    {
        return payCommPartyauthMapper.deletePayCommPartyauthByIds(partyids);
    }

    /**
     * 删除业务权限变更信息信息
     * 
     * @param partyid 业务权限变更信息ID
     * @return 结果
     */
    @Override
    public int deletePayCommPartyauthById(String partyid)
    {
        return payCommPartyauthMapper.deletePayCommPartyauthById(partyid);
    }

    @Override
    public int replaceAuthInfo(AuthInfoDO authInfoDO) {
        return payCommPartyauthMapper.replaceAuthInfo(authInfoDO);
    }
}
