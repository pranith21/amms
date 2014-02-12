/**
 * 
 */
package com.iq.amms.valueobjects;


/**
 * @author Sam
 *
 */
public class PaymentDetailsVO {
	
	/**
	 * 
	 */
	private int paymentID = 0;
	/**
	 * 
	 */
	private ChequeDetailsVO chequeDetailsVO = null;
	/**
	 * 
	 */
	private NEFTDetailsVO neftDetailsVO = null;
	

	/**
	 * @return the chequeDetailsVO
	 */
	public ChequeDetailsVO getChequeDetailsVO() {
		return chequeDetailsVO;
	}
	/**
	 * @param chequeDetailsVO the chequeDetailsVO to set
	 */
	public void setChequeDetailsVO(ChequeDetailsVO chequeDetailsVO) {
		this.chequeDetailsVO = chequeDetailsVO;
	}
	
	/**
	 * @return the paymentID
	 */
	public int getPaymentID() {
		return paymentID;
	}
	/**
	 * @param paymentID the paymentID to set
	 */
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	/**
	 * @return the neftDetailsVO
	 */
	public NEFTDetailsVO getNeftDetailsVO() {
		return neftDetailsVO;
	}
	/**
	 * @param neftDetailsVO the neftDetailsVO to set
	 */
	public void setNeftDetailsVO(NEFTDetailsVO neftDetailsVO) {
		this.neftDetailsVO = neftDetailsVO;
	}	
}
