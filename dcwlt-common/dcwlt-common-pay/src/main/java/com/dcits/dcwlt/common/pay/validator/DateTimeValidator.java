package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.validator.annotation.DateTime;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;

/**
 * @Time 2020年2月27日下午4:16:37
 * @Version 
 * <p>Description: ISODateTime 日期时间格式检验</p>
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, String>{
	
	private String pattern = "yyyy-MM-dd";
	
	@Override
	public void initialize(DateTime constraintAnnotation) {
		pattern = constraintAnnotation.pattern();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		 FastDateFormat datetimeFormatter = FastDateFormat.getInstance(pattern);
		try {
			datetimeFormatter.parse(value);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

}
