package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongQueryResDTO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleCheckResultMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CheckResultServiceImpl implements ICheckResultService {

	@Autowired
	private SettleCheckResultMapper settleCheckResultMapper;

    /**
     * 插入数据
     * @return
     */
    @Override
    public int insert(CheckResultDO checkResultDO) {
        return settleCheckResultMapper.insert( checkResultDO);
    }
    
    /**
     * 批量插入数据
     * @param list
     * @return
     */
	@Override
    public int insertByBatch(List<CheckResultDO> list) {
    	return settleCheckResultMapper.insertByBatch( list);
    }
    
    /**
     * 查询数据
     * @param payDate
     * @param paySerno
     * @return
     */
	@Override
    public CheckResultDO select(String payDate, String paySerno) {
        return settleCheckResultMapper.selectCheckResult(payDate, paySerno);
    }
    
    /**
     * 查询需要处理的对账结果表
     * @param batchId
     * @return
     */
	@Override
    public List<CheckResultDO> selectCheckResultByStatus(String payDate, String batchId) {
    	return settleCheckResultMapper.selectCheckResultByStatus(payDate,batchId);
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
	@Override
    public List<CheckWrongQueryResDTO> selectWrongMatchCheckResult(String payDate, String batchId, String msgType,
																   String checkStatus, String procStatus, String msgId, int count, int queryPageFlag, String queryPageKey) {
//    	Map<String, Object> map = new HashMap<>();
//    	map.put("payDate( payDate);
//    	map.put("batchId( batchId);
//    	map.put("msgType( msgType);
//    	map.put("checkStatus( checkStatus);
//    	map.put("procStatus( procStatus);
//    	map.put("msgId( msgId);
//    	map.put("count( count);
//    	map.put("queryPageFlag( queryPageFlag);
//    	map.put("queryPageKey( queryPageKey);
    	return settleCheckResultMapper.selectWrongMatchCheckResult(payDate,batchId,msgType,checkStatus,procStatus,msgId,count,queryPageFlag,queryPageKey
		);
    }

	/**
	 * 对账状态更新
	 * @param checkResultDO
	 * @return
	 */
	@Override
	public int update(CheckResultDO checkResultDO) {
		return settleCheckResultMapper.updateCheckResult(checkResultDO);
	}
}
