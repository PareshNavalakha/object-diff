package com.paresh.diff.cache;

import com.paresh.diff.constants.Constants;
import com.paresh.diff.dto.ClassMetadata;
import com.paresh.diff.util.DiffComputeEngine;
import com.paresh.diff.util.ReflectionUtil;

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

    public Map<Class, ClassMetadata> getClassMetaDataMap() {
        return classMetaDataMap;
    }

    private void buildMetaDataIfNotAvailable(Class clazz) {
        if (!classMetaDataMap.containsKey(clazz)) {
            ClassMetadata classMetadata = new ClassMetadata();
            classMetadata.setClassDescription(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getClassDescription(clazz));
            List<Method> methods = ReflectionUtil.fetchAllGetterMethods(clazz);
            Map<Method, String> classMethods = new HashMap<>();
            if (methods != null && !methods.isEmpty()) {
                methods.forEach(method -> classMethods.put(method, DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getMethodDescription(method)));
            }
            classMetadata.setClassAttributes(classMethods);
            //An identifier method has to be one of the Getter methods
            classMetadata.setIdentifierMethod(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getIdentifierMethod(clazz));
            classMetaDataMap.put(clazz, classMetadata);
        }
    }

    public String getMethodDescription(Class clazz, Method method) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getClassAttributes().get(method);
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
        return classMetaDataMap.get(clazz).getClassAttributes().keySet();
    }

    public String getIdentifierString(Object object) {
        Object returnObject = getIdentifier(object);
        return returnObject == null ? Constants.BLANK : returnObject.toString();
    }
}
