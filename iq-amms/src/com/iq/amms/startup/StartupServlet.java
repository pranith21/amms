package com.iq.amms.startup;

import java.io.IOException;
import java.util.Date;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.iq.comm.CommunicatorThread;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

import com.iq.amms.runners.BillGenerator;
import com.iq.amms.runners.LatePaymentReminderThread;
import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.SystemHelper;

/**
 * @author Sam
 */
public class StartupServlet extends GenericServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 375299886649118252L;

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#init()
   */
  @Override
  public void init() throws ServletException {
	System.out.println("STARTING AMMS");

	super.init();

	SystemHelper systemHelper = new SystemHelper();
	ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
	BillHelper billHelper = new BillHelper();
	try {
	  // Calculating late payments
	  System.out.println("STARTING LATE PAYMENTS PROCESSING...");
	  if (!systemHelper.checkLatePaymentProcessed()) {
		Date dueDate = systemHelper.getLatePaymentDueDate();
		Date today =  
		  DateUtil.stringToDate(
			  DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
			  DateFormat.MMM_dd_yyyy);
		int i = dueDate.compareTo(today);
		if (i < 0) {
		  systemHelper.createBackUp();
		  systemHelper.uploadBackupFiles();
		  billHelper.processLatePayments();
		  systemHelper.updateLatePaymentProcessed(1);
		  System.out.println("LATE PAYMENTS PROCESSING DONE.");
		  /*if ("1".equals(userParamsHelper.getUserParamValue(UserParamsHelper.LATE_PAYMENT_SMS))) {
					  systemHelper.postLatePaymentMessages();
				  }*/
		}
		else {
		  System.out.println("LATE PAYMENTS PROCESSING NOT DONE.");
		}
	  }
	  else {
		System.out.println("LATE PAYMENTS PROCESSING NOT DONE.");
	  }

	  // Generating Bill
	  System.out.println("STARTING BILL GENERATION THREAD...");
	  BillGenerator.generate();


	  /*if ("1".equals(userParamsHelper.getUserParamValue(UserParamsHelper.BIRTHDAY_SMS)) || 
				  "1".equals(userParamsHelper.getUserParamValue(UserParamsHelper.ANNIVERSARY_SMS))) {
			  //Send messages for Birthdays and Anniversaries
			  DOBSMSThread dobsmsRunner = new DOBSMSThread();
			  dobsmsRunner.start();
		  }*/

	  if ("0".equals(apartmentMasterHelper
		  .getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SENT))
		  && ("1".equals(apartmentMasterHelper
			  .getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SMS)) || "1"
			  .equals(apartmentMasterHelper
				  .getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_MAIL)))) {
		LatePaymentReminderThread.startThread();
	  }
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	System.out.println("APPLICATION STARTED SUCCESSFULLY");
  }
  

  /**
   * 
   */
  /*private void initializeServiceModule() {
    System.out.println("SERVICE MODULE INITIALIZED");
  }*/

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse)
   */
  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#destroy()
   */
  @Override
  public void destroy() {
    super.destroy();
    CommunicatorThread.stopThread();
  }
}