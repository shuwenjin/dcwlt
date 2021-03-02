package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String>{

	private Class<? extends Enum<?>> enumClass;
	private static final String METHOD_NAME = "hasEnum";
	private static final Logger logger = LoggerFactory.getLogger(EnumValueValidator.class);

	private boolean required;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		enumClass = constraintAnnotation.value();
		required = constraintAnnotation.required();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Method declaredMethod;

		if(!required && StringUtils.isEmpty(value)){
			return true;
		}
		try {
			declaredMethod = enumClass.getDeclaredMethod(METHOD_NAME, String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("枚举类未声明校验方法");
			return false;
		}
		
		try {
			return (boolean) declaredMethod.invoke(null, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return false;
		}
	}
	
	
}
