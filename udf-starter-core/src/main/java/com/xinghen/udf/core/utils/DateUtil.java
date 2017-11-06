package com.xinghen.udf.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    private DateUtil() {
    }

    /**
     * 对日期(日)进行加减计算
     *
     * @param d
     * @param amount
     * @return
     */
    public static Date calculateByDate(Date d, int amount) {
        return calculate(d, GregorianCalendar.DATE, amount);
    }

    /**
     * 对日期(分钟)进行加减计算
     *
     * @param d
     * @param amount
     * @return
     */
    public static Date calculateByMinute(Date d, int amount) {
        return calculate(d, GregorianCalendar.MINUTE, amount);
    }

    /**
     * 对日期(年)进行加减计算
     *
     * @param d
     * @param amount
     * @return
     */
    public static Date calculateByYear(Date d, int amount) {
        return calculate(d, GregorianCalendar.YEAR, amount);
    }

    /**
     * 对日期(时间)中由field参数指定的日期成员进行加减计算
     *
     * @param d
     * @param field
     * @param amount
     * @return
     */
    private static Date calculate(Date d, int field, int amount) {
        if (d == null) {
            return null;
        }
        GregorianCalendar g = new GregorianCalendar();
        g.setGregorianChange(d);
        g.add(field, amount);
        return g.getTime();
    }

    /**
     * 日期转字符串
     *
     * @param formater
     * @param aDate
     * @return
     */
    public static String dateToString(String formater, Date aDate) {
        if (formater == null || "".equals(formater)) {
            return null;
        }
        if (aDate == null) {
            return null;
        }
        return (new SimpleDateFormat(formater)).format(aDate);
    }

    /**
     * 当前日期转为字符串
     *
     * @param formater
     * @return
     */
    public static String dateToString(String formater) {
        return dateToString(formater, new Date());
    }

    /**
     * 获取当前日期对应的星期
     *
     * @return
     */
    public static int dayOfWeek() {
        GregorianCalendar g = new GregorianCalendar();
        return g.get(java.util.Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取所有的时区编号
     *
     * @return
     */
    public static List<String> fecthAllTimeZoneIds() {
        List<String> v = new ArrayList<>();
        String[] ids = TimeZone.getAvailableIDs();
        Arrays.stream(ids).forEach(id -> { //NOSONAR
            v.add(id);
        });
        Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
        return v;
    }

    /**
     * 将日期时间字符串转换为指定时区的日期时间.
     * @param srcFormater
     * @param srcDateTime
     * @param dstFormater
     * @param dstTimeZoneId
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    public static String stringToTimezone(String srcFormater, String srcDateTime, String dstFormater, String dstTimeZoneId) throws ParseException, ParseException { //NOSONAR

        if (srcFormater == null || "".equals(srcFormater)) {
            return null;
        }
        if (srcDateTime == null || "".equals(srcDateTime)) {
            return null;
        }
        if (dstFormater == null || "".equals(dstFormater)) {
            return null;
        }
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
        int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
        Date d = sdf.parse(srcDateTime);
        long nowTime = d.getTime();
        long newNowTime = nowTime - diffTime;
        d = new Date(newNowTime);
        return dateToString(dstFormater, d);
    }

    /**
     * 获取系统当前默认时区与UTC的时间差.(单位:毫秒)
     * @return
     */
    public static int getDefaultTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset();
    }

    /**

     * <p>

     * Description: 返回UTC与指定时区的时间差

     * </p>

     *

     * @param timeZoneId 时区ID

     * @return 时差

     */
    public static String getUtcTimeZoneRawOffset(String timeZoneId) {
        return new SimpleDateFormat("HH:mm").format(getTimeZoneRawOffset(timeZoneId));
    }

    /**

     * 获取指定时区与UTC的时间差.(单位:毫秒)

     *

     * @param timeZoneId 时区Id

     * @return 指定时区与UTC的时间差.(单位=毫秒)

     */
    public static int getTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**

     * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)

     *

     * @param timeZoneId 时区Id

     * @return 系统当前默认时区与指定时区的时间差.(单位=毫秒)

     */
    private static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**

     * 将日期时间字符串根据转换为指定时区的日期时间.

     *

     * @param srcDateTime   待转化的日期时间.

     * @param dstTimeZoneId 目标的时区编号.

     * @return 转化后的日期时间.

     * @throws ParseException 异常

     */
    public static String stringToTimezoneDefault(String srcDateTime, String dstTimeZoneId) throws ParseException {
        return stringToTimezone("yyyy-MM-dd HH:mm:ss", srcDateTime, "yyyy-MM-dd HH:mm:ss", dstTimeZoneId);
    }

}
