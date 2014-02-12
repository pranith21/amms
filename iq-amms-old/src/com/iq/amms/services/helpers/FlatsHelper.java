package com.iq.amms.services.helpers;

import java.util.ArrayList;

import org.iq.db.DataSet;
import org.iq.exception.DbException;

import com.iq.amms.Constants.DwellerStatus;
import com.iq.amms.Constants.Gender;
import com.iq.amms.valueobjects.DwellersMasterVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

/**
 * @author Sam
 * 
 */
public class FlatsHelper extends BaseHelper {

  private static final String ALL_FLATS_SELECT_QUERY =
    "SELECT FLAT_ID,FLAT_NUMBER_PREFIX_1,FLAT_NUMBER_PREFIX_2,FLAT_NUMBER," +
    "FLAT_NUMBER_SUFFIX_1,FLAT_NUMBER_SUFFIX_2,FLOOR_NUMBER,AREA_SQFT," +
    "CREATE_DATE,FLAT_STATUS FROM FLAT_MASTER";
  
  private static final String FLAT_DETAILS_BY_FLAT_NUMBER_SELECT_QUERY =
    "SELECT FLAT_ID,FLAT_NUMBER_PREFIX_1,FLAT_NUMBER_PREFIX_2,FLAT_NUMBER," +
    "FLAT_NUMBER_SUFFIX_1,FLAT_NUMBER_SUFFIX_2,FLOOR_NUMBER,AREA_SQFT," +
    "CREATE_DATE,FLAT_STATUS FROM FLAT_MASTER WHERE FLAT_NUMBER_PREFIX_1 = ? AND FLAT_NUMBER = ?";

  private static final String FLAT_DETAILS_BY_FLAT_ID_SELECT_QUERY =
    "SELECT FLAT_ID,FLAT_NUMBER_PREFIX_1,FLAT_NUMBER_PREFIX_2,FLAT_NUMBER," +
    "FLAT_NUMBER_SUFFIX_1,FLAT_NUMBER_SUFFIX_2,FLOOR_NUMBER,AREA_SQFT," +
    "CREATE_DATE,FLAT_STATUS FROM FLAT_MASTER WHERE FLAT_ID = ?";

  private static final String DWELLERS_DETAILS_BY_FLAT_NUMBER_SELECT_QUERY =
    "SELECT DWELLERS_ID, FLAT_ID, NAME_SALUTATION, NAME_FIRST, NAME_MIDDLE, NAME_LAST," +
    " NAME_PREFERRED, GENDER, DATE_OF_BIRTH, DATE_OF_ANNIVERSARY," +
    " EMAIL_PRIMARY, EMAIL_SECONDARY, PHONE_BUSINESS, PHONE_BUSINESS_EXTN," +
    " PHONE_MOBILE, PHONE_RESIDENCE, PREFERRED_CONTACT, DWELLERS_STATUS, CREATE_DATE" +
    " FROM DWELLERS_MASTER WHERE DWELLERS_MASTER.FLAT_ID = (SELECT FLAT_ID FROM FLAT_MASTER" +
    " WHERE FLAT_NUMBER_PREFIX_1 = ? AND FLAT_NUMBER = ?)";

  private static final String DWELLERS_DETAILS_BY_FLAT_ID_SELECT_QUERY =
    "SELECT DWELLERS_ID, FLAT_ID, NAME_SALUTATION, NAME_FIRST, NAME_MIDDLE, NAME_LAST," +
    " NAME_PREFERRED, GENDER, DATE_OF_BIRTH, DATE_OF_ANNIVERSARY," +
    " EMAIL_PRIMARY, EMAIL_SECONDARY, PHONE_BUSINESS, PHONE_BUSINESS_EXTN," +
    " PHONE_MOBILE, PHONE_RESIDENCE, PREFERRED_CONTACT, DWELLERS_STATUS, CREATE_DATE" +
    " FROM DWELLERS_MASTER WHERE DWELLERS_MASTER.FLAT_ID = ?";
  
  private static final String UPDATE_DWELLERS_MASTER = "UPDATE DWELLERS_MASTER SET " +
  	"NAME_SALUTATION=?,NAME_FIRST=?,NAME_MIDDLE=?,NAME_LAST=?,NAME_PREFERRED=?" +
  	",GENDER=?,DATE_OF_BIRTH=?,DATE_OF_ANNIVERSARY=?,EMAIL_PRIMARY=?,EMAIL_SECONDARY=?," +
  	"PHONE_BUSINESS=?,PHONE_BUSINESS_EXTN=?,PHONE_MOBILE=?,PHONE_RESIDENCE=?" +
  	",PREFERRED_CONTACT=? WHERE DWELLERS_ID=?";

