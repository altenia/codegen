package com.altenia.tool.codegen.genlet;

import com.altenia.tool.schema.DefObject;
import com.altenia.tool.schema.SchemaDef;

import java.util.List;

/**
 * Created by ysahn on 3/3/2017.
 */
public abstract class Genlet {

    /**
     * Generats the code from the scema
     * @param model
     * @return
     */
    public abstract List<CodeGeneration> generate(DefObject model);

}
