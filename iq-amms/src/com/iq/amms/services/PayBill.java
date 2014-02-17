package com.iq.amms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.ChequeDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.NeftDetailsVO;
import com.iq.amms.valueobjects.PaymentDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 */
public class PayBill extends BaseService {

  private static final String PAID_AMOUNT_KEY = "paidAmount";
  private static final String PAYMENT_MODE_SELECT_KEY = "paymentModeSelect";

  private static final String CHEQUE_NUMBER_KEY = "chequeNumber";
  private static final String CHEQUE_DRAWEE_BANK_KEY = "chequeDraweeBank";
  private static final String CHEQUE_DRAWN_BRANCH_KEY = "chequeDrawnBranch";
  private static final String CHEQUE_DRAWN_DATE_KEY = "chequeDrawnDate";
  private static final String NEFT_TRANSACTION_ID_KEY = "neftTransactionId";

  private static final String FLAT_ID_KEY = "flatId";
  private static final String CURRENT_BALANCE_KEY = "currentBalance";
  private static final String PAYMENT_MASTER_VO_KEY = "paymentMasterVO";
  private static final String FULL_FLATNUMBER_KEY = "fullFlatNumber";

  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    int flatId = 0;
    Double billAmount = null;
    String paymentMode = null;
    String chequeDraweeBank = null;
    String chequeDrawnBranch = null;
    Date chequeDrawnDate = null;
    int chequeNumber = 0;
    String neftTransactionID = null;

    if (input.get(FLAT_ID_KEY) != null && input.get(FLAT_ID_KEY) != "") {
      flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
    }
    if (input.get(PAID_AMOUNT_KEY) != null
        && input.get(PAID_AMOUNT_KEY) != "") {
      billAmount = Double.valueOf(input.get(PAID_AMOUNT_KEY).toString());
    }
    if (input.get(PAYMENT_MODE_SELECT_KEY) != null
        && input.get(PAYMENT_MODE_SELECT_KEY) != "") {
      paymentMode = input.get(PAYMENT_MODE_SELECT_KEY).toString();
    }
    if (input.get(CHEQUE_DRAWEE_BANK_KEY) != null
        && input.get(CHEQUE_DRAWEE_BANK_KEY) != "") {
      chequeDraweeBank = input.get(CHEQUE_DRAWEE_BANK_KEY).toString();
    }
    if (input.get(CHEQUE_NUMBER_KEY) != null
        && input.get(CHEQUE_NUMBER_KEY) != "") {
      chequeNumber =
        Integer.valueOf(input.get(CHEQUE_NUMBER_KEY).toString());
    }
    if (input.get(CHEQUE_DRAWN_BRANCH_KEY) != null
    		&& input.get(CHEQUE_DRAWN_BRANCH_KEY) != "") {
    	chequeDrawnBranch = input.get(CHEQUE_DRAWN_BRANCH_KEY).toString();
    }
    if (input.get(CHEQUE_DRAWN_DATE_KEY) != null
    		&& input.get(CHEQUE_DRAWN_DATE_KEY) != "") {
    	chequeDrawnDate = DateUtil.stringToDate(input.get(CHEQUE_DRAWN_DATE_KEY).
    			toString(), DateFormat.MMM_dd_yyyy);
    }
    if (input.get(NEFT_TRANSACTION_ID_KEY) != null
        && input.get(NEFT_TRANSACTION_ID_KEY) != "") {
      neftTransactionID = input.get(NEFT_TRANSACTION_ID_KEY).toString();
    }

    PaymentMasterVO paymentMasterVO = new PaymentMasterVO();
    PaymentDetailsVO paymentDetailsVO = new PaymentDetailsVO();

    paymentMasterVO.setFlatId(flatId);
    paymentMasterVO.setPaidAmount(billAmount);
    paymentMasterVO.setPaymentType(paymentMode);
    paymentMasterVO.setPaymentDate(new Date());
    
    boolean detailsUpdateRequired = false;
    
    if (paymentMode.equals(PaymentMode.CASH.getPaymentModeValue())) {
      detailsUpdateRequired = false;
      paymentMasterVO.setPaymentStatus(5);
    }
    else {
      detailsUpdateRequired = true;
      if (paymentMode.equals(PaymentMode.CHEQUE.getPaymentModeValue())) {
        paymentMasterVO.setPaymentStatus(1);

        ChequeDetailsVO chequeDetailsVO = new ChequeDetailsVO();
        chequeDetailsVO.setChequeNumber(chequeNumber);
        chequeDetailsVO.setChequeDraweeBank(chequeDraweeBank);
        chequeDetailsVO.setChequeDrawnBranch(chequeDrawnBranch);
        chequeDetailsVO.setChequeDrawnDate(chequeDrawnDate);
        chequeDetailsVO.setChequeReceivedDate(new Date());
        chequeDetailsVO.setChequeClearanceDate(null);

        paymentDetailsVO.setChequeDetailsVO(chequeDetailsVO);
      }
      else if (paymentMode.equals(PaymentMode.NEFT.getPaymentModeValue())) {
        paymentMasterVO.setPaymentStatus(5);

        NeftDetailsVO neftDetailsVO = new NeftDetailsVO();
        neftDetailsVO.setNeftTransactionID(neftTransactionID);
        neftDetailsVO.setNeftTransactionDate(new Date());
        neftDetailsVO.setNeftClearanceDate(new Date());

        paymentDetailsVO.setNeftDetailsVO(neftDetailsVO);
      }
    }
    
    PaymentsHelper paymentsHelper = new PaymentsHelper();

    int i = paymentsHelper.insertPaymentMaster(paymentMasterVO);

    if (i > 0) {
      long paymentID =
          paymentsHelper.lastPaymentID(paymentMasterVO.getFlatId());
      if (paymentID != 0) {
        paymentMasterVO.setPaymentID(paymentID);
        if (detailsUpdateRequired) {
          paymentDetailsVO.setPaymentID(paymentID);
          paymentsHelper.insertPaymentDetails(paymentDetailsVO);
        }
      }
    }
    
    Double currBal = 0.0;
    if (paymentMode.equals(PaymentMode.CASH.getPaymentModeValue()) || 
    		paymentMode.equals(PaymentMode.NEFT.getPaymentModeValue())) {
      currBal = paymentsHelper.updateFinancialBills(flatId, billAmount);
    }

    ArrayList<PaymentMasterVO> paymentMasterVOs =
        paymentsHelper.getPaymentMasterByFlatID(flatId);
    if (paymentMasterVOs != null && !paymentMasterVOs.isEmpty()
        && paymentMasterVOs.size() > 0) {
      paymentMasterVO = paymentMasterVOs.get(0);
    }
    
    FlatsHelper flatsHelper = new FlatsHelper();
    FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
    resultAttributes.put(FULL_FLATNUMBER_KEY, flatDetailsVO.getFullFlatNumber());
    resultAttributes.put(FLAT_ID_KEY, flatId);
    resultAttributes.put(CURRENT_BALANCE_KEY, currBal);
    resultAttributes.put(PAYMENT_MASTER_VO_KEY, paymentMasterVO);

    redirectUrl = "/payments.jsp";
  }

}
