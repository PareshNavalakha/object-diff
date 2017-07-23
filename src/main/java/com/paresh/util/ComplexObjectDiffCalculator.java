package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.Diff;
import com.paresh.dto.DiffBuilder;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 01-07-2017.
 */
class ComplexObjectDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object before, Object after, String description) {
        List<Diff> diffs = new LinkedList<>();
        if (before == null && after == null) {
            diffs.add(new DiffBuilder().hasNotChanged().setFieldDescription(description).build());
        } else if (before == null && after != null) {
            diffs.add(new DiffBuilder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new DiffBuilder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else if (before != null && before.equals(after)) {
            diffs.add(new DiffBuilder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build());
        } else if (before != null && after != null) {
            Diff diff = new DiffBuilder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(ClassMetadataCache.getInstance().getDescription(before.getClass())).build();
            diffs.add(diff);

            Set<Method> methods = ClassMetadataCache.getInstance().getAllGetterMethods(before.getClass());
            if (methods != null) {
                List<Diff> childDiffs = new LinkedList<>();
                diff.setChildDiffs(childDiffs);
                methods.forEach(method -> childDiffs.addAll(getDiffComputeEngine().evaluateAndExecute(ReflectionUtil.getMethodResponse(method, before), ReflectionUtil.getMethodResponse(method, after), ClassMetadataCache.getInstance().getMethodDescription(before.getClass(), method))));
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