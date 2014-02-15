/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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

  private static String appRoot = null;
  private static Properties sysProps = new Properties();

  static {
    try {
      appRoot = getSystemProperty(CATALINA_HOME)+File.separator+"webapps/amms";
      sysProps.load(new FileInputStream(appRoot+File.separator+"resources/system.conf"));
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
  public static String getDbHost() {
    return sysProps.getProperty(DB_HOST);
  }

  /**
   * @return
   */
  public static Short getDbPort() {
    String portStr =
        StringUtil.getStringValue(sysProps.getProperty(DB_PORT));
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
  public static String getSystemProperty(String sysParamName)
      throws Exception {
    String paramValue = System.getProperty(sysParamName);
    if (paramValue == null || paramValue.length() == 0) {
      Map<String,String> map = System.getenv();
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
}