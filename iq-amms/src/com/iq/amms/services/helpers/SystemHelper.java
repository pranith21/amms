package com.iq.amms.services.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.iq.data.DataUploadManager;
import org.iq.data.DataUploadManager.UploaderType;
import org.iq.data.DataUploader;
import org.iq.db.DataSet;
import org.iq.exception.DataUploaderException;
import org.iq.exception.DbException;
import org.iq.service.helpers.BaseHelper;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.file.FileUtil;

import com.iq.amms.valueobjects.DwellersMasterVO;

/**
 * @author Sam
 */
public class SystemHelper extends BaseHelper {

  private static String BIRTHDAY_QUERY = "SELECT PHONE_MOBILE, EMAIL_PRIMARY FROM DWELLERS_MASTER"
	  + " WHERE DATE_OF_BIRTH = ?";
  private static String ANNIVERSARY_QUERY = "SELECT PHONE_MOBILE, EMAIL_PRIMARY FROM DWELLERS_MASTER"
	  + " WHERE DATE_OF_ANNIVERSARY = ?";
  private static String PHONE_NUMBERS_QUERY = "SELECT PHONE_MOBILE,PHONE_RESIDENCE FROM DWELLERS_MASTER";

  public static final String MONTHLY_MAINTENANCE_PARAM_NAME = "Monthly Maintenance Rate";
  public static final String LAST_BILL_DATE_PARAM_NAME = "Last bill date";
  public static final String LATE_PAYMENT_PARAM_NAME = "Late Payment Amount";
  public static final String LATE_PAYMENT_PROCESSED = "Late Payment Processed";
  public static final String LATE_PAYMENT_DUE_DATE = "Late Payment Due Date";
  public static final String BOUNCE_CHARGES = "Bounce Charges";
  public static final String NEXT_BILL_DATE = "Next Bill Date";
  public static final String DOB_RUN_DATE = "DOB Run Date";

