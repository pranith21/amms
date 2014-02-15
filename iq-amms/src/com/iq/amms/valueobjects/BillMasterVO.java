/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author SC64807
 * 
 */
public class BillMasterVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 4977696435429562314L;

  private Date dateOfGeneration;
  private Date dateOfDue;
  private int flatId;
  private String billId;
  private int billStatus;
  private Double totalAmount;
  private ArrayList<BillDetailsVO> billDetailsList = new ArrayList<BillDetailsVO>();

  /**
   * @return the billDetailsList
   */
  public ArrayList<BillDetailsVO> getBillDetailsList() {
    return billDetailsList;
  }

  /**
   * @param billDetailsList the billDetailsList to set
   */
  public void setBillDetailsList(ArrayList<BillDetailsVO> billDetailsList) {
    this.billDetailsList = billDetailsList;
  }

  /**
   * @param billDetailsList the billDetailsList to set
   */
  public void addToBillDetailsList(BillDetailsVO billDetails) {
    this.billDetailsList.add(billDetails);
  }

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
   * @return the billStatus
   */
  public int getBillStatus() {
    return billStatus;
  }

  /**
   * @param billStatus
   *          the billStatus to set
   */
  public void setBillStatus(int billStatus) {
    this.billStatus = billStatus;
  }

  /**
   * @return the dateOfDue
   */
  public Date getDateOfDue() {
    return dateOfDue;
  }

  /**
   * @param dateOfDue
   *          the dateOfDue to set
   */
  public void setDateOfDue(Date dateOfDue) {
    this.dateOfDue = dateOfDue;
  }

  /**
   * @return the dateOfGeneration
   */
  public Date getDateOfGeneration() {
    return dateOfGeneration;
  }

  /**
   * @param dateOfGeneration
   *          the dateOfGeneration to set
   */
  public void setDateOfGeneration(Date dateOfGeneration) {
    this.dateOfGeneration = dateOfGeneration;
  }

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
   * @return the totalAmount
   */
  public Double getTotalAmount() {
    return totalAmount;
  }

  /**
   * @param totalAmount
   *          the totalAmount to set
   */
  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

}
