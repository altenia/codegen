package com.altenia.tool.codegen;

import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.Genlet;
import com.altenia.tool.reader.SchemaReader;
import com.altenia.tool.schema.SchemaDef;
import org.apache.commons.cli.*;

import java.util.List;
import java.util.Properties;

/**
 * Main Generator class
 */
public class CodeGenerator {

    public static void main( String[] args ) throws ParseException {
        Options options = new Options();
        options.addOption("c", true, "configuration");
        options.addOption("t", true, "target directory");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        if (cmd.getArgList() == null || cmd.getArgList().size() == 0)
        {
            System.out.println("Source file not specified");
            System.exit(-1);
        }

        Properties config = new Properties();
        config.setProperty("source", cmd.getArgList().get(0));
        config.setProperty("target", cmd.getOptionValue("t", "codegen"));

        CodeGenerator generator = new CodeGenerator();
        generator.generate(config);
    }

    CodeGenerator()
    {
    }

    void generate(Properties config)
    {
        try {
            SchemaReader reader = createReader("com.altenia.tool.reader.LiquibaseXmlReader");
            Genlet genlet = createGenlet("com.altenia.tool.codegen.genlet.javagen.JavaEntityGenlet");

            SchemaDef schema = reader.read(config.getProperty("source"), null);

            List<CodeGeneration> codes = genlet.generate(schema);

            for (CodeGeneration code : codes) {
                System.out.print(code.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: implement
    }

    SchemaReader createReader(String readerName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object instance = Class.forName(readerName).newInstance();

        SchemaReader reader = null;
        if (instance != null && (instance instanceof SchemaReader))
        {
            reader = (SchemaReader) instance;
        }

        return reader;
    }

    Genlet createGenlet(String genletName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object genletInstance = Class.forName(genletName).newInstance();

        Genlet genlet = null;
        if (genletInstance != null || (genletInstance instanceof Genlet))
        {
            genlet = (Genlet) genletInstance;
        }

        return genlet;
    }
}
