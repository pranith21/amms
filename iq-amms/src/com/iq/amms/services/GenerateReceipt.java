/**
 * 
 */
package com.iq.amms.services;

import java.io.File;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.services.helpers.PdfBuilder;
import com.iq.amms.valueobjects.BillPdflDataVO;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.PaymentDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 */
public class GenerateReceipt extends BaseService {

  private static String FLAT_ID_KEY = "flatId";
  private static String PAYMENT_ID_KEY = "paymentId";

  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    int flatId = 0;
    int paymentId = 0;
    if (input.get(FLAT_ID_KEY) != null) {
      flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
    }
    if (input.get(PAYMENT_ID_KEY) != null) {
      paymentId = Integer.valueOf(input.get(PAYMENT_ID_KEY).toString());
    }

    FlatsHelper flatsHelper = new FlatsHelper();
    PaymentsHelper paymentsHelper = new PaymentsHelper();

    FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
    DwellersMasterVO dwellersMasterVO =
        flatsHelper.getDwellerDetails(flatId);
//    System.out.println("paymentId = "+paymentId);
    PaymentMasterVO paymentMasterVO =
        paymentsHelper.getPaymentMaster(paymentId);
//    System.out.println("##### = "+paymentMasterVO.toString());
    PaymentDetailsVO paymentDetailsVO =
        paymentsHelper.getPaymentDetails(paymentId);

    BillPdflDataVO billPdflDataVO = new BillPdflDataVO();
    billPdflDataVO.setName(dwellersMasterVO.getName());
    billPdflDataVO.setFlatNumber(flatDetailsVO.getFullFlatNumber());
    billPdflDataVO.setDateOfGeneration(paymentMasterVO.getPaymentDate());
    String receiptNumber = flatDetailsVO.getFlatNumberPrefix1()+"-"+flatDetailsVO.getFlatNumber()
            + "-"
            + paymentId
            + "-"
            + DateUtil.dateToString(paymentMasterVO.getPaymentDate(),
                DateFormat.MM_dd_yyyy);
    billPdflDataVO.setReceiptNumber(receiptNumber);
    PaymentMode paymentMode =
        PaymentMode.getPaymentMode(paymentMasterVO.getPaymentType());
    billPdflDataVO.setPaymentMode(paymentMode);
    
    String paymentStatus = "";
    int status = paymentMasterVO.getPaymentStatus();
    String paymentModeStr = paymentMasterVO.getPaymentType();
    
    if(status == 1) {
        paymentStatus = "Cheque Clearance Pending";
    }
    else if(status == 2) {
        paymentStatus = "Cheque Bounced";
    }
    else if (status == 3) {
        paymentStatus = "NEFT Verification Pending";
    }
    else if (status == 4) {
        paymentStatus = "NEFT Invalid";
    }
    else if (status == 5) {
        if ("0".equals(paymentModeStr)) {
            paymentStatus = "Paid";
        }else if ("1".equals(paymentModeStr)) {
            paymentStatus = "Cheque Cleared";
        }else if ("2".equals(paymentModeStr)) {
            paymentStatus = "NEFT Valid";
        }else if ("3".equals(paymentModeStr)) {
            paymentStatus = "Discounted";
        }else if ("4".equals(paymentModeStr)) {
            paymentStatus = "Error Rectified";
        }
    }
    billPdflDataVO.setPaymentStatus(paymentStatus);
    
    switch (paymentMode) {
      case CHEQUE:
        billPdflDataVO.setPaymentNumber(StringUtil
            .getStringValue(paymentDetailsVO.getChequeDetailsVO()
                .getChequeNumber()));
        break;
      case NEFT:
        billPdflDataVO.setPaymentNumber(StringUtil
            .getStringValue(paymentDetailsVO.getNeftDetailsVO()
                .getNeftTransactionID()));
        break;
      case CASH:
    	  billPdflDataVO.setPaymentNumber(paymentDetailsVO.getComments());
          break;
      case DISCOUNT:
      case ERROR_RECTIFICATION:
    	  billPdflDataVO.setPaymentNumber(paymentDetailsVO.getComments());
          break;
      default:
        break;
    }
    billPdflDataVO.setTotalAmount(paymentMasterVO.getPaidAmount());
    if (!FileUtil.isFileExists(SystemConf.getAppRoot() + File.separator
        + "pdfs/payments")) {
      FileUtil.createFolder(SystemConf.getAppRoot() + File.separator
          + "pdfs/payments");
    }
    PdfBuilder.createReceipt(SystemConf.getAppRoot() + File.separator
        + "pdfs/payments/" + receiptNumber + ".pdf", billPdflDataVO);

    redirectUrl = "/pdfs/payments/" + receiptNumber + ".pdf";
  }

}
