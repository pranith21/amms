package org.iq.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.iq.util.string.StringUtil;

/**
 * @author Sam
 * 
 */
public class DateUtil {

  /**
   * 
   * enum for date format
   */
  public enum DatePart {
	/**
	 * constant for year
	 */
	YEAR(Calendar.YEAR),
	/**
	 * constant for month
	 */
	MONTH(Calendar.MONTH),
	/**
	 * constant for date
	 */
	WEEK(100),
	/**
	 * constant for date
	 */
	DATE(Calendar.DATE),
	/**
	 * constant for hour
	 */
	HOUR(Calendar.HOUR),
	/**
	 * constant for minute
	 */
	MINUTE(Calendar.MINUTE),
	/**
	 * constant for second
	 */
	SECOND(Calendar.SECOND),
	/**
	 * constant for millisecond
	 */
	MILLI_SECOND(Calendar.MILLISECOND);

	private final int datePart;

	DatePart(int partType) {
	  datePart = partType;
	}

	/**
	 * @return dateFormat
	 */
	public int getDatePartValue() {
	  return datePart;
	}

	/**
	 * @param type
	 * @return DateFormat
	 */
	public static DatePart getDatePart(int type) {
	  DatePart datePart = null;
	  for (DatePart part : DatePart.values()) {
		if (part.getDatePartValue() == type) {
		  datePart = part;
		  break;
		}
	  }
	  return datePart;
	}
  }

  /**
   * 
   * enum for month
   */
  public enum Month {
	/**
	 * Value of the {@link Month} field indicating the first month of the year.
	 */
	JANUARY(Calendar.JANUARY),
	/**
	 * Value of the {@link Month} field indicating the second month of the year.
	 */
	FEBRUARY(Calendar.FEBRUARY),
	/**
	 * Value of the {@link Month} field indicating the third month of the year.
	 */
	MARCH(Calendar.MARCH),
	/**
	 * Value of the {@link Month} field indicating the fourth month of the year.
	 */
	APRIL(Calendar.APRIL),
	/**
	 * Value of the {@link Month} field indicating the fifth month of the year.
	 */
	MAY(Calendar.MAY),
	/**
	 * Value of the {@link Month} field indicating the sixth month of the year.
	 */
	JUNE(Calendar.JUNE),
	/**
	 * Value of the {@link Month} field indicating the seventh month of the
	 * year.
	 */
	JULY(Calendar.JULY),
	/**
	 * Value of the {@link Month} field indicating the eighth month of the year.
	 */
	AUGUST(Calendar.AUGUST),
	/**
	 * Value of the {@link Month} field indicating the ninth month of the year.
	 */
	SEPTEMBER(Calendar.SEPTEMBER),
	/**
	 * Value of the {@link Month} field indicating the tenth month of the year.
	 */
	OCTOBER(Calendar.OCTOBER),
	/**
	 * Value of the {@link Month} field indicating the eleventh month of the
	 * year.
	 */
	NOVEMBER(Calendar.NOVEMBER),
	/**
	 * Value of the {@link Month} field indicating the twelfth month of the
	 * year.
	 */
	DECEMBER(Calendar.DECEMBER),
	/**
	 * Value of the {@link Month} field indicating the thirteenth month of the
	 * year. Although <code>GregorianCalendar</code> does not use this value,
	 * lunar calendars do.
	 */
	UNDECIMBER(Calendar.UNDECIMBER);

	private final int month;

	Month(int inputMonth) {
	  month = inputMonth;
	}

	/**
	 * @return dateFormat
	 */
	public int getMonthValue() {
	  return month;
	}

	/**
	 * @param inputMonth
	 * @return DateFormat
	 */
	public static Month getMonth(int inputMonth) {
	  Month retMonth = null;
	  for (Month thisMonth : Month.values()) {
		if (thisMonth.getMonthValue() == inputMonth) {
		  retMonth = thisMonth;
		  break;
		}
	  }
	  return retMonth;
	}
  }

  /**
   * 
   * enum for date format
   */
  public enum DateFormat {
	/**
	 * constant for MMMyy date format
	 */
	MMM_yy("MMM-yy"),
	/**
	 * constant for MMM-dd-yyyy date format
	 */
	MMM_dd_yyyy("MMM-dd-yyyy"),
	/**
	 * constant for yyyy-MM-dd date format
	 */
	yyyy_MM_dd("yyyy-MM-dd"),
	/**
	 * constant for HH-mm-ss-SSS date format
	 */
	HH_mm_ss_SSS("HH-mm-ss-SSS"),
	/**
	 * constant for MMddyyyy date format
	 */
	MM_dd_yyyy("MMddyyyy"),
	/**
	 * constant for yyyy-MM-dd hh:mm:ss.SSS date format
	 */
	yyyy_MM_dd_hh_mm_ss_SSS("yyyy-MM-dd hh:mm:ss.SSS"),
	/**
	 * constant for yyyy-MM-dd-hh:mm:ss.SSSZ date format
	 */
	yyyy_MM_dd_hh_mm_ss_SSS_Z("yyyy-MM-dd-hh:mm:ss.SSSZ"),
	/**
	 * constant for EEE MMM dd hh:mm:ss z yyyy date format
	 */
	EEE_MMM_dd_hh_mm_ss_z_yyyy("EEE MMM dd hh:mm:ss z yyyy"),
	/**
	 * constant for EEEE date format
	 */
	EEEE("EEEE");

	private final String dateFormat;

	DateFormat(String type) {
	  dateFormat = type;
	}

	/**
	 * @return dateFormat
	 */
	public String getDateFormatValue() {
	  return dateFormat;
	}

