package com.dcits.dcwlt.pay.online.baffle.dcep.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmtDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.EcnyReSendApyReqDTO;
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


    @Override
    public JSONObject receive902From933(DCEPReqDTO dcepReqDTO) {
        return JSONObject.parseObject( "{\n" +
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
                "        \"LoginRspn\": {\n" +
                "            \"GrpHdr\": {\n" +
                "                \"msgId\": \"123456789\",\n" +
                "                \"creDtTm\": \"20180126\",\n" +
                "                \"instgPty\": {\n" +
                "                    \"instgDrctPty\": \"12\"\n" +
                "                },\n" +
                "                \"instdPty\": {\n" +
                "                    \"instDrctPty\": \"111\"\n" +
                "                },\n" +
                "                \"rmk\": \"ha\"\n" +
                "            },\n" +
                "            \"OrgnlGrpInf\": {\n" +
                "                \"orgnlMsgId\": \"2013513\"\n" +
                "            },\n" +
                "            \"LoginRspnInf\": {\n" +
                "                \"loginOprTp\":  \"\",\n" +
                "                \"prcSts\": \"PR00\",\n" +
                "                \"prcCd\": \"123\",\n" +
                "                \"rjctInf\": \"123\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}"
        );
    }

    @Override
    public JSONObject receive802From801(DCEPReqDTO<DsptReqDTO> dsptReqDTODCEPReqDTO) {
        return JSONObject.parseObject("{\n" +
                "    \"ecnyHead\": {\n" +
                "        \"Sender\": \"C1030644021075\",\n" +
                "        \"SignSN\": \"01\",\n" +
                "        \"Ver\": \"01\",\n" +
                "        \"Receiver\": \"C1010311000014\",\n" +
                "        \"MsgSN\": \"20210113106040120333044574013001\",\n" +
                "        \"SndDtTm\": \"2021-01-13T20:37:31\",\n" +
                "        \"MsgTp\": \"dcep.802.001.01\"\n" +
                "    },\n" +
                "    \"body\": {\n" +
                "        \"DsptRsp\": {\n" +
                "            \"GrpHdr\": {\n" +
                "                \"CreDtTm\": \"2021-01-13T20:33:33\",\n" +
                "                \"InstdPty\": {\n" +
                "                    \"InstdDrctPty\": \"C1010311000014\"\n" +
                "                },\n" +
                "                \"InstgPty\": {\n" +
                "                    \"InstgDrctPty\": \"C1030644021075\"\n" +
                "                },\n" +
                "                \"MsgId\": \"20210113106040120333044574013001\"\n" +
                "            },\n" +
                "            \"OrgnlGrpHdr\": {\n" +
                "                \"orgnlMsgId\": \"11\",\n" +
                "                \"orgnlInstgPty\": \"111\",\n" +
                "                \"orgnlMT\": \"1111\"\n" +
                "            },\n" +
                "            \"RspsnInf\": {\n" +
                "                \"PrcSts\": \"11\",\n" +
                "                \"RspsnSts\": \"11\",\n" +
                "                \"RjctCd\": \"111\",\n" +
                "                \"RjctInf\": \"111\",\n" +
                "                \"BatchId\": \"111\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}");
    }


    @Override
    public JSONObject receive920(DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO) {
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
                "        \"MsgTp\": \"dcep.920.001.01\"\n" +
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
