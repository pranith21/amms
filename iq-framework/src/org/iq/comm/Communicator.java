package org.iq.comm;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

/**
 * @author Sam
 */
public abstract class Communicator {
  protected static final String FROM_PARAM_NAME = "from";
  protected static final String TO_PARAM_NAME = "to";
  protected static final String CC_PARAM_NAME = "cc";
  protected static final String BCC_PARAM_NAME = "bcc";
  protected static final String SUBJECT_PARAM_NAME = "subject";
  protected static final String MESSAGE_PARAM_NAME = "message";
  protected static final String ATTACHMENT_PARAM_NAME = "attachment";

  private CommunicationType commType = null;

  protected String commFilename = null;
  protected String commFileContent = null;

  protected String from = null;
  protected String to = null;
  protected String cc = null;
  protected String bcc = null;
  protected String subject = null;
  protected String message = null;
  protected String attachmentFilename = null;

  /**
   * 
   */
  protected Communicator(CommunicationType commType) {
	this.commType = commType;
  }

  /**
   * @param to
   * @param message
   * @throws IOException
   */
  public void communicate(String from, String to, String message) {
	communicate(from, to, null, null, null, message, null);
  }

  /**
   * @param to
   * @param cc
   * @param bcc
   * @param subject
   * @param message
   * @throws IOException
   */
  public void communicate(String from, String to, String cc, String bcc,
	  String subject, String message, String attachmentFilename) {

	this.from = from;
	this.to = to;
	this.cc = cc;
	this.bcc = bcc;
	this.subject = subject;
	this.message = message;
	this.attachmentFilename = attachmentFilename;

	if (validateReceipient()) {
	  StringBuffer buf = new StringBuffer();
	  buf.append(CommunicationManager.COMM_TYPE + "=" + commType);
	  buf.append(StringUtil.lineSeparator);

	  if (StringUtil.isEmpty(this.from) == false) {
		buf.append(FROM_PARAM_NAME);
		buf.append("=");
		buf.append(this.from);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.to) == false) {
		buf.append(TO_PARAM_NAME);
		buf.append("=");
		buf.append(this.to);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.cc) == false) {
		buf.append(CC_PARAM_NAME);
		buf.append("=");
		buf.append(this.cc);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.bcc) == false) {
		buf.append(BCC_PARAM_NAME);
		buf.append("=");
		buf.append(this.bcc);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.subject) == false) {
		buf.append(SUBJECT_PARAM_NAME);
		buf.append("=");
		buf.append(this.subject);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.message) == false) {
		buf.append(MESSAGE_PARAM_NAME);
		buf.append("=");
		// TODO for multiline message
		buf.append(this.message);
		buf.append(StringUtil.lineSeparator);
	  }

	  if (StringUtil.isEmpty(this.attachmentFilename) == false) {
		buf.append(ATTACHMENT_PARAM_NAME);
		buf.append("=");
		buf.append(this.attachmentFilename);
		buf.append(StringUtil.lineSeparator);
	  }

	  this.commFileContent = buf.toString();

	  this.commFilename = CommunicationManager.COMM_FOLDER + File.separator
		  + UUID.randomUUID().toString() + "."
		  + CommunicationManager.COMM_FILE_EXT;

	  try {
		FileUtil.createFile(commFilename, commFileContent);
	  }
	  catch (IOException e) {
		System.err.println("Error creating comm file.");
		e.printStackTrace();
	  }
	}
	else {
	  System.out.println("Invalid receipient");
	}
  }

  /**
   * 
   */
  protected abstract boolean validateReceipient();

  /**
   * @return
   * 
   */
  protected abstract boolean processCommunication(String from, String to,
	  String message);

  /**
   * @return
   * 
   */
  protected abstract boolean processCommunication(String from, String to,
	  String cc, String bcc, String subject, String message,
	  String attachmentFilename);
}