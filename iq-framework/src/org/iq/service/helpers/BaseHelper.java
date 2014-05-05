/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 **/

package org.iq.service.helpers;

import org.iq.db.mysql.MySqlSession;

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
	mySqlSession = new MySqlSession(SystemConf.getDbHost(),
		SystemConf.getDbPort(), SystemConf.getDbName(), SystemConf.getDbUser(),
		SystemConf.getDbPass());
  }
}