package com.paresh.diff.cache;

import com.paresh.diff.dto.ClassMetadata;
import com.paresh.diff.util.DiffComputeEngine;
import com.paresh.diff.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.Collection;
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

    public Map<Class, ClassMetadata> getClassMetaDataMap() {
        return classMetaDataMap;
    }

    private void buildMetaDataIfNotAvailable(Class clazz) {
        if (!classMetaDataMap.containsKey(clazz)) {
            ClassMetadata classMetadata = new ClassMetadata();
            classMetadata.setClassDescription(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getClassDescription(clazz));
            List<Method> methods = ReflectionUtil.fetchAllGetterMethods(clazz);
            if (methods != null && !methods.isEmpty()) {
                for (Method method : methods) {
                    if (!DiffComputeEngine.getInstance().getClassMetaDataConfiguration().isIgnoreMethod(method)) {
                        classMetadata.getDisplayOrder().add(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getMethodOrder(method));
                        classMetadata.getMethodDescriptions().add(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getMethodDescription(method));
                        classMetadata.getMethods().add(method);
                    }
                }
            }
            //An identifier method has to be one of the Getter methods
            classMetadata.setIdentifierMethod(DiffComputeEngine.getInstance().getClassMetaDataConfiguration().getIdentifierMethod(clazz));
            classMetaDataMap.put(clazz, classMetadata);
        }
    }

    public String getMethodDescription(Class clazz, Method method) {
        buildMetaDataIfNotAvailable(clazz);
        return classMetaDataMap.get(clazz).getMethodDescriptions().get(classMetaDataMap.get(clazz).getMethods().indexOf(method));
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
        return classMetaDataMap.get(clazz).getMethods();
    }

    //For complex objects, we need to compare identifiers to get the corresponding object
    public Object getCorrespondingObject(final Object object, final Collection collection) {
        if (object != null && !isNullOrEmpty(collection)) {
            Object identifier = getIdentifier(object);
            if (identifier != null) {
                Object comparisonIdentifier;
                for (Object indexElement : collection) {
                    comparisonIdentifier = ClassMetadataCache.getInstance().getIdentifier(indexElement);
                    if (identifier.equals(comparisonIdentifier)) {
                        return indexElement;
                    }
                }
            }
        }
        return null;
    }

    //For complex objects, we need to compare identifiers to get the corresponding object
    public Object getObjectFromIdentifier(final Object identifier, final Collection collection) {
        if (identifier != null && !isNullOrEmpty(collection)) {
            if (identifier != null) {
                Object comparisonIdentifier;
                for (Object indexElement : collection) {
                    comparisonIdentifier = ClassMetadataCache.getInstance().getIdentifier(indexElement);
                    if (identifier.equals(comparisonIdentifier)) {
                        return indexElement;
                    }
                }
            }
        }
        return null;
    }

    private boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

}
