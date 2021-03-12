package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery.TxDtlQryRspDTO;

import javax.validation.Valid;

/**
 * 银行回查平台-交易行明细查询
 */
public interface ITxDtlQryNetPartyService {
    /**
     * 银行回查平台-交易状态查询
     *
     * @param orgnlGrpHdr 原交易主键组件
     * @return TxDtlQryRspDTO 交易明细报文体
     */
    TxDtlQryRspDTO txDtlQryNetParty(@Valid OrgnlGrpHdr orgnlGrpHdr);
}
