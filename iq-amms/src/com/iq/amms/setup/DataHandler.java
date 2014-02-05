package com.iq.amms.setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.iq.db.DataSet;
import org.iq.db.mysql.MySqlSession;
import org.iq.exception.DbException;
import org.iq.exception.UtilityException;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.SystemHelper;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Sam
 * 
 */
public class DataHandler {

  private static final String CSV_FILE_NAME = "resources/data.csv";

  private static final String SYSTEM_PARAMS_INSERT_QUERY =
    "INSERT INTO SYSTEM_PARAMS (PARAM_NAME, PARAM_VALUE)"
        + " VALUES " + "(?,?)";

  private static final String FLAT_MASTER_INSERT_QUERY =
    "INSERT INTO FLAT_MASTER (FLAT_NUMBER_PREFIX_1, FLAT_NUMBER, FLAT_STATUS, AREA_SQFT, CREATE_DATE)"
        + " VALUES " + "(?,?,?,?,?)";

  private static final String FLAT_ID_SELECT_QUERY =
      "SELECT FLAT_ID FROM FLAT_MASTER WHERE FLAT_NUMBER_PREFIX_1=? AND FLAT_NUMBER=?";

  private static final String DWELLERS_DETAILS_INSERT_QUERY =
    "INSERT INTO DWELLERS_MASTER (FLAT_ID, NAME_SALUTATION, NAME_FIRST, NAME_MIDDLE, NAME_LAST, DWELLERS_STATUS, CREATE_DATE)"
        + " VALUES " + "(?,?,?,?,?,?,?)";

  private static final String FINANCIALS_INSERT_QUERY =
    "INSERT INTO FINANCIALS (CURRENT_BALANCE, FLAT_ID)"
        + " VALUES " + "(?,?)";

  protected MySqlSession mySqlSession = null;

  /**
   * @param mySqlSession
   */
  public DataHandler(MySqlSession mySqlSession) {
    this.mySqlSession = mySqlSession;
  }
  
  /**
   * @throws DbException 
   * 
   */
  public void importFromSetupConf() throws DbException {
    mySqlSession.executeUpdate(SYSTEM_PARAMS_INSERT_QUERY,
        SystemHelper.MONTHLY_MAINTENANCE_PARAM_NAME, SetupConf.getMonthlyRate());
    mySqlSession.executeUpdate(SYSTEM_PARAMS_INSERT_QUERY,
            SystemHelper.LAST_BILL_DATE_PARAM_NAME, SetupConf.getLastBillDate());
    mySqlSession.executeUpdate(SYSTEM_PARAMS_INSERT_QUERY,
            SystemHelper.LATE_PAYMENT_PARAM_NAME, SetupConf.getLatePaymentAmount());
  }


  public void importFromCsv() throws Exception {
    try {
      CSVReader reader = new CSVReader(new FileReader(CSV_FILE_NAME));

      String[] headerLine = reader.readNext();
      validateHeader(headerLine);

      Date createDate = new Date();
      String[] nextLine;
      int lineNumber = 0;
      while ((nextLine = reader.readNext()) != null) {
        mySqlSession.executeUpdate(FLAT_MASTER_INSERT_QUERY, "Block "
            + nextLine[4], nextLine[6], "1", nextLine[10], createDate);

        DataSet dataSet =
            mySqlSession.executeQuery(FLAT_ID_SELECT_QUERY, "Block "
                + nextLine[4], nextLine[6]);

        int flatId = dataSet.getIntValue(0, 0);

        System.out.println("Current flat id = " + flatId);

        mySqlSession.executeUpdate(DWELLERS_DETAILS_INSERT_QUERY, flatId,
            nextLine[0], nextLine[1], nextLine[2], nextLine[3], "1",
            createDate);

        String pendingAmount = nextLine[11];
        Double pendingDouble = 0.0;
        if (StringUtil.isEmpty(pendingAmount)==false) {
          pendingDouble = Double.valueOf(pendingAmount);
        }
        mySqlSession.executeUpdate(FINANCIALS_INSERT_QUERY, pendingDouble,flatId);

        lineNumber++;
      }
      System.out.println("Imported " + lineNumber + " records.");
    }
    catch (FileNotFoundException e) {
      throw new UtilityException(null, e);
    }
    catch (IOException e) {
      throw new UtilityException(null, e);
    }
  }

  /**
   * @param headerLine
   * @throws Exception
   */
  private static void validateHeader(String[] headerLine) throws Exception {

    if (headerLine.length == 12) {
      if (headerLine[0].equals("nameSalutation")
          && headerLine[1].equals("nameFirst")
          && headerLine[2].equals("nameMiddle")
          && headerLine[3].equals("nameLast")
          && headerLine[4].equals("flatNumberPrefix1")
          && headerLine[5].equals("flatNumberPrefix2")
          && headerLine[6].equals("flatNumber")
          && headerLine[7].equals("flatNumberSuffix1")
          && headerLine[8].equals("flatNumberSuffix2")
          && headerLine[9].equals("floorNumber")
          && headerLine[10].equals("areaInSqft")
          && headerLine[11].equals("pendingDues")) {
        return;
      }
      else {
        throw new Exception(
            "Format did not match!!! Mismatch in column name!!!");
      }
    }
    else {
      throw new Exception(
          "Format did not match!!! Mismatch in column count!!!");
    }
  }
}