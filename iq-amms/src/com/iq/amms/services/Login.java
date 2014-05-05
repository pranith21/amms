/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.services;

import java.util.HashMap;
import java.util.List;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.BillOutwardsHelper;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.GetApartmentDetails;
import com.iq.amms.valueobjects.ApartmentMasterVO;
import com.iq.amms.valueobjects.BillerDetailsVO;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.SessionVO;

/**
 * @author SC64807
 * 
 */
public class Login extends BaseService {

	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private static final String OPERATION_NAME = "operationName";
	private static final String OPERATION_TYPE = "operationType";  
	private static final String DWELLER__MASTER_DETAILS = "dwellersMasterVO";
	private static final String FLAT_DETAILS = "flatDetailsVO";
	private static final String FLAT_ID = "flatIDValue";
	private static final String FINANCIAL_DETAILS = "financialDetailsVO";
	private static final String EDIT_DWELLER_DETAILS = "editDwellerFlag";
	private static final String EDIT_FLAT_DETAILS = "editFlatDetails";
	private static final String EDIT_SETTINGS = "editSettingsFlag";
	private static final String BILL_STATUS = "billStatus";
	private static final String ADMIN_USERNAME = "su";
	private static final String ADMIN_PASSWORD = "su";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		String username = StringUtil.getStringValue(input.get(USERNAME_KEY));
		String password = StringUtil.getStringValue(input.get(PASSWORD_KEY));
		String operationName = StringUtil.getStringValue(input.get(OPERATION_NAME));
		String operationType = StringUtil.getStringValue(input.get(OPERATION_TYPE));
		if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
			BillOutwardsHelper billOutwardsHelper = new BillOutwardsHelper();
			if("edit-apts-detail".equals(operationName)){
				if ("Edit Settings".equals(operationType)) {
					resultAttributes.put(EDIT_SETTINGS, true);
					redirectUrl = "/settings.jsp";
				}else if ("Add Biller".equals(operationType)) {
					resultAttributes.put("AddBillerFlag", true);
					resultAttributes.put("EditBillerFlag", false);
					resultAttributes.put("DeleteBillerFlag", false);
					resultAttributes.put("billerDetails", null);
					resultAttributes.put("UploadBillFlag", false);
					resultAttributes.put("approveBillFlag", false);
					redirectUrl = "/billoutwards.jsp";
				}else if ("Edit Biller".equals(operationType)) {
					resultAttributes.put("AddBillerFlag", false);
					resultAttributes.put("DeleteBillerFlag", false);
					resultAttributes.put("UploadBillFlag", false);
					resultAttributes.put("approveBillFlag", false);
					resultAttributes.put("EditBillerFlag", true);
					List<BillerDetailsVO> billerDetailsVOs = billOutwardsHelper.getBillerDetails();
					resultAttributes.put("billerDetails", billerDetailsVOs);
					redirectUrl = "/billoutwards.jsp";
				}else if ("Delete Biller".equals(operationType)) {
					resultAttributes.put("AddBillerFlag", false);
					resultAttributes.put("EditBillerFlag", false);
					resultAttributes.put("UploadBillFlag", false);
					resultAttributes.put("approveBillFlag", false);
					resultAttributes.put("DeleteBillerFlag", true);
					List<BillerDetailsVO> billerDetailsVOs = billOutwardsHelper.getBillerDetails();
					resultAttributes.put("billerDetails", billerDetailsVOs);
					redirectUrl = "/billoutwards.jsp";
				}else if("Add Bill to Upload".equals(operationType)){
					resultAttributes.put("AddBillerFlag", false);
					resultAttributes.put("EditBillerFlag", false);
					resultAttributes.put("DeleteBillerFlag", false);
					resultAttributes.put("approveBillFlag", false);
					resultAttributes.put("UploadBillFlag", true);
					List<BillerDetailsVO> billerDetailsVOs = billOutwardsHelper.getBillerDetails();
					resultAttributes.put("billerDetails", billerDetailsVOs);
					redirectUrl = "/billoutwards.jsp";
				}else if ("Approve Bill".equals(operationType)) {
					resultAttributes.put("AddBillerFlag", false);
					resultAttributes.put("EditBillerFlag", false);
					resultAttributes.put("DeleteBillerFlag", false);
					resultAttributes.put("billerDetails", null);
					resultAttributes.put("UploadBillFlag", false);
					resultAttributes.put("approveBillFlag", true);
					resultAttributes.put("billProcessType", "Approve");
					redirectUrl = "/billoutwards.jsp";
				}else if ("Reject Bill".equals(operationType)) {
					resultAttributes.put("AddBillerFlag", false);
					resultAttributes.put("EditBillerFlag", false);
					resultAttributes.put("DeleteBillerFlag", false);
					resultAttributes.put("billerDetails", null);
					resultAttributes.put("UploadBillFlag", false);
					resultAttributes.put("approveBillFlag", true);
					resultAttributes.put("billProcessType", "Reject");
					redirectUrl = "/billoutwards.jsp";
				}else{
					int flatID = 0;
					if(input.get(FLAT_ID) != null){
						flatID = Integer.valueOf(input.get(FLAT_ID).toString());
					}
					FlatsHelper flatsHelper = new FlatsHelper();

					FlatDetailsVO flatDetailsVO =
							flatsHelper.getFlatDetails(flatID);

					DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();

					FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
					if (flatDetailsVO != null) {
						dwellersMasterVO = flatsHelper.getDwellerDetails(flatID);
						FinancialsHelper financialsHelper = new FinancialsHelper();
						financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO
								.getFlatId());
					}

					BillHelper billHelper = new BillHelper();
					int billStatus = billHelper.getBillStatusByFlatId(flatDetailsVO.getFlatId());

					resultAttributes.put(BILL_STATUS, billStatus);
					resultAttributes.put(FLAT_DETAILS, flatDetailsVO);
					resultAttributes.put(DWELLER__MASTER_DETAILS, dwellersMasterVO);
					resultAttributes.put(FINANCIAL_DETAILS, financialDetailsVO);
					if ("Edit Dweller Details".equals(operationType)) {
						resultAttributes.put(EDIT_DWELLER_DETAILS, true);
					}else if ("Edit Flat Details".equals(operationType)){
						resultAttributes.put(EDIT_FLAT_DETAILS, true);
					}
					redirectUrl = "/apts-detail.jsp";			  
				}
			}else{
				SessionVO sessionVO = new SessionVO();

				GetApartmentDetails getApartmentDetails = new GetApartmentDetails();
				ApartmentMasterVO apartmentMasterVO = getApartmentDetails.execute();
				if (apartmentMasterVO != null) {
					System.out.println(apartmentMasterVO);
					sessionVO.setApartmentName(apartmentMasterVO.getApartmentName());
					sessionVO.setNameOfUser("Admin");
					System.out.println(sessionVO);
					resultAttributes.put("sessionVO", sessionVO);
					//    	        redirectUrl = "/apartments/index.jsp";
					//    	      redirectUrl = "/financials/index.jsp";
					redirectUrl = "/landing.jsp";

				} else{
					redirectUrl = "/community/index.jsp";
				}
			}
		}
		else {
			resultAttributes.put("loginFailed", true);
			redirectUrl = "/";
		}
	}
}