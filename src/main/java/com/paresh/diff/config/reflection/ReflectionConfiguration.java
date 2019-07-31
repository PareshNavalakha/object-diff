package com.paresh.diff.config.reflection;

import com.paresh.diff.annotations.Description;
import com.paresh.diff.annotations.Identifier;
import com.paresh.diff.annotations.Ignore;
import com.paresh.diff.annotations.Order;
import com.paresh.diff.config.ClassMetaDataConfiguration;
import com.paresh.diff.util.ReflectionUtil;
import com.paresh.diff.util.StringUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class ReflectionConfiguration implements ClassMetaDataConfiguration {

    public boolean isIgnoreMethod(Method method) {
        return isAnnotationPresent(method, Ignore.class);
    }

    public boolean isDescriptionMethod(Method method) {
        return isAnnotationPresent(method, Description.class);
    }

    private boolean isAnnotationPresent(Method method, Class clazz) {
        return clazz.isAnnotation() && method.isAnnotationPresent(clazz);
    }

    private boolean isAnnotationPresent(Class baseClass, Class clazz) {
        return clazz.isAnnotation() && baseClass.isAnnotationPresent(clazz);
    }

    public Method getIdentifierMethod(Class clazz) {
        List<Method> methods = ReflectionUtil.fetchAllGetterMethods(clazz);
        return getIdentifierMethod(methods);
    }

    private Method getIdentifierMethod(List<Method> methods) {
        Method identifierMethod = null;
        if (methods != null) {
            Optional<Method> methodOptional = methods.parallelStream().filter(method -> isAnnotationPresent(method, Identifier.class)).findFirst();
            identifierMethod = methodOptional.isPresent() ? methodOptional.get() : null;
        }
        return identifierMethod;
    }

    public String getClassDescription(Class clazz) {
        String returnValue;
        if (isAnnotationPresent(clazz, Description.class)) {
            Description description = (Description) clazz.getAnnotation(Description.class);
            returnValue = description.userFriendlyDescription();
        } else {
            returnValue = StringUtil.getHumanReadableNameFromCamelCase(clazz.getSimpleName());
        }
        return returnValue;
    }

    public String getMethodDescription(Method method) {
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

    @Override
    public int getMethodOrder(Method method) {
        if (isAnnotationPresent(method, Order.class)) {
            Order order = method.getAnnotation(Order.class);
            return order.order();
        }
        return 0;
    }

}
