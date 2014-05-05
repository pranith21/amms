/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

import org.iq.util.string.StringUtil;
import org.iq.valueobject.BaseVO;

/**
 * @author SC64807
 * 
 */
public class SessionVO extends BaseVO{
  /**
   * 
   */
  private static final long serialVersionUID = 4322205131918207730L;
  private String apartmentName = null;
  private String apartmentUrl = null;
  private String nameOfUser = null;
  private String username = null;

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
   * @return the apartmentUrl
   */
  public String getApartmentUrl() {
    return apartmentUrl;
  }

  /**
   * @param apartmentUrl
   *          the apartmentUrl to set
   */
  public void setApartmentUrl(String apartmentUrl) {
    this.apartmentUrl = apartmentUrl;
  }

  /**
   * @return the nameOfUser
   */
  public String getNameOfUser() {
    return nameOfUser;
  }

  /**
   * @param nameOfUser
   *          the nameOfUser to set
   */
  public void setNameOfUser(String nameOfUser) {
    this.nameOfUser = nameOfUser;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /* (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("SessionVO = [");
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  ApartmentName = ");
    buffer.append(apartmentName);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  ApartmentUrl = ");
    buffer.append(apartmentUrl);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  NameOfUser = ");
    buffer.append(nameOfUser);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("  Username = ");
    buffer.append(username);
    buffer.append(StringUtil.lineSeparator);
    buffer.append("]");
    
    return buffer.toString();
  }

}
