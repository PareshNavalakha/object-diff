package com.paresh.diff.dto;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 27-06-2017.
 */
public class ClassMetadata {

    private List<String> methodDescriptions = new ArrayList<>();
    private List<Method> methods = new ArrayList<>();
    private List<Integer> displayOrder = new ArrayList<>();
    private String classDescription;
    private Method identifierMethod;

    public List<String> getMethodDescriptions() {
        return methodDescriptions;
    }

    public void setMethodDescriptions(List<String> methodDescriptions) {
        this.methodDescriptions = methodDescriptions;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<Integer> getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(List<Integer> displayOrder) {
        this.displayOrder = displayOrder;
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