  /**
   * @throws Exception
   */
  public Double getMonthlyMaintenanceRate() throws Exception {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Double monthlyMaintRate = Double.valueOf(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.MONTHLY_MAINTENANCE_RATE));
	return monthlyMaintRate;
  }

  /**
   * @throws Exception
   */
  public void updateMonthlyMaintenanceRate(Double rate) throws Exception {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.MONTHLY_MAINTENANCE_RATE, rate.toString());
  }

  /**
   * @throws Exception
   */
  public String getLastBillDate() throws Exception {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	String lastBillDate = apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.LAST_BILL_DATE);
	return lastBillDate;
  }

  /**
   * @throws Exception
   */
  public void updateLastBillDate(Date date) throws Exception {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.LAST_BILL_DATE,
		DateUtil.dateToString(date, DateFormat.MMM_dd_yyyy));
  }

  /**
   * @param amount
   * @throws DbException
   */
  public void updateLatePaymentAmount(Double amount) throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.LATE_PAYMENT_AMOUNT, amount.toString());
  }

  /**
   * @return Double
   * @throws DbException
   */
  public Double getLatePaymentAmount() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Double latePaymentAmount = Double.valueOf(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_AMOUNT));
	return latePaymentAmount;
  }

  /**
   * @return Boolean
   * @throws DbException
   */
  public Boolean checkLatePaymentProcessed() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	String flag = apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_PROCESSED);

	if ("1".equals(flag)) {
	  return true;
	}
	return false;
  }

  /**
   * @param flag
   * @throws DbException
   */
  public void updateLatePaymentProcessed(int flag) throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.LATE_PAYMENT_PROCESSED, flag + "");
  }

  /**
   * @return Date
   * @throws DbException
   */
  public Date getLatePaymentDueDate() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Date latePaymentDueDate = DateUtil.stringToDate(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_DUE_DATE),
		DateFormat.MMM_dd_yyyy);
	return latePaymentDueDate;
  }

  /**
   * @throws DbException
   */
  public void updateLatePaymentDueDate() throws DbException {
	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	int latePayDays = Integer.valueOf(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_CALCULATION_DATE));
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.LATE_PAYMENT_DUE_DATE, DateUtil
			.dateToString(calculateDate(latePayDays), DateFormat.MMM_dd_yyyy));
  }

  /**
   * calculates the days in a month, if the value exceeds last day of the month,
   * last date is returned.
   * 
   * @param latePayDays
   * @return
   */
  public Date calculateDate(int latePaymentCalculationDate) {
	Date today = DateUtil.stringToDate(
		DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
		DateFormat.MMM_dd_yyyy);
	int monthEndDay = DateUtil.getMonthEndDate(
		DateUtil.Month.getMonth(today.getMonth()), today.getYear());
	Calendar latePayDate = Calendar.getInstance();
	latePayDate.setTime(today);
	if (latePaymentCalculationDate < monthEndDay) {
	  latePayDate.set(Calendar.DATE, latePaymentCalculationDate);
	}
	else {
	  latePayDate.set(Calendar.DATE, monthEndDay);
	}
	return latePayDate.getTime();
  }

  /**
   * @return Double
   * @throws DbException
   */
  public Double getBounceCharge() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Double charges = Double.valueOf(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.CHEQUE_BOUNCE_CHARGES));
	return charges;
  }

  public void updateBounceCharges(String bounceCharges) throws DbException {
	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.CHEQUE_BOUNCE_CHARGES, bounceCharges);
  }

  /**
   * @return Date
   * @throws DbException
   */
  public Date getNextBillDate() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Date nextBillDate = DateUtil.stringToDate(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.NEXT_BILL_DATE),
		DateFormat.MMM_dd_yyyy);
	return nextBillDate;
  }

  /**
   * @param dueDate
   * @throws DbException
   */
  public void updateNextBillDate(Date billDate) throws DbException {
	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.NEXT_BILL_DATE,
		DateUtil.dateToString(billDate, DateFormat.MMM_dd_yyyy));
  }

  /**
   * @return Date
   * @throws DbException
   */
  public Date getDOBRunDate() throws DbException {

	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	Date dobRunDate = DateUtil.stringToDate(apartmentMasterParamsHelper
		.getParam(ApartmentMasterParamsHelper.DOB_RUN_DATE),
		DateFormat.MMM_dd_yyyy);
	return dobRunDate;
  }

  /**
   * @param nextDate
   * @throws DbException
   */
  public void updateDOBRunDate(Date nextDate) throws DbException {
	ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	apartmentMasterParamsHelper.updateParam(
		ApartmentMasterParamsHelper.DOB_RUN_DATE,
		DateUtil.dateToString(nextDate, DateFormat.MMM_dd_yyyy));
  }

  /**
   * @return
   */
  public boolean createBackUp() {
	return mySqlSession.createDump(SystemConf.getAmmsHome() + "/dbbackup");
  }

  /**
	 * 
	 */
  public void uploadBackupFiles() {
	String backUpFolder = SystemConf.getAmmsHome() + "/dbbackup";
	if (FileUtil.isFileExists(backUpFolder)) {
	  List<File> fileList = FileUtil.listAllFiles(new File(backUpFolder));
	  for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
		File file = iterator.next();
		if (file != null) {
		  DataUploader dataUploader = null;
		  try {
			dataUploader = DataUploadManager
				.getDataUploader(UploaderType.DROPBOX);
		  }
		  catch (DataUploaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  if (dataUploader != null) {
			dataUploader.upload(file.getAbsolutePath());
		  }
		}
	  }
	}
  }

  /**
   * @param queryDate
   * @return List<String>
   * @throws DbException
   */
  public List<DwellersMasterVO> getBirthdayNumbersList(String queryDate)
	  throws DbException {
	List<DwellersMasterVO> list = new ArrayList<DwellersMasterVO>();
	DataSet dataSet = mySqlSession.executeQuery(BIRTHDAY_QUERY, queryDate);
	if (dataSet.getRowCount() > 0) {
	  for (int i = 0; i < dataSet.getRowCount(); i++) {
		DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();
		dwellersMasterVO.setPhoneMobile(dataSet.getStringValue(i,
			"PHONE_MOBILE"));
		dwellersMasterVO.setEmailPrimary(dataSet.getStringValue(i,
			"EMAIL_PRIMARY"));
		list.add(dwellersMasterVO);
	  }
	}
	return list;
  }

  /**
   * @param queryDate
   * @return List<String
   * @throws DbException
   */
  public List<DwellersMasterVO> getAnniversaryNumbersList(String queryDate)
	  throws DbException {
	List<DwellersMasterVO> list = new ArrayList<DwellersMasterVO>();
	DataSet dataSet = mySqlSession.executeQuery(ANNIVERSARY_QUERY, queryDate);
	if (dataSet.getRowCount() > 0) {
	  for (int i = 0; i < dataSet.getRowCount(); i++) {
		DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();
		dwellersMasterVO.setPhoneMobile(dataSet.getStringValue(i,
			"PHONE_MOBILE"));
		dwellersMasterVO.setEmailPrimary(dataSet.getStringValue(i,
			"EMAIL_PRIMARY"));
		list.add(dwellersMasterVO);
	  }
	}
	return list;
  }

  /**
   * @return List<String
   * @throws DbException
   */
  public List<DwellersMasterVO> getPhoneNumbersList() throws DbException {
	List<DwellersMasterVO> list = new ArrayList<DwellersMasterVO>();
	DataSet dataSet = mySqlSession.executeQuery(PHONE_NUMBERS_QUERY);
	if (dataSet.getRowCount() > 0) {
	  for (int i = 0; i < dataSet.getRowCount(); i++) {
		DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();
		dwellersMasterVO.setPhoneMobile(dataSet.getStringValue(i,
			"PHONE_MOBILE"));
		dwellersMasterVO.setPhoneResidence(dataSet.getStringValue(i,
			"PHONE_RESIDENCE"));
		list.add(dwellersMasterVO);
	  }
	}
	return list;
  }
}