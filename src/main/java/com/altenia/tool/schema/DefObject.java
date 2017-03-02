package com.altenia.tool.schema;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ysahn on 3/1/2017.
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

    public Map<String, Object> getProperties() {
        return properties;
    }
}
