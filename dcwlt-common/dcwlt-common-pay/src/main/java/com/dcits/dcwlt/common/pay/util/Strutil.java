package com.dcits.dcwlt.common.pay.util;


import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class Strutil {

    private static final Logger logger = LoggerFactory.getLogger(Strutil.class);

    public static String listToString(List list, String separator){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if(i<list.size() -1){
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 填充指定字符使原有字符串达到自定长度
     * @param src 源字符串
     * @param len 指定长度
     * @param fillChar  填充的字符
     * @param direct 填充方向 'l'-左 'r'-右
     * @return
     * @throws Error
     */
    public static String fillchar(String src, int len, char fillChar, char direct){
        if(direct == 'l'){
            while(src.length()<len){
                src = fillChar + src;
            }
        }else if((direct == 'r')){
            while(src.length()<len){
                src = src + fillChar;
            }
        }else{
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        }
        return src;
    }

    /**
     * 带字符集的字符串 转换为 java原生字符串
     * @param src 源字符串
     * @param encoding 源字符串的字符集
     * @return java原生字符串
     */
    public static String StringDecode(String src, String encoding) throws UnsupportedEncodingException {
        byte[] a;
        a = src.getBytes(encoding);
        return new String(a);
    }

    /**
     * java原生字符串  转换为  带字符集的字符串
     * @param src java原生字符串
     * @param encoding 需要转换的字符集
     * @return 带字符集的字符串
     */
    public static String StringEncode(String src, String encoding) throws UnsupportedEncodingException{
        byte[] a = src.getBytes();
        String b = new String(a , encoding);
        return b;
    }

    /**
     * 带字符集的字符串间的相互转换
     * @param src 源字符串
     * @param srcEncoding 源字符集
     * @param dstEncoding 目标字符集
     * @return 转换后的字符串
     * @throws Error
     */
    public static String StringChangeCoding(String src, String srcEncoding, String dstEncoding) throws UnsupportedEncodingException{
        if(srcEncoding != null){
            src = StringDecode(src, srcEncoding);
        }
        if(dstEncoding != null){
            src = StringEncode(src, dstEncoding);
        }
        return src;
    }

    /**
     *
     * @param text
     * @param padLen
     * @param ch
     * @return
     * @throws Error
     */
    public static String ljust(String text, int padLen, char ch, String encoding) throws Throwable {
        if (padLen < 0) {
            throw new IllegalArgumentException("填充长度不得小于0:" + padLen);
        }
        int strLen = getLen(text, encoding);
        if (padLen <= strLen){
            return text;
        }
        String repeatedChars = repeat(ch, padLen-strLen);
        return text.concat(repeatedChars);
    }

    /**
     *
     * @param text
     * @param padLen
     * @param ch
     * @return
     * @throws Error
     */
    public static String ljust(String text, int padLen, char ch) throws Throwable {
        if (padLen < 0) {
            throw new IllegalArgumentException("填充长度不得小于0:" + padLen);
        }
        int strLen = getLen(text);
        if (padLen <= strLen){
            return text;
        }
        String repeatedChars = repeat(ch, padLen-strLen);
        return text.concat(repeatedChars);
    }

    /**
     *
     * @param text
     * @param padLen
     * @param ch
     * @return
     * @throws Error
     */
    public static String rjust(String text, int padLen, char ch,  String encoding) throws Throwable {
        if (padLen < 0) {
            throw new IllegalArgumentException("填充长度不得小于0:" + padLen);
        }
        int strLen = getLen(text, encoding);
        if (padLen <= strLen){
            return text;
        }
        String repeatedChars = repeat(ch, padLen-strLen);
        return repeatedChars.concat(text);
    }


    /**
     *
     * @param text
     * @param padLen
     * @param ch
     * @return
     * @throws Error
     */
    public static String rjust(String text, int padLen, char ch) throws Throwable {
        if (padLen < 0) {
            throw new IllegalArgumentException("填充长度不得小于0:" + padLen);
        }
        int strLen = getLen(text);
        if (padLen <= strLen){
            return text;
        }
        String repeatedChars = repeat(ch, padLen-strLen);
        return repeatedChars.concat(text);
    }

    public static int getLen(String str, String encoding) throws Throwable{
        try {
            return str.getBytes(encoding).length;
        } catch (UnsupportedEncodingException e) {
            logger.error("获取字符串长度失败", e);
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        }
    }

    private static String repeat(final char ch, final int repeat) {
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    public static int getLen(String str) throws Throwable {
        return getLen(str, "UTF-8");
    }



    /**
     * 替换模板字符串中 %('key')s 中的值为模板值Dict中key对应的Value
     * @param tempStr 模板字符串
     * @param tempValueMap 模板字符串替换替换的内容
     * @return
     * @throws Error
     */
    public static String parseTempString(String tempStr,String rex, Map tempValueMap){
        logger.debug(String.format("模板字符串:%s", tempStr));
        String[] str = tempStr.split(rex);
        StringBuffer outStr = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if(i%2 == 0){
                outStr.append(str[i]);
            } else{
                String value = (String)tempValueMap.get(str[i]);
                if(value == null){
                    logger.info(String.format("模板字符串Dict中找不到相应的值(%s:%s)", str[i],value));
                    throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
                }else{
                    outStr.append(value);
                }
            }
        }
        logger.info(String.format("解析后字符串:%s", outStr.toString()));
        return outStr.toString();
    }
}
