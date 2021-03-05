/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dcits.dcwlt.common.pay.sequence.util;

import com.dcits.dcwlt.common.pay.sequence.exception.SequenceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类，用于进行日期格式转换
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月20日
 * @Version 1.0
 */
public class SeqDateUtils {
    private SeqDateUtils() {
    }

    /**
     * Patterns
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String formatByDateTimePattern(Date date) {
        return formatByPattern(date, DATETIME_PATTERN);
    }

    public static String getTodayDateStr() {
        return formatByPattern(new Date(), DATE_PATTERN);
    }

    public static String getTomorrowDateStr() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return formatByPattern(c.getTime(), DATE_PATTERN);
    }

    /**
     * 传入时间字符串，获得今天的该时间的日期对象
     * 例如：
     * 1、传入 "05:03:22"
     * 2、再获得今天的日期格式字符串"2020-04-22"
     * 3、再拼接在一起，得到完成日期格式字符串"2020-04-22 05:03:22"
     * 4、再将它们转为Date对象返回
     *
     * @param timeStr
     * @return
     */
    public static Date getTodayDateByTimeStr(String timeStr) {
        String dateStr = getTodayDateStr();
        String dateTimeStr = dateStr + " " + timeStr;
        return parseByPattern(dateTimeStr, DATETIME_PATTERN);
    }

    public static Date getTomorrowDateByTimeStr(String timeStr) {
        String dateStr = getTomorrowDateStr();
        String dateTimeStr = dateStr + " " + timeStr;
        return parseByPattern(dateTimeStr, DATETIME_PATTERN);
    }

    private static String formatByPattern(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    private static Date parseByPattern(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            throw new SequenceException(e);
        }
    }
}
