package com.iq.amms.services.helpers;

import java.util.ArrayList;
import java.util.Date;

import org.iq.db.DataSet;
import org.iq.exception.DbException;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.string.StringUtil;

import com.iq.amms.valueobjects.BillDetailsVO;
import com.iq.amms.valueobjects.BillMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author SC64807
 * 
 */
public class BillHelper extends BaseHelper {


  private static String BILL_MASTER_INSERT_QUERY =
    "INSERT INTO BILLS_INWARDS_MASTER"
        + " (DATE_OF_GENERATION,DATE_OF_DUE,FLAT_ID,BILL_ID,BILL_STATUS,TOTAL_AMOUNT)"
        + " VALUES (?,?,?,?,?,?)";

  private static String BILL_MASTER_FINALIZE_STATUS_UPDATE_QUERY =
      "UPDATE BILLS_INWARDS_MASTER SET BILL_STATUS=1 WHERE BILL_ID = ?";

  private static final String BILL_MASTER_TOTAL_AMOUNT_UPDATE_QUERY =
      "UPDATE BILLS_INWARDS_MASTER SET TOTAL_AMOUNT = ? WHERE BILL_ID = ?";

  private static String BILL_DETAILS_INSERT_QUERY =
      "INSERT INTO BILLS_INWARDS_DETAILS (BILL_ID,CHARGE_NAME,CHARGE_TYPE,CHARGE_AMOUNT) VALUES (?,?,?,?)";

  private static String BILL_MASTER_BY_FLAT_ID_SELECT_QUERY =
    "SELECT DATE_OF_GENERATION, DATE_OF_DUE, FLAT_ID, BILL_ID,"
        + " BILL_STATUS, TOTAL_AMOUNT" + " FROM BILLS_INWARDS_MASTER"
        + " WHERE FLAT_ID = ? ORDER BY DATE_OF_GENERATION DESC LIMIT 1";

  private static String BILL_DETAILS_BY_BILL_ID_SELECT_QUERY =
    "SELECT CHARGE_AMOUNT, CHARGE_NAME, CHARGE_TYPE, BILL_ID FROM BILLS_INWARDS_DETAILS"
        + " WHERE BILL_ID = ?";

  /**
   * @throws Exception
   * 
   */
  public void generateInvoices(Date current) throws Exception {
    FlatsHelper flatsHelper = new FlatsHelper();
    ArrayList<FlatDetailsVO> flatDetailsVOList = flatsHelper.getAllFlats();
    int billId = 0;
    for (FlatDetailsVO flatDetailsVO : flatDetailsVOList) {

      BillMasterVO billMasterVO = new BillMasterVO();

      billMasterVO.setFlatId(flatDetailsVO.getFlatId());

      billMasterVO.setBillId("MGRA/" + (1900 + current.getYear()) + "/"
          + (current.getMonth() + 1) + "/"
          + (StringUtil.padCharLead("" + (++billId), 4, "0")));

      billMasterVO.setDateOfGeneration(current);
      billMasterVO.setDateOfDue(DateUtil.addToDate(current, 14));
      billMasterVO.setBillStatus(0);

      BillDetailsVO billDetailsVO = new BillDetailsVO();
      billDetailsVO.setBillId(billMasterVO.getBillId());

      // bill for pending amount if any
      FinancialsHelper financialsHelper = new FinancialsHelper();
      FinancialDetailsVO financialDetailsVO =
          financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());

      if (financialDetailsVO != null
          && financialDetailsVO.getCurrentBalance() != 0) {

        billDetailsVO.setChargeName("Pending charges from previous months");
        billDetailsVO.setChargeType("system");
        billDetailsVO
            .setChargeAmount(financialDetailsVO.getCurrentBalance());

        billMasterVO.addToBillDetailsList(billDetailsVO);
      }
      // bill for pending amount if any

      // bill for new month
      billDetailsVO = new BillDetailsVO();
      billDetailsVO.setBillId(billMasterVO.getBillId());
      billDetailsVO
          .setChargeName("Maintenance charges for the month of "
              + (DateUtil.dateToString(current, DateFormat.MMM_yy)/* current.getMonth()+1 */));
      billDetailsVO.setChargeType("system");

      SystemHelper systemHelper = new SystemHelper();
      Double chargeAmount =
          flatDetailsVO.getAreaInSqft()
              * systemHelper.getMonthlyMaintenanceRate();
      billDetailsVO.setChargeAmount(chargeAmount);

      billMasterVO.addToBillDetailsList(billDetailsVO);
      // bill for new month

      Double thisMonthAmount = 0.0;
      for (BillDetailsVO detailsVO : billMasterVO.getBillDetailsList()) {
        thisMonthAmount += detailsVO.getChargeAmount();
      }
      billMasterVO.setTotalAmount(thisMonthAmount);

      // creating updated finc table data
      FinancialDetailsVO newFincDetailsVO = new FinancialDetailsVO();
      newFincDetailsVO.setFlatId(financialDetailsVO.getFlatId());
      newFincDetailsVO.setCurrentBalance(thisMonthAmount);
      newFincDetailsVO.setPriorBalanceMonthOne(thisMonthAmount-financialDetailsVO.getCurrentBalance());
      newFincDetailsVO.setPriorBalanceMonthTwo(financialDetailsVO.getPriorBalanceMonthOne());
      newFincDetailsVO.setPriorBalanceMonthThree(financialDetailsVO.getPriorBalanceMonthTwo());
      newFincDetailsVO.setPriorBalanceMonthFour(financialDetailsVO.getPriorBalanceMonthThree());
      newFincDetailsVO.setPriorBalanceMonthFive(financialDetailsVO.getPriorBalanceMonthFour());
      newFincDetailsVO.setPriorBalanceMonthSix(financialDetailsVO.getPriorBalanceMonthFive());
      newFincDetailsVO.setPriorBalanceMonthMore(financialDetailsVO.getPriorBalanceMonthSix() + financialDetailsVO.getPriorBalanceMonthMore());

      // Updating DB
      insertBillMaster(billMasterVO);
      for (BillDetailsVO detailsVO : billMasterVO.getBillDetailsList()) {
        insertBillDetails(detailsVO);
      }

      financialsHelper.updateFinancialDetails(newFincDetailsVO);

      System.out.println("Generated bill for "
          + flatDetailsVO.getFullFlatNumber());
    }

