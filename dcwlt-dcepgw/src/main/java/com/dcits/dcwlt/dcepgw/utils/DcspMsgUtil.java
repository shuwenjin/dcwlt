package com.dcits.dcwlt.dcepgw.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.exception.GwException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class DcspMsgUtil {
    public static final String SIGNSN_KEY = "SignSN";
    public static final String SIGNSN_TAG = ":SSN:";
    public static final String DIGITALSIGNATURE_KEY = "DigitalSignature";
    public static final String DIGITALSIGNATURE_TAG = ":DSG:";
    public static final String NCRPTNSN_KEY = "NcrptnSN";
    public static final String NCRPTNSN_TAG = ":NSN:";
    public static final String DGTLENVLP_KEY = "DgtlEnvlp";
    public static final String DGTLENVLP_TAG = ":DEP:";

    public static final int[] HEAD_LEN_ARRAY = {3, 2, 14, 4, 14, 4, 8, 6, 3, 20, 40, 40, 1, 1, 10, 10, 10, 9, 3};
    public static final String[] HEAD_KEY_ARRAY = {"BeginFlag", "VersionID", "OrigSender", "OrigSenderSID", "OrigReceiver", "OrigReceiverSID", "OrigSendDate", "OrigSendTime", "StructType", "MesgType", "MesgID", "MesgRefID", "MesgPriority", "MesgDirection", "SderReserved", "RcverReserved", "CenterReserved", "Reserve", "EndFlag"};

    public static final String HAED_BEGIN_FLAG = "{H:";
    public static final String SIGN_BEGIN_FLAG = "{S:";
    public static final String END_FLAG = "}\r\n";

    public static final Map<String, String[]> ENCRYPT_FIELD = new LinkedHashMap<String, String[]>();
    //发送方公钥
    public static String SENDPUBLICKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEdcjKVPXzo9pHK+tSgKRlLME8ViiaaLrOwt7LZ7hUHphx/q8fvGfy1nmbUIZlZJ++E4WKiqrYH457WyaObaG+WQ==";
    //发送方私钥
    public static String SENDPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg405/E9Kvb30cE4R0MNwnuiOGQA+J66zKlheVtIK9/+qgCgYIKoEcz1UBgi2hRANCAAR1yMpU9fOj2kcr61KApGUswTxWKJpous7C3stnuFQemHH+rx+8Z/LWeZtQhmVkn74ThYqKqtgfjntbJo5tob5Z";

    //接收方公钥
    public static String PUBLICKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==";
    //接收方私钥
    public static String PRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ";

    static {
        //DC/EP兑回业务请求报文
        String[] F221 = {"DbtrNm", "DbtrWltId", "CdtrNm", "CdtrAcct"};
        ENCRYPT_FIELD.put("dcep.221.001.01", F221);
        //DC/EP兑出业务请求报文
        String[] F225 = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
        ENCRYPT_FIELD.put("dcep.225.001.01", F225);
        //DC/EP汇款兑出请求报文
        String[] F227 = {"DbtrNm", "DbtrAcct", "CdtrNm", "CdtrWltId"};
        ENCRYPT_FIELD.put("dcep.227.001.01", F227);
        //交易状态查询请求报文
        String[] F411 = {"OrgnlDbtrWltId", "OrgnlCdtrWltId"};
        ENCRYPT_FIELD.put("dcep.411.001.01", F411);
        //交易明细查询应答报文
        String[] F418 = {"Cntt"};
        ENCRYPT_FIELD.put("dcep.418.001.01", F418);
        //交易终态通知报文
        String[] F909 = {"Cntt"};
        ENCRYPT_FIELD.put("dcep.909.001.01", F909);
        //账户借记控制通知报文
        String[] F951 = {"Cntt"};
        ENCRYPT_FIELD.put("dcps.951.001.01", F951);
        //银行账户挂接管理请求报文
        String[] F433 = {"SgnAcctId", "SgnAcctNm", "IDNo", "Tel", "WltId"};
        ENCRYPT_FIELD.put("dcep.433.001.01", F433);
        //银行账户挂接管理应答报文
        String[] F434 = {"SgnAcctId", "SgnAcctNm", "WltId"};
        ENCRYPT_FIELD.put("dcep.434.001.01", F434);

    }

    /**
     * 拆解混合结构报文
     *
     * @param orgString 混合结构报文
     * @return JSONObject 拆解后的json
     */
    public static JSONObject unPack(String orgString) throws Exception {
        JSONObject jsonObject = new JSONObject();
        //头
        String head = orgString.substring(0, 202);
        //head定长拆解
        Map<String, String> headerMap = DcspMsgUtil.unPackFixHead(head);
        String signAndBody = orgString.substring(202);
        int bodyStartIdx = 0;
        if (signAndBody.startsWith(SIGN_BEGIN_FLAG)) {
            bodyStartIdx = signAndBody.indexOf("}") + 3;
            //签名域
            String sgn = signAndBody.substring(0, bodyStartIdx);
            //签名域tag 拆解
            Map signMap = DcspMsgUtil.unPackTagSign(sgn);
            headerMap.putAll(signMap);
        }
        //报文体
        String body = signAndBody.substring(bodyStartIdx);

        //验签 TODO 根据序号获取对方公钥，暂时用固定的
        if (StrUtil.isNotBlank(headerMap.get(SIGNSN_KEY))) {
            boolean verify = verifyDigitalSignature(body, headerMap.get(DIGITALSIGNATURE_KEY), PUBLICKEY);
            if (!verify) {
                log.error("报文验签不通过！");
                throw new GwException(GwException.CODE_SIGN, "报文验签不通过");
            }
        }
        //解密 TODO 根据序号获取我方私钥，暂时用固定的
        if (StrUtil.isNotBlank(headerMap.get(NCRPTNSN_KEY))) {
            byte[] key = getDigitalEnvelopePlain(PRIVATEKEY, headerMap.get(DGTLENVLP_KEY));
            //将数字信封密码HEX存放到数字信封
            headerMap.put(DGTLENVLP_KEY, HexUtil.encodeHexStr(key));
            //解密敏感字段
            body = decryptField(headerMap.get(HEAD_KEY_ARRAY[9]), body, key);
        }
        //报文转换
        JSONObject jsonBody = JsonXmlUtil.dcspXmlToJson(body);

        jsonObject.put(JsonXmlUtil.HEAD, headerMap);
        jsonObject.put(JsonXmlUtil.BODY, jsonBody);

        return jsonObject;
    }

    /**
     * 拆解定长报文头
     *
     * @param headString 定长head
     * @return Map 拆解后的头map
     */
    private static Map unPackFixHead(String headString) {
        Map mapHead = new LinkedHashMap();
        int curPosition = 0;
        for (int index = 0; index < HEAD_LEN_ARRAY.length; index++) {
            mapHead.put(HEAD_KEY_ARRAY[index], headString.substring(curPosition, curPosition + HEAD_LEN_ARRAY[index]).trim());
            curPosition += HEAD_LEN_ARRAY[index];
        }
        return mapHead;
    }

    /**
     * 拆解Tag变长签名域
     *
     * @param signString Tag变长签名域
     * @return Map 拆解后的头map
     */
    private static Map unPackTagSign(String signString) {
        Map mapSign = new LinkedHashMap();
        //签名域tag 拆解
        boolean ssnFlag = false;
        if (signString.contains(SIGNSN_TAG)) {
            //有签名证书序列号，必然有 :DSG:
            mapSign.put(SIGNSN_KEY, StrUtil.subBetween(signString, SIGNSN_TAG, ":"));
            mapSign.put(DIGITALSIGNATURE_KEY, StrUtil.subBetween(signString, DIGITALSIGNATURE_TAG, "}"));
            ssnFlag = true;
        }
        if (signString.contains(NCRPTNSN_TAG)) {
            //有加密证书序列号，必然有 :DEP:
            mapSign.put(NCRPTNSN_KEY, StrUtil.subBetween(signString, NCRPTNSN_TAG, ":"));
            if (ssnFlag) {
                mapSign.put(DGTLENVLP_KEY, StrUtil.subBetween(signString, DGTLENVLP_TAG, ":"));
            } else {
                mapSign.put(DGTLENVLP_KEY, StrUtil.subBetween(signString, DGTLENVLP_TAG, "}"));
            }
        }
        return mapSign;
    }

    /**
     * 拆解混合结构报文
     *
     * @param orgJson json结构报文
     * @return String 组装后的混合报文
     */
    public static String pack(String orgJson) throws Exception {
        StringBuilder mix = new StringBuilder();
        JSONObject jsonObject = JSON.parseObject(orgJson);
        //报文体
        String bodyXml = JsonXmlUtil.jsonToDcspXml(jsonObject);
        //加密敏感字段 不能用API操作，因为BASE64信息会被API转码导致加解密异常 TODO
        String msgType = jsonObject.getJSONObject(JsonXmlUtil.HEAD).getString(HEAD_KEY_ARRAY[9]);
        //随机生成对称密钥
        byte[] sm4Key = new SM4().getSecretKey().getEncoded();
        String nsn = "1";
        if (StrUtil.isNotBlank(nsn)) {
            bodyXml = encryptField(msgType, bodyXml, sm4Key);
        }
        //报文头
        String headString = packFixHead(jsonObject.getJSONObject(JsonXmlUtil.HEAD));

        //签名

        String ssn = "1";//TODO  根据序号获取我方私钥，暂时用固定的
        //报文签名
        String dsg = "";
        if (StrUtil.isNotBlank(ssn)) {
            dsg = getDigitalSignature(bodyXml, PRIVATEKEY);
        }
        //TODO  根据序号获取对方公钥，暂时用固定的
        //数字信封
        String dep = "";
        if (StrUtil.isNotBlank(nsn)) {
            dep = getDigitalEnvelopeCipher(PUBLICKEY, sm4Key);
        }
        //签名域
        String tagString = packTagSign(ssn, dsg, nsn, dep);

        mix.append(headString)
                .append(tagString)
                .append(bodyXml);
        return mix.toString();
    }

    /**
     * 拆解定长报文头
     *
     * @param headJson Jsonhead
     * @return String 拼装后的定长String头
     */
    private static String packFixHead(JSONObject headJson) {
        StringBuilder headString = new StringBuilder();
        for (int index = 0; index < HEAD_LEN_ARRAY.length; index++) {
            String value = headJson.getString(HEAD_KEY_ARRAY[index]);
            if (0 == index) {
                value = HAED_BEGIN_FLAG;
            } else if (HEAD_LEN_ARRAY.length - 1 == index) {
                value = END_FLAG;
            }
            headString.append(StrUtil.fillAfter(null == value ? "" : value, ' ', HEAD_LEN_ARRAY[index]));
        }
        return headString.toString();
    }

    /**
     * 拆解Tag变长签名域
     *
     * @param ssn 签名证书序列号
     * @param dsg 报文签名
     * @param nsn 加密证书序列号
     * @param dep 数字信封
     * @return String 数字签名域
     */
    private static String packTagSign(String ssn, String dsg, String nsn, String dep) {
//        if (StrUtil.isBlank(ssn) && StrUtil.isBlank(nsn)) {
//            //如果签名证书序列号 和 加密证书序列号 都为空 则可以没有签名域
//            return "";
//        }
        StringBuilder tagString = new StringBuilder();
        tagString.append(SIGN_BEGIN_FLAG);
        //签名证书序列号
//        if (StrUtil.isNotBlank(ssn)) {
//            tagString.append(SIGNSN_TAG)
//                    .append(ssn);
//        }
        tagString.append(SIGNSN_TAG).append(ssn);

        //加密证书序列号 、数字信封
        if (StrUtil.isNotBlank(nsn)) {
            tagString.append(NCRPTNSN_TAG)
                    .append(nsn)
                    .append(DGTLENVLP_TAG)
                    .append(dep);
        }
        //报文签名
        tagString.append(DIGITALSIGNATURE_TAG);
        if (StrUtil.isNotBlank(ssn)) {
            tagString.append(dsg);
        }
        tagString.append(END_FLAG);

        return tagString.toString();
    }

    /**
     * 生成数字信封
     * 使用接收方数字证书（公钥）和非对称算法（SM2）加密对称密钥
     *
     * @param publicKey            对方公钥
     * @param digitalEnvelopePlain 数字信封明文(对称密钥)
     * @return String              Base64数字信封
     */
    public static String getDigitalEnvelopeCipher(String publicKey, byte[] digitalEnvelopePlain) {
        String data = SmUtil.sm2(null, publicKey).encryptBase64(digitalEnvelopePlain, KeyType.PublicKey);
        return data;
    }

    /**
     * 解密数字信封获取明文密钥
     *
     * @param privateKey            我方私钥
     * @param digitalEnvelopeCipher 数字信封密文(对称密钥)
     * @return byte[]               数字信封密文密钥
     */
    public static byte[] getDigitalEnvelopePlain(String privateKey, String digitalEnvelopeCipher) {
        byte[] data = SmUtil.sm2(privateKey, null).decrypt(digitalEnvelopeCipher, KeyType.PrivateKey);
        return data;
    }


    /**
     * 生成数字签名内容
     * （1）业务发起方将整个Document（报文体）计算哈希值，作为签名源串；
     * （2）使用本行的数字证书（私钥）对签名源串签名；
     * （3）将签名值使用BASE64转码
     *
     * @param msg        待签名信息
     * @param privateKey 签名我方私钥
     * @return String    签名
     */
    public static String getDigitalSignature(String msg, String privateKey) {
        String signature = "";
        try {
            byte[] data = SmUtil.sm2(privateKey, null).sign(SM3.create().digest(msg));
            signature = Base64.encode(data);
        } catch (Exception e) {
            log.error("签名异常", e);
            throw new GwException(GwException.CODE_SIGN, "签名异常");
        }

        return signature;
    }

    /**
     * 校验数字签名内容
     * （1）将签名值使用BASE64解码
     * （1）业务发起方将整个Document（报文体）计算哈希值，作为签名源串；
     * （2）使用对方的数字证书（公钥）对签名验签；
     *
     * @param msg       待校验原文
     * @param sign      签名信息
     * @param publicKey 对方公钥
     * @return String   签名
     */
    public static boolean verifyDigitalSignature(String msg, String sign, String publicKey) {
        boolean isVerify = false;
        try {
            isVerify = SmUtil.sm2(null, publicKey).verify(SM3.create().digest(msg), Base64.decode(sign));
        } catch (Exception e) {
            log.error("验签异常", e);
            throw new GwException(GwException.CODE_SIGN, "验签异常");
        }
        return isVerify;
    }

    /**
     * 加密报文体中敏感字段
     *
     * @param msgType 报文类型
     * @param xmlMsg  源xml报文
     * @param key     加密密钥
     */
    public static String encryptField(String msgType, String xmlMsg, byte[] key) {
        String bodyMsg = xmlMsg;
        try {
            String[] elements = ENCRYPT_FIELD.get(msgType);
            if (null != elements) {
                bodyMsg = updateXmlValue(xmlMsg, elements, key, true);
            }
        } catch (Exception e) {
            log.error("加密敏感字段异常！", e);
        }

        return bodyMsg;
    }

    /**
     * 解密报文体中敏感字段
     *
     * @param msgType 报文类型
     * @param xmlMsg  源xml报文
     * @param key     加密密钥
     */
    public static String decryptField(String msgType, String xmlMsg, byte[] key) {
        String bodyMsg = xmlMsg;
        try {
            String[] elements = ENCRYPT_FIELD.get(msgType);
            if (null != elements) {
                bodyMsg = updateXmlValue(xmlMsg, elements, key, false);
            }
        } catch (Exception e) {
            log.error("解密敏感字段异常！", e);
            throw new GwException(GwException.CODE_ENCRYPT, "解密敏感字段异常");
        }

        return bodyMsg;
    }

    public static String updateXmlValue(String xml, String[] elements, byte[] dpKey, boolean isencrypt) {
        Map<String, String> values = new HashMap<>();
        try {
            for (String key : elements) {
                String value = getXmlKeyValue(xml, key);
                if (isencrypt) {
                    values.put(key, SmUtil.sm4(dpKey).encryptBase64(value));
                } else {
                    values.put(key, SmUtil.sm4(dpKey).decryptStr(value));
                }

            }
            for (String element : values.keySet()) {
                String value = values.get(element);
                int len = xml.indexOf("<" + element + ">");
                int len2 = xml.lastIndexOf("</" + element + ">");
                xml = xml.substring(0, len + element.length() + 2) + value + xml.substring(len2, xml.length());
            }
        } catch (Exception e) {
            log.error("加解密敏感字段异常！", e);
            throw new GwException(GwException.CODE_ENCRYPT, "更新加解密敏感字段异常");
        }
        return xml;
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

    /**
     * 通用响应报文，900
     * */
    public static String get900() {
        JSONObject header = new JSONObject();
        header.put("MesgType", "dcep.900.001.01");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dcepHead", header);

        JSONObject cmonConf = new JSONObject();
        JSONObject cmonConfInf = new JSONObject();
        cmonConfInf.put("PrcSts", "PR01");
        cmonConfInf.put("ProcessCode", "S9007");
        cmonConfInf.put("RejectInformation", "系统调用失败");
        cmonConf.put("CmonConfInf", cmonConfInf);
        JSONObject body = new JSONObject();
        body.put("CmonConf", cmonConf);
        jsonObject.put("body", body);
        return jsonObject.toJSONString();
    }
}
