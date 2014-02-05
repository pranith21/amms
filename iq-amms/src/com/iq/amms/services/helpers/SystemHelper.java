/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services.helpers;

import java.util.Date;

import org.iq.db.DataSet;
import org.iq.exception.DbException;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

/**
 * @author SC64807
 *
 */
public class SystemHelper extends BaseHelper {

  private static final String SYSTEM_PARAMS_SELECT_QUERY =
    "SELECT PARAM_VALUE FROM SYSTEM_PARAMS WHERE PARAM_NAME = ?";
  private static final String SYSTEM_PARAMS_UPDATE_QUERY = "UPDATE SYSTEM_PARAMS SET PARAM_VALUE = ? WHERE PARAM_NAME = ?";
  
  public static final String MONTHLY_MAINTENANCE_PARAM_NAME= "Monthly Maintenance Rate";
  public static final String LAST_BILL_DATE_PARAM_NAME= "Last bill date";
  public static final String LATE_PAYMENT_PARAM_NAME= "Late Payment Amount";
  
  /**
   * @throws Exception 
   * 
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
   * 
   */
  public void updateMonthlyMaintenanceRate(Double rate) throws Exception {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, rate,
        MONTHLY_MAINTENANCE_PARAM_NAME);
  }
  /**
   * @throws Exception 
   * 
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
   * 
   */
  public void updateLastBillDate(Date date) throws Exception {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, DateUtil
        .dateToString(date, DateFormat.MMM_DD_YYYY),
        LAST_BILL_DATE_PARAM_NAME);
  }
  
  /**
   * @param amount
   * @throws DbException
   */
  public void updateLatePaymentAmount(Double amount) throws DbException{
	  mySqlSession.executeUpdate(SYSTEM_PARAMS_UPDATE_QUERY, amount,
		        LATE_PAYMENT_PARAM_NAME);
  }
  
  /**
 * @return Double
 * @throws DbException
 */
public Double getLatePaymentAmount() throws DbException{
	  DataSet dataSet =
		        mySqlSession.executeQuery(SYSTEM_PARAMS_SELECT_QUERY,
		        		LATE_PAYMENT_PARAM_NAME);
		    if (dataSet != null && dataSet.getRowCount() > 0) {
		      return Double.valueOf(dataSet.getStringValue(0, 0));
		    }
		    return null;
  }
}