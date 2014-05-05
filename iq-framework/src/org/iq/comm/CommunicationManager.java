package org.iq.comm;

import java.io.File;

import org.iq.comm.mail.GmailMailCommunicator;
import org.iq.comm.sms.TextguruSmsCommunicator;
import org.iq.service.helpers.SystemConf;
import org.iq.util.file.FileUtil;

/**
 * @author Sam
 */
public class CommunicationManager {

  static final String COMM_FILE_EXT = "comm";
  static final String COMM_FOLDER = SystemConf.getAmmsHome() + File.separator
	  + "comm";
  static final String COMM_TYPE = "commType";

  static {
	FileUtil.createFolder(COMM_FOLDER);
	CommunicatorThread.startThread();
  }

  /**
   * 
   * enum for date format
   */
  public enum CommunicationType {

	GMAIL_MAIL("Gmail Mail Communicator"), TEXT_GURU_SMS(
		"Textguru Sms Communicator");

	private final String commType;

	CommunicationType(String type) {
	  commType = type;
	}

	/**
	 * @return String
	 */
	public String getCommunicationTypeValue() {
	  return commType;
	}

	/**
	 * @param type
	 * @return CommunicationType
	 */
	public static CommunicationType getCommunicationType(String type) {
	  CommunicationType commType = null;
	  for (CommunicationType thisCommType : CommunicationType.values()) {
		if (thisCommType.getCommunicationTypeValue().equals(type)) {
		  commType = thisCommType;
		  break;
		}
	  }
	  return commType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	  return commType;
	}
  }

  public static Communicator
	  getCommunicator(CommunicationType communicationType) {
	switch (communicationType) {
	  case GMAIL_MAIL:
		// return new GmailMailCommunicatorOld();
		return new GmailMailCommunicator();
	  case TEXT_GURU_SMS:
		return new TextguruSmsCommunicator();
	  default:
		return null;
	}
  }
}