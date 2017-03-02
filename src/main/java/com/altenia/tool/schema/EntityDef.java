package com.altenia.tool.schema;

import java.util.LinkedHashMap;

/**
 * Created by ysahn on 3/1/2017.
 */
public class EntityDef extends DefObject{

    private LinkedHashMap<String, FieldDef> fields;

    public EntityDef(){}

    public EntityDef(String name, LinkedHashMap<String, FieldDef> fields) {
        super(name);
        this.fields = fields;
    }

    public LinkedHashMap<String, FieldDef> getFields() {
        return fields;
    }
}
