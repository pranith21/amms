package com.iq.amms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.FinancialsHelper;
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

	private static final String BILL_AMOUNT = "paidAmount";
	private static final String PAYMENT_MODE = "paymentMode";
	private static final String CHEQUE_DRAWEE_BANK = "chequeDraweeBank";
	private static final String CHEQUE_NUMBER = "chequeNumber";
	private static final String NEFT_TRANSACTION_ID = "neftTransactionID";
	private static final String FLAT_ID_KEY = "flatId";	
	private static final String FINANCIAL_DETAILS = "FinancialDetails";
	private static final String PAYMENT_MASTER_DETAILS = "PaymentMasterDetails";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		int flatId = 0;
		Double billAmount = null;
		String paymentMode = null;
		String chequeDraweeBank = null;
		int chequeNumber = 0;		
		String neftTransactionID = null;		

		if (input.get(FLAT_ID_KEY) != null && input.get(FLAT_ID_KEY) != "") {
			flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
		}
		if (input.get(BILL_AMOUNT) != null && input.get(BILL_AMOUNT) != "") {
			billAmount = Double.valueOf(input.get(BILL_AMOUNT).toString());
		}
		if (input.get(PAYMENT_MODE) != null && input.get(PAYMENT_MODE) != "") {
			paymentMode = input.get(PAYMENT_MODE).toString();
		}
		if (input.get(CHEQUE_DRAWEE_BANK) != null && input.get(CHEQUE_DRAWEE_BANK) != "") {
			chequeDraweeBank = input.get(CHEQUE_DRAWEE_BANK).toString();
		}
		if (input.get(CHEQUE_NUMBER) != null && input.get(CHEQUE_NUMBER) != "") {
			chequeNumber = Integer.valueOf(input.get(CHEQUE_NUMBER).toString());
		}
		if (input.get(NEFT_TRANSACTION_ID) != null && input.get(NEFT_TRANSACTION_ID) != "") {
			neftTransactionID = input.get(NEFT_TRANSACTION_ID).toString();
		}

		PaymentsHelper paymentsHelper = new PaymentsHelper();

		PaymentDetailsVO paymentDetailsVO = new PaymentDetailsVO();
		PaymentMasterVO paymentMasterVO = new PaymentMasterVO();
		paymentMasterVO.setFlatId(flatId);
		paymentMasterVO.setPaidAmount(billAmount);
		paymentMasterVO.setPaymentType(paymentMode);
		paymentMasterVO.setPaymentDate(new Date());

		ChequeDetailsVO chequeDetailsVO = new ChequeDetailsVO();
		if (paymentMode.equals(PaymentMode.CHEQUE.getPaymentModeValue())) {
			chequeDetailsVO.setChequeClearanceDate(new Date());
			chequeDetailsVO.setChequeDrawnDate(new Date());			
			paymentMasterVO.setPaymentStatus(1);
		}else{
			chequeDetailsVO.setChequeClearanceDate(null);
			chequeDetailsVO.setChequeDrawnDate(null);						
		}
		chequeDetailsVO.setChequeDraweeBank(chequeDraweeBank);
		chequeDetailsVO.setChequeNumber(chequeNumber);
		paymentDetailsVO.setChequeDetailsVO(chequeDetailsVO);

		NEFTDetailsVO neftDetailsVO = new NEFTDetailsVO();
		if (paymentMode.equals(PaymentMode.NEFT.getPaymentModeValue())) {
			neftDetailsVO.setNeftClearanceDate(new Date());
			neftDetailsVO.setNEFTTransactionDate(new Date());			
			paymentMasterVO.setPaymentStatus(3);
		}else{
			neftDetailsVO.setNeftClearanceDate(null);
			neftDetailsVO.setNEFTTransactionDate(null);			
		}
		neftDetailsVO.setNEFTTransactionID(neftTransactionID);
		if (paymentMode.equals(PaymentMode.CASH.getPaymentModeValue())) {
			paymentMasterVO.setPaymentStatus(5);
		}
		paymentDetailsVO.setNeftDetailsVO(neftDetailsVO);

		int i = paymentsHelper.insertPaymentMasterDetails(paymentMasterVO);
		int paymentID = paymentsHelper.lastPaymentID(paymentMasterVO.getFlatId());
		if (paymentID != 0) {
			paymentMasterVO.setPaymentID(paymentID);
			if(i > 0 && !paymentMode.equals(PaymentMode.CASH.getPaymentModeValue())){
				paymentDetailsVO
				.setPaymentID(paymentID);
				i = paymentsHelper.insertPaymentDetails(paymentDetailsVO);
			}
		}

		FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
		FinancialsHelper financialsHelper = new FinancialsHelper();
		financialDetailsVO = financialsHelper.getFinancialDetails(flatId);
		if(paymentMode.equals(PaymentMode.CASH.getPaymentModeValue())){
			financialDetailsVO.setCurrentBalance(paymentsHelper.updateFinancialBills(flatId, billAmount));
		}

		ArrayList<PaymentMasterVO> paymentMasterVOs  = paymentsHelper.getPaymentMasterByFlatID(flatId);
		if (paymentMasterVOs!= null && !paymentMasterVOs.isEmpty() && paymentMasterVOs.size() > 0) {
			paymentMasterVO = paymentMasterVOs.get(0);
		}

		resultAttributes.put(FINANCIAL_DETAILS, financialDetailsVO);
		resultAttributes.put(PAYMENT_MASTER_DETAILS, paymentMasterVO);
		redirectUrl = "/payments.jsp";
	}

}
