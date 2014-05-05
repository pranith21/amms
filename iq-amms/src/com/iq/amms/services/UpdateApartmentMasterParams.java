package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.ApartmentMasterParamsHelper;

/**
 * @author Sam
 *
 */
public class UpdateApartmentMasterParams extends BaseService {
	
	private static String RATE_PER_SFT = "ratePerSqft";
	private static String PAYMENT_DUE_DATE = "latePaymentCalculationDate";
	private static String LATE_PAYMENT_DISPLAY_DATE = "latePaymentDisplayDate";
	private static String LATE_PAYMENT_AMOUNT = "latePaymentAmount";
	private static String LATE_PAYMENT_MIN_BALANCE="latePaymentMinimumBalance";
	private static String BOUNCE_CHARGES = "bounceCharges";
	private static String BILL_GENERATION_SMS = "billGenerationsms";
	private static String LATE_PAYMENT_SMS = "latePaymentsms";
	private static String PAYMENT_SMS = "paymentssms";
	private static String BIRTHDAY_SMS = "birthdaysms";
	private static String ANNIVERSARY_SMS = "anniversarysms";
	private static String BILL_GENERATION_MAIL = "billGenerationmail";
	private static String LATE_PAYMENT_MAIL = "latePaymentmail";
	private static String PAYMENT_MAIL = "paymentsmail";
	private static String BIRTHDAY_MAIL = "birthdaymail";
	private static String ANNIVERSARY_MAIL = "anniversarymail";
	private static String LATE_PAYMENT_REMINDER_DAYS="latePaymentReminderDays";
	private static String LATE_PAYMENT_REMINDER_SMS="latePaymentReminderSMS";
	private static String LATE_PAYMENT_REMINDER_MAIL="latePaymentReminderMail";
	private static String CHECKED = "1";
	private static String UNCHECKED = "0";

  /* (non-Javadoc)
   * @see org.iq.service.BaseService#execute(java.util.HashMap)
   */
  @Override
  public void execute(HashMap<String, Object> input) throws Exception {
	  
	  String paymentDueDate = StringUtil.getStringValue(input.get(PAYMENT_DUE_DATE));
	  String latePaymentDisplayDate = StringUtil.getStringValue(input.get(LATE_PAYMENT_DISPLAY_DATE));
	  String latePaymentMinimumBalance = StringUtil.getStringValue(input.get(LATE_PAYMENT_MIN_BALANCE));
	  String latePaymentReminderDays = StringUtil.getStringValue(input.get(LATE_PAYMENT_REMINDER_DAYS));
	  String ratePerSqft = StringUtil.getStringValue(input.get(RATE_PER_SFT));
	  String latePaymentAmount = StringUtil.getStringValue(input.get(LATE_PAYMENT_AMOUNT));
	  String bounceCharges = StringUtil.getStringValue(input.get(BOUNCE_CHARGES));
	  String billGenerationsms = CHECKED.equals(StringUtil.getStringValue(input.get(BILL_GENERATION_SMS)))?CHECKED:UNCHECKED;
	  String latePaymentsms = CHECKED.equals(StringUtil.getStringValue(input.get(LATE_PAYMENT_SMS)))?CHECKED:UNCHECKED;
	  String paymentssms = CHECKED.equals(StringUtil.getStringValue(input.get(PAYMENT_SMS)))?CHECKED:UNCHECKED;
	  String birthdaysms = CHECKED.equals(StringUtil.getStringValue(input.get(BIRTHDAY_SMS)))?CHECKED:UNCHECKED;
	  String anniversarysms = CHECKED.equals(StringUtil.getStringValue(input.get(ANNIVERSARY_SMS)))?CHECKED:UNCHECKED;
	  String billGenerationmail = CHECKED.equals(StringUtil.getStringValue(input.get(BILL_GENERATION_MAIL)))?CHECKED:UNCHECKED;
	  String latePaymentmail = CHECKED.equals(StringUtil.getStringValue(input.get(LATE_PAYMENT_MAIL)))?CHECKED:UNCHECKED;
	  String paymentsmail = CHECKED.equals(StringUtil.getStringValue(input.get(PAYMENT_MAIL)))?CHECKED:UNCHECKED;
	  String birthdaymail = CHECKED.equals(StringUtil.getStringValue(input.get(BIRTHDAY_MAIL)))?CHECKED:UNCHECKED;
	  String anniversarymail = CHECKED.equals(StringUtil.getStringValue(input.get(ANNIVERSARY_MAIL)))?CHECKED:UNCHECKED;
	  String latePaymentReminderSMS = CHECKED.equals(StringUtil.getStringValue(input.get(LATE_PAYMENT_REMINDER_SMS)))?CHECKED:UNCHECKED;
	  String latePaymentReminderMail = CHECKED.equals(StringUtil.getStringValue(input.get(LATE_PAYMENT_REMINDER_MAIL)))?CHECKED:UNCHECKED;
	  
	  ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
	  
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_CALCULATION_DATE, paymentDueDate);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_DISPLAY_DATE, latePaymentDisplayDate);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.BILL_GENERATION_SMS, billGenerationsms);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_SMS, latePaymentsms);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.PAYMENTS_SMS, paymentssms);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.BIRTHDAY_SMS, birthdaysms);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.ANNIVERSARY_SMS, anniversarysms);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.BILL_GENERATION_MAIL, billGenerationmail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_MAIL, latePaymentmail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.PAYMENTS_MAIL, paymentsmail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.BIRTHDAY_MAIL, birthdaymail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.ANNIVERSARY_MAIL, anniversarymail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_MINIMUM_BALANCE, latePaymentMinimumBalance);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_DAYS, latePaymentReminderDays);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SMS, latePaymentReminderSMS);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_MAIL, latePaymentReminderMail);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.MONTHLY_MAINTENANCE_RATE, ratePerSqft);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.LATE_PAYMENT_AMOUNT, latePaymentAmount);
	  apartmentMasterHelper.updateParam(ApartmentMasterParamsHelper.CHEQUE_BOUNCE_CHARGES, bounceCharges);
	  redirectUrl = "/settings.jsp";
  }
}