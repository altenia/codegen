package com.altenia.tool.schema;

import java.util.LinkedHashMap;

/**
 * Class that represents Entity (a table)
 */
public class EntityDef extends DefObject{

    public static final String PROP_CATALOG = "catalogName";
    public static final String PROP_SCHEMA = "schemaName";
    public static final String PROP_TABLESPACE = "tablespace";

    private LinkedHashMap<String, FieldDef> fields = new LinkedHashMap<>();

    public EntityDef(){}

    public EntityDef(String name) {
        super(name);
    }

    public LinkedHashMap<String, FieldDef> getFields() {
        return fields;
    }

    public void addField(FieldDef fieldSpec)
    {
        fields.put(fieldSpec.getName(), fieldSpec);
    }
}
