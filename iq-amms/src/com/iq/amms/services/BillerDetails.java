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
public class BillerDetails extends BaseService {
	
	private static final String OPERATION = "Operation";
	private static final String ADD_BILLER_NAME = "AddBillerName";
	private static final String EDIT_BILLER_ID = "EditBillerId";
	private static final String EDIT_BILLER_NAME = "EditBillerName";
	private static final String DELETE_BILLER_ID = "DeleteBillerId";
	

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		String operation = StringUtil.getStringValue(input.get(OPERATION));
		
		BillOutwardsHelper billOutwardsHelper = new BillOutwardsHelper();
		if ("Add".equals(operation)) {
			String addBillerName = StringUtil.getStringValue(input.get(ADD_BILLER_NAME));
			billOutwardsHelper.insertBillerData(addBillerName);
		}else if("Edit".equals(operation)){
			String billerName = StringUtil.getStringValue(input.get(EDIT_BILLER_NAME));
			int billId = Integer.valueOf(StringUtil.getStringValue(input.get(EDIT_BILLER_ID)));
			billOutwardsHelper.updateBillerData(billId, billerName);
		}else if("Delete".equals(operation)){
			int billId = Integer.valueOf(StringUtil.getStringValue(input.get(DELETE_BILLER_ID)));
			billOutwardsHelper.deleteBiller(billId);
		}

		resultAttributes.put("AddBillerFlag", false);
		resultAttributes.put("EditBillerFlag", false);
		resultAttributes.put("DeleteBillerFlag", false);
		resultAttributes.put("UploadBillFlag", false);
		redirectUrl = "/billoutwards.jsp";
	}
}
