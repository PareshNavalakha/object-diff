package com.paresh.diff.config;

import java.lang.reflect.Method;

public interface ClassMetaDataConfiguration {
    boolean isIgnoreMethod(Method method);

    Method getIdentifierMethod(Class clazz);

    String getClassDescription(Class clazz);

    String getMethodDescription(Method method);
}
