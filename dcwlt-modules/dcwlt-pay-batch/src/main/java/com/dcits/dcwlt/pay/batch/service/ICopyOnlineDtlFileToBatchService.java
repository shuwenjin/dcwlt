package com.dcits.dcwlt.pay.batch.service;

/**
 *  转移联机数据库对账文件清单数据到批量数据库
 *
 * @author 
 * @date 2021/1/7
 */
public interface ICopyOnlineDtlFileToBatchService {

    /**
     * 移动联机数据库对账文件清单到批量数据库
     * @param batchId
     * @return
     */
    boolean copyDtlFile(String batchId);
}
