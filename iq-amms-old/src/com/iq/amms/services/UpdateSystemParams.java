/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.services.helpers.SystemHelper;

/**
 * @author SC64807
 *
 */
public class UpdateSystemParams extends BaseService {

  /* (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    SystemHelper systemHelper = new SystemHelper();
    Double monthlyRate = systemHelper.getMonthlyMaintenanceRate();
  }
}