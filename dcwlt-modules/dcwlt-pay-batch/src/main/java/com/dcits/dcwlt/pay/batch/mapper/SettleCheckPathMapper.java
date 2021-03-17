package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/12 11:25
 */

@Mapper
public interface SettleCheckPathMapper {

    public int insert(CheckPathDO checkPathDO);

    public List<CheckPathDO> selectPath(@Param("batchDate") String batchDate, @Param("batchId")String batchId);

    public int updateStatus(@Param("payDate") String payDate, @Param("paySerno") String paySerno, @Param("checkStatus") String checkStatus, @Param("lastUpDate") String lastUpDate, @Param("lastUpTime") String lastUpTime);

    /**
     * 批量插入数据
     * @return
     */
    public int insertByBatch(List<CheckPathDO> list);

    /**
     * 在联机库中查询通道对账汇总数据
     * @param batchDate
     * @param batchId
     * @return
     */
    public List<CheckPathDO> selectPathInOnline(//String datasource,
                                                @Param("batchDate") String batchDate, @Param("batchId") String batchId);


}
