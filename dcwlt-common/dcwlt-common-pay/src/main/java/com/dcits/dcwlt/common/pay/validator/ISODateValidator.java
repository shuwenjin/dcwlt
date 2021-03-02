package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.validator.annotation.ISODate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class ISODateValidator implements ConstraintValidator<ISODate, String> {
    private static final Logger logger = LoggerFactory.getLogger(ISODateValidator.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

    private static final String isoDatePattern = "yyyy-mm-dd";

    private boolean required;

    @Override
    public void initialize(ISODate constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (required || !StringUtils.isEmpty(value)) {
            if (required && StringUtils.isEmpty(value)) {
                result = false;
            } else {
                try {
                    dateTimeFormatter.parse(value);
                } catch (Exception e) {
                    logger.error("请求报文中的日期字段：{}不符合ISODate：{}格式",value, isoDatePattern);
                    result = false;
                }
            }
            return result;
        } else {
            return true;
        }
    }
}
