package com.common.utils;

import java.time.*;
import java.util.Date;

public class DateUtils {
    /**
     * 基于当前时区，获取当前日期+时间
     *
     * @return 日期+时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 将LocalDateTime类型强转为Date类型
     *
     * @param localDateTime LocalDateTime日期
     * @return Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前日期+时间，并返回Date类型
     *
     * @return Date
     */
    public static Date currentDate() {
        return localDateTime2Date(now());
    }
}
