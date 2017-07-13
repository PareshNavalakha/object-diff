package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.ChangeType;
import com.paresh.dto.Diff;

import java.util.LinkedList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 01-07-2017.
 */
class CollectionDiffCalculator extends DiffCalculator {
    @Override
    public List<Diff> apply(Object beforeObject, Object afterObject, String description) {
        List<Diff> diffs = new LinkedList<>();
        Collection before = (Collection) beforeObject;
        Collection after= (Collection) afterObject;

        if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            for (Object object : before) {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null,description));
            }
        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            for (Object object : after) {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute(null, object,description));
            }
        } else {
            for (Object object : before) {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, findCorrespondingObject(object, after),description));
                } else {
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, getCorrespondingObject(object, after),description));
                }

            }
            List<Diff> temp;

            //Now we need to ignore UPDATED and UNCHANGED for duplicates
            for (Object object : after) {
                if (ReflectionUtil.isBaseClass(object.getClass())) {
                    temp = getDiffComputeEngine().evaluateAndExecute(findCorrespondingObject(object, before),object,description);
                } else {
                    temp = getDiffComputeEngine().evaluateAndExecute(getCorrespondingObject(object, before),object,description);
                }
                diffs.addAll(temp.stream().filter(delta -> (!delta.getChangeType().equals(ChangeType.NO_CHANGE) && !delta.getChangeType().equals(ChangeType.UPDATED))).collect(Collectors.toList()));
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
        if (object != null  &&  !isNullOrEmpty(collection)) {
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

