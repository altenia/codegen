package com.altenia.tool.schema;

import java.util.LinkedHashMap;

/**
 * Created by ysahn on 3/1/2017.
 */
public class SchemaDef extends DefObject {

    public LinkedHashMap<String, EntityDef> entities;

    public SchemaDef(String name, LinkedHashMap<String, EntityDef> entities) {
        super(name);
        this.entities = entities;
    }

    public LinkedHashMap<String, EntityDef> getEntities() {
        return entities;
    }
}
