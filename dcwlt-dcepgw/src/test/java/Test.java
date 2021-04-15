import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.JsonXmlUtil;

public class Test {

    @org.junit.Test
    public void t3() throws Exception {
        String json= "{\"Header\":{\"BeginFlag\":\"{H:\",\"VersionID\":\"04\",\"OrigSender\":\"G4001011000013\",\"OrigSenderSID\":\"DCPS\",\"OrigReceiver\":\"C1091231000013\",\"OrigReceiverSID\":\"DCPS\",\"OrigSendDate\":\"20201030\",\"OrigSendTime\":\"094508\",\"StructType\":\"XML\",\"MesgType\":\"dcep.221.001.01\",\"MesgID\":\"202011171021221000000000001120010001\",\"MesgRefID\":\"202011171021221000000000001120010001\",\"MesgPriority\":\"3\",\"MesgDirection\":\"U\",\"SderReserved\":\"\",\"RcverReserved\":\"\",\"CenterReserved\":\"\",\"Reserve\":\"\",\"EndFlag\":\"}\"},\"Body\":{\"ReconvertReq\":{\"TrxInf\":{\"TrxBizTp\":\"C201\",\"BatchId\":\"B202101131600\",\"TrxAmt\":{\"Ccy\":\"CNY\",\"value\":\"11.00\"},\"TrxCtgyPurpCd\":\"03011\"},\"GrpHdr\":{\"CreDtTm\":\"2021-01-13T16:49:07\",\"InstdPty\":{\"InstdDrctPty\":\"C1030644021075\"},\"InstgPty\":{\"InstgDrctPty\":\"C1010411000013\"},\"MsgId\":\"20210113000122184595346246598765\"},\"DbtrInf\":{\"DbtrWltId\":\"101112345678916\",\"DbtrWltTp\":\"WT01\",\"DbtrWltNm\":\"测试钱包\",\"DbtrPtyId\":\"C1010411000013\",\"DbtrNm\":\"测试1\",\"DbtrWltLvl\":\"WL01\"},\"CdtrInf\":{\"CdtrAcctTp\":\"AT00\",\"CdtrNm\":\"造数泰伯\",\"CdtrPtyId\":\"C1030644021075\",\"CdtrAcct\":\"6214622121002964305\"}}}}\n";

        String mix = DcspMsgUtil.pack(json);
        System.out.println(mix);

        JSONObject jsonObject = DcspMsgUtil.unPack(mix);
        System.out.println(jsonObject.toJSONString());
    }
    @org.junit.Test
    public void t2(){
        byte[] sm4Key = new SM4().getSecretKey().getEncoded();
        System.out.println(sm4Key.length);
        String s1= new SM4(sm4Key).encryptBase64("hugh");
        System.out.println(s1);
        String s2= new SM4(sm4Key).decryptStr(s1);
        System.out.println(s2);
    }

