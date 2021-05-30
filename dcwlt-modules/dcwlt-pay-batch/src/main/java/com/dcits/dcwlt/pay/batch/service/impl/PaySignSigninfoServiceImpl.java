package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfo;
import com.dcits.dcwlt.pay.batch.mapper.PaySignSigninfoMapper;
import com.dcits.dcwlt.pay.batch.service.IPaySignSigninfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 协议Service业务层处理
 *
 * @author dcwlt
 * @date 2021-05-08
 */
@Service
public class PaySignSigninfoServiceImpl implements IPaySignSigninfoService {
    @Autowired
    private PaySignSigninfoMapper paySignSigninfoMapper;

    /**
     * 查询协议
     *
     * @param signno 协议ID
     * @return 协议
     */
    @Override
    public PaySignSigninfo selectPaySignSigninfoById(String signno) {
        return paySignSigninfoMapper.selectPaySignSigninfoById(signno);
    }

    /**
     * 查询协议列表
     *
     * @param paySignSigninfo 协议
     * @return 协议
     */
    @Override
    public List<PaySignSigninfo> selectPaySignSigninfoList(PaySignSigninfo paySignSigninfo) {
        return paySignSigninfoMapper.selectPaySignSigninfoList(paySignSigninfo);
    }

    /**
     * 新增协议
     *
     * @param paySignSigninfo 协议
     * @return 结果
     */
    @Override
    public int insertPaySignSigninfo(PaySignSigninfo paySignSigninfo) {
        return paySignSigninfoMapper.insertPaySignSigninfo(paySignSigninfo);
    }

    /**
     * 修改协议
     *
     * @param paySignSigninfo 协议
     * @return 结果
     */
    @Override
    public int updatePaySignSigninfo(PaySignSigninfo paySignSigninfo) {
        return paySignSigninfoMapper.updatePaySignSigninfo(paySignSigninfo);
    }

    /**
     * 批量删除协议
     *
     * @param signnos 需要删除的协议ID
     * @return 结果
     */
    @Override
    public int deletePaySignSigninfoByIds(String[] signnos) {
        return paySignSigninfoMapper.deletePaySignSigninfoByIds(signnos);
    }

    /**
     * 删除协议信息
     *
     * @param signno 协议ID
     * @return 结果
     */
    @Override
    public int deletePaySignSigninfoById(String signno) {
        return paySignSigninfoMapper.deletePaySignSigninfoById(signno);
    }
}
