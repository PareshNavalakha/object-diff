package com.paresh.cache;

import com.paresh.constants.Constants;
import com.paresh.dto.ClassMetadata;
import com.paresh.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
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

    private void buildMetaDataIfNotAvailable(Class clazz) {
        if (!classMetaDataMap.containsKey(clazz)) {
            ClassMetadata classMetadata = new ClassMetadata();
            classMetadata.setClassDescription(ReflectionUtil.getDescription(clazz));
            List<Method> methods = ReflectionUtil.fetchAllGetterMethods(clazz);
            classMetadata.setGetterMethods(methods);
            classMetadata.setMethodDescriptions(ReflectionUtil.getMethodDescriptions(methods));
            //An identifier method has to be one of the Getter methods
            classMetadata.setIdentifierMethod(ReflectionUtil.getIdentifierMethod(methods));
            classMetaDataMap.put(clazz, classMetadata);
        }
    }

    public String getMethodDescription(Class clazz, Method method) {
        buildMetaDataIfNotAvailable(clazz);
        if (method != null && classMetaDataMap.containsKey(clazz)) {
            List<Method> methods = classMetaDataMap.get(clazz).getGetterMethods();
            if (methods != null) {
                for (int index = 0; index < methods.size(); index++) {
                    if (method.equals(methods.get(index))) {
                        return classMetaDataMap.get(clazz).getMethodDescriptions().get(index);
                    }
                }
            }
        }
        return method != null ? ReflectionUtil.getDescription(method) : Constants.BLANK;
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

    public List<Method> getAllGetterMethods(Class clazz) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getGetterMethods();
    }

    public String getIdentifierString(Object object) {
        Object returnObject = getIdentifier(object);
        return returnObject == null ? Constants.BLANK : returnObject.toString();
    }
}
