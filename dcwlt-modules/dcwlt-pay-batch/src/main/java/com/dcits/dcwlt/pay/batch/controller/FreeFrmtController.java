package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 自由格式Controller
 *
 * @author dcwlt
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/freefrmt")
public class FreeFrmtController extends BaseController
{
    @Autowired
    private IPayPayTransdtlNonfService payPayTransdtlNonfService;

    /**
     * 自由格式查询
     */
    @PreAuthorize(hasPermi = "pay-batch:freefrmt:list")
    @GetMapping("/list")
    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        startPage();
        payPayTransdtlNonf.setPkgNo(MsgTpEnum.DCEP401.getCode());
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        return getDataTable(list);
    }

    /**
     * 导出非金融登记簿列表
     */
    @PreAuthorize(hasPermi = "pay-batch:freefrmt:export")
    @Log(title = "非金融登记簿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTransDtlNonfDO payPayTransdtlNonf) throws IOException
    {
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
        util.exportExcel(response, list, "freefrmt");
    }

    /**
     * 获取自由格式详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:freefrmt:query")
    @GetMapping(value = "/{msgid}")
    public AjaxResult getInfo(@PathVariable("msgid") String msgid)
    {
        return AjaxResult.success(payPayTransdtlNonfService.selectPayPayTransdtlNonfById(msgid));
    }




}
