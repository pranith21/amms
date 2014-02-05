package com.iq.amms.services;

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
public class CalculateOutstandingBill extends BaseService {

	  private static final String BLOCK_KEY = "block";
	  private static final String FLAT_NUMBER_KEY = "flatNumber";

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
	    
	    PaymentMasterVO paymentMasterVO = null;

	    FlatDetailsVO flatDetailsVO =
	        flatsHelper.getFlatDetails(block, flatNumber);
		FinancialDetailsVO financialDetailsVO = null;
	    if (flatDetailsVO != null) {
	    	financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
	    	paymentMasterVO = paymentsHelper.getPaymentMasterByFlatID(flatDetailsVO.getFlatId());
		}
	    
		resultAttributes.put("FinancialDetails", financialDetailsVO);
		resultAttributes.put("PaymentMasterDetails", paymentMasterVO);
		redirectUrl = "/payments.jsp";
	}

}
