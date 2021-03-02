package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.validator.annotation.Amount;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * <p>采用正则表达式校验金额格式</p>
 */
public class AmountValidator implements ConstraintValidator<Amount, String> {

    private static final Pattern centPattern = Pattern.compile("^[1-9]\\d*\\.\\d{2}$");
    private static final Pattern centPatternZero = Pattern.compile("^0\\.\\d{2}$");

    private boolean required;

    @Override
    public void initialize(Amount constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String amount, ConstraintValidatorContext context) {
        if(!required && StringUtils.isBlank(amount)){
            return true;
        }
        if(StringUtils.isBlank(amount)){
            return false;
        }
        return centPattern.matcher(amount).matches() || centPatternZero.matcher(amount).matches();
    }

}
