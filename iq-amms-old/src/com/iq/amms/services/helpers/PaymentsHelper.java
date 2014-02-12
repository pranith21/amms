package com.iq.amms.services.helpers;

import java.util.ArrayList;

import org.iq.db.DataSet;
import org.iq.exception.DbException;

import com.iq.amms.valueobjects.ChequeDetailsVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.NEFTDetailsVO;
import com.iq.amms.valueobjects.PaymentDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 */
public class PaymentsHelper extends BaseHelper {

  private static final String INSERT_PAYMENT_INWARDS_DETAILS =
      "INSERT INTO PAYMENT_INWARDS_DETAILS (PAYMENT_ID, CHEQUE_NUMBER,"
          + "CHEQUE_DRAWEE_BANK,CHEQUE_DRAWN_DATE,CHEQUE_CLEARANCE_DATE,NEFT_TRANSACTION_ID,"
          + "NEFT_TRANSACTION_DATE,NEFT_CLEARANCE_DATE) VALUES(?,?,?,?,?,?,?,?)";

  private static final String INSERT_PAYMENT_INWARDS_MASTER =
      "INSERT INTO PAYMENT_INWARDS_MASTER (FLAT_ID, PAID_AMOUNT, PAYMENT_STATUS,MODE_OF_PAYMENT) VALUES(?,?,?,?)";

  private static final String LAST_INSERTED_PAYMENT_ID =
      "SELECT PAYMENT_ID FROM PAYMENT_INWARDS_MASTER "
          + "WHERE FLAT_ID = ? ORDER BY DATE_OF_PAYMENT DESC";

  private static final String GET_PAYMENT_DETAILS =
      "SELECT PAYMENT_ID, CHEQUE_NUMBER,"
          + "CHEQUE_DRAWEE_BANK,CHEQUE_DRAWN_DATE,CHEQUE_CLEARANCE_DATE,NEFT_TRANSACTION_ID,"
          + "NEFT_TRANSACTION_DATE,NEFT_CLEARANCE_DATE FROM PAYMENT_INWARDS_DETAILS WHERE PAYMENT_ID=?";

  private static final String GET_PAYMENT_MASTER_DETAILS =
      "SELECT FLAT_ID, PAID_AMOUNT, PAYMENT_STATUS,"
          + "DATE_OF_PAYMENT, PAYMENT_ID, MODE_OF_PAYMENT FROM PAYMENT_INWARDS_MASTER WHERE PAYMENT_ID=?";

  private static final String GET_PAYMENT_MASTER_DETAILS_BY_FLAT_ID =
      "SELECT FLAT_ID, PAID_AMOUNT, PAYMENT_STATUS,"
          + "DATE_OF_PAYMENT, PAYMENT_ID, MODE_OF_PAYMENT FROM PAYMENT_INWARDS_MASTER WHERE FLAT_ID=? ORDER BY DATE_OF_PAYMENT DESC";

  private static final String UPDATE_PAYMENT_MASTER = "UPDATE PAYMENT_INWARDS_MASTER" +
			" SET PAYMENT_STATUS = ? WHERE PAYMENT_ID = ?";

