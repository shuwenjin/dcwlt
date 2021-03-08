package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;


/**
 * @author weimeiyuan
 * @version 1.0.0
 * Description:对帐明细文件接口
 * @Date 2021/1/5 15:14
 */
public interface IDtlFileInfService {
    /**
     * 先检查是否有相同的数据，有则先删除再插入
     * @param dtlFileInfDO
     * @return
     * @throws Exception
     */
    int replaceDtlFileInfDO(DtlFileInfDO dtlFileInfDO) throws Exception;
}
