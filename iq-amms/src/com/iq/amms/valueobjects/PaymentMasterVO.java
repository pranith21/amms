package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.util.string.StringUtil;
import org.iq.valueobject.BaseVO;

/**
 * @author Sam
 */
public class PaymentMasterVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = -1073460229534744840L;
  /**
   * 
   */
  private int flatId = 0;
  /**
   * 
   */
  private Double paidAmount = null;
  /**
   * 
   */
  private int paymentStatus = 0;
  /**
   * 
   */
  private long paymentID = 0;
  /**
   * 
   */
  private Date paymentDate = null;

  /**
   * 
   */
  private String paymentType = null;

  /**
   * @return the flatId
   */
  public int getFlatId() {
    return flatId;
  }

  /**
   * @param flatId
   *          the flatId to set
   */
  public void setFlatId(int flatId) {
    this.flatId = flatId;
  }

  /**
   * @return the paymentType
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * @param paymentType
   *          the paymentType to set
   */
  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  /**
   * @return the paidAmount
   */
  public Double getPaidAmount() {
    return paidAmount;
  }

  /**
   * @param paidAmount
   *          the paidAmount to set
   */
  public void setPaidAmount(Double paidAmount) {
    this.paidAmount = paidAmount;
  }

  /**
   * @return the paymentStatus
   */
  public int getPaymentStatus() {
    return paymentStatus;
  }

  /**
   * @param paymentStatus
   *          the paymentStatus to set
   */
  public void setPaymentStatus(int paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  /**
   * @return the paymentID
   */
  public long getPaymentID() {
    return paymentID;
  }

  /**
   * @param paymentID
   *          the paymentID to set
   */
  public void setPaymentID(long paymentID) {
    this.paymentID = paymentID;
  }

  /**
   * @return the paymentDate
   */
  public Date getPaymentDate() {
    return paymentDate;
  }

  /**
   * @param paymentDate
   *          the paymentDate to set
   */
  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  /*
   * (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("FlatDetailsVO = [");
    stringBuffer.append("    flatId = ").append(flatId);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    paymentID = ").append(paymentID);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    paymentDate = ").append(paymentDate);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    paidAmount = ").append(paidAmount);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    paymentType = ").append(paymentType);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    paymentStatus = ").append(paymentStatus);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("]");
    return stringBuffer.toString();
  }
}