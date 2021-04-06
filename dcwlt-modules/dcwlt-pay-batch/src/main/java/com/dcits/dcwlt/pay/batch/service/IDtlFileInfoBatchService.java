package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;

import java.util.List;

/**
 * 批次查询对账明细文件列表
 *
 * @author 
 * @date 2021/1/7
 */
public interface IDtlFileInfoBatchService {

    /**
     * 更新对账文件清单
     * @param dtlFileInfDOS
     * @return
     */
    int replaceDtlFileInfo(List<DtlFileInfDO> dtlFileInfDOS);

    /**
     * 更新对账清单数据
     * @param dtlFileInfDO
     * @return
     */
    int updateDtlFileInfo(DtlFileInfDO dtlFileInfDO);

    /**
     * 更新文件明细处理状态
     * @param dtlFileInfDO
     * @return
     */
    int updateDtlFileProcessStatusInfo(DtlFileInfDO dtlFileInfDO);

    int updateDtlFileProcessStatusInfoWhenApply(DtlFileInfDO dtlFileInfDO);

    /**
     * 查询对账清单数据
     * @param batchId
     * @return
     */
    List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId);

    /**
     * 查找指定批次中某种处理状态的数据
     * @param batchId
     * @return
     */
    List<DtlFileInfDO> queryDtlFileInfoBatchIdStatus(String batchId, String processStatus);

    List<DtlFileInfDO> queryDtlFileInfoBatchIdUnStatus(String batchId, String processStatus);

    /**
     * 删除对账明细文件列表，指定交易批次号
     * @param batchId
     * @return
     */
    int deleteByBatchId(String batchId);

    int updateDtlFileInfoLastProcessStatus(DtlFileInfDO dtlFileInfDO);
}
