package com.dcits.dcwlt.pay.batch.service.impl;


import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.FileUtil;
import com.dcits.dcwlt.common.pay.util.ZipUtil;
import com.dcits.dcwlt.pay.api.model.CheckPathDetialDO;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDetailService;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import com.dcits.dcwlt.pay.batch.service.IDtlFileParseService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对账文件解析服务实现类
 *
 * @author majun
 * @date 2021/1/8
 */
@Service
public class DtlFileParseServiceImpl implements IDtlFileParseService {

    private static final Logger logger = LoggerFactory.getLogger(DtlFileParseServiceImpl.class);
    //对账明细文件分割后数字常量标识, 最后一位可能不存在
    //业务处理时间|报文编号|报文标识号|发起机构|付款机构编码|收款机构编码|货币代码|金额|业务状态|交易描述信息
    private static final int FILE_FORMAT_SPLIT_ARRAY_LENGTH = 9;

    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;
    @Autowired
    private ICheckPathDetailService checkPathDetailService;

    /**
     * 批量解析一个批次号中所有对账明细文件内容入库
     *
     * @param batchId
     * @throws Exception
     */
    @Override
    public void parse(String batchId) throws Exception{
        List<DtlFileInfDO> dtlFileInfDOS
                = dtlFileInfoBatchService.queryDtlFileInfoBatchIdStatus(batchId, DtlFileProcStatusEnum.MOVE.getCode());

        //定义变量标识是否存在解析失败的文件
        boolean hasParseFile = false;

        //判断指定批次是否存在已经下载文件的文件
        if (dtlFileInfDOS != null && !dtlFileInfDOS.isEmpty()) {
            logger.info("解析对账明细文件入库，批次号：{}，本次需要解析入库{}个文件", batchId, dtlFileInfDOS.size());
            //遍历所有需要解压的文件，并进行文件解析
            for (DtlFileInfDO dtlFileInfDO : dtlFileInfDOS) {
                logger.info("开始解析批次号:{} 中文件{}", batchId, dtlFileInfDO.getFileName());
                //拿到文件在磁盘上的路径
                String filePath = dtlFileInfDO.getLocalFilePath();

                //更换文件后缀，拿到解压后的文件名称
                //文件后缀为在，不直接使用字符替换，避免出现路径中间出现.zip
                String destPath = null;
                String extension = FilenameUtils.getExtension(filePath).toLowerCase();
                if (!"zip".equals(extension)) {
                    logger.error("对账明细文件格式不对，文件必须是.zip压缩文件");
                    throw new SettleTaskException(SettleTaskErrorEnum.BC0113.getCode(), SettleTaskErrorEnum.BC0113.getDesc());
                }

                //判断文件的完整性
                String endFile = dtlFileInfDO.getLocalFilePath() + ".end";
                if (!FileUtil.isFileExist(endFile)) {
                    logger.error("对账明细文件不完整，end文件不存在");
                    throw new SettleTaskException(SettleTaskErrorEnum.BC0116.getCode(), SettleTaskErrorEnum.BC0116.getDesc());
                }

                int indexZip = filePath.lastIndexOf(".zip");
                destPath = filePath.substring(0, indexZip) + ".txt";

                //判断文件是否存在，如果存在， 先删除目标文件
                File destFile = new File(destPath);
                if (!destFile.exists()) {
                    destFile.getParentFile().mkdirs();
                } else {
                    FileUtil.delete(destPath);
                }

                //异常包裹，当出现异常时，将文件状态置INIT,标识可能需要重新进行文件申请
                //并且在此处如果一个文件解析失败， 后续文件继续解析
                try {
                    //执行解压文件
                    ZipUtil.unZip(filePath, destFile.getParent());

                    //调用文件解析方法， 进行文件解析
                    parseFile(dtlFileInfDO, destPath);
                } catch (Exception e) {
                    hasParseFile = true;

                    //更新文件处理状态为INIT, 后续启动重新申请文件下载等任务需要依赖状态
                    dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.MOVE.getCode());
                    dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
                    dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());

                    dtlFileInfoBatchService.updateDtlFileInfo(dtlFileInfDO);
                    logger.warn("文件解析失败，批次号：{}，文件名{}， 原因：{}",
                            dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName(), e.getMessage(), e);
                }
            }
        } else {
            logger.info("解析对账明细文件入库，批次号：{}，没有需要解析的文件", batchId);
        }
        //如果存在文件解析失败，抛出异常
        if (hasParseFile) {
            throw new SettleTaskException(SettleTaskErrorEnum.BC0115.getCode(), SettleTaskErrorEnum.BC0115.getDesc());
        }
    }

    /**
     * @param dtlFileInfDO 对账明细文件对象
     * @param filePath     对账文件路径
     * @throws IOException
     */
    @Override
    public void parseFile(DtlFileInfDO dtlFileInfDO, String filePath) throws IOException {
        logger.info("开始解析文件批次号{}中的 {}文件", dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName());

        //int limit = Integer.parseInt(RtpUtil.getInstance().getProperty("db.batch.limit", "1000"));
        int limit = Integer.parseInt("1000");
        File file = new File(filePath);
        if (!file.exists()) {
            logger.warn("对账明细文件不存在，文件名称：{}", filePath);
            throw new SettleTaskException(SettleTaskErrorEnum.BC0117);
        }

        //开始解析文件
        List<CheckPathDetialDO> checkPathDetailDOS = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            //文件可能会很大，每次批量处理1000条入库
            String dateLine = null;
            //文件第一样为明细总数和明细总金额，需要抛弃
            boolean fisrtLine = true;
            //每次入库操作不超过1000条，用该变量做统计
            int count = 0;
            long fileDataCount = 0;
            while ((dateLine = reader.readLine()) != null) {
                //丢弃第一行
                if (fisrtLine) {
                    fisrtLine = false;
                    continue;
                }

                //文件结束<end>标识，结束循环
                if ("<end>".equals(dateLine)) {
                    break;
                }

                //处理行统计，日志体现
                fileDataCount++;

                //文件格式
                //业务处理时间|报文编号|报文标识号|发起机构|付款机构编码|收款机构编码|货币代码|金额|业务状态|交易描述信息
                String[] data = dateLine.split("\\|");
                //最后一位可能为空，使用split函数切割以后， 数字长度可能为9
                if (data.length < FILE_FORMAT_SPLIT_ARRAY_LENGTH) {
                    logger.error("文件格式不对，当前文件{}，当前处理行{}，{}当前拿到数据为{}，正确格式：业务处理时间|报文编号|报文标识号|发起机构|付款机构编码|收款机构编码|货币代码|金额|业务状态|交易描述信息",
                            dtlFileInfDO.getFileName(),
                            fileDataCount, dateLine);
                    throw new SettleTaskException(SettleTaskErrorEnum.BC0111);
                }

                //根据每行对账明细数据生成通道对账明细表对象
                CheckPathDetialDO checkPathDetialDO = createCheckPathDetialDo(data, dtlFileInfDO);
                checkPathDetailDOS.add(checkPathDetialDO);
                count++;
                //如果已经满(默认)1000， 可通过（db.batch.limit配置）条数据，进行一次查库
                if (count == limit) {
                    int replaceSum = checkPathDetailService.replaceCheckPathDtl(checkPathDetailDOS);
                    if (replaceSum != count) {
                        logger.error("批次号：{}，文件[{}]有数据入库失败", dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName());
                        throw new SettleTaskException(SettleTaskErrorEnum.BC0112);
                    }
                    logger.debug("批次号：{}，文件{}当前完成处理{}条数据", dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName(), fileDataCount);

                    //清空集合,准备下一次缓存入库操作
                    checkPathDetailDOS.clear();

                    //批次统计归零，一个批次最大如果可以1000条
                    count = 0;
                }
                logger.debug("解析文件{}，解析入库{}条", dtlFileInfDO.getFileName(), fileDataCount);
            }

            //避免最后一次统计无法入库
            if (!checkPathDetailDOS.isEmpty()) {
                int replaceSum = checkPathDetailService.replaceCheckPathDtl(checkPathDetailDOS);
                if (replaceSum != count) {
                    logger.error("批次号{}中文件{}有数据入库失败", dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName());
                    throw new SettleTaskException(SettleTaskErrorEnum.BC0112);
                }
                logger.debug("解析文件{}，解析入库{}条", dtlFileInfDO.getFileName(), fileDataCount);
                logger.info("批次号：{}，文件{}当前完成处理{}条数据", dtlFileInfDO.getBatchId(), dtlFileInfDO.getFileName(), fileDataCount);
            }

            //更新对账清单文件状态
            dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.SUCC.getCode());
            dtlFileInfoBatchService.updateDtlFileInfo(dtlFileInfDO);

            //删除解压文件
            FileUtil.delete(file);
        }
    }

    private CheckPathDetialDO createCheckPathDetialDo(String[] data, DtlFileInfDO dtlFileInfDO) {
        //0         | 1    | 2      | 3     | 4        |  5      |  6    | 7 | 8     |  9
        //业务处理时间|报文编号|报文标识号|发起机构|付款机构编码|收款机构编码|货币代码|金额|业务状态|交易描述信息
        CheckPathDetialDO checkPathDetialDO = new CheckPathDetialDO();
        String workDate = dtlFileInfDO.getBatchId();

        //取批次号中的日期作为业务日志 B202005251400 取中间日期
        workDate = workDate.substring(1, 9);
        checkPathDetialDO.setWorkdate(workDate); //业务日期	VARCHAR	8
        checkPathDetialDO.setMsgId(dtlFileInfDO.getMsgId());  //报文标识号	VARCHAR	35
        checkPathDetialDO.setBatchId(dtlFileInfDO.getBatchId()); //	交易批次号	VARCHAR	13
        checkPathDetialDO.setFileName(dtlFileInfDO.getFileName() + ".sec");  //	文件名	VARCHAR	64
        checkPathDetialDO.setDtlBizTime(data[0]); //业务处理时间
        checkPathDetialDO.setMsgType(data[1]); //	报文编号
        checkPathDetialDO.setDtlMsgId(data[2]); //	明细的报文标识号	VARCHAR	35
        checkPathDetialDO.setInstgDrctPty(data[3]);  //	发起机构	VARCHAR	14
        checkPathDetialDO.setDBITParty(data[4]);  //付款机构编码	VARCHAR	14
        checkPathDetialDO.setCRDTParty(data[5]);//	收款机构编码	VARCHAR	14
        checkPathDetialDO.setCcy(data[6]); //	货币代码	VARCHAR	3
        //存库前需要将人行元单位转成分单位
        checkPathDetialDO.setAmount(AmountUtil.yuanToFen(data[7])); //	金额	VARCHAR	19
        checkPathDetialDO.setDtlBizStatus(data[8]); //	业务状态	VARCHAR	4
        //最后一位可能不存在
        if (data.length > 9) {
            checkPathDetialDO.setDtlDesc(data[9]); //	交易描述信息	VARCHAR	384
        }
        checkPathDetialDO.setLastUpDate(DateUtil.getDefaultDate());
        checkPathDetialDO.setLastUpTime(DateUtil.getDefaultTime());

        return checkPathDetialDO;
    }

    @Override
    public boolean parseSuccess(String batchId) {
        List<DtlFileInfDO> dtlFileInfDOS = dtlFileInfoBatchService.queryDtlFileInfoByBatchId(batchId);

        boolean processSuccess = true;
        for (DtlFileInfDO dtlFileInfDO : dtlFileInfDOS) {
            if (!DtlFileProcStatusEnum.SUCC.getCode().equals(dtlFileInfDO.getFileProcStatus())) {
                processSuccess = false;
                break;
            }
        }
        return processSuccess;
    }
}