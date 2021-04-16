package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.CheckPathDetialDO;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 对账明细Controller
 * 
 * @author 
 * @date 2021-03-09
 */
@RestController
@RequestMapping("/checkpathdtl")
public class CheckPathDetailController extends BaseController
{
    @Autowired
    private ICheckPathDetailService checkPathDetailService;

    /**
     * 查询对账汇总列表
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:list")
    @GetMapping("/list")
    public TableDataInfo list(String workdate, String batchId)
    {
        startPage();
        List<CheckPathDetialDO> list = checkPathDetailService.select(workdate, batchId,"");
        return getDataTable(list);
    }

    /**
     * 查询对账明细列表（不平对账）
     */
    @PreAuthorize(hasPermi = "pay-batch:checkpath:detailsList")
    @GetMapping("/detailsList")
    public TableDataInfo detailsList(String workdate, String batchId,String checkstatus)
    {
        startPage();
        List<CheckPathDetialDO> list = checkPathDetailService.select(workdate, batchId,checkstatus);
        return getDataTable(list);
    }
}
