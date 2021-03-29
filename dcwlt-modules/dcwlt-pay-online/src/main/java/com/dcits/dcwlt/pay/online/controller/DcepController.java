package com.dcits.dcwlt.pay.online.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供互联互通调用入口
 */
@RestController
@RequestMapping("/dcep")
public class DcepController {
    @Autowired
    private DcepTransInTradeFlow dcepTransInTradeFlow;

    @PostMapping(value = ApiConstant.DCEP_SERVICE_NAME,produces = {"application/json;charset=UTF-8"})
    public JSONObject list(@RequestBody JSONObject reqMsg) {
        return dcepTransInTradeFlow.execute(reqMsg);
    }

    public static void main(String[] args){
        JSONObject string = JSONObject.parseObject("{\n" +
                "    \"Header\": {\n" +
                "        \"Sender\": \"G4001011000013\", \n" +
                "        \"Ver\": \"01\", \n" +
                "        \"SignSN\": \"4\", \n" +
                "        \"Receiver\": \"C1030644021075\", \n" +
                "        \"SndDtTm\": \"2021-01-13T17:55:29\", \n" +
                "        \"MsgSN\": \"202101130001915202815629446000000001\", \n" +
                "        \"MsgTp\": \"dcep.915.001.01\"\n" +
                "    }, \n" +
                "    \"Body\": {\n" +
                "        \"AuthrtyChngNtfctn\": {\n" +
                "            \"AuthrtyInf\": [\n" +
                "                {\n" +
                "                    \"FctvDt\": \"2021-01-13T17:55:29\", \n" +
                "                    \"FctvTp\": \"EF00\", \n" +
                "                    \"ChngTp\": \"CC01\"\n" +
                "                }, \n" +
                "                \"C1030644021075\", \n" +
                "                {\n" +
                "                    \"MT\": \"dcep.401.001.01\", \n" +
                "                    \"TrdCtgyCd\": \"11\", \n" +
                "                    \"InitAuthrtySgnCd\": \"AS00\", \n" +
                "                    \"RcvAuthrtySgnCd\": \"AS00\"\n" +
                "                }\n" +
                "            ], \n" +
                "            \"GrpHdr\": {\n" +
                "                \"Rmk\": \"rmk\", \n" +
                "                \"CreDtTm\": \"2021-01-13T17:55:29\", \n" +
                "                \"InstdPty\": {\n" +
                "                    \"InstdDrctPty\": \"C1030644021075\"\n" +
                "                }, \n" +
                "                \"InstgPty\": {\n" +
                "                    \"InstgDrctPty\": \"G4001011000013\"\n" +
                "                }, \n" +
                "                \"MsgId\": \"20210113000191520281562944600000\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}");

        JSONObject jsonObject = string.getJSONObject(AppConstant.DCEP_BODY).getJSONObject("AuthrtyChngNtfctn");

        AuthrtyChngNtfctn body = (AuthrtyChngNtfctn) JSONObject.toJavaObject(
                jsonObject, AuthrtyChngNtfctn.class);
        System.out.println(body.toString());

    }

}
