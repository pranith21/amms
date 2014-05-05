/**
 * 
 */
package com.iq.amms.valueobjects;

import org.iq.valueobject.BaseVO;

/**
 * @author Sam
 *
 */
public class BillOutwardsDetailVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1591126998433478075L;
	
	private int billId = 0;
	
	private String billDetail = null;
	
	private String billerName = null;
	
	private int billNumber = 0;
	
	private Double amount = null;
	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}



	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}



	/**
	 * @return the billId
	 */
	public int getBillId() {
		return billId;
	}



	/**
	 * @param billId the billId to set
	 */
	public void setBillId(int billId) {
		this.billId = billId;
	}



	/**
	 * @return the billDetail
	 */
	public String getBillDetail() {
		return billDetail;
	}



	/**
	 * @param billDetail the billDetail to set
	 */
	public void setBillDetail(String billDetail) {
		this.billDetail = billDetail;
	}



	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}



	/**
	 * @param billerName the billerName to set
	 */
	public void setBillerId(String billerName) {
		this.billerName = billerName;
	}



	/**
	 * @return the billNumber
	 */
	public int getBillNumber() {
		return billNumber;
	}



	/**
	 * @param billNumber the billNumber to set
	 */
	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
