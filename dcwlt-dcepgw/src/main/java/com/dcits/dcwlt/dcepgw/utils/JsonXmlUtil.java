package com.dcits.dcwlt.dcepgw.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JSON对象与XML相互转换工具类
 *
 * @version 1.0.0
 * @createDate 2019-11-18
 */
public class JsonXmlUtil {

    private static final String ENCODING = "UTF-8";
    private static final String KEY_CCY = "Ccy";
    private static final String KEY_VALUE = "value";
    public static final String HEAD = "dcepHead";
    public static final String BODY = "body";
    private static final String SOAP_ROOT_PREFIX = "soap:";
    private static final String SOAP_HEAD_PREFIX = "head:";
    private static final String SOAP_BODY_PREFIX = "wstxns1:";
    private static final String SOAP_ROOT_ELM = "soap:Envelope";
    private static final String SOAP_ROOT_ATT = "xmlns:soap";
    private static final String SOAP_ROOT_ATT_V = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAP_HEAD_ATT = "xmlns:head";
    private static final String SOAP_HEAD_ATT_V = "http://www.dcep.com/dcep/header/";
    private static final String SOAP_BODY_ATT = "xmlns:wstxns1";
    private static final String SOAP_BODY_ATT_V = "http://www.dcep.com/";
    private static final String MSGTP = "head:MsgTp";

    private static final String MESGTYPE_KEY = "MesgType";
    private static final String DCSP_ROOT_ELM = "Document";
    private static final String DCSP_ROOT_ATT_XMLNS = "xmlns";
    private static final String DCSP_ROOT_ATT_XMLNS_V = "urn:cbcc:std:dcep:2020:tech:xsd:{MSGTP}";
    private static final String DCSP_ROOT_ATT_XSI = "xmlns:xsi";
    private static final String DCSP_ROOT_ATT_XSI_V = "http://www.w3.org/2001/XMLSchema-instance";
    private static final Set<String> LISTELEMENTS = new HashSet<String>(Arrays.asList(new String[]{"AuthrtyInf", "ChngInf", "BizAuthrtyInf", "BatchList", "ClrList", "FileInf", "FileName", "ChkPayInf", "SummaryGrp"}));

    private JsonXmlUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 城银清XML转json
     */
    public static JSONObject dcspXmlToJson(String xml) throws DocumentException {
        return soapToJson(xml);
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws org.dom4j.DocumentException
     */
    public static JSONObject soapToJson(String xml) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();
        JSONObject rootJosn = new JSONObject();
        if (root.elements().isEmpty()) {
            return rootJosn;
        }
        rootJosn = dcepElementToJson(root, rootJosn);
        return rootJosn;
    }

    /**
     * DCEPElement对象转JSON对象
     *
     * @param parentElement Element
     * @param parent        JSONObject
     * @return JSON对象
     */
    public static JSONObject dcepElementToJson(Element parentElement, JSONObject parent) {
        // 数组类型Map
        Map<String, JSONArray> arrayMap = new HashMap<>();
        for (Object child : parentElement.elements()) {
            Element e = (Element) child;
            // 当前节点是数组
            if (LISTELEMENTS.contains(e.getName())) {
                // 数组子节点没有child
                if (e.elements().isEmpty()) {
                    if (e.attributes().isEmpty()) {
                        arrayMapAddItem(arrayMap, e, e.getText());
                    } else {
                        JSONObject json = new JSONObject();
                        json.put(e.getName(), ElementWithAttrToJSON(e));
                        arrayMapAddItem(arrayMap, e, json);
                    }
                } else {
                    arrayMapAddItem(arrayMap, e, dcepElementToJson(e, new JSONObject()));
                }
            } else {
                // 当前节点无child
                if (e.elements().isEmpty()) {
                    if (e.attributes().isEmpty()) {
                        parent.put(e.getName(), e.getText());
                    } else {
                        parent.put(e.getName(), ElementWithAttrToJSON(e));
                    }
                } else {
                    // 当前节点有child
                    parent.put(e.getName(), dcepElementToJson(e, new JSONObject()));
                }
            }
        }
        // 将数组类型属性挂到父节点上
        for (Map.Entry<String, JSONArray> entry : arrayMap.entrySet()) {
            parent.put(entry.getKey(), entry.getValue());
        }

        return parent;
    }

