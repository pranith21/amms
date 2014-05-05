package com.iq.amms.runners;

import java.util.Date;

import org.iq.exception.DbException;
import org.iq.runner.BaseRunner;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.date.DateUtil.DatePart;

import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.SystemHelper;



/**
 * @author Sam
 * 
 */
public class BillGenerator extends BaseRunner {
  
  private static BillGenerator generatorInstance = null;

  /**
   * 
   */
  private BillGenerator() {
	super("Bill Generator Thread");
  }
  
  public static void generate() {
	if (generatorInstance==null) {
	  generatorInstance = new BillGenerator();
	}
	generatorInstance.startRunner();
  }

  @Override
  protected boolean preCheckConditions() {
	try {
	  SystemHelper systemHelper = new SystemHelper();
	  Date nextBillDate = systemHelper.getNextBillDate();
	  Date today = DateUtil.stringToDate(
		  DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
		  DateFormat.MMM_dd_yyyy);
	  int i = nextBillDate.compareTo(today);
	  if (i <= 0) {
		return true;
	  }
	}
	catch (DbException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	System.out.println("Bill Generation Not Done.");
	return false;
  }

  @Override
  protected void performAction() {
	try {
	  SystemHelper systemHelper = new SystemHelper();
	  BillHelper billHelper = new BillHelper();

	  systemHelper.createBackUp();
	  systemHelper.uploadBackupFiles();

	  Date today = DateUtil.stringToDate(
		  DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
		  DateFormat.MMM_dd_yyyy);

	  billHelper.generateInvoices(today);
	  systemHelper.updateNextBillDate(DateUtil.addToDate(today, 1,
		  DatePart.MONTH));
	  systemHelper.updateLatePaymentDueDate();
	  systemHelper.updateLatePaymentProcessed(0);
	  ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	  apartmentMasterParamsHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SENT, "0");
	  System.out.println("BILL GENERATION DONE.");
	}
	catch (DbException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }
}