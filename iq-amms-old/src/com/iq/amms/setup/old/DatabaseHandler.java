package com.iq.amms.setup.old;

import java.io.File;

import org.iq.db.mysql.MySqlSession;
import org.iq.exception.DbException;
import org.iq.exception.UtilityException;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.setup.SetupConf;

/**
 * @author Sam
 * 
 */
public class DatabaseHandler {

  public static final String DB_NAME = "AMMS_DEV";
  private static final String DROP_DB_QUERY =
    "DROP DATABASE IF EXISTS " + DB_NAME;
  private static final String CREATE_DB_QUERY =
    "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
  private static final String DB_SCRIPT_FILE_NAME =
      "resources/create-tables.dbs";

  private MySqlSession mySqlSession = null;

  /**
   * @throws DbException
   * 
   */
  public void create() throws DbException {
    try {
      mySqlSession =
          new MySqlSession(SetupConf.getDbHost(), SetupConf.getDbPort(),
              SetupConf.getDbUser(), SetupConf.getDbPass());
      mySqlSession.executeUpdate(DROP_DB_QUERY);
      System.out.println("Dropped Database: " + DB_NAME);

      mySqlSession.executeUpdate(CREATE_DB_QUERY);
      System.out.println("Created Database: " + DB_NAME);

      String dbScriptContent =
          FileUtil.getFileContent(new File(DB_SCRIPT_FILE_NAME));
      String[] queries = dbScriptContent.split(";");
      mySqlSession =
          new MySqlSession(SetupConf.getDbHost(), SetupConf.getDbPort(),
              DB_NAME, SetupConf.getDbUser(), SetupConf.getDbPass());

      for (String query : queries) {
        if (StringUtil.isEmpty(query) == false) {
          mySqlSession.executeUpdate(query);
        }
      }
      System.out.println("Created all tables listed in : "
          + DB_SCRIPT_FILE_NAME);
    }
    catch (UtilityException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new DbException(e);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new DbException(e);
    }
  }

  /**
   * @throws DbException
   * 
   */
  public void importData() throws DbException {
    mySqlSession =
        new MySqlSession(SetupConf.getDbHost(), SetupConf.getDbPort(),
            DB_NAME, SetupConf.getDbUser(), SetupConf.getDbPass());

    DataHandler dataHandler = new DataHandler(mySqlSession);

    try {
      dataHandler.importFromSetupConf();
    }
    catch (DbException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new DbException(e);
    }

    try {
      dataHandler.importFromCsv();
    }
    catch (UtilityException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new DbException(e);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new DbException(e);
    }
  }
}