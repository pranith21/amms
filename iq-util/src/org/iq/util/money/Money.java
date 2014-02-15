/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.util.money;

import org.iq.util.string.StringUtil;

/**
 * @author SC64807
 * 
 */
public class Money {
  private String currency = null;
  private Integer amount = 0;
  
  /**
   * 
   */
  public Money() {
    
  }
  
  /**
   * @param amount
   */
  /*public Money(Integer amount){
    this(null, amount);
  }*/

  /**
   * @param currency
   * @param amount
   */
  /*public Money(String currency, Integer amount){
    this.amount = amount;
    this.currency = currency;
  }*/

  /**
   * @return the amount
   */
  public Integer getAmount() {
    return amount;
  }

  /**
   * @return the amount
   */
  public int getAmountFractionPart() {
    return Integer.valueOf(amountAsString().split("\\.")[1]);
    
  }

  /**
   * @return the amount
   */
  public int getAmountWholePart() {
    return Integer.valueOf(amountAsString().split("\\.")[0]);
  }

  /**
   * @param amount
   *          the amount to set
   * @throws Exception 
   */
  public void setAmount(Integer amount) throws Exception {
    if(StringUtil.getStringValue(amount).length()>10){
      throw new Exception("Only 10 digits allowed. 8 for whole number part and 2 for fraction part.");
    }
    this.amount = amount;
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @param currency
   *          the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  private String amountAsString() {
    String string = StringUtil.getStringValue(this.amount);
    if (string.length()==1) {
      string = "0.0"+ string;
    }
    else if (string.length()==2) {
      string = "0."+ string;
    }
    else{
      string =
        string.substring(0, string.length() - 2) + "."
            + string.substring(string.length() - 2);
    }
    return string;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return (StringUtil.isEmpty(this.currency) ? "" : this.currency + " ")
        + amountAsString();
  }
}
