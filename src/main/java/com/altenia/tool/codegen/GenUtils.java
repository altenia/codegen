package com.altenia.tool.codegen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ysahn on 3/5/2017.
 */
public class GenUtils {

    public static final String standardComment(String resourceName)
    {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());


        return "Generated code for " + resourceName + "\n"
            + "@date " + nowAsISO + "\n";
    }
}
