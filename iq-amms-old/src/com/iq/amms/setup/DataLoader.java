/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 **/

package com.iq.amms.setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.iq.db.mysql.MySqlSession;
import org.iq.exception.UtilityException;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author SC64807
 */
public abstract class DataLoader {

  private CSVReader reader = null;

  protected String csvFileFullName = null;
//  private String csvFilename = null;
  protected String[] headerLine = null;
  protected MySqlSession mySqlSession = null;

  public void importFromCsv() throws Exception {
    try {
      reader = new CSVReader(new FileReader(csvFileFullName));

      headerLine = reader.readNext();

      validateHeader();

      int lineNumber = importData();

      System.out.println("Imported " + lineNumber + " records.");
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }

  protected abstract void validateHeader() throws Exception;

  protected abstract int importData() throws Exception;

  /**
   * @return the csvFileFullName
   */
  public String getCsvFileFullName() {
    return csvFileFullName;
  }

  /**
   * @param csvFileFullName the csvFileFullName to set
   */
  public void setCsvFileFullName(String csvFileFullName) {
    this.csvFileFullName = csvFileFullName;
//    this.csvFilename = this.csvFileFullName.substring(this.csvFileFullName.lastIndexOf(File.separator)+1);
  }

  /**
   * @return String[]
   * @throws IOException
   */
  protected String[] getNextLine() throws IOException {
    return reader.readNext();
  }

  /**
   * @param mySqlSession the mySqlSession to set
   */
  public void setMySqlSession(MySqlSession mySqlSession) {
    this.mySqlSession = mySqlSession;
  }
}