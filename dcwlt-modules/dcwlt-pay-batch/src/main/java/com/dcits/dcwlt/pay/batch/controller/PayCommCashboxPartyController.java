package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxParty;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxPartyService;
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
 * 运营机构钱柜参数Controller
 * 
 * @author dcwlt
 * @date 2021-05-12
 */
@RestController
@RequestMapping("/cashparty")
public class PayCommCashboxPartyController extends BaseController
{
    @Autowired
    private IPayCommCashboxPartyService payCommCashboxPartyService;

    /**
     * 查询运营机构钱柜参数列表
     */
    @PreAuthorize(hasPermi = "system:cashparty:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommCashboxParty payCommCashboxParty)
    {
        startPage();
        List<PayCommCashboxParty> list = payCommCashboxPartyService.selectPayCommCashboxPartyList(payCommCashboxParty);
        return getDataTable(list);
    }

    /**
     * 导出运营机构钱柜参数列表
     */
    @PreAuthorize(hasPermi = "system:cashparty:export")
    @Log(title = "运营机构钱柜参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommCashboxParty payCommCashboxParty) throws IOException
    {
        List<PayCommCashboxParty> list = payCommCashboxPartyService.selectPayCommCashboxPartyList(payCommCashboxParty);
        ExcelUtil<PayCommCashboxParty> util = new ExcelUtil<PayCommCashboxParty>(PayCommCashboxParty.class);
        util.exportExcel(response, list, "party");
    }

    /**
     * 获取运营机构钱柜参数详细信息
     */
    @PreAuthorize(hasPermi = "system:cashparty:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payCommCashboxPartyService.selectPayCommCashboxPartyById(id));
    }

    /**
     * 新增运营机构钱柜参数
     */
    @PreAuthorize(hasPermi = "system:cashparty:add")
    @Log(title = "运营机构钱柜参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommCashboxParty payCommCashboxParty)
    {
        return toAjax(payCommCashboxPartyService.insertPayCommCashboxParty(payCommCashboxParty));
    }

    /**
     * 修改运营机构钱柜参数
     */
    @PreAuthorize(hasPermi = "system:cashparty:edit")
    @Log(title = "运营机构钱柜参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommCashboxParty payCommCashboxParty)
    {
        return toAjax(payCommCashboxPartyService.updatePayCommCashboxParty(payCommCashboxParty));
    }
    /**
     * 修改运营机构钱柜参数
     */
    @PreAuthorize(hasPermi = "system:cashparty:edit")
    @Log(title = "运营机构钱柜预警余额", businessType = BusinessType.UPDATE)
    @PutMapping("/warn")
    public AjaxResult editwarn(@RequestBody PayCommCashboxParty payCommCashboxParty)
    {
        return toAjax(payCommCashboxPartyService.updatePayCommCashboxPartyWarn(payCommCashboxParty));
    }
    /**
     * 删除运营机构钱柜参数
     */
    @PreAuthorize(hasPermi = "system:cashparty:remove")
    @Log(title = "运营机构钱柜参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(payCommCashboxPartyService.deletePayCommCashboxPartyByIds(ids));
    }
}
