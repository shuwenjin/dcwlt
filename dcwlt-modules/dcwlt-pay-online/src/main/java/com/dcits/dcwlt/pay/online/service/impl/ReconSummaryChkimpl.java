package com.dcits.dcwlt.pay.online.service.impl;
import com.dcits.dcwlt.pay.api.model.ReconSummaryChkDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IReconSummaryChService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weimeiyuan
 * @Time 2021/1/3 17:16
 * @Version 1.0
 * Description:
 */
@Service
public class ReconSummaryChkimpl implements IReconSummaryChService {
    //private static final String REPLACE_SQL = "reconsummarychkmapper.replaceReconSummaryChkDO";

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    @Override
    public int replaceReconSummaryChkDO(ReconSummaryChkDO reconSummaryChkDO) throws Exception {
        return reconsummaryChkMapper.insertReconSummaryChkDO(reconSummaryChkDO);
    }
}
