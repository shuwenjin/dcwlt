package com.dcits.dcwlt.system.controller;

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
import com.dcits.dcwlt.system.domain.PayCommParty;
import com.dcits.dcwlt.system.service.IPayCommPartyService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 机构Controller
 * 
 * @author ruoyi
 * @date 2021-02-25
 */
@RestController
@RequestMapping("/party")
public class PayCommPartyController extends BaseController
{
    @Autowired
    private IPayCommPartyService payCommPartyService;

    /**
     * 查询机构列表
     */
    @PreAuthorize(hasPermi = "system:party:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommParty payCommParty)
    {
        startPage();
        List<PayCommParty> list = payCommPartyService.selectPayCommPartyList(payCommParty);
        return getDataTable(list);
    }

    /**
     * 导出机构列表
     */
    @PreAuthorize(hasPermi = "system:party:export")
    @Log(title = "机构", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommParty payCommParty) throws IOException
    {
        List<PayCommParty> list = payCommPartyService.selectPayCommPartyList(payCommParty);
        ExcelUtil<PayCommParty> util = new ExcelUtil<PayCommParty>(PayCommParty.class);
        util.exportExcel(response, list, "party");
    }

    /**
     * 获取机构详细信息
     */
    @PreAuthorize(hasPermi = "system:party:query")
    @GetMapping(value = "/{partyid}")
    public AjaxResult getInfo(@PathVariable("partyid") String partyid)
    {
        return AjaxResult.success(payCommPartyService.selectPayCommPartyById(partyid));
    }

    /**
     * 新增机构
     */
    @PreAuthorize(hasPermi = "system:party:add")
    @Log(title = "机构", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommParty payCommParty)
    {
        return toAjax(payCommPartyService.insertPayCommParty(payCommParty));
    }

    /**
     * 修改机构
     */
    @PreAuthorize(hasPermi = "system:party:edit")
    @Log(title = "机构", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommParty payCommParty)
    {
        return toAjax(payCommPartyService.updatePayCommParty(payCommParty));
    }

    /**
     * 删除机构
     */
    @PreAuthorize(hasPermi = "system:party:remove")
    @Log(title = "机构", businessType = BusinessType.DELETE)
	@DeleteMapping("/{partyids}")
    public AjaxResult remove(@PathVariable String[] partyids)
    {
        return toAjax(payCommPartyService.deletePayCommPartyByIds(partyids));
    }
}
