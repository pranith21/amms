package com.iq.amms.setup;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.iq.db.mysql.MySqlSession;
import org.iq.exception.DbException;
import org.iq.exception.UtilityException;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

/**
 * @author Sam
 * 
 */
public class SetupDatabase {

  public static final String DB_NAME = "AMMS_DEV";
  private static final String DROP_DB_QUERY = "DROP DATABASE IF EXISTS" + DB_NAME;
  private static final String CREATE_DB_QUERY = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
//  private static final String DB_SCRIPT_FILE_NAME = "resources/create-tables.dbs";
  private static final String RESOURCES_DIR = "resources";

  private String[] appVersions = new String[] {};
  private MySqlSession mySqlSession = null;

  /**
   * @param appVersions
   */
  public SetupDatabase(String[] appVersions) {
    this.appVersions = appVersions;
  }

  /**
   * @throws Exception 
   * 
   */
  public void setup() throws Exception {
    initialize();
  }

  /**
   * 
   */
  private void initialize() throws Exception {
    for (String appVersion : appVersions) {
      if ("beta".equalsIgnoreCase(appVersion)) {
        mySqlSession =
          new MySqlSession(SetupConf.getDbHost(), SetupConf.getDbPort(),
              SetupConf.getDbUser(), SetupConf.getDbPass());
        mySqlSession.executeUpdate(DROP_DB_QUERY);
        System.out.println("Dropped Database: " + DB_NAME);

        mySqlSession.executeUpdate(CREATE_DB_QUERY);
        System.out.println("Created Database: " + DB_NAME);
      }
      System.out.println("Starting database setup for " + appVersion
          + " version...");
      System.out.println();
      mySqlSession =
          new MySqlSession(SetupConf.getDbHost(), SetupConf.getDbPort(),
              SetupConf.getDbName(), SetupConf.getDbUser(),
              SetupConf.getDbPass());
      String resourceDirectory = RESOURCES_DIR + File.separator + appVersion;
      if (FileUtil.isFileExists(resourceDirectory)) {
        List<File> dbsList =
            FileUtil.listAllFiles(new File(resourceDirectory),
                new FileFilter() {
                  public boolean accept(File pathname) {
                    return pathname.getName().endsWith("dbs");
                  }
                });

        System.out.println("Found " + dbsList.size() + " db script files.");
        for (File dbScriptFile : dbsList) {
          processDbScript(dbScriptFile);
        }
      }
      else {
        throw new Exception("Resource directory - " + resourceDirectory
            + " not found.");
      }
    }
  }

  /**
   * @throws UtilityException
   * @throws DbException
   * 
   */
  private void processDbScript(File dbScriptFile) throws UtilityException,
      DbException {
    System.out.println("Processing " + dbScriptFile.getName() + " file...");
    String[] queries = FileUtil.getFileContent(dbScriptFile).split(";");
    for (String query : queries) {
      if (StringUtil.isEmpty(query) == false) {
        mySqlSession.executeUpdate(query);
      }
    }
  }
}