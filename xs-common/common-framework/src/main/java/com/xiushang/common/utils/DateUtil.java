package com.xiushang.common.utils;

import com.xiushang.common.exception.ServiceException;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Date Utility Class
 * This is used to convert Strings to Dates and Timestamps
 */
public class DateUtil {
    //~ Static fields/initializers =============================================

	/**
	 * Log
	 */
    private static Log log = LogFactory.getLog(DateUtil.class);
    /**
     * defaultDatePattern
     */
    private static String defaultDatePattern = null;
    /**
     * timePattern
     */
    public static String timePattern = "HH:mm";

    //日期完整格式
    public static String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    //~ Methods ================================================================

    /**
     * Return default datePattern (MM/dd/yyyy)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        defaultDatePattern = "yyyy-MM-dd";

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 转换日期字符串为日期类
     *
     * @param aMask 日期格式 如"yyyy-MM-dd"
     * @param strDate 需要转换的字符串
     * @return 日期类
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate){
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                      + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * 取出当前的系统日期
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * 转换一个日期类指定格式字符串
     * @param aMask 日期格式 如"yyyy-MM-dd"
     * @param aDate 需要转换的日期类
     * @return 转换后的字符串
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    public static final String convertDateToString(String pattern,Date aDate) {
        return getDateTime(pattern, aDate);
    }
    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     *
     */
    public static Date convertStringToDate(String strDate) {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (Exception pe) {
            log.error("Could not convert '" + strDate
                      + "' to a date, throwing exception");
            pe.printStackTrace();

        }

        return aDate;
    }

    /**
     * 增加或减少天数
     *
     * @param date 日期
     * @param days 增加或减少的天数(+/-)
     * @return 处理后的日期
     */
    public static Date addDays(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        //calendar.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 增加或减少月份
     *
     * @param date 日期
     * @param months 增加或减少的月份(+/-)
     * @return 处理后的日期
     */
    public static Date addDMonth(Date date, int months)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 增加或减少年份
     *
     * @param date 日期
     * @param year 增加或减少的年份(+/-)
     * @return 处理后的日期
     */
    public static Date addDYear(Date date, int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }


    /**
     * 取得一年前当月的第一天
     *
     * @param date 日期
     * @param year 增加或减少的年份(+/-)
     * @return 处理后的日期
     */
    public static Date getLastYear(Date date, int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 转换字符串日期格式,比如从"yyyy-MM-dd" to "yyyy/MM/dd"
     * @param sDate 源字符串日期
     * @param sMask 源日期格式 如"yyyy-MM-dd"
     * @param dMask 目标日期格式 如"yyyy/MM/dd"
     * @return
     */
    public static String convertDateFormat(String sDate, String sMask,
			String dMask) {
    	String retStr = "";
    	try {
			Date date = convertStringToDate(sMask, sDate);
			retStr = getDateTime(dMask, date);

		} catch (Exception e) {
			log.warn(e.getMessage());
			return "";
		}
    	//getDateTime
    	return retStr;
    }

    /**
     * 比较两个日期的先后
     * @param date1 日期1
     * @param date2 日期2
     * @return '1' 日期1>日期2<br> '-1' 日期2>日期1<br> '0' 日期1=日期2
     */
    public static int compareDate(Date date1, Date date2)
    {
    	return date1.compareTo(date2);
    }

    /**
     * 比较两个日期的先后
     * @param date1 日期1 "yyyy-MM-dd"
     * @param date2 日期2 "yyyy-MM-dd"
     * @param mask 格式"yyyy-MM-dd" "yyyy/MM/dd"
     * @return '1' 日期1>日期2<br> '-1' 日期2>日期1<br> '0' 日期1=日期2
     * @throws ParseException
     */
    public static int compareDate(String date1, String date2, String mask) throws ParseException
    {

		Date d1 = convertStringToDate(mask, date1);
		Date d2 = convertStringToDate(mask, date2);
		if(DateUtils.isSameDay(d1, d2)){
			return 0;
		}
    	return compareDate(d1, d2);
    }

    /**
     * 取得某月的最后一天
     * @param date1 yyyy/MM/dd
     * @return 取得结果
     * @throws ServiceException
     */
    @SuppressWarnings("unused")
	public static String getLastDateOfMonth(String date1) throws ServiceException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = convertStringToDate("yyyy/MM/dd", date1);
            //取得下个月的第一天
            Date nextMonth = addDMonth(date, 1);
            Date lastDateOfMonth = addDays(nextMonth, -1);
            return getDateTime("yyyy/MM/dd", lastDateOfMonth);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }


    /**
	 * 判断日期是否为周末(周六周日)
	 *
	 * @param date1
	 *            yyyy-MM-dd
	 * @return boolean 是否为周末(false:否,true:是)
	 * @throws ParseException
	 */
	public static boolean isWeekEnd(String date1) throws ParseException {
		// 转换时间
		Date date = convertStringToDate("yyyy-MM-dd", date1);
		// 获得时间类
		Calendar calendar = Calendar.getInstance();
		// 设置时间
		calendar.setTime(date);
		// 取得当前日期对应的周
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		// 如果是周六或者周日
		if (Calendar.SATURDAY == i || Calendar.SUNDAY == i) {
			// 返回是周末
			return true;
		} else {
			// 返回不是周末
			return false;
		}

	}



}
