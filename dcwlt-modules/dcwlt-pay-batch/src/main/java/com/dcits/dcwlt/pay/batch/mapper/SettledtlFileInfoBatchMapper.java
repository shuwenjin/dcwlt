package com.dcits.dcwlt.pay.batch.mapper;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 11:16
 */

@Mapper
public interface SettledtlFileInfoBatchMapper {


     int replaceDtlFileInfo(List<DtlFileInfDO> dtlFileInfDOS);


     int updateDtlFileInfo(DtlFileInfDO dtlFileInfDO);


     int updateDtlFileInfoProcessStatus(DtlFileInfDO dtlFileInfDO);


     List<DtlFileInfDO> queryDtlFileInfoByBatchId(String batchId);


     List<DtlFileInfDO> queryDtlFileInfoByBatchIdStatus(String batchId, String processStatus);


     List<DtlFileInfDO> queryDtlFileInfoByBatchIdUnStatus(String batchId,String processStatus);


     int deleteByBatchId(String batchId);

     DtlFileInfDO queryByFileName(String fileName);

     int updateDtlFileInfoLastProcessStatus(DtlFileInfDO dtlFileInfDO);
}
