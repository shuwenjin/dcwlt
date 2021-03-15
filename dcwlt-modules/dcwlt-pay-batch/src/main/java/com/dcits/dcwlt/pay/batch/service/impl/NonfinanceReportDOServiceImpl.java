package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.NonfinanceReportDOMapper;
import com.dcits.dcwlt.pay.api.model.NonfinanceReportDO;
import com.dcits.dcwlt.pay.batch.service.INonfinanceReportDOService;

/**
 * 非金融交易统计报表Service业务层处理
 * 
 * @author yangjld
 * @date 2021-03-11
 */
@Service
public class NonfinanceReportDOServiceImpl implements INonfinanceReportDOService 
{
    @Autowired
    private NonfinanceReportDOMapper nonfinanceReportDOMapper;

    /**
     * 查询非金融交易统计报表
     * 
     * @param reportDate 非金融交易统计报表ID
     * @return 非金融交易统计报表
     */
    @Override
    public NonfinanceReportDO selectNonfinanceReportDOById(String reportDate)
    {
        return nonfinanceReportDOMapper.selectNonfinanceReportDOById(reportDate);
    }

    /**
     * 查询非金融交易统计报表列表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 非金融交易统计报表
     */
    @Override
    public List<NonfinanceReportDO> selectNonfinanceReportDOList(NonfinanceReportDO nonfinanceReportDO)
    {
        return nonfinanceReportDOMapper.selectNonfinanceReportDOList(nonfinanceReportDO);
    }

    /**
     * 新增非金融交易统计报表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 结果
     */
    @Override
    public int insertNonfinanceReportDO(NonfinanceReportDO nonfinanceReportDO)
    {
        return nonfinanceReportDOMapper.insertNonfinanceReportDO(nonfinanceReportDO);
    }

    /**
     * 修改非金融交易统计报表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 结果
     */
    @Override
    public int updateNonfinanceReportDO(NonfinanceReportDO nonfinanceReportDO)
    {
        return nonfinanceReportDOMapper.updateNonfinanceReportDO(nonfinanceReportDO);
    }

}
