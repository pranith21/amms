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
	private long paymentID = 0;
	/**
	 * 
	 */
	private ChequeDetailsVO chequeDetailsVO = null;
	/**
	 * 
	 */
	private NeftDetailsVO neftDetailsVO = null;
	

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
	public long getPaymentID() {
		return paymentID;
	}
	/**
	 * @param paymentID the paymentID to set
	 */
	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}

	/**
	 * @return the neftDetailsVO
	 */
	public NeftDetailsVO getNeftDetailsVO() {
		return neftDetailsVO;
	}
	/**
	 * @param neftDetailsVO the neftDetailsVO to set
	 */
	public void setNeftDetailsVO(NeftDetailsVO neftDetailsVO) {
		this.neftDetailsVO = neftDetailsVO;
	}	
}
