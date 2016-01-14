package com.talosdev.movies.util;

import java.util.Set;

/**
 * Created by apapad on 2016-01-14.
 */
public class StringUtils {

    /**
     * Creates a multiline String with the entries of a Set<String>.
     * @param set
     * @return
     */
    public static String convertSetToMultilineString(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        for (String table: set) {
            sb.append("\n" + table);
        }
        return sb.toString();
    }
}
