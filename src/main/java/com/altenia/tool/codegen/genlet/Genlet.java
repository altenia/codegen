package com.altenia.tool.codegen.genlet;

import com.altenia.tool.schema.SchemaDef;

/**
 * Created by ysahn on 3/3/2017.
 */
public abstract class Genlet {

    public abstract String generate(SchemaDef model);

}
