package com.altenia.tool.codegen.genlet;

/**
 * Class that represents the generated code and it's metadata.
 */
public class CodeGeneration {

    private String resourceName;

    private String code;


    public CodeGeneration(String resourceName, String code) {
        this.resourceName = resourceName;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getResourceName() {
        return resourceName;
    }
}
