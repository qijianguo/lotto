package com.qijianguo.game.common.util;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qijianguo
 */
@Slf4j
public class TimeUtils {

    public static final String YYYY_HH_MM_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYHHMMHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_HH_MM_00_00_00 = "yyyy-MM-dd 00:00:00";

    public static final String TIME_STAMP_PATTERN = "yyyyMMddHHmmssSSS";


    public static final String YYYY_HH_MM = "yyyy-MM-dd";
    public static final String YYYY_HH = "yyyy-MM";

    public static final String YYYYHHMM = "yyyyMMdd";
    public static final String YYYYHH = "yyyyMM";

    public static LocalDateTime convertDate2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date convertLocalDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertString2Date(String text, String pattern) throws BusinessException {
        try {
            if ("1970-01-01 08:00:00".equals(text)) {
                text = "1970-01-01 08:00:01";
            }
            return new SimpleDateFormat(pattern).parse(text);
        } catch (ParseException e) {
            throw new BusinessException(EmBusinessError.DATE_FORMAT_ERROR);
        }
    }

    public static Date convertDate2Date(Date date, String pattern) throws BusinessException {
        String time = new SimpleDateFormat(pattern).format(date);
        return convertString2Date(time, pattern);
    }

    public static String convertDate2DateString(Object o) throws BusinessException {
        try {
            return new SimpleDateFormat(YYYY_HH_MM_HH_MM_SS).format(o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(EmBusinessError.DATE_FORMAT_ERROR);
        }
    }

    public static String convertDate2String(Object o, String pattern) throws BusinessException {
        try {
            return new SimpleDateFormat(pattern).format(o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(EmBusinessError.DATE_FORMAT_ERROR);
        }
    }

    public static long convertDate2long(Date date) {
        LocalDateTime localDateTime = convertDate2LocalDateTime(date);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static String convertLocalDateTime2String(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(YYYY_HH_MM_HH_MM_SS));
    }

    public static String convertLocalDateTime2String(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String convertLocalDate2String(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(YYYYHHMM));
    }

    public static String convertLocalDate2String(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getLatestDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date); //??????????????????
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return convertLocalDate2String(lastDay);
    }

    public static String getNextMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        ; //??????????????????
        return convertLocalDate2String(localDate.plusMonths(1));
    }

