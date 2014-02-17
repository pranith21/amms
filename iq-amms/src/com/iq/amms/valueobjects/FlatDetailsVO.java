package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.util.string.StringUtil;

import com.iq.amms.Constants.FlatStatus;

/**
 * @author SC64807
 *
 */
public class FlatDetailsVO {

  /**
   * 
   */
  private static final long serialVersionUID = 1450840450292097575L;

  private int flatId = -1;
  private String flatNumberPrefix1 = null;
  private String flatNumberPrefix2 = null;
  private String flatNumber = null;
  private String flatNumberSuffix1 = null;
  private String flatNumberSuffix2 = null;
  private String floorNumber = null;
  private String flatStatus = null;
  private int areaInSqft = 0;
  private Date createDate = null;

  /**
   * 
   */
  public FlatDetailsVO() {
    
  }

  /**
   * @return the areaInSqft
   */
  public int getAreaInSqft() {
    return areaInSqft;
  }

  /**
   * @param areaInSqft the areaInSqft to set
   */
  public void setAreaInSqft(int areaInSqft) {
    this.areaInSqft = areaInSqft;
  }

  /**
   * @return the flatId
   */
  public int getFlatId() {
    return flatId;
  }

  /**
   * @param flatId the flatId to set
   */
  public void setFlatId(int flatId) {
    this.flatId = flatId;
  }

  /**
   * @return the name
   */
  public String getFullFlatNumber() {
    return (StringUtil.isEmpty(flatNumberPrefix1) ? ""
        : (flatNumberPrefix1 + " "))
        + (StringUtil.isEmpty(flatNumberPrefix2) ? ""
            : (flatNumberPrefix2 + " "))
        + (StringUtil.isEmpty(flatNumber) ? "" : (flatNumber + " "))
        + (StringUtil.isEmpty(flatNumberSuffix1) ? ""
            : (flatNumberSuffix1 + " "))
        + (StringUtil.isEmpty(flatNumberSuffix2) ? ""
            : (flatNumberSuffix2 + " "));
  }

  /**
   * @return the flatNumber
   */
  public String getFlatNumber() {
    return flatNumber;
  }

  /**
   * @param flatNumber the flatNumber to set
   */
  public void setFlatNumber(String flatNumber) {
    this.flatNumber = flatNumber;
  }

  /**
   * @return the flatNumberPrefix1
   */
  public String getFlatNumberPrefix1() {
    return flatNumberPrefix1;
  }

  /**
   * @param flatNumberPrefix1 the flatNumberPrefix1 to set
   */
  public void setFlatNumberPrefix1(String flatNumberPrefix1) {
    this.flatNumberPrefix1 = flatNumberPrefix1;
  }

  /**
   * @return the flatNumberPrefix2
   */
  public String getFlatNumberPrefix2() {
    return flatNumberPrefix2;
  }

  /**
   * @param flatNumberPrefix2 the flatNumberPrefix2 to set
   */
  public void setFlatNumberPrefix2(String flatNumberPrefix2) {
    this.flatNumberPrefix2 = flatNumberPrefix2;
  }

  /**
   * @return the flatNumberSuffix1
   */
  public String getFlatNumberSuffix1() {
    return flatNumberSuffix1;
  }

  /**
   * @param flatNumberSuffix1 the flatNumberSuffix1 to set
   */
  public void setFlatNumberSuffix1(String flatNumberSuffix1) {
    this.flatNumberSuffix1 = flatNumberSuffix1;
  }

  /**
   * @return the flatNumberSuffix2
   */
  public String getFlatNumberSuffix2() {
    return flatNumberSuffix2;
  }

  /**
   * @param flatNumberSuffix2 the flatNumberSuffix2 to set
   */
  public void setFlatNumberSuffix2(String flatNumberSuffix2) {
    this.flatNumberSuffix2 = flatNumberSuffix2;
  }

  /**
   * @return the floorNumber
   */
  public String getFloorNumber() {
    return floorNumber;
  }

  /**
   * @param floorNumber the floorNumber to set
   */
  public void setFloorNumber(String floorNumber) {
    this.floorNumber = floorNumber;
  }

  /**
   * @return the status
   */
  public String getFlatStatus() {
    return flatStatus;
  }

  /**
   * @param status the status to set
   */
  public void setFlatStatus(String status) {
    this.flatStatus = status;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("FlatDetailsVO = [");
    stringBuffer.append("    flatId = ").append(flatId);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatNumberPrefix1 = ").append(flatNumberPrefix1);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatNumberPrefix2 = ").append(flatNumberPrefix2);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatNumber = ").append(flatNumber);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatNumberSuffix1 = ").append(flatNumberSuffix1);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatNumberSuffix2 = ").append(flatNumberSuffix2);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    floorNumber = ").append(floorNumber);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatStatus = ").append(flatStatus);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    areaInSqft = ").append(areaInSqft);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    createDate = ").append(createDate);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("]");
    return stringBuffer.toString();
  }

  /**
   * @return the createDate
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @param createDate the createDate to set
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}