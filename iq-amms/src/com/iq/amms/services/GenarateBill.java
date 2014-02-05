/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services;

import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.services.helpers.BillHelper;

/**
 * @author SC64807
 *
 */
public class GenarateBill extends BaseService {

  /* (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    BillHelper billHelper = new BillHelper();
    billHelper.generateInvoices(new Date("01/01/2014"));
    redirectUrl = "/apts-view-all.jsp";
  }
}