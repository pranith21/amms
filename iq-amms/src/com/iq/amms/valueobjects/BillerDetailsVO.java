/**
 * 
 */
package com.iq.amms.valueobjects;

import org.iq.valueobject.BaseVO;

/**
 * @author Sam
 *
 */
public class BillerDetailsVO extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4251314482789365099L;
	
	private String billerName = null;
	private int billerId =0;
	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}
	/**
	 * @param billerName the billerName to set
	 */
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	/**
	 * @return the billerId
	 */
	public int getBillerId() {
		return billerId;
	}
	/**
	 * @param billerId the billerId to set
	 */
	public void setBillerId(int billerId) {
		this.billerId = billerId;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
