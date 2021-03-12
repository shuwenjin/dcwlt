package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.domain.dcep.gwf008.CommonRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.gwf008.Gwf008ReqDTO;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;

/**
 * 对账文下载申请服务
 *
 * @author majun
 * @date 2021/1/6
 */
public interface IGwf008Service {

    /**
     * 文件下载申请报文初始化
     *
     * @param fileName    文件名称
     * @param filePath    商户地址（文件地址）
     * @param exChangeKey 数字信封（由711报文中的数字信封字段决定，五羊支付提供）
     * @return
     */
    Gwf008ReqDTO initGwf008ReqDTO(String fileName, String filePath, String transDate, String exChangeKey);


    Gwf008ReqDTO initGwf008ReqDTO(String fileName, String filePath,
                                  String exChangeKey, String transDate, String overWrite);

    /**
     * 发起请求服务
     *
     * @param gwf008ReqDTO
     * @return
     */
    CommonRspDTO doApply(Gwf008ReqDTO gwf008ReqDTO) throws SettleTaskException;

    boolean fileDownloadApply(DtlFileInfDO dtlFileInfDO, String exChangeKey) throws SettleTaskException;

    void fileDownloadApply(String batchId, String exChangeKey) throws SettleTaskException;
}
