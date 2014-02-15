package com.iq.amms.services.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.iq.db.DataSet;
import org.iq.exception.DbException;
import org.iq.service.helpers.BaseHelper;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

/**
 * @author Sam
 */
public class SystemHelper extends BaseHelper {

  private static final String SYSTEM_PARAMS_SELECT_QUERY =
      "SELECT PARAM_VALUE FROM SYSTEM_PARAMS WHERE PARAM_NAME = ?";
  private static final String SYSTEM_PARAMS_UPDATE_QUERY =
      "UPDATE SYSTEM_PARAMS SET PARAM_VALUE = ? WHERE PARAM_NAME = ?";

  public static final String MONTHLY_MAINTENANCE_PARAM_NAME =
      "Monthly Maintenance Rate";
  public static final String LAST_BILL_DATE_PARAM_NAME = "Last bill date";
  public static final String LATE_PAYMENT_PARAM_NAME = "Late Payment Amount";
  public static final String LATE_PAYMENT_PROCESSED = "Late Payment Processed";
  public static final String LATE_PAYMENT_DUE_DATE = "Late Payment Due Date";
  public static final String BOUNCE_CHARGES = "Bounce Charges";
  public static final String NEXT_BILL_DATE = "Next Bill Date";

  /**
   * @throws Exception
   */
  public Double getMonthlyMaintenanceRate() throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
            MONTHLY_MAINTENANCE_PARAM_NAME);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      String rate = dataSet.getStringValue(0, 0);
      return Double.valueOf(rate);
    }
    return 0.0;
  }

  /**
   * @throws Exception
   */
  public void updateMonthlyMaintenanceRate(Double rate) throws Exception {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, rate,
        MONTHLY_MAINTENANCE_PARAM_NAME);
  }

  /**
   * @throws Exception
   */
  public String getLastBillDate() throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
            LAST_BILL_DATE_PARAM_NAME);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      return dataSet.getStringValue(0, 0);
    }
    return "";
  }

  /**
   * @throws Exception
   */
  public void updateLastBillDate(Date date) throws Exception {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY,
        DateUtil.dateToString(date, DateFormat.MMM_dd_yyyy),
        LAST_BILL_DATE_PARAM_NAME);
  }

  /**
   * @param amount
   * @throws DbException
   */
  public void updateLatePaymentAmount(Double amount) throws DbException {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, amount,
        LATE_PAYMENT_PARAM_NAME);
  }

  /**
   * @return Double
   * @throws DbException
   */
  public Double getLatePaymentAmount() throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
            LATE_PAYMENT_PARAM_NAME);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      return Double.valueOf(dataSet.getStringValue(0, 0));
    }
    return null;
  }
  
  /**
   * @return Boolean
   * @throws DbException
   */
  public Boolean checkLatePaymentProcessed() throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
            LATE_PAYMENT_PROCESSED);
    if (dataSet != null && dataSet.getRowCount() > 0) {
    	String flag = dataSet.getStringValue(0, 0);
    	if ("1".equals(flag))
    	{
    		return true;
    	}
    }
    return false;
  }
  
  /**
   * @param flag
   * @throws DbException
   */
  public void updateLatePaymentProcessed(int flag) throws DbException {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, flag,
        LATE_PAYMENT_PROCESSED);
  }

  /**
   * @return Date
   * @throws DbException
   */
  public Date getLatePaymentDueDate() throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY, 
        		LATE_PAYMENT_DUE_DATE);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      return DateUtil.stringToDate(dataSet.getStringValue(0, 0), DateFormat.MMM_dd_yyyy);
    }
    return null;
  }
  
  /**
   * @param dueDate
   * @throws DbException
   */
  public void updateLatePaymentDueDate(Date dueDate) throws DbException {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, DateUtil.dateToString(dueDate, 
    		DateFormat.MMM_dd_yyyy),  LATE_PAYMENT_DUE_DATE);
  }
  
  /**
   * @return Double
   * @throws DbException
   */
  public Double getBounceCharge() throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
            BOUNCE_CHARGES);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      return Double.valueOf(dataSet.getStringValue(0, 0));
    }
    return null;
  }

  /**
   * @return Date
   * @throws DbException
   */
  public Date getNextBillDate() throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY, 
        		NEXT_BILL_DATE);
    if (dataSet != null && dataSet.getRowCount() > 0) {
      return DateUtil.stringToDate(dataSet.getStringValue(0, 0), DateFormat.MMM_dd_yyyy);
    }
    return null;
  }
  
  /**
   * @param dueDate
   * @throws DbException
   */
  public void updateNextBillDate(Date billDate) throws DbException {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, DateUtil.dateToString(billDate, 
    		DateFormat.MMM_dd_yyyy),  NEXT_BILL_DATE);
  }
}