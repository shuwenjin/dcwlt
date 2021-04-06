package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.AuthInfoDO;

import java.util.List;

/**
 * 业务权限变更信息Service接口
 * 
 * @author 
 * @date 2021-03-03
 */
public interface IPayCommPartyauthService 
{
    /**
     * 查询业务权限变更信息
     * 
     * @param partyid 业务权限变更信息ID
     * @return 业务权限变更信息
     */
    public AuthInfoDO selectPayCommPartyauthById(String partyid);

    /**
     * 查询业务权限变更信息列表
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 业务权限变更信息集合
     */
    public List<AuthInfoDO> selectPayCommPartyauthList(AuthInfoDO payCommPartyauth);

    /**
     * 新增业务权限变更信息
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 结果
     */
    public int insertPayCommPartyauth(AuthInfoDO payCommPartyauth);

    /**
     * 修改业务权限变更信息
     * 
     * @param payCommPartyauth 业务权限变更信息
     * @return 结果
     */
    public int updatePayCommPartyauth(AuthInfoDO payCommPartyauth);

    /**
     * 批量删除业务权限变更信息
     * 
     * @param partyids 需要删除的业务权限变更信息ID
     * @return 结果
     */
    public int deletePayCommPartyauthByIds(String[] partyids);

    /**
     * 删除业务权限变更信息信息
     * 
     * @param partyid 业务权限变更信息ID
     * @return 结果
     */
    public int deletePayCommPartyauthById(String partyid);
}
