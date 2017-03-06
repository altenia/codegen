package com.altenia.tool.genlet;

import com.altenia.tool.schema.DataType;
import com.altenia.tool.schema.EntityDef;
import com.altenia.tool.schema.FieldDef;

/**
 * Created by ysahn on 3/5/2017.
 */
public class SchemaTestHelper {

    public static EntityDef mockEntitySnakeCase()
    {
        EntityDef mock = new EntityDef( "test_thing");
        mock.addField(
                new FieldDef.FieldDefBuilder()
                        .setName("test_id_long")
                        .setDataType(new DataType(DataType.TYPE_LONG))
                        .setIsPrimaryKey(true)
                        .setIsAutoIncrement(true)
                        .createFieldDef()
        );
        mock.addField(
                new FieldDef.FieldDefBuilder()
                        .setName("test_field_varchar")
                        .setDataType(new DataType(DataType.TYPE_VARCHAR))
                        .setIsUnique(true)
                        .createFieldDef()
            );

        return mock;
    }

    public static EntityDef mockEntityCamelCase()
    {
        EntityDef mock = new EntityDef( "test_thing");
        mock.addField(
                new FieldDef.FieldDefBuilder()
                        .setName("testIdLong")
                        .setDataType(new DataType(DataType.TYPE_LONG))
                        .setIsPrimaryKey(true)
                        .setIsAutoIncrement(true)
                        .createFieldDef()
        );
        mock.addField(
                new FieldDef.FieldDefBuilder()
                        .setName("testFieldVarchar")
                        .setDataType(new DataType(DataType.TYPE_VARCHAR))
                        .setIsUnique(true)
                        .createFieldDef()
        );

        return mock;
    }
}
