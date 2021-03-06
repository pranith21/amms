/**
 * 
 */
package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.valueobject.BaseVO;

/**
 * @author Sam
 *
 */
public class ChequeDetailsVO extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6202573073959624312L;
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
	private String chequeDrawnBranch = null;
	/**
	 * 
	 */
	private Date chequeDrawnDate = null;
	/**
	 * 
	 */
	private Date chequeClearanceDate = null;
	/**
	 * 
	 */
	private Date chequeReceivedDate = null;
	
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
	/**
	 * @return the chequeReceivedDate
	 */
	public Date getChequeReceivedDate() {
		return chequeReceivedDate;
	}
	/**
	 * @param chequeReceivedDate the chequeReceivedDate to set
	 */
	public void setChequeReceivedDate(Date chequeReceivedDate) {
		this.chequeReceivedDate = chequeReceivedDate;
	}
	/**
	 * @return the chequeDrawnBranch
	 */
	public String getChequeDrawnBranch() {
		return chequeDrawnBranch;
	}
	/**
	 * @param chequeDrawnBranch the chequeDrawnBranch to set
	 */
	public void setChequeDrawnBranch(String chequeDrawnBranch) {
		this.chequeDrawnBranch = chequeDrawnBranch;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
