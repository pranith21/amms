package com.iq.amms.services.helpers;

import org.iq.db.DataSet;
import org.iq.service.helpers.BaseHelper;

import com.iq.amms.valueobjects.ApartmentMasterVO;

/**
 * @author Sam
 * 
 */
public class GetApartmentDetails extends BaseHelper {

  private static final String APARTMENT_DETAILS_SELECT_QUERY =
      "SELECT APARTMENT_ID, APARTMENT_NAME, APARTMENT_TYPE, STREET_NUMBER, STREET_NAME,"
          + " POST_OFFICE, LANDMARK, LOCALITY, DISTRICT, PIN, CITY, STATE, COUNTRY,"
          + " APARTMENT_STATUS, NUMBER_OF_UNITS, CREATE_DATE FROM APARTMENT_MASTER";

  public ApartmentMasterVO execute() throws Exception {

    DataSet dataSet =
        mySqlSession.executeQuery(APARTMENT_DETAILS_SELECT_QUERY);
    ApartmentMasterVO apartmentMasterVO = null;
    System.out.println("dataSet.getRowCount() = "+dataSet.getRowCount());
    System.out.println(dataSet);
    if (dataSet.getRowCount() >= 1) {
      apartmentMasterVO = new ApartmentMasterVO();
      apartmentMasterVO.setApartmentId(dataSet.getIntValue(0, 0));
      apartmentMasterVO.setApartmentName(dataSet.getStringValue(0, 1));
      apartmentMasterVO.setApartmentType(dataSet.getStringValue(0, 2));
      apartmentMasterVO.setStreetNumber(dataSet.getStringValue(0, 3));
      apartmentMasterVO.setStreetName(dataSet.getStringValue(0, 4));
      apartmentMasterVO.setPostOffice(dataSet.getStringValue(0, 5));
      apartmentMasterVO.setLandmark(dataSet.getStringValue(0, 6));
      apartmentMasterVO.setLocality(dataSet.getStringValue(0, 7));
      apartmentMasterVO.setDistrict(dataSet.getStringValue(0, 8));
      apartmentMasterVO.setPin(dataSet.getIntValue(0, 9));
      apartmentMasterVO.setCity(dataSet.getStringValue(0, 10));
      apartmentMasterVO.setState(dataSet.getStringValue(0, 11));
      apartmentMasterVO.setCountry(dataSet.getStringValue(0, 12));
      apartmentMasterVO.setApartmentStatus(dataSet.getStringValue(0, 13));
      apartmentMasterVO.setNumberOfUnits(dataSet.getIntValue(0, 14));
      apartmentMasterVO.setCreateDate(dataSet.getDateValue(0, 15));
    }
    System.out.println(apartmentMasterVO);
    return apartmentMasterVO;
  }
}