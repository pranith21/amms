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
public class UploadBillDetails extends BaseService {
	
	private static final String BILL_ID = "BillId";
	private static final String COMMENTS = "comments";
	private static final String BILL_AMOUNT = "billAmount";
	private static final String BILLER_ID = "billerName";
	

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		int billNumber = Integer.valueOf(StringUtil.getStringValue(input.get(BILL_ID)));
		int billerId = Integer.valueOf(StringUtil.getStringValue(input.get(BILLER_ID)));
		String comments = StringUtil.getStringValue(input.get(COMMENTS));
		String amount = StringUtil.getStringValue(input.get(BILL_AMOUNT));
		
		BillOutwardsHelper billOutwardsHelper = new BillOutwardsHelper();
		
		billOutwardsHelper.insertBillOutwardMasrter(amount);
		
		int billId = billOutwardsHelper.getLastBillId();
		
		billOutwardsHelper.insertBillOutwardsDetail(billerId, billId, comments, billNumber);
		
		resultAttributes.put("AddBillerFlag", false);
		resultAttributes.put("EditBillerFlag", false);
		resultAttributes.put("DeleteBillerFlag", false);
		resultAttributes.put("UploadBillFlag", false);
		redirectUrl = "/billoutwards.jsp";
	}

}
