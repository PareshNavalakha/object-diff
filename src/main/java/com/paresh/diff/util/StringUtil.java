package com.paresh.diff.util;

/**
 * Created by Admin on 27-06-2017.
 */
public class StringUtil {
    private StringUtil()
    {}
    public static String getHumanReadableNameFromCamelCase(String string) {
        return string.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
