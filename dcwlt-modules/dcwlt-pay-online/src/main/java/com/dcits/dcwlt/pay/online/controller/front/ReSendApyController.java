package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.api.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 交易重发申请Controller
 *
 * @author dcwlt
 * @date 2021-04-21
 */
@RestController
@RequestMapping("/ReSendApy")
public class ReSendApyController extends BaseController {
//    @Autowired
//    private IPayPayTransdtlNonfService payPayTransdtlNonfService;
//
//    /**
//     * 查询交易重发申请列表
//     */
//    @PreAuthorize(hasPermi = "pay-batch:ReSendApy:list")
//    @GetMapping("/list")
//    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf) {
//        startPage();
//        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出交易重发申请列表
//     */
//    @PreAuthorize(hasPermi = "pay-batch:ReSendApy:export")
//    @Log(title = "交易重发申请", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, PayTransDtlNonfDO payPayTransdtlNonf) throws IOException {
//        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
//        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
//        util.exportExcel(response, list, "ReSendApy");
//    }


    /**
     * 新增交易重发申请
     */
    @PreAuthorize(hasPermi = "pay-batch:ReSendApy:resend")
    @Log(title = "交易重发申请", businessType = BusinessType.INSERT)
    @PostMapping("/resend")
    public AjaxResult add(@RequestBody PayTransDtlNonfDO payPayTransdtlNonf) {
//        return toAjax(payPayTransdtlNonfService.insertPayPayTransdtlNonf(payPayTransdtlNonf));
        return toAjax(1);
    }
}