    /**
     * 数组类型Map添加元素
     *
     * @param arrayMap
     * @param e
     * @param item
     */
    public static void arrayMapAddItem(Map<String, JSONArray> arrayMap, Element e, Object item) {
        if (null == arrayMap.get(e.getName())) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(item);
            arrayMap.put(e.getName(), jsonArray);
        } else {
            JSONArray jsonArray = arrayMap.get(e.getName());
            jsonArray.add(item);
            arrayMap.put(e.getName(), jsonArray);
        }
    }

    /**
     * ElementWithAttrToJSON 处理带属性的节点
     *
     * @param element
     * @return
     */
    public static JSONObject ElementWithAttrToJSON(Element element) {
        JSONObject json = new JSONObject();
        for (int index = 0; index < element.attributes().size(); ++index) {
            Attribute attribute = element.attribute(index);
            json.put(attribute.getName(), attribute.getValue());
        }
        json.put(KEY_VALUE, element.getText());
        return json;
    }

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON字符串
     * @return 漂亮的xml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToSoap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Document document = jsonToSoapDocument(jsonObject, ENCODING);

        return ToPrettyXml(document);
    }

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToSoap(JSONObject json) throws IOException {
        Document document = jsonToSoapDocument(json, ENCODING);

        return ToPrettyXml(document);
    }

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param document xml对象
     * @return 漂亮的xml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String ToPrettyXml(Document document) {
        try {
            /* 格式化xml */
            OutputFormat format = OutputFormat.createPrettyPrint();

            // 设置缩进为4个空格
            format.setIndent(" ");
            format.setIndentSize(4);

            StringWriter formatXml = new StringWriter();
            XMLWriter writer = new XMLWriter(formatXml, format);
            writer.write(document);
            return formatXml.toString();
        } catch (Exception e) {
            return document.asXML();
        }
    }

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToPrettyXml(JSONObject json) throws IOException {

        Document document = jsonToDocument(json, ENCODING);

        /* 格式化xml */
        OutputFormat format = OutputFormat.createPrettyPrint();

        // 设置缩进为4个空格
        format.setIndent(" ");
        format.setIndentSize(4);

        StringWriter formatXml = new StringWriter();
        XMLWriter writer = new XMLWriter(formatXml, format);
        writer.write(document);

        return formatXml.toString();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToXml(JSONObject json) {
        return jsonToDocument(json, ENCODING).asXML();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToXml(JSONObject json, String encoding) {
        return jsonToDocument(json, encoding).asXML();
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws org.xml.sax.SAXException
     */
    public static Document jsonToSoapDocument(JSONObject json, String encoding) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(encoding);
        // root
        Element root = DocumentHelper.createElement(SOAP_ROOT_ELM);
        root.addAttribute(SOAP_ROOT_ATT, SOAP_ROOT_ATT_V);
        root.addAttribute(SOAP_HEAD_ATT, SOAP_HEAD_ATT_V);
        String msgTp = "";
        //header 和 body
        if (json.containsKey(HEAD)) {
            Element header = DocumentHelper.createElement(SOAP_HEAD_PREFIX + HEAD);
            header = jsonToSoapHeader(json.getJSONObject(HEAD), header);
            root.add(header);
            msgTp = header.elementText(MSGTP);
        }
        if (json.containsKey(BODY)) {
            Element body = DocumentHelper.createElement(SOAP_ROOT_PREFIX + BODY);
            for (String bodyChildKey : json.getJSONObject(BODY).keySet()) {
                Element bodyChild = DocumentHelper.createElement(SOAP_BODY_PREFIX + bodyChildKey);
                bodyChild.addAttribute(SOAP_BODY_ATT, msgTpToBodySchema(msgTp));
                bodyChild = addJsonToElement(json.getJSONObject(BODY).getJSONObject(bodyChildKey), bodyChild);
                body.add(bodyChild);

            }


            root.add(body);
        }
        document.add(root);
        return document;
    }

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param jsonObject JSON对象
     * @return 漂亮的DCSPxml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToDcspXml(JSONObject jsonObject) {
        Document document = jsonToDcspDocument(jsonObject, ENCODING);
        return ToPrettyXml(document);
    }
    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON字符串
     * @return 漂亮的DCSPxml字符串
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static String jsonToDcspXml(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Document document = jsonToDcspDocument(jsonObject, ENCODING);
        return ToPrettyXml(document);
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws org.xml.sax.SAXException
     */
    public static Document jsonToDcspDocument(JSONObject json, String encoding) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(encoding);
        // root

        String msgTp = "";
        //header 和 body
        if (json.containsKey(HEAD)) {
            msgTp = json.getJSONObject(HEAD).getString(MESGTYPE_KEY);
        }
        Element root = document.addElement(DCSP_ROOT_ELM,DCSP_ROOT_ATT_XMLNS_V.replace("{MSGTP}", msgTp));
//        Element root =DocumentHelper.createElement(DCSP_ROOT_ELM);
        root.addAttribute(DCSP_ROOT_ATT_XSI, DCSP_ROOT_ATT_XSI_V);

        if (json.containsKey(BODY)) {
            for (String bodyChildKey : json.getJSONObject(BODY).keySet()) {
                Element bodyChild = root.addElement(bodyChildKey);
                addJsonToElement(json.getJSONObject(BODY).getJSONObject(bodyChildKey), bodyChild);
            }
        }
//        document.add(root);
        return document;
    }

    public static String msgTpToBodySchema(String msgTp) {
        StringBuilder bodySchema = new StringBuilder();
        bodySchema.append(SOAP_BODY_ATT_V);
        if (StringUtils.isNotBlank(msgTp)) {
            String[] tpa = msgTp.split("\\.");
            if (tpa.length > 0) {
                bodySchema.append(tpa[0]).append("/");
                for (int i = 1; i < tpa.length; i++) {
                    bodySchema.append(tpa[i]);
                }
                bodySchema.append("/");
            }
        }
        return bodySchema.toString();
    }

    /**
     * JSON对象添加到Element对象
     *
     * @param json JSON对象
     * @param node Element对象
     * @return Element对象
     */
    public static Element addJsonToElement(JSONObject json, Element node) {
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Object child = json.get(key);
            if (child instanceof JSONObject) {
                //带币种的特殊处理
                if (((JSONObject) child).containsKey(KEY_CCY) && ((JSONObject) child).containsKey(KEY_VALUE)) {
                    Element element = node.addElement(key);
                    element.setText(json.getJSONObject(key).getString(KEY_VALUE) == null ? "" : json.getJSONObject(key).getString(KEY_VALUE));
                    element.addAttribute(KEY_CCY, json.getJSONObject(key).getString(KEY_CCY));
//                    node.add(element);
                } else {
//                    Element nodeChild = DocumentHelper.createElement(key);
//                    node.add(addJsonToElement(json.getJSONObject(key), nodeChild));
                    Element nodeChild = node.addElement(key);
                    addJsonToElement(json.getJSONObject(key), nodeChild);
                }

            } else if (child instanceof JSONArray) {
                //数组处理
                JSONArray jsonArray = (JSONArray) child;
                for (int i = 0; i < jsonArray.size(); i++) {
//                    Element nodeChild = DocumentHelper.createElement(key);
//                    JSONObject arrayChild = jsonArray.getJSONObject(i);
//                    node.add(addJsonToElement(arrayChild, nodeChild));
                    Element nodeChild = node.addElement(key);
                    Object object =  jsonArray.get(i);
                    if(object instanceof JSONObject) {
                        JSONObject arrayChild = jsonArray.getJSONObject(i);
                        addJsonToElement(arrayChild, nodeChild);
                    }else if(object instanceof JSONArray){
                        JSONArray arrayChild = jsonArray.getJSONArray(i);
//                        addJsonToElement(arrayChild, nodeChild);
                        //TODO
                    }else{
                        nodeChild.setText(jsonArray.getString(i));
                    }
                }
            } else {
                //字符串
//                Element element = DocumentHelper.createElement(key);
//                element.setText(json.getString(key) == null ? "" : json.getString(key));
//                node.add(element);
                Element element = node.addElement(key);
                element.setText(json.getString(key) == null ? "" : json.getString(key));
            }
        }

        return node;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json   JSON对象
     * @param header headerElement
     * @return Element headerElement
     */
    public static Element jsonToSoapHeader(JSONObject json, Element header) {
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Element element = DocumentHelper.createElement(SOAP_HEAD_PREFIX + key);
            element.setText(json.getString(key) == null ? "" : json.getString(key));
            header.add(element);
        }
        return header;
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws org.xml.sax.SAXException
     */
    public static Document jsonToDocument(JSONObject json, String encoding) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(encoding);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Element root = jsonToElement(json.getJSONObject(rootKey), rootKey);
            document.add(root);
        }
        return document;
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws org.xml.sax.SAXException
     */
    public static Document jsonToDocument(JSONObject json) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(ENCODING);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Element root = jsonToElement(json.getJSONObject(rootKey), rootKey);
            document.add(root);
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json     JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    public static Element jsonToElement(JSONObject json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);

        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Object child = json.get(key);
            if (child instanceof JSONObject) {
                node.add(jsonToElement(json.getJSONObject(key), key));
            } else {
                Element element = DocumentHelper.createElement(key);
                element.setText(json.getString(key) == null ? "" : json.getString(key));
                node.add(element);
            }
        }

        return node;
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws org.dom4j.DocumentException
     */
    public static JSONObject xmlToJson(String xml) throws DocumentException {
        JSONObject json = new JSONObject();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();

        json.put(root.getName(), elementToJson(root));

        return json;
    }

    /**
     * Element对象转JSON对象
     *
     * @param element Element对象
     * @return JSON对象
     */
    public static JSONObject elementToJson(Element element) {
        JSONObject json = new JSONObject();
        for (Object child : element.elements()) {
            Element e = (Element) child;
            if (e.elements().isEmpty()) {
                if (e.attributes().isEmpty()) {
                    json.put(e.getName(), e.getText());
                } else {
                    json.put(e.getName(), ElementWithAttrToJSON(e));
                }
            } else {
                json.put(e.getName(), elementToJson(e));
            }
        }

        return json;
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws java.io.IOException
     */
    public static String fileToString(URL filePath, String encoding) throws IOException {
        return IOUtils.toString(filePath, encoding);
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws java.io.IOException
     */
    public static String fileToString(String filePath, String encoding) throws IOException {
        return IOUtils.toString(Paths.get(filePath).toUri(), encoding);
    }

    /**
     * 字符串输出到文件
     *
     * @param str      字符串内容
     * @param filePath 文件路径
     * @throws java.io.IOException
     */
    public static void stringToFile(String str, String filePath, String encoding) throws IOException {
        FileUtils.writeStringToFile(Paths.get(filePath).toFile(), str, encoding);
    }

    /**
     * 字符串输出到文件
     *
     * @param str      字符串内容
     * @param filePath 文件路径
     * @throws java.io.IOException
     */
    public static void stringToFile(String str, URL filePath, String encoding) throws IOException {
        FileUtils.writeStringToFile(new File(filePath.getPath()), str, encoding);
    }

    /**
     * 字符串输出到文件
     *
     * @param str  字符串内容
     * @param file 输出文件
     * @throws java.io.IOException
     */
    public static void stringToFile(String str, File file, String encoding) throws IOException {
        FileUtils.writeStringToFile(file, str, encoding);
    }

}
