package com.dcits.dcwlt.pay.api.model;


import java.util.HashMap;

/**
 * 任务执行信息
 *
 */
public class SettleTaskExecDO {

	private String settleDate; //清算日期
	private String taskCode; //任务代码
	private String taskName; //任务名称
	private String taskGroupCode; //任务分组代码
	private String taskGroupName; //任务分组名称
	private String busiCode; //业务代码
	private String busiCodeName; //业务名称
	private String batchId; //批次号
	private String taskType; //任务类型
	private String taskIndex; //任务顺序
	private String taskClassName; //任务执行类
	private String execParam; //执行参数
	private String execState; //执行状态 00-初始 01-执行中 02-执行成功  03-执行失败
	private String startTime; //开始时间
	private String endTime; //结束时间

	private HashMap paramDict; //执行参数临时变量
	private String execStateOld;

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

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

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getExecState() {
		return execState;
	}

	public void setExecState(String execState) {
		this.execState = execState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExecStateOld() {
		return execStateOld;
	}

	public void setExecStateOld(String execStateOld) {
		this.execStateOld = execStateOld;
	}

	public HashMap getParamDict() {
		if(paramDict == null) {
			synchronized (this) {
				paramDict = new HashMap();
				if (execParam != null && !execParam.equals("")) {
					String[] paramArray = execParam.split(";");
					for (String param : paramArray) {
						String[] keyArray = param.split("=");
						paramDict.put(keyArray[0], keyArray[1]);
					}
				}
			}
		}
		return paramDict;
	}

	public void setParamDict(HashMap paramDict) {
		this.paramDict = paramDict;
	}

	@Override
	public String toString() {
		return "SettleTaskExecDO{" +
				"settleDate='" + settleDate + '\'' +
				", taskCode='" + taskCode + '\'' +
				", taskName='" + taskName + '\'' +
				", taskGroupCode='" + taskGroupCode + '\'' +
				", taskGroupName='" + taskGroupName + '\'' +
				", busiCode='" + busiCode + '\'' +
				", busiCodeName='" + busiCodeName + '\'' +
				", batchId='" + batchId + '\'' +
				", taskType='" + taskType + '\'' +
				", taskIndex='" + taskIndex + '\'' +
				", taskClassName='" + taskClassName + '\'' +
				", execParam='" + execParam + '\'' +
				", execState='" + execState + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				'}';
	}
}
