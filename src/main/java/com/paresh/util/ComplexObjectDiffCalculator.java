package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.Diff;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
class ComplexObjectDiffCalculator extends DiffCalculator {

    @Override
    public Collection<Diff> apply(Object before, Object after, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        if (before == null && after == null) {
            diffs.add(new Diff.Builder().hasNotChanged().setFieldDescription(description).build());
        } else if (before == null && after != null) {
            diffs.add(new Diff.Builder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new Diff.Builder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else if (before != null && before.equals(after)) {
            diffs.add(new Diff.Builder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build());
        } else if (before != null && after != null) {
            Diff diff = new Diff.Builder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build();
            diffs.add(diff);

            Set<Method> methods = ClassMetadataCache.getInstance().getAllGetterMethods(before.getClass());
            if (methods != null) {
                Collection<Diff> childDiffs = new ConcurrentLinkedQueue<>();
                diff.setChildDiffs(childDiffs);
                methods.parallelStream().forEach(method -> childDiffs.addAll(getDiffComputeEngine().evaluateAndExecute(ReflectionUtil.getMethodResponse(method, before), ReflectionUtil.getMethodResponse(method, after), ClassMetadataCache.getInstance().getMethodDescription(before.getClass(), method))));
            }
        }
        return diffs;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean test(Object object) {
        return !ReflectionUtil.isBaseClass(object.getClass());
    }

}