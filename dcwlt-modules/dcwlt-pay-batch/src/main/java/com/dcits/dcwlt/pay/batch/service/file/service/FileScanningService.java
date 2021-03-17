package com.dcits.dcwlt.pay.batch.service.file.service;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.constant.FileConst;
import com.dcits.dcwlt.common.pay.constant.SchedulerResult;
import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.schedular.service.SchedulerBaseService;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.FileUtil;

import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.mapper.SettledtlFileInfoBatchMapper;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FileScanningService implements SchedulerBaseService {
    //private final static String UPDATE_DLT = "dtlfile_batch.updateDtlFileInfoLastProcessStatus";

    @Autowired
    private SettledtlFileInfoBatchMapper settledtlFileInfoBatchMapper;

    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;

    private int scanningStartus;//运行状态

    private static final Logger logger = LoggerFactory.getLogger(FileScanningService.class);

    private Long timeOutCurrentTime;

    private String scaningPath;
    private String saveBakPath;
    private String fromatFailPath;
    private String repeatFilePath;
    private String nowDate;


    @PostConstruct
    private void postFileConstruct() {
        timeOutCurrentTime = System.currentTimeMillis();
        setScanningStartus(FileConst.RUNNINGSTATRTUS_INIT);
    }

    /**
     * 运行扫描流程
     */
    public void runFlow(String batchId) throws Exception {
        //运行中检查 防止重复调用
        if (checkRuning()) {
            //init交易初始化
            try {
                //初始化参数 各参数重新获取 Ac单例需要自动刷新静态配置，否则不生效
                init();
                //扫描文件路径添加批次号中日期
                scaningPath = scaningPath + File.separator + batchId.substring(1, 9);
                //扫描文件夹所有待处理数据 获取所有带End的文件的主文件对象
                ArrayList<File> fileList = scaningFilePath(null, scaningPath);
                //fileInfoList过滤无效数据
                fileList = fileListFilter(fileList, batchId);
                if (fileList.isEmpty()) {
                    logger.info("扫描文件为空，或者改批次无需移动文件，无需处理");
                    return;
                }
                fileList.forEach(f -> {
                    logger.info("扫描文件列表：" + f.getPath() + File.separator + f.getName());
                });
                //移动文件并入库
                moveFileAndRegisInfo(fileList, batchId);
            } catch (Exception e) {
                logger.error("runFlowException:", e);
                throw e;
            } finally {
                setScanningStartus(FileConst.RUNNINGSTATRTUS_INIT);
            }
        }
    }

    /**
     * 检查是否可以运行 并抢占运行状态锁
     *
     * @return Boolean 是否运行后续运行
     */
    private Boolean checkRuning() {
        Long nowCurrentTime = System.currentTimeMillis();
        if (scanningStartus == FileConst.RUNNINGSTATRTUS_INIT || nowCurrentTime > timeOutCurrentTime) {
            synchronized (FileScanningService.class) {
                if (scanningStartus == FileConst.RUNNINGSTATRTUS_INIT || nowCurrentTime > timeOutCurrentTime) {
                    Long timeOut = 10 * 60 * 1000L;//十分钟超时
                    timeOutCurrentTime = System.currentTimeMillis() + timeOut;
                    setScanningStartus(FileConst.RUNNINGSTATRTUS_RUNNING);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 初始化
     */
    private void init() {
        nowDate = DateUtil.getSysDate();
        //scaningPath = RtpUtil.getInstance().getProperty(FileConst.CONFIG_PROPERTY_SCANINGPATH);
        //saveBakPath = RtpUtil.getInstance().getProperty(FileConst.CONFIG_PROPERTY_SAVEBAKPATH);
        //fromatFailPath = RtpUtil.getInstance().getProperty(FileConst.CONFIG_PROPERTY_FROMATFAILPATH);
        //repeatFilePath = RtpUtil.getInstance().getProperty(FileConst.CONFIG_PROPERTY_REPEATFILEPATH);
        scaningPath = FileConst.CONFIG_PROPERTY_SCANINGPATH;
        saveBakPath = FileConst.CONFIG_PROPERTY_SAVEBAKPATH;
        fromatFailPath = FileConst.CONFIG_PROPERTY_FROMATFAILPATH;
        repeatFilePath = FileConst.CONFIG_PROPERTY_REPEATFILEPATH;
    }

    /**
     * 扫描所有目录及子目录下的end文件
     *
     * @param fileList 文件列表（ 用递归算法）
     * @param nowurl   目前路径
     * @return ArrayList<ArrayList < String>> fileList
     * ArrayList<String> fileListElement
     * @throws Exception IOException
     */
    private ArrayList<File> scaningFilePath(ArrayList<File> fileList, String nowurl) throws Exception {
        if (fileList == null) {
            fileList = new ArrayList<File>();
        }
        File file = new File(nowurl);
        try {
            if (file.isDirectory()) {
                File[] subFileArr = file.listFiles();
                for (File subFile : subFileArr) {
                    scaningFilePath(fileList, subFile.getCanonicalPath());
                }
            }
            if (!file.isDirectory()) {
                //文件名
                String fileName = file.getName();
                logger.info("全部文件列表：" + fileName);
                if (fileName.endsWith(FileConst.FILE_TRAN_FINISH_ENDTAG)) {
                    File f = new File(file.getCanonicalPath().substring(0, file.getCanonicalPath().length() - FileConst.FILE_TRAN_FINISH_ENDTAG.length()));
                    fileList.add(f);
                }
            }
            return fileList;
        } catch (Exception e) {
            logger.error("excepiton:", e);
            throw e;
        }
    }

    /**
     * 过滤那些不在本批次内的文件
     *
     * @param fileList 待过滤文件列表
     * @return
     */
    private ArrayList<File> fileListFilter(ArrayList<File> fileList, String batchId) {
        ArrayList<File> filterAfterList = new ArrayList<>();
        List<DtlFileInfDO> dtlFileInfDOS = dtlFileInfoBatchService.queryDtlFileInfoByBatchId(batchId);
        Set<String> fileNames = new HashSet<>(dtlFileInfDOS.size());
        for (DtlFileInfDO dtlFileInfDO : dtlFileInfDOS) {
            fileNames.add(dtlFileInfDO.getFileName());
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            //排除不在本批次内的文件
            if (!fileNames.contains(file.getName())) {
                //如果不在本次移动， 那么暂时不管
                continue;
            }
            //判断.end文件是否存在，如果文件对应的end文件不存在，那么当前不能移动该文件
            File fileEnd = new File(file.getPath() + ".end");
            if (!fileEnd.exists()) {
                continue;
            }
            //其余加入返回队列
            filterAfterList.add(file);
        }
        return filterAfterList;
    }

    /**
     * 移动文件并登记数据库
     *
     * @param fileList
     */
    private void moveFileAndRegisInfo(ArrayList<File> fileList, String batchId) {
        for (File file : fileList) {
            File targetFile = null;
            File endFile = null;
            File endTargetFile = null;
            try {
                String targetPath = FileUtil.mergeFilePathName(saveBakPath, batchId) + File.separator;
                targetFile = getTargeFile(file, scaningPath, targetPath);
                logger.info("=========file:" + file.getPath());
                endFile = new File(file.getCanonicalPath() + FileConst.FILE_TRAN_FINISH_ENDTAG);
                endTargetFile = getTargeFile(endFile, scaningPath, targetPath);
                logger.info("=========endFile:" + endFile.getPath());
            } catch (IOException e) {
                logger.error("run fileListFilter Excepiton:", e);
            }
            if (targetFile == null || endTargetFile == null) {
                continue;
            }
            if (moveFile(file, targetFile)) {
                if (moveFile(endFile, endTargetFile)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("dealStep", FileConst.FILES_STEP_MOVE);
                    map.put("dealStatus", FileConst.FILES_STATUS_SUCC);
                    map.put("fileName", file.getName());

                    //更新对账文件批量库
                    DtlFileInfDO dtlFileInfDO = new DtlFileInfDO();
                    dtlFileInfDO.setFileName(file.getName());
                    dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.MOVE.getCode());
                    dtlFileInfDO.setLocalFilePath(targetFile.getPath());
                    dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());
                    dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
                    try {
                        //DBUtil.update(UPDATE_DLT, dtlFileInfDO);
                        settledtlFileInfoBatchMapper.updateDtlFileInfoLastProcessStatus(dtlFileInfDO);
                        dtlFileInfoBatchService.updateDtlFileInfoLastProcessStatus(dtlFileInfDO);
                    } catch (Exception e) {
                        logger.warn("文件移动入库记录失败，原因{}", e.getMessage());
                    }
                } else {
                    doExeception("移动end文件失败");
                }
            } else {
                doExeception("移动主文件失败");
            }
        }
    }


    /**
     * 移动文件到重复文件夹
     *
     * @param origFile
     * @return
     */
    private Boolean backupFileToRepeat(File origFile) {
        //根据传入文件名
        File targetFile = getTargeFile(origFile, scaningPath, repeatFilePath);
        if (targetFile == null) {
            logger.info("文件移动至重复文件夹失败 Mothod:backupFileToRepeat，文件名：%s", origFile.getName());
            return false;
        }
        return moveFile(origFile, targetFile);
    }

    /**
     * 移动文件到垃圾场
     *
     * @param origFile
     * @return
     */
    private Boolean backupFileToFormatFail(File origFile) {
        //根据传入文件名
        File targetFile = getTargeFile(origFile, scaningPath, fromatFailPath);
        if (targetFile == null) {
            logger.info("文件移动至垃圾文件夹失败 Mothod:backupFileToFormatFail，文件名：%s", origFile.getName());
            return false;
        }
        return moveFile(origFile, targetFile);
    }

    /**
     * 移动文件 返回true和false 后期使用异常捕获
     *
     * @param origFile
     * @param targetFile
     * @return
     */
    private Boolean moveFile(File origFile, File targetFile) {
        logger.info("origFile:" + origFile.getPath());
        logger.info("targetFile:" + targetFile.getPath());
        //如果目标文件已经存在， 删除目标文件
        if (targetFile.exists()) {
            targetFile.delete();
        }
        File targetDir = new File(targetFile.getParent());
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        if (origFile.renameTo(targetFile)) {
            logger.info("文件移动至文件夹成功，文件：" + origFile.getPath() + ",目标文件：" + targetFile.getPath());
            return true;
        } else {
            logger.error("文件移动至文件夹失败 Mothod:moveFile，文件：{}，目标文件：{}", origFile.getPath(), targetFile.getPath());
            return false;
        }
    }

    /**
     * 获取虚构目标File对象
     *
     * @param file     File对象
     * @param origPath 原目录（提供目录保留目录往下的文件夹结构，null值不保留结构直接移动）
     * @param newPath  新目录
     * @return 目标路径虚构File
     */
    private File getTargeFile(File file, String origPath, String newPath) {
        try {
            logger.info("origPath:" + origPath);
            logger.info("newPath:" + newPath);
            logger.info("file:" + file.getCanonicalPath());
            if (StringUtil.isBlank(origPath)) {
                origPath = file.getCanonicalPath().substring(0, file.getCanonicalPath().length());
            }
            return new File(file.getCanonicalPath().replaceAll(StringEscapeUtils.escapeJava(origPath), StringEscapeUtils.escapeJava((newPath))));
        } catch (IOException e) {
            return null;
        }
    }


    public void setScanningStartus(int scanningStartus) {
        scanningStartus = scanningStartus;
    }

    public void doExeception(String e) {
        logger.info("==doExeception" + e);
        throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
    }

    @Override
    public String dealTask(Map<String, String> param) throws Exception {
        String batchId = param.get("batchId");
        runFlow(batchId);
        return SchedulerResult.succMsg("处理成功");
    }
}
