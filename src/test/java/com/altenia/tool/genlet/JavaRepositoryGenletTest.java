package com.altenia.tool.genlet;

import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.javagen.JavaSpringRepositoryGenlet;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ysahn on 3/5/2017.
 */
public class JavaRepositoryGenletTest {

    @Test
    public void testGen_whenSnakeCase_thenGenEntity()
    {
        JavaSpringRepositoryGenlet genlet = new JavaSpringRepositoryGenlet();

        List<CodeGeneration> gens =  genlet.generate(SchemaTestHelper.mockEntitySnakeCase());

        System.out.println(gens.get(0).getCode());
        assertThat(gens).isNotNull();
    }

    @Test
    public void testGen_whenCamelCase_thenGenEntity()
    {
        JavaSpringRepositoryGenlet genlet = new JavaSpringRepositoryGenlet();

        List<CodeGeneration> gens =  genlet.generate(SchemaTestHelper.mockEntityCamelCase());

        System.out.println(gens.get(0).getCode());
        assertThat(gens).isNotNull();
    }


}
