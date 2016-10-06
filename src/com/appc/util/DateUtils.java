
package com.appc.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final TimeZone CHINA_TIMEZONE = TimeZone.getTimeZone("GMT+8");

	private static final SimpleDateFormat DATE_FORMAT_Month = new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat DATE_FORMAT_Day = new SimpleDateFormat("dd");

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_LINK_FORMAT = new SimpleDateFormat("yyyyMMdd");

	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat TIME_ONLY_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat DATETIMEHM_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat DATETIMEHMS_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final SimpleDateFormat CHINADATE_FORMAT = new SimpleDateFormat("yyyy?M?dd?");

	private static final SimpleDateFormat CHINADATE_MONTH_DATE_FORMAT = new SimpleDateFormat("M?dd?");
	private static final SimpleDateFormat CHINADATE_HOUR_MINUTE_FORMAT = new SimpleDateFormat("HH:mm");
	public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
	public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	public static final String FORMAT_YEAR = "yyyy";
	public static final String FORMAT_STARTDATE = "yyyy-MM-dd 00:00:00";
	public static final String FORMAT_ENDDATE = "yyyy-MM-dd 23:59:59";
	public static final String STRING_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final long MILLIS_PER_DAY = 86400000L;
	private static final SimpleDateFormat CHINADATE_YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat CHINADATE_YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat CHINADATE_YEAR_FORMAT = new SimpleDateFormat("yyyy");

	private static Map<Integer, String> weekMap = Collections.synchronizedMap(new HashMap());
	public static final int TIME_OUT = 30;

	public static String convertToDateTime(Date date) {
		return DATETIME_FORMAT.format(date);
	}

	public static String convertToCurrentDateTime(Date date) {
		return DATE_TIME_FORMAT.format(date);
	}

	public static String convertToDate(Date date) {
		return ((SimpleDateFormat) DATE_FORMAT.clone()).format(date);
	}

	public static String convertToMonth(Date date) {
		return DATE_FORMAT_Month.format(date);
	}

	public static String convertToDay(Date date) {
		return DATE_FORMAT_Day.format(date);
	}

	public static String convertToLinkDate(Date date) {
		return DATE_LINK_FORMAT.format(date);
	}

	public static Date parseLinkDate(String dateStr) throws ParseException {
		return DATE_LINK_FORMAT.parse(dateStr);
	}

	public static String convertToTime(Date date) {
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tf.setTimeZone(CHINA_TIMEZONE);
		return tf.format(date);
	}

	public static String convertToTimeHM(Date date) {
		return DATETIMEHM_FORMAT.format(date);
	}

	public static String convertToTimeHMS(Date date) {
		return DATETIMEHMS_FORMAT.format(date);
	}

	public static String convertToOnlyTime(Date date) {
		return TIME_ONLY_FORMAT.format(date);
	}

	public static boolean compare(Date lastLoginDate) {
		Date now = new Date();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(now);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(lastLoginDate);
		long tt = cal1.getTimeInMillis() - cal2.getTimeInMillis();

		return (tt / 60000L < 30L);
	}

	public static String dayForWeek(Date pTime) {
		String chinaDate = CHINADATE_FORMAT.format(pTime);
		Calendar c = Calendar.getInstance();
		c.setTime(pTime);
		int dayForWeek = 0;
		if (c.get(7) == 1)
			dayForWeek = 7;
		else {
			dayForWeek = c.get(7) - 1;
		}
		return chinaDate + " " + ((String) weekMap.get(Integer.valueOf(dayForWeek)));
	}

	public static String getSpecifiedDate(Date currentDate, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(5, calendar.get(5) + days);

		return convertToDate(calendar.getTime());
	}

	public static Date getNextMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		logger.debug(c.getTime().toString());
		c.set(2, c.get(2) + 1);
		c.set(5, 1);

		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);

		return c.getTime();
	}

	public static Date timeStamp2Date(String timestampString) {
		Long timestamp = Long.valueOf(Long.parseLong(timestampString) * 1000L);
		return new Date(timestamp.longValue());
	}

	public static int compareTo(Date date, Date date1) {
		String pattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String s = format.format(date);
		String s1 = format.format(date1);
		return s.compareTo(s1);
	}

	public static Date parseYearMonth(String dateStr) throws ParseException {
		Date d = CHINADATE_YEAR_MONTH_FORMAT.parse(dateStr);
		return d;
	}

	public static Date parseYearMonthDay(String dateStr) throws ParseException {
		Date d = CHINADATE_YEAR_MONTH_DAY_FORMAT.parse(dateStr);
		return d;
	}

	public static Date parseYear(String dateStr) throws ParseException {
		Date d = CHINADATE_YEAR_FORMAT.parse(dateStr);
		return d;
	}

	public static Date parseNextYear(String dateStr) throws ParseException {
		Date d = CHINADATE_YEAR_FORMAT.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(1, cal.get(1) + 1);
		return cal.getTime();
	}

	public static Date fromatStartDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		format.setTimeZone(CHINA_TIMEZONE);
		String s = format.format(date);
		Date d = null;
		try {
			logger.debug(s);

			SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tf.setTimeZone(CHINA_TIMEZONE);
			d = tf.parse(s);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return d;
	}

	public static Date fromatEndDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		format.setTimeZone(CHINA_TIMEZONE);
		String s = format.format(date);
		Date d = null;
		try {
			SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tf.setTimeZone(CHINA_TIMEZONE);
			d = tf.parse(s);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return d;
	}

	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		dd.setTimeZone(CHINA_TIMEZONE);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}

	public static boolean isValidYYmmddFormat(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		if (sDate != null) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			}
			return false;
		}

		return false;
	}

	public static boolean isValidHHmmssFormat(String sDate) {
		String regex = "((((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))";
		if (sDate != null) {
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(sDate);
			return match.matches();
		}
		return false;
	}

	public static String isExceed(String startDateStr, String endDateStr) {
		String isExceed = "1";
		DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dd.setTimeZone(CHINA_TIMEZONE);
		Date startDate = null;
		Date endDate = null;
		Date now = new Date();
		try {
			startDate = dd.parse(startDateStr + " 00:00:00");
			endDate = dd.parse(endDateStr + " 23:59:59");
			if ((compareTo(now, startDate) > 0) && (compareTo(endDate, now) > 0))
				isExceed = "0";
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return isExceed;
	}

	public static int daysBetweenEnddateAndNow(Date endDate) {
		int result = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(CHINA_TIMEZONE);
			Date sysDate = sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
			endDate = sdf.parse(sdf.format(endDate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			long endTime = cal.getTimeInMillis();
			cal.setTime(sysDate);
			long sysTime = cal.getTimeInMillis();
			if (endTime >= sysTime) {
				long between_days = (endTime - sysTime) / 86400000L;
				result = Integer.parseInt(String.valueOf(between_days + 1L));
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static String getTimeInterval(Date createTime, Date now) throws ParseException {
		long millisecondInterval = now.getTime() - createTime.getTime();

		if (millisecondInterval < 3600000L) {
			if (millisecondInterval < 60000L) {
				return "??";
			}
			return String.valueOf(millisecondInterval / 60000L) + "???";
		}
		if ((millisecondInterval >= 3600000L) && (millisecondInterval < 86400000L)) {
			return String.valueOf(millisecondInterval / 3600000L) + "???";
		}
		if ((millisecondInterval >= 86400000L) && (millisecondInterval < 604800000L)) {
			return String.valueOf(millisecondInterval / 86400000L) + "??";
		}
		return CHINADATE_MONTH_DATE_FORMAT.format(createTime);
	}

	public static long getTimeMinus(Date passDate, Date nowDate) throws ParseException {
		Long checkday = Long.valueOf((nowDate.getTime() - passDate.getTime()) / 86400000L);
		return checkday.longValue();
	}

	public static Calendar getMillisecond(long time) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeZone(CHINA_TIMEZONE);
		calendar.setTimeInMillis(time);

		return calendar;
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "???", "???", "???", "???", "???", "???", "???" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(7) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	public static String convertToHourMinute(Date date) {
		return CHINADATE_HOUR_MINUTE_FORMAT.format(date);
	}

	public static Calendar getChinaCalendar(long time) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeZone(CHINA_TIMEZONE);
		calendar.setTimeInMillis(time);
		return calendar;
	}

	public static boolean compareDates(Date nextTime, int day) throws ParseException {
		boolean flag = false;
		long l = nextTime.getTime() + 86400000 * day;
		long days = getTimeMinus(new Date(l), new Date());
		if (days > 0L) {
			flag = true;
		}
		return flag;
	}

	public static String dateToStringYYYY_MM_DD(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	public static String dateToString(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	public static Date offset(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(5, days);
		return cal.getTime();
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(compareDates(new Date(new Date().getTime() - 259200000L), 4));
	}

	static {
		DATE_FORMAT.setTimeZone(CHINA_TIMEZONE);
		TIME_FORMAT.setTimeZone(CHINA_TIMEZONE);
		DATETIME_FORMAT.setTimeZone(CHINA_TIMEZONE);
		DATETIMEHM_FORMAT.setTimeZone(CHINA_TIMEZONE);
		CHINADATE_FORMAT.setTimeZone(CHINA_TIMEZONE);
		DATE_LINK_FORMAT.setTimeZone(CHINA_TIMEZONE);
		DATETIMEHMS_FORMAT.setTimeZone(CHINA_TIMEZONE);
		CHINADATE_YEAR_MONTH_FORMAT.setTimeZone(CHINA_TIMEZONE);
		CHINADATE_YEAR_FORMAT.setTimeZone(CHINA_TIMEZONE);

		weekMap.put(Integer.valueOf(1), "???");
		weekMap.put(Integer.valueOf(2), "???");
		weekMap.put(Integer.valueOf(3), "???");
		weekMap.put(Integer.valueOf(4), "???");
		weekMap.put(Integer.valueOf(5), "???");
		weekMap.put(Integer.valueOf(6), "???");
		weekMap.put(Integer.valueOf(7), "???");
	}
}