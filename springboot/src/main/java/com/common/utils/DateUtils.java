package com.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
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
     * 基于当前时区，获取当前日期、时间，并按指定的Pattern格式化为字符串
     *
     * @param pattern 格式
     * @return 字符串
     */
    public static String current(String pattern) {
        return format(now(), pattern);
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

    /**
     * 基于当前时区，获取当前日期
     *
     * @return 日期
     */
    public static LocalDate getDate() {
        return LocalDate.now();
    }

    /**
     * 基于当前时区，获取当前时间
     *
     * @return 时间
     */
    public static LocalTime getTime() {
        return LocalTime.now();
    }

    public static int getYear() {
        return now().getYear();
    }
    public static int getMonth() {
        return now().getMonthValue();
    }
    public static int getDay() {
        return now().getDayOfMonth();
    }
    ////////////// 日期格式化操作 日期 -> 字符串 ///////////////////

    /**
     * 格式化日期为字符串
     *
     * @param localDateTime 时刻
     * @param pattern       格式
     * @return 日期字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern) {

        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期为字符串
     *
     * @param date    date
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {

        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
