package com.altenia.tool.reader;

import com.altenia.tool.schema.SchemaDef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Base class of all schema reader
 */
public abstract class SchemaReader {

    public SchemaDef read(String inputFile, String pathExpression) throws FileNotFoundException {
        File file = new File(inputFile);
        InputStream fis = new FileInputStream(file);

        return read(fis, pathExpression);
    }

    public abstract SchemaDef read(InputStream input, String pathExpression);
}
