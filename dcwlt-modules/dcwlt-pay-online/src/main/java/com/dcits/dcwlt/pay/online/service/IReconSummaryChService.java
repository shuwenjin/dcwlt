package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.ReconSummaryChkDO;


/**
 * @author
 * @Time 2021/1/3 17:06
 * @Version 1.0
 * Description:
 */
public interface IReconSummaryChService {
    /**
     * 先检查是否有相同的数据，有则先删除再插入
     * @param reconSummaryChkDO
     * @return
     * @throws Exception
     */
    int replaceReconSummaryChkDO(ReconSummaryChkDO reconSummaryChkDO) throws Exception;

}
