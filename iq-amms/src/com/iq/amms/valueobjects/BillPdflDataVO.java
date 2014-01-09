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
public class BillPdflDataVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 4977696435429562314L;

  private String billId;
  private String name = null;
  private String flatNumber = null;
  private Date dateOfGeneration;
  private Date dateOfDue;
  private Double totalAmount;
  private ArrayList<BillDetailsVO> billDetailsList =
      new ArrayList<BillDetailsVO>();

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
   * @return the billId
   */
  public String getBillId() {
    return billId;
  }

  /**
   * @param billId the billId to set
   */
  public void setBillId(String billId) {
    this.billId = billId;
  }

  /**
   * @return the dateOfDue
   */
  public Date getDateOfDue() {
    return dateOfDue;
  }

  /**
   * @param dateOfDue the dateOfDue to set
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
   * @param dateOfGeneration the dateOfGeneration to set
   */
  public void setDateOfGeneration(Date dateOfGeneration) {
    this.dateOfGeneration = dateOfGeneration;
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
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the totalAmount
   */
  public Double getTotalAmount() {
    return totalAmount;
  }

  /**
   * @param totalAmount the totalAmount to set
   */
  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }
}