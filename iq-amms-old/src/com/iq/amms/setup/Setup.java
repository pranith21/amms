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
  public static final String DB_NAME = "AMMS_DEV";
  private static final String DROP_DB_QUERY = "DROP DATABASE IF EXISTS " + DB_NAME;
  private static final String CREATE_DB_QUERY =
      "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
  // private static final String DB_SCRIPT_FILE_NAME =
  // "resources/create-tables.dbs";
  private static final String RESOURCES_DIR = "resources";
  
  private static final String DATA_LOADER_PACKAGE = "com.iq.amms.setup.";
  private static final String DATA_LOADER = "DataLoader";

  private static String[] appVersions = new String[] {};
  private static MySqlSession mySqlSession = null;

  /**
   * @param args
   */
  public static void main(String[] args) {
    initialize();
    try {

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

          System.out
              .println("Found " + dbsList.size() + " db script files.");
          for (File dbScriptFile : dbsList) {
            processDbScript(dbScriptFile);
          }

          List<File> csvList =
              FileUtil.listAllFiles(new File(resourceDirectory),
                  new FileFilter() {
                    public boolean accept(File pathname) {
                      return pathname.getName().endsWith("csv");
                    }
                  });

          System.out.println("Found " + csvList.size() + " csv files.");
          for (File csvFile : csvList) {
            processCsv(csvFile);
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
   * @param csvFile
   */
  private static void processCsv(File csvFile) {
    String csvFilename = csvFile.getAbsolutePath();
    System.out.println("Processing " + csvFilename + " file...");

    if (StringUtil.isEmpty(csvFilename) == false) {
      try {
        DataLoader dataLoader =
            (DataLoader)Class.forName(getDataLoaderClassName(csvFilename))
                .newInstance();
        dataLoader.setCsvFileFullName(csvFilename);
        dataLoader.setMySqlSession(mySqlSession);
        dataLoader.importFromCsv();
      }
      catch (InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * 
   */
  private static String getDataLoaderClassName(String csvFilename) {
    StringBuffer buffer = new StringBuffer();
    String localFilename =
        csvFilename.substring((csvFilename.lastIndexOf(File.separator) + 1),
            csvFilename.indexOf("."));
    buffer.append(localFilename.substring(0, 1).toUpperCase());
    buffer.append(localFilename.substring(1).toLowerCase());
    return DATA_LOADER_PACKAGE + buffer.toString() + DATA_LOADER;
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
  }
}