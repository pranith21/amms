/**
 * 
 */
package com.iq.amms.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.service.BaseService;
import org.iq.service.helpers.SystemConf;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.SystemHelper;
import com.iq.amms.valueobjects.DwellersMasterVO;

/**
 * @author Sam
 * 
 */
public class MessageSender extends BaseService {

  private static String PHONE_NUMBERS = "mobileNumber";
  private static String MESSAGE = "message";
  private static String MESSAGE_TYPE = "messageType";

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
	String phoneNumber = StringUtil.getStringValue(input.get(PHONE_NUMBERS));
	String message = StringUtil.getStringValue(input.get(MESSAGE));
	String messageType = StringUtil.getStringValue(input.get(MESSAGE_TYPE));

	Communicator communicator = CommunicationManager
		.getCommunicator(CommunicationType.TEXT_GURU_SMS);

	if ("on".equals(messageType)) {
	  SystemHelper systemHelper = new SystemHelper();
	  List<DwellersMasterVO> phoneNumberList = systemHelper.getPhoneNumbersList();
	  for (Iterator<DwellersMasterVO> iterator = phoneNumberList.iterator(); iterator
			  .hasNext();) {
		  DwellersMasterVO dwellersMasterVO = iterator.next();
		  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneMobile(), message);
		  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneResidence() , message);
	  }
	}
	else {
	  if (phoneNumber != null && phoneNumber.contains(";")) {
		String[] phoneNumbers = phoneNumber.split(";");
		for (int i = 0; i < phoneNumbers.length; i++) {
			communicator.communicate(SystemConf.getTextGuruSource(), phoneNumbers[i], message);
		}
	  }
	  else {
		communicator.communicate(SystemConf.getTextGuruSource(), phoneNumber, message);
	  }
	}
	redirectUrl = "/";
  }
}
