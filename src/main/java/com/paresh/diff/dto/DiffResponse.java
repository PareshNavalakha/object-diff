package com.paresh.diff.dto;

import java.util.Collection;
import java.util.Map;

public class DiffResponse {
    private Collection<Diff> diffs;
    private Map<Class, ClassMetadata> classMetaDataMap;

    public Collection<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(Collection<Diff> diffs) {
        this.diffs = diffs;
    }

    public Map<Class, ClassMetadata> getClassMetaDataMap() {
        return classMetaDataMap;
    }

    public void setClassMetaDataMap(Map<Class, ClassMetadata> classMetaDataMap) {
        this.classMetaDataMap = classMetaDataMap;
    }
}
