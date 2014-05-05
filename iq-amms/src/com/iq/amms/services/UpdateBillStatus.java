/**
 * 
 */
package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.BillOutwardsHelper;

/**
 * @author Sam
 *
 */
public class UpdateBillStatus extends BaseService {
	
	private static String OPERATION = "Submit";
	private static String BILL_ID = "processBillID";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		String operation = StringUtil.getStringValue(input.get(OPERATION));
		int billId = Integer.valueOf(StringUtil.getStringValue(input.get(BILL_ID)));
		
		BillOutwardsHelper billOutwardsHelper = new BillOutwardsHelper();
		if("Approve".equals(operation)){
			billOutwardsHelper.updateBillStatus(billId, 2);
		}else{
			billOutwardsHelper.updateBillStatus(billId, 3);
		}
		
		resultAttributes.put("AddBillerFlag", false);
		resultAttributes.put("EditBillerFlag", false);
		resultAttributes.put("DeleteBillerFlag", false);
		resultAttributes.put("UploadBillFlag", false);
		redirectUrl = "/billoutwards.jsp";
	}

}
