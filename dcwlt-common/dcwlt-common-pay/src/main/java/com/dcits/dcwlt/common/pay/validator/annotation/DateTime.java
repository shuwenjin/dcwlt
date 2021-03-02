package com.dcits.dcwlt.common.pay.validator.annotation;

import com.dcits.dcwlt.common.pay.validator.DateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {DateTimeValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface DateTime {
	
	String pattern() default "yyyy-MM-dd";

	String message() default "日期时间格式有误";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
