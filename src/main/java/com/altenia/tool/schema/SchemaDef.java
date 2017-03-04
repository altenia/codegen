package com.altenia.tool.schema;

import java.util.LinkedHashMap;

/**
 * Class that represents a schema, which is a collection of entities.
 */
public class SchemaDef extends DefObject {

    public LinkedHashMap<String, EntityDef> entities = new LinkedHashMap<>();

    public SchemaDef(String name)
    {
        super(name);
    }

    public SchemaDef(String name, LinkedHashMap<String, EntityDef> entities) {
        super(name);
        this.entities = entities;
    }

    public LinkedHashMap<String, EntityDef> getEntities() {
        return entities;
    }

    public void addEntity(EntityDef entity)
    {
        this.entities.put(entity.getName(), entity);
    }
}
