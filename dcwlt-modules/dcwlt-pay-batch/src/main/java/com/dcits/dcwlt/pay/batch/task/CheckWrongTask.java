package com.dcits.dcwlt.pay.batch.task;

import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.common.pay.enums.CheckProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDetailService;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 明细对账不平结果重对账
 */
@Service
public class CheckWrongTask implements ISettleTask {
	private static final Logger logger = LoggerFactory.getLogger(CheckWrongTask.class);

	@Value("${ecny.check.wrong.max.count:100}")
	private String checkWrongMaxCount;
	@Autowired
    private ICheckCollectService checkCollectService;
    @Autowired
    private ICheckPathDetailService checkPathDetailService;
    @Autowired
    private ICheckResultService checkResultService;
    private List<CheckResultDO> list;
    private int size = 0;
    
	@Override
	public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
		list = checkResultService.selectCheckResultByStatus(taskExec.getSettleDate(), taskExec.getBatchId());	
		if (list != null) {
			size = list.size();
		}
	}

	@Override
	public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
		logger.info("开始执行不平记录对账，对账日期:{}，对账批次:{}，总共需处理记录数：{}", taskExec.getSettleDate(), 
				taskExec.getBatchId(), size);
		if (size == 0) {
			return;
		}
		//每次最多使用多少个报文标示号进行查询
		int limit = Integer.parseInt(StringUtils.isNotBlank(checkWrongMaxCount)? checkWrongMaxCount :"100");
		List<String> msgList = null;
		List<CheckResultDO> procList = new ArrayList<>(size);
        if (size > limit) {
        	//分批次操作
        	msgList = new ArrayList<>(limit);
        	for (int i = 0; i < size; i++) {
        		msgList.add(list.get(i).getMsgId());
        		if (i > 0 && i % limit == 0) {
        			updateCheckStatusByMsgId(taskExec.getSettleDate(), taskExec.getBatchId(), msgList);
        			List<CheckResultDO> retList = checkCollectService.selectPathDetailByMsgIdList(taskExec.getBatchId(), msgList);
        			procList.addAll(retList);
        			msgList.clear();
        		}
			}
        	if (!msgList.isEmpty()) {
        		updateCheckStatusByMsgId(taskExec.getSettleDate(), taskExec.getBatchId(), msgList);
        		List<CheckResultDO> retList = checkCollectService.selectPathDetailByMsgIdList(taskExec.getBatchId(), msgList);
    			procList.addAll(retList);
        	}
        } else {
        	//没有超过设置的最大值，一次性提交
        	msgList = new ArrayList<>(size);
        	for (int i = 0; i < size; i++) {
        		msgList.add(list.get(i).getMsgId());
			}
        	updateCheckStatusByMsgId(taskExec.getSettleDate(), taskExec.getBatchId(), msgList);
        	List<CheckResultDO> retList = checkCollectService.selectPathDetailByMsgIdList(taskExec.getBatchId(), msgList);
			procList.addAll(retList);
        }
		Map<String, CheckResultDO> map = procList.stream().collect(
	      		Collectors.toMap(CheckResultDO::getMsgId, p -> p));
		//逐条对结果进行处理
		CheckResultDO data = null;
		for (CheckResultDO crd : list) {
			data = map.get(crd.getMsgId());
			if (data != null) {
				crd.setCheckStatus(data.getCheckStatus());
			} 
			if (CheckStatusEnum.SAME.equalCode(crd.getCheckStatus())) {
				crd.setProcStatus(CheckProcStatusEnum.DONE.getCode());
			} else {
				crd.setProcStatus(CheckProcStatusEnum.INIT.getCode());
			}
			crd.setLastUpDate(DateUtil.getDefaultDate());
			crd.setLastUpTime(DateUtil.getDefaultTime());
            checkResultService.update(crd);
		}
		logger.info("不平记录对账结束，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
	}
	
	private void updateCheckStatusByMsgId(String settleDate, String batchId, List<String> msgIdList) {
		TransactionStatus txStatus = null;
		try {
			//txStatus = DBUtil.beginTx(false);// 开启一个事务
			checkPathDetailService.updateCheckStatusForMsgMatchWithoutLimit(settleDate, 
					batchId, msgIdList);
    		// try的最后结束并且提交事务
			//DBUtil.commit(txStatus);
		} catch (Exception e) {
			// 发生了异常必须先执行回滚事务
			if (txStatus != null) {
				//DBUtil.rollback(txStatus);
			}
			logger.error("updateCheckStatusByMsgId", e);
		}
	}
}
