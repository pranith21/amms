/**
 * 
 */
package com.iq.amms.services.helpers;

import org.iq.db.DataSet;
import org.iq.exception.DbException;
import org.iq.service.helpers.BaseHelper;

/**
 * @author Sam
 *
 */
public class ApartmentMasterParamsHelper extends BaseHelper{

	private String aptMasterParamsSelectQuery =
			"SELECT %s FROM APARTMENT_MASTER_PARAMS WHERE APARTMENT_ID=?";
	private String aptMasterParamsUpdateQuery =
			"UPDATE APARTMENT_MASTER_PARAMS SET %s = ? WHERE APARTMENT_ID=?";

	public static String MONTHLY_MAINTENANCE_RATE = "MONTHLY_MAINTENANCE_RATE";
	public static String LAST_BILL_DATE = "LAST_BILL_DATE";
	public static String LATE_PAYMENT_AMOUNT = "LATE_PAYMENT_AMOUNT";
	public static String LATE_PAYMENT_DUE_DATE = "LATE_PAYMENT_DUE_DATE";
	public static String LATE_PAYMENT_PROCESSED = "LATE_PAYMENT_PROCESSED";
	public static String CHEQUE_BOUNCE_CHARGES = "CHEQUE_BOUNCE_CHARGES";
	public static String NEXT_BILL_DATE = "NEXT_BILL_DATE";
	public static String DOB_RUN_DATE = "DOB_RUN_DATE";
	public static String LATE_PAYMENT_CALCULATION_DATE="LATE_PAYMENT_CALCULATION_DATE";
	public static String LATE_PAYMENT_DISPLAY_DATE="LATE_PAYMENT_DISPLAY_DATE";
	public static String BILL_GENERATION_SMS="BILL_GENERATION_SMS";
	public static String BILL_GENERATION_MAIL="BILL_GENERATION_MAIL";
	public static String LATE_PAYMENT_SMS="LATE_PAYMENT_SMS";
	public static String LATE_PAYMENT_MAIL="LATE_PAYMENT_MAIL";
	public static String PAYMENTS_SMS="PAYMENTS_SMS";
	public static String PAYMENTS_MAIL="PAYMENTS_MAIL";
	public static String BIRTHDAY_SMS="BIRTHDAY_SMS";
	public static String BIRTHDAY_MAIL="BIRTHDAY_MAIL";
	public static String ANNIVERSARY_SMS="ANNIVERSARY_SMS";
	public static String ANNIVERSARY_MAIL="ANNIVERSARY_MAIL";
	public static String LATE_PAYMENT_MINIMUM_BALANCE="LATE_PAYMENT_MINIMUM_BALANCE";
	public static String LATE_PAYMENT_REMINDER_DAYS="LATE_PAYMENT_REMINDER_DAYS";
	public static String LATE_PAYMENT_REMINDER_SMS="LATE_PAYMENT_REMINDER_SMS";
	public static String LATE_PAYMENT_REMINDER_MAIL="LATE_PAYMENT_REMINDER_MAIL";
	public static String LATE_PAYMENT_REMINDER_SENT="LATE_PAYMENT_REMINDER_SENT";
	
	public String getParam(String paramName) throws DbException{
		String paramValue = null;
		String sql = String.format(aptMasterParamsSelectQuery, paramName);
		DataSet dataSet = mySqlSession.executeQuery(sql,1);
		if (dataSet!=null && dataSet.getRowCount()>0) {
			paramValue = dataSet.getStringValue(0, 0);
		}
		
		return paramValue;
	}
	
	public int updateParam(String paramName, String paramValue) throws DbException{
		String sql = String.format(aptMasterParamsUpdateQuery, paramName);
		return mySqlSession.executeUpdate(sql,paramValue,1);
	}
}
