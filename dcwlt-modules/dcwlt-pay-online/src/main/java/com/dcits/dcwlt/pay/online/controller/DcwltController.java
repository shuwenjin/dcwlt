package com.dcits.dcwlt.pay.online.controller;

import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.check.SummaryQueryInputDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.check.SummaryQueryOutputDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.EcnyFreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.login.LoginInnerReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.payconvert.PayConvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.payconvert.PayConvertRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.ReconSummaryChkDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlRspDTO;
import com.dcits.dcwlt.pay.online.flow.EcnyTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.receive.ReconSummaryChk711TradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.*;
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

    @PostMapping(value = ApiConstant.DSPT_SERVICE_NAME)
    public ECNYRspDTO<SummaryQueryOutputDTO> dspt(@RequestBody ECNYReqDTO<SummaryQueryInputDTO> summaryQueryInputDTO) {
        return ecnyTransInTradeFlow.execute(summaryQueryInputDTO, Dispute801STradeFlow.DSPT_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.LOGINOUT_SERVICE_NAME)
    public ECNYRspDTO<FreeFrmtRspDTO> loginFrmts(@RequestBody ECNYReqDTO<LoginInnerReqDTO> ecnyLoginReqDTO) {
        return ecnyTransInTradeFlow.execute(ecnyLoginReqDTO, Login933STradeFlow.LOGIN_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.PAYCONVERT_SERVICE_NAME)
    public ECNYRspDTO<PayConvertChnlRspDTO> payConvert(@RequestBody ECNYReqDTO<PayConvertChnlReqDTO> payConvertChnlReqDTO) {
        return ecnyTransInTradeFlow.execute(payConvertChnlReqDTO, PayConvert227STradeFlow.PAY_CONVERT_TRADE_FLOW);
    }

}
