package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlService;
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

/**
 * 金融交易登记Controller
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/transdtl")
public class TransdtlQryController extends BaseController
{
    @Autowired
    private IPayPayTransdtlService payPayTransdtlService;

    /**
     * 查询金融交易登记列表
     */

    @GetMapping("/list")
    public TableDataInfo list(PayTransDtlInfoDO payPayTransdtl)
    {
        startPage();
        List<PayTransDtlInfoDO> list = payPayTransdtlService.selectPayPayTransdtlList(payPayTransdtl);
        return getDataTable(list);
    }

    /**
     * 导出金融交易登记列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTransDtlInfoDO payPayTransdtl) throws IOException
    {
        List<PayTransDtlInfoDO> list = payPayTransdtlService.selectPayPayTransdtlList(payPayTransdtl);
        ExcelUtil<PayTransDtlInfoDO> util = new ExcelUtil<PayTransDtlInfoDO>(PayTransDtlInfoDO.class);
        util.exportExcel(response, list, "transdtl");
    }


}
