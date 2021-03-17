package com.dcits.dcwlt.pay.batch.service.impl;


import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.common.pay.enums.TaskExecStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskExecMapper;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskGroupExecMapper;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskGroupInfoMapper;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskInfoMapper;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import com.dcits.dcwlt.pay.batch.task.ISettleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 清算批次执行服务
 * @author liuyuanhui
 *
 */
@Service
public class SettleTaskExecServiceImpl implements ISettleTaskExecService {
	private static final Logger logger = LoggerFactory.getLogger(SettleTaskExecServiceImpl.class);

	private SimpleDateFormat fullTimeFormat = new SimpleDateFormat("yyyyMMddhhmmss");

	@Autowired
	private SettleTaskInfoMapper settleTaskInfoMapper;

	@Autowired
	private SettleTaskGroupInfoMapper settleTaskGroupInfoMapper;

	@Autowired
	private SettleTaskExecMapper settleTaskExecMapper;

	@Autowired
	private SettleTaskGroupExecMapper settleTaskGroupExecMapper;
	
	/**
	 * 按清算日期、任务组号运行工作组
	 * @param settleDate --对账日期
	 * @param taskGroupCode --批次任务号
	 * @throws SettleTaskException
	 */

	@Override
	public void runTaskGroup(String settleDate, String batchId, String taskGroupCode, String digitalEnvelope)
			throws SettleTaskException {
		SettleTaskGroupExecDO taskGroupExec = settleTaskGroupExecMapper
				.queryTaskGroupExec(settleDate, batchId, taskGroupCode);
		if (taskGroupExec == null) {
			// 从数据库配置中初始化
			taskGroupExec = initTaskExecByCfg(settleDate, batchId, taskGroupCode, digitalEnvelope);
			// 写入任务执行表
			genTaskExec(taskGroupExec);
			
			//再次查询
			taskGroupExec = settleTaskGroupExecMapper.queryTaskGroupExec(settleDate, batchId, taskGroupCode);
		}
		
		this.runTaskGroup(taskGroupExec);
	}
	
	/**
	 * 运行任务
	 * @param settleDate
	 * @param taskGroupCode
	 * @param taskCode
	 * @throws SettleTaskException
	 */
	@Override
	public void runTask(String settleDate, String taskGroupCode, String taskCode, String batchId)
			throws SettleTaskException {

		SettleTaskExecDO taskExec = settleTaskExecMapper.queryTaskExecByCode(
				settleDate, taskGroupCode, taskCode, batchId);
		this.checkTaskExec(taskExec);
		this.run(taskExec);
		
	}

	@Override
	public void runTaskGroup(SettleTaskGroupExecDO taskGroupExec) throws SettleTaskException {

		if (taskGroupExec.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0001.getCode(), SettleTaskErrorEnum.BC0001.getDesc());
		}
		
		//查询任务列表
		List<SettleTaskExecDO> taskExecList = settleTaskExecMapper.queryTaskExecListByCode(taskGroupExec.getSettleDate(),
				taskGroupExec.getTaskGroupCode(), taskGroupExec.getBatchId());
		
		//更新任务组状态为执行中
		this.updateTaskGroupExecDoing(taskGroupExec);

		logger.info("开始执行任务组：" + taskGroupExec);
		
