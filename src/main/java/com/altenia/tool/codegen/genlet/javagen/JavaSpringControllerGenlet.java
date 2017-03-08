package com.altenia.tool.codegen.genlet.javagen;

import com.altenia.tool.codegen.GenUtils;
import com.altenia.tool.codegen.genlet.CodeGeneration;
import com.altenia.tool.codegen.genlet.Genlet;
import com.altenia.tool.schema.*;
import com.altenia.tool.util.StringUtils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by ysahn on 3/4/2017.
 */
public class JavaSpringControllerGenlet extends Genlet {

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
        ClassName restControllerClazz = ClassName.get("org.springframework.web.bind.annotation", "RestController");
        ClassName requestMappingClazz = ClassName.get("org.springframework.web.bind.annotation", "RequestMapping");

        String className = StringUtils.titleCase(entity.getNameCamelCase()+"Controller");
        TypeSpec.Builder controllerBuilder = TypeSpec.classBuilder(className)
                .addJavadoc(CodeBlock.builder().add(GenUtils.standardComment("Controller class")).build())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(restControllerClazz)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/" + entity.getName()).build())
                ;
                //.addSuperinterface(JpaRepository.class);

        // Generate service member variable
        ClassName autowiredClazz = ClassName.get("org.springframework.beans.factory.annotation", "Autowired" );
        String serviceVarName = entity.getNameCamelCase()+"Service";
        ClassName serviceClazz = ClassName.get(entity.getName(), StringUtils.titleCase(serviceVarName) );
        controllerBuilder.addField(
                FieldSpec.builder(serviceClazz, serviceVarName)
                        .addAnnotation(autowiredClazz).build()
        );


        ClassName responseStatusClazz = ClassName.get("org.springframework.web.bind.annotation", "ResponseStatus");
        ClassName pathVariableClazz = ClassName.get("org.springframework.web.bind.annotation", "PathVariable");
        ClassName entityClazz = ClassName.get(entity.getName(), StringUtils.titleCase(entity.getNameCamelCase()));
        Optional<FieldDef> pkFieldOpt = entity.getFields().values().stream()
                .filter(field -> field.isPrimaryKey() ).findFirst( );

        if (!pkFieldOpt.isPresent()) {
            throw new IllegalArgumentException("There is no Primary Key field");
        }
        FieldDef pfField = pkFieldOpt.get();
        Type pkType = DataType.equivalentJavaType(pfField.getDataType().getType());

        // Generate GET one request method
        String fetchMethodName = "fetch" + StringUtils.titleCase(entity.getNameCamelCase());

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(fetchMethodName)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/{id}")
                        .addMember("method", "RequestMethod.GET").build())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(
                        pkType, pfField.getNameCamelCase()).addAnnotation(pathVariableClazz).build()
                )
                .returns(entityClazz)
                .addStatement("return " + serviceVarName + ".fetch(" +pfField.getNameCamelCase()+")");
        controllerBuilder.addMethod(methodBuilder.build());


        // Generate GET list request method
        String listMethodName = "fetchList" + StringUtils.titleCase(entity.getNameCamelCase());

        methodBuilder = MethodSpec.methodBuilder(listMethodName)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/")
                        .addMember("method", "RequestMethod.GET").build())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "criteria")
                .returns(ParameterizedTypeName.get(Collection.class, Object.class))
                .addStatement("return " + serviceVarName + ".fetchList(criteria)");
        controllerBuilder.addMethod(methodBuilder.build());


        // Generate POST request method
        String addName = "add" + StringUtils.titleCase(entity.getNameCamelCase());

        methodBuilder = MethodSpec.methodBuilder(addName)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/")
                        .addMember("method", "RequestMethod.POST").build())
                .addAnnotation(AnnotationSpec.builder(responseStatusClazz)
                        .addMember("value", "HttpStatus.CREATED").build())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(
                        entityClazz, entity.getNameCamelCase()).addAnnotation(pathVariableClazz).build())
                .returns(entityClazz)
                .addStatement("return " + serviceVarName + ".add(" + entity.getNameCamelCase() +")");
        controllerBuilder.addMethod(methodBuilder.build());


        // Generate PUT request method
        String updateMethodName = "update" + StringUtils.titleCase(entity.getNameCamelCase());

        methodBuilder = MethodSpec.methodBuilder(updateMethodName)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/{id}")
                        .addMember("method", "RequestMethod.PUT").build())
                .addModifiers(Modifier.PUBLIC)
                .returns(entityClazz)
                .addParameter(ParameterSpec.builder(
                        entityClazz, entity.getNameCamelCase()).addAnnotation(pathVariableClazz).build()
                )
                .addStatement("return " + serviceVarName + ".update(" + entity.getNameCamelCase() +")");
        controllerBuilder.addMethod(methodBuilder.build());


        // Generate DELETE request method
        String deleteMethodName = "delete" + StringUtils.titleCase(entity.getNameCamelCase());

        methodBuilder = MethodSpec.methodBuilder(deleteMethodName)
                .addAnnotation(AnnotationSpec.builder(requestMappingClazz).addMember("path", "$S", "/{id}")
                        .addMember("method", "RequestMethod.DELETE").build())
                .addAnnotation(AnnotationSpec.builder(responseStatusClazz)
                        .addMember("value", "HttpStatus.NO_CONTENT").build())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(
                        pkType, pfField.getNameCamelCase()).addAnnotation(pathVariableClazz).build()
                )
                .returns(entityClazz)
                .addStatement("return " + serviceVarName + ".delete(" + pfField.getNameCamelCase() +")");
        controllerBuilder.addMethod(methodBuilder.build());


        TypeSpec entityClass = controllerBuilder.build();

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

    protected MethodSpec buildFinderSpec(EntityDef entity, FieldDef field)
    {
        ClassName entityClazz = ClassName.get(entity.getName(), StringUtils.titleCase(entity.getNameCamelCase()));

        String finderName = "findBy" + StringUtils.titleCase(field.getNameCamelCase());
        Type paramType = DataType.equivalentJavaType(field.getDataType().getType());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(finderName)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(entityClazz)
                .addParameter(paramType, field.getNameCamelCase());

        return methodBuilder.build();
    }

}
