package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.ChangeType;
import com.paresh.dto.Diff;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
class CollectionDiffCalculator extends DiffCalculator {
    @Override
    public Collection<Diff> apply(final Object beforeObject, final Object afterObject, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        Collection before = (Collection) beforeObject;
        Collection after = (Collection) afterObject;

        if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            before.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            after.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else {

            before.parallelStream().forEach( object -> {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, findCorrespondingObject(object, after), description));
                } else {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, getCorrespondingObject(object, after), description));
                }

            });
            Collection<Diff> temp = new ConcurrentLinkedQueue<>();

            //Now we need to ignore all Updated and Unchanged items
            after.parallelStream().forEach( object -> {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    temp.addAll(getDiffComputeEngine().evaluateAndExecute(findCorrespondingObject(object, before), object, description));
                } else {
                    temp.addAll(getDiffComputeEngine().evaluateAndExecute(getCorrespondingObject(object, before), object, description));
                }

            });
            if (temp != null && temp.size() > 0) {
                temp.removeIf(delta -> delta.getChangeType().equals(ChangeType.NO_CHANGE)||delta.getChangeType().equals(ChangeType.UPDATED));
                diffs.addAll(temp);
            }
        }
        return diffs;
    }

    //For simple objects, we need to simply compare the objects
    private Object findCorrespondingObject(Object object, Collection collection) {
        if (object != null && !isNullOrEmpty(collection)) {
            for (Object indexElement : collection) {
                if (indexElement != null && object.equals(indexElement)) {
                    return indexElement;
                }
            }
        }
        return null;
    }

    //For complex objects, we need to compare identifiers to get the corresponding object
    private Object getCorrespondingObject(final Object object, final Collection collection) {
        if (object != null && !isNullOrEmpty(collection)) {
            Object identifier = ClassMetadataCache.getInstance().getIdentifier(object);
            if (identifier != null) {
                Object comparisonIdentifier;
                for (Object indexElement : collection) {
                    if (indexElement != null) {
                        comparisonIdentifier = ClassMetadataCache.getInstance().getIdentifier(indexElement);
                        if (identifier.equals(comparisonIdentifier)) {
                            return indexElement;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean test(Object object) {
        return ReflectionUtil.isInstanceOfCollection(object);
    }

}

