package com.dcits.dcwlt.pay.batch.mapper;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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


     List<DtlFileInfDO> queryDtlFileInfoByBatchId(@Param("batchId")String batchId);


     List<DtlFileInfDO> queryDtlFileInfoByBatchIdStatus(@Param("batchId")String batchId, @Param("fileProcStatus")String processStatus);


     List<DtlFileInfDO> queryDtlFileInfoByBatchIdUnStatus(@Param("batchId")String batchId, @Param("fileProcStatus")String processStatus);


     int deleteByBatchId(@Param("batchId")String batchId);

     DtlFileInfDO queryByFileName(@Param("fileName")String fileName);

     int updateDtlFileInfoLastProcessStatus(DtlFileInfDO dtlFileInfDO);
}
