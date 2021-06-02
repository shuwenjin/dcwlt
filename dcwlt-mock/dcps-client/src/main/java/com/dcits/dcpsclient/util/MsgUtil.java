package com.dcits.dcpsclient.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xingjj
 * @Description TODO
 * @Date 2021/4/13 11:07
 * @Version 1.0
 */
public class MsgUtil {
    private final static Logger logger = LoggerFactory.getLogger(MsgUtil.class);
    //固定报文头
    public static String header = "{H:04G4001011000013DCPSC1091231000013DCPS%sXML%s     %s     %s     3U                                       }";


    //发送方公钥
    //public static String SendpublicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEdcjKVPXzo9pHK+tSgKRlLME8ViiaaLrOwt7LZ7hUHphx/q8fvGfy1nmbUIZlZJ++E4WKiqrYH457WyaObaG+WQ==";
    //发送方私钥
    //public static String SendprivateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg405/E9Kvb30cE4R0MNwnuiOGQA+J66zKlheVtIK9/+qgCgYIKoEcz1UBgi2hRANCAAR1yMpU9fOj2kcr61KApGUswTxWKJpous7C3stnuFQemHH+rx+8Z/LWeZtQhmVkn74ThYqKqtgfjntbJo5tob5Z";

    //接收方公钥
    //public static String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==";
    //接收方私钥
    //public static String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ";

    //接收方公钥
    public static String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==";
    //接收方私钥
    public static String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ";


    //明文密钥
    public static String digitalenvelope_plain = "F2D484F5D5E695A9FCB48B6064C8081C";

    //是否需要加密。默认需要加签
    public static boolean isGenerateDigitalSign = false;

    //生成数字信封
    public static String digitalenvelope_cipher() {
        byte[] data = SmUtil.sm2(privateKey, publicKey).encrypt(Convert.hexToBytes(digitalenvelope_plain), KeyType.PublicKey);
        return Base64.getEncoder().encodeToString(data);

    }

    //获取明文密钥
    public static byte[] getDigitalenvelope_plain(String digitalenvelope_cipher) {
        return SmUtil.sm2(privateKey, publicKey).decrypt(Base64.getDecoder().decode(digitalenvelope_cipher), KeyType.PrivateKey);
    }


