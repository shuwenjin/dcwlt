package com.dcits.dcwlt.common.pay.validator.annotation;

import com.dcits.dcwlt.common.pay.validator.ISODateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 日期格式校验
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ISODateValidator.class})
public @interface ISODate {
    boolean required() default true;

    String message() default "日期格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
