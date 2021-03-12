package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.domain.dcep.check.CheckWrongQueryResDTO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 19:06
 */

@Mapper
public interface SettleCheckResultMapper {
    /**
     * 插入数据
     * @return
     */
    public int insert(CheckResultDO checkResultDO);

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    public int insertByBatch(List<CheckResultDO> list);

    /**
     * 查询数据
     * @param payDate
     * @param paySerno
     * @return
     */
    public CheckResultDO selectCheckResult(String payDate, String paySerno);
    /**
     * 查询需要处理的对账结果表
     * @param batchId
     * @return
     */
    public List<CheckResultDO> selectCheckResultByStatus(String payDate, String batchId);

    /**
     * 对账不平记录查询
     * @param payDate --平台日期, 必输
     * @param batchId --交易批次号, 可选
     * @param msgType --报文编号, 可选
     * @param checkStatus --对账标识, 可选
     * @param procStatus --差错处理状态, 可选
     * @param msgId --报文标识号, 可选 翻页时必填
     * @param count --查询条数, 必输
     * @param queryPageFlag --上下翻页操作, 必输, 0首页; 1上翻; 2下翻 只按升序排序
     * @return
     */
    public List<CheckWrongQueryResDTO> selectWrongMatchCheckResult(String payDate, String batchId, String msgType,
                                                                   String checkStatus, String procStatus, String msgId, int count, int queryPageFlag, String queryPageKey);


    /**
     * 对账状态更新
     * @param checkResultDO
     * @return
     */
    public int updateCheckResult(CheckResultDO checkResultDO);
}
