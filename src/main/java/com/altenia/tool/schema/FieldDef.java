package com.altenia.tool.schema;

/**
 * Created by ysahn on 3/1/2017.
 */
public class FieldDef extends DefObject {

    enum DataType {
        String, Integer
    }

    private DataType dataType;
    private boolean isNullable = false;
    private boolean isPrimaryKey = false;
    private boolean isUnique = false;
    private boolean isAutoIncrement = false;
    private Object defaultValue;
    private FieldDef foreignReferene;
    private String comment;

    public FieldDef(){}

    public FieldDef(DataType dataType, boolean isNullable, boolean isPrimaryKey, boolean isUnique, boolean isAutoIncrement, Object defaultValue, FieldDef foreignReferene, String comment) {
        this.dataType = dataType;
        this.isNullable = isNullable;
        this.isPrimaryKey = isPrimaryKey;
        this.isUnique = isUnique;
        this.isAutoIncrement = isAutoIncrement;
        this.defaultValue = defaultValue;
        this.foreignReferene = foreignReferene;
        this.comment = comment;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public FieldDef getForeignReferene() {
        return foreignReferene;
    }

    public String getComment() {
        return comment;
    }


    public static class FieldDefBuilder {
        private DataType dataType;
        private boolean isNullable;
        private boolean isPrimaryKey;
        private boolean isUnique;
        private boolean isAutoIncrement;
        private Object defaultValue;
        private FieldDef foreignReferene;
        private String comment;

        public FieldDefBuilder setDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public FieldDefBuilder setIsNullable(boolean isNullable) {
            this.isNullable = isNullable;
            return this;
        }

        public FieldDefBuilder setIsPrimaryKey(boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
            return this;
        }

        public FieldDefBuilder setIsUnique(boolean isUnique) {
            this.isUnique = isUnique;
            return this;
        }

        public FieldDefBuilder setIsAutoIncrement(boolean isAutoIncrement) {
            this.isAutoIncrement = isAutoIncrement;
            return this;
        }

        public FieldDefBuilder setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public FieldDefBuilder setForeignReferene(FieldDef foreignReferene) {
            this.foreignReferene = foreignReferene;
            return this;
        }

        public FieldDefBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public FieldDef createFieldDef() {
            return new FieldDef(dataType, isNullable, isPrimaryKey, isUnique, isAutoIncrement, defaultValue, foreignReferene, comment);
        }
    }
}
