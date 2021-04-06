package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;

import java.util.List;

/**
 * 联机库查询对账文件列表
 *
 * @author 
 * @date 2021/1/7
 */
public interface IDtlFileInfoOnlineService {

    List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId);
}
