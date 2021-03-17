package com.dcits.dcwlt.pay.batch.task;

import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import com.dcits.dcwlt.pay.batch.service.ICheckPathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 汇总对账处理
 */
@Service
public class CheckTradeSummaryTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeSummaryTask.class);
    @Autowired
    private ICheckCollectService checkCollectService;
    @Autowired
    private ICheckPathService checkPathService;
    @Autowired
    private IGenerateCodeService generateCodeService;
    
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
    	logger.info("汇总对账，前置检查, 对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
    	//1. 在联机库中查询人行的汇总对账数据
        List<CheckPathDO> pathList = checkPathService.selectPathInOnline(//BusiConst.DATASOURCE_ONLINE,
				taskExec.getSettleDate(), taskExec.getBatchId());
        if (pathList == null || pathList.isEmpty()) {
        	logger.info("找到报文总数:0");
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0014.getCode(), SettleTaskErrorEnum.BC0014.getDesc());
        } else {
        	logger.info("找到报文总数:{}", pathList.size());
        	//给每一条记录配置主键
        	for (CheckPathDO item : pathList) {
				item.setPaySerno(generateCodeService.generatePlatformFlowNo());
				item.setLastUpDate(DateUtil.getDefaultDate());
				item.setLastUpTime(DateUtil.getDefaultTime());
			}
        }
        //2.把联机库中的数据导入批量库中
        checkPathService.insertByBatch(pathList);
    }

    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行汇总对账，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        //查询人行的汇总对账数据
        List<CheckPathDO> pathList = checkPathService.select(taskExec.getSettleDate(), taskExec.getBatchId());
        if (pathList == null || pathList.isEmpty()) {
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0014.getCode(), SettleTaskErrorEnum.BC0014.getDesc());
        }
        //1. 查询总的汇总数据
        List<CheckPathDO> totalCollectList = checkCollectService.selectCollectSumTotal(taskExec.getBatchId());
        //2. 查询分片汇总数据
        List<CheckPathDO> collectMsgList = checkCollectService.selectCollectSumByMsgType(taskExec.getBatchId());
        if (collectMsgList == null || collectMsgList.isEmpty()) {
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0015.getCode(), SettleTaskErrorEnum.BC0015.getDesc());
        }
        //转成Map
        Map<String, CheckPathDO> collectMap = collectMsgList.stream().collect(
      		Collectors.toMap(path -> path.getMsgType() + "-" + path.getCcy() + "-" + path.getMsgBizStatus(), path -> path));
        //3. 核对总数
        CheckPathDO pathDO = pathList.get(0);
        CheckPathDO totalCollect = null;
        for (CheckPathDO chk : totalCollectList) {
			if ("CNY".equals(chk.getCcy())) {//人民币
				totalCollect = chk;
				break;
			}
		}
        if (totalCollect == null) {
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0018.getCode(), SettleTaskErrorEnum.BC0018.getDesc());
        }
        if (checkTotalDiff(pathDO, totalCollect)) {
        	logger.info("trade record date={}, serno={}, total number different.", pathDO.getPayDate(), pathDO.getPaySerno());
        	checkPathService.updateStatus(pathDO.getPayDate(), pathDO.getPaySerno(), CheckStatusEnum.DIFF.getCode());
        } 
    	//4. 总数一样，对分组汇总
        CheckPathDO cnt = null;
        String key = null;
        for (CheckPathDO path : pathList) {
        	logger.info("check path record msgType={}, status={}, ccy={}", 
        			path.getMsgType(), path.getMsgBizStatus(), path.getCcy());
        	key = path.getMsgType() + "-" + path.getCcy() + "-" + path.getMsgBizStatus();
            cnt =  collectMap.get(key);
            if (cnt == null) {
                //人行有数据，我行没有
            	logger.info("check found in pboc, don't found in cgb.", 
            			path.getMsgType(), path.getMsgBizStatus(), path.getCcy());
                checkPathService.updateStatus(path.getPayDate(), path.getPaySerno(), CheckStatusEnum.LESS.getCode());
            } else {
            	if (checkSubDiff(path, cnt)) {
            		logger.info("check found in diff mode."); 
            		checkPathService.updateStatus(path.getPayDate(), path.getPaySerno(), CheckStatusEnum.DIFF.getCode());
            	} else {
            		logger.info("check found in same mode.");
            		checkPathService.updateStatus(path.getPayDate(), path.getPaySerno(), CheckStatusEnum.SAME.getCode());
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
    private boolean checkTotalDiff(CheckPathDO path, CheckPathDO cnt) {
	   if (!checkNumber(path.getCountNum(), cnt.getCountNum())) {
//		   	logger.info("getCountNum is diff path={}, collect={}", path.getCountNum(), cnt.getCountNum());
	       	//报文的交易记录数不一致
	       	return true;
       } 
	   if (!checkNumber(path.getCountAmt(), cnt.getCountAmt())) {
//		   logger.info("getCountAmt is diff path={}, collect={}", path.getCountAmt(), cnt.getCountAmt());
       		//报文的交易金额不一致
		   return true;
       } 
	   if (!checkNumber(path.getDBITCountNum(), cnt.getDBITCountNum())) {
//		   logger.info("getDBITCountNum is diff path={}, collect={}", path.getDBITCountNum(), cnt.getDBITCountNum());
       		//报文记录付款笔数总数不一致
    	   return true;
       } 
	   if (!checkNumber(path.getDBITCountAmt(), cnt.getDBITCountAmt())) {
//		   logger.info("getDBITCountAmt is diff path={}, collect={}", path.getDBITCountAmt(), cnt.getDBITCountAmt());
       		//报文记录付款金额总数不一致
		   return true;
       } 
	   if (!checkNumber(path.getCRDTCountNum(), cnt.getCRDTCountNum())) {
//		   logger.info("getCRDTCountNum is diff path={}, collect={}", path.getCRDTCountNum(), cnt.getCRDTCountNum());
       		//报文记录收款笔数总数不一致
		   return true;
       } 
	   if (!checkNumber(path.getCRDTCountAmt(), cnt.getCRDTCountAmt())) {
//		   logger.info("getCRDTCountAmt is diff path={}, collect={}", path.getCRDTCountAmt(), cnt.getCRDTCountAmt());
       		//报文记录收款金额总数不一致
		   return true;
       }
	   return false;
    }
    
    /**
     * 检查汇总各项数据是否一致
     * @param path
     * @param cnt
     * @return
     */
    private boolean checkSubDiff(CheckPathDO path, CheckPathDO cnt) {
	   if (!checkNumber(path.getMsgCountNum(), cnt.getMsgCountNum())) {
//		   	logger.info("getMsgCountNum is diff path={}, collect={}", path.getMsgCountNum(), cnt.getMsgCountNum());
	       	//报文的交易记录数不一致
	       	return true;
       } 
	   if (!checkNumber(path.getMsgCountAmt(), cnt.getMsgCountAmt())) {
//		   logger.info("getMsgCountAmt is diff path={}, collect={}", path.getMsgCountAmt(), cnt.getMsgCountAmt());
       		//报文的交易金额不一致
		   return true;
       } 
	   if (!checkNumber(path.getMsgDBITCountNum(), cnt.getMsgDBITCountNum())) {
//		   logger.info("getMsgDBITCountNum is diff path={}, collect={}", path.getMsgDBITCountNum(), cnt.getMsgDBITCountNum());
       		//报文记录付款笔数总数不一致
    	   return true;
       } 
	   if (!checkNumber(path.getMsgDBITCountAmt(), cnt.getMsgDBITCountAmt())) {
//		   logger.info("getMsgDBITCountAmt is diff path={}, collect={}", path.getMsgDBITCountAmt(), cnt.getMsgDBITCountAmt());
       		//报文记录付款金额总数不一致
		   return true;
       } 
	   if (!checkNumber(path.getMsgCRDTCountNum(), cnt.getMsgCRDTCountNum())) {
//		   logger.info("getMsgCRDTCountNum is diff path={}, collect={}", path.getMsgCRDTCountNum(), cnt.getMsgCRDTCountNum());
       		//报文记录收款笔数总数不一致
		   return true;
       } 
	   if (!checkNumber(path.getMsgCRDTCountAmt(), cnt.getMsgCRDTCountAmt())) {
//		   logger.info("getMsgCRDTCountAmt is diff path={}, collect={}", path.getMsgCRDTCountAmt(), cnt.getMsgCRDTCountAmt());
       		//报文记录收款金额总数不一致
		   return true;
       }
	   return false;
    }
    
    private boolean checkNumber(String a, String b) {
    	long v1 = 0;
    	try {
    		if (a != null) {
    			v1 = (long)Double.parseDouble(a.trim());
    		}
		} catch (Exception e) {
			v1 = 0;
		}
    	
    	long v2 = 0;
    	try {
    		if (b != null) {
    			v2 = (long)Double.parseDouble(b.trim());
    		}
		} catch (Exception e) {
			v2 = 0;
		}		
    	logger.info("checkNumber a ={}, b={}, v1 = {}, v2={}", a, b, v1, v2);
    	return (v1 == v2);
    }
}
