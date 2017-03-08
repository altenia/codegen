package com.altenia.tool.codegen;

import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.Genlet;
import com.altenia.tool.reader.SchemaReader;
import com.altenia.tool.schema.SchemaDef;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        options.addOption("console", false, "output to console only");
        options.addOption("log", false, "output log");

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
        config.setOption("console", cmd.hasOption("console"));
        config.setOption("log", cmd.hasOption("log"));

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

        int filesCount = 0;
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
                    if (config.getBooleanOption("log", false)) {
                        System.out.print("Generating resource '" + code.getResourceName()+ "'");
                    }
                    if (config.getBooleanOption("console", false)) {
                        System.out.print(code.getCode());
                    } else {
                        if (config.getBooleanOption("log", false)) {
                            System.out.println(" into directory: " + config.getTarget());
                        }
                        output(config.getTarget(), code.getResourceName(), code.getCode());
                    }
                    filesCount++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (config.getBooleanOption("log", false)) {
            System.out.println("Completed generating " + filesCount + " files.");
        }
    }

    static void output(String basePath, String resourceName, String content)
    {
        /*
        Path currentRelativePath = Paths.get("");
        String cwd = currentRelativePath.toAbsolutePath().toString();
        */

        File basePathFile = new File(basePath);
        if (!basePathFile.isDirectory())
            throw new IllegalArgumentException("Path " + basePath +" is not a valid directory");

        basePath = ((basePath != null) ? basePath : "");
        if (basePath.trim().length() > 0 && !basePath.endsWith(File.separator)) {
            basePath += File.separator;
        }

        //String resourePath = resourceName.replace(".", File.separator);
        String fullPath = basePath + resourceName;
        File file = new File(fullPath);

        try {
            file.getParentFile().mkdirs();

            PrintWriter writer = new PrintWriter(file);
            writer.print(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
