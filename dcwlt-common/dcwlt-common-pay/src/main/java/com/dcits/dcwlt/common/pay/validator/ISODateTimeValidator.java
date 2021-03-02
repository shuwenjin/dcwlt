package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.validator.annotation.ISODateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

/**
 * @date  2020/12/29
 * @version 1.0.0
 * <p>IsoDateTime格式校验类</p>
 */
public class ISODateTimeValidator implements ConstraintValidator<ISODateTime, String> {

    private static final Logger logger = LoggerFactory.getLogger(ISODateTimeValidator.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    private static final String isoDateTimePattern = "yyyy-mm-ddTHH:MM:SS";

    private boolean required;

    @Override
    public void initialize(ISODateTime constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = true;
        if (required || !StringUtils.isEmpty(value)) {
            if (required && StringUtils.isEmpty(value)) {
                result = false;
            } else {
                try {
                    dateTimeFormatter.parse(value);
                } catch (Exception e) {
                    logger.error("请求报文中的日期字段：{}不符合ISODateTime：{}格式",value,isoDateTimePattern);
                    result = false;
                }
            }
            return result;
        } else {
            return true;
        }
    }
}
