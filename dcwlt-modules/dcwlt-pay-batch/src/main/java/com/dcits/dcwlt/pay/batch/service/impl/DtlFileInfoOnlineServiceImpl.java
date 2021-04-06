package com.dcits.dcwlt.pay.batch.service.impl;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.common.pay.constant.BusiConst;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.mapper.SettledtlFileInfoOnlineMapper;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 联机对账文件组件
 *
 * @author 
 * @date 2021/1/7
 */
@Component
public class DtlFileInfoOnlineServiceImpl implements IDtlFileInfoOnlineService {
    private static final String QUERY_URL = "dtlfile_online.queryDtlFileInfoByBatchId";

    @Autowired
    private SettledtlFileInfoOnlineMapper settledtlFileInfoOnlineMapper;
    /**
     * 根据批次号查询对账文件列表
     * @param batchId
     * @return
     */
    @Override
    public List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId) {
        return settledtlFileInfoOnlineMapper.queryDtlFileInfoByBatchId(batchId);
    }
}
