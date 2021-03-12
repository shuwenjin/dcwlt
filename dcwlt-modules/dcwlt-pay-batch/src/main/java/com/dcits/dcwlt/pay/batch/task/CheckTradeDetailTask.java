package com.dcits.dcwlt.pay.batch.task;


import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.TranstionStatusCodeEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.CheckCollectDO;
import com.dcits.dcwlt.pay.api.model.CheckPathDetialDO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDetailService;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;
import com.dcits.dcwlt.pay.batch.service.impl.CheckWrongResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

/**
 * 汇总对账处理
 */
@Service
public class CheckTradeDetailTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailTask.class);
    @Autowired
    private ICheckCollectService checkCollectService;
    @Autowired
    private CheckWrongResultService basicService;
    @Autowired
    private ICheckPathDetailService checkPathDetailService;
    @Autowired
    private ICheckResultService checkResultService;
    
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //检查抽取交易数据是否完成
    }

    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行明细对账，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        //String limit = RtpUtil.getInstance().getProperty("ecny.check.batch.sql.update.limit", "2000");
		String limit = "2000";
        int batchUpdateLimit = Integer.parseInt(limit); //对账时SQL每次更新的数据大小
        //1. 执行人行和我行明细数据勾对SQL，更新结果状态
        //统计当前批次两边都存在的情况下的数据量
        int updateCount = checkPathDetailService.selectCheckDetailCount(taskExec.getSettleDate(), taskExec.getBatchId());
        logger.info("找到两边都存在的对账记录数：{}条, 每次更新的条数：{}", updateCount, batchUpdateLimit);
        int loopCount = 0;
        if (updateCount > batchUpdateLimit) {
        	//分批次提交更新
        	loopCount = updateCount / batchUpdateLimit;
        	if (updateCount % batchUpdateLimit > 0) {
        		loopCount = loopCount + 1;
        	}
        	TransactionStatus txStatus = null;
        	for (int i = 0; i < loopCount; i++) {
        		logger.info("共分成：{}批次更新对账状态，当前正在更新第{}次", loopCount, i);
        		try {
        			//txStatus = DBUtil.beginTx(false);// 开启一个事务
	        		checkPathDetailService.updateCheckStatusForMsgMatchWithLimit(taskExec.getSettleDate(), 
	        				taskExec.getBatchId(), batchUpdateLimit);
	        		// try的最后结束并且提交事务
					//DBUtil.commit(txStatus);
        		} catch (Exception e) {
        			// 发生了异常必须先执行回滚事务
        			if (txStatus != null) {
        				//DBUtil.rollback(txStatus);
        			}
        			logger.error("updateCheckStatusForMsgMatchWithLimit", e);
        		}
			}
        } else {
        	//没有超过设置的最大值，一次性提交
        	checkPathDetailService.updateCheckStatusForMsgMatchWithoutLimit(taskExec.getSettleDate(), 
        			taskExec.getBatchId(), null);
        }
        //2. 处理对账明细表中对账异常的数据
        //int insertLimit = Integer.parseInt(RtpUtil.getInstance().getProperty("db.batch.limit", "1000"));
		int insertLimit = Integer.parseInt("1000");
        batchInsertCheckResult(taskExec.getBatchId(), insertLimit);
        
        //3. 查找人行有交易的数据，但我行没有的交易数据
        handleCgbNotFoundCase(taskExec.getSettleDate(), taskExec.getBatchId(), insertLimit);
        
        //4. 处理我行有交易的数据，但人行没有的交易数据
        batchInsertCheckResultWithPbocNotFound(taskExec.getBatchId(), insertLimit);
        logger.info("明细对账结束，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
    }
    
    /**
     * 处理对账明细表中对账异常的数据，如果有异常，插入到对账异常表中
     * @param limit
     */
    private void batchInsertCheckResult(String batchId, int limit) {
    	int total = checkCollectService.selectPathDetailForNotMatchCount(batchId);
    	logger.debug("找到对账异常的总数为：{}", total);
    	if (total == 0) {
    		logger.debug("找到对账异常的总数为0，不需要插入。");
    		return;
    	}
        if (total < limit) {
        	//没有超过设置的最大值，一次性提交
        	List<CheckResultDO> notMatchList = checkCollectService.selectPathDetailForNotMatch(batchId, null, null);
        	checkResultService.insertByBatch(notMatchList); 
        } else {
        	int loopCount = total / limit;
        	if (total % limit > 0) {
        		loopCount = loopCount + 1;
        	}
        	List<CheckResultDO> notMatchList = null;
        	int offset = 0;
        	TransactionStatus txStatus = null;
        	for (int i = 0; i < loopCount; i++) {
        		offset = i * limit;
        		logger.info("共分成：{}批次插入对账异常数据，当前正在插入第{}次", loopCount, i);
        		notMatchList = checkCollectService.selectPathDetailForNotMatch(batchId, offset, limit);
        		try {
        			//txStatus = DBUtil.beginTx(false);// 开启一个事务
        			//找到账异常，插入对账结果表
                	checkResultService.insertByBatch(notMatchList); 
                	// try的最后结束并且提交事务
					//DBUtil.commit(txStatus);
					txStatus = null;
        		} catch (Exception e) {
        			// 发生了异常必须先执行回滚事务
        			if (txStatus != null) {
        				//DBUtil.rollback(txStatus);
        			}
        			logger.error("batchInsertCheckResult", e);
        		}
        	}
        }
    }
    /**
     * 处理我行不存在，但人行有的异常
     * @param workdate
     * @param batchId
     * @param limit
     */
    private void handleCgbNotFoundCase(String workdate, String batchId, int limit) {
    	int total = checkPathDetailService.selectPathDetailWhitCgbNotFoundCount(workdate, batchId);
    	logger.debug("找到我行不存在，但人行有的异常的总数为：{}", total);
    	if (total == 0) {
    		logger.debug("找到我行不存在，但人行有的异常的总数为0，不需要处理。");
    		return;
    	}
    	if (total < limit) {
        	//没有超过设置的最大值，一次性提交
    		List<CheckPathDetialDO> pathList = checkPathDetailService.selectPathDetailWhitCgbNotFound(workdate, batchId, null, null);
    		for (CheckPathDetialDO pathDO : pathList) {
    			if (isHandleStatus(pathDO.getDtlBizStatus())) {
	            	checkPathDetailService.updateStatus(pathDO.getMsgId(), pathDO.getDtlMsgId(), CheckStatusEnum.LESS.getCode());
	            	basicService.createExptResult(null, pathDO.getMsgType(), pathDO.getDtlMsgId(),
	        				pathDO.getBatchId(), null, pathDO.getInstgDrctPty(), pathDO.getDBITParty(),
	        				null, pathDO.getPayerAccount(), pathDO.getCRDTParty(), pathDO.getPayeeName(),
	        				pathDO.getPayeeAccount(), pathDO.getPayeeWalletId(), pathDO.getCcy(), pathDO.getAmount(),
	        				pathDO.getOgnlMsgType(), pathDO.getOgnlMsgId(), null, null, null, CheckStatusEnum.LESS.getCode());
    			} else {
    				//人行是失败或者错误的状态，我行不存在，当作对平处理
    				checkPathDetailService.updateStatus(pathDO.getMsgId(), pathDO.getDtlMsgId(), CheckStatusEnum.SAME.getCode());
    			}
			} 
        } else {
        	int loopCount = total / limit;
        	if (total % limit > 0) {
        		loopCount = loopCount + 1;
        	}
        	List<CheckPathDetialDO> pathList = null;
        	int offset = 0;
        	for (int i = 0; i < loopCount; i++) {
        		offset = i * limit;
        		logger.info("共分成：{}批次查询我行不存在，但人行有的异常数据，当前正在插入第{}次", loopCount, i);
        		pathList = checkPathDetailService.selectPathDetailWhitCgbNotFound(workdate, batchId, offset, limit);
        		for (CheckPathDetialDO pathDO : pathList) {
        			if (isHandleStatus(pathDO.getDtlBizStatus())) {
	                	checkPathDetailService.updateStatus(pathDO.getMsgId(), pathDO.getDtlMsgId(), CheckStatusEnum.LESS.getCode());
	                	basicService.createExptResult(null, pathDO.getMsgType(), pathDO.getDtlMsgId(),
	            				pathDO.getBatchId(), null, pathDO.getInstgDrctPty(), pathDO.getDBITParty(),
	            				null, pathDO.getPayerAccount(), pathDO.getCRDTParty(), pathDO.getPayeeName(),
	            				pathDO.getPayeeAccount(), pathDO.getPayeeWalletId(), pathDO.getCcy(), pathDO.getAmount(),
	            				pathDO.getOgnlMsgType(), pathDO.getOgnlMsgId(), null, null, null, CheckStatusEnum.LESS.getCode());
        			} else {
        				//人行是失败或者错误的状态，我行不存在，当作对平处理
        				checkPathDetailService.updateStatus(pathDO.getMsgId(), pathDO.getDtlMsgId(), CheckStatusEnum.SAME.getCode());
        			}
    			} 
        	}
        }
    }
    /**
     * 处理我行有交易的数据，但人行没有的交易数据，如果有异常，插入到对账异常表中
     * @param limit
     */
    private void batchInsertCheckResultWithPbocNotFound(String batchId, int limit) {
    	int total = checkCollectService.selectCollectWithPbocNotFoundCount(batchId);
    	logger.debug("找到对账异常的总数为：{}", total);
    	if (total == 0) {
    		logger.debug("找到对账异常的总数为0，不需要插入。");
    		return;
    	}
        if (total < limit) {
        	//没有超过设置的最大值，一次性提交
        	List<CheckResultDO> notMatchList = checkCollectService.selectCollectWithPbocNotFound(batchId, null, null);
        	checkResultService.insertByBatch(notMatchList); 
        } else {
        	int loopCount = total / limit;
        	if (total % limit > 0) {
        		loopCount = loopCount + 1;
        	}
        	List<CheckResultDO> notMatchList = null;
        	int offset = 0;
        	TransactionStatus txStatus = null;
        	for (int i = 0; i < loopCount; i++) {
        		offset = i * limit;
        		logger.info("共分成：{}批次插入对账异常数据，当前正在插入第{}次", loopCount, i);
        		notMatchList = checkCollectService.selectCollectWithPbocNotFound(batchId, offset, limit);
        		try {
        			//txStatus = DBUtil.beginTx(false);// 开启一个事务
        			//找到账异常，插入对账结果表
                	checkResultService.insertByBatch(notMatchList); 
                	// try的最后结束并且提交事务
					//DBUtil.commit(txStatus);
					txStatus = null;
        		} catch (Exception e) {
        			// 发生了异常必须先执行回滚事务
        			if (txStatus != null) {
        				//DBUtil.rollback(txStatus);
        			}
        			logger.error("batchInsertCheckResultWithPbocNotFound", e);
        		}
        	}
        }
    }
    
    /**
     * 检查汇总各项数据是否一致
     * @param path
     * @param cnt
     * @return
     */
	private CheckStatusEnum checkDiff(CheckPathDetialDO path, CheckCollectDO cnt) {
		// 交易状态不一致
		return checkStatus(path.getDtlBizStatus(), cnt.getTradeStatus());
		//第一阶段先做状态检查，后续再做其他的检查
		/*
		if (!path.getAmount().equals(cnt.getAmount())) {
			// 交易金额不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getCcy().equals(cnt.getCcy())) {
			// 货币代码不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getDBITParty().equals(cnt.getDBITParty())) {
			// 付款机构编码不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getCRDTParty().equals(cnt.getCRDTParty())) {
			// 收款机构编码不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getPayerAccount().equals(cnt.getPayerAccount())) {
			// 付款人账号不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getPayeeAccount().equals(cnt.getPayeeAccount())) {
			// 收款人账号不一致
			return CheckStatusEnum.DIFF;
		}
		if (!path.getPayeeName().equals(cnt.getPayeeName())) {
			// 收款人名称不一致
			return CheckStatusEnum.DIFF;
		}
		return CheckStatusEnum.SAME;
		*/
	}
	
	/**
	 * 只有当人行的交易状态为成功或者为处理中，才能需要检查我行的数据是否存在
	 * @param pathStatus
	 * @return
	 */
	private boolean isHandleStatus(String pathStatus) {
		if (ProcessStsCdEnum.PR00.getCode().equals(pathStatus)) {
			return true;
		}
		if (ProcessStsCdEnum.PR02.getCode().equals(pathStatus)) {
			return true;
		}
		if (ProcessStsCdEnum.PR03.getCode().equals(pathStatus)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查人行和我行的交易状态
	 * @param pathStatus 互联互通交易状态
	 * @param tradeStatus 行内交易状态
	 * @return
	 */
	private CheckStatusEnum checkStatus(String pathStatus, String tradeStatus) {
		if (ProcessStsCdEnum.PR02.getCode().equals(pathStatus)) {
			//互联互通交易 处理中，不用对比
			return CheckStatusEnum.PROC;
		}
		if (ProcessStsCdEnum.PR00.getCode().equals(pathStatus) || ProcessStsCdEnum.PR03.getCode().equals(pathStatus)) {
			//互联互通交易 成功
			if (TranstionStatusCodeEnum.SUCCESS.getCode().equals(tradeStatus) || TranstionStatusCodeEnum.PRECREDITSUCCESS.getCode().equals(tradeStatus)) {
				return CheckStatusEnum.SAME; //相同
			} else if (TranstionStatusCodeEnum.FAILED.getCode().equals(tradeStatus) || TranstionStatusCodeEnum.REVERSED.getCode().equals(tradeStatus)) {
				return CheckStatusEnum.FANS; //我行失败，人行成功
			} else {
				return CheckStatusEnum.EANS; //我行异常，人行成功
			}
		} 
		if (ProcessStsCdEnum.PR01.getCode().equals(pathStatus) || ProcessStsCdEnum.PR04.getCode().equals(pathStatus)) {
			//互联互通交易 失败
			if (TranstionStatusCodeEnum.SUCCESS.getCode().equals(tradeStatus) || TranstionStatusCodeEnum.PRECREDITSUCCESS.getCode().equals(tradeStatus)) {
				return CheckStatusEnum.SANF; //我行成功，人行失败
			} else if (TranstionStatusCodeEnum.FAILED.getCode().equals(tradeStatus) || TranstionStatusCodeEnum.REVERSED.getCode().equals(tradeStatus)) {
				return CheckStatusEnum.SAME; //相同
			} else {
				return CheckStatusEnum.EANF; //我行异常，人行失败
			}
		}
		//互联互通不知名的状态，返回人行处理中
		return CheckStatusEnum.PROC;
	}
}
