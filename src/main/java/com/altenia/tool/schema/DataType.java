package com.altenia.tool.schema;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class that represents DataType
 */
public class DataType {

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


    private String type;
    private Integer size;
    private Integer decimals;

    public DataType() {
    }

    public DataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    
    public static Type equivalentJavaType(String typeStr)
    {
        if (DataType.TYPE_BIGINT.equals(typeStr)) {
            return Long.class;
        } else if (DataType.TYPE_BLOB.equals(typeStr)) {
            return Byte[].class;
        } else if (DataType.TYPE_BOOLEAN.equals(typeStr)) {
            return Boolean.class;
        } else if (DataType.TYPE_CHAR.equals(typeStr)) {
            return Character.class;
        } else if (DataType.TYPE_CLOB.equals(typeStr)) {
            return String.class;
        } else if (DataType.TYPE_CURRENCY.equals(typeStr)) {
            return BigDecimal.class;
        } else if (DataType.TYPE_DATE.equals(typeStr)) {
            return Date.class;
        } else if (DataType.TYPE_DATETIME.equals(typeStr)) {
            return Date.class;
        } else if (DataType.TYPE_DECIMAL.equals(typeStr)) {
            return BigDecimal.class;
        } else if (DataType.TYPE_INT.equals(typeStr)) {
            return Integer.class;
        } else if (DataType.TYPE_LONG.equals(typeStr)) {
            return Long.class;
        } else if (DataType.TYPE_NUMBER.equals(typeStr)) {
            return Long.class;
        } else if (DataType.TYPE_SHORT.equals(typeStr)) {
            return Short.class;
        } else if (DataType.TYPE_TIME.equals(typeStr)) {
            return Date.class;
        } else if (DataType.TYPE_VARCHAR.equals(typeStr)) {
            return String.class;
        }
        return null;
    }
}