  public FlatDetailsVO getFlatDetails(String block, String flatNumber) throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(FLAT_DETAILS_BY_FLAT_NUMBER_SELECT_QUERY,block,flatNumber);
    FlatDetailsVO flatDetailsVO = null;
    if (dataSet.getRowCount() >= 1) {
      flatDetailsVO = getFlatDetailsVO(dataSet, 0);
    }
    return flatDetailsVO;
  }

  public FlatDetailsVO getFlatDetails(int flatId) throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(FLAT_DETAILS_BY_FLAT_ID_SELECT_QUERY,flatId);
    FlatDetailsVO flatDetailsVO = null;
    if (dataSet.getRowCount() >= 1) {
      flatDetailsVO = getFlatDetailsVO(dataSet, 0);
    }
    return flatDetailsVO;
  }

  public ArrayList<FlatDetailsVO> getAllFlats() throws DbException {
    DataSet dataSet = mySqlSession.executeQuery(ALL_FLATS_SELECT_QUERY);
    ArrayList<FlatDetailsVO> allFlatsVOList = new ArrayList<FlatDetailsVO>();
    if (dataSet.getRowCount() >= 1) {
      int i = 0;
      while (i < dataSet.getRowCount()) {
        FlatDetailsVO flatDetailsVO = getFlatDetailsVO(dataSet, i++);
        allFlatsVOList.add(flatDetailsVO);
      }
    }
    return allFlatsVOList;
  }
  
  public DwellersMasterVO getDwellerDetails(String block, String flatNumber)
      throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(
            DWELLERS_DETAILS_BY_FLAT_NUMBER_SELECT_QUERY, block, flatNumber);
    DwellersMasterVO dwellersMasterVO = null;
    if (dataSet.getRowCount() >= 1) {
      dwellersMasterVO = getDwellersMasterVO(dataSet, 0);
    }
    return dwellersMasterVO;
  }

  public DwellersMasterVO getDwellerDetails(int flatId) throws Exception {
    DataSet dataSet =
        mySqlSession.executeQuery(DWELLERS_DETAILS_BY_FLAT_ID_SELECT_QUERY,
            flatId);
    DwellersMasterVO dwellersMasterVO = null;
    if (dataSet.getRowCount() >= 1) {
      dwellersMasterVO = getDwellersMasterVO(dataSet, 0);
    }
    return dwellersMasterVO;
  }

  /**
   * 
   */
  private FlatDetailsVO getFlatDetailsVO(DataSet dataSet, int rowNum) {
    FlatDetailsVO flatDetailsVO = new FlatDetailsVO();
    flatDetailsVO.setFlatId(dataSet.getIntValue(rowNum, 0));
    flatDetailsVO.setFlatNumberPrefix1(dataSet.getStringValue(rowNum, 1));
    flatDetailsVO.setFlatNumberPrefix2(dataSet.getStringValue(rowNum, 2));
    flatDetailsVO.setFlatNumber(dataSet.getStringValue(rowNum, 3));
    flatDetailsVO.setFlatNumberSuffix1(dataSet.getStringValue(rowNum, 4));
    flatDetailsVO.setFlatNumberSuffix2(dataSet.getStringValue(rowNum, 5));
    flatDetailsVO.setFloorNumber(dataSet.getStringValue(rowNum, 6));
    flatDetailsVO.setAreaInSqft(dataSet.getIntValue(rowNum, 7));
    flatDetailsVO.setCreateDate(dataSet.getDateValue(rowNum, 8));
    return flatDetailsVO;
  }
  
  private DwellersMasterVO getDwellersMasterVO(DataSet dataSet, int rowNum) {
    DwellersMasterVO dwellersMasterVO = new DwellersMasterVO();
    dwellersMasterVO.setDwellersId(dataSet.getIntValue(rowNum, 0));
    dwellersMasterVO.setFlatId(dataSet.getIntValue(rowNum, 1));
    dwellersMasterVO.setNameSalutation(dataSet.getStringValue(rowNum, 2));
    dwellersMasterVO.setNameFirst(dataSet.getStringValue(rowNum, 3));
    dwellersMasterVO.setNameMiddle(dataSet.getStringValue(rowNum, 4));
    dwellersMasterVO.setNameLast(dataSet.getStringValue(rowNum, 5));
    dwellersMasterVO.setNamePreferred(dataSet.getStringValue(rowNum, 6));
    dwellersMasterVO.setGender(dataSet.getStringValue(rowNum, 7));
    dwellersMasterVO.setDateOfBirth(dataSet.getDateValue(rowNum, 8) );
    dwellersMasterVO.setDateOfAnniversary(dataSet.getDateValue(rowNum, 9) );

    dwellersMasterVO.setEmailPrimary(dataSet.getStringValue(rowNum, 10));
    dwellersMasterVO.setEmailSecondary(dataSet.getStringValue(rowNum, 11));
    dwellersMasterVO.setPhoneBusiness(dataSet.getStringValue(rowNum, 12));
    dwellersMasterVO
        .setPhoneBusinessExtn(dataSet.getStringValue(rowNum, 13));
    dwellersMasterVO.setPhoneMobile(dataSet.getStringValue(rowNum, 14));
    dwellersMasterVO.setPhoneResidence(dataSet.getStringValue(rowNum, 15));
    dwellersMasterVO.setPreferredContact(dataSet.getStringValue(rowNum, 16));
    dwellersMasterVO.setDwellerStatus(DwellerStatus.getDwellerStatus(dataSet
        .getStringValue(rowNum, 17)));
    dwellersMasterVO.setCreateDate(dataSet.getDateValue(rowNum, 18));
    return dwellersMasterVO;
  }
  
  
  /**
 * @param dwellersMasterVO
 * @return int
 * @throws DbException
 */
public int updateDwellersMaster(DwellersMasterVO dwellersMasterVO) throws DbException{
	  int i=0;
	  	mySqlSession.executeUpdate(UPDATE_DWELLERS_MASTER,dwellersMasterVO.getNameSalutation(),
	  			dwellersMasterVO.getNameFirst(),dwellersMasterVO.getNameMiddle(),dwellersMasterVO.getNameLast(),
	  			dwellersMasterVO.getNamePreferred(),dwellersMasterVO.getGender(),dwellersMasterVO.getDateOfBirth(),
	  			dwellersMasterVO.getDateOfAnniversary(),dwellersMasterVO.getEmailPrimary(),dwellersMasterVO.getEmailSecondary(),
	  			dwellersMasterVO.getPhoneBusiness(),dwellersMasterVO.getPhoneBusinessExtn(),dwellersMasterVO.getPhoneMobile(),
	  			dwellersMasterVO.getPhoneResidence(),dwellersMasterVO.getPreferredContact(),dwellersMasterVO.getDwellersId());
	  return i;
  }
}