package com.paresh.diff.util;

import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Admin on 01-07-2017.
 */
public class MapDiffCalculator extends DiffCalculator {

    @Override
    public Collection<Diff> apply(Object beforeObject, Object afterObject, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        Map before = (Map) beforeObject;
        Map after = (Map) afterObject;

        if (isNullOrEmpty(before) && isNullOrEmpty(after)) {
            //Do nothing
        }
        else if (!isNullOrEmpty(before) && isNullOrEmpty(after)) {
            before.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(value, null, description + "::" + key)));
        } else if (isNullOrEmpty(before) && !isNullOrEmpty(after)) {
            after.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(null, value, description + "::" + key)));
        } else {
            before.forEach((key, value) -> diffs.addAll(getDiffComputeEngine().evaluateAndExecute(value, after.get(key), description + "::" + key)));

            Collection<Diff> temp = new ConcurrentLinkedQueue<>();

            //Now we need to ignore all besides DELETED items
            after.forEach((key, value) -> temp.addAll(getDiffComputeEngine().evaluateAndExecute(before.get(key), value, description + "::" + key)));

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
    public boolean test(Object object1,Object object2) {
        if(object1!=null)
        {
            return ReflectionUtil.isInstanceOfMap(object1);
        }
        if(object2!=null)
        {
            return ReflectionUtil.isInstanceOfMap(object2);
        }
        return false;
    }

}

