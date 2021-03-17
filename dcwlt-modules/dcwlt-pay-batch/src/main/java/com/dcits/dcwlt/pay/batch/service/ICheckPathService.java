package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.CheckPathDO;

import java.util.List;
import java.util.Map;

public interface ICheckPathService {

    /**
     * 插入数据
     * @param checkPathDO
     * @return
     */
    public int insert(CheckPathDO checkPathDO);
    
    /**
     * 批量插入数据
     * @param list
     * @return
     */
    public int insertByBatch(List<CheckPathDO> list);
    /**
     * 查询数据
     * @param batchDate
     * @param batchId
     * @return
     */
    public List<CheckPathDO> select(String batchDate, String batchId);
    
    /**
     * 在联机库中查询通道对账汇总数据
     * @param batchDate
     * @param batchId
     * @return
     */
    public List<CheckPathDO> selectPathInOnline(//String datasource,
                                                String batchDate, String batchId);
    
    /**
     * 使用主键更新对账后的状态
     * @param payDate
     * @param paySerno
     * @param checkStatus
     * @return
     */
    public int updateStatus(String payDate, String paySerno, String checkStatus);


    /**
     *  分页查询
     * @param map
     * @param sqlId
     * @param queryPageFlag
     * @param pageCnt
     * @return
     */
//    Map<String, Object> querySummaryInfo(Map<String, Object> map, String sqlId, String queryPageFlag, int pageCnt);

}
