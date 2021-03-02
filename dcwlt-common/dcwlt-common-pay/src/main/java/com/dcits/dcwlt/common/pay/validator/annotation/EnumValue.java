package com.dcits.dcwlt.common.pay.validator.annotation;

import com.dcits.dcwlt.common.pay.validator.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {

	boolean required() default true;

	String message() default "枚举值有误";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	Class<? extends Enum<?>> value();
	
	
	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@interface List{
		EnumValue[] value();
	}
	
}
