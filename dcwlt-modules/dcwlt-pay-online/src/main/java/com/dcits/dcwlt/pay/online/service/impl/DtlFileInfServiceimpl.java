package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IDtlFileInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author weimeiyuan
 * @version 1.0.0
 * Description:
 * @Date 2021/1/5 15:15
 */
@Repository
public class DtlFileInfServiceimpl implements IDtlFileInfService {
    //private static final String REPLACE_SQL = "dtlfileinfmapper.replaceDtlFileInfDO";

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    @Override
    public int replaceDtlFileInfDO(DtlFileInfDO dtlFileInfDO) throws Exception {
        return reconsummaryChkMapper.insertDtlFileInfDO(dtlFileInfDO);
    }
}
