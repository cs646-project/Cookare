package com.common.utils;

import java.time.*;
import java.util.Date;

public class DateUtils {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date currentDate() {
        return localDateTime2Date(now());
    }
}
