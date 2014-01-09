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
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author SC64807
 * 
 */
public class LookupApartment extends BaseService {

  private static final String BLOCK_KEY = "block";
  private static final String FLAT_NUMBER_KEY = "flatNumber";

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    String block = StringUtil.getStringValue(input.get(BLOCK_KEY));
    String flatNumber =
        StringUtil.getStringValue(input.get(FLAT_NUMBER_KEY));

    FlatsHelper flatsHelper = new FlatsHelper();
    
    FlatDetailsVO flatDetailsVO =
        flatsHelper.getFlatDetails(block, flatNumber);
    
    DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(block, flatNumber);
    
    FinancialsHelper financialsHelper = new FinancialsHelper();
    FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());

    resultAttributes.put("flatDetailsVO", flatDetailsVO);
    resultAttributes.put("dwellersMasterVO", dwellersMasterVO);
    resultAttributes.put("financialDetailsVO", financialDetailsVO);
    redirectUrl = "/apts-detail.jsp";
  }
}
