package com.iq.amms.services.helpers;

import java.util.ArrayList;

import org.iq.db.DataSet;

import com.iq.amms.valueobjects.AllFlatsVO;

/**
 * @author Sam
 * 
 */
public class GetAllFlats extends BaseHelper {

  private static final String ALL_FLATS_SELECT_QUERY =
    "SELECT FLAT_NUMBER_PREFIX_1, FLAT_NUMBER_PREFIX_2, FLAT_NUMBER," +
    " FLAT_NUMBER_SUFFIX_1, FLAT_NUMBER_SUFFIX_2," +
    " NAME_SALUTATION, NAME_FIRST, NAME_MIDDLE, NAME_LAST, NAME_PREFERRED," +
    " PHONE_MOBILE, PHONE_RESIDENCE, PHONE_BUSINESS, PHONE_BUSINESS_EXTN," +
    " EMAIL_PRIMARY, EMAIL_SECONDARY, PREFERRED_CONTACT, CURRENT_BALANCE, FLAT_MASTER.FLAT_ID" +
    " FROM FLAT_MASTER, DWELLERS_MASTER, FINANCIALS" +
    " WHERE DWELLERS_MASTER.FLAT_ID = FLAT_MASTER.FLAT_ID" +
    " AND FINANCIALS.FLAT_ID = FLAT_MASTER.FLAT_ID";

  public ArrayList<AllFlatsVO> execute() throws Exception {

    DataSet dataSet = mySqlSession.executeQuery(ALL_FLATS_SELECT_QUERY);
//    System.out.println("dataSet.getRowCount() = "+dataSet.getRowCount());
//    System.out.println(dataSet);

    ArrayList<AllFlatsVO> allFlatsVOList = new ArrayList<AllFlatsVO>();
    if (dataSet.getRowCount() >= 1) {
      int i = 0;
      while (i < dataSet.getRowCount()) {
        AllFlatsVO allFlatsVO = new AllFlatsVO();
        allFlatsVO.setFlatNumberPrefix1(dataSet.getStringValue(i, 0));
        allFlatsVO.setFlatNumberPrefix2(dataSet.getStringValue(i, 1));
        allFlatsVO.setFlatNumber(dataSet.getStringValue(i, 2));
        allFlatsVO.setFlatNumberSuffix1(dataSet.getStringValue(i, 3));
        allFlatsVO.setFlatNumberSuffix2(dataSet.getStringValue(i, 4));
        
        allFlatsVO.setNameSalutation(dataSet.getStringValue(i, 5));
        allFlatsVO.setNameFirst(dataSet.getStringValue(i, 6));
        allFlatsVO.setNameMiddle(dataSet.getStringValue(i, 7));
        allFlatsVO.setNameLast(dataSet.getStringValue(i, 8));
        allFlatsVO.setNamePreferred(dataSet.getStringValue(i, 9));
        
        allFlatsVO.setPhoneMobile(dataSet.getStringValue(i, 10));
        allFlatsVO.setPhoneResidence(dataSet.getStringValue(i, 11));
        allFlatsVO.setPhoneBusiness(dataSet.getStringValue(i, 12));
        allFlatsVO.setPhoneBusinessExtn(dataSet.getStringValue(i, 13));
        allFlatsVO.setEmailPrimary(dataSet.getStringValue(i, 14));
        allFlatsVO.setEmailSecondary(dataSet.getStringValue(i, 15));
        allFlatsVO.setPreferredContact(dataSet.getStringValue(i, 16));
        
        allFlatsVO.setCurrentBalance(dataSet.getDoubleValue(i, 17));
        
        allFlatsVO.setFlatId(dataSet.getIntValue(i++, 18));

        allFlatsVOList.add(allFlatsVO);
      }
    }
    return allFlatsVOList;
  }
  
  public static void main(String[] args) {
    GetAllFlats getAllFlats = new GetAllFlats();
    try {
      ArrayList<AllFlatsVO> allFlatsVOList = getAllFlats.execute();
      if (allFlatsVOList !=null && allFlatsVOList.size()>0) {
        
      }
      for (AllFlatsVO flatsVO : allFlatsVOList) {
        flatsVO.getFullFlatNumber();
        flatsVO.getFullName();
        flatsVO.getPrefferdContact();
      }
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}