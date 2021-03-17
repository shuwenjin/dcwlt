package com.dcits.dcwlt.pay.batch.service.impl;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.constant.BusiConst;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongCheckReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongCheckResDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongQueryReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongQueryResDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.page.PageResult;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
import com.dcits.dcwlt.pay.batch.task.TaskExecRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 通用处理服务
 **/
@Service
public class CheckWrongResultService extends BasicEncyService {
	private static final Logger logger = LoggerFactory.getLogger(CheckWrongResultService.class);
	
	@Autowired
    private ICheckResultService checkResultService;
	@Autowired
    private IGenerateCodeService sernoService;
	@Autowired
    private ISettleTaskExecService taskExecService;
	@Autowired
    private ISettleTaskGroupExecService taskGroupExecService;
	
	/**
	 * 创建比较结果记录
	 */
	public void createExptResult(String coreAcctDate, String msgType, String msgId, String batchId, 
    		String payFlag, String instgDrctPty, String dbitParty, String payerWalletId, String payerAccount,
    		String crdtParty, String payeeName, String payeeAccount, String payeeWalletId, String ccy,
    		String amount, String ognlMsgType, String ognlMsgId, String tradeStatus, String coreStatus,
    		String pathStatus, String checkStatus) {
    	CheckResultDO resultDO = new CheckResultDO();
		resultDO.setPayDate(DateUtil.getDefaultDate());
	    resultDO.setPaySerno(sernoService.generatePlatformFlowNo());           
	    resultDO.setPayTime(DateUtil.getDefaultTime());
	    resultDO.setCoreAcctDate(coreAcctDate) ;  
	    resultDO.setMsgType(msgType);            
	    resultDO.setMsgId(msgId);            
	    resultDO.setBatchId(batchId);   
	    if (StringUtil.isEmpty(payFlag)) {
	    	if (BusiConst.CGB_FINANCIAL_INSTITUTION_CD.equals(dbitParty)) {
	    		resultDO.setPayFlag(BusiConst.IDENTIFICATION_PAYER);
	    	} else {
	    		resultDO.setPayFlag(BusiConst.IDENTIFICATION_PAYEE);
	    	}
	    } else {
	    	resultDO.setPayFlag(payFlag);
	    }
	    resultDO.setInstgDrctPty(instgDrctPty);  
	    resultDO.setDbitParty(dbitParty);    
	    resultDO.setPayerWalletId(payerWalletId);                                            
	    resultDO.setPayerAccount(payerAccount);  
	    resultDO.setCrdtParty(crdtParty);      
	    resultDO.setPayeeName(payeeName);    
	    resultDO.setPayeeAccount(payeeAccount);
	    resultDO.setPayeeWalletId(payeeWalletId);
	    resultDO.setCcy(ccy);               
	    resultDO.setAmount(amount);       
	    resultDO.setOgnlMsgType(ognlMsgType);    
	    resultDO.setOgnlMsgId(ognlMsgId);    
	    resultDO.setTradeStatus(tradeStatus);  
	    resultDO.setCoreStatus(coreStatus);   
	    resultDO.setPathStatus(pathStatus);    
	    resultDO.setCheckStatus(checkStatus); 
	    resultDO.setProcStatus(BusiConst.OPERSTEP_INIT);
	    resultDO.setLastUpDate(resultDO.getPayDate());     
	    resultDO.setLastUpTime(resultDO.getPayTime()); 
	    checkResultService.insert(resultDO);
    }
	
