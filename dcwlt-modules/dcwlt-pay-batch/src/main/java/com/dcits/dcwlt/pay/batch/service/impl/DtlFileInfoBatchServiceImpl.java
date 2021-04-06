package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.mapper.SettledtlFileInfoBatchMapper;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 批量对账文件组件
 *
 * @author 
 * @date 2021/1/7
 */
@Service
public class DtlFileInfoBatchServiceImpl implements IDtlFileInfoBatchService {

    @Autowired
    private SettledtlFileInfoBatchMapper settledtlFileInfoBatchMapper;

//    private static final String QUERY_URL = "dtlfile_batch.queryDtlFileInfoByBatchId";
//    private static final String REPLACE_URL = "dtlfile_batch.replaceDtlFileInfo";
//    private static final String UPDATE_URL = "dtlfile_batch.updateDtlFileInfo";
//    private static final String UPDATE_PROCESS_URL = "dtlfile_batch.updateDtlFileInfoProcessStatus";
//    private static final String QUERY_STATUS_URL = "dtlfile_batch.queryDtlFileInfoByBatchIdStatus";
//    private static final String QUERY_UN_STATUS_URL = "dtlfile_batch.queryDtlFileInfoByBatchIdUnStatus";
//    private static final String QUERY_DELETE_URL = "dtlfile_batch.deleteByBatchId";
//    private static final String QUERY_FILENAME_URL = "dtlfile_batch.queryByFileName";

    @Override
    public int replaceDtlFileInfo(List<DtlFileInfDO> dtlFileInfDOS) {
        return settledtlFileInfoBatchMapper.replaceDtlFileInfo(dtlFileInfDOS);
    }

    @Override
    public int updateDtlFileInfo(DtlFileInfDO dtlFileInfDO) {
        return settledtlFileInfoBatchMapper.updateDtlFileInfo(dtlFileInfDO);
    }

    @Override
    public int updateDtlFileProcessStatusInfo(DtlFileInfDO dtlFileInfDO) {
        return settledtlFileInfoBatchMapper.updateDtlFileInfoProcessStatus(dtlFileInfDO);
    }

    @Override
    public int updateDtlFileProcessStatusInfoWhenApply(DtlFileInfDO dtlFileInfDO) {
        //TransactionStatus status = null;
        int successNum = 0;
        try {
            //status = DBUtil.beginTx(false);//开启一个事务
            DtlFileInfDO old = settledtlFileInfoBatchMapper.queryByFileName(dtlFileInfDO.getFileName());
            if (old != null && DtlFileProcStatusEnum.INIT.getCode().equals(old.getFileProcStatus()) ||
                    DtlFileProcStatusEnum.APLY.getCode().equals(old.getFileProcStatus())) {
                return settledtlFileInfoBatchMapper.updateDtlFileInfoProcessStatus(dtlFileInfDO);
            }
//            DBUtil.commit(status);
        } catch (Exception e) {
//            if (status != null) {
//                DBUtil.rollback(status);
//            }
        }
        return successNum;
    }

    @Override
    public List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId) {
        return settledtlFileInfoBatchMapper.queryDtlFileInfoByBatchId(batchId);
    }

    @Override
    public List<DtlFileInfDO> queryDtlFileInfoBatchIdStatus(String batchId, String processStatus) {
        return settledtlFileInfoBatchMapper.queryDtlFileInfoByBatchIdStatus(batchId,processStatus);
    }

    @Override
    public List<DtlFileInfDO> queryDtlFileInfoBatchIdUnStatus(String batchId, String processStatus) {
        return settledtlFileInfoBatchMapper.queryDtlFileInfoByBatchIdUnStatus(batchId,processStatus);
    }

    @Override
    public int deleteByBatchId(String batchId) {
        return settledtlFileInfoBatchMapper.deleteByBatchId(batchId);
    }

    @Override
    public int updateDtlFileInfoLastProcessStatus(DtlFileInfDO dtlFileInfDO) {
        return settledtlFileInfoBatchMapper.updateDtlFileInfoLastProcessStatus(dtlFileInfDO);
    }
}
