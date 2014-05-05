/**
 * Copyright (c) 2014, iquesters, India. All Rights Reserved.
 **/
package org.iq.comm.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.service.helpers.SystemConf;
import org.iq.util.string.StringUtil;

/**
 * @author Sam
 * 
 */
public class GmailMailCommunicator extends MailCommunicator {

  private static String username = SystemConf.getMailUsername();
  private static String password = SystemConf.getMailPassword();
  private static String smtpAuthFlag = SystemConf.getMailAuthFlag();
  private static String smtpTLSEnableFlag = SystemConf.getMailTLSEnableFlag();
  private static String smtpHost = SystemConf.getMailHost();
  private static String smtpPort = SystemConf.getMailPort();

  /**
   * @param commType
   */
  public GmailMailCommunicator() {
	super(CommunicationType.GMAIL_MAIL);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.comm.Communicator#validateReceipient()
   */
  @Override
  protected boolean validateReceipient() {
	boolean valid = false;
	if (StringUtil.isEmpty(to) == false) {
	  if (to.contains(",")) {
		String[] emails = to.split(",");
		valid = validateEmails(emails);
	  }
	  else {
		valid = validateEmails(to);
	  }
	}

	if (StringUtil.isEmpty(cc) == false) {
	  if (cc.contains(",")) {
		String[] emails = cc.split(",");
		valid &= validateEmails(emails);
	  }
	  else {
		valid &= validateEmails(cc);
	  }
	}

	if (StringUtil.isEmpty(bcc) == false) {
	  if (bcc.contains(",")) {
		String[] emails = bcc.split(",");
		valid &= validateEmails(emails);
	  }
	  else {
		valid &= validateEmails(bcc);
	  }
	}
	System.out.println("Mail ID valid - "+valid);
	return valid;
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

	if (StringUtil.isEmpty(username) == false
		&& StringUtil.isEmpty(password) == false) {
	  Properties props = new Properties();
	  props.put(SMTP_AUTH_PARAM_NAME, smtpAuthFlag);
	  props.put(SMTP_TLS_ENABLE_PARAM_NAME, smtpTLSEnableFlag);
	  props.put(SMTP_HOST_PARAM_NAME, smtpHost);
	  props.put(SMTP_PORT_PARAM_NAME, smtpPort);

	  Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication(userName, password);
			}
		  });

	  if (StringUtil.isEmpty(from) == false && StringUtil.isEmpty(to) == false
		  && StringUtil.isEmpty(message) == false) {
		try {

		  Message gmailMessage = new MimeMessage(session);

		  // Setting sender
		  gmailMessage.setFrom(new InternetAddress(from));

		  // Setting receipients
		  // Setting to
		  gmailMessage.setRecipients(Message.RecipientType.TO,
			  InternetAddress.parse(to));
		  // Setting cc
		  if (StringUtil.isEmpty(cc) == false) {
			gmailMessage.setRecipients(Message.RecipientType.CC,
				InternetAddress.parse(cc));
		  }
		  // Setting bcc
		  if (StringUtil.isEmpty(bcc) == false) {
			gmailMessage.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(bcc));
		  }

		  // Setting subject
		  if (StringUtil.isEmpty(subject) == false) {
			gmailMessage.setSubject(subject);
		  }

		  // Setting message content
		  // Creating multipart message content
		  Multipart multipart = new MimeMultipart();

		  // Creating the message part
		  BodyPart messageBodyPart = new MimeBodyPart();
		  // Filling the input message
		  messageBodyPart.setText(message);

		  // Setting text message part to multipart content
		  multipart.addBodyPart(messageBodyPart);

		  // Creating the attachment part
		  if (StringUtil.isEmpty(attachmentFilename) == false) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentFilename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachmentFilename);
			// Setting attachment part to multipart content
			multipart.addBodyPart(messageBodyPart);
		  }

		  // Setting multipart message content
		  gmailMessage.setContent(multipart);

		  // Sending message
		  Transport.send(gmailMessage);

		  System.out.println("Mail sent");
		  return true;
		}
		catch (MessagingException e) {
		  System.out.println("Mail not sent: " + e.getMessage());
		  e.printStackTrace();
		}
	  }
	}
	return false;
  }
}
