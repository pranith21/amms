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
 * @author SC64807
 */
public class Setup {

  private static final int INITIAL_WAIT_TIME_IN_SECS = 1;

  private static final String DB_HOST = SetupConf.getDbHost();
  private static final Short DB_PORT = SetupConf.getDbPort();
  private static final String DB_NAME = SetupConf.getDbName();
  private static final String DB_USER = SetupConf.getDbUser();
  private static final String DB_PASS = SetupConf.getDbPass();

  private static final String DROP_DB_QUERY = "DROP DATABASE IF EXISTS "
      + DB_NAME;
  private static final String CREATE_DB_QUERY =
      "CREATE DATABASE IF NOT EXISTS " + DB_NAME;

  private static final String RESOURCES_DIR = "resources";

  private static String[] appVersions = new String[] {};
  private static MySqlSession mySqlSession = null;

  /**
   * @param args
   */
  public static void main(String[] args) {
    initialize();
    try {

      for (String appVersion : appVersions) {
        System.out.println();
        System.out.println("Starting database setup for " + appVersion
            + " version...");
        System.out.println();
        if ("beta".equalsIgnoreCase(appVersion)) {
          mySqlSession =
              new MySqlSession(DB_HOST, DB_PORT, DB_USER, DB_PASS);

          mySqlSession.executeUpdate(DROP_DB_QUERY);
          System.out.println("Dropped Database: " + DB_NAME);

          mySqlSession.executeUpdate(CREATE_DB_QUERY);
          System.out.println("Created Database: " + DB_NAME);
        }

        mySqlSession =
            new MySqlSession(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS);

        String resourceDirectory =
            RESOURCES_DIR + File.separator + appVersion;

        if (FileUtil.isFileExists(resourceDirectory)) {
          List<File> dbsList =
              FileUtil.listAllFiles(new File(resourceDirectory),
                  new FileFilter() {
                    public boolean accept(File pathname) {
                      return pathname.getName().endsWith("dbs");
                    }
                  });

          System.out.println();
          System.out.println("Found " + dbsList.size()
              + " db script file(s).");
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
    catch (Exception e) {
      if (e.getMessage() != null) {
        System.err.println("Error occured: " + e.getMessage());
      }
      else if (e.getCause() != null && e.getCause().getMessage() != null) {
        System.err.println("Error occured: " + e.getCause().getMessage());
      }
      else {
        e.printStackTrace();
      }
    }
  }

  /**
   * 
   */
  private static void initialize() {
    appVersions = SetupConf.getAppVersion();
    System.out.println("Initiating setup...");

    int wait = INITIAL_WAIT_TIME_IN_SECS;

    while (wait >= 0) {
      System.out.print("To stop press Ctrl+C in " + (wait) + " seconds...");

      try {
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      for (int i = 0; i < ("To stop press Ctrl+C in " + (wait) + " seconds...")
          .length(); i++) {
        System.out.print("\b");
      }
      if (wait == 0) {
        for (int i = 0; i < ("To stop press Ctrl+C in " + (wait) + " seconds...")
            .length(); i++) {
          System.out.print(" ");
        }
      }
      wait--;
    }
    System.out.println();
  }

  /**
   * @throws UtilityException
   * @throws DbException
   */
  private static void processDbScript(File dbScriptFile)
      throws UtilityException, DbException {
    System.out.println("Processing " + dbScriptFile.getName() + " file...");
    String[] queries = FileUtil.getFileContent(dbScriptFile).split(";");
    for (String query : queries) {
      if (StringUtil.isEmpty(query) == false) {
        mySqlSession.executeUpdate(query);
      }
    }
    System.out.println("Successfully completed " + dbScriptFile.getName()
        + " file.");
    System.out.println();
  }
}