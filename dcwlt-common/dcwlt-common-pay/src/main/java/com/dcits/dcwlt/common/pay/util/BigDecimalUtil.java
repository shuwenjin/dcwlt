package com.dcits.dcwlt.common.pay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BigDecimalUtil {
    private static final Logger logger = LoggerFactory.getLogger(BigDecimalUtil.class);



    public static BigDecimal parse(String amount){
        BigDecimal BigAmount=new BigDecimal(amount);
        return BigAmount;
    }
}
