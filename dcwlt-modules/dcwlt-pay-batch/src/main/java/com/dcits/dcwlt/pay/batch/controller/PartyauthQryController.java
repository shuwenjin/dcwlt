package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.batch.service.IPayCommPartyauthService;
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
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.pay.api.model.*;

/**
 * 业务权限变更信息Controller
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/partyauth")
public class PartyauthQryController extends BaseController
{


    @Autowired
    private IPayCommPartyauthService payCommPartyauthService;

    /**
     * 查询业务权限变更信息列表
     */
    @PreAuthorize(hasPermi = "pay-batch:partyauth:list")
    @GetMapping("/list")
    public TableDataInfo list(AuthInfoDO payCommPartyauth)
    {
        startPage();
        List<AuthInfoDO> list = payCommPartyauthService.selectPayCommPartyauthList(payCommPartyauth);
        return getDataTable(list);
    }

    /**
     * 导出业务权限变更信息列表
     */
    @PreAuthorize(hasPermi = "pay-batch:partyauth:export")
    @Log(title = "业务权限变更信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuthInfoDO payCommPartyauth) throws IOException
    {
        List<AuthInfoDO> list = payCommPartyauthService.selectPayCommPartyauthList(payCommPartyauth);
        ExcelUtil<AuthInfoDO> util = new ExcelUtil<AuthInfoDO>(AuthInfoDO.class);
        util.exportExcel(response, list, "partyauth");
    }

    /**
     * 获取业务权限变更信息详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:partyauth:query")
    @GetMapping(value = "/{partyid}")
    public AjaxResult getInfo(@PathVariable("partyid") String partyid)
    {
        return AjaxResult.success(payCommPartyauthService.selectPayCommPartyauthById(partyid));
    }

}
