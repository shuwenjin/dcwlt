package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.domain.dcep.login.LoginInnerReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.login.LoginInnerRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.online.flow.EcnyTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.Login933STradeFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginLogoutController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EcnyTransInTradeFlow ecnyTransInTradeFlow;


    /**
     * 登陆登出操作
     */
    @PreAuthorize(hasPermi = "pay-online:login:loginoutFmtMsgSnd")
    @PostMapping("/loginoutFmtMsgSnd")
    public AjaxResult loginoutFmtMsgSnd(@RequestBody Map<String, String> req) {
        ECNYReqDTO<LoginInnerReqDTO> ecnyReqDTO = ECNYReqDTO.getInstanceWithDefaultHead();

        LoginInnerReqDTO loginInnerReqDTO = new LoginInnerReqDTO();
        loginInnerReqDTO.setOpterationType(req.get("opterationType"));
        loginInnerReqDTO.setTlrNo(ecnyReqDTO.getEcnyHead().getTellerno());
        ecnyReqDTO.setBody(loginInnerReqDTO);

        ECNYRspDTO<LoginInnerRspDTO> rspDTO = ecnyTransInTradeFlow.execute(ecnyReqDTO, Login933STradeFlow.LOGIN_TRADE_FLOW);

        logger.info("body的返回信息{}",rspDTO.getHead());
        if ("000000".equals(rspDTO.getHead().getRetCode())) {
            return toAjax(1);
        } else {

            logger.error("响应失败原因：{}",rspDTO.getHead().getRetInfo());
            return AjaxResult.error("登陆登出失败");
        }
    }
}
