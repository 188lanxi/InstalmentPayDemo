package com.lanxi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author wuxiaobo created in 2018/2/11 17:17
 */
public interface TimeAssist {
    //日期格式格式
    static DateTimeFormatter DAY_FORMATTER=DateTimeFormatter.ofPattern("yyyyMMdd");
    //时间格式
    static DateTimeFormatter TIME_FORMATTER=DateTimeFormatter.ofPattern("HHmmss");
    //yyyyMMddHHmmss格式
    static DateTimeFormatter DAY_TIME_FORMATTER=DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    //字符串形式获取今天的年月日
    static String today(){
        return DAY_FORMATTER.format(LocalDateTime.now());
    }

    //字符串形式获取n天以后的年月日
    static String nDayLater(int n){
        return DAY_FORMATTER.format(LocalDateTime.now().plusDays(n));
    }
    //获取n小时之后的时间
    static String nHourLater(int n){
        return DAY_TIME_FORMATTER.format(LocalDateTime.now().plusHours(n));
    }
    //获取n分钟之后的时间
    static String nMinuteLater(int n){return DAY_TIME_FORMATTER.format(LocalDateTime.now().plusMinutes(n));}
    //字符串形式获取当前的时分秒
    static String now(){
        return TIME_FORMATTER.format(LocalDateTime.now());
    }
    //字符串形式获取年月日时分秒
    static String todayFull(){
        return DAY_TIME_FORMATTER.format(LocalDateTime.now());
    }
    //获取指定格式的时间
    static String geTime(String pattern){
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }
    /**
     * 根据 两个Date 判断时间差是否在指定时间内
     * @param startDate
     * @param endDate
     * @param timeOut 小时
     * @return
     */
    public static boolean isBetweenDate(Date startDate, Date endDate, long timeOut) {

        long between = endDate.getTime() - startDate.getTime();
        if(between <= (timeOut * 3600000)){
            return true;
        }
        return false;
    }

    /**
     * 获取两个时间之间的时间差(秒)
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimeDifference(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date start=sdf.parse(startTime);
        Date end=sdf.parse(endTime);
        return (end.getTime()-start.getTime())/1000;
    }

    /**
     * 获取两个时间之间相差的天数
     * @param startDate yyyyMMdd
     * @param endDate   yyyyMMdd
     * @return
     */
    public static int between(String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return (int)start.until(end, ChronoUnit.DAYS);
    }

    /**
     * 获取指定时间 指定天后的时间 yyyyMMdd
     * @param date
     * @param day
     * @return
     */
    public static String getNDaysLater(String date, Long day){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localDate1 = localDate.plusDays(day);
        return DAY_FORMATTER.format(localDate1);
    }

    /**
     * 获取指定时间 指定月后的世家 yyyyMMdd
     * @param date
     * @param month
     * @return
     */
    public static String getNMonthLater(String date, Long month){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localDate1 = localDate.plusMonths(month);
        return DAY_FORMATTER.format(localDate1);
    }
}
