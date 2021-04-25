package com.dcits.dcwlt.pay.online.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.online.flow.EcnyTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.Dispute801STradeFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/checkpath")
public class CheckPathController extends BaseController{

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EcnyTransInTradeFlow ecnyTransInTradeFlow;


    /**
     *当前业务，只满足调账业务，才会执行，其余都会返回成功
     * @param req
     * @return
     */
    @PostMapping(value = "changeAccount")
    public AjaxResult changeAccount(@RequestBody JSONObject req) {

        ECNYReqDTO<DsptChnlReqDTO> ecnyReqDTO = ECNYReqDTO.getInstanceWithDefaultHead();

        DsptChnlReqDTO dsptChnlReqDTO = JSON.toJavaObject(req, DsptChnlReqDTO.class);
        ecnyReqDTO.setBody(dsptChnlReqDTO);

        //DisputeReasonCode=="OT04"
        String disputeReasonCode = req.getString("disputeReasonCode");
        if (Objects.nonNull(disputeReasonCode) && "OT04".equals(disputeReasonCode)) {
            ECNYRspDTO rspDTO = ecnyTransInTradeFlow.execute(ecnyReqDTO, Dispute801STradeFlow.DSPT_TRADE_FLOW);

            logger.info("rspDTO===>{}",rspDTO);
            String retCode = rspDTO.getHead().getRetCode();
            if ("000000".equals(retCode)) {
                return toAjax(1);
            } else {
                return AjaxResult.error("调账失败");
            }
        } else {
            return toAjax(1);
        }
    }
}
