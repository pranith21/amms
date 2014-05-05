/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.db.mysql;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.iq.db.DbSession;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

/**
 * @author SC64807
 * 
 */
public class MySqlSession extends DbSession {
  // "jdbc:mysql://hostname:port/dbname"
  /**
   * 
   */
  public MySqlSession(String hostname, Short port, String databaseName) {
	this.driverQualifiedName = "com.mysql.jdbc.Driver";
	this.urlPattern = URL_PREFIX + "//" + HOSTNAME + ":" + PORT + "/" + DB_NAME;
	this.urlPrefix = "jdbc:mysql:";
	this.hostname = hostname;
	this.port = port;
	this.databaseName = databaseName;
  }

  /**
   * 
   */
  public MySqlSession(String hostname, Short port, String databaseName,
	  String username, String password) {
	this.driverQualifiedName = "com.mysql.jdbc.Driver";
	this.urlPattern = URL_PREFIX + "//" + HOSTNAME + ":" + PORT + "/" + DB_NAME;
	this.urlPrefix = "jdbc:mysql:";
	this.hostname = hostname;
	this.port = port;
	this.databaseName = databaseName;
	this.username = username;
	this.password = password;
  }

  /**
   * 
   */
  /*
   * public MySqlSession(String hostname, Short port, String username, String
   * password) { this.driverQualifiedName = "com.mysql.jdbc.Driver";
   * this.urlPattern = URL_PREFIX + "//" + HOSTNAME + ":" + PORT; this.urlPrefix
   * = "jdbc:mysql:"; this.hostname = hostname; this.port = port; this.username
   * = username; this.password = password; }
   */

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.db.DbSession#createDump()
   */
  @Override
  public boolean createDump(String dumpFileLocation) {
	int exitVal = -999;
	Runtime runtime = Runtime.getRuntime();

	String fileName = dumpFileLocation + File.separator + this.databaseName
		+ "-" + DateUtil.dateToString(new Date(), DateFormat.yyyy_MM_dd) + "-"
		+ DateUtil.dateToString(new Date(), DateFormat.HH_mm_ss_SSS) + "-"
		+ SystemConf.getSystemName() + ".sql";

	String command = "cmd /c mysqldump --user=" + this.username
		+ " --password=" + this.password + " --databases " + this.databaseName
		+ " > " + fileName;

	Process process;
	try {
	  process = runtime.exec(command);
	  exitVal = process.waitFor();
	}
	catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	catch (InterruptedException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}

	return exitVal == 0;
  }
}