/**
 * 
 */
package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.valueobject.BaseVO;

/**
 * @author prashanth1
 */
public class NeftDetailsVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 9206630228377704832L;
  /**
   * 
   */
  private String neftTransactionId = null;
  /**
   * 
   */
  private Date neftTransactionDate = null;
  /**
   * 
   */
  private Date neftClearanceDate = null;

  /**
   * @return the nEFTTransactionID
   */
  public String getNeftTransactionID() {
    return neftTransactionId;
  }

  /**
   * @param nEFTTransactionID
   *          the nEFTTransactionID to set
   */
  public void setNeftTransactionID(String neftTransactionId) {
    this.neftTransactionId = neftTransactionId;
  }

  /**
   * @return the nEFTTransactionDate
   */
  public Date getNeftTransactionDate() {
    return neftTransactionDate;
  }

  /**
   * @param nEFTTransactionDate
   *          the nEFTTransactionDate to set
   */
  public void setNeftTransactionDate(Date neftTransactionDate) {
    this.neftTransactionDate = neftTransactionDate;
  }

  /**
   * @return the neftClearanceDate
   */
  public Date getNeftClearanceDate() {
    return neftClearanceDate;
  }

  /**
   * @param neftClearanceDate
   *          the neftClearanceDate to set
   */
  public void setNeftClearanceDate(Date neftClearanceDate) {
    this.neftClearanceDate = neftClearanceDate;
  }

  /*
   * (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "toString not implemented";
  }
}