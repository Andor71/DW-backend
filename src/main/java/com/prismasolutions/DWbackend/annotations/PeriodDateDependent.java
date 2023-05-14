package com.prismasolutions.DWbackend.annotations;

import com.prismasolutions.DWbackend.enums.PeriodEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PeriodDateDependent {
    public PeriodEnums key() default PeriodEnums.NONE;
}
