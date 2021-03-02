package com.dcits.dcwlt.common.pay.validator.annotation;

import com.dcits.dcwlt.common.pay.validator.AmountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @Time 2020/10/29 15:43
 * @Version 1.00
 * <p>自定义金额校验注解</p>
 */
@Documented
@Constraint(validatedBy = {AmountValidator.class})
@Target({ FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Amount {
    String message() default "金额有误";

    boolean required() default true;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
