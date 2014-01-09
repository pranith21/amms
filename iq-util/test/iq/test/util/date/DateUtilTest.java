package iq.test.util.date;

import iq.test.BaseTestCase;

import java.util.Calendar;
import java.util.Date;

import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.date.DateUtil.DatePart;
import org.iq.util.date.DateUtil.Month;

/**
 * @author Sam
 * 
 */
public class DateUtilTest extends BaseTestCase {

  private static Date date = null;

  /**
   * 
   */
  public void setUp() {
    date =
        DateUtil.stringToDate("2013-04-25 13:37:32.800",
            DateFormat.yyyy_MM_dd_hh_mm_ss_SSS);
  }

  /**
   * 
   */
  public void testIsLeapYear() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2012);
    assertEquals(true, DateUtil.isLeapYear(calendar.getTime()));
  }

  /**
   * 
   */
  public void testIsNotLeapYear() {
    assertEquals(false, DateUtil.isLeapYear(date));
  }

  /**
   * 
   */
  public void testGetDay() {
    assertEquals("Thursday", DateUtil.getDay(date));
  }

  /**
   * 
   */
  public void testGetMonthEndDateLeap() {
    assertEquals(29, DateUtil.getMonthEndDate(Month.FEBRUARY, 2012));
  }

  /**
   * 
   */
  public void testGetMonthEndDateArgDate() {
    assertEquals(29, DateUtil.getMonthEndDate(DateUtil.stringToDate(
        "2012-02-03 13:37:32.800", DateFormat.yyyy_MM_dd_hh_mm_ss_SSS)));
  }

  /**
   * 
   */
  public void testGetMonthEndDateArgMonth() {
    assertEquals(31, DateUtil.getMonthEndDate(Month.JANUARY));
  }

  /**
   * 
   */
  public void testGetMonthEndDateArgMonthYear() {
    assertEquals(31, DateUtil.getMonthEndDate(Month.JANUARY, 123456));
  }

  /**
   * 
   */
  public void testAddYearToDate() {
    assertEquals(DateUtil.stringToDate("2018-04-25 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.YEAR));
  }

  /**
   * 
   */
  public void testAddMonthToDate() {
    assertEquals(DateUtil.stringToDate("2013-09-25 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.MONTH));
  }

  /**
   * 
   */
  public void testAddWeekToDate() {
    assertEquals(DateUtil.stringToDate("2013-05-30 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.WEEK));
  }

  /**
   * 
   */
  public void testAddDayToDate() {
    assertEquals(DateUtil.stringToDate("2013-04-30 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.DATE));
  }

  /**
   * 
   */
  public void testAddHourToDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 18:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.HOUR));
  }

  /**
   * 
   */
  public void testAddMinuteToDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:42:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.MINUTE));
  }

  /**
   * 
   */
  public void testAddSecondToDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:37:37.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.SECOND));
  }

  /**
   * 
   */
  public void testAddMillisecondToDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:37:32.805",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.addToDate(date, 5,
        DatePart.MILLI_SECOND));
  }

  /**
   * 
   */
  public void testSubtractYearFromDate() {
    assertEquals(DateUtil.stringToDate("2008-04-25 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.YEAR));
  }

  /**
   * 
   */
  public void testSubtractMonthFromDate() {
    assertEquals(DateUtil.stringToDate("2012-11-25 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.MONTH));
  }

  /**
   * 
   */
  public void testSubtractWeekFromDate() {
    assertEquals(DateUtil.stringToDate("2013-03-21 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.WEEK));
  }

  /**
   * 
   */
  public void testSubtractDayFromDate() {
    assertEquals(DateUtil.stringToDate("2013-04-20 13:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.DATE));
  }

  /**
   * 
   */
  public void testSubtractHourFromDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 08:37:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.HOUR));
  }

  /**
   * 
   */
  public void testSubtractMinuteFromDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:32:32.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.MINUTE));
  }

  /**
   * 
   */
  public void testSubtractSecondFromDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:37:27.800",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.SECOND));
  }

  /**
   * 
   */
  public void testSubtractMillisecondFromDate() {
    assertEquals(DateUtil.stringToDate("2013-04-25 13:37:32.795",
        DateFormat.yyyy_MM_dd_hh_mm_ss_SSS), DateUtil.subtractFromDate(date,
        5, DatePart.MILLI_SECOND));
  }

  /**
   * 
   */
  public void testdaysBetweenDates() {
    Date date1 =
        DateUtil.stringToDate("2012-02-27 00:00:00.000",
            DateFormat.yyyy_MM_dd_hh_mm_ss_SSS);
    Date date2 =
        DateUtil.stringToDate("2012-03-01 23:59:59.999",
            DateFormat.yyyy_MM_dd_hh_mm_ss_SSS);
    System.out.println(DateUtil.daysBetweenDates(date1, date2, false));
  }
}