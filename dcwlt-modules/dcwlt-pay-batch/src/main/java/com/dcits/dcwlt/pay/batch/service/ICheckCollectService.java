package com.dcits.dcwlt.pay.batch.service;



import com.dcits.dcwlt.pay.api.model.CheckCollectDO;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;

import java.util.List;

public interface ICheckCollectService {

    /**
     * 插入数据
     *
     * @param checkCollectDO
     * @return
     */
    public int insert(CheckCollectDO checkCollectDO);

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    public int insertByBatch(List<CheckCollectDO> list);

    /**
     * 文件导入
     *
     * @param fileName
     * @return
     */
    public void loadFileByBatch(String fileName);

    /**
     * 将数据从联机库导入文件
     *
     
     * @param batchId
     * @param fileName
     * @param startNum
     * @param limit
     * @return
     */
    public int intoFilePayTranstionDetails( String batchId, String fileName, Integer startNum, Integer limit);

    /**
     * 查询数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckCollectDO> selectCheckCollect(String batchId);

    /**
     * 分组汇总查询数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckPathDO> selectCollectSumByMsgType(String batchId);

    /**
     * 分组汇总查询数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckPathDO> selectCollectSumTotal(String batchId);

    /**
     * 查询金融交易登记表当前批次的数据数量
     *
     
     * @param batchId
     * @return
     */
    public int selectPayTranstionDetailCount( String batchId);


    /**
     * 查询我行有，人行没有的交易数据总数
     *
     * @param
     * @param batchId
     * @return
     */
    public int selectCollectWithPbocNotFoundCount(String batchId);

    /**
     * 查询我行有，人行没有的交易数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckResultDO> selectCollectWithPbocNotFound(String batchId, Integer offset, Integer limit);

    /**
     * 查询通道对账明细表中对账异常的数据总数
     *
     * @param
     * @param batchId
     * @return
     */
    public int selectPathDetailForNotMatchCount(String batchId);

    /**
     * 查询通道对账明细表中对账异常的数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckResultDO> selectPathDetailForNotMatch(String batchId, Integer offset, Integer limit);

    /**
     * 查询金融交易登记表当前批次的数据
     *
     
     * @param batchId
     * @param msgIdList
     * @return
     */
    public List<CheckCollectDO> selectPayTranstionDetailsByMsgId( String batchId, List<String> msgIdList);

    public int replaceIntoBatch(List<CheckCollectDO> list);

    /**
     * 查询通道对账明细表中对账结果数据
     *
     * @param
     * @param batchId
     * @return
     */
    public List<CheckResultDO> selectPathDetailByMsgIdList(String batchId, List<String> msgIdList);
}
