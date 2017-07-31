package com.paresh.util;

import com.paresh.dto.Diff;

import java.util.Collection;

/**
 * Created by Admin on 02-07-2017.
 */
interface DiffFunction {

    Collection<Diff> apply(Object before, Object after, String description);
}
