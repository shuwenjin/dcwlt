package com.dcits.dcwlt.pay.batch.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.dcits.dcwlt.common.pay.constant.BusiConst;
import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.gwf008.*;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import com.dcits.dcwlt.pay.batch.service.IGwf008Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 批量文件下载申请请求
 *
 * @author majun
 * @date 2021/1/6
 */
@Service
public class Gwf008ServiceImpl implements IGwf008Service {

    private static final Logger logger = LoggerFactory.getLogger(Gwf008ServiceImpl.class);

    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;

    /**
     * 初始文件下载申请报文
     *
     * @param fileName    文件名称
     * @param filePath    商户地址（文件地址）
     * @param exChangeKey 数字信封（由711报文中的数字信封字段决定，五羊支付提供）
     * @return
     */
    @Override
    public Gwf008ReqDTO initGwf008ReqDTO(String fileName, String filePath, String transDate, String exChangeKey) {
        //获取文件下载请求报文
        return initGwf008ReqDTO(fileName, filePath, exChangeKey, transDate, "1");
    }

    /**
     * 初始文件下载申请报文
     *
     * @param fileName
     * @param filePath
     * @param exChangeKey
     * @param transDate
     * @param overWrite
     * @return
     */
    @Override
    public Gwf008ReqDTO initGwf008ReqDTO(String fileName, String filePath,
                                         String exChangeKey, String transDate, String overWrite) {
        return Gwf008ReqFactory.getGwf008ReqDTO(fileName, filePath, exChangeKey, transDate, overWrite);
    }

    private void checkRspDTO(CommonRspDTO commonRspDTO) throws SettleTaskException {
        if (commonRspDTO == null) {
            logger.warn("对账明细文件下载申请失败， 开放平台无响应结果");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0108.getCode(), SettleTaskErrorEnum.BC0108.getDesc());
        }
//        if (commonRspDTO.getHead() == null) {
//            logger.warn("对账明细文件下载申请失败， 开放平台响应结果无head头");
//            throw new SettleTaskException(SettleTaskErrorEnum.BC0105.getCode(), SettleTaskErrorEnum.BC0105.getDesc());
//        }

//        Head head = commonRspDTO.getHead();
//        if (!BusiConst.COP_RECEIVE_SUCCESS_CODE.equals(head.getRetCode())) {
//            logger.warn("对账明细文件下载申请失败， 开放平台响应head头提示失败， retCode：{}", head.getRetCode());
//            throw new SettleTaskException(SettleTaskErrorEnum.BC0108.getCode(), SettleTaskErrorEnum.BC0108.getDesc());
//        }

