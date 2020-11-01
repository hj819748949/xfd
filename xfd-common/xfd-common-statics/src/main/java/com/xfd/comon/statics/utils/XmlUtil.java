package com.xfd.comon.statics.utils;

import java.io.File;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 * xml工具类
 * @author xfd
 * 
 * 2020年5月7日
 *
 */
public class XmlUtil
{
	private XmlUtil () { }
	
	/**
	 * 获取xml文档
	 * @param xmlPath
	 * @return
	 */
	public static Document getDocument(InputStream xmlStream) throws Exception
	{
		DocumentBuilderFactory buildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = buildFactory.newDocumentBuilder();
		return builder.parse(xmlStream);
	}
	/**
	 * 创建doc
	 * @return
	 * @throws Exception
	 */
	public static Document createDocument() throws Exception
	{
		DocumentBuilderFactory buildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = buildFactory.newDocumentBuilder();
		return builder.newDocument();
	}
	/**
	 * 写入xml
	 * @param doc
	 * @param xmlName
	 * @throws Exception
	 */
	public static void writeToXml(Document doc, String xmlName) throws Exception
	{
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer tf = factory.newTransformer();
		// 处理生成xml文件时, 进行格式化, 不需要处理
		// tf.setOutputProperty(OutputKeys.INDENT, "yes");      
		tf.transform(new DOMSource(doc), new StreamResult(new File(xmlName)));
	}
}
