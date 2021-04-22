package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.domain.Head;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.ReSendApyRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqHead;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.online.flow.EcnyTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.send.ReSendApy920STradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 交易重发申请Controller
 *
 * @author dcwlt
 * @date 2021-04-21
 */
@RestController
@RequestMapping("/ReSendApy")
public class ReSendApyController extends BaseController {

//    @Autowired
//    private IPayPayTransdtlNonfService payPayTransdtlNonfService;

    @Autowired
    private EcnyTransInTradeFlow ecnyTransInTradeFlow;

    /**
     * 查询交易重发申请列表
     */
//    @PreAuthorize(hasPermi = "pay-batch:ReSendApy:list")
//    @GetMapping("/list")
//    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf) {
//        startPage();
//        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出交易重发申请列表
//     */
//    @PreAuthorize(hasPermi = "pay-batch:ReSendApy:export")
//    @Log(title = "交易重发申请", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, PayTransDtlNonfDO payPayTransdtlNonf) throws IOException {
//        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
//        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
//        util.exportExcel(response, list, "ReSendApy");
//    }


    /**
     * 新增交易重发申请
     */
    @PreAuthorize(hasPermi = "pay-online:ReSendApy:resend")
    @Log(title = "交易重发申请", businessType = BusinessType.INSERT)
    @PostMapping("/resend")
    public AjaxResult add(@RequestBody Map<String, String> req) {
        ECNYReqDTO<ReSendApyReqDTO> ecnyReqDTO = ECNYReqDTO.getInstanceWithDefaultHead();

        ReSendApyReqDTO reSendApyReqDTO = new ReSendApyReqDTO();
        reSendApyReqDTO.setBatchId(req.get("batchId"));
        reSendApyReqDTO.setMsgType(MsgTpEnum.DCEP711.getCode());
        ecnyReqDTO.setBody(reSendApyReqDTO);

        ECNYRspDTO<ReSendApyRspDTO> rspDTO = ecnyTransInTradeFlow.execute(ecnyReqDTO, ReSendApy920STradeFlow.RESEND_APPLY_TRADE_FLOW);

        if (ReSendApy920STradeFlow.RES_STS_SUCCESS.equals(rspDTO.getBody().getPrcSts())) {
            return toAjax(1);
        } else {
            return AjaxResult.error(rspDTO.getBody().getPrcDesc());
        }

    }
}
