package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;
import com.dcits.dcwlt.pay.batch.service.IBatchCheckClearDOService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 资金调整汇总核对Controller
 * 
 * @author 
 * @date 2021-03-09
 */
@RestController
@RequestMapping("/checkclear")
public class BatchCheckClearDOController extends BaseController
{
    @Autowired
    private IBatchCheckClearDOService batchCheckClearDOService;

    /**
     * 查询资金调整汇总核对列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkclear:list")
    @GetMapping("/list")
    public TableDataInfo list(BatchCheckClearDO batchCheckClearDO)
    {
        startPage();
        List<BatchCheckClearDO> list = batchCheckClearDOService.selectBatchCheckClearDOList(batchCheckClearDO);
        return getDataTable(list);
    }

    /**
     * 导出资金调整汇总核对列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkclear:export")
    @Log(title = "资金调整汇总核对", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BatchCheckClearDO batchCheckClearDO) throws IOException
    {
        List<BatchCheckClearDO> list = batchCheckClearDOService.selectBatchCheckClearDOList(batchCheckClearDO);
        ExcelUtil<BatchCheckClearDO> util = new ExcelUtil<BatchCheckClearDO>(BatchCheckClearDO.class);
        util.exportExcel(response, list, "checkclear");
    }

    /**
     * 获取资金调整汇总核对详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:checkclear:query")
    @GetMapping(value = "/{msgid}")
    public AjaxResult getInfo(@PathVariable("msgid") String msgid)
    {
        return AjaxResult.success(batchCheckClearDOService.selectBatchCheckClearDOById(msgid));
    }

}
