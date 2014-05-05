package org.iq.comm.mail;

import org.apache.commons.validator.routines.EmailValidator;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.service.helpers.SystemConf;

/**
 * @author Sam
 */
public abstract class MailCommunicator extends Communicator {

  protected static final String SMTP_AUTH_PARAM_NAME = "mail.smtp.auth";
  protected static final String SMTP_TLS_ENABLE_PARAM_NAME = "mail.smtp.starttls.enable";
  protected static final String SMTP_HOST_PARAM_NAME = "mail.smtp.host";
  protected static final String SMTP_PORT_PARAM_NAME = "mail.smtp.port";

  protected String userName = SystemConf.getMailUsername();
  protected String password = SystemConf.getMailPassword();
  protected String smtpAuthFlag = SystemConf.getMailAuthFlag();;
  protected String smtpTLSEnableFlag = SystemConf.getMailTLSEnableFlag();
  protected String smtpHost = SystemConf.getMailHost();
  protected String smtpPort = SystemConf.getMailPort();

  /**
   * @param apiUrl
   * @param parameterMap
   */
  protected MailCommunicator(CommunicationType commType) {
	super(commType);
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
	return processCommunication(from, to, null, null, null, message, null);
  }

  /**
   * @param emails
   * @return boolean
   */
  protected boolean validateEmails(String... emails) {
	if (emails != null && emails.length > 0) {
	  for (int i = 0; i < emails.length; i++) {
		String email = emails[i];
		if (!EmailValidator.getInstance().isValid(email)) {
		  return false;
		}
	  }
	}
	return true;
  }
}