package io.vicp.frlib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间服务类
 * Created by zhoudr on 2016/12/27.
 */
public final class DateUtil {

    /**
     * 一天中的秒数
     */
    public static final int SECONDS_IN_ONE_DAY = 86400;

    /**
     * 获取指定时间与当前时间相隔的秒数
     *
     * @param begin：开始时间
     * @param end：结束时间
     * @return
     */
    public static Long getIntervalSecondsBetweenDates(Date begin, Date end) {
        if (begin == null || end == null) {
            return 0L;
        }
        long millSeconds = end.compareTo(begin) > 0 ? end.getTime() - begin.getTime() : begin.getTime() - end.getTime();
        return millSeconds / 1000;
    }

    /**
     * 将日期字符串转换成指定格式的日期，默认返回当前日期
     *
     * @param date
     * @return
     */
    public static Date convertStringDateWithDefaultNow(String date) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return converStringToDate(date, simpleDateFormat);
    }

    /**
     * 将时间字符串转换成指定格式的时间，默认返回当前时间
     *
     * @param dateTime
     * @return
     */
    public static Date convertStringTimeWithDefaultNow(String dateTime) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return converStringToDate(dateTime, simpleDateFormat);
    }

    private static Date converStringToDate(String date, SimpleDateFormat simpleDateFormat) throws ParseException{
        final Date defaultDate = new Date();
        if (date == null) {
            return defaultDate;
        }
        return simpleDateFormat.parse(date);
    }

    /**
     * 转换日期至指定格式的字符串
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static String converDateToString(Date date, SimpleDateFormat simpleDateFormat) {
        if (date == null) {
            throw new RuntimeException("date is null");
        }
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 获取一天中最早的时间
     *
     * @param date
     * @return
     */
    public static Date getEarlliestTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置一天当中最晚时间
     *
     * @param date
     * @return
     */
    public static Date getLatestTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Long getIntervalDaysBetweenDates(Date beginDate, Date endDate) {
        Date beginTime = getEarlliestTime(beginDate);
        Date endTime = getEarlliestTime(endDate);
        long intervalSeconds = getIntervalSecondsBetweenDates(beginTime, endTime);
        return intervalSeconds / SECONDS_IN_ONE_DAY;
    }

    public static Long getIntervalWeeksBetweenDates(Date beginDate, Date endDate) {
        long intervalDays = getIntervalDaysBetweenDates(beginDate, endDate);
        return intervalDays / 7;
    }

    public static void main(String[] args) {
        try {
            Date beginDate = convertStringTimeWithDefaultNow("2016-12-15 00:00:00");
            Date endDate = convertStringTimeWithDefaultNow("2017-08-31 00:00:00");
            long intervalDays = getIntervalDaysBetweenDates(beginDate, endDate);
            long intervalWeeks = getIntervalWeeksBetweenDates(beginDate, endDate);
            System.out.println("intervalDays:" + intervalDays + ",intervalWeeks:" + intervalWeeks);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
