package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 从联机库导数据到批量库（金融登记簿抽数）
 */
@Service
public class ImportTradeDataFileTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(ImportTradeDataFileTask.class);
    //sql导出文件路径
    @Value("{ecny.sql.load.path}")
    private String fileRootPath;
    @Autowired
    private ICheckCollectService checkCollectService;

    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //检查抽取交易数据是否完成
//        rowCount = checkCollectRepository.selectPayTranstionDetailCount(AppConstant.DATASOURCE_ONLIE, taskExec.getBatchId());
    }

    /**
     * 执行抽数任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行抽数任务，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        // 1.组装文件名
        String batchId = taskExec.getBatchId();
        //获取配置中心的文件生成目录
        String filePath = fileRootPath;
        String fileNamePrefix = filePath + "/" + batchId + "_" + DateUtil.formatSeconds();
        //  2.每次只能导出10万条数据
        //String limitCount = RtpUtil.getInstance().getProperty("ecny.check.collect.max.count", "100000");
        String limitCount = "100000";
        //对账时SQL每次更新的数据大小
        int maxCount = Integer.parseInt(limitCount);
        try {
            // 3.查询总条数
            int totalCount = checkCollectService.selectPayTranstionDetailCount(taskExec.getBatchId());
            logger.info("每次写文件的最大条数:{}，查询到数据库当前批次的总数：{}", limitCount, totalCount);
             //  4. 如果总条数大于最大数，则按批次号导入
            if (totalCount > maxCount) {
                 // 5.处理数据分文件
                checkLength(batchId, fileNamePrefix, totalCount, maxCount);
            } else {
                // 6.否则按批次从联机库直接导入文件
            	String fileName = fileNamePrefix + ".txt";
                checkCollectService.intoFilePayTranstionDetails(taskExec.getBatchId(), fileName, null, null);
                checkCollectService.loadFileByBatch(fileName);
            }
            logger.info("联机库导报文文件数据到批量库任务数据导入成功,导入成功文件个数：{}", totalCount);
        } catch (Exception e) {
            logger.error("执行从联机库导报文文件数据到批量库任务失败,对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId(), e);
            throw new SettleTaskException(SettleTaskErrorEnum.BC0114.getCode(), SettleTaskErrorEnum.BC0114.getDesc());
        }
    }

    /**
     * 每次只能导出10万条数据，超过则分文件导出
     */
    private void checkLength(String batchId, String fileNamePrefix, int totalCount, int maxCount) {
        //总分批数据
        int loopCount = 0;
        //开始导入编号
        int startNum = 0;
        //分批次提交更新
        loopCount = totalCount / maxCount;
        if (totalCount % maxCount > 0) {
            loopCount = loopCount + 1;
        }
        //获取配置中心的文件生成目录
        String fileName = null;
        for (int i = 0; i < loopCount; i++) {
            logger.info("共分成：{}批次，当前第{}次", loopCount, i);
            //1.组装文件名
            fileName = fileNamePrefix + "_" + i + ".txt";
            // 计算开始数
            startNum = i * maxCount;
            // 2.将数据从联机库导入文件
            checkCollectService.intoFilePayTranstionDetails(batchId, fileName, startNum, maxCount);
            // 3.将文件中的数据导入批量库
            checkCollectService.loadFileByBatch(fileName);
            //4.是否导入成功
            logger.info("数据从文件 {} 导入批量库成功。", fileName);
        }
    }
}
