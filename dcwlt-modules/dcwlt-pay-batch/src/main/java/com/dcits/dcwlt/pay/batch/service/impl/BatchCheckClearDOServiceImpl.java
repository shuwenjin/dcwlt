package com.dcits.dcwlt.pay.batch.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.BatchCheckClearDOMapper;
import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;
import com.dcits.dcwlt.pay.batch.service.IBatchCheckClearDOService;

/**
 * 资金调整汇总核对Service业务层处理
 * 
 * @author yangjld
 * @date 2021-03-09
 */
@Service
public class BatchCheckClearDOServiceImpl implements IBatchCheckClearDOService 
{
    @Autowired
    private BatchCheckClearDOMapper batchCheckClearDOMapper;

    /**
     * 查询资金调整汇总核对
     * 
     * @param msgid 资金调整汇总核对ID
     * @return 资金调整汇总核对
     */
    @Override
    public BatchCheckClearDO selectBatchCheckClearDOById(String msgid)
    {
        return batchCheckClearDOMapper.selectBatchCheckClearDOById(msgid);
    }

    /**
     * 查询资金调整汇总核对列表
     * 
     * @param batchCheckClearDO 资金调整汇总核对
     * @return 资金调整汇总核对
     */
    @Override
    public List<BatchCheckClearDO> selectBatchCheckClearDOList(BatchCheckClearDO batchCheckClearDO)
    {
        return batchCheckClearDOMapper.selectBatchCheckClearDOList(batchCheckClearDO);
    }

}
