package com.dcits.dcwlt.dcepgw.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.exception.GwException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;

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
    public static final String MSGTP903 = "ccms.903.001.02";

    public static final Map<String, String[]> ENCRYPT_FIELD = new LinkedHashMap<String, String[]>();

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

    public static JSONObject unPack(String orgString) throws Exception {
        return unPack(orgString, false);
    }

    /**
     * 拆解混合结构报文
     *
     * @param orgString 混合结构报文
     * @param isSign    签名开关
     * @return JSONObject 拆解后的json
     */
    public static JSONObject unPack(String orgString, boolean isSign) throws Exception {
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
        //报文编号
        String msgTp = headerMap.get(HEAD_KEY_ARRAY[9]);
        //报文体
        String body = signAndBody.substring(bodyStartIdx);

        //验签
        //如果是证书绑定通知使用pkcs7打包 dsg TODO
        if (StringUtils.equals(msgTp, MSGTP903)) {
            Map certInfo = new HashMap();
            boolean re = SMUtil.signedData_Verify(Base64.decode(headerMap.get(DIGITALSIGNATURE_KEY)), certInfo);
            if (re) {
                //TODO
                System.out.println(certInfo);
            } else {
                log.error("报文验签不通过！");
                throw new GwException(GwException.CODE_SIGN, "报文验签不通过");
            }
        } else {
            // 根据序号获取对方公钥
            String ssn = headerMap.get(SIGNSN_KEY);
            if (isSign) {
                if (StrUtil.isNotBlank(ssn)) {
                    boolean verify = SMUtil.verifySign(ssn, body, headerMap.get(DIGITALSIGNATURE_KEY));
                    if (!verify) {
                        log.error("报文验签不通过！");
                        throw new GwException(GwException.CODE_SIGN, "报文验签不通过");
                    }
                }

//                //校验schema
//                boolean xmlValid = XmlUtil.validateXMLByXSD(body, msgTp);
//                if (!xmlValid) {
//                    //TODO
//                    throw new GwException("GW-1001", "XML格式检验失败！");
//                }

                //解密 根据序号获取我方私钥
                String nsn = headerMap.get(NCRPTNSN_KEY);
                if (StrUtil.isNotBlank(nsn)) {
//            byte[] key = getDigitalEnvelopePlain(PRIVATEKEY, headerMap.get(DGTLENVLP_KEY));
                    byte[] key = SMUtil.getKeyFromDigitalEnvelope(nsn, headerMap.get(DGTLENVLP_KEY));
                    //将数字信封密码HEX存放到数字信封
                    headerMap.put(DGTLENVLP_KEY, HexUtil.encodeHexStr(key, false));
                    //解密敏感字段
                    body = decryptField(headerMap.get(HEAD_KEY_ARRAY[9]), body, key);
                }
            }
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

    public static String pack(String orgJson) throws Exception {
        return pack(orgJson, false);
    }

    /**
     * 拆解混合结构报文
     *
     * @param orgJson json结构报文
     * @param isSign  签名开关
     * @return String 组装后的混合报文
     */
    public static String pack(String orgJson, boolean isSign) throws Exception {
        StringBuilder mix = new StringBuilder();
        JSONObject jsonObject = JSON.parseObject(orgJson);
        //报文体
        String bodyXml = JsonXmlUtil.jsonToDcspXml(jsonObject);
        log.debug("加密前报文体[{}]", bodyXml);
        //加密敏感字段 不能用API操作，因为BASE64信息会被API转码导致加解密异常
        String msgType = jsonObject.getJSONObject(JsonXmlUtil.HEAD).getString(HEAD_KEY_ARRAY[9]);
        //随机生成对称密钥
        byte[] sm4Key = SMUtil.genSM4Key();
        //加密证书序列号 ,TODO 后端选择，通过报文头传过来
        String nsn = jsonObject.getJSONObject(JsonXmlUtil.HEAD).getString(NCRPTNSN_KEY);
        if (isSign) {
            if (StrUtil.isNotBlank(nsn)) {
                bodyXml = encryptField(msgType, bodyXml, sm4Key);
            }
        }

//        //排序
//        XmlUtil.sortByXSD(bodyXml, msgType);
//        //校验schema
//        boolean xmlValid = XmlUtil.validateXMLByXSD(bodyXml, msgType);
//        if (!xmlValid) {
//            //TODO
//            throw new GwException("GW-1001", "XML格式检验失败！");
//        }


        //报文头
        String headString = packFixHead(jsonObject.getJSONObject(JsonXmlUtil.HEAD));

        //签名
        //签名证书序列号 TODO  后端选择，通过报文头传过来
        String ssn = jsonObject.getJSONObject(JsonXmlUtil.HEAD).getString(SIGNSN_KEY);
        ;
        String dsg = "";
        String dep = "";
        if (isSign) {
            //报文签名
            if (StrUtil.isNotBlank(ssn)) {
                dsg = SMUtil.sign(ssn, bodyXml);
            }
            //数字信封
            if (StrUtil.isNotBlank(nsn)) {
                dep = SMUtil.getDigitalEnvelopeCipher(nsn, sm4Key);
            }
        }
        //如果是证书绑定通知使用pkcs7打包 dsg TODO
        if (StringUtils.equals(msgType, MSGTP903)) {
            dsg = SMUtil.pkcs7(SMUtil.digest(bodyXml));
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
                    values.put(key, SMUtil.encryptSm4B64(value, dpKey));
                } else {
                    values.put(key, SMUtil.decryptSm4B64(value, dpKey));
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
     */
    public static JSONObject get900() {
        JSONObject header = new JSONObject();
        header.put("MesgType", "dcep.900.001.01");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonXmlUtil.HEAD, header);

        JSONObject cmonConf = new JSONObject();
        JSONObject cmonConfInf = new JSONObject();
        cmonConfInf.put("PrcSts", "PR01");
        cmonConfInf.put("ProcessCode", "S9007");
        cmonConfInf.put("RejectInformation", "系统调用失败");
        cmonConf.put("CmonConfInf", cmonConfInf);
        JSONObject body = new JSONObject();
        body.put("CmonConf", cmonConf);
        jsonObject.put(JsonXmlUtil.BODY, body);
        return jsonObject;
    }
}
