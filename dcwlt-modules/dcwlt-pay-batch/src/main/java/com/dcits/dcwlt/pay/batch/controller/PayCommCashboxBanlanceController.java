package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.batch.domain.PayCommCashboxBanlance;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxBanlanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 钱柜余额对账通知Controller
 * 
 * @author dcwlt
 * @date 2021-05-12
 */
@RestController
@RequestMapping("/banlance")
public class PayCommCashboxBanlanceController extends BaseController
{
    @Autowired
    private IPayCommCashboxBanlanceService payCommCashboxBanlanceService;

    /**
     * 查询钱柜余额对账通知列表
     */
    @PreAuthorize(hasPermi = "system:banlance:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommCashboxBanlance payCommCashboxBanlance)
    {
        startPage();
        List<PayCommCashboxBanlance> list = payCommCashboxBanlanceService.selectPayCommCashboxBanlanceList(payCommCashboxBanlance);
        return getDataTable(list);
    }

    /**
     * 导出钱柜余额对账通知列表
     */
    @PreAuthorize(hasPermi = "system:banlance:export")
    @Log(title = "钱柜余额对账通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommCashboxBanlance payCommCashboxBanlance) throws IOException
    {
        List<PayCommCashboxBanlance> list = payCommCashboxBanlanceService.selectPayCommCashboxBanlanceList(payCommCashboxBanlance);
        ExcelUtil<PayCommCashboxBanlance> util = new ExcelUtil<PayCommCashboxBanlance>(PayCommCashboxBanlance.class);
        util.exportExcel(response, list, "banlance");
    }

    /**
     * 获取钱柜余额对账通知详细信息
     */
    @PreAuthorize(hasPermi = "system:banlance:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payCommCashboxBanlanceService.selectPayCommCashboxBanlanceById(id));
    }

    /**
     * 新增钱柜余额对账通知
     */
    @PreAuthorize(hasPermi = "system:banlance:add")
    @Log(title = "钱柜余额对账通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommCashboxBanlance payCommCashboxBanlance)
    {
        return toAjax(payCommCashboxBanlanceService.insertPayCommCashboxBanlance(payCommCashboxBanlance));
    }

    /**
     * 修改钱柜余额对账通知
     */
    @PreAuthorize(hasPermi = "system:banlance:edit")
    @Log(title = "钱柜余额对账通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommCashboxBanlance payCommCashboxBanlance)
    {
        return toAjax(payCommCashboxBanlanceService.updatePayCommCashboxBanlance(payCommCashboxBanlance));
    }

    /**
     * 删除钱柜余额对账通知
     */
    @PreAuthorize(hasPermi = "system:banlance:remove")
    @Log(title = "钱柜余额对账通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(payCommCashboxBanlanceService.deletePayCommCashboxBanlanceByIds(ids));
    }
}
