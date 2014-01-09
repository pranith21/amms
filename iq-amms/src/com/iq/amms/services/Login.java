/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.GetApartmentDetails;
import com.iq.amms.valueobjects.ApartmentMasterVO;
import com.iq.amms.valueobjects.SessionVO;

/**
 * @author SC64807
 * 
 */
public class Login extends BaseService {

  private static final String USERNAME_KEY = "username";
  private static final String PASSWORD_KEY = "password";
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
    if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
      
      SessionVO sessionVO = new SessionVO();
      
      GetApartmentDetails getApartmentDetails = new GetApartmentDetails();
      ApartmentMasterVO apartmentMasterVO = getApartmentDetails.execute();
      if (apartmentMasterVO != null) {
        System.out.println(apartmentMasterVO);
        sessionVO.setApartmentName(apartmentMasterVO.getApartmentName());
        sessionVO.setNameOfUser("Admin");
        System.out.println(sessionVO);
        resultAttributes.put("sessionVO", sessionVO);
//        redirectUrl = "/apartments/index.jsp";
//      redirectUrl = "/financials/index.jsp";
      redirectUrl = "/landing.jsp";
      }
      else{
        redirectUrl = "/community/index.jsp";
      }
    }
    else {
      resultAttributes.put("loginFailed", true);
      redirectUrl = "/";
    }
  }
}