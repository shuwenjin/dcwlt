package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.CheckPathDOMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDOService;

/**
 * 对账汇总Service业务层处理
 * 
 * @author lihch
 * @date 2021-03-09
 */
@Service
public class CheckPathDOServiceImpl implements ICheckPathDOService 
{
    @Autowired
    private CheckPathDOMapper checkPathDOMapper;

    /**
     * 查询对账汇总
     * 
     * @param paydate 对账汇总ID
     * @return 对账汇总
     */
    @Override
    public CheckPathDO selectCheckPathDOById(String paydate)
    {
        return checkPathDOMapper.selectCheckPathDOById(paydate);
    }

    /**
     * 查询对账汇总列表
     * 
     * @param checkPathDO 对账汇总
     * @return 对账汇总
     */
    @Override
    public List<CheckPathDO> selectCheckPathDOList(CheckPathDO checkPathDO)
    {
        return checkPathDOMapper.selectCheckPathDOList(checkPathDO);
    }


}
