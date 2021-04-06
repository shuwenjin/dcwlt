package com.dcits.dcwlt.pay.batch.mapper;

import java.util.List;
import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;

/**
 * 资金调整汇总核对Mapper接口
 * 
 * @author 
 * @date 2021-03-09
 */
public interface BatchCheckClearDOMapper 
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
