package org.iq.comm.sms;

import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;

/**
 * @author Sam
 */
public abstract class SmsCommunicator extends Communicator {

  /**
   * @param apiUrl
   * @param parameterMap
   */
  protected SmsCommunicator(CommunicationType commType) {
	super(commType);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.comm.Communicator#processCommunication(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  protected boolean processCommunication(String from, String to, String cc,
	  String bcc, String subject, String message, String attachmentFilename) {
	return processCommunication(from, to, message);
  }
}