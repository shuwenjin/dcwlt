package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfo;

import java.util.List;

/**
 * 协议Mapper接口
 *
 * @author dcwlt
 * @date 2021-05-08
 */
public interface PaySignSigninfoMapper {
    /**
     * 查询协议
     *
     * @param signno 协议ID
     * @return 协议
     */
    public PaySignSigninfo selectPaySignSigninfoById(String signno);

    /**
     * 查询协议列表
     *
     * @param paySignSigninfo 协议
     * @return 协议集合
     */
    public List<PaySignSigninfo> selectPaySignSigninfoList(PaySignSigninfo paySignSigninfo);

    /**
     * 新增协议
     *
     * @param paySignSigninfo 协议
     * @return 结果
     */
    public int insertPaySignSigninfo(PaySignSigninfo paySignSigninfo);

    /**
     * 修改协议
     *
     * @param paySignSigninfo 协议
     * @return 结果
     */
    public int updatePaySignSigninfo(PaySignSigninfo paySignSigninfo);

    /**
     * 删除协议
     *
     * @param signno 协议ID
     * @return 结果
     */
    public int deletePaySignSigninfoById(String signno);

    /**
     * 批量删除协议
     *
     * @param signnos 需要删除的数据ID
     * @return 结果
     */
    public int deletePaySignSigninfoByIds(String[] signnos);
}
