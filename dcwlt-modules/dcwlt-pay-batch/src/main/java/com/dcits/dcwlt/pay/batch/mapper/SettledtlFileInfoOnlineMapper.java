package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 17:09
 */
@Mapper
public interface SettledtlFileInfoOnlineMapper {

    public List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId);
}
