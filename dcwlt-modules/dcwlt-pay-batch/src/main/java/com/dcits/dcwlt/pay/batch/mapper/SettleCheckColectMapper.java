package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.CheckCollectDO;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 15:36
 */

 @Mapper
 public interface SettleCheckColectMapper {

     int insert(CheckCollectDO checkCollectDO);

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
     int insertByBatch(List<CheckCollectDO> list);

     List<CheckCollectDO> selectCheckCollect(@Param("batchId")String batchId);

     List<CheckPathDO> selectCollectSumByMsgType(@Param("batchId")String batchId);

     List<CheckPathDO> selectCollectSumTotal(@Param("batchId")String batchId);

    /**
     * 查询金融交易登记表当前批次的数据数量
     *
     * @param batchId
     * @return
     */
     int selectPayTranstionDetailCount(@Param("batchId")String batchId);

    /**
     * 查询金融交易登记表当前批次的数据
     *
     * @param datasource
     * @param batchId
     * @param beginNum   给定值时，使用批量查询方式，如果不赋值则查询所有
     * @param rowCount
     * @return
     */
     //List<CheckCollectDO> selectPayTranstionDetails( String batchId, Long beginNum, Long rowCount);

    /**
     * 将查询金融交易登记表数据导入文件
     *
     * @param batchId
     * @param fileName
     * @return
     */
    List<CheckCollectDO> intoFilePayTranstionDetails(@Param("batchId")String batchId, @Param("fileName")String fileName, @Param("startNum")Integer startNum, @Param("limit")Integer limit);


    /**
     * 查询通道对账明细表中对账异常的数据总数
     *
     * @param
     * @param batchId
     * @return
     */
     int selectPathDetailForNotMatchCount(@Param("batchId")String batchId);

    /**
     * 查询通道对账明细表中对账异常的数据
     *
     * @param batchId
     * @return
     */
     List<CheckResultDO> selectPathDetailForNotMatch(@Param("batchId")String batchId, @Param("offset")Integer offset, @Param("limit")Integer limit);

    /**
     * 查询我行有，人行没有的交易数据总数
     *
     * @param
     * @param batchId
     * @return
     */
     int selectCollectWithPbocNotFoundCount(@Param("batchId")String batchId);

     List<CheckResultDO> selectCollectWithPbocNotFound(@Param("batchId")String batchId, @Param("offset")Integer offset, @Param("limit")Integer limit);

    /**
     * 查询金融交易登记表当前批次的数据
     *
     * @param batchId
     * @param msgIdList
     * @return
     */
     List<CheckCollectDO> selectPayTranstionDetailsByMsgId(@Param("batchId") String batchId, @Param("list") List<String> msgIdList);

     int replaceIntoBatch(List<CheckCollectDO> list);
    /**
     * 查询通道对账明细表中对账结果数据
     *
     * @param
     * @param batchId
     * @return
     */
     List<CheckResultDO> selectPathDetailByMsgIdList(@Param("batchId")String batchId, @Param("list")List<String> msgIdList);

}