    public static LocalDate convertDate2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String time = df.format(date);
        return time;
    }

    /**
     * ?????????????????????yyyy-MM-dd???
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_HH_MM);
        Date date = new Date();
        String currentDate = format.format(date);
        return currentDate;
    }

    public static String minusMinutes(Integer minute, String time) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_HH_MM_HH_MM_SS);
            LocalDateTime ldt = LocalDateTime.parse(time, df).minusMinutes(minute);
            return df.format(ldt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static long getDateDayPoor(Date startTime, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        // ???????????????????????????????????????
        long diff = endDate.getTime() - startTime.getTime();
        // ??????????????????
        long day = diff / nd;
        return day;
    }

    private static final long nd = 1000 * 24 * 60 * 60;
    private static final long nh = 1000 * 60 * 60;
    private static final long nm = 1000 * 60;
    private static final long ns = 1000;

    public static String getDatePoor(Date startTime, Date endDate) {
        // ???????????????????????????????????????
        long diff = endDate.getTime() - startTime.getTime();
        // ??????????????????
        long day = diff / nd;
        // ?????????????????????
        long hour = diff % nd / nh;
        // ?????????????????????
        long min = diff % nd % nh / nm;
        // ??????????????????//????????????
        long sec = diff % nd % nh % nm / ns;
        return day + "???" + hour + "??????" + min + "??????" + sec + "???";
    }

    public static long getDifferSec(Date startTime, Date endDate) {
        long ns = 1000;
        // ???????????????????????????????????????
        long diff = endDate.getTime() - startTime.getTime();
        long sec = diff / ns;
        return sec;
    }

    public static long getDifferMin(Date startTime, Date endDate) {
        long diff = endDate.getTime() - startTime.getTime();
        long sec = diff / nm;
        return sec;
    }

    public static long getDifferHour(Date startTime, Date endDate) {
        long diff = endDate.getTime() - startTime.getTime();
        long sec = diff / nh;
        return sec;
    }

    public static long getDifferDay(Date startTime, Date endDate) {
        long diff = endDate.getTime() - startTime.getTime();
        long sec = diff / nd;
        return sec;
    }

    public static Date long2DateStr(long timestemp) {
        return new Date(timestemp / 1000 * 1000);
    }

    public static String long2Date(long timestemp) {
        return convertDate2DateString(new Date(timestemp / 1000 * 1000));
    }

    /**
     * x???x???x???
     *
     * @param secDiff
     * @return
     */
    public static String sec2String(long secDiff) {
        secDiff = secDiff * 1000;
        // ??????????????????
        long day = secDiff / nd;
        // ?????????????????????
        long hour = secDiff % nd / nh;
        // ?????????????????????
        long min = secDiff % nd % nh / nm;
        // ??????????????????//????????????
        long sec = secDiff % nd % nh % nm / ns;
        StringBuilder time = new StringBuilder();
        if (day > 0) {
            time.append(day + "???");
        }
        if (hour > 0) {
            time.append(hour + "???");
        }
        if (min > 0) {
            time.append(min + "???");
        }
        if (sec > 0) {
            time.append(sec + "???");
        }
        if (time.length() == 0) {
            time.append("-");
        }
        return time.toString();
    }

    public static String sec2HourMin(long secDiff) {
        secDiff = secDiff * 1000;
        // ?????????????????????
        long hour = secDiff / nh;
        // ?????????????????????
        long min = secDiff % nh / nm;
        // ??????????????????//????????????
        long sec = secDiff % nh % nm / ns;
        StringBuilder time = new StringBuilder();
        if (hour > 0) {
            time.append(hour + "??????");
        }
        if (min > 0) {
            time.append(min + "??????");
        }
        if (time.length() == 0) {
            if (secDiff > 0) {
                time.append("1??????");
            } else {
                time.append("0??????");
            }
        }
        return time.toString();
    }

    public static String sec2Min(long secDiff) {
        secDiff = secDiff * 1000;
        // ?????????????????????
        long min = secDiff / nm;
        if (min == 0L && secDiff > 0) {
            return "1";
        } else {
            return String.valueOf(min);
        }

    }

    public static List<LocalDate> getLatestWeek() {
        List<LocalDate> calendars = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(7 - now.getDayOfWeek().getValue());
        for (int i = 13; i >= 0; i--) {
            calendars.add(localDate.minusDays(i));
        }
        return calendars;
    }

    public static boolean nowBetweenTime(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            long now = System.currentTimeMillis();
            return startTime.getTime() < now && endTime.getTime() > now;
        }
        return false;
    }

    public static String time(Date startTime, Date endTime) {
        // ???????????????????????????????????????
        long diff = endTime.getTime() - startTime.getTime();
        StringBuilder sb = new StringBuilder();
        // ??????????????????
        long day = diff / nd;
        // ?????????????????????
        long hour = diff % nd / nh;
        // ?????????????????????
        long min = diff % nd % nh / nm;
        // ??????????????????//????????????
        long sec = diff % nd % nh % nm / ns;


        return day + "???" + hour + "??????" + min + "??????" + sec + "???";
    }

    public static String mill2HourMin(long millDiff) {
        // ?????????????????????
        long hour = millDiff / nh;
        // ?????????????????????
        long min = millDiff % nh / nm;
        // ??????????????????//????????????
        long sec = millDiff % nh % nm / ns;
        if (hour > 0) {
            return hour + "Hour";
        } else {
            if (min > 30) {
                return "30Min";
            } else if (min > 15) {
                return "15Min";
            } else if (min > 5) {
                return "5Min";
            } else {
                return "now";
            }
        }
    }
}
