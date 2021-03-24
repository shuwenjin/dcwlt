package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.SummaryInfoDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.ISummaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @Author weimeiyuan
 * @Date 2021/1/6 22:41
 * @Version 1.0
 * Description: 汇总信息分类及业务清单信息接口
 */
@Repository
public class SummaryInfoServiceImpl implements ISummaryInfoService {
    //private static final String REPLACE_SQL = "summaryinfomapper.replaceSummaryInfoDO";

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    /**
     * 先检查是否有相同组件，有则删除再插入，否则插入
     * @param summaryInfoDO
     * @return
     * @throws Exception
     */
    @Override
    public int replaceSummaryInfoDO(SummaryInfoDO summaryInfoDO) throws Exception {
        return reconsummaryChkMapper.update(summaryInfoDO);
    }
}
