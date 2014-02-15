/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

/**
 * @author SC64807
 * 
 */
public class SystemParamsVO {

  /**
   * 
   */
  private static final long serialVersionUID = 7850804804834560709L;

  private String paramName = null;
  private String paramValue = null;

  /**
   * @return the paramName
   */
  public String getParamName() {
    return paramName;
  }

  /**
   * @param paramName
   *          the paramName to set
   */
  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  /**
   * @return the paramValue
   */
  public String getParamValue() {
    return paramValue;
  }

  /**
   * @param paramValue
   *          the paramValue to set
   */
  public void setParamValue(String paramValue) {
    this.paramValue = paramValue;
  }
}