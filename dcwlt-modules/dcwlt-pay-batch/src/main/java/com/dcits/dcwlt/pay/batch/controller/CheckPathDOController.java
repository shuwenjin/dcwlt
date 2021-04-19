package com.dcits.dcwlt.pay.batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 对账汇总Controller
 * 
 * @author 
 * @date 2021-03-09
 */
@RestController
@RequestMapping("/checkpath")
public class CheckPathDOController extends BaseController
{

    @Autowired
    private ICheckPathDOService checkPathDOService;



    /**
     * 查询对账汇总列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:list")
    @GetMapping("/list")
    public TableDataInfo list(CheckPathDO checkPathDO)
    {
        startPage();
        List<CheckPathDO> list = checkPathDOService.selectCheckPathDOList(checkPathDO);
        return getDataTable(list);
    }

    /**
     * 导出对账汇总列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:export")
    @Log(title = "对账汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckPathDO checkPathDO) throws IOException
    {
        List<CheckPathDO> list = checkPathDOService.selectCheckPathDOList(checkPathDO);
        ExcelUtil<CheckPathDO> util = new ExcelUtil<CheckPathDO>(CheckPathDO.class);
        util.exportExcel(response, list, "checkpath");
    }

    /**
     * 获取对账汇总详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:query")
    @GetMapping(value = "/{paydate}")
    public AjaxResult getInfo(@PathVariable("paydate") String paydate)
    {
        return AjaxResult.success(checkPathDOService.selectCheckPathDOById(paydate));
    }

    /**
     * 发送801 手动差错
     * @param checkPathDO
     * @return
     */
    @PostMapping(value = "/executeSend801")
    public AjaxResult executeSend801(@RequestBody  JSONObject dsptChnlReqDTO){
          boolean checkResult=checkPathDOService.execute801(dsptChnlReqDTO);
        if (checkResult) {
            return AjaxResult.success("手动差错成功");
        } else {
            return AjaxResult.error("手动差错失败");
        }
    }


    /**
     * 重新对账
     * @param checkPathDO
     * @return
     */
    @PostMapping(value = "/reconciliation")
    public AjaxResult reconciliation (CheckPathDO checkPathDO){
        boolean checkResult=checkPathDOService.reconciliation(checkPathDO);
        if (checkResult) {
            return AjaxResult.success("重新对账成功");
        } else {
            return AjaxResult.error("重新对账失败");
        }
    }











}
