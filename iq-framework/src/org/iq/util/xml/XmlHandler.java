package org.iq.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 */
public abstract class XmlHandler<T> extends DefaultHandler {

  private boolean parsed = false;
  protected T object = null;
  protected File xmlFile = null;
  protected StringBuffer xmlTextContent = new StringBuffer();

  /**
   * 
   */
  public XmlHandler(File xmlFileInput) {
	this.xmlFile = xmlFileInput;
  }

  /**
   * @return the object
   */
  final public T getObject() {
	if (!parsed) {
	  parseTemplate(this.xmlFile);
	  parsed = true;
	}
	return object;
  }

  private void parseTemplate(File xmlFileInput) {
	SAXParserFactory spf = SAXParserFactory.newInstance();
	SAXParser sp;
	try {
	  sp = spf.newSAXParser();
	  sp.parse(new FileInputStream(xmlFileInput), this);
	}
	catch (ParserConfigurationException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (SAXException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }

  /*
   * (non-Javadoc)
   * 
   * @see iq.html.generator.BaseHandler#startDocument()
   */
  @Override
  public void startDocument() throws SAXException {
	startXmlDocument();
  }

  /**
   * @throws SAXException
   */
  public abstract void startXmlDocument() throws SAXException;

  /*
   * (non-Javadoc)
   * 
   * @see iq.html.generator.BaseHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String uri, String localName, String qName,
	  Attributes attributes) throws SAXException {
	xmlTextContent.delete(0, xmlTextContent.length());
	startXmlElement(uri, localName, qName, attributes);
  }

  /**
   * @param uri
   * @param localName
   * @param qName
   * @param attributes
   * @throws SAXException
   */
  public abstract void startXmlElement(String uri, String localName,
	  String qName, Attributes attributes) throws SAXException;

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
   */
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
	xmlTextContent.append(ch, start, length);
  }

  /*
   * (non-Javadoc)
   * 
   * @see iq.html.generator.BaseHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void endElement(String uri, String localName, String qName)
	  throws SAXException {
	endXmlElement(uri, localName, qName);
  }

  /**
   * @param uri
   * @param localName
   * @param qName
   * @throws SAXException
   */
  public abstract void
	  endXmlElement(String uri, String localName, String qName)
		  throws SAXException;

  /*
   * (non-Javadoc)
   * 
   * @see iq.html.generator.BaseHandler#startDocument()
   */
  @Override
  public void endDocument() throws SAXException {
	endXmlDocument();
  }

  /**
   * @throws SAXException
   */
  public abstract void endXmlDocument() throws SAXException;
}