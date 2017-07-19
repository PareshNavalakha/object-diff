package com.paresh.cache;

import com.paresh.constants.Constants;
import com.paresh.dto.ClassMetadata;
import com.paresh.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Admin on 30-06-2017.
 */
public class ClassMetadataCache {

    private static final ClassMetadataCache instance;

    static {
        synchronized (ClassMetadataCache.class) {
            instance = new ClassMetadataCache();
        }
    }

    private final Map<Class, ClassMetadata> classMetaDataMap = new ConcurrentHashMap<>();

    private ClassMetadataCache() {
    }

    public static ClassMetadataCache getInstance() {
        return instance;
    }

    public void clearCache() {
        classMetaDataMap.clear();
        ;
    }

    private void buildMetaDataIfNotAvailable(Class clazz) {
        if (!classMetaDataMap.containsKey(clazz)) {
            ClassMetadata classMetadata = new ClassMetadata();
            classMetadata.setClassDescription(ReflectionUtil.getDescription(clazz));
            List<Method> methods = ReflectionUtil.fetchAllGetterMethods(clazz);
            Map<Method, String> classMethods = new HashMap<>();
            if (methods != null && methods.size() > 0) {
                for (Method method : methods) {
                    classMethods.put(method, ReflectionUtil.getDescription(method));
                }
            }
            classMetadata.setClassMethods(classMethods);
            //An identifier method has to be one of the Getter methods
            classMetadata.setIdentifierMethod(ReflectionUtil.getIdentifierMethod(methods));
            classMetaDataMap.put(clazz, classMetadata);
        }
    }

    public String getMethodDescription(Class clazz, Method method) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getClassMethods().get(method);
    }


    public String getDescription(Class clazz) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getClassDescription();
    }


    public Object getIdentifier(Object object) {

        if (object != null) {
            buildMetaDataIfNotAvailable(object.getClass());
            return ReflectionUtil.getMethodResponse(classMetaDataMap.get(object.getClass()).getIdentifierMethod(), object);

        }
        return null;
    }

    public Set<Method> getAllGetterMethods(Class clazz) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getClassMethods().keySet();
    }

    public String getIdentifierString(Object object) {
        Object returnObject = getIdentifier(object);
        return returnObject == null ? Constants.BLANK : returnObject.toString();
    }
}
