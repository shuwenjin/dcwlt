package com.dcits.dcwlt.pay.online.baffle.dcep.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import org.springframework.stereotype.Service;

/**
 * 请求互联互通
 */
@Service
public class DcepServiceImpl implements DcepService {

    /**
     * 互联互通发送401报文接收902报文
     * @param dcepReqDTO
     * @return
     */
    @Override
    public JSONObject receive902From401(DCEPReqDTO<FreeFrmtDTO> dcepReqDTO) {
        return JSONObject.parseObject("{\n" +
                "    \"ecnyHead\": {\n" +
                "        \"Sender\": \"C1010311000014\",\n" +
                "        \"DgtlEnvlp\": null,\n" +
                "        \"SignSN\": \"01\",\n" +
                "        \"Ver\": \"01\",\n" +
                "        \"NcrptnSN\": null,\n" +
                "        \"Receiver\": \"C1030644021075\",\n" +
                "        \"MsgSN\": \"20210113106040120333044574013001\",\n" +
                "        \"SndDtTm\": \"2021-03-08T14:18:20\",\n" +
                "        \"MsgTp\": \"dcep.902.001.01\"\n" +
                "    },\n" +
                "    \"body\": {\n" +
                "        \"ComConf\": {\n" +
                "            \"ConfInf\": {\n" +
                "                \"OrgnlMsgId\": \"20210113106040120333044574013001\",\n" +
                "                \"OrgnlInstgPty\": \"C1030644021075\",\n" +
                "                \"PrcSts\": \"PR00\",\n" +
                "                \"OrgnlMT\": \"dcep.401.001.01\",\n" +
                "                \"OrigSndDtTm\": \"2021-01-13T20:37:31\",\n" +
                "                \"Remark\": null\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "\t\"head\":{\"retCode\":\"000000\"}\n" +
                "}");
    }
}
