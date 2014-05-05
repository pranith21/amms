package com.iq.amms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.comm.CommunicationManager;
import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.comm.Communicator;
import org.iq.service.BaseService;
import org.iq.service.helpers.SystemConf;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;
import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.services.helpers.PaymentsHelper;
import com.iq.amms.services.helpers.SystemHelper;
import com.iq.amms.valueobjects.DwellersMasterVO;
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
  private static final String MAIL_SUBJECT = "MGRA - Payment Info";
  private String chequePaymentBounced="Your Cheque No.%s of Rs.%s has bounced. Penal charges Rs.%s. Current Balance Rs.%s";
  private String chequePaymentSuccess="Your Cheque No.%s of Rs.%s has been received. Current Balance Rs.%s";

  /*
   * (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
	  int flatId = Integer.valueOf(input.get(FLAT_ID_KEY).toString());
	  int paymentId = Integer.valueOf(input.get(PAYMENT_ID_KEY).toString());
	  int paymentStatus = Integer.valueOf(input.get(NEW_PAYMENT_STATUS_SELECT_KEY).toString());

	  Communicator communicator = CommunicationManager
				.getCommunicator(CommunicationType.TEXT_GURU_SMS);
	  Communicator gmailCommunicator = CommunicationManager
				.getCommunicator(CommunicationType.GMAIL_MAIL);

	  PaymentsHelper paymentsHelper = new PaymentsHelper();
	  int i = paymentsHelper.updatePaymentStatus(paymentId, paymentStatus);

	  FlatsHelper flatsHelper = new FlatsHelper();
	  if (i > 0) {
		  PaymentMasterVO paymentMasterVO = paymentsHelper.getPaymentMaster(paymentId);

		  PaymentDetailsVO paymentDetailsVO = paymentsHelper.getPaymentDetails(paymentId);

		  if (PaymentMode.CHEQUE.getPaymentModeValue().equals(paymentMasterVO.getPaymentType())) {
			  paymentDetailsVO.getChequeDetailsVO().setChequeClearanceDate(new Date());
		  }
		  paymentsHelper.updatePaymentDetails(paymentDetailsVO);
		  DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatId);
		  Double currBal = 0.0;
		  if (paymentStatus == 5) {
			  currBal = paymentsHelper.updateFinancialBills(paymentMasterVO.getFlatId(), paymentMasterVO.getPaidAmount());
			  ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
			  Double billAmount = Double.valueOf(String.format("%1.2f",paymentMasterVO.getPaidAmount()));
			  chequePaymentSuccess = String.format(chequePaymentSuccess, paymentDetailsVO.getChequeDetailsVO().getChequeNumber(), billAmount ,currBal);
			  if ("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_SMS))) {
		    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneMobile(), chequePaymentSuccess);
		    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneResidence(), chequePaymentSuccess);
			  }
//			  if ("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_MAIL))) {
//		    	  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(), null, null, MAIL_SUBJECT, chequePaymentSuccess, null);
//			  }
		  }
		  else {
			  FinancialsHelper financialsHelper = new FinancialsHelper();
			  financialsHelper.incrementBounceFlag(flatId);
			  FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatId);
			  currBal = financialDetailsVO.getCurrentBalance();
			  ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
			  SystemHelper systemHelper = new SystemHelper();
			  String penalCharges = systemHelper.getBounceCharge().toString();
			  Double billAmount = Double.valueOf(String.format("%1.2f",paymentMasterVO.getPaidAmount()));
			  chequePaymentBounced = String.format(chequePaymentBounced, paymentDetailsVO.getChequeDetailsVO().getChequeNumber(), billAmount,penalCharges,currBal);
			  if ("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_SMS))) {
		    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneMobile(), chequePaymentBounced);
		    	  communicator.communicate(SystemConf.getTextGuruSource(), dwellersMasterVO.getPhoneResidence(), chequePaymentBounced);
			  }
//			  if ("1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_MAIL))) {
//		    	  gmailCommunicator.communicate(null, dwellersMasterVO.getEmailPrimary(), null, null, MAIL_SUBJECT, chequePaymentSuccess, null);
//			  }
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

	  FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
	  resultAttributes.put(FULL_FLATNUMBER_KEY, flatDetailsVO.getFullFlatNumber());
	  redirectUrl = "/payments.jsp";
  }
}