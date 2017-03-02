package com.altenia.tool.reader;

import com.altenia.tool.schema.SchemaDef;

import java.io.InputStream;

/**
 * Created by ysahn on 3/1/2017.
 */
public abstract class SchemaReader {
    public abstract SchemaDef read(InputStream input);
}
