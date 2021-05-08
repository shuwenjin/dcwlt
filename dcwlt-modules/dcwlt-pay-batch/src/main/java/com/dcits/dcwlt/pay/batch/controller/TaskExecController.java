package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 任务执行信息管理
 *
 * @author Ashin
 */
@RestController
@RequestMapping("/taskexec")
public class TaskExecController extends BaseController {

    @Autowired
    private ISettleTaskGroupExecService settleTaskGroupExecService;

    @Autowired
    private ISettleTaskExecService settleTaskExecService;

    @PreAuthorize(hasPermi = "task:taskgroupexec:list")
    @GetMapping(value = "/taskgroupexec/list")
    public TableDataInfo queryTaskGroupExecList(SettleTaskGroupExecDO settleTaskGroupExecDO) {
        startPage();

        return getDataTable(settleTaskGroupExecService.querySettleTaskExec(settleTaskGroupExecDO));
    }

    @Log(title = "任务执行组", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "task:taskgroupexec:export")
    @PostMapping("/taskgroupexec/export")
    public void exportTaskInfo(HttpServletResponse response, SettleTaskGroupExecDO settleTaskGroupExecDO) throws IOException {
        List<SettleTaskGroupExecDO> resultList = settleTaskGroupExecService.querySettleTaskExec(settleTaskGroupExecDO);

        ExcelUtil<SettleTaskGroupExecDO> util = new ExcelUtil<>(SettleTaskGroupExecDO.class);

        util.exportExcel(response, resultList, "任务执行组");
    }

    @PreAuthorize(hasPermi = "task:taskexec:list")
    @GetMapping(value = "/taskexec/list")
    public TableDataInfo queryTaskExecList(SettleTaskExecDO settleTaskExecDO) {
        startPage();

        return getDataTable(settleTaskExecService.queryTaskExecList(settleTaskExecDO));
    }

    @Log(title = "任务执行日志详情", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "task:taskexec:export")
    @PostMapping("/taskexec/export")
    public void exportTaskInfo(HttpServletResponse response, SettleTaskExecDO settleTaskExecDO) throws IOException {
        List<SettleTaskExecDO> resultList = settleTaskExecService.queryTaskExecList(settleTaskExecDO);

        ExcelUtil<SettleTaskExecDO> util = new ExcelUtil<>(SettleTaskExecDO.class);

        util.exportExcel(response, resultList, "任务执行日志详情");
    }


}
