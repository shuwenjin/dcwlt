package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.CheckPathDetialDO;

import java.util.List;

public interface ICheckPathDetailService {

    int replaceCheckPathDtl(List<CheckPathDetialDO> checkPathDetialDOS);
    
    /**
     * 统计当前批次两方都存在的情况下需要对账的总数
     * @param workdate
     * @param batchId
     * @return
     */
    public int selectCheckDetailCount(String workdate, String batchId);
    
    /**
     * 对人行的交易明细和我行的交易明细进行状态比对，然后更新结果状态, 不限制更新的条数
     */
    public int updateCheckStatusForMsgMatchWithoutLimit(String workdate, String batchId, List<String> msgIdList);
    
    /**
     * 对人行的交易明细和我行的交易明细进行状态比对，然后更新结果状态, 限制更新的条数
     * @param updateNumber 每次更新的记录数
     */
    public int updateCheckStatusForMsgMatchWithLimit(String workdate, String batchId, int updateNumber);
    
    public int insert(CheckPathDetialDO checkPathDetialDO);

    public List<CheckPathDetialDO> select(String workdate, String batchId,String checkstatus);
    
    public int insertByBatch(List<CheckPathDetialDO> checkPathDetialDOS);
    
    /**
     * 使用主键更新对账后的状态
     * @param msgId
     * @param dtlMsgId
     * @param checkStatus
     * @return
     */
    public int updateStatus(String msgId, String dtlMsgId, String checkStatus);
    
    /**
     * 查找当前日期下，给定的批次号中人行有数据，但我行没有的明细数据的总数
     * @param workdate
     * @param batchId
     * @return
     */
    public int selectPathDetailWhitCgbNotFoundCount(String workdate, String batchId);
    
    /**
     * 查找当前日期下，给定的批次号中人行有数据，但我行没有的明细数据
     * @param workdate
     * @param batchId
     * @return
     */
    public List<CheckPathDetialDO> selectPathDetailWhitCgbNotFound(String workdate, String batchId, Integer offset, Integer limit);




    CheckPathDetialDO querySingle(String mshId, String dtlmsgId);
}
