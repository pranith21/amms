/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public class ApartmentMasterVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 931560542591846052L;
  private int apartmentId;
  private String apartmentName;
  private String apartmentType;
  private String streetNumber;
  private String streetName;
  private String postOffice;
  private String landmark;
  private String locality;
  private String district;
  private int pin;
  private String city;
  private String state;
  private String country;
  private String apartmentStatus;
  private int numberOfUnits;
  private Date createDate;

  /**
   * @return the apartmentId
   */
  public int getApartmentId() {
    return apartmentId;
  }

  /**
   * @param apartmentId
   *          the apartmentId to set
   */
  public void setApartmentId(int apartmentId) {
    this.apartmentId = apartmentId;
  }

  /**
   * @return the apartmentName
   */
  public String getApartmentName() {
    return apartmentName;
  }

  /**
   * @param apartmentName
   *          the apartmentName to set
   */
  public void setApartmentName(String apartmentName) {
    this.apartmentName = apartmentName;
  }

  /**
   * @return the apartmentStatus
   */
  public String getApartmentStatus() {
    return apartmentStatus;
  }

  /**
   * @param apartmentStatus
   *          the apartmentStatus to set
   */
  public void setApartmentStatus(String apartmentStatus) {
    this.apartmentStatus = apartmentStatus;
  }

  /**
   * @return the apartmentType
   */
  public String getApartmentType() {
    return apartmentType;
  }

  /**
   * @param apartmentType
   *          the apartmentType to set
   */
  public void setApartmentType(String apartmentType) {
    this.apartmentType = apartmentType;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the createDate
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @param createDate
   *          the createDate to set
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * @return the district
   */
  public String getDistrict() {
    return district;
  }

  /**
   * @param district
   *          the district to set
   */
  public void setDistrict(String district) {
    this.district = district;
  }

  /**
   * @return the landmark
   */
  public String getLandmark() {
    return landmark;
  }

  /**
   * @param landmark
   *          the landmark to set
   */
  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }

  /**
   * @return the locality
   */
  public String getLocality() {
    return locality;
  }

  /**
   * @param locality
   *          the locality to set
   */
  public void setLocality(String locality) {
    this.locality = locality;
  }

  /**
   * @return the numberOfUnits
   */
  public int getNumberOfUnits() {
    return numberOfUnits;
  }

  /**
   * @param numberOfUnits
   *          the numberOfUnits to set
   */
  public void setNumberOfUnits(int numberOfUnits) {
    this.numberOfUnits = numberOfUnits;
  }

  /**
   * @return the pin
   */
  public int getPin() {
    return pin;
  }

  /**
   * @param pin
   *          the pin to set
   */
  public void setPin(int pin) {
    this.pin = pin;
  }

  /**
   * @return the postOffice
   */
  public String getPostOffice() {
    return postOffice;
  }

  /**
   * @param postOffice
   *          the postOffice to set
   */
  public void setPostOffice(String postOffice) {
    this.postOffice = postOffice;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @param streetName
   *          the streetName to set
   */
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @param streetNumber
   *          the streetNumber to set
   */
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  /* (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("ApartmentMasterVO = [");
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  ApartmentId = ");
    buffer.append(apartmentId);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  ApartmentName = ");
    buffer.append(apartmentName);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  ApartmentType = ");
    buffer.append(apartmentType);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  StreetNumber = ");
    buffer.append(streetNumber);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  StreetName = ");
    buffer.append(streetName);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  PostOffice = ");
    buffer.append(postOffice);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  Landmark = ");
    buffer.append(landmark);
    buffer.append(StringUtil.lineSeparator);
/*    apartmentMasterVO.setLocality(dataSet.getStringValue(0, 7));
    apartmentMasterVO.setDistrict(dataSet.getStringValue(0, 8));
    apartmentMasterVO.setPin(dataSet.getIntValue(0, 9));
    apartmentMasterVO.setCity(dataSet.getStringValue(0, 10));
    apartmentMasterVO.setState(dataSet.getStringValue(0, 11));
    apartmentMasterVO.setCountry(dataSet.getStringValue(0, 12));
    apartmentMasterVO.setApartmentStatus(dataSet.getStringValue(0, 13));
    apartmentMasterVO.setNumberOfUnits(dataSet.getIntValue(0, 14));
    apartmentMasterVO.setCreateDate(dataSet.getDateValue(0, 15));*/
    
    return buffer.toString();
  }
}