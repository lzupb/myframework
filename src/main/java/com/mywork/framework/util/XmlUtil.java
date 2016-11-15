package com.mywork.framework.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @ xml工具类
 * */
public class XmlUtil {
	private XmlUtil() {
	};

	private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	// from object 2 xml
	@SuppressWarnings("restriction")
	public static Object Xml2Object(Class<?> clazz, File file)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller localUnmarshaller = context.createUnmarshaller();
		return localUnmarshaller.unmarshal(file);
		// return null;
	};

	public static Object Xml2Object(Class<?> clazz, File file, String schema)
			throws SAXException, JAXBException {
		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema sch = sf.newSchema(new File(schema));
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(file);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	// xml 2 object
	public static Object Xml2Object(Class<?> clazz, InputStream is)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller localUnmarshaller = context.createUnmarshaller();
		return localUnmarshaller.unmarshal(is);
		// return null;
	};

	public static Object Xml2Object(Class<?> clazz, InputStream is,
			String schema) throws SAXException, JAXBException {
		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema sch = sf.newSchema(new File(schema));
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(is);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	// 使用schema的url来验证jaxb
	public static Object Xml2Object(Class<?> clazz, InputStream is, URL schema)
			throws SAXException, JAXBException {

		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			// InputSource inSource=new InputSource(schema);
			// SAXSource sax=new SAXSource(inSource);
			Schema sch = sf.newSchema(schema);
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(is);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	// 通过流来判断
	public static Object Xml2Object(Class<?> clazz, InputStream is,
			InputStream schema) throws SAXException, JAXBException {
		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputSource inSource = new InputSource(schema);
			SAXSource sax = new SAXSource(inSource);
			Schema sch = sf.newSchema(sax);
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(is);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	// xml 2 object
	public static Object XML2Obejct(Class<?> clazz, Reader reader)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] { clazz });
		Unmarshaller localMarshaller = context.createUnmarshaller();
		return localMarshaller.unmarshal(reader);
	};

	public static Object XML2Object(Class<?> clazz, Reader reader,
			InputStream schema) throws SAXException, JAXBException {
		// ss
		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputSource inSource = new InputSource(schema);
			SAXSource sax = new SAXSource(inSource);
			Schema sch = sf.newSchema(sax);
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(reader);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	public static Object XML2Obejct(Class<?> clazz, Reader reader, String schema)
			throws SAXException, JAXBException {
		Object obj = null;
		try {
			ValidationEventCollector vec = new ValidationEventCollector();
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema sch = sf.newSchema(new File(schema));
			JAXBContext jc = JAXBContext.newInstance(clazz.getPackage()
					.getName());
			Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(sch);
			u.setEventHandler(vec);
			obj = u.unmarshal(reader);
		} catch (SAXException e) {
			logger.error("error", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("error", e);
			throw e;
		}
		return obj;
	};

	// xml 2 object
	public static void Object2Xml(Object ob, OutputStream os)
			throws JAXBException {
		Class local = ob.getClass();
		JAXBContext context = JAXBContext.newInstance(new Class[] { local });
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(ob, os);
	};

	// from object 2 xml file
	public static void Object2XmlFile(Object ob, String path)
			throws JAXBException, FileNotFoundException {
		Class local = ob.getClass();
		JAXBContext context = JAXBContext.newInstance(new Class[] { local });
		Marshaller marshaller = context.createMarshaller();
		// marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
		// paramNamespacePrefixMapper);
		// marshaller.setProperty("jaxb.formatted.output",
		// Boolean.valueOf(true));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(ob, new FileOutputStream(new File(path)));
	};

}