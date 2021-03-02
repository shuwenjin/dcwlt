package com.dcits.dcwlt.common.pay.validator;

import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.AmountText;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Time 2020年2月27日下午5:25:33
 * @Version 
 * <p>Description: 金额格式检验，格式预期为：金额；其中货币符号（3 位），小数部分2位数字整数部分最多16位数字，小数点（1位）。例如： CNY10000000</p>
 */
public class AmountTextValidator implements ConstraintValidator<AmountText, String>{

	@Override
	public boolean isValid(String amount, ConstraintValidatorContext context) {
		try {
			if (!StringUtils.isBlank(amount)) {
				AmountUtil.formatAmount(amount);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
