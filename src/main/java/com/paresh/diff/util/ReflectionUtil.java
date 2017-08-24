package com.paresh.diff.util;

import com.paresh.diff.annotations.Description;
import com.paresh.diff.annotations.Identifier;
import com.paresh.diff.annotations.Ignore;
import com.paresh.diff.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    private ReflectionUtil()
    {}

    private static boolean isAnnotationPresent(Method method, Class clazz) {
        return clazz.isAnnotation() && method.isAnnotationPresent(clazz);
    }

    private static boolean isAnnotationPresent(Class baseClass, Class clazz) {
        return clazz.isAnnotation() && baseClass.isAnnotationPresent(clazz);
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
            if (isGetterMethod(method) && !isIgnoreMethod(method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private static boolean isIgnoreMethod(Method method) {
        return isAnnotationPresent(method, Ignore.class);
    }


    public static boolean isBaseClass(Class clazz) {
        String BASE_PACKAGE = "java.";
        return clazz.isPrimitive() || clazz.getPackage().getName().startsWith(BASE_PACKAGE);
    }

    public static String getDescription(Class clazz) {
        String returnValue;
        if (isAnnotationPresent(clazz, Description.class)) {
            Description description = (Description) clazz.getAnnotation(Description.class);
            returnValue = description.userFriendlyDescription();
        } else {
            returnValue = StringUtil.getHumanReadableNameFromCamelCase(clazz.getSimpleName());
        }
        return returnValue;
    }

    public static String getDescription(Method method) {
        String returnValue;
        if (isAnnotationPresent(method, Description.class)) {
            Description description = method.getAnnotation(Description.class);
            returnValue = description.userFriendlyDescription();
        } else {
            //Skipping get from the Getter Method
            returnValue = StringUtil.getHumanReadableNameFromCamelCase(method.getName().substring(3));
        }
        return returnValue;
    }

    public static Method getIdentifierMethod(List<Method> methods) {
        Method identifierMethod = null;
        if (methods != null) {
            for (Method method : methods) {
                if (isAnnotationPresent(method, Identifier.class)) {
                    identifierMethod = method;
                    break;
                }
            }
        }
        return identifierMethod;

    }

    public static Object getMethodResponse(Method method, Object object) {
        Object methodResponse = null;

        if (object != null && method != null) {

            try {
                methodResponse = method.invoke(object, (Object[]) null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("Could not execute method" ,e);
            }
            if (methodResponse == null && object.getClass().equals(String.class)) {
                return Constants.BLANK;
            }
        }
        return methodResponse;
    }

}