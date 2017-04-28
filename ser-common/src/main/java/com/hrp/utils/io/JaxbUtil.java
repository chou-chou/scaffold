package com.hrp.utils.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.hrp.utils.lang.StringUtil;

/**
 * 工具类-》IO处理工具类-》JAXBUtils
 */
public final class JaxbUtil {
	private JaxbUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * convertXmlFile2Java
	 * 
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static Object convertXmlFile2Java(final Class<?> c, final String filePath, final boolean namespaceAware) throws JAXBException, SAXException, ParserConfigurationException {
		Object obj = null;
		JAXBContext ctx = JAXBContext.newInstance(c);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		SAXParserFactory sax = SAXParserFactory.newInstance();
		sax.setNamespaceAware(namespaceAware);  // 忽略命名空间 true不忽略 false忽略
		XMLReader xmlReader = sax.newSAXParser().getXMLReader();
		Source source = new SAXSource(xmlReader, new InputSource(filePath));
		obj = unmarshaller.unmarshal(source);
		return obj;
	}

	/**
	 * convertXmlStr2Java
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static Object convertXmlStr2Java(final Class<?> c, final String xmlStr, final boolean namespaceAware) throws JAXBException, SAXException, ParserConfigurationException {
		Object obj = null;
		JAXBContext ctx = JAXBContext.newInstance(c);
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
		SAXParserFactory sax = SAXParserFactory.newInstance();
		sax.setNamespaceAware(namespaceAware);  // 忽略命名空间 true不忽略 false忽略
		XMLReader xmlReader = sax.newSAXParser().getXMLReader();
		Source source = new SAXSource(xmlReader, new InputSource(is));
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		obj = unmarshaller.unmarshal(source);
		return obj;
	}

	/**
	 * convertJava2XmlStr
	 * 
	 * @throws JAXBException
	 */
	public static String convertJava2XmlStr(final Object originalObj, final boolean formated, final String xmlCharset, final boolean isFragment) throws JAXBException {
		String xmlStr = "";
		JAXBContext ctx;
		ctx = JAXBContext.newInstance(originalObj.getClass());
		Marshaller marshaller = ctx.createMarshaller();
		if (!StringUtil.isEmpty(xmlCharset)) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, xmlCharset);
		}
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formated);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(originalObj, os);
		xmlStr = os.toString();
		return xmlStr;
	}

	/**
	 * convertJava2XmlFile
	 * 
	 * @throws JAXBException
	 */
	public static void convertJava2XmlFile(final Object originalObj, final boolean formated, final String xmlCharset, final boolean isFragment, final String filePath) throws JAXBException {
		JAXBContext ctx;
		ctx = JAXBContext.newInstance(originalObj.getClass());
		Marshaller marshaller = ctx.createMarshaller();
		if (!StringUtil.isEmpty(xmlCharset)) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, xmlCharset);
		}
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formated);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment);
		marshaller.marshal(originalObj, new File(filePath));
	}

	/**
	 * validate 根据schema校验实例文档
	 */
	public static void validate(final Object originalObj, final String schemaPath) throws JAXBException {
		Schema mySchema;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			mySchema = sf.newSchema(new File(schemaPath));
		} catch (SAXException saxe) {
			// ...(error handling)
			mySchema = null;
		}
		JAXBContext jc = JAXBContext.newInstance(originalObj.getClass());
		Unmarshaller u = jc.createUnmarshaller();
		u.setSchema(mySchema);
		ValidationEventCollector vec = new ValidationEventCollector();
		u.setEventHandler(vec);
		if (vec != null && vec.hasEvents()) {
			for (ValidationEvent ve : vec.getEvents()) {
				String msg = ve.getMessage();
				ValidationEventLocator vel = ve.getLocator();
				int line = vel.getLineNumber();
				int column = vel.getColumnNumber();
				System.err.println(line + "." + column + ": " + msg);
			}
		}
	}
}
