package com.dcits.dcwlt.common.pay.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import java.util.Map;

/**
 * JSON对象与XML相互转换工具类
 * 
 *
 * @createDate 2019-11-18
 * @version 1.0.0
 *
 */
public class JsonXmlUtil {
	
	private static final String ENCODING = "UTF-8";

	private JsonXmlUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * JSON对象转漂亮的xml字符串
	 * 
	 * @param json
	 *            JSON对象
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
	 * @param json
	 *            JSON对象
	 * @return xml字符串
	 * @throws org.xml.sax.SAXException
	 */
	public static String jsonToXml(JSONObject json) {
		return jsonToDocument(json, ENCODING).asXML();
	}

	/**
	 * JSON对象转xml字符串
	 *
	 * @param json
	 *            JSON对象
	 * @return xml字符串
	 * @throws org.xml.sax.SAXException
	 */
	public static String jsonToXml(JSONObject json, String encoding) {
		return jsonToDocument(json, encoding).asXML();
	}

	/**
	 * JSON对象转Document对象
	 *
	 * @param json
	 *            JSON对象
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
	 * @param json
	 *            JSON对象
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
	 * @param json
	 *            JSON对象
	 * @param nodeName
	 *            节点名称
	 * @return Element对象
	 */
	public static Element jsonToElement(JSONObject json, String nodeName) {
		Element node = DocumentHelper.createElement(nodeName);

		for(Map.Entry<String, Object> entry : json.entrySet()) {
			String key = entry.getKey();
			Object child = json.get(key);
			if (child instanceof JSONObject) {
				node.add(jsonToElement(json.getJSONObject(key), key));
			}

			else {
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
	 * @param xml
	 *            xml字符串
	 * @return JSON对象
	 * @throws DocumentException
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
	 * @param element
	 *            Element对象
	 * @return JSON对象
	 */
	public static JSONObject elementToJson(Element element) {
		JSONObject json = new JSONObject();
		for (Object child : element.elements()) {
			Element e = (Element) child;
			if (e.elements().isEmpty()) {
				json.put(e.getName(), e.getText());
			}

			else {
				json.put(e.getName(), elementToJson(e));
			}
		}

		return json;
	}

	/**
	 * 文件内容转换成字符串
	 *
	 * @param filePath
	 *            文件路径
	 * @return 内容字符串
	 * @throws java.io.IOException
	 */
	public static String fileToString(URL filePath, String encoding) throws IOException {
		return IOUtils.toString(filePath, encoding);
	}

	/**
	 * 文件内容转换成字符串
	 *
	 * @param filePath
	 *            文件路径
	 * @return 内容字符串
	 * @throws java.io.IOException
	 */
	public static String fileToString(String filePath, String encoding) throws IOException {
		return IOUtils.toString(Paths.get(filePath).toUri(), encoding);
	}

	/**
	 * 字符串输出到文件
	 *
	 * @param str
	 *            字符串内容
	 * @param filePath
	 *            文件路径
	 * @throws java.io.IOException
	 */
	public static void stringToFile(String str, String filePath, String encoding) throws IOException {
		FileUtils.writeStringToFile(Paths.get(filePath).toFile(), str, encoding);
	}

	/**
	 * 字符串输出到文件
	 *
	 * @param str
	 *            字符串内容
	 * @param filePath
	 *            文件路径
	 * @throws java.io.IOException
	 */
	public static void stringToFile(String str, URL filePath, String encoding) throws IOException {
		FileUtils.writeStringToFile(new File(filePath.getPath()), str, encoding);
	}

	/**
	 * 字符串输出到文件
	 *
	 * @param str
	 *            字符串内容
	 * @param file
	 *            输出文件
	 * @throws java.io.IOException
	 */
	public static void stringToFile(String str, File file, String encoding) throws IOException {
		FileUtils.writeStringToFile(file, str, encoding);
	}

}
