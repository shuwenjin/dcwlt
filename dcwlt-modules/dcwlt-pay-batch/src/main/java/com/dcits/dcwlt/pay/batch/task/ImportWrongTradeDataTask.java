package com.dcits.dcwlt.pay.batch.task;

import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.CheckCollectDO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 以报文标示号从联机库导数据到批量库（金融登记簿抽数）
 */
@Service
public class ImportWrongTradeDataTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(ImportWrongTradeDataTask.class);
    @Autowired
    private ICheckCollectService checkCollectService;
    @Autowired
    private ICheckResultService checkResultService;
    private List<CheckResultDO> list;

    @Value("${ecny.check.wrong.max.count:100}")
    private String checkWrongMaxCount;
    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        list = checkResultService.selectCheckResultByStatus(taskExec.getSettleDate(), taskExec.getBatchId());
    }

    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行不平记录重对账抽数任务，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        //每次最多使用多少个报文标示号进行查询
        //int limit = Integer.parseInt(RtpUtil.getInstance().getProperty("ecny.check.wrong.max.count", "100"));

        int limit = Integer.parseInt(checkWrongMaxCount);
        if (list != null && !list.isEmpty()) {
        	int size = list.size();
        	List<String> msgList = null;
            if (size > limit) {
            	//分批次操作
            	msgList = new ArrayList<>(limit);
            	for (int i = 0; i < size; i++) {
            		msgList.add(list.get(i).getMsgId());
            		if (i > 0 && i % limit == 0) {
            			insertOrUpdateCollectData(taskExec.getBatchId(), msgList);
            			msgList.clear();
            		}
				}
            	if (!msgList.isEmpty()) {
            		insertOrUpdateCollectData(taskExec.getBatchId(), msgList);
            	}
            } else {
	        	msgList = new ArrayList<>(size);
	        	for (int i = 0; i < size; i++) {
	        		msgList.add(list.get(i).getMsgId());
				}
	        	insertOrUpdateCollectData(taskExec.getBatchId(), msgList);
            }
        }
        logger.info("执行不平记录重对账抽数任务完成");
    }
    
    private void insertOrUpdateCollectData(String batchId, List<String> msgList) {
//    	List<CheckCollectDO> collectList = checkCollectService.selectPayTranstionDetailsByMsgId(
//				BusiConst.DATASOURCE_ONLINE, batchId, msgList);

        List<CheckCollectDO> collectList = checkCollectService.selectPayTranstionDetailsByMsgId(
                batchId, msgList);
    	if (collectList != null && !collectList.isEmpty()) {
            checkCollectService.replaceIntoBatch(collectList);
    	}
    }
}
