package com.dcits.dcwlt.pay.online.baffle.dcep;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;

public interface DcepService {

    JSONObject receive902From401(DCEPReqDTO<FreeFrmtDTO> dcepReqDTO);

    JSONObject receive902From933(DCEPReqDTO dcepReqDTO);
}
