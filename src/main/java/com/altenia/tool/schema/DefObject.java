package com.altenia.tool.schema;

import java.util.HashMap;
import java.util.Map;

/**
 * Base of all objects in the schema.
 */
public class DefObject {
    protected String name;
    protected String comment;
    protected Map<String, Object> properties = new HashMap<>();

    public DefObject(){}

    public DefObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void putProperty(String key, Object value)
    {
        this.properties.put(key, value);
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }
}
