package com.altenia.tool.codegen;

import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.Genlet;
import com.altenia.tool.reader.SchemaReader;
import com.altenia.tool.schema.SchemaDef;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        Config config = new Config();
        String filepath = cmd.getOptionValue("c", "./gen-profile.json");
        try {
            config = loadConfig(filepath);
        } catch (IOException e) {
            System.out.println("Could not open file: " + filepath);
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }

        config.setSource(cmd.getArgList().get(0));
        config.setTarget(cmd.getOptionValue("t", "codegen"));

        CodeGenerator generator = new CodeGenerator();
        generator.generate(config);
    }

    CodeGenerator()
    {
    }

    void generate(Config config)
    {
        List< Map<String, Object> > genletEntries = config.getGenlets();

        if (genletEntries == null || genletEntries.size() == 0) {
            return;
        }

        SchemaReader reader = null;
        try {
            reader = createReader(config.getReaderClass());
        } catch (Exception e){
            throw new IllegalArgumentException("Could not instantiate reader " + config.getReaderClass());
        }

        for( Map<String, Object> genletEntry: genletEntries) {

            try {
                String genletName =  (String)genletEntry.get("genletClass");
                if (genletName == null) {
                    break;
                }
                Genlet genlet = createGenlet(genletName);

                SchemaDef schema = reader.read(config.getSource(), null);

                List<CodeGeneration> codes = genlet.generate(schema);

                for (CodeGeneration code : codes) {
                    System.out.print(code.getCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO: implement
    }

    static Config loadConfig(String filepath) throws IOException {
        //HashMap<String, Object> config = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File(filepath);
        return mapper.readValue(configFile, Config.class);
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
