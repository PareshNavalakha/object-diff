package com.paresh.diff.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
