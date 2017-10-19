package com.lazylive.autoResponse.Utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 * 获取本地时间
 * 
 * @author Lazy-F
 */
public class TimeUtil {
    /**
     * 获取本地日期,以 "yyyy-MM-dd" 形式返回
     */
    public static String ISO_LOCAL_DATE() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取本地日期,以 "yyyyMMdd" 形式返回
     */
    public static String BASIC_ISO_DATE() {
        return LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    /**
     * 获取本地时间,以 "hh：mm：ss" 形式返回
     */
    public static String ISO_LOCAL_TIME() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * 获取本地日期时间,以 "'yyyy-MM-dd T hh：mm：ss'" 形式返回
     */
    public static String ISO_LOCAL_DATE_TIME() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * 返回ISO时代的区域设置特定日期格式。 这将返回格式化程序，以格式化或解析日期。所使用的确切格式模式因地区而异。
     */
    public static String ofLocalizedDate(FormatStyle dateStyle) {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(dateStyle));
    }

    /**
     * 返回ISO时代的区域设置特定时间格式。 这将返回一个格式化程序，用于格式化或解析一个时间。所使用的确切格式模式因地区而异。
     */
    public static String ofLocalizedTime(FormatStyle timeStyle) {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(timeStyle));
    }

    /**
     * 返回ISO时代的区域设置特定的日期时间格式化程序。 这将返回格式化程序，以格式化或解析日期时间。所使用的确切格式模式因地区而异。
     */
    public static String ofLocalizedDateTime(FormatStyle datetimeStyle) {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(datetimeStyle));
    }


    /**
     * java.util.Date --> java.time.LocalDate
     */
    public static LocalDateTime UDateToLocalDate(Instant instant) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

}
