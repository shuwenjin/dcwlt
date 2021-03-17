package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.model.FinanceReportDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.batch.service.IFinanceReportDOService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 金融交易统计报表Controller
 * 
 * @author yangjld
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/financereport")
public class FinanceReportDOController extends BaseController
{
    @Autowired
    private IFinanceReportDOService financeReportDOService;

    /**
     * 查询金融交易统计报表列表
     */
    @PreAuthorize(hasPermi = "pay-batch:financereport:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceReportDO financeReportDO)
    {
        startPage();
        List<FinanceReportDO> list = financeReportDOService.selectFinanceReportDOList(financeReportDO);
        return getDataTable(list);
    }

    /**
     * 导出金融交易统计报表列表
     */
    @PreAuthorize(hasPermi = "pay-batch:financereport:export")
    @Log(title = "金融交易统计报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceReportDO financeReportDO) throws IOException
    {
        List<FinanceReportDO> list = financeReportDOService.selectFinanceReportDOList(financeReportDO);
        ExcelUtil<FinanceReportDO> util = new ExcelUtil<FinanceReportDO>(FinanceReportDO.class);
        util.exportExcel(response, list, "financereport");
    }

    /**
     * 获取金融交易统计报表详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:financereport:query")
    @GetMapping(value = "/{reportDate}")
    public AjaxResult getInfo(@PathVariable("reportDate") String reportDate)
    {
        return AjaxResult.success(financeReportDOService.selectFinanceReportDOById(reportDate));
    }

}