  /**
   * @param flatId
   * @param amount
   * @return Double
   */
  public Double updateFinancialBills(int flatId, Double amount) {

    FinancialsHelper financialsHelper = new FinancialsHelper();
    FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
    Double tempAmount = null;
    Double currentBalance = null;
    try {
      financialDetailsVO = financialsHelper.getFinancialDetails(flatId);

      if (financialDetailsVO != null) {
        if (financialDetailsVO.getCurrentBalance().equals(amount)) {
          financialDetailsVO.setCurrentBalance(new Double(0));
          financialDetailsVO.setPriorBalanceMonthOne(new Double(0));
          financialDetailsVO.setPriorBalanceMonthTwo(new Double(0));
          financialDetailsVO.setPriorBalanceMonthThree(new Double(0));
          financialDetailsVO.setPriorBalanceMonthFour(new Double(0));
          financialDetailsVO.setPriorBalanceMonthFive(new Double(0));
          financialDetailsVO.setPriorBalanceMonthMore(new Double(0));
        }
        else {
          if (financialDetailsVO.getPriorBalanceMonthMore() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthMore() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthMore();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthMore(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthMore();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthMore(tempAmount
                  - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0 && financialDetailsVO.getPriorBalanceMonthSix() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthSix() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthSix();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthSix(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthSix();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthFive(tempAmount
                  - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0
              && financialDetailsVO.getPriorBalanceMonthFive() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthFive() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthFive();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthFive(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthFive();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthFive(tempAmount
                  - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0
              && financialDetailsVO.getPriorBalanceMonthFour() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthFour() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthFour();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthFour(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthFour();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthFour(tempAmount
                  - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0
              && financialDetailsVO.getPriorBalanceMonthThree() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthThree() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthThree();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthThree(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthThree();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthThree(tempAmount
                  - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0 && financialDetailsVO.getPriorBalanceMonthTwo() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthTwo() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthTwo();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthTwo(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthTwo();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO
                  .setPriorBalanceMonthTwo(tempAmount - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
          if (amount > 0 && financialDetailsVO.getPriorBalanceMonthOne() > 0) {
            if (financialDetailsVO.getPriorBalanceMonthOne() <= amount) {
              tempAmount = financialDetailsVO.getPriorBalanceMonthOne();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO.setPriorBalanceMonthOne(new Double(0));
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
            else {
              tempAmount = financialDetailsVO.getPriorBalanceMonthOne();
              currentBalance = financialDetailsVO.getCurrentBalance();
              financialDetailsVO
                  .setPriorBalanceMonthOne(tempAmount - amount);
              financialDetailsVO.setCurrentBalance(currentBalance - amount);
              amount = amount - tempAmount;
              tempAmount = null;
              currentBalance = null;
            }
          }
/*          if (amount > 0) {
            currentBalance = financialDetailsVO.getCurrentBalance();
            financialDetailsVO.setCurrentBalance(currentBalance - amount);
            currentBalance = null;
          }*/
        }
        financialsHelper.updateFinancialDetails(financialDetailsVO);
      }
    }
    catch (DbException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return financialDetailsVO.getCurrentBalance();
  }

  /**
   * @param flatNumber
   * @param billAmount
   * @param paymentType
   * @throws DbException
   */
  public int insertPaymentDetails(PaymentDetailsVO paymentDetailsVO)
      throws DbException {
    ChequeDetailsVO chequeDetailsVO = paymentDetailsVO.getChequeDetailsVO();
    NEFTDetailsVO neftDetailsVO = paymentDetailsVO.getNeftDetailsVO();

    int i =
        mySqlSession.executeUpdate(INSERT_PAYMENT_INWARDS_DETAILS,
            paymentDetailsVO.getPaymentID(),
            chequeDetailsVO.getChequeNumber(),
            chequeDetailsVO.getChequeDraweeBank(),
            chequeDetailsVO.getChequeDrawnDate(),
            chequeDetailsVO.getChequeClearanceDate(),
            neftDetailsVO.getNEFTTransactionID(),
            neftDetailsVO.getNEFTTransactionDate(),
            neftDetailsVO.getNeftClearanceDate());
    return i;
  }

  public int insertPaymentMasterDetails(PaymentMasterVO paymentMasterVO)
      throws DbException {
    int i =
        mySqlSession.executeUpdate(INSERT_PAYMENT_INWARDS_MASTER,
            paymentMasterVO.getFlatId(), paymentMasterVO.getPaidAmount(),
            paymentMasterVO.getPaymentStatus(), paymentMasterVO.getPaymentType());
    return i;
  }

  /**
   * @param flatNumber
   * @return Number
   * @throws DbException
   */
  public int lastPaymentID(int flatNumber) throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(LAST_INSERTED_PAYMENT_ID, flatNumber);
    int paymentID = 0;
	if (dataSet.getRowCount() > 0) {
		paymentID = dataSet.getIntValue(0, 0);
	}
    return paymentID;
  }

  /**
   * @param paymentID
   * @return PaymentDetailsVO
   * @throws DbException
   */
  public PaymentDetailsVO getPaymentDetails(int paymentID)
      throws DbException {
    DataSet dataSet =
        mySqlSession.executeQuery(GET_PAYMENT_DETAILS, paymentID);
    PaymentDetailsVO paymentDetailsVO = new PaymentDetailsVO();
    if (dataSet.getRowCount() > 0) {
      ChequeDetailsVO chequeDetailsVO = new ChequeDetailsVO();
      chequeDetailsVO.setChequeClearanceDate(dataSet.getDateValue(0,
          "CHEQUE_CLEARANCE_DATE"));
      chequeDetailsVO.setChequeDraweeBank(dataSet.getStringValue(0,
          "CHEQUE_DRAWEE_BANK"));
      chequeDetailsVO.setChequeDrawnDate(dataSet.getDateValue(0,
          "CHEQUE_DRAWN_DATE"));
      chequeDetailsVO.setChequeNumber(dataSet
          .getIntValue(0, "CHEQUE_NUMBER"));
      paymentDetailsVO.setChequeDetailsVO(chequeDetailsVO);

      NEFTDetailsVO neftDetailsVO = new NEFTDetailsVO();
      neftDetailsVO.setNeftClearanceDate(dataSet.getDateValue(0,
          "NEFT_CLEARANCE_DATE"));
      neftDetailsVO.setNEFTTransactionDate(dataSet.getDateValue(0,
          "NEFT_TRANSACTION_DATE"));
      neftDetailsVO.setNEFTTransactionID(dataSet.getStringValue(0,
          "NEFT_TRANSACTION_ID"));
      paymentDetailsVO.setNeftDetailsVO(neftDetailsVO);
      paymentDetailsVO.setPaymentID(dataSet.getIntValue(0, "PAYMENT_ID"));
    }
    return paymentDetailsVO;
  }

  /**
   * @param paymentID
   * @return PaymentMasterVO
   * @throws DbException
   */
  public PaymentMasterVO getPaymentMaster(int paymentID)
      throws DbException {
    PaymentMasterVO paymentMasterVO = new PaymentMasterVO();
    DataSet dataSet =
        mySqlSession.executeQuery(GET_PAYMENT_MASTER_DETAILS, paymentID);
    if (dataSet.getRowCount() > 0) {
      paymentMasterVO.setPaymentID(paymentID);
      paymentMasterVO.setFlatId(dataSet.getIntValue(0, "FLAT_ID"));
      paymentMasterVO.setPaymentDate(dataSet.getDateValue(0,
          "DATE_OF_PAYMENT"));
      paymentMasterVO.setPaymentStatus(Integer.valueOf(dataSet.getStringValue(0,
          "PAYMENT_STATUS")));
      paymentMasterVO
          .setPaidAmount(dataSet.getDoubleValue(0, "PAID_AMOUNT"));
      paymentMasterVO.setPaymentType(dataSet.getStringValue(0,
              "MODE_OF_PAYMENT"));
    }
    return paymentMasterVO;
  }

  /**
   * @param flatID
   * @return PaymentMasterVO
   * @throws DbException
   */
  public ArrayList<PaymentMasterVO> getPaymentMasterByFlatID(int flatID)
      throws DbException {
    PaymentMasterVO paymentMasterVO = null;
    DataSet dataSet =
        mySqlSession.executeQuery(GET_PAYMENT_MASTER_DETAILS_BY_FLAT_ID,
            flatID);
    
    int limit = 10;
    if(dataSet.getRowCount() < limit){
    	limit = dataSet.getRowCount();
    }
    
    ArrayList<PaymentMasterVO> paymentMasterVOs = new ArrayList<PaymentMasterVO>();
    for(int i =0; i < limit; i++) {
      paymentMasterVO = new PaymentMasterVO();
      paymentMasterVO.setPaymentID(Long.valueOf(dataSet.getIntValue(i,
          "PAYMENT_ID")));
      paymentMasterVO.setFlatId(dataSet.getIntValue(i, "FLAT_ID"));
      paymentMasterVO.setPaymentDate(dataSet.getDateValue(i,
          "DATE_OF_PAYMENT"));
      paymentMasterVO.setPaymentStatus(Integer.valueOf(dataSet
          .getStringValue(i, "PAYMENT_STATUS")));
      paymentMasterVO
          .setPaidAmount(dataSet.getDoubleValue(i, "PAID_AMOUNT"));
      paymentMasterVO.setPaymentType(dataSet.getStringValue(i,
              "MODE_OF_PAYMENT"));
      paymentMasterVOs.add(paymentMasterVO);
    }
    return paymentMasterVOs;
  }
  
  /**
   * @param paymentStatus
   * @param paymentID
   * @return
   * @throws DbException
   */
  public int updatePaymentMaster(int paymentStatus, Long paymentID) throws DbException{
	  int i =0;
	  i = mySqlSession.executeUpdate(UPDATE_PAYMENT_MASTER, paymentStatus,
			  paymentID);
	  return i;
  }
}