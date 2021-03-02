package com.dcits.dcwlt.common.pay.validator;


import com.dcits.dcwlt.common.pay.validator.annotation.ByteLength;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.Charset;

/**
 * @Time 2020/9/9 14:43
 * @Version 1.0
 * Description:
 */
public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {
    private int min;
    private int max;
    private String encoding;

    @Override
    public void initialize(ByteLength parameters) {
        min = parameters.min();
        max = parameters.max();
        encoding = parameters.encoding();
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext context) {
        //检验min、max参数是否合法、源字符串
        if (min < 0 || max < 0 || max < min) {
            return false;
        }

        //校验字节长度是否合法
        if(StringUtils.isBlank(src)){
            return true;
        }

        long len = getByteLength(src);

        if (max < len || min > len){
            return false;
        }
        return true;
    }


    /**
     * 获取字节长度
     *
     * @param src
     * @return
     */
    private long getByteLength(String src) {
        // 获取默认编码
        if (encoding == null) {
            encoding = "UTF-8";
        }
        // 获取字符集
        Charset cs = Charset.forName(encoding);

        //获取字符串字节长度
        return src.getBytes(cs).length;
    }

}
