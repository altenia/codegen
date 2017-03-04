package com.altenia.tool.reader;

import com.altenia.tool.schema.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ysahn on 3/1/2017.
 */
public class LiquibaseXmlReader extends XmlBasedReader {

    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_CURRENCY = "currency";
    public static final String TYPE_CLOB = "clob";
    public static final String TYPE_BLOB = "blob";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATETIME = "datetime";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_DECIMAL = "decimal";
    public static final String TYPE_BIGINT = "bigint";
    public static final String TYPE_SHORT = "short";
    public static final String TYPE_VARCHAR = "varchar";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_CHAR = "char";

    public LiquibaseXmlReader(){}

    @Override
    public SchemaDef read(InputStream input, String pathExpression)
    {
        if (pathExpression == null) {
            pathExpression = "/databaseChangeLog/changeSet/createTable";
        }
        SchemaDef model = new SchemaDef(null);

        try {
            Document doc = super.readXml(input);
            NodeList nodeList = super.queryPath(doc, pathExpression);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                switch(currentNode.getNodeType()) {
                    case Node.ELEMENT_NODE:
                        Element elem = (Element) currentNode;
                        if ("createTable".equals(elem.getTagName()) ) {
                            EntityDef entity = this.processCreateTableEl(elem);
                            model.addEntity(entity);
                        }
                }
            }

        } catch (SAXException se) {
            throw new IllegalArgumentException(se.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }


        return model;
    }

    private EntityDef processCreateTableEl(Element el) {
        String attrib = el.getAttribute("tableName");
        EntityDef entitySpec = new EntityDef(attrib);

        attrib = el.getAttribute("remarks");
        entitySpec.setComment(attrib);

        attrib = el.getAttribute("catalogName");
        if (attrib != null) {
            entitySpec.putProperty(EntityDef.PROP_CATALOG, attrib);
        }
        attrib = el.getAttribute("schemaName");
        if (attrib != null) {
            entitySpec.putProperty(EntityDef.PROP_SCHEMA, attrib);
        }
        attrib = el.getAttribute("tablespace");
        if (attrib != null) {
            entitySpec.putProperty(EntityDef.PROP_TABLESPACE, attrib);
        }

        NodeList nodeList = el.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) currentNode;
                if ("column".equals(elem.getTagName())) {
                    entitySpec.addField(this.processColumnEl(elem));
                } else {
                    throw new IllegalArgumentException("Unexpected element "+elem.getTagName()+" found.");
                }
            }
        }

        return entitySpec;
    }

    private FieldDef processColumnEl(Element el)
    {
        String attrib = el.getAttribute("name");
        FieldDef fieldSpec = new FieldDef(attrib);

        attrib = el.getAttribute("type");
        if (attrib != null) {
            fieldSpec.setDataType(this.parseDataType(attrib));
        }
        attrib = el.getAttribute("defaultValue");
        if (attrib != null) {
            fieldSpec.setDefaultValue(attrib);
        }
        attrib = el.getAttribute("autoIncrement");
        if (attrib != null) {
            fieldSpec.setAutoIncrement(Boolean.parseBoolean(attrib));
        }

        attrib = el.getAttribute("remarks");
        if (attrib != null) {
            fieldSpec.setComment(attrib);
        }

        return fieldSpec;
    }

    private DataType parseDataType(String typeStr)
    {
        String typeStrLower = typeStr.trim().toLowerCase();
        if (typeStrLower.length() == 0) {
            throw new IllegalArgumentException("Data type not specified");
        }
        DataType dataType = new DataType();
        if (typeStrLower.startsWith(TYPE_BOOLEAN)) {
            dataType.setType(DataType.TYPE_BOOLEAN);
        } else if (typeStrLower.startsWith(TYPE_BIGINT)) {
            dataType.setType(DataType.TYPE_BIGINT);
        } else if (typeStrLower.startsWith(TYPE_BLOB)) {
            dataType.setType(DataType.TYPE_BLOB);
        } else if (typeStrLower.startsWith(TYPE_CHAR)) {
            dataType.setType(DataType.TYPE_CHAR);
        } else if (typeStrLower.startsWith(TYPE_CLOB)) {
            dataType.setType(DataType.TYPE_CLOB);
        } else if (typeStrLower.startsWith(TYPE_CURRENCY)) {
            dataType.setType(DataType.TYPE_CURRENCY);
        } else if (typeStrLower.startsWith(TYPE_DATE)) {
            dataType.setType(DataType.TYPE_DATE);
        } else if (typeStrLower.startsWith(TYPE_DATETIME)) {
            dataType.setType(DataType.TYPE_DATETIME);
        } else if (typeStrLower.startsWith(TYPE_DECIMAL)) {
            dataType.setType(DataType.TYPE_DECIMAL);
        } else if (typeStrLower.startsWith(TYPE_INT)) {
            dataType.setType(DataType.TYPE_INT);
        } else if (typeStrLower.startsWith(TYPE_LONG)) {
            dataType.setType(DataType.TYPE_LONG);
        } else if (typeStrLower.startsWith(TYPE_NUMBER)) {
            dataType.setType(DataType.TYPE_NUMBER);
        } else if (typeStrLower.startsWith(TYPE_SHORT)) {
            dataType.setType(DataType.TYPE_SHORT);
        } else if (typeStrLower.startsWith(TYPE_TIME)) {
            dataType.setType(DataType.TYPE_TIME);
        } else if (typeStrLower.startsWith(TYPE_VARCHAR)) {
            dataType.setType(DataType.TYPE_VARCHAR);
        }
        return dataType;
    }
}
