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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ysahn on 3/4/2017.
 */
public class JavaEntityGenlet extends Genlet {

    public List<CodeGeneration> generate(DefObject model) {

        List<CodeGeneration> codes = new ArrayList<>();

        if (model instanceof SchemaDef) {
            SchemaDef schema = (SchemaDef)model;

            schema.getEntities().entrySet().stream().forEach(
                    (entry) -> {
                        codes.add(this.generateEntity(entry.getValue()));
                    }
            );
        } else if (model instanceof EntityDef) {
            EntityDef entity = (EntityDef)model;
            codes.add(this.generateEntity(entity));
        }
        return codes;
    }

    protected CodeGeneration generateEntity(EntityDef entity)
    {
        String className = StringUtils.titleCase(entity.getNameCamelCase());
        TypeSpec.Builder entityClassBuilder = TypeSpec.classBuilder(className)
                .addJavadoc(CodeBlock.builder().add(GenUtils.standardComment("Entity class")).build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Entity.class);


        // Generate member variables
        for (FieldDef field: entity.getFields().values()) {
            entityClassBuilder.addField(buildFieldSpec(field));
        }

        // Generate default Constructor
        entityClassBuilder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC).build());

        // Generate Constructor
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);
        for (FieldDef field: entity.getFields().values()) {
            Type type = DataType.equivalentJavaType(field.getDataType().getType());
            constructorBuilder.addParameter(type, field.getNameCamelCase());
            constructorBuilder.addStatement("this.$N = $L", field.getNameCamelCase(), field.getNameCamelCase());
        }
        entityClassBuilder.addMethod(constructorBuilder.build());


        // Generate getters
        for (FieldDef field: entity.getFields().values()) {
            entityClassBuilder.addMethod(buildGetterSpec(field));
        }

        TypeSpec entityClass = entityClassBuilder.build();

        String packageName = entity.getName();
        JavaFile javaFile = JavaFile.builder(packageName, entityClass)
                .build();

        StringBuffer code = new StringBuffer();

        try {
            javaFile.writeTo(code);
        } catch (Exception e) {
            throw new IllegalStateException("Exception", e);
        }

        return new CodeGeneration(packageName.replace(".", File.separator) + File.separator + className + ".java", code.toString());
    }

    protected MethodSpec buildGetterSpec(FieldDef field)
    {
        String getterName = "get" + StringUtils.titleCase(field.getNameCamelCase());
        Type type = DataType.equivalentJavaType(field.getDataType().getType());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(getterName)
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addStatement("return this.$L", field.getNameCamelCase());
        return methodBuilder.build();
    }

    protected FieldSpec buildFieldSpec(FieldDef field)
    {
        Type type = DataType.equivalentJavaType(field.getDataType().getType());

        if (type == null) {
            throw new IllegalArgumentException("Unknown type " + field.getDataType().getType());
        }

        FieldSpec.Builder fieldBuilder = FieldSpec.builder(type, field.getNameCamelCase())
                    .addModifiers(Modifier.PRIVATE);

        ClassName entityClazz = ClassName.get("javax.persistence", "Column");
        fieldBuilder.addAnnotation(
                AnnotationSpec.builder(entityClazz).addMember("name", "$S", field.getName()).build()
        );

        if (field.isPrimaryKey()) {
            fieldBuilder.addAnnotation(Id.class);
        }
        if (field.isAutoIncrement()) {
            fieldBuilder.addAnnotation(AnnotationSpec.builder(GeneratedValue.class)
                    .addMember("strategy", "GenerationType.AUTO")
                    .build());
        }

        return fieldBuilder.build();
    }

}
