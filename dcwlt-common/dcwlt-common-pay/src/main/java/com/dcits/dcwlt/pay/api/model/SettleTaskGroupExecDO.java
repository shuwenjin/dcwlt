package com.dcits.dcwlt.pay.api.model;

import java.util.List;

/**
 * 任务组执行时信息
 * 
 *
 */
public class SettleTaskGroupExecDO {

	private String settleDate;  //清算日期
	private String taskGroupCode; //任务组代码
	private String taskGroupName; //任务组名称
	private String busiCode; //业务代码
	private String busiCodeName; //业务名称
	private String batchId; //批次号
	//执行状态 00-初始 01-执行中 02-执行成功  03-执行失败
	private String execState;
	private String createTime; //创建时间
	private String updateTime; //更新时间
	private String execStateOld;
	private List<SettleTaskExecDO> taskExecList;
	
	
	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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

	public String getExecState() {
		return execState;
	}

	public void setExecState(String execState) {
		this.execState = execState;
	}

	public List<SettleTaskExecDO> getTaskExecList() {
		return taskExecList;
	}

	public void setTaskExecList(List<SettleTaskExecDO> taskExecList) {
		this.taskExecList = taskExecList;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getExecStateOld() {
		return execStateOld;
	}

	public void setExecStateOld(String execStateOld) {
		this.execStateOld = execStateOld;
	}
	@Override
	public String toString() {
		return "SettleTaskGroupExecDO{" +
				"settleDate='" + settleDate + '\'' +
				", taskGroupCode='" + taskGroupCode + '\'' +
				", taskGroupName='" + taskGroupName + '\'' +
				", busiCode='" + busiCode + '\'' +
				", busiCodeName='" + busiCodeName + '\'' +
				", batchId='" + batchId + '\'' +
				", execState='" + execState + '\'' +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", taskExecList=" + taskExecList +
				'}';
	}
}
