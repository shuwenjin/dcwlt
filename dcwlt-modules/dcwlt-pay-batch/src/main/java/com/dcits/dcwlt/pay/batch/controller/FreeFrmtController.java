package com.dcits.dcwlt.pay.batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自由格式Controller
 *
 * @author 
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/freefrmt")
public class FreeFrmtController extends BaseController
{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IPayPayTransdtlNonfService payPayTransdtlNonfService;

    /**
     * 自由格式查询
     */
  //  @PreAuthorize(hasPermi = "pay-batch:freefrmt:list")
    @GetMapping("/list")
    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        startPage();
        payPayTransdtlNonf.setPkgNo(MsgTpEnum.DCEP401.getCode());
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        return getDataTable(list);
    }

    /**
     * 导出非金融登记簿列表
     */
    @PreAuthorize(hasPermi = "pay-batch:freefrmt:export")
    @Log(title = "非金融登记簿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTransDtlNonfDO payPayTransdtlNonf) throws IOException
    {
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
        util.exportExcel(response, list, "freefrmt");
    }

    /**
     * 获取自由格式详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:freefrmt:query")
    @GetMapping(value = "/{msgid}")
    public AjaxResult getInfo(@PathVariable("msgid") String msgid)
    {
        return AjaxResult.success(payPayTransdtlNonfService.selectPayPayTransdtlNonfById(msgid));
    }


    /**
     * 发送自由格式的详细信息
     * @return
     */
    @PostMapping(value = "/sendFreeFrmt")
    public AjaxResult getInfo(@RequestBody  PayTransDtlNonfDO payTransDtlNonfDO){
        JSONObject jsonObject = this.freeFmt("11", payTransDtlNonfDO.getMessageContext(), payTransDtlNonfDO.getInstdDrctPty());
        String url="http://localhost:9301/dcwlt/pymtFrdmFmtMsgSnd";
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, jsonObject, String.class);
        boolean flag = this.checkResult(stringResponseEntity);
        if (flag) {
            return AjaxResult.success("发送自由格式成功");
        }else {
            return AjaxResult.error("发送自由格式失败");
        }
    }

    /**
     * {
     * 	"ecnyHead": {
     * 		"busiChnl": "1111",
     * 		"busiChnl2": "2222",
     * 		"zoneno": "01",
     * 		"brno": "01234",
     * 		"tellerno": "111",
     * 		"origChnl": "11",
     * 		"origChnl2": "22",
     * 		"origChnlDtl": "2"
     *        },
     * 	"body": {
     * 		"opterationType": "OT01",
     * 		"tlrNo": "11"
     *    },
     *       "head": {
     *     "tranDate": "20210317",
     *     "tranTime": "103524",
     *     "seqNo": "20210113000122532910308590900000"
     *   }
     * }
     * @return
     */
    protected JSONObject freeFmt(String trlNo,String msgContext,String instdDrctPty){
        Map<String,String> head=new HashMap<>();
        head.put("tranDate","20210317");
        head.put("tranTime","103524");
        head.put("seqNo","20210113000122532910308590900000");

        Map<String,String> body=new HashMap<>();
        body.put("tlrNo",trlNo);
        body.put("msgContext",msgContext);
        //
        body.put("instdDrctPty",instdDrctPty);

        Map<String,String> ecnyHead=new HashMap<>();
        ecnyHead.put("busiChnl","1111");
        ecnyHead.put("busiChnl2","2222");
        ecnyHead.put("zoneno","01");
        ecnyHead.put("brno","01234");
        ecnyHead.put("tellerno","1234");
        ecnyHead.put("origChnl","11");
        ecnyHead.put("origChnl2","22");
        ecnyHead.put("origChnlDtl","2");

        JSONObject json=new JSONObject();
        json.put("head",head);
        json.put("body",body);
        json.put("ecnyHead",ecnyHead);
        return  json;
    }


    /**
     * {"head":{"retCode":"000000","retInfo":"交易成功","tranDate":"20210413","tranTime":"154350","seqNo":"123"},
     "ecnyRspHead":{"trxStatus":"1"},"body":{"procResult":"登录/退出成功。"}}
     **/
    protected   boolean checkResult(ResponseEntity<String> stringResponseEntity){
        String body = stringResponseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject head = jsonObject.getJSONObject("head");
        String retCode = head.getString("retCode");
        if (retCode.equals("000000")){
            return true;
        }else {
            return false;
        }
    }


}
