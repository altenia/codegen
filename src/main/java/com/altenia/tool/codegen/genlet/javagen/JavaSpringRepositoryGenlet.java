package com.altenia.tool.codegen.genlet.javagen;

import com.altenia.tool.codegen.GenUtils;
import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.Genlet;
import com.altenia.tool.schema.*;
import com.altenia.tool.util.StringUtils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ysahn on 3/4/2017.
 */
public class JavaSpringRepositoryGenlet extends Genlet {

    public List<CodeGeneration> generate(DefObject model) {

        List<CodeGeneration> codes = new ArrayList<>();

        if (model instanceof SchemaDef) {
            SchemaDef schema = (SchemaDef)model;

            schema.getEntities().entrySet().stream().forEach(
                    (entry) -> {
                        codes.add(this.generateFromEntity(entry.getValue()));
                    }
            );
        } else if (model instanceof EntityDef) {
            EntityDef entity = (EntityDef)model;
            codes.add(this.generateFromEntity(entity));
        }
        return codes;
    }

    protected CodeGeneration generateFromEntity(EntityDef entity)
    {
        ClassName jpaRepositoryClazz = ClassName.get("org.springframework.data.jpa.repository", "JpaRepository");

        String className = StringUtils.titleCase(entity.getNameCamelCase()+"Repo");
        TypeSpec.Builder interfaceBuilder = TypeSpec.interfaceBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(CodeBlock.builder().add("Generated code").build())
                .addSuperinterface(jpaRepositoryClazz)
                ;
                //.addSuperinterface(JpaRepository.class);

        // Generate finders
        for (FieldDef field: entity.getFields().values()) {

            if (field.isUnique()) {
                interfaceBuilder.addMethod(buildFinderSpec(entity, field));
            }
        }

        TypeSpec entityClass = interfaceBuilder.build();

        String packageName = entity.getName();
        JavaFile javaFile = JavaFile.builder(packageName, entityClass)
                .build();

        StringBuffer code = new StringBuffer();

        try {
            javaFile.writeTo(code);
        } catch (Exception e) {
            throw new IllegalStateException("Exception", e);
        }

        return new CodeGeneration(packageName + entity.getNameCamelCase(), code.toString());
    }

    protected MethodSpec buildFinderSpec(EntityDef entity, FieldDef field)
    {
        ClassName entityClazz = ClassName.get(entity.getName(), StringUtils.titleCase(entity.getNameCamelCase()));

        String finderName = "findBy" + StringUtils.titleCase(field.getNameCamelCase());
        Type paramType = DataType.equivalentJavaType(field.getDataType().getType());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(finderName)
                .addJavadoc(CodeBlock.builder().add(GenUtils.standardComment("Controller class")).build())
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(entityClazz)
                .addParameter(paramType, field.getNameCamelCase());

        return methodBuilder.build();
    }

}
