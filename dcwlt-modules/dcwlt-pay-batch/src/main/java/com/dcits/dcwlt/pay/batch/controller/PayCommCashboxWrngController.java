package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.model.PayCommCashboxWrng;
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
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxWrngService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 钱柜余额告警Controller
 * 
 * @author dcwlt
 * @date 2021-06-01
 */
@RestController
@RequestMapping("/wrng")
public class PayCommCashboxWrngController extends BaseController
{
    @Autowired
    private IPayCommCashboxWrngService payCommCashboxWrngService;

    /**
     * 查询钱柜余额告警列表
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommCashboxWrng payCommCashboxWrng)
    {
        startPage();
        List<PayCommCashboxWrng> list = payCommCashboxWrngService.selectPayCommCashboxWrngList(payCommCashboxWrng);
        return getDataTable(list);
    }

    /**
     * 导出钱柜余额告警列表
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:export")
    @Log(title = "钱柜余额告警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommCashboxWrng payCommCashboxWrng) throws IOException
    {
        List<PayCommCashboxWrng> list = payCommCashboxWrngService.selectPayCommCashboxWrngList(payCommCashboxWrng);
        ExcelUtil<PayCommCashboxWrng> util = new ExcelUtil<PayCommCashboxWrng>(PayCommCashboxWrng.class);
        util.exportExcel(response, list, "wrng");
    }

    /**
     * 获取钱柜余额告警详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:query")
    @GetMapping(value = "/{coopbankWltid}")
    public AjaxResult getInfo(@PathVariable("coopbankWltid") String coopbankWltid)
    {
        return AjaxResult.success(payCommCashboxWrngService.selectPayCommCashboxWrngById(coopbankWltid));
    }

    /**
     * 新增钱柜余额告警
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:add")
    @Log(title = "钱柜余额告警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommCashboxWrng payCommCashboxWrng)
    {
        return toAjax(payCommCashboxWrngService.insertPayCommCashboxWrng(payCommCashboxWrng));
    }

    /**
     * 修改钱柜余额告警
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:edit")
    @Log(title = "钱柜余额告警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommCashboxWrng payCommCashboxWrng)
    {
        return toAjax(payCommCashboxWrngService.updatePayCommCashboxWrng(payCommCashboxWrng));
    }

    /**
     * 删除钱柜余额告警
     */
    @PreAuthorize(hasPermi = "pay-batch:wrng:remove")
    @Log(title = "钱柜余额告警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{coopbankWltids}")
    public AjaxResult remove(@PathVariable String[] coopbankWltids)
    {
        return toAjax(payCommCashboxWrngService.deletePayCommCashboxWrngByIds(coopbankWltids));
    }
}
