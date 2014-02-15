/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

/**
 * @author SC64807
 * 
 */
public class FinancialDetailsVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = -7257229134472593580L;

  private Double currentBalance = null;
  private int currentBucketCount;
  private int flatId;
  private Double priorBalanceMonthOne = null;
  private Double priorBalanceMonthTwo = null;
  private Double priorBalanceMonthThree = null;
  private Double priorBalanceMonthFour = null;
  private Double priorBalanceMonthFive = null;
  private Double priorBalanceMonthSix = null;
  private Double priorBalanceMonthMore = null;
  private int latePaymentFlag = 0;
  private int bounceFlag = 0;

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
   * @return the currentBalance
   */
  public Double getCurrentBalance() {
    return currentBalance;
  }

  /**
   * @return the latePaymentFlag
   */
  public int getLatePaymentFlag() {
	  return latePaymentFlag;
  }

  /**
   * @param latePaymentFlag the latePaymentFlag to set
   */
  public void setLatePaymentFlag(int latePaymentFlag) {
	  this.latePaymentFlag = latePaymentFlag;
  }

  /**
   * @return the bounceFlag
   */
  public int getBounceFlag() {
	  return bounceFlag;
  }

  /**
   * @param bounceFlag the bounceFlag to set
   */
  public void setBounceFlag(int bounceFlag) {
	  this.bounceFlag = bounceFlag;
  }

  /**
   * @param currentBalance
   *          the currentBalance to set
   */
  public void setCurrentBalance(Double currentBalance) {
	  this.currentBalance = currentBalance;
  }

  /**
   * @return the currentBucketCount
   */
  public int getCurrentBucketCount() {
    return currentBucketCount;
  }

  /**
   * @param currentBucketCount
   *          the currentBucketCount to set
   */
  public void setCurrentBucketCount(int currentBucketCount) {
    this.currentBucketCount = currentBucketCount;
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
   * @return the priorBalanceMonthFive
   */
  public Double getPriorBalanceMonthFive() {
    return priorBalanceMonthFive;
  }

  /**
   * @param priorBalanceMonthFive
   *          the priorBalanceMonthFive to set
   */
  public void setPriorBalanceMonthFive(Double priorBalanceMonthFive) {
    this.priorBalanceMonthFive = priorBalanceMonthFive;
  }

  /**
   * @return the priorBalanceMonthFour
   */
  public Double getPriorBalanceMonthFour() {
    return priorBalanceMonthFour;
  }

  /**
   * @param priorBalanceMonthFour
   *          the priorBalanceMonthFour to set
   */
  public void setPriorBalanceMonthFour(Double priorBalanceMonthFour) {
    this.priorBalanceMonthFour = priorBalanceMonthFour;
  }

  /**
   * @return the priorBalanceMonthMore
   */
  public Double getPriorBalanceMonthMore() {
    return priorBalanceMonthMore;
  }

  /**
   * @param priorBalanceMonthMore
   *          the priorBalanceMonthMore to set
   */
  public void setPriorBalanceMonthMore(Double priorBalanceMonthMore) {
    this.priorBalanceMonthMore = priorBalanceMonthMore;
  }

  /**
   * @return the priorBalanceMonthOne
   */
  public Double getPriorBalanceMonthOne() {
    return priorBalanceMonthOne;
  }

  /**
   * @param priorBalanceMonthOne
   *          the priorBalanceMonthOne to set
   */
  public void setPriorBalanceMonthOne(Double priorBalanceMonthOne) {
    this.priorBalanceMonthOne = priorBalanceMonthOne;
  }

  /**
   * @return the priorBalanceMonthSix
   */
  public Double getPriorBalanceMonthSix() {
    return priorBalanceMonthSix;
  }

  /**
   * @param priorBalanceMonthSix
   *          the priorBalanceMonthSix to set
   */
  public void setPriorBalanceMonthSix(Double priorBalanceMonthSix) {
    this.priorBalanceMonthSix = priorBalanceMonthSix;
  }

  /**
   * @return the priorBalanceMonthThree
   */
  public Double getPriorBalanceMonthThree() {
    return priorBalanceMonthThree;
  }

  /**
   * @param priorBalanceMonthThree
   *          the priorBalanceMonthThree to set
   */
  public void setPriorBalanceMonthThree(Double priorBalanceMonthThree) {
    this.priorBalanceMonthThree = priorBalanceMonthThree;
  }

  /**
   * @return the priorBalanceMonthTwo
   */
  public Double getPriorBalanceMonthTwo() {
    return priorBalanceMonthTwo;
  }

  /**
   * @param priorBalanceMonthTwo
   *          the priorBalanceMonthTwo to set
   */
  public void setPriorBalanceMonthTwo(Double priorBalanceMonthTwo) {
    this.priorBalanceMonthTwo = priorBalanceMonthTwo;
  }

}
