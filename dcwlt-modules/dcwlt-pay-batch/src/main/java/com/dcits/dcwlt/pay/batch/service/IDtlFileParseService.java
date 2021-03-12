package com.dcits.dcwlt.pay.batch.service;



import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;

import java.io.IOException;

/**
 * 对账文件解压解析
 *
 * @author majun
 * @date 2021/1/8
 */
public interface IDtlFileParseService {

    /**
     * 解析指定批次的文件
     * @param batchId
     */
    void parse(String batchId) throws Exception;

    /**
     * 判断是否完成指定批次所有文件的解析
     * @param batchId
     * @return
     */
    boolean parseSuccess(String batchId);

    /**
     * 解析明细对账文件，将文件数据入库
     * @param dtlFileInfDO
     * @param file
     * @throws IOException
     */
    void parseFile(DtlFileInfDO dtlFileInfDO, String file) throws IOException;
}
