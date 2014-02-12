/**
 * 
 */
package com.iq.amms.services;

import java.util.Date;
import java.util.HashMap;

import org.iq.service.BaseService;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.string.StringUtil;

import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author Sam
 *
 */
public class UpdateDwellerDetails extends BaseService {

	private static final String NAME_SALUTATION = "nameSalutation";
	private static final String FIRST_NAME = "firstName";
	private static final String MIDDLE_NAME = "middleName";
	private static final String LAST_NAME = "lastName";
	private static final String PREFERRED_NAME = "preferredName";
	private static final String GENDER = "gender";
	private static final String DATE_OF_BIRTH = "dateOfBirth";
	private static final String DATE_OF_ANNIVERSARY = "dateOfAnniversary";
	private static final String BUSINESS_PHONE = "businessPhone";
	private static final String BUSINESS_PHONE_EXTN = "businessPhoneExtn";
	private static final String MOBILE_PHONE = "mobile";
	private static final String RESIDENCE_PHONE = "resiPhone";
	private static final String PRIMARY_EMAIL = "primaryEmail";
	private static final String SECONDARY_EMAIL = "secondaryEmail";
	private static final String PREFERRED_CONTACT = "preferredContact";
	private static final String DWELLER_ID_KEY = "dwellerIDKey";
	private static final String FLAT_ID_KEY = "flatIdKey";
	private static final String EDIT_DWELLER_DETAILS = "editDwellerFlag";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		String nameSalutation = StringUtil.getStringValue(input.get(NAME_SALUTATION));
		String firstName = StringUtil.getStringValue(input.get(FIRST_NAME));
		String middleName = StringUtil.getStringValue(input.get(MIDDLE_NAME));
		String lastName = StringUtil.getStringValue(input.get(LAST_NAME));
		String preferredName = StringUtil.getStringValue(input.get(PREFERRED_NAME));
		String gender = StringUtil.getStringValue(input.get(GENDER));
		Date dateOfBirth = DateUtil.stringToDate(input.get(DATE_OF_BIRTH).toString(), DateFormat.MMM_dd_yyyy);
		Date dateOfAnniversary = DateUtil.stringToDate(input.get(DATE_OF_ANNIVERSARY).toString(), DateFormat.MMM_dd_yyyy);
		String businessPhone = StringUtil.getStringValue(input.get(BUSINESS_PHONE));
		String businessPhoneExtn = StringUtil.getStringValue(input.get(BUSINESS_PHONE_EXTN));
		String mobile = StringUtil.getStringValue(input.get(MOBILE_PHONE));
		String resiPhone = StringUtil.getStringValue(input.get(RESIDENCE_PHONE));
		String primaryEmail = StringUtil.getStringValue(input.get(PRIMARY_EMAIL));
		String secondaryEmail = StringUtil.getStringValue(input.get(SECONDARY_EMAIL));
		String preferredContact = StringUtil.getStringValue(input.get(PREFERRED_CONTACT));
		int dwellersId = Integer.valueOf(StringUtil.getStringValue(input.get(DWELLER_ID_KEY)));
		int flatId = Integer.valueOf(StringUtil.getStringValue(input.get(FLAT_ID_KEY)));

		DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();
		dwellersMasterVO.setNameFirst(firstName);
		dwellersMasterVO.setNameMiddle(middleName);
		dwellersMasterVO.setNameSalutation(nameSalutation);
		dwellersMasterVO.setNameLast(lastName);
		dwellersMasterVO.setNamePreferred(preferredName);
		dwellersMasterVO.setGender(gender);
		dwellersMasterVO.setDateOfAnniversary(dateOfAnniversary);
		dwellersMasterVO.setDateOfBirth(dateOfBirth);
		dwellersMasterVO.setPhoneBusiness(businessPhone);
		dwellersMasterVO.setPhoneBusinessExtn(businessPhoneExtn);
		dwellersMasterVO.setPhoneMobile(mobile);
		dwellersMasterVO.setPhoneResidence(resiPhone);
		dwellersMasterVO.setEmailPrimary(primaryEmail);
		dwellersMasterVO.setEmailSecondary(secondaryEmail);
		dwellersMasterVO.setPreferredContact(preferredContact);
		dwellersMasterVO.setDwellersId(dwellersId);
		
		FlatsHelper flatsHelper = new FlatsHelper();
		flatsHelper.updateDwellersMaster(dwellersMasterVO);
		FlatDetailsVO flatDetailsVO =
		        flatsHelper.getFlatDetails(flatId);
		FinancialsHelper financialsHelper = new FinancialsHelper();
		FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO
				.getFlatId());

	    resultAttributes.put("flatDetailsVO", flatDetailsVO);
	    resultAttributes.put("dwellersMasterVO", dwellersMasterVO);
	    resultAttributes.put("financialDetailsVO", financialDetailsVO);
		resultAttributes.put(EDIT_DWELLER_DETAILS, false);
		
		redirectUrl = "/apts-detail.jsp";
	}
}
