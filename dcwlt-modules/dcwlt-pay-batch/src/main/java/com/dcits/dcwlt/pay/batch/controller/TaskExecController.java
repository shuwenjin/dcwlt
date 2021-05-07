package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务执行信息管理
 */
@RestController
@RequestMapping("/taskexec")
public class TaskExecController extends BaseController {

    @Autowired
    private ISettleTaskGroupExecService settleTaskGroupExecService;

    @Autowired
    private ISettleTaskExecService settleTaskExecService;

    @PreAuthorize(hasPermi = "task:taskexecgroup:list")
    @GetMapping(value = "/taskgroupexec/list")
    public TableDataInfo queryTaskInfogGroupList(SettleTaskGroupExecDO settleTaskGroupExecDO) {
        startPage();

        return getDataTable(settleTaskGroupExecService.querySettleTaskExec(settleTaskGroupExecDO));
    }

    @PreAuthorize(hasPermi = "task:taskexec:list")
    @GetMapping(value = "/taskexec/list")
    public TableDataInfo queryTaskInfoList(SettleTaskExecDO settleTaskExecDO) {
        startPage();

        return getDataTable(settleTaskExecService.queryTaskExecList(settleTaskExecDO));
    }


}
