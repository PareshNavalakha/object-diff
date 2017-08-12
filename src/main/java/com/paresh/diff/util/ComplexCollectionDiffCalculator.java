package com.paresh.diff.util;

import com.paresh.diff.cache.ClassMetadataCache;
import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.exception.NotSatisfactorilyTested;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
public class ComplexCollectionDiffCalculator extends DiffCalculator {
    @Override
    public Collection<Diff> apply(final Object beforeObject, final Object afterObject, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        Collection before = (Collection) beforeObject;
        Collection after = (Collection) afterObject;

        if (isNullOrEmpty(before) && isNullOrEmpty(after)) {
            //Do nothing.
        } else if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            before.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            after.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else {

            before.parallelStream().forEach(object -> {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, getCorrespondingObject(object, after), description));
            });
            Collection<Diff> temp = new ConcurrentLinkedQueue<>();

            //Now we need to ignore all Updated and Unchanged items
            after.parallelStream().forEach(object -> {
                temp.addAll(getDiffComputeEngine().evaluateAndExecute(getCorrespondingObject(object, before), object, description));
            });
            if (!temp.isEmpty()) {
                temp.removeIf(delta -> delta.getChangeType().equals(ChangeType.NO_CHANGE) || delta.getChangeType().equals(ChangeType.UPDATED));
                diffs.addAll(temp);
            }
        }
        return diffs;
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
    public boolean test(Object object1,Object object2) {
        boolean returnValue = false;
        try
        {
            returnValue=isAComplexCollection(object1);
        }
        catch (NotSatisfactorilyTested e)
        {
            try {
                returnValue=isAComplexCollection(object2);
            } catch (NotSatisfactorilyTested notSatisfactorilyTested) {
                //EAT IT
            }
        }
        return returnValue;
    }

    private boolean isAComplexCollection(Object object) throws NotSatisfactorilyTested {
        if (ReflectionUtil.isInstanceOfCollection(object)) {
            Collection collection = (Collection) object;
            if (collection != null && !collection.isEmpty()) {
                Iterator iterator = collection.iterator();
                return !ReflectionUtil.isBaseClass(iterator.next().getClass());
            }
        }
        throw new NotSatisfactorilyTested();
    }

}

