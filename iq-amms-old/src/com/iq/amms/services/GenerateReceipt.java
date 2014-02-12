/**
 * 
 */
package com.iq.amms.services;

import java.io.File;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.file.FileUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.SystemConf;
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
  private static String PAYMENT_ID_KEY = "paymentID";

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
    PaymentMasterVO paymentMasterVO =
        paymentsHelper.getPaymentMaster(paymentId);
    PaymentDetailsVO paymentDetailsVO =
        paymentsHelper.getPaymentDetails(paymentId);

    BillPdflDataVO billPdflDataVO = new BillPdflDataVO();
    billPdflDataVO.setName(dwellersMasterVO.getName());
    billPdflDataVO.setFlatNumber(flatDetailsVO.getFullFlatNumber());
    billPdflDataVO.setDateOfGeneration(paymentMasterVO.getPaymentDate());
    String receiptNumber =
        flatId
            + "-"
            + paymentId
            + "-"
            + DateUtil.dateToString(paymentMasterVO.getPaymentDate(),
                DateFormat.MM_dd_yyyy);
    billPdflDataVO.setReceiptNumber(receiptNumber);
    PaymentMode paymentMode =
        PaymentMode.getPaymentMode(paymentMasterVO.getPaymentType());
    billPdflDataVO.setPaymentMode(paymentMode);
    switch (paymentMode) {
      case CHEQUE:
        billPdflDataVO.setPaymentNumber(StringUtil
            .getStringValue(paymentDetailsVO.getChequeDetailsVO()
                .getChequeNumber()));
        break;
      case NEFT:
        billPdflDataVO.setPaymentNumber(StringUtil
            .getStringValue(paymentDetailsVO.getNeftDetailsVO()
                .getNEFTTransactionID()));
        break;
      case CASH:
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
