package com.dcits.dcwlt.pay.batch.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.dcits.dcwlt.pay.api.domain.dcep.config.PayCommonParam;
import com.dcits.dcwlt.pay.batch.service.IPayCommonParamService;
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
 * 参数配置Controller
 *
 * @author dcwlt
 * @date 2021-04-14
 */
@RestController
@RequestMapping("/param")
public class PayCommonParamController extends BaseController {
    @Autowired
    private IPayCommonParamService payCommonParamService;

    /**
     * 查询参数配置列表
     */
    @PreAuthorize(hasPermi = "system:param:list")
    @GetMapping("/list")
    public TableDataInfo list(PayCommonParam payCommonParam) {
        startPage();
        List<PayCommonParam> list = payCommonParamService.selectPayCommonParamList(payCommonParam);
        return getDataTable(list);
    }

    /**
     * 导出参数配置列表
     */
    @PreAuthorize(hasPermi = "system:param:export")
    @Log(title = "参数配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayCommonParam payCommonParam) throws IOException {
        List<PayCommonParam> list = payCommonParamService.selectPayCommonParamList(payCommonParam);
        ExcelUtil<PayCommonParam> util = new ExcelUtil<PayCommonParam>(PayCommonParam.class);
        util.exportExcel(response, list, "param");
    }

    /**
     * 获取参数配置详细信息
     */
    @PreAuthorize(hasPermi = "system:param:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(payCommonParamService.selectPayCommonParamById(id));
    }

    /**
     * 新增参数配置
     */
    @PreAuthorize(hasPermi = "system:param:add")
    @Log(title = "参数配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayCommonParam payCommonParam) {
        try {
            return toAjax(payCommonParamService.insertPayCommonParam(payCommonParam));
        } catch (Exception e) {
            return AjaxResult.error("参数id不能重复");
        }

    }

    /**
     * 修改参数配置
     */
    @PreAuthorize(hasPermi = "system:param:edit")
    @Log(title = "参数配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayCommonParam payCommonParam) {
        return toAjax(payCommonParamService.updatePayCommonParam(payCommonParam));
    }

    /**
     * 删除参数配置
     */
    @PreAuthorize(hasPermi = "system:param:remove")
    @Log(title = "参数配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(payCommonParamService.deletePayCommonParamByIds(ids));
    }
}
