package com.paresh.diff.util;

import com.paresh.diff.dto.Diff;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ObjectDiffCalculator extends DiffCalculator {

    @Override
    public Collection<Diff> apply(Object before, Object after, String description) {
        Collection<Diff> diffs = new ConcurrentLinkedQueue<>();
        if (before == null && after == null) {
            diffs.add(new Diff.Builder().hasNotChanged().setFieldDescription(description).build());
        }
        else if (before == null) {
            diffs.add(new Diff.Builder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (after == null) {
            diffs.add(new Diff.Builder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else {
            if (before.equals(after)) {
                diffs.add(new Diff.Builder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            } else {
                diffs.add(new Diff.Builder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            }
        }
        return diffs;
    }


    //Default fallback in case no other calculator gets triggered
    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public boolean test(Object o1,Object o2) {
        return true;
    }

}