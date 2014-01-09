/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.SystemConf;
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
              flatsHelper.getDwellerDetails(StringUtil
                  .getStringValue(flatDetailsVO.getFlatId()));
          BillMasterVO billMasterVO =
              billHelper.getBillMaster(StringUtil
                  .getStringValue(flatDetailsVO.getFlatId()));

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
}