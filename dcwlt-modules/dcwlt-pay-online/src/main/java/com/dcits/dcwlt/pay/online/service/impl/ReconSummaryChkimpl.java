package com.dcits.dcwlt.pay.online.service.impl;
import com.dcits.dcwlt.pay.api.model.ReconSummaryChkDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IReconSummaryChkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author weimeiyuan
 * @Time 2021/1/3 17:16
 * @Version 1.0
 * Description:
 */
@Repository
public class ReconSummaryChkimpl implements IReconSummaryChkRepository {
    private static final String REPLACE_SQL = "reconsummarychkmapper.replaceReconSummaryChkDO";

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    @Override
    public int replaceReconSummaryChkDO(ReconSummaryChkDO reconSummaryChkDO) throws Exception {
        return reconsummaryChkMapper.insertReconSummaryChkDO(REPLACE_SQL, reconSummaryChkDO);
    }
}
