/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services;

import java.io.File;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.SystemConf;
import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PdfBuilder;
import com.iq.amms.valueobjects.BillDetailsVO;
import com.iq.amms.valueobjects.BillMasterVO;
import com.iq.amms.valueobjects.BillPdflDataVO;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author sc64807
 *
 */
public class UpdateBill extends BaseService {

  private static final String BILL_ID_KEY = "billId";
  private static final String FLAT_ID_KEY = "flatId";
  private static final String CHARGE_NAME_ONE_KEY = "chargeNameOne";
  private static final String CHARGE_NAME_TWO_KEY = "chargeNameTwo";
  private static final String CHARGE_NAME_THREE_KEY = "chargeNameThree";
  private static final String CHARGE_NAME_FOUR_KEY = "chargeNameFour";
  private static final String AMOUNT_ONE_KEY = "amountOne";
  private static final String AMOUNT_TWO_KEY = "amountTwo";
  private static final String AMOUNT_THREE_KEY = "amountThree";
  private static final String AMOUNT_FOUR_KEY = "amountFour";
  private static final String ACTION_KEY = "action";

  /* (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    
    BillHelper billHelper = new BillHelper();
    
    String action = StringUtil.getStringValue(input.get(ACTION_KEY));

    String billId = StringUtil.getStringValue(input.get(BILL_ID_KEY));
    String flatId = StringUtil.getStringValue(input.get(FLAT_ID_KEY));
    
    if ("finalize".equalsIgnoreCase(action)||"update bill".equalsIgnoreCase(action)) {

      String chargeNameOne = StringUtil.getStringValue(input.get(CHARGE_NAME_ONE_KEY));
      String amountOne = StringUtil.getStringValue(input.get(AMOUNT_ONE_KEY));
      if (StringUtil.isEmpty(chargeNameOne) == false
          && StringUtil.isEmpty(amountOne) == false) {
        BillDetailsVO billDetailsVO = new BillDetailsVO();
        billDetailsVO.setBillId(billId);
        billDetailsVO.setChargeName(chargeNameOne);
        billDetailsVO.setChargeAmount(Double.valueOf(amountOne));
        billDetailsVO.setChargeType("user");

        billHelper.insertBillDetails(billDetailsVO);
      }

      String chargeNameTwo = StringUtil.getStringValue(input.get(CHARGE_NAME_TWO_KEY));
      String amountTwo = StringUtil.getStringValue(input.get(AMOUNT_TWO_KEY));
      if (StringUtil.isEmpty(chargeNameTwo) == false
          && StringUtil.isEmpty(amountTwo) == false) {
        BillDetailsVO billDetailsVO = new BillDetailsVO();
        billDetailsVO.setBillId(billId);
        billDetailsVO.setChargeName(chargeNameTwo);
        billDetailsVO.setChargeAmount(Double.valueOf(amountTwo));
        billDetailsVO.setChargeType("user");

        billHelper.insertBillDetails(billDetailsVO);
      }

      String chargeNameThree = StringUtil.getStringValue(input.get(CHARGE_NAME_THREE_KEY));
      String amountThree = StringUtil.getStringValue(input.get(AMOUNT_THREE_KEY));
      if (StringUtil.isEmpty(chargeNameThree) == false
          && StringUtil.isEmpty(amountThree) == false) {
        BillDetailsVO billDetailsVO = new BillDetailsVO();
        billDetailsVO.setBillId(billId);
        billDetailsVO.setChargeName(chargeNameThree);
        billDetailsVO.setChargeAmount(Double.valueOf(amountThree));
        billDetailsVO.setChargeType("user");

        billHelper.insertBillDetails(billDetailsVO);
      }

      String chargeNameFour = StringUtil.getStringValue(input.get(CHARGE_NAME_FOUR_KEY));
      String amountFour = StringUtil.getStringValue(input.get(AMOUNT_FOUR_KEY));
      if (StringUtil.isEmpty(chargeNameFour) == false
          && StringUtil.isEmpty(amountFour) == false) {
        BillDetailsVO billDetailsVO = new BillDetailsVO();
        billDetailsVO.setBillId(billId);
        billDetailsVO.setChargeName(chargeNameFour);
        billDetailsVO.setChargeAmount(Double.valueOf(amountFour));
        billDetailsVO.setChargeType("user");

        billHelper.insertBillDetails(billDetailsVO);
      }
      
      BillMasterVO billMasterVO = billHelper.getBillMaster(flatId);
      
      Double thisMonthAmount = 0.0;
      for (BillDetailsVO detailsVO : billMasterVO.getBillDetailsList()) {
        thisMonthAmount += detailsVO.getChargeAmount();
      }
      billMasterVO.setTotalAmount(thisMonthAmount);
      
      FinancialsHelper financialsHelper = new FinancialsHelper();
      FinancialDetailsVO financialDetailsVO =
          financialsHelper.getFinancialDetails(Integer.valueOf(flatId));
      
      // creating updated finc table data
      financialDetailsVO.setCurrentBalance(thisMonthAmount);
      financialDetailsVO.setPriorBalanceMonthOne(thisMonthAmount);

      // Updating DB
      billHelper.updateTotalAmount(billMasterVO.getBillId(),billMasterVO.getTotalAmount());
      financialsHelper.updateFinancialDetails(financialDetailsVO);

      
      if ("finalize".equalsIgnoreCase(action)) {
        billHelper.updateBillStatus(billId);
      }
      
      redirectUrl = "/bill-details.jsp?flatId="+flatId;
    }
    else if ("Export PDF".equalsIgnoreCase(action)) {

      FlatsHelper flatsHelper = new FlatsHelper();
      FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
      DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatId);
      BillMasterVO billMasterVO = billHelper.getBillMaster(flatId);

      BillPdflDataVO billPdflDataVO = new BillPdflDataVO();
      billPdflDataVO.setBillId(billMasterVO.getBillId());
      billPdflDataVO.setName(dwellersMasterVO.getName());
      billPdflDataVO.setFlatNumber(flatDetailsVO.getFullFlatNumber());
      billPdflDataVO.setDateOfGeneration(billMasterVO.getDateOfGeneration());
      billPdflDataVO.setDateOfDue(billMasterVO.getDateOfDue());
      billPdflDataVO.setTotalAmount(billMasterVO.getTotalAmount());
      billPdflDataVO.setBillDetailsList(billMasterVO.getBillDetailsList());
      
      String fileName = billPdflDataVO.getBillId().replaceAll("/", "-");
      FileUtil.createFolder(SystemConf.getAppRoot()+File.separator+"pdfs");
      PdfBuilder.createInvoice(SystemConf.getAppRoot()+File.separator+"pdfs/"+fileName+".pdf", billPdflDataVO);

      redirectUrl = "/pdfs/"+fileName+".pdf";
    }
  }
}