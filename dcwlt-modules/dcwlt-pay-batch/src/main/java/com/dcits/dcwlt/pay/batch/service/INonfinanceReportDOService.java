package com.dcits.dcwlt.pay.batch.service;

import java.util.List;
import com.dcits.dcwlt.pay.api.model.NonfinanceReportDO;

/**
 * 非金融交易统计报表Service接口
 * 
 * @author yangjld
 * @date 2021-03-11
 */
public interface INonfinanceReportDOService 
{
    /**
     * 查询非金融交易统计报表
     * 
     * @param reportDate 非金融交易统计报表ID
     * @return 非金融交易统计报表
     */
    public NonfinanceReportDO selectNonfinanceReportDOById(String reportDate);

    /**
     * 查询非金融交易统计报表列表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 非金融交易统计报表集合
     */
    public List<NonfinanceReportDO> selectNonfinanceReportDOList(NonfinanceReportDO nonfinanceReportDO);

    /**
     * 新增非金融交易统计报表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 结果
     */
    public int insertNonfinanceReportDO(NonfinanceReportDO nonfinanceReportDO);

    /**
     * 修改非金融交易统计报表
     * 
     * @param nonfinanceReportDO 非金融交易统计报表
     * @return 结果
     */
    public int updateNonfinanceReportDO(NonfinanceReportDO nonfinanceReportDO);

}
