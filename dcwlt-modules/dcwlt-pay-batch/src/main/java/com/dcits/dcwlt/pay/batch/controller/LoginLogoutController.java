package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlNonfService;
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
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;

/**
 * 登录登出Controller
 *
 * @author dcwlt
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/login")
public class LoginLogoutController extends BaseController
{
    @Autowired
    private IPayPayTransdtlNonfService payPayTransdtlNonfService;

    /**
     * 登录登出信息查询
     */
    @PreAuthorize(hasPermi = "pay-batch:login:list")
    @GetMapping("/list")
    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        startPage();
        payPayTransdtlNonf.setPkgNo(MsgTpEnum.DCEP933.getCode());
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        return getDataTable(list);
    }

    /**
     * 导出非金融登记簿列表
     */
    @PreAuthorize(hasPermi = "pay-batch:login:export")
    @Log(title = "非金融登记簿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTransDtlNonfDO payTransDtlNonfDO) throws IOException
    {
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payTransDtlNonfDO);
        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
        util.exportExcel(response, list, "login");
    }

    /**
     * 获取非金融登记簿详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:login:query")
    @GetMapping(value = "/{msgid}")
    public AjaxResult getInfo(@PathVariable("msgid") String msgid)
    {
        return AjaxResult.success(payPayTransdtlNonfService.selectPayPayTransdtlNonfById(msgid));
    }

}
