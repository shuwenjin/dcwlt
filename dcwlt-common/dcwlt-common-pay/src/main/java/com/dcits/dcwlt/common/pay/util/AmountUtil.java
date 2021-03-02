package com.dcits.dcwlt.common.pay.util;

import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Locale;


/**
 * 金额工具处理类
 *
 *
 */
public class AmountUtil {

    private static final Logger logger = LoggerFactory.getLogger(AmountUtil.class);


    private AmountUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 删除千分符
     *
     * @param amount
     * @return
     */
    public static String deleteComma(String amount) {
        amount = amount.replaceAll(",", "");
        return amount;
    }

    /**
     * 元转分
     *
     * @param amount
     * @return
     */
    public static String yuanToFen(String amount) {

        // 删除千分符
        amount = amount.replaceAll(",", "");

        // 空则直接返回
        if (("").equals(amount)) {
            return "0";
        }

        // 去掉符号位
        String sign = "";
        if (amount.startsWith("-")) {
            sign = "-";
            amount = amount.substring(1);
        } else if (amount.startsWith("+")) {
            sign = "";
            amount = amount.substring(1);
        }

        String left = "";
        String right = "";

        String[] items = amount.split("\\.");

        logger.debug("长度：%d", items.length);

        if (items.length == 1) {
            left = items[0];
            right = "00";
        } else if (items.length == 2) {
            left = items[0];
            right = items[1];
        } else {
            logger.error("非法金额：" + amount);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }

        // 处理左侧
        if (!"".equals(left) && !isNumeric(left)) {
            logger.error("非法金额：" + amount);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }

        // 处理右侧
        if ("".equals(right)) {
            right = "00";
        } else {
            if (!isNumeric(right)) {
                logger.error("非法金额：" + amount);
                throw new PlatformException(PlatformError.AMOUNT_ERROR);
            }

            try {
                right = right.length() >= 2 ? right.substring(0, 2) : right
                        + "0";
            } catch (Exception e) {
                logger.error("非法金额：" + amount);
                throw new PlatformException(PlatformError.AMOUNT_ERROR);
            }
        }
        // 返回结果
        return lRemoveZero(String.format("%s%s%s", sign, left, right));
    }


    /**
     * 分转元，不插千分符
     *
     * @param amount
     * @return
     */
    public static String fenToYuan(String amount) {

        return fenToYuan(amount, false);

    }

    /**
     * 分转元
     *
     * @param amount
     * @param insComma
     * @return
     */
    public static String fenToYuan(String amount, Boolean insComma) {

        if (insComma == null) {
            insComma = false;
        }
        // 空则直接返回
        if ("".equals(amount)) {
            return "0.00";
        }
        String sign = "";
        // 去掉符号位
        if (amount.startsWith("-")) {
            sign = "-";
            amount = amount.substring(1);
        } else if (amount.startsWith("+")) {
            sign = "";
            amount = amount.substring(1);
        }

        if (!isNumeric(amount)) {
            logger.error("非法金额：" + amount);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }

        String left = "";
        String right = "";

        if (amount.length() > 2) {
            left = amount.substring(0, amount.length() - 2);
            right = amount.substring(amount.length() - 2);
        } else if (amount.length() == 2) {
            left = "0";
            right = amount;
        } else if (amount.length() == 1) {
            left = "0";
            right = "0" + amount;
        } else {
            logger.error("非法金额：" + amount);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }

        // 添加千分符
        if (insComma) {
            left = String.format(Locale.US, "%,d", Long.parseLong(left));
        }

        // 返回结果
        return String.format("%s%s.%s", sign, left, right);

    }


    /**
     * 插入千分符
     *
     * @param amount
     * @return
     */
    public static String insertComma(String amount) {

        return fenToYuan(yuanToFen(amount), true);

    }

    /**
     * 删除前缀0
     *
     * @param amount
     * @return
     */
    public static String deletePreZero(String amount) {

        // 删除前缀0
        String sign = "";
        // 去掉符号位
        if (amount.startsWith("-")) {
            sign = "-";
            amount = amount.substring(1);
        } else if (amount.startsWith("+")) {
            sign = "";
            amount = amount.substring(1);
        }
        // 处理左侧
        if (!"".equals(amount)) {
            try {
                amount = amount.replaceAll("^(0+)", "");
            } catch (Exception e) {
                logger.error("非法金额：" + amount);
                throw new PlatformException(PlatformError.AMOUNT_ERROR);
            }

            if (amount.startsWith(".")) {
                amount = "0" + amount;
            }
        } else {
            amount = "0";
        }

        if ("".equals(amount)) {
            amount = "0";
        }

        // 返回结果
        return String.format("%s%s", sign, amount);
    }


    /**
     * 金额运算
     *
     * @param oper
     * @param a
     * @param b
     * @param rounding
     * @return
     */
    public static String calcAmount(String oper, String a, String b,
                                    String rounding) {

        BigDecimal ad = new BigDecimal(Double.valueOf(a));
        BigDecimal bd = new BigDecimal(Double.valueOf(b));

        BigDecimal ret;
        int round;

        if ("".equals(rounding)) {
            round = BigDecimal.ROUND_HALF_UP;
        }
        //小数点后有数字全去掉
        else if ("0".equals(rounding)) {
            round = BigDecimal.ROUND_DOWN;
        } else if ("1".equals(rounding)) {
            round = BigDecimal.ROUND_HALF_UP;
        }
        //小数点只要有数字就进1
        else if ("2".equals(rounding)) {
            round = BigDecimal.ROUND_UP;
        } else {
            logger.error("非法标识：%s", rounding);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }

        if ("+".equals(oper)) {
            ret = ad.add(bd);
        } else if ("-".equals(oper)) {
            ret = ad.subtract(bd);
        } else if ("*".equals(oper)) {
            ret = ad.multiply(bd);
        } else if ("/".equals(oper)) {
            ret = ad.divide(bd, 0, round);
            return ret.toPlainString();
        } else {
            logger.error("非法标识：%s", rounding);
            throw new PlatformException(PlatformError.AMOUNT_ERROR);
        }
        return String.valueOf(Math.round(ret.doubleValue()));

    }


    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 左去0
     *
     * @param amount
     * @return
     */
    public static String lRemoveZero(String amount) {
        amount = amount.replaceFirst("^0+", "");
        if ("".equals(amount.trim())) {
            return "0";
        } else {
            return amount;
        }
    }

    /**
     * 金额；其中货币符号（3 位），小数部分2位数字整数部分最多16位数字，小数点（1位）。例如： CNY10000000
     *
     * @param amount
     * @return
     */
    public static String formatAmount(String amount) {
        return lRemoveZero(yuanToFen(amount.substring(3, amount.length())));
    }


    /**
     * 判断金额是否小于0，ture为小于0，false为大于0
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean amountCompareToZero(String a, String b) {
        String amount = calcAmount("-", a, b, "");
        return amount.startsWith("-");
    }


    /**
     * @param amt
     * @param amtLen
     * @return
     * @Description:格式化金额，左补0
     */
    public static String lAddZero(String amt, int amtLen) {
        if (amt.length() < amtLen) {
            int len = amtLen - amt.length();
            for (int i = 0; i < len; i++) {
                amt = "0" + amt;
            }
        }
        return amt;
    }
}