    @org.junit.Test
    public void tt() throws Exception {

        SM2 sm2 = SmUtil.sm2();
        String es = sm2.encryptBase64("hugh", KeyType.PublicKey);
        System.out.println(sm2.signHex(SmUtil.sm3(es)));
        System.out.println(es);
        System.out.println(sm2.decryptStr(es, KeyType.PrivateKey));

        String s = FileUtil.readString("/Volumes/Work/ECNY/文档整理/城银清/城银清报文结构.txt", "utf-8");
        String json = DcspMsgUtil.unPack(s).toJSONString();
        System.out.println(json);

        String xml = JsonXmlUtil.jsonToDcspXml(json);
        System.out.println(">>>>>>.xml:"+xml);
        String soap = JsonXmlUtil.jsonToSoap(json);
        System.out.println(">>>>>>.soap:"+soap);
        //        System.out.println(s);
        //头
        String head = s.substring(0,202);
        System.out.println("head：["+head+"]");
        //head定长拆解
        //3 |2 |14 |4 |14 |4 |8 |6 |3 |20 |40 |40 |1 |1 |10 |10 |10 |9 |3
//        System.out.println(DcspMsgUtil.unPackFixHead(head));
        String sub = s.substring(202);
//        System.out.println("["+sub+"]");
        int bodyStartIdx = 0;
        if(sub.startsWith("{S:")){
            bodyStartIdx = sub.indexOf("}")+3;
            //签名域
            String sgn = sub.substring(0,bodyStartIdx);
            System.out.println("sgn:["+sgn+"]");
            //签名域tag 拆解
            boolean ssnFlag = false;
            if(sgn.contains(":SSN:")){
                //有签名证书序列号，必然有 :DSG:
                System.out.println("SignSN:"+StrUtil.subBetween(sgn, ":SSN:", ":"));
                System.out.println("DigitalSignature:"+StrUtil.subBetween(sgn, ":DSG:", "}"));
                ssnFlag = true;
            }
            if(sgn.contains(":NSN:")){
                //有加密证书序列号，必然有 :DEP:
                System.out.println("NcrptnSN:"+StrUtil.subBetween(sgn, ":NSN:", ":"));
                if(ssnFlag) {
                    System.out.println("DgtlEnvlp:" + StrUtil.subBetween(sgn, ":DEP:", ":"));
                }else{
                    System.out.println("DgtlEnvlp:" + StrUtil.subBetween(sgn, ":DEP:", "}"));
                }
            }

        }
        //报文体
        String body = sub.substring(bodyStartIdx);
//        System.out.println("body:["+body+"]");

        String s1="{H:04G4001011000013DCPSC1091231000013DCPS20201030094508XMLdcep.221.001.01     202011171021221000000000001120010001    202011171021221000000000001120010001    3U                                       }";
//        String s2="{S::SSN:1036581724:NSN:1036593915:DEP:Qk8TENJZD4KCQkJPElz2+yn5V4KQLyvTrdAn3FaAiEA//6PcWN:DSG:MEUCIFECshV+4FuUS8VUDjfa/5rO2+yn5V4KQLyvTrdAn3FaAiEA//6PcWNh6SlTHTWekOcJkAvMVTmbm5JsGi5yMUtgQfA=}";
//        String s2="{S::SSN:1036581724:DSG:MEUCIFECshV+4FuUS8VUDjfa/5rO2+yn5V4KQLyvTrdAn3FaAiEA//6PcWNh6SlTHTWekOcJkAvMVTmbm5JsGi5yMUtgQfA=}";
//        String s2="{S:}";
        String s2="";
        String s3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Document xmlns=\"urn:cbcc:std:dcep:2020:tech:xsd:dcep.221.001.01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<ReconvertReq xmlns:wstxns1=\"http://www.dcep.com/dcep/22100101/\">\n" +
                "            <GrpHdr>\n" +
                "                <!--报文标识号-->\n" +
                "                <MsgId>20210113000122184595346246598765</MsgId>\n" +
                "                <!--报文发送时间-->\n" +
                "                <CreDtTm>2021-01-13T16:49:07</CreDtTm>\n" +
                "                <InstgPty>\n" +
                "                    <!--发起运营机构-->\n" +
                "                    <InstgDrctPty>C1010411000013</InstgDrctPty>\n" +
                "                </InstgPty>\n" +
                "                <InstdPty>\n" +
                "                    <!--接收运营机构-->\n" +
                "                    <InstdDrctPty>C1030644021075</InstdDrctPty>\n" +
                "                </InstdPty>\n" +
                "                <!--备注-->\n" +
                "\n" +
                "            </GrpHdr>\n" +
                "            <TrxInf>\n" +
                "                <TrxBizTp>C201</TrxBizTp>  <!--业务类型编码-->\n" +
                "                <TrxCtgyPurpCd>03011</TrxCtgyPurpCd>  <!--业务种类编码-->\n" +
                "                <TrxAmt Ccy=\"CNY\">11.00</TrxAmt>    <!--交易金额-->\n" +
                "                <BatchId>B202101131600</BatchId>    <!--交易批次号-->\n" +
                "            </TrxInf>\n" +
                "            <DbtrInf><!--兑回钱包信息--><!--兑回是指用户将钱包中的DC/EP兑换为银行存款。-->\n" +
                "                <DbtrPtyId>C1010411000013</DbtrPtyId>   <!--付款人钱包所属运营机构-->\n" +
                "                <DbtrNm>测试1</DbtrNm>   <!--付款人名称-->\n" +
                "                <DbtrWltId>101112345678916</DbtrWltId>   <!--付款人钱包ID-->\n" +
                "                <DbtrWltLvl>WL01</DbtrWltLvl>   <!--付款人钱包等级-->\n" +
                "                <DbtrWltTp>WT01</DbtrWltTp>   <!--付款人钱包类型-->\n" +
                "                <DbtrWltNm>测试钱包</DbtrWltNm>   <!--付款人钱包名称-->\n" +
                "            </DbtrInf>\n" +
                "            <CdtrInf><!--收款人信息-->\n" +
                "                <CdtrPtyId>C1030644021075</CdtrPtyId>   <!--收款人账户所属运营机构-->\n" +
                "                <CdtrAcctTp>AT00</CdtrAcctTp>   <!--收款人账户类型-->\n" +
                "                <CdtrNm>造数泰伯</CdtrNm>   <!--收款人名称-->\n" +
                "                <CdtrAcct>6214622121002964305</CdtrAcct>   <!--收款人账户账号-->\n" +
                "            </CdtrInf>\n" +
                "        </ReconvertReq>\n" +
                "</Document>";
        String tt = s1+"\r\n"+s2+"\r\n"+s3;
        if(StrUtil.isBlank(s2)){
            tt = s1+"\r\n"+s3;
        }
        FileUtil.writeString(tt, "/Volumes/Work/ECNY/文档整理/城银清/城银清报文结构.txt", "utf-8");

    }
}
