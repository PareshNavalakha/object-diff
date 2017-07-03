package com.paresh.dto;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Admin on 27-06-2017.
 */
public class ClassMetadata {
    private List<Method> getterMethods;
    private List<String> methodDescriptions;

    private String classDescription;
    private Method identifierMethod;

    public List<String> getMethodDescriptions() {
        return methodDescriptions;
    }

    public void setMethodDescriptions(List<String> methodDescriptions) {
        this.methodDescriptions = methodDescriptions;
    }

    public Method getIdentifierMethod() {
        return identifierMethod;
    }

    public void setIdentifierMethod(Method identifierMethod) {
        this.identifierMethod = identifierMethod;
    }

    public List<Method> getGetterMethods() {
        return getterMethods;
    }

    public void setGetterMethods(List<Method> getterMethods) {
        this.getterMethods = getterMethods;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}
