package com.altenia.tool.reader;

import com.altenia.tool.schema.SchemaDef;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base class of all XML-based input reader.
 */
public abstract class XmlBasedReader extends SchemaReader {

    protected Document readXml(InputStream input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(input);
    }

    protected NodeList queryPath(Document doc, String pathExpression) throws XPathExpressionException {

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(pathExpression);

        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        return nl;
    }

}
