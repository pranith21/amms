package org.iq.comm.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.service.helpers.SystemConf;
import org.iq.util.string.StringUtil;

public class TextguruSmsCommunicator extends SmsCommunicator {

  private static final String API_URL = "http://www.txtguru.in/imobile/api.php?";

  private static final String USERNAME_PARAM_NAME = "username";
  private static final String PASSWORD_PARAM_NAME = "password";
  private static final String SOURCE_PARAM_NAME = "source";
  private static final String DMOBILE_PARAM_NAME = "dmobile";
  private static final String MESSAGE_PARAM_NAME = "message";

  private static String username = SystemConf.getTextGuruUsername();
  private static String password = SystemConf.getTextGuruPassword();

  /**
   * 
   */
  public TextguruSmsCommunicator() {
	super(CommunicationType.TEXT_GURU_SMS);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.comm.sms.SmsCommunicator#validateReceipient(java.lang.String)
   */
  @Override
  protected boolean validateReceipient() {
	System.out.println("To = " + to);
	if (StringUtil.isEmpty(to) == false) {
	  // Removing + sign if any
	  if (to.startsWith("+")) {
		to = to.substring(1);
	  }

	  // Checking if 'to' is a number
	  try {
		Double.valueOf(to);
	  }
	  catch (NumberFormatException e) {
		System.out.println("Not a number");
		return false;
	  }

	  // Checking if 'to' has 91 in prefix
	  if (to.length() < 10 || to.length() == 11 || to.length() > 12) {
		System.out.println("Not correct length");
		return false;
	  }
	  else if (to.length() == 10) {
		to = "91" + to;
	  }
	  else if (to.length() == 12) {
		String code = to.substring(0, 2);
		if (!code.equals("91")) {
		  System.out.println("Not a India number");
		  return false;
		}
	  }
	}
	else {
	  System.out.println("Number is null or blank");
	  return false;
	}
	return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.comm.Communicator#processCommunication(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  protected boolean
	  processCommunication(String from, String to, String message) {
	try {
	  String commUrl = API_URL + buildParameterString(from, to, message);
	  System.out.println("Communication URL: " + commUrl);

	  HttpURLConnection con = (HttpURLConnection) new URL(commUrl)
		  .openConnection();

	  BufferedReader in = new BufferedReader(new InputStreamReader(
		  con.getInputStream()));
	  String inputLine = null;
	  StringBuffer response = new StringBuffer();
	  while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	  }
	  in.close();

	  return getCommunicationStatus(con.getResponseCode(), response.toString());
	}
	catch (IOException e) {
	  return false;
	}
  }

  /**
   * @return String
   */
  private String buildParameterString(String from, String to, String message) {
	StringBuffer buf = new StringBuffer();

	if (StringUtil.isEmpty(username) == false
		&& StringUtil.isEmpty(password) == false) {
	  buf.append(USERNAME_PARAM_NAME);
	  buf.append("=");
	  buf.append(username);
	  buf.append("&");

	  buf.append(PASSWORD_PARAM_NAME);
	  buf.append("=");
	  buf.append(password);
	  buf.append("&");

	  if (StringUtil.isEmpty(from) == false && StringUtil.isEmpty(to) == false
		  && StringUtil.isEmpty(message) == false) {
		buf.append(SOURCE_PARAM_NAME);
		buf.append("=");
		buf.append(from);
		buf.append("&");

		buf.append(DMOBILE_PARAM_NAME);
		buf.append("=");
		buf.append(to);
		buf.append("&");

		buf.append(MESSAGE_PARAM_NAME);
		buf.append("=");
		buf.append(getMessageForURL(message));
	  }
	}
	return buf.toString();
  }

  /**
   * @param message
   * @return
   */
  private String getMessageForURL(String message) {
	// TODO line separator character
	return message.replace(" ", "+");
  }

  private boolean getCommunicationStatus(int responseCode,
	  String responseMessage) {
	System.out.println(responseCode + "::" + responseMessage);
	if (responseCode == 200) {
	  System.out.println("Communication with TextGuru Server successful.");
	  if (StringUtil.isEmpty(responseMessage) == false) {
		if (responseMessage.startsWith("MsgID:")
			&& responseMessage.contains(",MobileCount:")) {
		  System.out.println("Message sent successfully.");
		  return true;
		}
		else if ("Invalid Username or Password".equals(responseMessage)) {
		  System.out
			  .println("Message not sent : Invalid Username or Password.");
		  return true;
		}
		else {
		  System.out.println("Unknown response:" + responseMessage
			  + ". Will try again.");
		  return false;
		}
	  }
	  else {
		System.out.println("No response message. Will try again.");
		return false;
	  }
	}
	else {
	  System.out
		  .println("Communication with TextGuru Server NOT successful. Will try again.");
	  return false;
	}
  }

}