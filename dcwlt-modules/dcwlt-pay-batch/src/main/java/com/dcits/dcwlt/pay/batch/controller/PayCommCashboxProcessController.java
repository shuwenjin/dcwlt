package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxProcess;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 钱柜出库入库异常处理Controller
 *
 * @author dcwlt
 * @date 2021-05-11
 */
@RestController
@RequestMapping("/process")
public class PayCommCashboxProcessController extends BaseController {
    @Autowired
    private IPayCommCashboxProcessService payCommCashboxProcessService;

    /**
     * 查询钱柜出库入库异常处理列表
     */
    @PreAuthorize(hasPermi = "system:process:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommCashboxProcess payCommCashboxProcess) {
        startPage();
        List<PayCommCashboxProcess> list = payCommCashboxProcessService.selectPayCommCashboxProcessList(payCommCashboxProcess);
        return getDataTable(list);
    }

    /**
     * 导出钱柜出库入库异常处理列表
     */
    @PreAuthorize(hasPermi = "system:process:export")
    @Log(title = "钱柜出库入库异常处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommCashboxProcess payCommCashboxProcess) throws IOException {
        List<PayCommCashboxProcess> list = payCommCashboxProcessService.selectPayCommCashboxProcessList(payCommCashboxProcess);
        ExcelUtil<PayCommCashboxProcess> util = new ExcelUtil<PayCommCashboxProcess>(PayCommCashboxProcess.class);
        util.exportExcel(response, list, "process");
    }

    /**
     * 出库入库接口调用
     *
     * @param map map.put("OprTp","");//入库类型
     *            map.put("AmtCcy", "CNY");//币种
     *            map.put("AmtValue","");//入库金额
     *            map.put("CshBoxInstnId","");//钱柜所属运营机构
     * @return
     */
    //@PreAuthorize(hasPermi = "system:process:sendCashbox")
    @PostMapping(value = "/sendCashbox")
    public AjaxResult sendCashbox(@RequestBody Map<String, String> map) {
        return payCommCashboxProcessService.sendCashbox(map);
    }

    /**
     * @param type 0、未申请， 1、 出入库重发，2、核心状态同步 3、手工冲账   默认为0
     * @param id
     * @return
     */
    @PreAuthorize(hasPermi = "system:process:bankRev")
    @GetMapping(value = "/bankRev")
    public AjaxResult bankRev(@RequestParam("type") Integer type, @RequestParam("id") String id) {
        PayCommCashboxProcess pay = new PayCommCashboxProcess();
        //pay.setApprovalStuts(type);
        pay.setId(Long.parseLong(id));
        return toAjax(payCommCashboxProcessService.updatePayCommCashboxProcess(pay));
    }

    /**
     * @param id 表id
     * @return
     */
    @PreAuthorize(hasPermi = "system:process:changeApprovalStuts")
    @GetMapping(value = "/changeApprovalStuts")
    public AjaxResult changeApprovalStuts(@RequestParam("appStuts") int appStuts, @RequestParam("id") Long id) {
        return payCommCashboxProcessService.changeApprovalStuts(appStuts, id);
    }

    /**
     * 获取钱柜出库入库异常处理详细信息
     */
    @PreAuthorize(hasPermi = "system:process:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(payCommCashboxProcessService.selectPayCommCashboxProcessById(id));
    }

    /**
     * 新增钱柜出库入库异常处理
     */
    @PreAuthorize(hasPermi = "system:process:add")
    @Log(title = "钱柜出库入库异常处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommCashboxProcess payCommCashboxProcess) {
        return toAjax(payCommCashboxProcessService.insertPayCommCashboxProcess(payCommCashboxProcess));
    }

    /**
     * 修改钱柜出库入库异常处理
     */
    @PreAuthorize(hasPermi = "system:process:edit")
    @Log(title = "钱柜出库入库异常处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommCashboxProcess payCommCashboxProcess) {
        return toAjax(payCommCashboxProcessService.updatePayCommCashboxProcess(payCommCashboxProcess));
    }

    /**
     * 删除钱柜出库入库异常处理
     */
    @PreAuthorize(hasPermi = "system:process:remove")
    @Log(title = "钱柜出库入库异常处理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(payCommCashboxProcessService.deletePayCommCashboxProcessByIds(ids));
    }
}
