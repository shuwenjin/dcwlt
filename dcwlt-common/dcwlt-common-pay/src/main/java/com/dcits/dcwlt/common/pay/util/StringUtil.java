package com.dcits.dcwlt.common.pay.util;

import java.nio.charset.Charset;

public class StringUtil {
    /**
     * 字符串截取
     * @param src	原字符串
     * @param beginIndex	起始位置
     * @param endIndex		结束位置
     * @param encoding		字符编码
     * @return
     */
    public static String truncate(String src, int beginIndex, int endIndex, String encoding) {
        // 获取默认编码
        if (encoding == null) {
            encoding = "UTF-8";
        }
        // 获取字符集
        Charset cs = Charset.forName(encoding);
        // 逐个字符截取,从右往左截取，每次截取一位
        while (true) {
            // 转换为byte[]
            byte[] data = src.getBytes(cs);
            // 未超长
            if (data.length <= endIndex) {
                break;
            }
            // 截取
            src = src.substring(0, src.length() - 1);
            // 字符为空
            if (src.isEmpty()) {
                break;
            }
        }
        // 逐个字符截取，从左往右截取，每次截取一位
        while (true) {
            // 转换为byte[]
            byte[] data = src.getBytes(cs);
            // 未超长
            if (data.length <= endIndex - beginIndex) {
                break;
            }
            // 截取
            src = src.substring(1);
            // System.out.println(src+"|"+src.length());
            // 字符为空
            if (src.isEmpty()) {
                break;
            }
        }
        // 返回
        return src;
    }

}
