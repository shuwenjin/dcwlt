package com.dcits.dcwlt.pay.api.model;

import java.util.List;

/**
 * 任务组实体类
 * 
 *
 */
public class SettleTaskGroupInfoDO {

	private String taskGroupCode;
	private String taskGroupName;
	private String busiCode;
	private String busiCodeName;
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
