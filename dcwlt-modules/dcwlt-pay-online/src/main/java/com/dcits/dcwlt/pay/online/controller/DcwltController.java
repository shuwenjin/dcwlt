package com.dcits.dcwlt.pay.online.controller;

import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.pay.api.domain.Head;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.EcnyFreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.login.LoginInnerReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlRspDTO;
import com.dcits.dcwlt.pay.online.flow.DcwltTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 供行内系统调用入口
 */
@RestController
@RequestMapping("/dcwlt")
public class DcwltController {

    @Autowired
    private DcwltTransInTradeFlow ecnyTransInTradeFlow;

    @PostMapping(value = ApiConstant.FREEFRMT_SERVICE_NAME)
    public ECNYRspDTO<FreeFrmtRspDTO> freeFrmts(@RequestBody ECNYReqDTO<EcnyFreeFrmtReqDTO> ecnyFreeFrmtReqDTO) {
        return ecnyTransInTradeFlow.execute(ecnyFreeFrmtReqDTO, FreeFrmt401STradeFlow.FREEFRMT_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.RESENDAPY_SERVICE_NAME)
    public ECNYRspDTO<ReSendApyRspDTO> reSendApy(@RequestBody ECNYReqDTO<ReSendApyReqDTO> reSendApyReqDTO) {
        return ecnyTransInTradeFlow.execute(reSendApyReqDTO, ReSendApy920STradeFlow.RESEND_APPLY_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.DSPT_SERVICE_NAME)
    public ECNYRspDTO<DsptChnlRspDTO> dspt(@RequestBody ECNYReqDTO<DsptChnlReqDTO> dsptChnlReqDTOECNYReqDTO) {
        return ecnyTransInTradeFlow.execute(dsptChnlReqDTOECNYReqDTO, Dispute801STradeFlow.DSPT_TRADE_FLOW);
    }

    @PostMapping(value = ApiConstant.LOGINOUT_SERVICE_NAME)
    public ECNYRspDTO<FreeFrmtRspDTO> loginFrmts(@RequestBody ECNYReqDTO<LoginInnerReqDTO> ecnyLoginReqDTO) {
        return ecnyTransInTradeFlow.execute(ecnyLoginReqDTO, Login933STradeFlow.LOGIN_TRADE_FLOW);
    }


    @PostMapping(value = ApiConstant.PAYCONVERT_SERVICE_NAME)
    public ECNYRspDTO<PayConvertChnlRspDTO> payConvert(@RequestBody ECNYReqDTO<PayConvertChnlReqDTO> payConvertChnlReqDTO) {
        return ecnyTransInTradeFlow.execute(payConvertChnlReqDTO, PayConvert227STradeFlow.PAY_CONVERT_TRADE_FLOW);
    }




    /**
     *当前业务，只满足调账业务，才会执行，其余都会返回成功
     * @param dsptChnlReqDTOECNYReqDTO
     * @return
     */
    @PostMapping(value = "changeAccount")
    public ECNYRspDTO<DsptChnlRspDTO> changeAccount(@RequestBody ECNYReqDTO<DsptChnlReqDTO> dsptChnlReqDTOECNYReqDTO) {

       //DisputeReasonCode=="OT04"
        String disputeReasonCode = dsptChnlReqDTOECNYReqDTO.getBody().getDisputeReasonCode();
        if (Objects.nonNull(disputeReasonCode)&&disputeReasonCode.equals("OT04")){
            return ecnyTransInTradeFlow.execute(dsptChnlReqDTOECNYReqDTO, Dispute801STradeFlow.DSPT_TRADE_FLOW);
        }else{
            return   success();
        }
    }


    /**
     * m默认返回成功
     * @return
     */
    private ECNYRspDTO<DsptChnlRspDTO> success(){
        ECNYRspDTO ecnyRspDTO = new ECNYRspDTO<>();

        DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
        rspDTO.setProcResult("交易成功");
        ecnyRspDTO.setBody(rspDTO);

        Head head=new Head();
        head.setRetCode("000000");
        head.setRetInfo("交易成功");
        head.setSeqNo("20210113000122532910308590900000");
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        head.setTranDate(sdf.format(date));
        head.setTranTime("15764");
        ecnyRspDTO.setHead(head);


        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        ecnyRspHead.setTrxStatus("0");

        ecnyRspDTO.setEcnyRspHead(ecnyRspHead);

       return  ecnyRspDTO;
    }


}