    //生成数字签名内容
    public static String getDigitalSignature(String msg) {
        String digitalsign_cipher = "";
        try {
            byte[] digest = SmUtil.sm3().digest(msg.getBytes("UTF-8"));
            //byte[] data = SmUtil.sm2(privateKey, publicKey).sign(msg.getBytes("UTF-8"));
            byte[] data = SmUtil.sm2(privateKey, publicKey).sign(digest);
            digitalsign_cipher = Base64.getEncoder().encodeToString(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return digitalsign_cipher;

    }

    //校验数字签名内容
    public static boolean verifyDigitalSignature(String msg, String sign) {
        boolean isVerify = false;
        try {
            //byte[] digest = SmUtil.sm3().digest(msg.getBytes("UTF-8"));
            isVerify = SmUtil.sm2(privateKey, publicKey).verify(msg.getBytes("UTF-8"), Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVerify;

    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String getXmlKeyValue(String xml, String key) {
        //检查数据合法性
        if (xml == null) {

            return "";
        }

        //检查数据合法性
        if (key == null) {

            return "";
        }
        StringBuffer sb = new StringBuffer();
        String posKey = key;
        //定位查找字符串
        int len = xml.indexOf("<" + posKey + ">");
        if (len == -1) {

            return "";
        }
        int xmlLen = xml.length();
        //找到节点对应的结束">"
        int start = 0;
        for (int i = len + posKey.length(); i < xmlLen; i++) {
            char c = xml.charAt(i);
            if (c == '>') {
                start = i;
                break;
            }

        }

        //提取报文内容
        for (int i = start + 1; i < xmlLen; i++) {
            char c = xml.charAt(i);
            if (c != '<')
                sb.append(c);
            else
                break;
        }

        return sb.toString();
    }

    public static String updateXmlValue(String xml, String[] elements, byte[] dpKey, boolean isencrypt) {
        Map<String, String> values = new HashMap<>();
        try {
            for (String key : elements) {
                String value = getXmlKeyValue(xml, key);
                if (isencrypt) {
                    values.put(key, SmUtil.sm4(dpKey).encryptBase64(value));
                } else {
                    values.put(key, new String(SmUtil.sm4(dpKey).decrypt(Base64.getDecoder().decode(value)), "UTF-8"));
                }

            }
            for (String element : values.keySet()) {
                String value = values.get(element);
                int len = xml.indexOf("<" + element + ">");
                int len2 = xml.lastIndexOf("</" + element + ">");
                xml = xml.substring(0, len + element.length() + 2) + value + xml.substring(len2, xml.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    //加密报文体
    public static String encryptBody(String msgtype, String reqMsg) {
        String bodyMsg = "";
        byte[] dpKey = Convert.hexToBytes(digitalenvelope_plain);
        try {
            if ("dcep.221.001.01.xml".equals(msgtype)) {
                //DC/EP兑回业务请求报文
                //<DbtrNm>,<DbtrWltId>,<CdtrNm>,<CdtrAcct>需加密
                //加密处理并做Base64转码
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrWltId", "CdtrNm", "CdtrAcct"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.225.001.01.xml".equals(msgtype)) {
                //DC/EP兑出业务请求报文
                //<DbtrNm>,<DbtrAcct>,<CdtrNm>,<CdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.227.001.01.xml".equals(msgtype)) {
                //DC/EP汇款兑出请求报文
                //<DbtrNm>,<DbtrAcct>,<CdtrNm>,<CdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.411.001.01.xml".equals(msgtype)) {
                //交易状态查询请求报文
                //<OrgnlDbtrWltId>,<OrgnlCdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"OrgnlDbtrWltId", "OrgnlCdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.433.001.01.xml".equals(msgtype)) {
                //银行账户挂接管理请求报文
                isGenerateDigitalSign = true;
                String[] elements = {"SgnAcctId", "SgnAcctNm", "IDNo", "Tel", "WltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.909.001.01.xml".equals(msgtype)) {
                //交易终态通知报文
                isGenerateDigitalSign = true;
                String[] elements = {"Cntt"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else if ("dcep.951.001.01.xml".equals(msgtype)) {
                //账户借记控制通知报文
                isGenerateDigitalSign = true;
                String[] elements = {"Cntt"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, true);
            } else {
                isGenerateDigitalSign = false;
                bodyMsg = reqMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyMsg;
    }

    //解密报文体
    public static String decryptBody(String msgtype, String reqMsg) {
        String bodyMsg = "";
        byte[] dpKey = Convert.hexToBytes(digitalenvelope_plain);
        try {
            if ("dcep.221.001.01.xml".equals(msgtype)) {
                //DC/EP兑回业务请求报文
                //<DbtrNm>,<DbtrWltId>,<CdtrNm>,<CdtrAcct>需加密
                //加密处理并做Base64转码
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrWltId", "CdtrNm", "CdtrAcct"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.225.001.01.xml".equals(msgtype)) {
                //DC/EP兑出业务请求报文
                //<DbtrNm>,<DbtrAcct>,<CdtrNm>,<CdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.227.001.01.xml".equals(msgtype)) {
                //DC/EP汇款兑出请求报文
                //<DbtrNm>,<DbtrAcct>,<CdtrNm>,<CdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.411.001.01.xml".equals(msgtype)) {
                //交易状态查询请求报文
                //<OrgnlDbtrWltId>,<OrgnlCdtrWltId>
                isGenerateDigitalSign = true;
                String[] elements = {"OrgnlDbtrWltId", "OrgnlCdtrWltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.433.001.01.xml".equals(msgtype)) {
                //银行账户挂接管理请求报文
                isGenerateDigitalSign = true;
                String[] elements = {"SgnAcctId", "SgnAcctNm", "IDNo", "Tel", "WltId"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.909.001.01.xml".equals(msgtype)) {
                //交易终态通知报文
                isGenerateDigitalSign = true;
                String[] elements = {"Cntt"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else if ("dcep.951.001.01.xml".equals(msgtype)) {
                //账户借记控制通知报文
                isGenerateDigitalSign = true;
                String[] elements = {"Cntt"};
                bodyMsg = updateXmlValue(reqMsg, elements, dpKey, false);
            } else {
                isGenerateDigitalSign = false;
                bodyMsg = reqMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyMsg;
    }

    //生成数字签名域,，含加密加签
    public static String getDigitalSign(String msg) {
        String digitalSign = "";
        if (isGenerateDigitalSign) {
            digitalSign = "{S::SSN:1036581724:NSN:1036593915"
                    + ":DEP:" + digitalenvelope_cipher()
                    + ":DSG:" + getDigitalSignature(msg)
                    + "}\r\n";
        } else {
            //无需加密
            digitalSign = "{S::SSN:1036581724"
                    //+":DEP:"+ digitalenvelope_cipher()
                    + ":DSG:" + getDigitalSignature(msg)
                    + "}\r\n";
        }
        return digitalSign;
    }


    //生成请求报文
    public static String getReqMsg(String msgType) {
        String reqMsg = "";
        try {
            String bodyMsg = encryptBody(msgType, FilesUtil.getInstance().getMsg(msgType));
            //MesgID -> 202011171021221000000000001120010001

            String msgId = "00000000001120010001" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
            bodyMsg = String.format(bodyMsg, msgId);
            bodyMsg = bodyMsg.replace("#ISODT#",DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss"));
            String newHeader = String.format(header, DateUtil.format(new Date(), "yyyyMMddHHmmss"), msgType.substring(0, 15), msgId, msgId);

            reqMsg = newHeader + "\r\n" + getDigitalSign(bodyMsg) + bodyMsg;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reqMsg;
    }

    //生成响应报文
    public static String getRspMsg(String msgType) {
        String rspMsg = "";
        //默认需要加签，不加密
        isGenerateDigitalSign = false;
        try {
            //String bodyMsg = encryptBody(msgType, new String(FilesUtil.getInstance().getMsgbyte(msgType),"UTF-8"));
            //无需加密
            String bodyMsg = FilesUtil.getInstance().getMsg(msgType);
            String msgId = "00000000001120010001" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());

            String newHeader = String.format(header, DateUtil.format(new Date(), "yyyyMMddHHmmss"), msgType.substring(0, 15), msgId, msgId);

            rspMsg = newHeader + "\r\n" + getDigitalSign(bodyMsg) + bodyMsg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rspMsg;
    }

    public static String checkMsg(String message) {
        String header = message.substring(0, 202);
        String removeHeadStr = message.substring(202, message.length());
        //处理签名域部分
        int domainEndPox = 0;
        if (removeHeadStr.startsWith("{S:")) {
            int beginpox = removeHeadStr.indexOf("}");
            domainEndPox = beginpox + 3;
            String signDomainStr = removeHeadStr.substring(0, domainEndPox);
            //验签
            //获取数字签名内容
            String digitalSignature = signDomainStr.substring(signDomainStr.indexOf("DSG:") + 4, signDomainStr.indexOf("}"));
            //获取body内容
            String bodyMsg = removeHeadStr.substring(domainEndPox, removeHeadStr.length());
            if (verifyDigitalSignature(bodyMsg, digitalSignature)) {
                logger.info("交易验签成功！");
                if (signDomainStr.indexOf("DEP:") != -1) {
                    //获取数字信封对称密钥
                    String dgtlEnvlp = signDomainStr.substring(signDomainStr.indexOf("DEP:") + 4, signDomainStr.indexOf(":DSG"));
                    //获取数字信封明文密钥
                    byte[] getDigitalenvelope_plain = getDigitalenvelope_plain(dgtlEnvlp);
                    //解密报文体字段
                    String msgType = bodyMsg.substring(bodyMsg.indexOf("xsd:") + 4, bodyMsg.indexOf("xsd:") + 19) + ".xml";
                    String decryptBodyMsg = decryptBody(msgType, bodyMsg);
                    System.out.println(decryptBodyMsg);
                }

            } else {
                throw new RuntimeException("验签失败！");
            }

        }
        return "";
    }


    public static void main(String[] args) {
        //checkMsg(MsgUtil.getReqMsg("dcep.221.001.01.xml"));
        //System.out.println(MsgUtil.getReqMsg("dcep.221.001.01.xml"));
        //System.out.println(MsgUtil.digitalenvelope_cipher());
        //System.out.println(MsgUtil.getReqMsg("dcep.221.001.01.xml"));

        System.out.println(getReqMsg("dcep.221.001.01.xml"));
    }
}
