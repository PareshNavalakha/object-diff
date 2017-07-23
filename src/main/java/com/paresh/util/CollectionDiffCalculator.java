package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.ChangeType;
import com.paresh.dto.Diff;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 01-07-2017.
 */
class CollectionDiffCalculator extends DiffCalculator {
    @Override
    public List<Diff> apply(Object beforeObject, Object afterObject, String description) {
        List<Diff> diffs = new LinkedList<>();
        Collection before = (Collection) beforeObject;
        Collection after = (Collection) afterObject;

        if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            before.forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            after.forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else {
            for (Object object : before) {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, findCorrespondingObject(object, after), description));
                } else {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, getCorrespondingObject(object, after), description));
                }

            }
            List<Diff> temp = new LinkedList<>();

            //Now we need to ignore all besides DELETED items
            for (Object object : after) {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    temp.addAll(getDiffComputeEngine().evaluateAndExecute(findCorrespondingObject(object, before), object, description));
                } else {
                    temp.addAll(getDiffComputeEngine().evaluateAndExecute(getCorrespondingObject(object, before), object, description));
                }

            }
            if (temp != null && temp.size() > 0) {
                temp.removeIf(delta -> delta.getChangeType().equals(ChangeType.NO_CHANGE)||delta.getChangeType().equals(ChangeType.UPDATED));
                diffs.addAll(temp);
            }
        }
        return diffs;
    }

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

    private Object getCorrespondingObject(Object object, Collection collection) {
        if (object != null && !isNullOrEmpty(collection)) {
            Object identifier = ClassMetadataCache.getInstance().getIdentifier(object);
            Object comparisonIdentifier;
            if (identifier != null) {
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

