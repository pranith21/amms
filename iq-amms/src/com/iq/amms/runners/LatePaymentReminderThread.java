/**
 * 
 */
package com.iq.amms.runners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.exception.DbException;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.SystemHelper;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author Sam
 * 
 */
public class LatePaymentReminderThread implements Runnable {

  private static Thread runner = null;
  private static boolean runnerFlag = true;

  private LatePaymentReminderThread() {
	runner = new Thread(this, "LatePaymentReminderThread");
	runner.start();
  }

  public static void startThread() {
	new LatePaymentReminderThread();
  }

  private final String latePaymentReminderMessage = "Your invoice of Rs.%s"
	  + " is due on %s. Penal charges of Rs.%s would be charged "
	  + "if not paid by due date.";
  private String subject = "MGRA - Late Payment Info";

  public void run() {
	System.out.println("LatePaymentReminderThread started");
	while (runnerFlag) {
		System.out.println("LatePaymentReminderThread called");
	  try {
		Date today = DateUtil.stringToDate(
			DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
			DateFormat.MMM_dd_yyyy);
		ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
		SystemHelper systemHelper = new SystemHelper();
		Date runDate = systemHelper.getLatePaymentDueDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(runDate);
		int reminderDays = Integer.valueOf(apartmentMasterParamsHelper.getParam(
				ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_DAYS));
		int reminderDate = calendar.get(Calendar.DATE) - reminderDays;
		if (reminderDate > 0) {
		  calendar.set(Calendar.DATE, reminderDate);
		}
		else {
		  calendar.set(Calendar.DATE, 1);
		}
		if (today.compareTo(calendar.getTime()) >= 0) {
		  int displayDate = Integer.valueOf(apartmentMasterParamsHelper.getParam(
					ApartmentMasterParamsHelper.LATE_PAYMENT_DISPLAY_DATE));
		  Calendar calendar2 = Calendar.getInstance();
		  calendar2.setTime(runDate);
		  calendar2.set(Calendar.DATE, displayDate);
		  String latePaymentDate = DateUtil.dateToString(calendar2.getTime(),
			  DateFormat.MMM_dd_yyyy);
		  String latePaymentAmount = String.format("%1.2f", systemHelper.getLatePaymentAmount());
		  FlatsHelper flatsHelper = new FlatsHelper();
		  ArrayList<FlatDetailsVO> flatDetailsVOs = flatsHelper.getAllFlats();
		  FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
		  FinancialsHelper financialsHelper = new FinancialsHelper();
		  Double latePaymentMinBalance = Double
			  .valueOf(apartmentMasterParamsHelper.getParam(
						ApartmentMasterParamsHelper.LATE_PAYMENT_MINIMUM_BALANCE));

		  for (FlatDetailsVO flatDetailsVO : flatDetailsVOs) {
			int flatID = flatDetailsVO.getFlatId();
			financialDetailsVO = financialsHelper.getFinancialDetails(flatID);
			if (financialDetailsVO.getCurrentBalance() > latePaymentMinBalance) {
			  DwellersMasterVO dwellersMasterVO = flatsHelper
				  .getDwellerDetails(flatID);
			  String localLatePaymentReminderMessage = String.format(
				  latePaymentReminderMessage, String.format("%1.2f",
					  financialDetailsVO.getCurrentBalance()), latePaymentDate,
				  latePaymentAmount);
			  if ("1".equals(apartmentMasterParamsHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SMS))) {
				  Communicator communicator = CommunicationManager
						  .getCommunicator(CommunicationType.TEXT_GURU_SMS);
				  communicator.communicate(SystemConf.getTextGuruSource(),
						  dwellersMasterVO.getPhoneMobile(),
						  localLatePaymentReminderMessage);
				  communicator.communicate(SystemConf.getTextGuruSource(),
						  dwellersMasterVO.getPhoneResidence(),
						  localLatePaymentReminderMessage);
			  }
//			  if ("1".equals(apartmentMasterParamsHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_MAIL))) {
//				  Communicator gmailCommunicator = CommunicationManager
//						  .getCommunicator(CommunicationType.GMAIL_MAIL);
//				  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(),
//						  null, null, subject, latePaymentReminderMessage, null);
//			  }
			}
		  }
		  apartmentMasterParamsHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SENT, "1");
		}
		try {
		  Thread.sleep(300000);
		}
		catch (InterruptedException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		}
		runnerFlag = false;
	  }
	  catch (DbException e) {
		// TODO: handle exception
		  e.printStackTrace();
		  runnerFlag = false;
	  }
	}
  }
}