package com.dcits.dcwlt.pay.batch.service.impl;


import com.dcits.dcwlt.pay.api.model.CheckCollectDO;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleCheckColectMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账明细采集表
 */
@Service
public class CheckCollectServiceImpl implements ICheckCollectService {

    @Autowired
    private SettleCheckColectMapper settleCheckColectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "settleCheckColectMapper.insert";
    private static final String SELECT_SQL = "settleCheckColectMapper.selectCheckCollect";
    private static final String INTO_FILE = "settleCheckColectMapper.intoFilePayTranstionDetails";

    @Override
    public int insert(CheckCollectDO checkCollectDO) {
        return settleCheckColectMapper.insert(checkCollectDO);
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    @Override
    public int insertByBatch(List<CheckCollectDO> list) {
        return settleCheckColectMapper.insertByBatch(list);
    }

    /**
     * 文件导入
     *
     * @param
     * @return
     */
    @Override
    public void loadFileByBatch(String fileName) {
        String sql = "load data infile '" + fileName + "' into table pay_batch_checkcollect\r\n" +
                "        (PAYDATE, PAYSERNO, PAYTIME, COREACCTDATE, MSGTYPE, MSGID, BATCHID, PAYFLAG, INSTGDRCTPTY, DBITPARTY,\r\n" +
                "        PAYERWALLETID,\r\n" +
                "        PAYERACCOUNT, CRDTPARTY, PAYEENAME, PAYEEACCOUNT, PAYEEWALLETID, CCY, AMOUNT, OGNLMSGTYPE, OGNLMSGID,\r\n" +
                "        TRADESTATUS, CORESTATUS, PATHSTATUS, LASTUPDATE, LASTUPTIME)";
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<CheckCollectDO> selectCheckCollect(String batchId) {
        return settleCheckColectMapper.selectCheckCollect(batchId);
    }

    @Override
    public List<CheckPathDO> selectCollectSumByMsgType(String batchId) {
        return settleCheckColectMapper.selectCollectSumByMsgType(batchId);
    }

    @Override
    public List<CheckPathDO> selectCollectSumTotal(String batchId) {
        return settleCheckColectMapper.selectCollectSumTotal( batchId);
    }

    /**
     * 查询金融交易登记表当前批次的数据数量
     *
    
     * @param batchId
     * @return
     */
    @Override
    public int selectPayTranstionDetailCount(String batchId) {
        return settleCheckColectMapper.selectPayTranstionDetailCount(batchId);
    }

    /**
     * 查询金融交易登记表当前批次的数据
     *
    
     * @param batchId
     * @param beginNum   给定值时，使用批量查询方式，如果不赋值则查询所有
     * @param rowCount
     * @return
     */
//    public List<CheckCollectDO> selectPayTranstionDetails(String batchId, Long beginNum, Long rowCount) {
//        Map<String, Object> map = null;
//        if (beginNum != null && rowCount != null) {
//            map = ImmutableMap.of("batchId", batchId, "beginNum", beginNum, "rowCount", rowCount);
//        } else {
//            map = ImmutableMap.of("batchId", batchId);
//        }
//        return DBUtil.selectList(datasource, "settleCheckColectMapper.selectPayTranstionDetails", map);
//    }

    /**
     * 将查询金融交易登记表数据导入文件
     *
     * @param batchId
     * @param fileName
     * @return
     */
    @Override
    public int intoFilePayTranstionDetails(String batchId, String fileName, Integer startNum, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (batchId != null) {
            map.put("batchId", batchId);
        }
        if (fileName != null) {
            map.put("fileName", fileName);
        }
        if (startNum != null) {
            map.put("startNum", startNum);
        }
        if (limit != null) {
            map.put("limit", limit);
        }
        List<CheckCollectDO> checkCollectDOS = settleCheckColectMapper.intoFilePayTranstionDetails(batchId, fileName, startNum, limit);
        int check = 0;
        if (!checkCollectDOS.isEmpty()) {
            check = 1;
        }
        return check;
    }


    /**
     * 查询通道对账明细表中对账异常的数据总数
     *
     * @param
     * @param batchId
     * @return
     */
    @Override
    public int selectPathDetailForNotMatchCount(String batchId) {
        return settleCheckColectMapper.selectPathDetailForNotMatchCount(batchId);
    }

    /**
     * 查询通道对账明细表中对账异常的数据
     *
     * @param batchId
     * @return
     */
    @Override
    public List<CheckResultDO> selectPathDetailForNotMatch(String batchId, Integer offset, Integer limit) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("batchId", batchId);
//        if (offset != null) {
//            map.put("offset", offset);
//        }
//        if (limit != null) {
//            map.put("limit", limit);
//        }
        return settleCheckColectMapper.selectPathDetailForNotMatch(batchId,offset,limit);
    }

    /**
     * 查询我行有，人行没有的交易数据总数
     *
     * @param
     * @param batchId
     * @return
     */
    @Override
    public int selectCollectWithPbocNotFoundCount(String batchId) {
        return settleCheckColectMapper.selectCollectWithPbocNotFoundCount( batchId);
    }

    @Override
    public List<CheckResultDO> selectCollectWithPbocNotFound(String batchId, Integer offset, Integer limit) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("batchId", batchId);
//        if (offset != null) {
//            map.put("offset", offset);
//        }
//        if (limit != null) {
//            map.put("limit", limit);
//        }
        return settleCheckColectMapper.selectCollectWithPbocNotFound(batchId,offset,limit);
    }

    /**
     * 查询金融交易登记表当前批次的数据
     *
    
     * @param batchId
     * @param msgIdList
     * @return
     */
    @Override
    public List<CheckCollectDO> selectPayTranstionDetailsByMsgId(String batchId, List<String> msgIdList) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("batchId", batchId);
//        map.put("list", msgIdList);
        return settleCheckColectMapper.selectPayTranstionDetailsByMsgId( batchId,msgIdList);
    }

    @Override
    public int replaceIntoBatch(List<CheckCollectDO> list) {
        return settleCheckColectMapper.replaceIntoBatch(list);
    }

    /**
     * 查询通道对账明细表中对账结果数据
     *
     * @param
     * @param batchId
     * @return
     */
    @Override
    public List<CheckResultDO> selectPathDetailByMsgIdList(String batchId, List<String> msgIdList) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("batchId", batchId);
//        map.put("list", msgIdList);
        return settleCheckColectMapper.selectPathDetailByMsgIdList(batchId,msgIdList);
    }
}
