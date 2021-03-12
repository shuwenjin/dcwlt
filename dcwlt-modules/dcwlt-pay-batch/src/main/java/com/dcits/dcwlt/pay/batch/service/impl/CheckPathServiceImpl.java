package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleCheckPathMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckPathService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 通道对账汇总表
 */
@Service
public class CheckPathServiceImpl implements ICheckPathService {

    @Autowired
    private SettleCheckPathMapper settleCheckPathMapper;

    @Override
    public int insert(CheckPathDO checkPathDO) {
        return settleCheckPathMapper.insert(checkPathDO);
    }

    @Override
    public List<CheckPathDO> select(String batchDate, String batchId) {
        return settleCheckPathMapper.selectPath(batchDate,  batchId);
    }

    @Override
    public int updateStatus(String payDate, String paySerno, String checkStatus) {
        //String datetime = DateUtil.getISODateTime();
        String date = "";
        String time = "";
        try {
            date = DateUtil.getDefaultDate();
            time = DateUtil.getDefaultTime();
        } catch (Exception e) {
            date = "";
            time = "";
        }
        return settleCheckPathMapper.updateStatus( payDate,  paySerno, checkStatus, date, time);
    }

    /**
     * 批量插入数据
     * @return
     */
    @Override
    public int insertByBatch(List<CheckPathDO> list) {
    	return settleCheckPathMapper.insertByBatch(list);
    }
    
    /**
     * 在联机库中查询通道对账汇总数据
     * @param batchDate
     * @param batchId
     * @return
     */
    @Override
    public List<CheckPathDO> selectPathInOnline(//String datasource,
                                                String batchDate, String batchId) {
    	return settleCheckPathMapper.selectPathInOnline(batchDate,batchId);
    }

    /**
     * 对账汇总分页查询接口
     * @param map
     * @param sqlId
     * @param queryPageFlag
     * @param pageCnt
     * @return
     */
//    @Override
//    public Map<String, Object> querySummaryInfo(Map<String, Object> map, String sqlId, String queryPageFlag, int pageCnt) {
//        Map<String, Object> stringObjectMap = PageQueryUtils.queryPage(map, sqlId, queryPageFlag, pageCnt);
//        return stringObjectMap;
//    }
}
