package com.paresh.util;

import com.paresh.dto.ChangeType;
import com.paresh.dto.Diff;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 01-07-2017.
 */
class MapDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object beforeObject, Object afterObject, String description) {
        List<Diff> diffs = new LinkedList<>();
        Map before = (Map) beforeObject;
        Map after = (Map) afterObject;


        if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            before.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(value, null, description)));
        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            after.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(null, value, description)));
        } else {
            before.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(value, after.get(key), description)));

            List<Diff> temp = new LinkedList<>();

            //Now we need to ignore all besides DELETED items
            before.forEach((key, value) -> temp.addAll(getDiffComputeEngine().evaluateAndExecute(before.get(key), value, description)));

            temp.removeIf(delta -> delta.getChangeType().equals(ChangeType.NO_CHANGE)||delta.getChangeType().equals(ChangeType.UPDATED));
            diffs.addAll(temp);

        }
        return diffs;
    }

    private boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean test(Object object) {
        return ReflectionUtil.isInstanceOfMap(object);
    }

}

