package com.altenia.tool.reader;

import com.altenia.tool.schema.SchemaDef;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

/**
 * Object of this class parses the Liquibase XML file and returns the normalized schema model.
 */
public class LiquibaseXmlReaderTest {

    @Test
    public void test()
    {
        LiquibaseXmlReader reader = new LiquibaseXmlReader();

        InputStream is = this.getClass().getResourceAsStream("/liquibase.sample.xml");
        SchemaDef schema = reader.read(is, null);

        assertThat(schema).isNotNull();
    }
}
