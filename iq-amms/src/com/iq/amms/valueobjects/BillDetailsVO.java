/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

import org.iq.valueobject.BaseVO;


/**
 * @author SC64807
 * 
 */
public class BillDetailsVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 4977696435429562314L;

  private String billId;
  private Double chargeAmount;
  private String chargeName;
  private String chargeType;

  /*
   * (non-Javadoc)
   * 
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @return the billId
   */
  public String getBillId() {
    return billId;
  }

  /**
   * @param billId
   *          the billId to set
   */
  public void setBillId(String billId) {
    this.billId = billId;
  }

  /**
   * @return the chargeAmount
   */
  public Double getChargeAmount() {
    return chargeAmount;
  }

  /**
   * @param chargeAmount
   *          the chargeAmount to set
   */
  public void setChargeAmount(Double chargeAmount) {
    this.chargeAmount = chargeAmount;
  }

  /**
   * @return the chargeName
   */
  public String getChargeName() {
    return chargeName;
  }

  /**
   * @param chargeName
   *          the chargeName to set
   */
  public void setChargeName(String chargeName) {
    this.chargeName = chargeName;
  }

  /**
   * @return the chargeType
   */
  public String getChargeType() {
    return chargeType;
  }

  /**
   * @param chargeType
   *          the chargeType to set
   */
  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }

}
