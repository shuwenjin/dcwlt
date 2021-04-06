package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.FinanceReportDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.FinanceReportDOMapper;
import com.dcits.dcwlt.pay.batch.service.IFinanceReportDOService;

/**
 * 金融交易统计报表Service业务层处理
 * 
 * @author 
 * @date 2021-03-11
 */
@Service
public class FinanceReportDOServiceImpl implements IFinanceReportDOService 
{
    @Autowired
    private FinanceReportDOMapper financeReportDOMapper;

    /**
     * 查询金融交易统计报表
     * 
     * @param reportDate 金融交易统计报表ID
     * @return 金融交易统计报表
     */
    @Override
    public FinanceReportDO selectFinanceReportDOById(String reportDate)
    {
        return financeReportDOMapper.selectFinanceReportDOById(reportDate);
    }

    /**
     * 查询金融交易统计报表列表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 金融交易统计报表
     */
    @Override
    public List<FinanceReportDO> selectFinanceReportDOList(FinanceReportDO financeReportDO)
    {
        return financeReportDOMapper.selectFinanceReportDOList(financeReportDO);
    }

    /**
     * 新增金融交易统计报表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 结果
     */
    @Override
    public int insertFinanceReportDO(FinanceReportDO financeReportDO)
    {
        return financeReportDOMapper.insertFinanceReportDO(financeReportDO);
    }

    /**
     * 修改金融交易统计报表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 结果
     */
    @Override
    public int updateFinanceReportDO(FinanceReportDO financeReportDO)
    {
        return financeReportDOMapper.updateFinanceReportDO(financeReportDO);
    }

}