    SystemHelper systemHelper = new SystemHelper();
    systemHelper.updateLastBillDate(current);
  }

  /**
   * @throws DbException
   * 
   */
  public void insertBillDetails(BillDetailsVO billDetailsVO)
      throws DbException {
    mySqlSession.executeUpdate(BILL_DETAILS_INSERT_QUERY, billDetailsVO
        .getBillId(), billDetailsVO.getChargeName(), billDetailsVO
        .getChargeType(), billDetailsVO.getChargeAmount());
  }

  /**
   * @throws DbException
   * 
   */
  private void insertBillMaster(BillMasterVO billMasterVO)
      throws DbException {
    mySqlSession.executeUpdate(BILL_MASTER_INSERT_QUERY, billMasterVO
        .getDateOfGeneration(), billMasterVO.getDateOfDue(), billMasterVO
        .getFlatId(), billMasterVO.getBillId(),
        billMasterVO.getBillStatus(), billMasterVO.getTotalAmount());
  }

  /**
   * @throws DbException
   * 
   */
  public BillMasterVO getBillMaster(int flatId) throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(BILL_MASTER_BY_FLAT_ID_SELECT_QUERY,
            flatId);
    BillMasterVO billMasterVO = null;
    if (dataSet.getRowCount() >= 1) {
      billMasterVO = getBillMasterVO(dataSet, 0);
    }
    
    dataSet = mySqlSession.executeQuery(BILL_DETAILS_BY_BILL_ID_SELECT_QUERY, billMasterVO.getBillId());
    if (dataSet.getRowCount() >= 1) {
      int i = 0;
      while (i < dataSet.getRowCount()) {
        BillDetailsVO billDetailsVO = getBillDetailsVO(dataSet, i++);
        billMasterVO.addToBillDetailsList(billDetailsVO);
      }
    }
    return billMasterVO;
  }

  /**
   * 
   */
  private BillMasterVO getBillMasterVO(DataSet dataSet, int rowNum) {
    BillMasterVO billMasterVO = new BillMasterVO();
    billMasterVO.setDateOfGeneration(dataSet.getDateValue(rowNum, 0));
    billMasterVO.setDateOfDue(dataSet.getDateValue(rowNum, 1));
    billMasterVO.setFlatId(dataSet.getIntValue(rowNum, 2));
    billMasterVO.setBillId(dataSet.getStringValue(rowNum, 3));
    billMasterVO.setBillStatus(dataSet.getIntValue(rowNum, 4));
    billMasterVO.setTotalAmount(dataSet.getDoubleValue(rowNum, 5));
    return billMasterVO;
  }
  
  /**
   * 
   */
  private BillDetailsVO getBillDetailsVO(DataSet dataSet, int rowNum) {
    BillDetailsVO billDetailsVO = new BillDetailsVO();
    billDetailsVO.setChargeAmount(dataSet.getDoubleValue(rowNum, 0));
    billDetailsVO.setChargeName(dataSet.getStringValue(rowNum, 1));
    billDetailsVO.setChargeType(dataSet.getStringValue(rowNum, 2));
    billDetailsVO.setBillId(dataSet.getStringValue(rowNum, 3));
    return billDetailsVO;
  }

  /**
   * @param billId
   * @throws DbException 
   */
  public void updateBillStatus(String billId) throws DbException {
    mySqlSession.executeUpdate(BILL_MASTER_FINALIZE_STATUS_UPDATE_QUERY, billId);
  }

  /**
   * @param billId
   * @param totalAmount
   * @throws DbException 
   */
  public void updateTotalAmount(String billId, Double totalAmount)
      throws DbException {
    mySqlSession.executeUpdate(BILL_MASTER_TOTAL_AMOUNT_UPDATE_QUERY,
        totalAmount, billId);
  }
  
  /**
   * @param billId
   * @param totalAmount
   * @throws DbException 
   */
  public void finalizeAll() throws DbException {
    FlatsHelper flatsHelper = new FlatsHelper();
    ArrayList<FlatDetailsVO> flatDetailsVOList = flatsHelper.getAllFlats();
    for (FlatDetailsVO flatDetailsVO : flatDetailsVOList) {
      BillMasterVO billMasterVO = getBillMaster(flatDetailsVO.getFlatId());
      updateBillStatus(billMasterVO.getBillId());
    }
  }

  /**
   * @param billId
   * @param totalAmount
   * @throws DbException 
   */
  public boolean isPrintReady() throws DbException {
    FlatsHelper flatsHelper = new FlatsHelper();
    ArrayList<FlatDetailsVO> flatDetailsVOList = flatsHelper.getAllFlats();
    for (FlatDetailsVO flatDetailsVO : flatDetailsVOList) {
      BillHelper billHelper = new BillHelper();
      BillMasterVO billMasterVO =
          billHelper.getBillMaster(flatDetailsVO.getFlatId());
      if (billMasterVO.getBillStatus() != 1) {
        return false;
      }
    }
    return true;
  }
}