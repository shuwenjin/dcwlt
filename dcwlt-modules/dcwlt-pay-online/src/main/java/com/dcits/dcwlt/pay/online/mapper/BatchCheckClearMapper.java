package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchCheckClearMapper {
    /*
     * 根据msgId查询资金调整核对信息
     * */
    BatchCheckClearDO findBatchCheckClear(BatchCheckClearDO batchCheckClearDO);

    /*
     * 增加资金调整核对信息
     * */
    int addBatchCheckClear(BatchCheckClearDO batchCheckClearDOList);
}
