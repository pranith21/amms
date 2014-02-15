/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package org.iq.util.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.iq.exception.UtilityException;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author SC64807
 *
 */
public class CsvUtil {/*

  *//**
   * @throws UtilityException 
   * 
   *//*
  public static List<String[]> getAllRecords(String csvFileName)
  throws UtilityException {
    try {
      return new CSVReader(new FileReader(csvFileName)).readAll();
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }

  *//**
   * @param csvFileName
   * @param csvObject
   * @return
   * @throws UtilityException
   *//*
  public static List<Object> getAllRecords(String csvFileName, CsvObject csvObject)
  throws UtilityException {
    try {
      return new CSVReader(new FileReader(csvFileName)).readAll();
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }

  *//**
   * @param csvFileName
   * @param rowNum
   * @return String []
   * @throws UtilityException
   *//*
  public static String[] getRecord(String csvFileName, int rowNum)
      throws UtilityException {
    try {
      CSVReader reader = new CSVReader(new FileReader(csvFileName));
      String[] nextLine;
      int lineNumber = 0;
      while ((nextLine = reader.readNext()) != null && ++lineNumber < rowNum);
      return nextLine;
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }
  
  public static CsvObject getRecord(String csvFileName, int rowNum, CsvObject csvObject) throws UtilityException {
    try {
      CSVReader reader = new CSVReader(new FileReader(csvFileName));
      String [] nextLine;
      int lineNumber = 0;
      while ((nextLine=reader.readNext()) != null && ++lineNumber<rowNum);
      return nextLine;
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }
  
  public static void main(String[] args) {
    try {
      //csv file containing data
//    String strFile = "C:\\Users\\rsaluja\\CMS_Evaluation\\Drupal_12_08_27.csv";
      CSVReader reader = new CSVReader(new FileReader(CSV_FILE_NAME));
      String [] nextLine;
      int lineNumber = 0;
      while ((nextLine = reader.readNext()) != null) {
        lineNumber++;
        System.out.println("Line # " + lineNumber);

        // nextLine[] is an array of values from the line
        System.out.println(nextLine[3] + " etc...");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

*/}
