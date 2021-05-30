package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfoJrn;
import com.dcits.dcwlt.pay.batch.service.IPaySignSigninfoJrnService;
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
 * 签约流水Controller
 *
 * @author dcwlt
 * @date 2021-05-07
 */
@RestController
@RequestMapping("/jrn")
public class PaySignSigninfoJrnController extends BaseController {
    @Autowired
    private IPaySignSigninfoJrnService paySignSigninfoJrnService;

    /**
     * 查询签约流水列表
     */
    @PreAuthorize(hasPermi = "system:jrn:list")
    @GetMapping("/list")
    public TableDataInfo list(PaySignSigninfoJrn paySignSigninfoJrn) {
        startPage();
        List<PaySignSigninfoJrn> list = paySignSigninfoJrnService.selectPaySignSigninfoJrnList(paySignSigninfoJrn);
        return getDataTable(list);
    }

    /**
     * 导出签约流水列表
     */
    @PreAuthorize(hasPermi = "system:jrn:export")
    @Log(title = "签约流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaySignSigninfoJrn paySignSigninfoJrn) throws IOException {
        List<PaySignSigninfoJrn> list = paySignSigninfoJrnService.selectPaySignSigninfoJrnList(paySignSigninfoJrn);
        ExcelUtil<PaySignSigninfoJrn> util = new ExcelUtil<PaySignSigninfoJrn>(PaySignSigninfoJrn.class);
        util.exportExcel(response, list, "jrn");
    }

    /**
     * 获取签约流水详细信息
     */
    @PreAuthorize(hasPermi = "system:jrn:query")
    @GetMapping(value = "/{paydate}")
    public AjaxResult getInfo(@PathVariable("paydate") String paydate) {
        return AjaxResult.success(paySignSigninfoJrnService.selectPaySignSigninfoJrnById(paydate));
    }

    /**
     * 新增签约流水
     */
    @PreAuthorize(hasPermi = "system:jrn:add")
    @Log(title = "签约流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaySignSigninfoJrn paySignSigninfoJrn) {
        return toAjax(paySignSigninfoJrnService.insertPaySignSigninfoJrn(paySignSigninfoJrn));
    }

    /**
     * 修改签约流水
     */
    @PreAuthorize(hasPermi = "system:jrn:edit")
    @Log(title = "签约流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaySignSigninfoJrn paySignSigninfoJrn) {
        return toAjax(paySignSigninfoJrnService.updatePaySignSigninfoJrn(paySignSigninfoJrn));
    }

    /**
     * 删除签约流水
     */
    @PreAuthorize(hasPermi = "system:jrn:remove")
    @Log(title = "签约流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{paydates}")
    public AjaxResult remove(@PathVariable String[] paydates) {
        return toAjax(paySignSigninfoJrnService.deletePaySignSigninfoJrnByIds(paydates));
    }
}
