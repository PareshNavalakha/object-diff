package com.paresh.diff.util;

import com.paresh.diff.cache.ClassMetadataCache;
import com.paresh.diff.dto.Diff;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
public class ComplexObjectDiffCalculator extends DiffCalculator {

    @Override
    public Collection<Diff> apply(Object before, Object after, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        if (before == null && after == null) {
            diffs.add(new Diff.Builder().hasNotChanged().setFieldDescription(description).build());
        } else if (before == null && after != null) {
            diffs.add(new Diff.Builder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new Diff.Builder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else if (before != null && after != null) {
            if (before.equals(after)) {
                diffs.add(new Diff.Builder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build());
            } else {
                Diff diff = new Diff.Builder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build();
                diffs.add(diff);

                Set<Method> methods = ClassMetadataCache.getInstance().getAllGetterMethods(before.getClass());
                if (methods != null) {
                    Collection<Diff> childDiffs = new ConcurrentLinkedQueue<>();
                    diff.setChildDiffs(childDiffs);
                    methods.parallelStream().forEach(method -> childDiffs.addAll(getDiffComputeEngine().evaluateAndExecute(ReflectionUtil.getMethodResponse(method, before), ReflectionUtil.getMethodResponse(method, after), ClassMetadataCache.getInstance().getMethodDescription(before.getClass(), method))));
                }
            }
        }
        return diffs;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean test(Object object1, Object object2) {
        if (object1 != null) {
            return !ReflectionUtil.isBaseClass(object1.getClass());
        }
        if (object2 != null) {
            return !ReflectionUtil.isBaseClass(object2.getClass());
        }
        return false;
    }

}