	public ECNYRspDTO<PageResult<CheckWrongQueryResDTO>> queryWrongCheckResult(ECNYReqDTO<CheckWrongQueryReqDTO> ecnyReqDTO) {
		try {
			CheckWrongQueryReqDTO req = ecnyReqDTO.getBody();
	    	List<CheckWrongQueryResDTO> list = queryWrongCheckResult(req.getPayDate(), req.getBatchId(), 
	    			req.getMsgType(), req.getCheckStatus(), req.getProcStatus(), req.getMsgId(), 
	    			req.getCount(), req.getQueryPageFlag(), req.getQueryPageKey());
	    	return createQueryResponse(ecnyReqDTO, list, req.getCount(), req.getQueryPageFlag());
		} catch (SettleTaskException e) {
			return createQueryResponseError(ecnyReqDTO, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("调用查询不平结果错误。", e);
			return createQueryResponseError(ecnyReqDTO, SettleTaskErrorEnum.SYS_ERROR.getCode(),
					SettleTaskErrorEnum.SYS_ERROR.getDesc());
		}
	}
	/**
	 * 对账不平记录查询
	 * @param payDate --平台日期, 必输
	 * @param batchId --交易批次号, 可选
	 * @param msgType --报文编号, 可选
	 * @param checkStatus --对账标识, 可选
	 * @param procStatus --差错处理状态, 可选
	 * @param msgId --报文标识号, 可选 翻页时必填
	 * @param count --查询条数, 必输
	 * @param queryPageFlag --上下翻页操作, 必输, 0首页; 1上翻; 2下翻 只按升序排序
	 * @return
	 */
	private List<CheckWrongQueryResDTO> queryWrongCheckResult(String payDate, String batchId, String msgType, 
			String checkStatus, String procStatus, String msgId, String count, String queryPageFlag,
			String queryPageKey) throws SettleTaskException {
		if (StringUtil.isBlank(payDate)) {
			throw new SettleTaskException(SettleTaskErrorEnum.PAYDATE_NULL.getCode(), SettleTaskErrorEnum.PAYDATE_NULL.getDesc());
		}
		int pageCount = 0;
		try {
			pageCount = Integer.parseInt(count);
		} catch (Exception e) {
			throw new SettleTaskException(SettleTaskErrorEnum.COUNT_INVALID.getCode(), SettleTaskErrorEnum.COUNT_INVALID.getDesc());
		}
		if (pageCount <= 0 || pageCount > BusiConst.MAX_COUNT) {
			throw new SettleTaskException(SettleTaskErrorEnum.COUNT_INVALID_NUM.getCode(), SettleTaskErrorEnum.COUNT_INVALID_NUM.getDesc());
		}
		int queryFlag = 0;
		try {
			queryFlag = Integer.parseInt(queryPageFlag);
		} catch (Exception e) {
			throw new SettleTaskException(SettleTaskErrorEnum.QUERYPAGEFLAG_INVALID_NUM.getCode(), SettleTaskErrorEnum.QUERYPAGEFLAG_INVALID_NUM.getDesc());
		}
		if (queryFlag != BusiConst.QUERY_PAGE_MODE_FRIST 
			&& queryFlag != BusiConst.QUERY_PAGE_MODE_UP 
			&& queryFlag != BusiConst.QUERY_PAGE_MODE_DOWN) {
			throw new SettleTaskException(SettleTaskErrorEnum.QUERYPAGEFLAG_INVALID.getCode(), SettleTaskErrorEnum.QUERYPAGEFLAG_INVALID.getDesc());
		}
		if ((queryFlag == BusiConst.QUERY_PAGE_MODE_UP || queryFlag == BusiConst.QUERY_PAGE_MODE_DOWN) && StringUtil.isBlank(queryPageKey)) {
			throw new SettleTaskException(SettleTaskErrorEnum.MSGID_INVALID.getCode(), SettleTaskErrorEnum.MSGID_INVALID.getDesc());
		}
		return checkResultService.selectWrongMatchCheckResult(payDate, batchId, msgType, checkStatus, procStatus, 
				msgId, pageCount + 1, queryFlag, queryPageKey); //这里比指定的查询条数多查询一条，主要是为了以后判断首页和尾页
	}
	
	public ECNYRspDTO<CheckWrongCheckResDTO> dcepPayRecileApply(ECNYReqDTO<CheckWrongCheckReqDTO> ecnyReqDTO) {
		try {
			CheckWrongCheckReqDTO req = ecnyReqDTO.getBody();
	    	if (req == null) {
	    		throw new SettleTaskException(SettleTaskErrorEnum.BODY_NULL);
	    	}
	    	return createResponse(ecnyReqDTO, payRecileApply(req.getCheckDay(), req.getBatchId(), req.getApplyType()));
		} catch (SettleTaskException e) {
			return createResponseError(ecnyReqDTO, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("调用不平记录重对账错误。", e);
			return createResponseError(ecnyReqDTO, SettleTaskErrorEnum.SYS_ERROR.getCode(), 
					SettleTaskErrorEnum.SYS_ERROR.getDesc());
		}
	}
	
	private ECNYRspDTO<CheckWrongCheckResDTO> createResponse(ECNYReqDTO<CheckWrongCheckReqDTO> ecnyReqDTO, String msg) {
		// 响应头
        ECNYRspHead head = new ECNYRspHead();
        CheckWrongCheckResDTO res = new CheckWrongCheckResDTO();
        res.setProcResult(msg);
        return ECNYRspDTO.newInstance(ecnyReqDTO, head, res, SettleTaskErrorEnum.SUCCESS.getCode(), SettleTaskErrorEnum.SUCCESS.getDesc());
	}
	
	private ECNYRspDTO<CheckWrongCheckResDTO> createResponseError(ECNYReqDTO<CheckWrongCheckReqDTO> ecnyReqDTO, String code, String desc) {
		// 响应头
        ECNYRspHead head = new ECNYRspHead();
        return ECNYRspDTO.newInstance(ecnyReqDTO, head, null, code, desc);
	}
	
	private String payRecileApply(String checkDay, String batchId, String applyType) {
		if (!ApplyTypeEnum.hasEnum(applyType)) {
			throw new SettleTaskException(SettleTaskErrorEnum.CHECK_BATCHID_INVALID);
		}
		ApplyTypeEnum type = ApplyTypeEnum.valueOf(applyType);
		String res = null;
		switch (type) {
		case ERROR:
			res = checkWrongApply(checkDay, batchId);
			break;
		case DETAIL:
			res = "当前系统还没有实现明细重对账功能，请联系管理员确认";
			break;
		case SECOND:
			res = "当前系统还没有实现二次对账功能，请联系管理员确认";
			break;
		case CORE:
			res = "当前系统还没有实现核心对账功能，请联系管理员确认";
			break;
		default:
			res = "不知名操作类型，请联系管理员确认";
			break;
		}
		return res;
	}
	
	private String checkWrongApply(String checkDay, String batchId) {
		if (StringUtil.isBlank(batchId)) {
			throw new SettleTaskException(SettleTaskErrorEnum.CHECK_BATCHID_EMPTY);
		}
		if (batchId.length() > 13) {
			throw new SettleTaskException(SettleTaskErrorEnum.CHECK_BATCHID_LEN);
		}
		if (!StringUtil.isBlank(checkDay) && checkDay.length() > 8) {
			throw new SettleTaskException(SettleTaskErrorEnum.CHECK_CHECKDAY_LEN);
		}
		String settleDate = checkDay;
		if (StringUtil.isBlank(settleDate)) {
			settleDate = DateUtil.getDefaultDate();
		}
		SettleTaskExecDO execTask = taskExecService.queryTaskExecByCode(settleDate, TaskGroupEnum.CHK_WRON.getCode(),
                TaskCodeEnum.CW002.getCode(), batchId);
        if (execTask != null) {
        	if (execTask.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
        		//如果已经成功执行过，清除执行过的任务
        		taskExecService.deleteExecTaskByGroupId(settleDate, TaskGroupEnum.CHK_WRON.getCode(), batchId);
        		taskGroupExecService.deleteExecTaskByGroupId(settleDate, TaskGroupEnum.CHK_WRON.getCode(), batchId);
        	} else {
        		throw new SettleTaskException(SettleTaskErrorEnum.CHECK_TASK_EXIST);
        	}
        }
        execTask = taskExecService.queryTaskExecByCode(settleDate, TaskGroupEnum.CHK_DATA.getCode(),
                TaskCodeEnum.CT006.getCode(), batchId);
        if (execTask != null && !execTask.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
        	throw new SettleTaskException(SettleTaskErrorEnum.CHECK_DETAIL_TASK_EXIST);
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new TaskExecRunnable(settleDate, batchId, TaskGroupEnum.CHK_WRON.getCode()));
        executorService.shutdown();
		return "系统开始执行不平记录重对账任务，请稍后查询执行结果";
	}
}
