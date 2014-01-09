/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.db.mysql;

import org.iq.db.DbSession;

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
    this.urlPattern =
        URL_PREFIX + "//" + HOSTNAME + ":" + PORT + "/" + DB_NAME;
    this.urlPrefix = "jdbc:mysql:";
    this.hostname = hostname;
    this.port = port;
    this.databaseName = databaseName;
  }

  /**
   * 
   */
  public MySqlSession(String hostname, Short port, String databaseName, String username, String password) {
    this.driverQualifiedName = "com.mysql.jdbc.Driver";
    this.urlPattern =
        URL_PREFIX + "//" + HOSTNAME + ":" + PORT + "/" + DB_NAME;
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
  public MySqlSession(String hostname, Short port, String username, String password) {
    this.driverQualifiedName = "com.mysql.jdbc.Driver";
    this.urlPattern =
        URL_PREFIX + "//" + HOSTNAME + ":" + PORT;
    this.urlPrefix = "jdbc:mysql:";
    this.hostname = hostname;
    this.port = port;
    this.username = username;
    this.password = password;
  }
}