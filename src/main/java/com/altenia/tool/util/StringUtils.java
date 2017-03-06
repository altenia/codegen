package com.altenia.tool.util;

/**
 * Created by ysahn on 3/5/2017.
 */
public class StringUtils {

    public static String titleCase(String str) {

        if (str.length() == 0)
            return "";
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
