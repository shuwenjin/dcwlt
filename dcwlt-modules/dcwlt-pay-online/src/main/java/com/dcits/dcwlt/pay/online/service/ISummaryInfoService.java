package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.SummaryInfoDO;


/**
 * @Author weimeiyuan
 * @Date 2021/1/6 22:39
 * @Version 1.0
 * Description:
 */
public interface ISummaryInfoService {
    int replaceSummaryInfoDO(SummaryInfoDO summaryInfoDO) throws Exception;
}
