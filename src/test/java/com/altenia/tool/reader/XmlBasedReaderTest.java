package com.altenia.tool.reader;

import com.altenia.tool.schema.SchemaDef;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

/**
 * XmlBasedReaderTest.
 */
public class XmlBasedReaderTest {

    @Test
    public void testReadXml_whenValidXmlProvided_returnDocument() throws IOException, SAXException, ParserConfigurationException {
        MockReader reader = new MockReader();

        InputStream is = this.getClass().getResourceAsStream("/liquibase.sample.xml");
        Document doc = reader.readXmlStub(is);

        assertThat(doc.getFirstChild().getNodeName()).isEqualTo("databaseChangeLog");
    }

    @Test
    public void testQueryPath_whenQueryCreateTable_returnTwoNodes() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        MockReader reader = new MockReader();

        InputStream is = this.getClass().getResourceAsStream("/liquibase.sample.xml");

        Document doc = reader.readXmlStub(is);
        NodeList nl = reader.queryPathStub(doc, "/databaseChangeLog/changeSet/createTable");

        assertThat(nl.getLength()).isEqualTo(2);
        assertThat(nl.item(0).getNodeName()).isEqualTo("createTable");
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    private class MockReader extends XmlBasedReader {

        @Override
        public SchemaDef read(InputStream input, String pathExpression) {
            return null;
        }

        public Document readXmlStub(InputStream input) throws ParserConfigurationException, IOException, SAXException {
            return super.readXml(input);
        }

        public NodeList queryPathStub(Document doc, String pathExpression) throws XPathExpressionException {
            return super.queryPath(doc, pathExpression);
        }
    }

}
