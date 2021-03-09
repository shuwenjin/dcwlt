package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;

public interface IAuthInfoService {
    Boolean validateAuthInfo(String partyid, String msgType, String TradeCtgyCode, AuthInfoDrctEnum flag);
    int replaceAuthInfo(AuthrtyChngNtfctn authrtyChngNtfctn);
}
