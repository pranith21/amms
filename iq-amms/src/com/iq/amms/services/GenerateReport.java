package com.iq.amms.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.SystemConf;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PdfBuilder;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.ReportDataVO;

/**
 * @author Sam
 */
public class GenerateReport extends BaseService {

  private static String SUBMIT_KEY = "submit";
  private static String ACCOUNTS_WITH_DUES =
      "Accounts with Dues (Debit balance)";
  private static String ACCOUNTS_WITH_DEPOSITS =
    "Accounts with Deposits (Credit balance)";
  private static String ACCOUNTS_WITH_NO_DUES =
    "Accounts with no Dues (Zero balance)";

  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    String reportType = null;
    if (input.get(SUBMIT_KEY) != null) {
      reportType = StringUtil.getStringValue(input.get(SUBMIT_KEY));
    }

    ReportDataVO reportDataVO = new ReportDataVO();

    if (ACCOUNTS_WITH_DUES.equals(reportType)) {
      reportDataVO.setTableHeader(ACCOUNTS_WITH_DUES);
      reportDataVO.setHeaders(new String[] { "Flat Number", "Owner",
          "Current Due" });
      FlatsHelper flatsHelper = new FlatsHelper();
      ArrayList<FlatDetailsVO> flatDetailsVOs = flatsHelper.getAllFlats();
      ArrayList<String[]> data = new ArrayList<String[]>();
      Double total = 0.0;
      for (FlatDetailsVO flatDetailsVO : flatDetailsVOs) {
        DwellersMasterVO dwellersMasterVO =
            flatsHelper.getDwellerDetails(flatDetailsVO.getFlatId());
        FinancialsHelper financialsHelper = new FinancialsHelper();
        FinancialDetailsVO financialDetailsVO =
            financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
        Double currBal = financialDetailsVO.getCurrentBalance();
        if (currBal > 0) {
          total+=currBal;
          String[] oneRow =
              new String[] { flatDetailsVO.getFullFlatNumber(),
                  dwellersMasterVO.getName(),
                  StringUtil.getStringValue(currBal) };
          data.add(oneRow);
        }
      }
      data.add(new String[]{ "","","<b>"+ StringUtil.getStringValue(total)+" (Total)</b>" });
      reportDataVO.setData(data);
    }
    else if (ACCOUNTS_WITH_DEPOSITS.equals(reportType)) {
      reportDataVO.setTableHeader(ACCOUNTS_WITH_DEPOSITS);
      reportDataVO.setHeaders(new String[] { "Flat Number", "Owner",
          "Current Due" });
      FlatsHelper flatsHelper = new FlatsHelper();
      ArrayList<FlatDetailsVO> flatDetailsVOs = flatsHelper.getAllFlats();
      ArrayList<String[]> data = new ArrayList<String[]>();
      Double total = 0.0;
      for (FlatDetailsVO flatDetailsVO : flatDetailsVOs) {
        DwellersMasterVO dwellersMasterVO =
            flatsHelper.getDwellerDetails(flatDetailsVO.getFlatId());
        FinancialsHelper financialsHelper = new FinancialsHelper();
        FinancialDetailsVO financialDetailsVO =
            financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
        Double currBal = financialDetailsVO.getCurrentBalance();
        if (currBal < 0) {
          total+=currBal;
          String[] oneRow =
              new String[] { flatDetailsVO.getFullFlatNumber(),
                  dwellersMasterVO.getName(),
                  StringUtil.getStringValue(currBal) };
          data.add(oneRow);
        }
      }
      data.add(new String[]{ "","","<b>"+ StringUtil.getStringValue(total)+" (Total)</b>" });
      reportDataVO.setData(data);
    }
    else if (ACCOUNTS_WITH_NO_DUES.equals(reportType)) {
      reportDataVO.setTableHeader(ACCOUNTS_WITH_NO_DUES);
      reportDataVO.setHeaders(new String[] { "Flat Number", "Owner",
          "Current Due" });
      FlatsHelper flatsHelper = new FlatsHelper();
      ArrayList<FlatDetailsVO> flatDetailsVOs = flatsHelper.getAllFlats();
      ArrayList<String[]> data = new ArrayList<String[]>();
      for (FlatDetailsVO flatDetailsVO : flatDetailsVOs) {
        DwellersMasterVO dwellersMasterVO =
            flatsHelper.getDwellerDetails(flatDetailsVO.getFlatId());
        FinancialsHelper financialsHelper = new FinancialsHelper();
        FinancialDetailsVO financialDetailsVO =
            financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
        Double currBal = financialDetailsVO.getCurrentBalance();
        if (currBal == 0) {
          String[] oneRow =
              new String[] { flatDetailsVO.getFullFlatNumber(),
                  dwellersMasterVO.getName(),
                  StringUtil.getStringValue(currBal) };
          data.add(oneRow);
        }
      }
      reportDataVO.setData(data);
    }
    else {

    }

    if (!FileUtil.isFileExists(SystemConf.getAppRoot() + File.separator
            + "pdfs/reports")) {
          FileUtil.createFolder(SystemConf.getAppRoot() + File.separator
              + "pdfs/reports");
    }

    PdfBuilder.createReport(SystemConf.getAppRoot() + File.separator
            + "pdfs/reports/" + reportDataVO.getTableHeader() + ".pdf", reportDataVO);

    resultAttributes.put("reportData", reportDataVO);
    resultAttributes.put("reportDataFileName", "/pdfs/reports/" + reportDataVO.getTableHeader() + ".pdf");
    redirectUrl = "/report-viewer.jsp";
  }
}