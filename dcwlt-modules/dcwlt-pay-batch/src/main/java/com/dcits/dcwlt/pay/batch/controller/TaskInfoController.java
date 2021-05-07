package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupInfoService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务信息管理
 */
@RestController
@RequestMapping("/taskinfo")
public class TaskInfoController extends BaseController {

    @Autowired
    private ISettleTaskGroupInfoService settleTaskGroupInfoService;

    @Autowired
    private ISettleTaskInfoService settleTaskInfoService;

    /**
     * 信息组查询
     *
     * @param settleTaskGroupInfoDO
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskgroupinfo:list")
    @GetMapping(value = "/taskgroupinfo/list")
    public TableDataInfo queryTaskInfogGroupList(SettleTaskGroupInfoDO settleTaskGroupInfoDO) {
        startPage();
        List<SettleTaskGroupInfoDO> settleTaskGroupInfoDOS = settleTaskGroupInfoService.queryTaskGroupInfoList(settleTaskGroupInfoDO);

        return getDataTable(settleTaskGroupInfoDOS);
    }


    /**
     * 添加任务组
     *
     * @param settleTaskGroupInfoDO
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskgroupinfo:add")
    @Log(title = "任务信息组", businessType = BusinessType.INSERT)
    @PostMapping(value = "/taskgroupinfo")
    public AjaxResult addTaskGroupInfo(@Validated @RequestBody SettleTaskGroupInfoDO settleTaskGroupInfoDO) {
        return toAjax(settleTaskGroupInfoService.addTaskGroupInfo(settleTaskGroupInfoDO));
    }

    /**
     * 更新任务组
     *
     * @param settleTaskGroupInfoDO
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskgroupinfo:edit")
    @Log(title = "任务信息组", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/taskgroupinfo")
    public AjaxResult editTaskGroupInfo(@Validated @RequestBody SettleTaskGroupInfoDO settleTaskGroupInfoDO) {
        return toAjax(settleTaskGroupInfoService.updateTaskGroupInfo(settleTaskGroupInfoDO));
    }

    /**
     * 删除任务组
     */
    @PreAuthorize(hasPermi = "task:taskgroupinfo:remove")
    @Log(title = "任务信息组", businessType = BusinessType.DELETE)
    @DeleteMapping("/taskgroupinfo/{taskGroupCodes}")
    public AjaxResult removeGroupInfo(@PathVariable String[] taskGroupCodes) {
        return toAjax(settleTaskGroupInfoService.deleteTaskGroupInfo(taskGroupCodes));
    }


    //=============任务信息列表 删除任务信息 修改组 修改任务信息

    @PreAuthorize(hasPermi = "task:taskinfo:list")
    @GetMapping(value = "/taskinfo/list")
    public TableDataInfo queryTaskInfoList(SettleTaskInfoDO settleTaskInfoDO) {
        startPage();

        List<SettleTaskInfoDO> resultList = settleTaskInfoService.queryTaskInfoList(settleTaskInfoDO);

        return getDataTable(resultList);
    }

    /**
     * 添加任务信息
     *
     * @param settleTaskInfoDO
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskinfo:add")
    @Log(title = "任务信息", businessType = BusinessType.INSERT)
    @PostMapping(value = "/taskinfo")
    public AjaxResult addTaskInfo(@Validated @RequestBody SettleTaskInfoDO settleTaskInfoDO) {
        return toAjax(settleTaskInfoService.addTaskInfo(settleTaskInfoDO));
    }

    /**
     * 更新任务信息
     *
     * @param settleTaskInfoDO
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskinfo:edit")
    @Log(title = "任务信息", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/taskinfo")
    public AjaxResult editTaskInfo(@Validated @RequestBody SettleTaskInfoDO settleTaskInfoDO) {
        return toAjax(settleTaskInfoService.updateTaskInfo(settleTaskInfoDO));
    }

    /***
     * 删除任务信息
     * @param taskCodes
     * @return
     */
    @PreAuthorize(hasPermi = "task:taskinfo:remove")
    @Log(title = "任务信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/taskinfo/{taskCodes}")
    public AjaxResult removeTaskInfo(@PathVariable String[] taskCodes) {
        return toAjax(settleTaskInfoService.deleteTaskInfo(taskCodes));
    }


}
