/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services.helpers;

import org.iq.db.mysql.MySqlSession;

import com.iq.amms.SystemConf;
import com.iq.amms.setup.old.DatabaseHandler;

/**
 * @author SC64807
 *
 */
public class BaseHelper {

  protected MySqlSession mySqlSession = null;

  /**
   * 
   */
  public BaseHelper() {
    mySqlSession =
      new MySqlSession(SystemConf.getDbHost(), SystemConf.getDbPort(),
          DatabaseHandler.DB_NAME, SystemConf.getDbUser(), SystemConf
              .getDbPass());
  }
}