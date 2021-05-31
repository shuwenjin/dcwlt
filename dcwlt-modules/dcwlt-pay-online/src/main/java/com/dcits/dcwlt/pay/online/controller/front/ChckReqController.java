package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.online.flow.DcwltTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.Chck991STradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.FreeFrmt401STradeFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chckreq")
public class ChckReqController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DcwltTransInTradeFlow ecnyTransInTradeFlow;


    /**
     * HTTP通讯级探测
     * @return
     */
    @PreAuthorize(hasPermi = "pay-online:chckreq:check")
    @GetMapping("/check")
    public AjaxResult chckreq() {

        ECNYRspDTO<ECNYRspBody> rspDTO = ecnyTransInTradeFlow.execute(new ECNYReqDTO(), Chck991STradeFlow.CHCK_TRADE_FLOW);
        logger.info("返回信息{}",rspDTO);
        return AjaxResult.success(rspDTO.getEcnyRspHead().getTrxStatus());
    }
}
