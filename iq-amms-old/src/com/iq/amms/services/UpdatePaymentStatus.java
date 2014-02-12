package com.iq.amms.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 *
 */
public class UpdatePaymentStatus extends BaseService {

	private static final String FLAT_ID = "flatId";
	private static final String PAYMENT_ID = "paymentID";	
	private static final String PAYMENT_STATUS = "status";
	private static final String BILL_AMOUNT = "amountPaid";
	private static final String FINANCIAL_DETAILS = "FinancialDetails";
	private static final String PAYMENT_MASTER_DETAILS = "PaymentMasterDetails";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		Long paymentID = null;
		int paymentStatus = 0;
		int flatId = 0;
		Double paidAmount = null;
		
		if (input.get(FLAT_ID) != null && input.get(FLAT_ID) != "") {
			flatId = Integer.valueOf(input.get(FLAT_ID).toString());
		}
		if (input.get(PAYMENT_STATUS) != null && input.get(PAYMENT_STATUS) != "") {
			paymentStatus = Integer.valueOf(input.get(PAYMENT_STATUS).toString());
		}
		if (input.get(PAYMENT_ID) != null && input.get(PAYMENT_ID) != "") {
			paymentID = Long.valueOf(input.get(PAYMENT_ID).toString());
		}
		if (input.get(BILL_AMOUNT) != null && input.get(BILL_AMOUNT) != "") {
			paidAmount = Double.valueOf(input.get(BILL_AMOUNT).toString());
		}
		
		PaymentsHelper paymentsHelper = new PaymentsHelper();
		FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
		FinancialsHelper financialsHelper = new FinancialsHelper();
		financialDetailsVO = financialsHelper.getFinancialDetails(flatId);
		int i = paymentsHelper.updatePaymentMaster(paymentStatus, paymentID);
		if (i > 0) {
			if (paymentStatus == 5){
				if (paidAmount != null){
					financialDetailsVO.setCurrentBalance(paymentsHelper
							.updateFinancialBills(flatId, paidAmount));
				}
			}else {
				i = financialsHelper.incrementBounceFlag(flatId);
			}
		}
		
		ArrayList<PaymentMasterVO> paymentMasterVOs  = paymentsHelper.getPaymentMasterByFlatID(flatId);
		PaymentMasterVO paymentMasterVO = null;
		if (paymentMasterVOs!= null && !paymentMasterVOs.isEmpty() && paymentMasterVOs.size() > 0) {
			paymentMasterVO = paymentMasterVOs.get(0);
		}
		resultAttributes.put(FINANCIAL_DETAILS, financialDetailsVO);
		resultAttributes.put(PAYMENT_MASTER_DETAILS, paymentMasterVO);
		redirectUrl = "/payments.jsp";
	}
}