        if (commonRspDTO.getBody() == null) {
            logger.warn("对账明细文件下载申请失败， 开放平台响应结果无Body");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0107.getCode(), SettleTaskErrorEnum.BC0107.getDesc());
        }

    }

    @Override
    public CommonRspDTO doApply(Gwf008ReqDTO gwf008ReqDTO) throws SettleTaskException {
        CommonReqDTO<Gwf008ReqDTO> commonReqDTO = CommonReqDTO.newInstance(BusiConst.SERVICE_CODE_GWF008, gwf008ReqDTO);

        //定义接收响应报文变量
        CommonRspDTO<Gwf008RspDTO> commonRspDTO = null;
        try {
            //发出任务
            commonRspDTO = sendReq(commonReqDTO);
        } catch (Exception e) {
            logger.warn("对账明细文件{}下载失败， 调用开放平台结束失败,失败原因：{}", gwf008ReqDTO.getFileName(), e);
            throw new SettleTaskException(SettleTaskErrorEnum.BC0108.getCode(), SettleTaskErrorEnum.BC0108.getDesc());
        }

        return commonRspDTO;
    }

    /**
     * 发送文件下载申请到开放平台(异步发送)
     *
     * @param commonReqDTO
     */
    private CommonRspDTO<Gwf008RspDTO> sendReq(CommonReqDTO commonReqDTO) {
        logger.info("开始发送文件下载申请请求到开放平台， 请求发送参数{}", commonReqDTO);
        //String rsp = RpcHttpJsonUtil.executeExt(BusiConst.SERVER_CODE_GWF008, JsonUtil.toJSONString(commonReqDTO));
        String rsp ="";

        JSONObject jsonObject = JSONObject.parseObject(rsp);

        return jsonToDCEPRsqDTO(jsonObject);
    }

    @Override
    public boolean fileDownloadApply(DtlFileInfDO dtlFileInfDO, String exChangeKey) throws SettleTaskException {

        //从交易批次号中获取传输日期
        String trantsDate = dtlFileInfDO.getBatchId().substring(1, 9);

        //初始化请求报文, 导对账明细文件时，去掉了文件后缀，文件下载申请是需要加上后缀
        Gwf008ReqDTO gwf008ReqDTO = initGwf008ReqDTO(dtlFileInfDO.getFileName(), dtlFileInfDO.getSrcFilePath(), trantsDate, exChangeKey);

        //执行发送请求
        CommonRspDTO commonRspDTO = doApply(gwf008ReqDTO);

        //响应结果交易
        checkRspDTO(commonRspDTO);

        Gwf008RspDTO gwf008RspDTO = (Gwf008RspDTO) commonRspDTO.getBody();
        //响应结果检查

        if (BusiConst.COP_RECEIVE_SUCCESS_CODE.equals(gwf008RspDTO.getErrorCode())) {
            //判断是否收到成功响应码，成功响应更新库
            dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
            dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());
            dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.APLY.getCode());

            //调用数据库更新操作
            int successNum = dtlFileInfoBatchService.updateDtlFileInfo(dtlFileInfDO);
            if (successNum > 0) {
                return true;
            }
        } else {
            logger.warn("对账文件下载申请失败，申请文件信息{}，开放平台响应信息{}", dtlFileInfDO, gwf008RspDTO);
        }

        return false;
    }

    /**
     * json对象转实体
     *
     * @param rsqMsg 请求报文
     * @return
     */
    private CommonRspDTO<Gwf008RspDTO> jsonToDCEPRsqDTO(JSONObject rsqMsg) {
        //互联互通报文体json对象-->DCEPRspBody实体
        return CommonRspDTO.jsonToDCEPRspDTO(rsqMsg, Gwf008RspDTO.class);
    }

    @Override
    public void fileDownloadApply(String batchId, String exChangeKey) throws SettleTaskException {
        List<DtlFileInfDO> dtlFileInfDOList = dtlFileInfoBatchService.queryDtlFileInfoBatchIdStatus(batchId, DtlFileProcStatusEnum.INIT.getCode());
        if (dtlFileInfDOList != null && !dtlFileInfDOList.isEmpty()) {
            logger.info("执行对账明细文件下载申请，批次号：{}，本次需要申请{}条", batchId, dtlFileInfDOList.size());

            int count = 0;
            for (DtlFileInfDO dtlFileInfDO : dtlFileInfDOList) {
                try {
                    fileDownloadApply(dtlFileInfDO, exChangeKey);
                    count++;
                } catch (Exception e) {
                    //出现异常时继续向后执行其他文件申请，此处打印警告日志
                    logger.warn("执行文件{}下载申请失败，批次号{}，需要申请{}条，已完成{}条，当前失败数据{}, 失败原因：{}", dtlFileInfDO.getFileName(),
                            batchId, dtlFileInfDOList.size(), count, dtlFileInfDO, e.getMessage(), e);
                }
            }
            //如果有对账文件下载申请失败，抛出异常，结束任务
            if (dtlFileInfDOList.size() != count) {
                logger.warn("执行文件下载申请失败，批次号{}，需要申请{}条，已完成{}条", batchId, dtlFileInfDOList.size(), count);
                throw new SettleTaskException(SettleTaskErrorEnum.BC0110.getCode(), SettleTaskErrorEnum.BC0110.getDesc());
            }
        } else {
            logger.info("执行对账明细文件下载申请，批次号：{}，当前批次号没有需要下载申请的文件", batchId);
        }
    }
}
