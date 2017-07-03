package com.paresh.util;

import com.paresh.dto.Diff;
import com.paresh.dto.DiffBuilder;

import java.util.ArrayList;
import java.util.List;


public class ObjectDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object before, Object after, String description) {
        List<Diff> diffs = new ArrayList<>();
        if (before == null && after != null) {
            diffs.add(new DiffBuilder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new DiffBuilder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else {
            if (before.equals(after)) {
                diffs.add(new DiffBuilder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            } else {
                diffs.add(new DiffBuilder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            }
        }
        return diffs;
    }


    //Default fallback incase no other calculator gets triggered
    @Override
    public int getPriority() {
        return 0;
    }


    @Override
    public boolean test(Object o) {
        return true;
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