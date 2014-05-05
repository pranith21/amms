/**
 * 
 */
package com.iq.amms.valueobjects;

import org.iq.valueobject.BaseVO;


/**
 * @author Sam
 *
 */
public class PaymentDetailsVO extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5152005281923639145L;
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
	 * 
	 */
	private String comments = null;
	

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}	
}
