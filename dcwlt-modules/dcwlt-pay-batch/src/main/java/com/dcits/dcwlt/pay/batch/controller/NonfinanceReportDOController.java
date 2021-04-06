package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.dcits.dcwlt.pay.api.model.NonfinanceReportDO;
import com.dcits.dcwlt.pay.batch.service.INonfinanceReportDOService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 非金融交易统计报表Controller
 * 
 * @author 
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/nonfinancereport")
public class NonfinanceReportDOController extends BaseController
{
    @Autowired
    private INonfinanceReportDOService nonfinanceReportDOService;

    /**
     * 查询非金融交易统计报表列表
     */
    @PreAuthorize(hasPermi = "pay-batch:nonfinancereport:list")
    @GetMapping("/list")
    public TableDataInfo list(NonfinanceReportDO nonfinanceReportDO)
    {
        startPage();
        List<NonfinanceReportDO> list = nonfinanceReportDOService.selectNonfinanceReportDOList(nonfinanceReportDO);
        return getDataTable(list);
    }

    /**
     * 导出非金融交易统计报表列表
     */
    @PreAuthorize(hasPermi = "pay-batch:nonfinancereport:export")
    @Log(title = "非金融交易统计报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NonfinanceReportDO nonfinanceReportDO) throws IOException
    {
        List<NonfinanceReportDO> list = nonfinanceReportDOService.selectNonfinanceReportDOList(nonfinanceReportDO);
        ExcelUtil<NonfinanceReportDO> util = new ExcelUtil<NonfinanceReportDO>(NonfinanceReportDO.class);
        util.exportExcel(response, list, "nonfinancereport");
    }

    /**
     * 获取非金融交易统计报表详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:nonfinancereport:query")
    @GetMapping(value = "/{reportDate}")
    public AjaxResult getInfo(@PathVariable("reportDate") String reportDate)
    {
        return AjaxResult.success(nonfinanceReportDOService.selectNonfinanceReportDOById(reportDate));
    }

    /**
     * 新增非金融交易统计报表
     */
    @PreAuthorize(hasPermi = "pay-batch:nonfinancereport:add")
    @Log(title = "非金融交易统计报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NonfinanceReportDO nonfinanceReportDO)
    {
        return toAjax(nonfinanceReportDOService.insertNonfinanceReportDO(nonfinanceReportDO));
    }

    /**
     * 修改非金融交易统计报表
     */
    @PreAuthorize(hasPermi = "pay-batch:nonfinancereport:edit")
    @Log(title = "非金融交易统计报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NonfinanceReportDO nonfinanceReportDO)
    {
        return toAjax(nonfinanceReportDOService.updateNonfinanceReportDO(nonfinanceReportDO));
    }

}
