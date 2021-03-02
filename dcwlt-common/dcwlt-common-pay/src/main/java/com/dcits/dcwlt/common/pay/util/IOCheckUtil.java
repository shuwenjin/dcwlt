package com.dcits.dcwlt.common.pay.util;


import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * @Time 2020年2月27日下午4:39:03
 * @Version <p>Description: IO校验工具类</p>
 */
public class IOCheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOCheckUtil.class);

    private static Validator validation = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * @param object
     * @Description:校验实体类字段
     */
    public static void verify(Object... object) {
        long startTime = System.currentTimeMillis();
        String errorMsg = "";
        for (Object obj : object) {
            Set<ConstraintViolation<Object>> violations = validation.validate(obj);
            //判断校验结果是否为空，为空则证明通过
            if (violations.isEmpty()) {
                continue;
            }
            //打印校验不通过字段
            for (Iterator<ConstraintViolation<Object>> iterator = violations.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> constraintViolation = iterator.next();
                errorMsg = String.format("字段{%s}-{%s}", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
                logger.error(String.format("错误信息：%s,耗时为：{%s}", errorMsg, (System.currentTimeMillis() - startTime)));
            }
            throw new PlatformException(PlatformError.IO_CHECK_ERROR.getErrorCode(), errorMsg);
        }
        logger.info("校验实体类字段耗时为：" + (System.currentTimeMillis() - startTime));
    }
}
