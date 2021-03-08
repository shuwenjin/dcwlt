package com.dcits.dcwlt.pay.online.controller;

import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.EcnyFreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.online.flow.EcnyTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.FreeFrmt401STradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.ReSendApy920STradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供行内系统调用入口
 */
@RestController
@RequestMapping("/dcwlt")
public class DcwltController {

    @Autowired
    private EcnyTransInTradeFlow ecnyTransInTradeFlow;

    @PostMapping(value = ApiConstant.FREEFRMT_SERVICE_NAME)
    public ECNYRspDTO<FreeFrmtRspDTO> freeFrmts(@RequestBody ECNYReqDTO<EcnyFreeFrmtReqDTO> ecnyFreeFrmtReqDTO) {
        return ecnyTransInTradeFlow.execute(ecnyFreeFrmtReqDTO, FreeFrmt401STradeFlow.FREEFRMT_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.RESENDAPY_SERVICE_NAME)
    public ECNYRspDTO<ReSendApyRspDTO> reSendApy(@RequestBody ECNYReqDTO<ReSendApyReqDTO> reSendApyReqDTO) {
        return ecnyTransInTradeFlow.execute(reSendApyReqDTO, ReSendApy920STradeFlow.RESEND_APPLY_TRADE_FLOW);
    }
}
