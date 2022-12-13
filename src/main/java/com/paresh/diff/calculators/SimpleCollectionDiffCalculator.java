package com.paresh.diff.calculators;

import com.paresh.diff.cache.ClassMetadataCache;
import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.exception.NotSatisfactorilyTested;
import com.paresh.diff.util.CollectionUtil;
import com.paresh.diff.util.ReflectionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
public class SimpleCollectionDiffCalculator extends DiffCalculator {
    @Override
    public Collection<Diff> apply(final Object beforeObject, final Object afterObject, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        Collection before = (Collection) beforeObject;
        Collection after = (Collection) afterObject;

        if (CollectionUtil.isNullOrEmpty(before) && CollectionUtil.isNullOrEmpty(after)) {
            diffs.add(new Diff.Builder().hasNotChanged().setFieldDescription(description).build());
        } else if (!CollectionUtil.isNullOrEmpty(before) && CollectionUtil.isNullOrEmpty(after)) {
            before.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object, null, description)));

        } else if (CollectionUtil.isNullOrEmpty(before) && !CollectionUtil.isNullOrEmpty(after)) {
            after.parallelStream().forEach(object -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(null, object, description)));

        } else {

            Map<Object, Object> beforeIdentifierMap = new ConcurrentHashMap<>(before.size());

            Map<Object, Object> afterIdentifierMap = new ConcurrentHashMap<>(after.size());

            before.parallelStream().forEach(element ->
                    beforeIdentifierMap.put(element, element));

            after.parallelStream().forEach(element ->
                    afterIdentifierMap.put(element, element));

            before.parallelStream().forEach(object ->
                    diffs.addAll(getDiffComputeEngine().evaluateAndExecute(object,
                            ClassMetadataCache.getInstance().getCorrespondingSimpleObject(object, afterIdentifierMap), description)));
            Collection<Diff> temp = new ConcurrentLinkedQueue<>();

            //Now we need to ignore all Updated and Unchanged items
            after.parallelStream().forEach(object ->
                    temp.addAll(getDiffComputeEngine().evaluateAndExecute(
                            ClassMetadataCache.getInstance().getCorrespondingSimpleObject(object, beforeIdentifierMap), object, description)));
            if (!temp.isEmpty()) {
                temp.removeIf(delta -> delta.getChangeType().equals(ChangeType.NO_CHANGE) || delta.getChangeType().equals(ChangeType.UPDATED));
                diffs.addAll(temp);
            }
        }
        return diffs;
    }


    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean test(Object object1, Object object2) {
        boolean returnValue = false;
        try {
            returnValue = isASimpleCollection(object1);
        } catch (NotSatisfactorilyTested e) {
            try {
                returnValue = isASimpleCollection(object2);
            } catch (NotSatisfactorilyTested notSatisfactorilyTested) {
                //EAT IT
            }
        }
        return returnValue;
    }

    private boolean isASimpleCollection(Object object) throws NotSatisfactorilyTested {
        if (ReflectionUtil.isInstanceOfCollection(object)) {
            Collection collection = (Collection) object;
            if (collection != null && !collection.isEmpty()) {
                Iterator iterator = collection.iterator();
                Object element = iterator.next();
                if (element != null) {
                    return ReflectionUtil.isBaseClass(element.getClass());
                }
            }
        }
        throw new NotSatisfactorilyTested();
    }

}

