/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 **/

package org.iq.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.iq.exception.DbException;
import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public abstract class DbSession {

  protected static final String URL_PREFIX = "URL_PREFIX";
  protected static final String HOSTNAME = "HOSTNAME";
  protected static final String PORT = "PORT";
  protected static final String DB_NAME = "DB_NAME";

  private Connection connection;
  private Statement statement;
  private PreparedStatement pStatement;
  private ResultSet resultSet;

  private String connectionUrl;

  protected String urlPattern;

  protected String driverQualifiedName;
  protected String urlPrefix;
  protected String hostname;
  protected Short port = -1;
  protected String databaseName;
  protected String username;
  protected String password;

  public DataSet executeQuery(String query) throws DbException {
	try {
	  prepareConnection();
	  statement = connection.createStatement();
	  resultSet = statement.executeQuery(query);
	  return new DataSet(resultSet);
	}
	catch (SQLException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	finally {
	  closeAll();
	}
  }

  /**
   * @param sql
   * @return int
   * @throws DbException
   */
  public DataSet executeQuery(String query, Object... args) throws DbException {
	try {
	  prepareConnection();
	  pStatement = connection.prepareStatement(query);
	  setValuesToStatement(pStatement, args);
	  resultSet = pStatement.executeQuery();
	  return new DataSet(resultSet);
	}
	catch (SQLException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	finally {
	  closeAll();
	}
  }

  /**
   * @param sql
   * @return int
   * @throws DbException
   */
  public int executeUpdate(String query) throws DbException {
	try {
	  prepareConnection();
	  statement = connection.createStatement();
	  return statement.executeUpdate(query);
	}
	catch (SQLException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	finally {
	  closeAll();
	}
  }

  /**
   * @param sql
   * @return int
   * @throws DbException
   */
  public int executeUpdate(String query, Object... args) throws DbException {
	try {
	  prepareConnection();
	  pStatement = connection.prepareStatement(query);
	  setValuesToStatement(pStatement, args);
	  return pStatement.executeUpdate();
	}
	catch (SQLException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	finally {
	  closeAll();
	}
  }

  private void setValuesToStatement(PreparedStatement pstatement,
	  Object... args) throws SQLException {
	for (int index = 0; index < args.length; index++) {
	  Object value = args[index];
	  String valueType = value == null ? null : value.getClass()
		  .getSimpleName();
	  if ("Integer".equalsIgnoreCase(valueType)) {
		pstatement.setInt(index + 1, ((Integer) value).intValue());
	  }
	  else if ("Float".equalsIgnoreCase(valueType)) {
		pstatement.setFloat(index + 1, ((Float) value).floatValue());
	  }
	  else if ("String".equalsIgnoreCase(valueType)) {
		pstatement.setString(index + 1, (String) value);
	  }
	  else if ("Date".equalsIgnoreCase(valueType)) {
		// System.out.println("date value found in PS");
		// the date format change has to be coded
		pstatement.setDate(index + 1,
			new java.sql.Date(((Date) value).getTime()));
	  }
	  else if ("BigDecimal".equalsIgnoreCase(valueType)) {
		// the date format change has to be coded
		pstatement.setBigDecimal(index + 1, ((BigDecimal) value));
	  }
	  else {
		pstatement.setObject(index + 1, value);
	  }
	}
  }

  /**
   * @throws DbException
   * 
   */
  private void prepareConnection() throws DbException {
	try {
	  Class.forName(driverQualifiedName);
	  buildConnectionUrl();
	  if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
		connection = DriverManager.getConnection(connectionUrl);
	  }
	  else {
		connection = DriverManager.getConnection(connectionUrl, username,
			password);
	  }
	}
	catch (ClassNotFoundException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
	catch (SQLException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  throw new DbException(e);
	}
  }

  /**
   * 
   */
  private void buildConnectionUrl() {
	String localConnectionUrl = urlPattern;
	if (StringUtil.isEmpty(urlPattern) == false) {
	  if (localConnectionUrl.contains(URL_PREFIX)) {
		if (StringUtil.isEmpty(urlPrefix) == false) {
		  localConnectionUrl = localConnectionUrl
			  .replace(URL_PREFIX, urlPrefix);
		}
	  }
	  if (localConnectionUrl.contains(HOSTNAME)) {
		if (StringUtil.isEmpty(hostname) == false) {
		  localConnectionUrl = localConnectionUrl.replace(HOSTNAME, hostname);
		}
	  }
	  if (localConnectionUrl.contains(PORT)) {
		if (port != -1) {
		  localConnectionUrl = localConnectionUrl.replace(PORT, "" + port);
		}
	  }
	  if (localConnectionUrl.contains(DB_NAME)) {
		if (StringUtil.isEmpty(databaseName) == false) {
		  localConnectionUrl = localConnectionUrl
			  .replace(DB_NAME, databaseName);
		}
	  }
	}
	connectionUrl = localConnectionUrl;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#finalize()
   */
  @Override
  protected void finalize() throws Throwable {
	closeAll();
	super.finalize();
  }

  protected void closeAll() throws DbException {
	if (statement != null) {
	  try {
		statement.close();
	  }
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new DbException(e);
	  }
	}
	if (resultSet != null) {
	  try {
		resultSet.close();
	  }
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new DbException(e);
	  }
	}
	if (connection != null) {
	  try {
		connection.close();
	  }
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new DbException(e);
	  }
	}
  }

  public abstract boolean createDump(String dumpFileLocation);
}