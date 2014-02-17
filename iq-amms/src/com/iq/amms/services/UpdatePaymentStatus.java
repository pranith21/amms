package com.iq.amms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;
import com.iq.amms.valueobjects.PaymentDetailsVO;
import com.iq.amms.valueobjects.PaymentMasterVO;

/**
 * @author Sam
 */
public class UpdatePaymentStatus extends BaseService {

  private static final String FLAT_ID_KEY = "flatId";
  private static final String PAYMENT_ID_KEY = "paymentId";
  private static final String NEW_PAYMENT_STATUS_SELECT_KEY =
      "newPaymentStatusSelect";
  private static final String PAYMENT_MASTER_VO_KEY = "paymentMasterVO";
  private static final String CURRENT_BALANCE_KEY = "currentBalance";
  private static final String FULL_FLATNUMBER_KEY = "fullFlatNumber";

  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
    int flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
    int paymentId = Integer.valueOf(input.get(PAYMENT_ID_KEY).toString());
    int paymentStatus = Integer.valueOf(input.get(NEW_PAYMENT_STATUS_SELECT_KEY).toString());

    PaymentsHelper paymentsHelper = new PaymentsHelper();
    int i = paymentsHelper.updatePaymentStatus(paymentId, paymentStatus);

    if (i > 0) {
      PaymentMasterVO paymentMasterVO = paymentsHelper.getPaymentMaster(paymentId);

      PaymentDetailsVO paymentDetailsVO = paymentsHelper.getPaymentDetails(paymentId);

      if (PaymentMode.CHEQUE.getPaymentModeValue().equals(paymentMasterVO.getPaymentType())) {
        paymentDetailsVO.getChequeDetailsVO().setChequeClearanceDate(new Date());
      }
      else if (PaymentMode.NEFT.getPaymentModeValue().equals(paymentMasterVO.getPaymentType())) {
        paymentDetailsVO.getNeftDetailsVO().setNeftClearanceDate(new Date());
      }

      paymentsHelper.updatePaymentDetails(paymentDetailsVO);

      Double currBal = 0.0;
      if (paymentStatus == 5) {
        currBal = paymentsHelper.updateFinancialBills(paymentMasterVO.getFlatId(), paymentMasterVO.getPaidAmount());
      }
      else {
        FinancialsHelper financialsHelper = new FinancialsHelper();
        FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatId);
        currBal = financialDetailsVO.getCurrentBalance();
      }
      resultAttributes.put(CURRENT_BALANCE_KEY, currBal);

    }
    resultAttributes.put(FLAT_ID_KEY, flatId);

    ArrayList<PaymentMasterVO> paymentMasterVOs =
      paymentsHelper.getPaymentMasterByFlatID(flatId);

    if (paymentMasterVOs != null && !paymentMasterVOs.isEmpty()
        && paymentMasterVOs.size() > 0) {
      resultAttributes.put(PAYMENT_MASTER_VO_KEY,
          paymentMasterVOs.get(0));
    }
    
    FlatsHelper flatsHelper = new FlatsHelper();
    FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
    resultAttributes.put(FULL_FLATNUMBER_KEY, flatDetailsVO.getFullFlatNumber());
    redirectUrl = "/payments.jsp";
  }
}