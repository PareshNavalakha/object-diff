package com.paresh.util;

import com.paresh.dto.Diff;

import java.util.List;

/**
 * Created by Admin on 02-07-2017.
 */
public interface DiffFunction {

    List<Diff> apply(Object before, Object after, String description);
}
