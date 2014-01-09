package org.iq.util.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.iq.exception.UtilityException;
import org.iq.util.BaseUtil;
import org.iq.util.file.FileUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Sam
 * 
 */
public class XmlUtil extends BaseUtil {

  /**
   * @param xmlContent
   * @return Document
   * @throws UtilityException
   */
  public static Document getXmlDocument(String xmlContent)
      throws UtilityException {
    try {
      DocumentBuilder builder =
          DocumentBuilderFactory.newInstance().newDocumentBuilder();
      InputStream xmlStream =
          new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
      return builder.parse(xmlStream);
    }
    catch (ParserConfigurationException e) {
      throw new UtilityException("Error converting string to xml document",
          e);
    }
    catch (SAXException e) {
      throw new UtilityException("Error converting string to xml document",
          e);
    }
    catch (IOException e) {
      throw new UtilityException("Error converting string to xml document",
          e);
    }
  }

  /**
   * @param file
   * @return Document
   * @throws UtilityException
   */
  public static Document getXmlDocument(File file) throws UtilityException {
    return getXmlDocument(FileUtil.getFileContent(file));
  }
  
  /**
   * @param xmlDoc
   * @return String
   * @throws UtilityException
   */
  public static String getFormattedString(Document xmlDoc)
      throws UtilityException {
    try {
      DOMSource domSource = new DOMSource(xmlDoc);
      StreamResult result = new StreamResult(new StringWriter());
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.transform(domSource, result);
      return result.getWriter().toString();
    }
    catch (TransformerException e) {
      throw new UtilityException("Error formatting xmlDoc to string", e);
    }
  }

  /**
   * Returns only the valid child nodes of a node. Eliminates the nodes from
   * the list of child nodes of the input node if the child node type is same
   * with any of <code>ignoreNodeTypes</code>
   * 
   * @param node
   * @param maintainOrder
   * @param ignoreNodeTypes
   * @return HashSet<Node>
   */
  public static HashSet<Node> getValidChildNodes(Node node,
      boolean maintainOrder, short... ignoreNodeTypes) {
    NodeList childNodes = node.getChildNodes();
    HashSet<Node> filteredChildNodes = new HashSet<Node>();
    if (maintainOrder) {
      filteredChildNodes = new LinkedHashSet<Node>();
    }
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node thisNode = childNodes.item(i);
      boolean allowAdd = true;
      for (int j = 0; j < ignoreNodeTypes.length; j++) {
        if (ignoreNodeTypes[j] == thisNode.getNodeType()) {
          allowAdd = false;
          break;
        }
      }
      if (allowAdd) {
        filteredChildNodes.add(thisNode);
      }
    }
    return filteredChildNodes;
  }
  
  /**
   * @param xmlDoc
   * @param xPathStr
   * @return Node
   * @throws UtilityException
   */
  public static Node getNodeXPath(Document xmlDoc, String xPathStr)
      throws UtilityException {
    try {
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression expression = xPath.compile(xPathStr);
      return (Node)expression.evaluate(xmlDoc, XPathConstants.NODE);
    }
    catch (XPathExpressionException e) {
      throw new UtilityException("Error getting node from xpath string", e);
    }
  }

  /**
   * @param xmlDoc
   * @param xPathStr
   * @return Node
   * @throws UtilityException
   */
  public static NodeList getNodeListXPath(Document xmlDoc, String xPathStr)
      throws UtilityException {
    try {
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression expression = xPath.compile(xPathStr);
      return (NodeList)expression.evaluate(xmlDoc, XPathConstants.NODESET);
    }
    catch (XPathExpressionException e) {
      throw new UtilityException("Error getting node from xpath string", e);
    }
  }

  /**
   * @param xmlDoc
   * @param xPathStr
   * @return Node
   * @throws UtilityException
   */
  public static String getStringXPath(Document xmlDoc, String xPathStr)
      throws UtilityException {
    try {
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression expression = xPath.compile(xPathStr);
      return (String)expression.evaluate(xmlDoc, XPathConstants.STRING);
    }
    catch (XPathExpressionException e) {
      throw new UtilityException("Error getting node from xpath string", e);
    }
  }

  /**
   * @param node
   */
  public static void removeChildNodes(Node node, short... ignoreNodeTypes) {
    HashSet<Node> childNodesSet =
        getValidChildNodes(node, false, ignoreNodeTypes);
    for (Node childNode : childNodesSet) {
      node.removeChild(childNode);
    }
  }
}