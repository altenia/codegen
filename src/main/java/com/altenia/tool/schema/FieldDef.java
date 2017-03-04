package com.altenia.tool.schema;

/**
 * Class that represents Field
 */
public class FieldDef extends DefObject {

    private DataType dataType;
    private boolean isNullable = false;
    private boolean isPrimaryKey = false;
    private boolean isUnique = false;
    private boolean isAutoIncrement = false;
    private Object defaultValue;
    private FieldDef foreignReferene;
    private String comment;

    public FieldDef(){}

    public FieldDef(String name){
        super(name);
    }

    public FieldDef(String name, DataType dataType, boolean isNullable, boolean isPrimaryKey, boolean isUnique, boolean isAutoIncrement, Object defaultValue, FieldDef foreignReferene, String comment) {
        this(name);
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

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public FieldDef getForeignReferene() {
        return foreignReferene;
    }

    public void setForeignReferene(FieldDef foreignReferene) {
        this.foreignReferene = foreignReferene;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    public static class FieldDefBuilder {
        private String name;
        private DataType dataType;
        private boolean isNullable;
        private boolean isPrimaryKey;
        private boolean isUnique;
        private boolean isAutoIncrement;
        private Object defaultValue;
        private FieldDef foreignReferene;
        private String comment;

        public FieldDefBuilder setName(String name) {
            this.name = name;
            return this;
        }

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
            return new FieldDef(name, dataType, isNullable, isPrimaryKey, isUnique, isAutoIncrement, defaultValue, foreignReferene, comment);
        }
    }
}
