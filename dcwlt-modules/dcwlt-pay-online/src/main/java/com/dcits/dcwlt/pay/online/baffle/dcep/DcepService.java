package com.dcits.dcwlt.pay.online.baffle.dcep;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.EcnyReSendApyReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.TxStsQryReqDTO;

public interface DcepService {

    JSONObject receive902From401(DCEPReqDTO<FreeFrmtDTO> dcepReqDTO);

    JSONObject receive902From933(DCEPReqDTO dcepReqDTO);

    JSONObject receive802From801(DCEPReqDTO<DsptReqDTO> dsptReqDTODCEPReqDTO);

    JSONObject receive920(DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO);

    JSONObject receive412(DCEPReqDTO<TxStsQryReqDTO> dcepReqDTO);
}
