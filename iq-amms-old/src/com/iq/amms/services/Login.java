/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.GetApartmentDetails;
import com.iq.amms.valueobjects.ApartmentMasterVO;
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
  private static final String DWELLER__MASTER_DETAILS = "dwellersMasterVO";
  private static final String FLAT_DETAILS = "flatDetailsVO";
  private static final String FLAT_ID = "flatID";
  private static final String FINANCIAL_DETAILS = "financialDetailsVO";
  private static final String EDIT_DWELLER_DETAILS = "editDwellerFlag";
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
	  if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {

		  if("edit-apts-detail".equals(operationName)){
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
			  
				  resultAttributes.put(FLAT_DETAILS, flatDetailsVO);
				  resultAttributes.put(DWELLER__MASTER_DETAILS, dwellersMasterVO);
				  resultAttributes.put(FINANCIAL_DETAILS, financialDetailsVO);
				  resultAttributes.put(EDIT_DWELLER_DETAILS, true);
				  redirectUrl = "/apts-detail.jsp";			  
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