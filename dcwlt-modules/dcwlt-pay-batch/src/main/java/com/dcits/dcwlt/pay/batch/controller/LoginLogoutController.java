package com.dcits.dcwlt.pay.batch.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.log.enums.OperatorType;
import com.dcits.dcwlt.common.pay.enums.LoginOperationTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.service.IPayPayTransdtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import org.springframework.web.client.RestTemplate;

/**
 * 登录登出Controller
 *
 * @author 
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/login")
public class LoginLogoutController extends BaseController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IPayPayTransdtlNonfService payPayTransdtlNonfService;

    /**
     * 登录登出信息查询
     */
    @PreAuthorize(hasPermi = "pay-batch:login:list")
    @GetMapping("/list")
    public TableDataInfo list(PayTransDtlNonfDO payPayTransdtlNonf)
    {
        startPage();
        payPayTransdtlNonf.setPkgNo(MsgTpEnum.DCEP933.getCode());
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payPayTransdtlNonf);
        return getDataTable(list);
    }

    /**
     * 导出非金融登记簿列表
     */
    @PreAuthorize(hasPermi = "pay-batch:login:export")
    @Log(title = "非金融登记簿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTransDtlNonfDO payTransDtlNonfDO) throws IOException
    {
        List<PayTransDtlNonfDO> list = payPayTransdtlNonfService.selectPayPayTransdtlNonfList(payTransDtlNonfDO);
        ExcelUtil<PayTransDtlNonfDO> util = new ExcelUtil<PayTransDtlNonfDO>(PayTransDtlNonfDO.class);
        util.exportExcel(response, list, "login");
    }

    /**
     * 获取非金融登记簿详细信息
     */
    @PreAuthorize(hasPermi = "pay-batch:login:query")
    @GetMapping(value = "/{msgid}")
    public AjaxResult getInfo(@PathVariable("msgid") String msgid)
    {
        return AjaxResult.success(payPayTransdtlNonfService.selectPayPayTransdtlNonfById(msgid));
    }

    /**
     * 登入操作
     * @param msgid
     * @return
     */
 //   @PreAuthorize(hasPermi = "pay-batch:login:query")
    @GetMapping(value = "/login/{msgid}")
    public AjaxResult login(@PathVariable("msgid") String msgid){
        String code = LoginOperationTpCdEnum.OT00.getCode();
        JSONObject sendInfo = getSendInfo(msgid, "11", code);
        String url="http://localhost:9301/dcwlt/loginoutFmtMsgSnd";
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, sendInfo, String.class);
        boolean flag = this.checkResult(stringResponseEntity);
        if (flag) {
            return AjaxResult.success("登录成功");
        }else {
            return AjaxResult.error("登录失败");
        }
    }

    /**
     * 登出操作
     * @param msgid
     * @return
     */
    @GetMapping(value = "/loginout/{msgid}")
    public AjaxResult loginout(@PathVariable("msgid") String msgid){
        String code = LoginOperationTpCdEnum.OT01.getCode();
        JSONObject sendInfo = getSendInfo(msgid, "11", code);
        String url="http://localhost:9301/dcwlt/loginoutFmtMsgSnd";
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, sendInfo, String.class);
        boolean flag = this.checkResult(stringResponseEntity);
        if (flag) {
            return AjaxResult.success("登出成功");
        }else {
            return AjaxResult.error("登出失败");
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
    protected JSONObject getSendInfo(String msgId,String trlNo,String opterationType){
        Map<String,String> head=new HashMap<>();
        head.put("tranDate","20210317");
        head.put("tranTime","103524");
        head.put("seqNo",msgId);

        Map<String,String> body=new HashMap<>();
        body.put("tlrNo",trlNo);
        body.put("opterationType",opterationType);

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
