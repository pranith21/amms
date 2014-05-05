package com.iq.amms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.service.BaseService;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.string.StringUtil;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.ChequeDetailsVO;
import com.iq.amms.valueobjects.DwellersMasterVO;
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
  private static final String COMMENTS_KEY = "comments";

  private static final String FLAT_ID_KEY = "flatId";
  private static final String CURRENT_BALANCE_KEY = "currentBalance";
  private static final String PAYMENT_MASTER_VO_KEY = "paymentMasterVO";
  private static final String FULL_FLATNUMBER_KEY = "fullFlatNumber";
  private static final String MAIL_SUBJECT = "MGRA - Payment Info";
  private String paymentMessage = "Your payment of Rs.%s has been received. Current Balance Rs.%s.";
  
  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    int flatId = 0;
    Double billAmount = null;
    Date chequeDrawnDate = null;
    int chequeNumber = 0;

    if (input.get(FLAT_ID_KEY) != null && input.get(FLAT_ID_KEY) != "") {
      flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
    }
    if (input.get(PAID_AMOUNT_KEY) != null
        && input.get(PAID_AMOUNT_KEY) != "") {
      billAmount = Double.valueOf(input.get(PAID_AMOUNT_KEY).toString());
    }
   
    if (input.get(CHEQUE_NUMBER_KEY) != null
        && input.get(CHEQUE_NUMBER_KEY) != "") {
      chequeNumber =
        Integer.valueOf(input.get(CHEQUE_NUMBER_KEY).toString());
    }

    if (input.get(CHEQUE_DRAWN_DATE_KEY) != null
    		&& input.get(CHEQUE_DRAWN_DATE_KEY) != "") {
    	chequeDrawnDate = DateUtil.stringToDate(input.get(CHEQUE_DRAWN_DATE_KEY)
    			.toString(),DateFormat.yyyy_MM_dd);
    }

    String paymentMode = StringUtil.getStringValue(input.get(PAYMENT_MODE_SELECT_KEY));
    String chequeDraweeBank = StringUtil.getStringValue(input.get(CHEQUE_DRAWEE_BANK_KEY));
    String chequeDrawnBranch = StringUtil.getStringValue(input.get(CHEQUE_DRAWN_BRANCH_KEY));
    String neftTransactionID = StringUtil.getStringValue(input.get(NEFT_TRANSACTION_ID_KEY));
    String comments = StringUtil.getStringValue(input.get(COMMENTS_KEY));
   
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
      }else if(paymentMode.equals(PaymentMode.DISCOUNT.getPaymentModeValue()) || 
    		  paymentMode.equals(PaymentMode.ERROR_RECTIFICATION.getPaymentModeValue())){
    	  paymentMasterVO.setPaymentStatus(5);
    	  paymentDetailsVO.setComments(comments);
      }
    }
    
    PaymentsHelper paymentsHelper = new PaymentsHelper();

    int i = paymentsHelper.insertPaymentMaster(paymentMasterVO);

    if (i > 0) {
      long paymentID =
          paymentsHelper.lastPaymentID(paymentMasterVO.getFlatId());
      System.out.println("Payment ID - "+paymentID);
      System.out.println("detailsUpdateRequired - "+detailsUpdateRequired);
      if (paymentID != 0) {
        paymentMasterVO.setPaymentID(paymentID);
        if (detailsUpdateRequired) {
          paymentDetailsVO.setPaymentID(paymentID);
          paymentsHelper.insertPaymentDetails(paymentDetailsVO);
        }
      }
    }
    
    FlatsHelper flatsHelper = new FlatsHelper();
    Double currBal = 0.0;

    if (paymentMode.equals(PaymentMode.CASH.getPaymentModeValue()) || 
    		paymentMode.equals(PaymentMode.NEFT.getPaymentModeValue())|| 
  		  paymentMode.equals(PaymentMode.DISCOUNT.getPaymentModeValue())|| 
		  paymentMode.equals(PaymentMode.ERROR_RECTIFICATION.getPaymentModeValue())) {
      currBal = paymentsHelper.updateFinancialBills(flatId, billAmount);
      ApartmentMasterParamsHelper apartmentMasterParamsHelper = new ApartmentMasterParamsHelper();
	  DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatId);
	  billAmount = Double.valueOf(String.format("%1.2f",billAmount));
	  paymentMessage = String.format(paymentMessage, billAmount, currBal);
      if ("1".equals(apartmentMasterParamsHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_SMS))) {
    	  Communicator communicator = CommunicationManager
  				.getCommunicator(CommunicationType.TEXT_GURU_SMS);
    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneMobile(), paymentMessage);
    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneResidence(), paymentMessage);
    	  }
//      if ("1".equals(apartmentMasterParamsHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_MAIL))) {
//    	  Communicator gmailCommunicator = CommunicationManager
//  				.getCommunicator(CommunicationType.GMAIL_MAIL);
//    	  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(), null, null, MAIL_SUBJECT, paymentMessage, null);
//      }
    }

    ArrayList<PaymentMasterVO> paymentMasterVOs =
        paymentsHelper.getPaymentMasterByFlatID(flatId);
    if (paymentMasterVOs != null && !paymentMasterVOs.isEmpty()
        && paymentMasterVOs.size() > 0) {
      paymentMasterVO = paymentMasterVOs.get(0);
    }
    
    FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
    resultAttributes.put(FULL_FLATNUMBER_KEY, flatDetailsVO.getFullFlatNumber());
    resultAttributes.put(FLAT_ID_KEY, flatId);
    resultAttributes.put(CURRENT_BALANCE_KEY, currBal);
    resultAttributes.put(PAYMENT_MASTER_VO_KEY, paymentMasterVO);

    redirectUrl = "/payments.jsp";
  }

}
