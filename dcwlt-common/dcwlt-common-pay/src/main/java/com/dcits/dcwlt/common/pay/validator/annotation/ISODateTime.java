package com.dcits.dcwlt.common.pay.validator.annotation;

import com.dcits.dcwlt.common.pay.validator.ISODateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date  2020/12/29
 * @version 1.0.0
 * <p>IsoDateTime校验注解</p>
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ISODateTimeValidator.class})
public @interface ISODateTime {

    boolean required() default true;

    String message() default "日期时间格式有误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
