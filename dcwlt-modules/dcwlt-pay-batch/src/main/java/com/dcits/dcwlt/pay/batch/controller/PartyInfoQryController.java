package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
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
import com.dcits.dcwlt.pay.batch.service.IPartyInfoDOService;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 机构Controller
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/party")
public class PartyInfoQryController extends BaseController
{
    @Autowired
    private IPartyInfoDOService partyInfoDOService;

    /**
     * 查询机构列表
     */
    @PreAuthorize(hasPermi = "pay-batch:party:list")
    @GetMapping("/list")
    public TableDataInfo list(PartyInfoDO partyInfoDO)
    {
        startPage();
        List<PartyInfoDO> list = partyInfoDOService.selectPartyInfoDOList(partyInfoDO);
        return getDataTable(list);
    }

    /**
     * 导出机构列表
     */
    @PreAuthorize(hasPermi = "pay-batch:party:export")
    @Log(title = "机构", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PartyInfoDO partyInfoDO) throws IOException
    {
        List<PartyInfoDO> list = partyInfoDOService.selectPartyInfoDOList(partyInfoDO);
        ExcelUtil<PartyInfoDO> util = new ExcelUtil<PartyInfoDO>(PartyInfoDO.class);
        util.exportExcel(response, list, "party");
    }

    /**
     * 获取机构详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:party:query")
    @GetMapping(value = "/{partyid}")
    public AjaxResult getInfo(@PathVariable("partyid") String partyid)
    {
        return AjaxResult.success(partyInfoDOService.selectPartyInfoDOById(partyid));
    }


}
