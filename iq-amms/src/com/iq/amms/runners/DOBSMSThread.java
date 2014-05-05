/**
 * 
 */
package com.iq.amms.runners;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.exception.DbException;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.date.DateUtil.DatePart;

import com.iq.amms.services.helpers.SystemHelper;
import com.iq.amms.valueobjects.DwellersMasterVO;

/**
 * @author Sam
 *
 */
public class DOBSMSThread extends Thread{
	
	private static String HAPPY_BIRTHDAY_MESSAGE = "AMMS Team wishes you a very HAPPY BIRTHDAY";
	private static String HAPPY_ANNIVERSARY_MESSAGE = "AMMS Team wishes you and your family a very HAPPY ANNIVERSARY";
	private static String BIRTHDAY_SUBJECT = "MGRA - BIRTHDAY WISHES";
	private static String ANNIVERSARY_SUBJECT = "MGRA - ANNIVERSARY WISHES";
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {/*
		Thread recThread = new Thread(this, "DOB SMS Thread");
		Communicator communicator = CommunicationManager
				.getCommunicator(CommunicationType.TEXT_GURU_SMS);
		Communicator gmailCommunicator = CommunicationManager
				.getCommunicator(CommunicationType.GMAIL_MAIL);
		try {
			SystemHelper systemHelper = new SystemHelper();
			Date today =  
					DateUtil.stringToDate(
							DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
							DateFormat.MMM_dd_yyyy);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			int todayDate = calendar.get(Calendar.DATE);
			int todayMonth = calendar.get(Calendar.MONTH);
			Date runDate = systemHelper.getDOBRunDate();
			if(today.compareTo(runDate) == 0){
				String queryDate = todayMonth+"-"+todayDate;
				List<DwellersMasterVO> birthdayList = systemHelper.getBirthdayNumbersList(queryDate);
				for (Iterator<DwellersMasterVO> iterator = birthdayList.iterator(); iterator
						.hasNext();) {
					DwellersMasterVO dwellersMasterVO = iterator.next();
			    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.
			    			  getPhoneMobile(), HAPPY_BIRTHDAY_MESSAGE);
					  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(),
							  null, null, BIRTHDAY_SUBJECT, HAPPY_BIRTHDAY_MESSAGE, null);
				}
				List<DwellersMasterVO> anniversaryList = systemHelper.getAnniversaryNumbersList(queryDate);
				for (Iterator<DwellersMasterVO> iterator = anniversaryList.iterator(); iterator
						.hasNext();) {
					DwellersMasterVO dwellersMasterVO = iterator.next();
			    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.
			    			  getPhoneMobile(), HAPPY_ANNIVERSARY_MESSAGE);
					  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(),
							  null, null, ANNIVERSARY_SUBJECT, HAPPY_ANNIVERSARY_MESSAGE, null);
				}
				
				systemHelper.updateDOBRunDate(DateUtil.addToDate(today, 1,
						DatePart.DATE));
			}
			Thread.sleep(7500000);
			recThread.start();
		} catch (DbException e) {
			e.printStackTrace();
			try {
				Thread.sleep(7500000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			recThread.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	*/}
}
