package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.ClrSummryChk;

/**
 * @author wanyangwei
 * @desc 资金调整汇总核对服务
 */

public interface IClrSummryChkService {
    //ClrSummryChk findByCondition(String msgId, String clearNetNum, String batchId);

    int addClrSummryChk(ClrSummryChk clrSummryChk);
}
