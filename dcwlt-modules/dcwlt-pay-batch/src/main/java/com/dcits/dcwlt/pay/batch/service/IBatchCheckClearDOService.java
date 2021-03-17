package com.dcits.dcwlt.pay.batch.service;

import java.util.List;
import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;

/**
 * 资金调整汇总核对Service接口
 * 
 * @author yangjld
 * @date 2021-03-09
 */
public interface IBatchCheckClearDOService 
{
    /**
     * 查询资金调整汇总核对
     * 
     * @param msgid 资金调整汇总核对ID
     * @return 资金调整汇总核对
     */
    public BatchCheckClearDO selectBatchCheckClearDOById(String msgid);

    /**
     * 查询资金调整汇总核对列表
     * 
     * @param batchCheckClearDO 资金调整汇总核对
     * @return 资金调整汇总核对集合
     */
    public List<BatchCheckClearDO> selectBatchCheckClearDOList(BatchCheckClearDO batchCheckClearDO);

}
