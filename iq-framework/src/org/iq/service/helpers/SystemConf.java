/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.service.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public class SystemConf {

  private static final String CATALINA_HOME = "CATALINA_HOME";

  private static final String DB_HOST = "db.host";
  private static final String DB_PORT = "db.port";
  private static final String DB_USER = "db.user";
  private static final String DB_PASS = "db.pass";
  private static final String DB_NAME = "db.name";
  private static final String TEXTGURU_USERNAME = "textguru.username";
  private static final String TEXTGURU_PASSWORD = "textguru.password";
  private static final String TEXTGURU_SOURCE = "textguru.source";
  private static final String AMMS_HOME = "C:/amms-data";
  private static final String MAIL_USERNAME = "mail.username";
  private static final String MAIL_PASSWORD = "mail.password";
  private static final String MAIL_AUTH = "mail.smtp.auth";
  private static final String MAIL_TLS_ENABLE = "mail.smtp.starttls.enable";
  private static final String MAIL_HOST = "mail.smtp.host";
  private static final String MAIL_PORT = "mail.smtp.port";

  private static String appRoot = null;
  private static String webAppsDir = null;
  private static Properties sysProps = new Properties();
  private static String sysName = System.getenv("COMPUTERNAME");

  static {
	try {
	  appRoot = getSystemProperty(CATALINA_HOME) + File.separator
		  + "webapps/amms";
	  webAppsDir = getSystemProperty(CATALINA_HOME) + File.separator
		  + "webapps";
	  sysProps.load(new FileInputStream(appRoot + File.separator
		  + "resources/system.conf"));
	  FileUtil.createFolder(AMMS_HOME);
	}
	catch (FileNotFoundException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }

  /**
   * @return
   */
  public static String getAppRoot() {
	return appRoot;
  }

  /**
   * @return
   */
  public static String getSystemName() {
	return sysName;
  }

  /**
   * @return
   */
  public static String getwebAppsDir() {
	return webAppsDir;
  }

  /**
   * @return
   */
  public static String getDbHost() {
	return sysProps.getProperty(DB_HOST);
  }

  /**
   * @return
   */
  public static Short getDbPort() {
	String portStr = StringUtil.getStringValue(sysProps.getProperty(DB_PORT));
	return Short.valueOf(StringUtil.isEmpty(portStr) ? "-1" : portStr);
  }

  /**
   * @return
   */
  public static String getDbUser() {
	return sysProps.getProperty(DB_USER);
  }

  /**
   * @return
   */
  public static String getDbPass() {
	return sysProps.getProperty(DB_PASS);
  }

  /**
   * @return
   */
  public static String getDbName() {
	return sysProps.getProperty(DB_NAME);
  }

  /**
   * Reads the System properties defined at the Operating System level.
   * 
   * @param sysParamName
   *          Operating System Environment variable name
   * @return String Value of the parameter.
   * @throws Exception
   */
  public static String getSystemProperty(String sysParamName) throws Exception {
	String paramValue = System.getProperty(sysParamName);
	if (paramValue == null || paramValue.length() == 0) {
	  Map<String, String> map = System.getenv();
	  paramValue = map.get(sysParamName);
	}
	if (paramValue == null) {
	  throw new Exception("System enironment variable: " + sysParamName
		  + " is not set.");
	}
	System.out.println("Found System Property: " + sysParamName + " = "
		+ paramValue);
	return paramValue;
  }

  public static String getTextGuruPassword() {
	return sysProps.getProperty(TEXTGURU_PASSWORD);
  }

  public static String getTextGuruUsername() {
	return sysProps.getProperty(TEXTGURU_USERNAME);
  }

  public static String getTextGuruSource() {
	return sysProps.getProperty(TEXTGURU_SOURCE);
  }

  public static String getAmmsHome() {
	return AMMS_HOME;
  }

  public static String getMailPassword() {
	return sysProps.getProperty(MAIL_PASSWORD);
  }

  public static String getMailUsername() {
	return sysProps.getProperty(MAIL_USERNAME);
  }

  public static String getMailAuthFlag() {
	return sysProps.getProperty(MAIL_AUTH);
  }

  public static String getMailTLSEnableFlag() {
	return sysProps.getProperty(MAIL_TLS_ENABLE);
  }

  public static String getMailHost() {
	return sysProps.getProperty(MAIL_HOST);
  }

  public static String getMailPort() {
	return sysProps.getProperty(MAIL_PORT);
  }
}