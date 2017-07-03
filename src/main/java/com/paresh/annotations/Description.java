package com.paresh.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to represent your Class or Method in a User friendly manner
 * This will ensure that when reports are generated out of the generated delta
 * Field Names will be properly represented
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Description {
    String userFriendlyDescription();
}
