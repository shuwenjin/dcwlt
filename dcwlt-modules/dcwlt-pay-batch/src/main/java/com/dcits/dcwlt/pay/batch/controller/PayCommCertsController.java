package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.dcits.dcwlt.pay.batch.domain.PayCommCerts;
import com.dcits.dcwlt.pay.batch.service.IPayCommCertsService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 证书管理Controller
 * 
 * @author dcwlt
 * @date 2021-04-25
 */
@RestController
@RequestMapping("/certs")
public class PayCommCertsController extends BaseController
{
    @Autowired
    private IPayCommCertsService payCommCertsService;

    /**
     * 查询证书管理列表
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommCerts payCommCerts)
    {
        startPage();
        List<PayCommCerts> list = payCommCertsService.selectPayCommCertsList(payCommCerts);
        return getDataTable(list);
    }

    /**
     * 导出证书管理列表
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:export")
    @Log(title = "证书管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommCerts payCommCerts) throws IOException
    {
        List<PayCommCerts> list = payCommCertsService.selectPayCommCertsList(payCommCerts);
        ExcelUtil<PayCommCerts> util = new ExcelUtil<PayCommCerts>(PayCommCerts.class);
        util.exportExcel(response, list, "certs");
    }

    /**
     * 获取证书管理详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payCommCertsService.selectPayCommCertsById(id));
    }

    /**
     * 新增证书管理
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:add")
    @Log(title = "证书管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommCerts payCommCerts)
    {
        return toAjax(payCommCertsService.insertPayCommCerts(payCommCerts));
    }

    /**
     * 修改证书管理
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:edit")
    @Log(title = "证书管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommCerts payCommCerts)
    {
        return toAjax(payCommCertsService.updatePayCommCerts(payCommCerts));
    }

    /**
     * 删除证书管理
     */
    @PreAuthorize(hasPermi = "pay-batch:certs:remove")
    @Log(title = "证书管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(payCommCertsService.deletePayCommCertsByIds(ids));
    }
}
