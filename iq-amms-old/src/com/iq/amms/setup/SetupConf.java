/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.setup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public class SetupConf {

  private static final String APP_VERSION = "app.version";
  private static final String DB_HOST = "db.host";
  private static final String DB_PORT = "db.port";
  private static final String DB_USER = "db.user";
  private static final String DB_PASS = "db.pass";
  private static final String DB_NAME = "db.name";
  private static final String MONTHLY_RATE = "monthly.rate";
  private static final String LAST_BILL_DATE = "last.bill.date";
  private static final String LATE_PAYMENT_AMOUNT = "late.payment.amount";

  private static Properties sysProps = new Properties();

  static {
    try {
      sysProps.load(new FileInputStream("setup.conf"));
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
  public static String[] getAppVersion() {
    String allVersions = sysProps.getProperty(APP_VERSION);
    return allVersions.contains(",") ? allVersions.split(",")
        : new String[] { allVersions };
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
   * @return
   */
  public static String getMonthlyRate() {
    return sysProps.getProperty(MONTHLY_RATE);
  }

  /**
   * @return
   */
  public static String getLastBillDate() {
    return sysProps.getProperty(LAST_BILL_DATE);
  }
  
  /**
   * @return
   */
  public static String getLatePaymentAmount() {
    return sysProps.getProperty(LATE_PAYMENT_AMOUNT);
  } 
}