/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public class DataTestService extends BaseService {

  private static final String NAME_KEY = "name";
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
    
    System.out.println("DataTestService called");
    String name = StringUtil.getStringValue(input.get(NAME_KEY));
    /*String password = StringUtil.getStringValue(input.get(PASSWORD_KEY));
    if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
      redirectUrl = "/user/index.jsp";
    }
    else {
      resultAttributes.put("loginFailed", true);
      redirectUrl = "/";
    }*/
  }
}