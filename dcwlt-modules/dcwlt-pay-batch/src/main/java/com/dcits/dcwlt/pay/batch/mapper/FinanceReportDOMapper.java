package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.FinanceReportDO;
import java.util.List;

/**
 * 金融交易统计报表Mapper接口
 * 
 * @author 
 * @date 2021-03-11
 */
public interface FinanceReportDOMapper 
{
    /**
     * 查询金融交易统计报表
     * 
     * @param reportDate 金融交易统计报表ID
     * @return 金融交易统计报表
     */
    public FinanceReportDO selectFinanceReportDOById(String reportDate);

    /**
     * 查询金融交易统计报表列表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 金融交易统计报表集合
     */
    public List<FinanceReportDO> selectFinanceReportDOList(FinanceReportDO financeReportDO);

    /**
     * 新增金融交易统计报表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 结果
     */
    public int insertFinanceReportDO(FinanceReportDO financeReportDO);

    /**
     * 修改金融交易统计报表
     * 
     * @param financeReportDO 金融交易统计报表
     * @return 结果
     */
    public int updateFinanceReportDO(FinanceReportDO financeReportDO);

}
