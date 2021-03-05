package com.dcits.dcwlt.pay.online.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 时间日期比较工具
 *
 * @author majun
 * @date 2020/12/30
 */
public final class DateCompareUtil {
    private DateCompareUtil() { }

    private static final String FORMAT_DATE = "yyyyMMdd";
    /**
     * 判断当前时间是否在指定时间范围内， [startDate, endDate);
     * @param startDate
     * @param endDate
     * @return  如果返回当前时间在开始于结束时间范围内， 返回true, 否则返回false
     */
    public static boolean atTimeFrame(String startDate, String endDate){
        if(startDate == null && endDate == null){   //没有时间限制
            return true;
        }
        boolean left = nowGTEDateTime(startDate);
        boolean right = nowLTDateTime(endDate);

        return left && right;
    }

    /**
     * 判断当前时间大于等于指定时间
     * @param date
     * @return
     */
    public static boolean nowGTEDateTime(String date){
        LocalDate localDate = LocalDate.now();

        boolean result= false;
        if(date == null){
            result = true;
        }else {
            LocalDate startT = LocalDate.parse(date, DateTimeFormatter.ofPattern(FORMAT_DATE));
            int res = localDate.compareTo(startT);
            result = res >= 0;
        }

        return result;
    }

    /**
     * 当前时间小于等于指定时间
     * @param date
     * @return
     */
    public static boolean nowLTEDateTime(String date){
        LocalDate localDate = LocalDate.now();

        boolean result= false;
        if(date == null){
            result = true;
        }else {
            LocalDate startT = LocalDate.parse(date, DateTimeFormatter.ofPattern(FORMAT_DATE));
            int res = localDate.compareTo(startT);
            result = res <= 0;
        }

        return result;
    }

    /**
     * 当前时间小于指定时间
     * @param date
     * @return
     */
    public static boolean nowLTDateTime(String date){
        LocalDate localDate = LocalDate.now();

        boolean result= false;
        if(date == null){
            result = true;
        }else {
            LocalDate startT = LocalDate.parse(date, DateTimeFormatter.ofPattern(FORMAT_DATE));
            int res = localDate.compareTo(startT);
            result = res < 0;
        }

        return result;
    }


    /**
     * 两个日期格式判断
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2){
        LocalDate startT = LocalDate.parse(date1, DateTimeFormatter.ofPattern(FORMAT_DATE));
        LocalDate endT = LocalDate.parse(date2, DateTimeFormatter.ofPattern(FORMAT_DATE));
        return startT.compareTo(endT);
    }
}
