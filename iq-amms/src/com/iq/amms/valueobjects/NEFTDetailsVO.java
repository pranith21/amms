/**
 * 
 */
package com.iq.amms.valueobjects;

import java.util.Date;

/**
 * @author prashanth1
 *
 */
public class NEFTDetailsVO {
	
	/**
	 * 
	 */
	private int NEFTTransactionID = 0;
	/**
	 * 
	 */
	private Date NEFTTransactionDate = null;
	/**
	 * 
	 */
	private Date NeftClearanceDate = null;
	
	/**
	 * @return the nEFTTransactionID
	 */
	public int getNEFTTransactionID() {
		return NEFTTransactionID;
	}
	/**
	 * @param nEFTTransactionID the nEFTTransactionID to set
	 */
	public void setNEFTTransactionID(int nEFTTransactionID) {
		NEFTTransactionID = nEFTTransactionID;
	}
	/**
	 * @return the nEFTTransactionDate
	 */
	public Date getNEFTTransactionDate() {
		return NEFTTransactionDate;
	}
	/**
	 * @param nEFTTransactionDate the nEFTTransactionDate to set
	 */
	public void setNEFTTransactionDate(Date nEFTTransactionDate) {
		NEFTTransactionDate = nEFTTransactionDate;
	}
	/**
	 * @return the neftClearanceDate
	 */
	public Date getNeftClearanceDate() {
		return NeftClearanceDate;
	}
	/**
	 * @param neftClearanceDate the neftClearanceDate to set
	 */
	public void setNeftClearanceDate(Date neftClearanceDate) {
		NeftClearanceDate = neftClearanceDate;
	}	
}
