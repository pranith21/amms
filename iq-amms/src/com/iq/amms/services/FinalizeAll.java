/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.exception.DbException;
import org.iq.service.BaseService;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PdfBuilder;
import com.iq.amms.valueobjects.BillMasterVO;
import com.iq.amms.valueobjects.BillPdflDataVO;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author sc64807
 *
 */
public class FinalizeAll extends BaseService {

  private static final String ACTION_KEY = "action";

  /* (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    String action = StringUtil.getStringValue(input.get(ACTION_KEY));
    BillHelper billHelper = new BillHelper();
    if ("finalize all".equalsIgnoreCase(action)) {
      billHelper.finalizeAll();
      ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
		if ("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_SMS)) || 
				"1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_MAIL))) {
			System.out.println("Posting Messages in Finalize All.....");
			postBillGenerationMessages();
		}
      redirectUrl = "/apts-view-all.jsp?callFromFinc=true";
    }
    else if ("Export Consolidated PDF".equalsIgnoreCase(action)) {
      Date current = new Date();
      String fileName =
          "MGRA-" + (1900 + current.getYear()) + "-"
              + (current.getMonth() + 1) + "-consolidated-bill";

      File f =
          new File(SystemConf.getAppRoot() + File.separator + "pdfs/"
              + fileName + ".pdf");
      if (f.exists() == false) {
        FlatsHelper flatsHelper = new FlatsHelper();
        ArrayList<FlatDetailsVO> flatDetailsVOList =
            flatsHelper.getAllFlats();
        ArrayList<BillPdflDataVO> billPdflDataVOList =
            new ArrayList<BillPdflDataVO>();
        for (FlatDetailsVO flatDetailsVO : flatDetailsVOList) {
          DwellersMasterVO dwellersMasterVO =
              flatsHelper.getDwellerDetails(flatDetailsVO.getFlatId());
          BillMasterVO billMasterVO =
              billHelper.getBillMaster(flatDetailsVO.getFlatId());

          BillPdflDataVO billPdflDataVO = new BillPdflDataVO();
          billPdflDataVO.setBillId(billMasterVO.getBillId());
          billPdflDataVO.setName(dwellersMasterVO.getName());
          billPdflDataVO.setFlatNumber(flatDetailsVO.getFullFlatNumber());
          billPdflDataVO.setDateOfGeneration(billMasterVO
              .getDateOfGeneration());
          billPdflDataVO.setDateOfDue(billMasterVO.getDateOfDue());
          billPdflDataVO.setTotalAmount(billMasterVO.getTotalAmount());
          billPdflDataVO.setBillDetailsList(billMasterVO
              .getBillDetailsList());

          billPdflDataVOList.add(billPdflDataVO);
        }
        FileUtil.createFolder(SystemConf.getAppRoot() + File.separator
            + "pdfs");
        PdfBuilder.createConsolidatedInvoice(SystemConf.getAppRoot()
            + File.separator + "pdfs/" + fileName + ".pdf",
            billPdflDataVOList);
      }
      redirectUrl = "/pdfs/" + fileName + ".pdf";
    }
  }
  
  /**
   * @throws DbException
   */
  public void postBillGenerationMessages() throws DbException {
	  
	final String message = "Flat no. - %s. Your maintenance invoice for the month "
		+ "of %s has been generated. Your total outstanding is Rs. %s. "
		+ "Payment due date %s.";
	String subject = "MGRA - Bill Generation Info";
	
	FlatsHelper flatsHelper = new FlatsHelper();
	ArrayList<FlatDetailsVO> flatDetailsVOs = flatsHelper.getAllFlats();

	BillHelper billHelper = new BillHelper();
	for (FlatDetailsVO flatDetailsVO : flatDetailsVOs) {
		BillMasterVO billMasterVO = billHelper.getBillMaster(flatDetailsVO.getFlatId());
		
		String month = DateUtil.dateToString(billMasterVO.getDateOfGeneration(), DateFormat.MMM_yy);
		String totalAmt = String.format("%1.2f",billMasterVO.getTotalAmount());
		String latePaymentDueDate = DateUtil.dateToString(billMasterVO.getDateOfDue(), DateFormat.MMM_dd_yyyy);
		
		String localMessage = String.format(message, flatDetailsVO.getFullFlatNumber(), month,
			totalAmt, latePaymentDueDate);
		
		ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
		DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatDetailsVO.getFlatId());
		if("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_SMS))){
			Communicator communicator = CommunicationManager.getCommunicator(CommunicationType.TEXT_GURU_SMS);
			communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneMobile(), localMessage);
			communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneResidence(), localMessage);
		}
		/*if("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_MAIL))){
			Communicator gmailCommunicator = CommunicationManager.getCommunicator(CommunicationType.GMAIL_MAIL);
			gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(), null, null, subject, message, null);
		}*/
	}
  }
}