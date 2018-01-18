package com.paresh.diff.util;

import com.paresh.diff.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    private static String BASE_PACKAGE = "java.";

    private ReflectionUtil() {
    }

    public static boolean isInstanceOfCollection(Object object) {
        return object instanceof Collection;
    }

    public static boolean isInstanceOfMap(Object object) {
        return object instanceof Map;
    }

    private static boolean isGetterMethod(Method method) {
        if (!method.getName().startsWith("get") || method.getName().equals("getClass")) {
            return false;
        }

        return method.getParameterCount() == 0 && !Void.class.equals(method.getReturnType());

    }

    public static List<Method> fetchAllGetterMethods(Class clazz) {
        List<Method> methods = new LinkedList<>();
        for (Method method : clazz.getMethods()) {
            if (isGetterMethod(method) && !DiffComputeEngine.getInstance().getClassMetaDataConfiguration().isIgnoreMethod(method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    public static boolean isBaseClass(Class clazz) {
        return clazz.isPrimitive() || (clazz.getPackage() != null && clazz.getPackage().getName().startsWith(BASE_PACKAGE));
    }

    public static Object getMethodResponse(Method method, Object object) {
        Object methodResponse = null;

        if (object != null && method != null) {

            try {
                methodResponse = method.invoke(object, (Object[]) null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("Could not execute method", e);
            }
            if (methodResponse == null && object.getClass().equals(String.class)) {
                return Constants.BLANK;
            }
        }
        return methodResponse;
    }

    public static Class getCollectionElementClass(Object object) {
        Collection collection = (Collection) object;
        if (collection != null && !collection.isEmpty()) {
            Iterator iterator = collection.iterator();
            return iterator.next().getClass();
        }
        return null;
    }

    public static Class getCollectionElementClass(Object before, Object after) {
        Class clazz = null;
        if (before != null) {
            clazz = getCollectionElementClass(before);
        }
        if (clazz == null && after != null) {
            clazz = getCollectionElementClass(after);
        }
        return clazz;
    }

}