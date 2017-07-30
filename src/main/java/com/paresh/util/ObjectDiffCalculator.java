package com.paresh.util;

import com.paresh.dto.Diff;

import java.util.LinkedList;
import java.util.List;


public class ObjectDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object before, Object after, String description) {
        List<Diff> diffs = new LinkedList<>();
        if (before == null && after != null) {
            diffs.add(new Diff.Builder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new Diff.Builder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else {
            if (before != null && before.equals(after)) {
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
    public boolean test(Object o) {
        return true;
    }

}