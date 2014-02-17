/**
 * 
 */
package com.iq.amms.services;

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
public class UpdateFlatDetails extends BaseService {

	private static final String FLAT_ID_KEY = "flatIdKey";
	private static final String EDIT_FLAT_DETAILS = "editFlatDetails";
	private static final String FLAT_NUMBER = "flatNumber";
	private static final String FLAT_NUMBER_PREFIX1 = "flatNumberPrefix1";
	private static final String FLOOR_NUMBER = "floorNumber";
	private static final String FLAT_STATUS = "flatStatus";
	private static final String FLAT_NUMBER_PREFIX2 = "flatNumberPrefix2";
	private static final String AREA_IN_SQFT = "areaInSqft";
	private static final String FLAT_NUMBER_SUFFIX1 = "flatNumberSuffix1";
	private static final String CREATE_DATE = "createDate";
	private static final String FLAT_NUMBER_SUFFIX2 = "flatNumberSuffix2";

	@Override
	public void execute(HashMap<String, Object> input) throws Exception {
		
		FlatDetailsVO flatDetailsVO = new FlatDetailsVO();
		
		int flatId = Integer.valueOf(StringUtil.getStringValue(input.get(FLAT_ID_KEY)));
		String flatNumber = StringUtil.getStringValue(input.get(FLAT_NUMBER));
		String flatNumberPrefix1 = StringUtil.getStringValue(input.get(FLAT_NUMBER_PREFIX1));
		String floorNumber = StringUtil.getStringValue(input.get(FLOOR_NUMBER));
		String flatStatus = StringUtil.getStringValue(input.get(FLAT_STATUS));
		String flatNumberPrefix2 = StringUtil.getStringValue(input.get(FLAT_NUMBER_PREFIX2));
		int areaInSqft = Integer.valueOf(StringUtil.getStringValue(input.get(AREA_IN_SQFT)));
		String flatNumberSuffix1 = StringUtil.getStringValue(input.get(FLAT_NUMBER_SUFFIX1));
		String createDate = StringUtil.getStringValue(input.get(CREATE_DATE));
		String flatNumberSuffix2 = StringUtil.getStringValue(input.get(FLAT_NUMBER_SUFFIX2));
		
		flatDetailsVO.setFlatId(flatId);
		flatDetailsVO.setFlatNumber(flatNumber);
		flatDetailsVO.setFlatNumberPrefix1(flatNumberPrefix1);
		flatDetailsVO.setFloorNumber(floorNumber);
		flatDetailsVO.setFlatStatus(flatStatus);
		flatDetailsVO.setFlatNumberPrefix2(flatNumberPrefix2);
		flatDetailsVO.setAreaInSqft(areaInSqft);
		flatDetailsVO.setFlatNumberSuffix1(flatNumberSuffix1);
		flatDetailsVO.setCreateDate(DateUtil.stringToDate(createDate,DateFormat.MMM_dd_yyyy));
		flatDetailsVO.setFlatNumberSuffix2(flatNumberSuffix2);
		
		FlatsHelper flatsHelper = new FlatsHelper();
		flatsHelper.updateFlatDetails(flatDetailsVO);

		DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatId);
		FinancialsHelper financialsHelper = new FinancialsHelper();
		FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO
				.getFlatId());

	    resultAttributes.put("flatDetailsVO", flatDetailsVO);
	    resultAttributes.put("dwellersMasterVO", dwellersMasterVO);
	    resultAttributes.put("financialDetailsVO", financialDetailsVO);
		resultAttributes.put(EDIT_FLAT_DETAILS, false);
		
		redirectUrl = "/apts-detail.jsp";

	}

}
