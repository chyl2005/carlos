package com.github.carlos.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String DATE_KEY_FORMAT = "yyyyMMdd";
    public static final String YY_MM = "yyyyMM";
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YMD_FORMAT_EN = "yyyy/MM/dd";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String HHmm = "HH/mm";
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String YMD_HMS_FORMAT_DIAS = "yyyy/MM/dd/HH/mm/ss";
    public static final String YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int SECONDS_IN_HOUR = 60 * 60;
    /**
     * 一天的秒数
     */
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    /**
     * 一天的毫秒数
     */
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    private DateUtils() {
    }

    public static String getDateformat(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = YMD_HMS_FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static Date getDate(String date, String pattern) {
        try {
            if (StringUtils.isBlank(pattern)) {
                pattern = YMD_HMS_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            return sdf.parse(date);
        } catch (Exception e) {
            LOGGER.error("getDateFormatString error", e);
        }
        return null;
    }


    /**
     * @param daysBefore
     * @return
     */
    public static Date getDateBefore(int daysBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -daysBefore);
        return cal.getTime();
    }
    /**
     * @param daysAfter
     * @return
     */
    public static Date getDateAfter(int daysAfter) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysAfter);
        return cal.getTime();
    }


    /**
     * @param minutes
     * @return
     */
    public static Date getDateAfterMinutes(Date date,int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }



    public static Date today() {
        return toDay(new Date());
    }

    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期当天的最后一秒
     *
     * @param date
     */
    public static Date toNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @param month 201701
     * @return
     */
    public static Integer getFirstDayOfMonth(Integer month) {
        Date dateByMonth = getDateByMonth(month);
        return getFirstDayOfMonth(dateByMonth);

    }

    /**
     * @param month 201701
     * @return
     */
    public static Integer getLastDayOfMonth(Integer month) {
        Date dateByMonth = getDateByMonth(month);
        return getLastDayOfMonth(dateByMonth);

    }

    public static Integer getActualMaximumOfDay(Integer month) {
        Date dateByMonth = getDateByMonth(month);
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(dateByMonth);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return 20160101
     */
    public static Integer getLastDayOfMonth(Date date) {
        //获取前月的最后一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDatekey(calendar.getTime());

    }

    /**
     * @return date
     */
    public static Date getFirstDateOfMonth(Date date) {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();

    }

    /**
     * @return 20160101
     */
    public static Integer getFirstDayOfMonth(Date date) {
        Date firstDateOfMonth = getFirstDateOfMonth(date);
        return getDatekey(firstDateOfMonth);

    }

    /**
     * @return 20160101
     */
    public static Integer getDatekey(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_KEY_FORMAT);
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    public static Date getDateByDatekey(Integer datekey) {
        return getDate(datekey.toString(), DATE_KEY_FORMAT);
    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth(Date date) {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return getDatekey(calendar.getTime());

    }



    /**
     */
    public static Date getYesterDayDate(Date date) {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();

    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth(Integer hourAgo) {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.DATE, -1);
        calendar.add(Calendar.HOUR_OF_DAY, -hourAgo);// before  hour
        return getDatekey(calendar.getTime());

    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth() {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.DATE, -1);
        return getDatekey(calendar.getTime());

    }

    public static Date getDateByMonth(Integer month) {
        return getDate(month.toString(), YY_MM);
    }

    /**
     * @return 201601
     */
    public static Integer getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(YY_MM);
        String time = format.format(date);
        return Integer.valueOf(time);

    }

    /**
     * @return 201601
     */
    public static Integer getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.MONTH, -1);
        return getMonth(calendar.getTime());

    }

    /**
     * @return 201601
     */
    public static Integer getMonthOfLastYear() {
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MONTH, -1);
        return getMonth(calendar.getTime());

    }

    public static void main(String[] args) {

        Date date = DateUtils.getDate("1/1", DateUtils.HHmm);
        String dateformat = DateUtils.getDateformat(date, DateUtils.HHmm);

        System.out.println(dateformat);
        System.out.println(date);
    }

}
