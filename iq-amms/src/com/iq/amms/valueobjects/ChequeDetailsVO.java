/**
 * 
 */
package com.iq.amms.valueobjects;

import java.util.Date;

/**
 * @author Sam
 *
 */
public class ChequeDetailsVO {
	
	/**
	 * 
	 */
	private int chequeNumber = 0;
	/**
	 * 
	 */
	private String chequeDraweeBank = null;
	/**
	 * 
	 */
	private Date chequeDrawnDate = null;
	/**
	 * 
	 */
	private Date chequeClearanceDate = null;
	
	/**
	 * @return the chequeNumber
	 */
	public int getChequeNumber() {
		return chequeNumber;
	}
	/**
	 * @param chequeNumber the chequeNumber to set
	 */
	public void setChequeNumber(int chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	/**
	 * @return the chequeDraweeBank
	 */
	public String getChequeDraweeBank() {
		return chequeDraweeBank;
	}
	/**
	 * @param chequeDraweeBank the chequeDraweeBank to set
	 */
	public void setChequeDraweeBank(String chequeDraweeBank) {
		this.chequeDraweeBank = chequeDraweeBank;
	}
	/**
	 * @return the chequeDrawnDate
	 */
	public Date getChequeDrawnDate() {
		return chequeDrawnDate;
	}
	/**
	 * @param chequeDrawnDate the chequeDrawnDate to set
	 */
	public void setChequeDrawnDate(Date chequeDrawnDate) {
		this.chequeDrawnDate = chequeDrawnDate;
	}
	/**
	 * @return the chequeClearanceDate
	 */
	public Date getChequeClearanceDate() {
		return chequeClearanceDate;
	}
	/**
	 * @param chequeClearanceDate the chequeClearanceDate to set
	 */
	public void setChequeClearanceDate(Date chequeClearanceDate) {
		this.chequeClearanceDate = chequeClearanceDate;
	}
	

}
