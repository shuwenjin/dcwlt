package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 任务组实体类
 *
 * @author Ashin
 */
public class SettleTaskGroupInfoDO {

    @NotBlank(message = "信息组代码不能为空")
    @Excel(name = "任务组代码")
    private String taskGroupCode;
    @NotBlank(message = "信息组名称不能为空")
	@Excel(name = "任务组名称")
    private String taskGroupName;
    @NotBlank(message = "业务代码不能为空")
	@Excel(name = "业务代码")
    private String busiCode;
    @NotBlank(message = "业务名称不能为空")
	@Excel(name = "业务名称")
    private String busiCodeName;
    @NotNull(message = "备注不能为空")
	@Excel(name = "备注")
    private String remark;

    private List<SettleTaskInfoDO> taskInfoList;

    public String getTaskGroupCode() {
        return taskGroupCode;
    }

    public void setTaskGroupCode(String taskGroupCode) {
        this.taskGroupCode = taskGroupCode;
    }

    public String getTaskGroupName() {
        return taskGroupName;
    }

    public void setTaskGroupName(String taskGroupName) {
        this.taskGroupName = taskGroupName;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getBusiCodeName() {
        return busiCodeName;
    }

    public void setBusiCodeName(String busiCodeName) {
        this.busiCodeName = busiCodeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SettleTaskInfoDO> getTaskInfoList() {
        return taskInfoList;
    }

    public void setTaskInfoList(List<SettleTaskInfoDO> taskInfoList) {
        this.taskInfoList = taskInfoList;
    }

}
