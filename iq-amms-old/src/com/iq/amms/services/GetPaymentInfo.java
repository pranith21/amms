package com.iq.amms.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 *
 */
public class GetPaymentInfo extends BaseService {

	  private static final String BLOCK_KEY = "block";
	  private static final String FLAT_NUMBER_KEY = "flatNumber";
	  private static final String FINANCIAL_DETAILS = "FinancialDetails";
	  private static final String PAYMENT_MASTER_DETAILS = "PaymentMasterDetails";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		String block = StringUtil.getStringValue(input.get(BLOCK_KEY));		
		String flatNumber = StringUtil.getStringValue(input.get(FLAT_NUMBER_KEY));
	    FlatsHelper flatsHelper = new FlatsHelper();
		FinancialsHelper financialsHelper = new FinancialsHelper();
	    PaymentsHelper paymentsHelper = new PaymentsHelper();
	    
	    ArrayList<PaymentMasterVO> paymentMasterVOs = null;
	    
	    PaymentMasterVO paymentMasterVO = null;

	    FlatDetailsVO flatDetailsVO =
	        flatsHelper.getFlatDetails(block, flatNumber);
		FinancialDetailsVO financialDetailsVO = null;
	    if (flatDetailsVO != null) {
	    	financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
	    	paymentMasterVOs = paymentsHelper.getPaymentMasterByFlatID(flatDetailsVO.getFlatId());
	    	
	    	if (paymentMasterVOs!= null && !paymentMasterVOs.isEmpty() && paymentMasterVOs.size() > 0) {
				paymentMasterVO = paymentMasterVOs.get(0);
			}
		}
	    
		resultAttributes.put(FINANCIAL_DETAILS, financialDetailsVO);
		resultAttributes.put(PAYMENT_MASTER_DETAILS, paymentMasterVO);
		redirectUrl = "/payments.jsp";
	}

}
