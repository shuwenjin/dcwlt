package com.dcits.dcwlt.pay.api.model;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * 任务信息
 *
 */
public class SettleTaskInfoDO {

	@NotBlank(message = "任务代码不能为空")
	private String taskCode;
	@NotBlank(message = "任务名称不能为空")
	private String taskName;
	@NotBlank(message = "任务组代码不能为空")
	private String taskGroupCode;
	@NotBlank(message = "任务类型不能为空")
	private String taskType;
	@NotBlank(message = "任务顺序不能为空")
	private String taskIndex;
	@NotBlank(message = "任务类不能为空")
	private String taskClassName;
	private String execParam;
	private HashMap paramDict;
	/* 0:有效  1：失效 */
	private String taskState;

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskGroupCode() {
		return taskGroupCode;
	}

	public void setTaskGroupCode(String taskGroupCode) {
		this.taskGroupCode = taskGroupCode;
	}

	public String getTaskClassName() {
		return taskClassName;
	}

	public void setTaskClassName(String taskClassName) {
		this.taskClassName = taskClassName;
	}

	public String getExecParam() {
		return execParam;
	}

	public void setExecParam(String execParam) {
		this.execParam = execParam;
	}

	public HashMap getParamDict() {
		return paramDict;
	}

	public void setParamDict(HashMap paramDict) {
		this.paramDict = paramDict;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(String taskIndex) {
		this.taskIndex = taskIndex;
	}

}
