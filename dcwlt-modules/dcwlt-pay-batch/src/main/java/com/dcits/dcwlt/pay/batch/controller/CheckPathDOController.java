package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDOService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 对账汇总Controller
 * 
 * @author 
 * @date 2021-03-09
 */
@RestController
@RequestMapping("/checkpath")
public class CheckPathDOController extends BaseController
{
    @Autowired
    private ICheckPathDOService checkPathDOService;

    /**
     * 查询对账汇总列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:list")
    @GetMapping("/list")
    public TableDataInfo list(CheckPathDO checkPathDO)
    {
        startPage();
        List<CheckPathDO> list = checkPathDOService.selectCheckPathDOList(checkPathDO);
        return getDataTable(list);
    }

    /**
     * 导出对账汇总列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:export")
    @Log(title = "对账汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckPathDO checkPathDO) throws IOException
    {
        List<CheckPathDO> list = checkPathDOService.selectCheckPathDOList(checkPathDO);
        ExcelUtil<CheckPathDO> util = new ExcelUtil<CheckPathDO>(CheckPathDO.class);
        util.exportExcel(response, list, "checkpath");
    }

    /**
     * 获取对账汇总详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:query")
    @GetMapping(value = "/{paydate}")
    public AjaxResult getInfo(@PathVariable("paydate") String paydate)
    {
        return AjaxResult.success(checkPathDOService.selectCheckPathDOById(paydate));
    }

}