		try {
			for (SettleTaskExecDO taskExec : taskExecList) {
				logger.info("任务信息：" + taskExec);
				//判断任务状态
				if (taskExec.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
					logger.info("任务：" + taskExec.getTaskCode() + "已执行成功，跳过");
					continue;
				}
				String settleDate = taskExec.getSettleDate();
				String taskGroupCode = taskExec.getTaskGroupCode();
				String taskCode = taskExec.getTaskCode();
				String batchId = taskExec.getBatchId();
				this.runTask(settleDate, taskGroupCode, taskCode, batchId);
			}
			//任务组执行成功
			taskGroupExec.setExecState(TaskExecStatusEnum.SUCC.getCode());
			this.updateTaskGroupExecDone(taskGroupExec);
		} catch (SettleTaskException e) {
			logger.error("runTaskGroup execute fialure, error code={}, message={}", e.getErrorCode(), e.getErrorMsg());
			taskGroupExec.setExecState(TaskExecStatusEnum.EXPT.getCode());
			this.updateTaskGroupExecDone(taskGroupExec);
			throw new SettleTaskException(e.getErrorCode(), e.getErrorMsg());
		}
	}

	@Override
	public void updateTaskGroupExecDoing(SettleTaskGroupExecDO taskGroupExec) throws SettleTaskException {

		taskGroupExec.setExecState(TaskExecStatusEnum.PROC.getCode());
		taskGroupExec.setUpdateTime(fullTimeFormat.format(new Date()));
		int ret = settleTaskGroupExecMapper.updateTaskGroupExecState(taskGroupExec);
		if(ret != 1) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0002.getCode(), SettleTaskErrorEnum.BC0002.getDesc());
		}
	}

	@Override
	public void updateTaskGroupExecDone(SettleTaskGroupExecDO taskGroupExec) throws SettleTaskException {

		taskGroupExec.setUpdateTime(fullTimeFormat.format(new Date()));
		int ret = settleTaskGroupExecMapper.updateTaskGroupExecState(taskGroupExec);

		if(ret != 1) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0002.getCode(), SettleTaskErrorEnum.BC0002.getDesc());
		}
	}

	@Override
	public SettleTaskGroupExecDO initTaskExecByCfg(String settleDate, String batchId, String taskGroupCode,
			String digitalEnvelope) throws SettleTaskException {
		// 根据任务组号，查询任务组信息
		SettleTaskGroupInfoDO taskGroupInfo = settleTaskGroupInfoMapper
				.queryTaskGroupInfoToExec(taskGroupCode);
		if (taskGroupInfo == null) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0003.getCode(), SettleTaskErrorEnum.BC0003.getDesc());
		}
		SettleTaskGroupExecDO taskGroupExec = new SettleTaskGroupExecDO();
		taskGroupExec.setTaskGroupCode(taskGroupInfo.getTaskGroupCode());
		taskGroupExec.setTaskGroupName(taskGroupInfo.getTaskGroupName());
		taskGroupExec.setBusiCode(taskGroupInfo.getBusiCode());
		taskGroupExec.setBusiCodeName(taskGroupInfo.getBusiCodeName());
		taskGroupExec.setSettleDate(settleDate);
		taskGroupExec.setBatchId(batchId);
		taskGroupExec.setCreateTime(fullTimeFormat.format(new Date()));
		taskGroupExec.setUpdateTime(taskGroupExec.getCreateTime());
		taskGroupExec.setExecState(TaskExecStatusEnum.INIT.getCode());
		
		// 根据任务组号，查询该任务组下的所有任务
		List<SettleTaskInfoDO> taskInfoList = settleTaskInfoMapper
				.queryTaskInfoListToExec(taskGroupCode);
		if (taskInfoList == null || taskInfoList.isEmpty()) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0004.getCode(), SettleTaskErrorEnum.BC0004.getDesc());
		}
		List<SettleTaskExecDO> taskExecList = new ArrayList<>();
		for (SettleTaskInfoDO taskInfo : taskInfoList) {
			SettleTaskExecDO taskExec = new SettleTaskExecDO();
			taskExec.setTaskCode(taskInfo.getTaskCode());
			taskExec.setTaskName(taskInfo.getTaskName());
			taskExec.setTaskGroupCode(taskInfo.getTaskGroupCode());
			taskExec.setTaskType(taskInfo.getTaskType());
			taskExec.setTaskIndex(taskInfo.getTaskIndex());
			taskExec.setTaskClassName(taskInfo.getTaskClassName());
			if (digitalEnvelope != null) {
				taskExec.setExecParam(digitalEnvelope);
			} else {
				taskExec.setExecParam(taskInfo.getExecParam());
			}
			taskExec.setBatchId(batchId);
			
			taskExec.setSettleDate(settleDate);
			taskExec.setTaskGroupName(taskGroupExec.getTaskGroupName());
			taskExec.setBusiCode(taskGroupExec.getBusiCode());
			taskExec.setBusiCodeName(taskGroupExec.getBusiCodeName());
			taskExec.setExecState(TaskExecStatusEnum.INIT.getCode()); //有效状态
			taskExec.setStartTime(fullTimeFormat.format(new Date()));
			taskExec.setEndTime(fullTimeFormat.format(new Date()));
			taskExecList.add(taskExec);
		}
		taskGroupExec.setTaskExecList(taskExecList);

		return taskGroupExec;
	}

	@Override
	public void genTaskExec(SettleTaskGroupExecDO taskGroupExec) throws SettleTaskException {
		try {
			//写入任务组执行信息
			int ret = settleTaskGroupExecMapper.insert(taskGroupExec);
			if (ret != 1) {
				throw new SettleTaskException(SettleTaskErrorEnum.BC0005.getCode(), SettleTaskErrorEnum.BC0005.getDesc());
			}
			
			//写入任务列表执行信息
			for (SettleTaskExecDO taskExec : taskGroupExec.getTaskExecList()) {
				ret = settleTaskExecMapper.insert(taskExec);
				if (ret != 1) {
					throw new SettleTaskException(SettleTaskErrorEnum.BC0005.getCode(), SettleTaskErrorEnum.BC0005.getDesc());
				}
			}
		} catch (SettleTaskException e) {
			logger.error("genTaskExec execute fialure, error code={}, message={}", e.getErrorCode(), e.getErrorMsg());
			throw e;
		} catch (Exception e) {
			logger.error("genTaskExec execute fialure", e);
			throw new SettleTaskException(SettleTaskErrorEnum.BC0006.getCode(), SettleTaskErrorEnum.BC0006.getDesc());
		}
	}

	@Override
	public void checkTaskExec(SettleTaskExecDO taskExec) throws SettleTaskException {
		if (taskExec == null) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0007.getCode(), SettleTaskErrorEnum.BC0007.getDesc());
		}
		logger.info("开始执行任务："  + taskExec.toString());
		
		
		if(taskExec.getTaskClassName() == null || taskExec.getTaskClassName().equals("")) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0008.getCode(), SettleTaskErrorEnum.BC0008.getDesc());
		}
		
		if(taskExec.getExecState().equals(TaskExecStatusEnum.PROC.getCode())) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0009.getCode(), SettleTaskErrorEnum.BC0009.getDesc());
		}
		
		if(taskExec.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0010.getCode(), SettleTaskErrorEnum.BC0010.getDesc());
		}
	}

	@Override
	public ISettleTask getTaskExecInstance(String taskClassName) throws SettleTaskException {
		
		ISettleTask settleTask = null;
		try {

			Class<?> clazz = Class.forName(taskClassName);
//			
//			settleTask = (ISettleTask)clazz.newInstance();
			//Object bean = clazz.newInstance();
			Object bean = SpringUtils.getBean(clazz);
			settleTask = (ISettleTask) bean;
		} catch (Exception e) {
			logger.error("加载任务执行任务失败", e);
			throw new SettleTaskException(SettleTaskErrorEnum.BC0011.getCode(), SettleTaskErrorEnum.BC0011.getDesc());
		}
		
		return settleTask;
	}

	@Override
	public void run(SettleTaskExecDO taskExec) throws SettleTaskException {
		
		
		ISettleTask settleTask = this.getTaskExecInstance(taskExec.getTaskClassName());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		//更新状态为处理中
		taskExec.setStartTime(df.format(new Date()));
		taskExec.setExecStateOld(taskExec.getExecState());
		taskExec.setExecState(TaskExecStatusEnum.PROC.getCode());
		int ret = this.updateTaskExecState(taskExec);
		if (ret != 1) {
			throw new SettleTaskException(SettleTaskErrorEnum.BC0012.getCode(), SettleTaskErrorEnum.BC0012.getDesc());
		}
		
		try {
			logger.info("开始执行任务initTask, 任务名称{}", taskExec.getTaskName());
			settleTask.initTask(taskExec);
			logger.info("开始执行任务runTask, 任务名称{}", taskExec.getTaskName());
			settleTask.runTask(taskExec);
			logger.info("任务执行成功,任务名称:{}, 任务编号{}",taskExec.getTaskName(), taskExec.getTaskCode());
			taskExec.setEndTime(df.format(new Date()));
			taskExec.setExecState(TaskExecStatusEnum.SUCC.getCode());
			this.updateTaskExecDone(taskExec);
		} catch (Exception e) {
			if (e instanceof SettleTaskException) {
				logger.error("运行时错误:{}", e.getMessage());
			} else {
				logger.error("运行时错误:{}", e);
			}
			taskExec.setEndTime(df.format(new Date()));
			taskExec.setExecState(TaskExecStatusEnum.EXPT.getCode());
			this.updateTaskExecDone(taskExec);
			throw new SettleTaskException(SettleTaskErrorEnum.BC0013.getCode(), SettleTaskErrorEnum.BC0013.getDesc());
		}
		
	}

	@Override
	public int updateTaskExecState(SettleTaskExecDO taskExec) {
		return settleTaskExecMapper.updateTaskExecState(taskExec);
	}

	@Override
	public int updateTaskExecDone(SettleTaskExecDO taskExec) {
		return settleTaskExecMapper.updateTaskExecDone(taskExec);
	}


	@Override
	public SettleTaskExecDO queryTaskExecByCode(String settleDate, String taskGroupCode, String taskCode, String batchId)
	{
		SettleTaskExecDO taskExec = settleTaskExecMapper.queryTaskExecByCode(settleDate, taskGroupCode, taskCode, batchId);
		return taskExec;
	}

	@Override
	public int deleteExecTaskByGroupId(String settleDate, String taskGroupCode, String batchId){

		return settleTaskExecMapper.deleteExecTaskByGroupId( settleDate,  taskGroupCode,  batchId);
	}

}