	/**
	 * @param type
	 * @return DateFormat
	 */
	public static DateFormat getDateFormat(String type) {
	  DateFormat dateFormat = null;
	  for (DateFormat stat : DateFormat.values()) {
		if (stat.getDateFormatValue().equals(type)) {
		  dateFormat = stat;
		  break;
		}
	  }
	  return dateFormat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	  return dateFormat;
	}
  }

  /**
   * Checks if year part of <code>date</code> is a leap year.
   * 
   * @param year
   * @return boolean
   */
  public static boolean isLeapYear(Date date) {
	return isLeapYear(getCalendar(date).get(Calendar.YEAR));
  }

  /**
   * Checks if <code>year</code> is a leap year.
   * 
   * @param year
   * @return boolean
   */
  public static boolean isLeapYear(int year) {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, year);
	return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
  }

  /**
   * @param date
   * @return String
   */
  public static String getDay(Date date) {
	return dateToString(date, DateFormat.EEEE);
  }

  /**
   * @return int
   */
  public static int getMonthEndDate() {
	return getMonthEndDate(new Date());
  }

  /**
   * @param date
   * @return int
   */
  public static int getMonthEndDate(Date date) {
	return getMonthEndDate(getCalendar(date));
  }

  /**
   * @return int
   */
  public static int getMonthEndDate(Month month) {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.MONTH, month.getMonthValue());
	return getMonthEndDate(calendar);
  }

  /**
   * @return int
   */
  public static int getMonthEndDate(Month month, int year) {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month.getMonthValue());
	return getMonthEndDate(calendar);
  }

  /**
   * @return int
   */
  private static int getMonthEndDate(Calendar calendar) {
	return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * Converts <code>java.util.Date</code> object From <code>String</code> based
   * on the format specified by <code>dateFormatStr</code>
   * 
   * @param date
   * @param dateFormatStr
   * @return String
   */
  public static String dateToString(Date date, String dateFormatStr) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
	if (date == null) {
	  return null;
	}
	return dateFormat.format(date);
  }

  /**
   * Converts <code>java.util.Date</code> object to <code>String</code> based on
   * the format specified by <code>dateFormat</code>
   * 
   * @param date
   * @param dateFormat
   * @return String
   */
  public static String dateToString(Date date, DateFormat dateFormat) {
	return dateToString(date, dateFormat.getDateFormatValue());
  }

  /**
   * Converts <code>String</code> object to <code>java.util.Date</code> based on
   * the format specified by <code>dateFormatStr</code>
   * 
   * @param dateStr
   * @param dateFormatStr
   * @return Date
   */
  public static Date stringToDate(String dateStr, String dateFormatStr) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
	Date retDate = null;
	if (!StringUtil.isEmpty(dateStr)) {
	  try {
		retDate = dateFormat.parse(dateStr);
	  }
	  catch (ParseException e) {
		// TODO
		e.printStackTrace();
	  }
	}
	return retDate;
  }

  /**
   * Converts <code>String</code> object to <code>java.util.Date</code> based on
   * the format specified by <code>dateFormat</code>
   * 
   * @param dateStr
   * @param dateFormat
   * @return Date
   */
  public static Date stringToDate(String dateStr, DateFormat dateFormat) {
	return stringToDate(dateStr, dateFormat.getDateFormatValue());
  }

  /**
   * @param date
   * @param value
   * @return Date
   */
  public static Date addToDate(Date date, int value) {
	return addToDate(date, value, DatePart.DATE);
  }

  /**
   * @param date
   * @param value
   * @param month
   * @return
   */
  public static Date addToDate(Date date, int value, DatePart datePart) {
	switch (datePart) {
	  case YEAR:
		return addYearToDate(date, value);
	  case MONTH:
		return addMonthToDate(date, value);
	  case WEEK:
		return addWeekToDate(date, value);
	  case DATE:
		return addDaysToDate(date, value);
	  case HOUR:
		return addHoursToDate(date, value);
	  case MINUTE:
		return addMinutesToDate(date, value);
	  case SECOND:
		return addSecondsToDate(date, value);
	  case MILLI_SECOND:
		return addMillisecondsToDate(date, value);
	  default:
		return date;
	}
  }

  /**
   * @param date
   * @return Calendar
   */
  private static Calendar getCalendar(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	return calendar;
  }

  /**
   * Adds <code>value</code> years to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return Date
   */
  private static Date addYearToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.YEAR, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> months to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return Date
   */
  private static Date addMonthToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.MONTH, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> weeks to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return Date
   */
  private static Date addWeekToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.DATE, 7 * value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> days to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return Date
   */
  private static Date addDaysToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.DATE, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> hours to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return
   */
  private static Date addHoursToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.HOUR, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> minutes to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return
   */
  private static Date addMinutesToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.MINUTE, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> seconds to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return
   */
  private static Date addSecondsToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.SECOND, value);
	return calendar.getTime();
  }

  /**
   * Adds <code>value</code> milliseconds to <code>date</code>.
   * 
   * @param date
   * @param value
   * @return
   */
  private static Date addMillisecondsToDate(Date date, int value) {
	Calendar calendar = getCalendar(date);
	calendar.add(Calendar.MILLISECOND, value);
	return calendar.getTime();
  }

  /**
   * @param date
   * @param value
   * @param month
   * @return
   */
  public static Date subtractFromDate(Date date, int value, DatePart datePart) {
	return addToDate(date, (value * (-1)), datePart);
  }

  /**
   * 
   */
  public static int daysBetweenDates(Date date1, Date date2,
	  boolean datesInclusive) {
	long diffInMillis = date1.getTime() - date2.getTime();
	int days = (int) ((diffInMillis < 0 ? diffInMillis * -1 : diffInMillis) / (24 * 3600 * 1000));
	return datesInclusive ? days + 1 : days - 1;
  }
}