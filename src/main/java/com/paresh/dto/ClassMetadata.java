package com.paresh.dto;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Admin on 27-06-2017.
 */
public class ClassMetadata {

    private Map<Method, String> classMethods;
    private String classDescription;
    private Method identifierMethod;

    public Map<Method, String> getClassMethods() {
        return classMethods;
    }

    public void setClassMethods(Map<Method, String> classMethods) {
        this.classMethods = classMethods;
    }

    public Method getIdentifierMethod() {
        return identifierMethod;
    }

    public void setIdentifierMethod(Method identifierMethod) {
        this.identifierMethod = identifierMethod;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}
