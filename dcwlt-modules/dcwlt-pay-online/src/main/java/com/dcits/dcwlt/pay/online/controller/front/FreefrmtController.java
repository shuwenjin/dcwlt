package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt.FreeFrmtRspDTO;
import com.dcits.dcwlt.pay.online.flow.DcwltTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.FreeFrmt401STradeFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/freefrmt")
public class FreefrmtController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DcwltTransInTradeFlow ecnyTransInTradeFlow;


    /**
     * 发送自由格式
     * @param req
     * @return
     */
    @PreAuthorize(hasPermi = "pay-online:freefrmt:pymtFrdmFmtMsgSnd")
    @PostMapping("/pymtFrdmFmtMsgSnd")
    public AjaxResult pymtFrdmFmtMsgSnd(@RequestBody Map<String, String> req) {
        ECNYReqDTO<FreeFrmtReqDTO> ecnyReqDTO = ECNYReqDTO.getInstanceWithDefaultHead();

        FreeFrmtReqDTO freeFrmtReqDTO = new FreeFrmtReqDTO();
        freeFrmtReqDTO.setTlrNO(req.get("tlrNO"));
        freeFrmtReqDTO.setInstdDrctPty(req.get("instdDrctPty"));
        freeFrmtReqDTO.setMsgContext(req.get("msgContext"));
        ecnyReqDTO.setBody(freeFrmtReqDTO);

        ECNYRspDTO<FreeFrmtRspDTO> rspDTO = ecnyTransInTradeFlow.execute(ecnyReqDTO, FreeFrmt401STradeFlow.FREEFRMT_TRADE_FLOW);
        logger.info("body的返回信息{}",rspDTO.getHead());
        if ("000000".equals(rspDTO.getHead().getRetCode())) {
            return toAjax(1);
        } else {

            logger.error("响应失败原因：{}",rspDTO.getHead().getRetInfo());
            return AjaxResult.error("自由格式发送失败:"+rspDTO.getBody().getProcResult());
        }
    }
}
