package com.iq.amms.services;

import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.ChequeDetailsVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.NEFTDetailsVO;
import com.iq.amms.valueobjects.PaymentDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 *
 */
public class PayBill extends BaseService {
	
  private static final String FLAT_NUMBER_KEY = "flatNumber";
  private static final String BILL_AMOUNT = "paidAmount";
  private static final String CURRENT_BALANCE = "currentbalance";
  private static final String PAYMENT_PROCESSED = "paymentProcessed";

  private static final String PAYMENT_TYPE = "paymentMode";
  private static final String CHEQUE_DRAWEE_BANK = "chequeDraweeBank";
  private static final String CHEQUE_NUMBER = "chequeNumber";
  private static final String NEFT_TRANSACTION_ID = "neftTransactionID";

  /* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		int flatNumber = 0;
		Double billAmount = null;
		int paymentMode = -1;
		String chequeDraweeBank = null;
		int chequeNumber = 0;		
		int neftTransactionID = 0;		
	
		if (input.get(FLAT_NUMBER_KEY) != null && input.get(FLAT_NUMBER_KEY) != "") {
			flatNumber = Integer.valueOf(input.get(FLAT_NUMBER_KEY).toString());
		}
		if (input.get(BILL_AMOUNT) != null && input.get(BILL_AMOUNT) != "") {
			billAmount = Double.valueOf(input.get(BILL_AMOUNT).toString());
		}
		if (input.get(PAYMENT_TYPE) != null && input.get(PAYMENT_TYPE) != "") {
			 paymentMode = Integer.valueOf(input.get(PAYMENT_TYPE).toString());
		}
		if (input.get(CHEQUE_DRAWEE_BANK) != null && input.get(CHEQUE_DRAWEE_BANK) != "") {
			chequeDraweeBank = input.get(CHEQUE_DRAWEE_BANK).toString();
		}
		if (input.get(CHEQUE_NUMBER) != null && input.get(CHEQUE_NUMBER) != "") {
			chequeNumber = Integer.valueOf(input.get(CHEQUE_NUMBER).toString());
		}
		if (input.get(NEFT_TRANSACTION_ID) != null && input.get(NEFT_TRANSACTION_ID) != "") {
			neftTransactionID = Integer.valueOf(input.get(NEFT_TRANSACTION_ID).toString());
		}
		
		PaymentsHelper paymentsHelper = new PaymentsHelper();
		FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
		financialDetailsVO.setCurrentBalance(paymentsHelper.updateFinancialBills(flatNumber, billAmount));

		PaymentDetailsVO paymentDetailsVO = new PaymentDetailsVO();
		PaymentMasterVO paymentMasterVO = new PaymentMasterVO();
		paymentMasterVO.setFlatNumber(flatNumber);
		paymentMasterVO.setPaidAmount(billAmount);
		paymentDetailsVO.setPaymentType(paymentMode);
		paymentMasterVO.setPaymentDate(new Date());
		
		ChequeDetailsVO chequeDetailsVO = new ChequeDetailsVO();
		if (paymentMode == 1) {
			chequeDetailsVO.setChequeClearanceDate(new Date());
			chequeDetailsVO.setChequeDrawnDate(new Date());			
			paymentMasterVO.setPaymentStatus(0);
		}else{
			chequeDetailsVO.setChequeClearanceDate(null);
			chequeDetailsVO.setChequeDrawnDate(null);						
		}
		chequeDetailsVO.setChequeDraweeBank(chequeDraweeBank);
		chequeDetailsVO.setChequeNumber(chequeNumber);
		paymentDetailsVO.setChequeDetailsVO(chequeDetailsVO);
		
		NEFTDetailsVO neftDetailsVO = new NEFTDetailsVO();
		if (paymentMode == 2) {
			neftDetailsVO.setNeftClearanceDate(new Date());
			neftDetailsVO.setNEFTTransactionDate(new Date());			
			paymentMasterVO.setPaymentStatus(3);
		}else{
			neftDetailsVO.setNeftClearanceDate(null);
			neftDetailsVO.setNEFTTransactionDate(null);			
		}
		neftDetailsVO.setNEFTTransactionID(neftTransactionID);
		if (paymentMode == 0) {
			paymentMasterVO.setPaymentStatus(5);
		}
		paymentDetailsVO.setNeftDetailsVO(neftDetailsVO);
		
		int i = paymentsHelper.insertPaymentMasterDetails(paymentMasterVO);
		if(i > 0){
			Number paymentID = paymentsHelper.lastPaymentID(paymentMasterVO.getFlatNumber());
			if (paymentID != null) {
				paymentDetailsVO
						.setPaymentID(Long.valueOf(paymentID.toString()));
				i = paymentsHelper.insertPaymentDetails(paymentDetailsVO);
			}
		}
		
		resultAttributes.put(CURRENT_BALANCE, financialDetailsVO.getCurrentBalance());
		resultAttributes.put(PAYMENT_PROCESSED, true);
		redirectUrl = "/payments.jsp";
	}

}
