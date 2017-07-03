package com.paresh.util;

import com.paresh.dto.ChangeType;
import com.paresh.dto.Diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Admin on 01-07-2017.
 */
class MapDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object beforeObject, Object afterObject, String description) {
        List<Diff> diffs = new ArrayList<>();
        Map before = (Map) beforeObject;
        Map after = (Map) afterObject;


        if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            for (Object object : before.keySet()) {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute( before.get(object), null,description));
            }
        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            for (Object object : after.keySet()) {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute(null, after.get(object),description));
            }
        } else {
            for (Object object : before.keySet()) {
                diffs.addAll(getDiffComputeEngine().evaluateAndExecute(before.get(object), after.get(object),description));
            }
            List<Diff> temp ;

            //Now we need to ignore UPDATED and UNCHANGED for duplicates
            for (Object object : after.keySet()) {
                temp = getDiffComputeEngine().evaluateAndExecute(before.get(object), after.get(object),description);
                diffs.addAll(temp.stream().filter(delta -> (!delta.getChangeType().equals(ChangeType.NO_CHANGE) && !delta.getChangeType().equals(ChangeType.UPDATED))).collect(Collectors.toList()));
            }
        }
        return diffs;
    }

    private boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean test(Object object) {
        return ReflectionUtil.isInstanceOfMap(object);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

