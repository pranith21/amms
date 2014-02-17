package com.iq.amms.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 *
 */
public class GetPaymentInfo extends BaseService {

      private static final String FLAT_ID_KEY = "flatId";
      private static final String CURRENT_BALANCE_KEY = "currentBalance";
	  private static final String PAYMENT_MASTER_VO_KEY = "paymentMasterVO";
	  private static final String FULL_FLATNUMBER_KEY = "fullFlatNumber";
	  
	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		int flatId = Integer.valueOf(StringUtil.getStringValue(input.get(FLAT_ID_KEY)));		
		Double currBal = Double.valueOf(StringUtil.getStringValue(input.get(CURRENT_BALANCE_KEY)));

		PaymentsHelper paymentsHelper = new PaymentsHelper();
		ArrayList<PaymentMasterVO> paymentMasterVOs = paymentsHelper.getPaymentMasterByFlatID(flatId);
	    	
		if (paymentMasterVOs!= null && !paymentMasterVOs.isEmpty() && paymentMasterVOs.size() > 0) {
		  resultAttributes.put(PAYMENT_MASTER_VO_KEY, paymentMasterVOs.get(0));
		}

		FlatsHelper flatsHelper = new FlatsHelper();
	    FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
	    resultAttributes.put(FULL_FLATNUMBER_KEY, flatDetailsVO.getFullFlatNumber());
		resultAttributes.put(FLAT_ID_KEY, flatId);
        resultAttributes.put(CURRENT_BALANCE_KEY, currBal);
		redirectUrl = "/payments.jsp";
	}
}