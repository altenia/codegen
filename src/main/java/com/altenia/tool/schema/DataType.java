package com.altenia.tool.schema;

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
}
