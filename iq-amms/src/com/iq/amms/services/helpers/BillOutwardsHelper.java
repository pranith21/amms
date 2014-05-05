/**
 * 
 */
package com.iq.amms.services.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iq.db.DataSet;
import org.iq.exception.DbException;
import org.iq.service.helpers.BaseHelper;

import com.iq.amms.valueobjects.BillOutwardsDetailVO;
import com.iq.amms.valueobjects.BillerDetailsVO;

/**
 * @author Sam
 *
 */
public class BillOutwardsHelper extends BaseHelper {
	
	private static final String BILLER_DETAILS_SQL = "SELECT BILLER_ID,BILLER_NAME FROM" +
			" BILLER_DETAILS"; 
	private static final String BILLER_DETAILS_INSERT_SQL = "INSERT INTO BILLER_DETAILS" +
			"(BILLER_NAME) VALUES(?)"; 
	private static final String BILLER_DETAILS_UPDATE_SQL = "UPDATE BILLER_DETAILS SET " +
			"BILLER_NAME=? WHERE BILLER_ID=?"; 
	private static final String BILLER_DETAILS_DELETE_SQL = "DELETE FROM BILLER_DETAILS " +
			"WHERE BILLER_ID=?"; 
	
	private static final String BILL_OUTWARDS_DETAIL_INSERT = "INSERT INTO BILLS_OUTWARDS_" +
			"DETAILS(BILL_ID,APARTMENT_ID,BILLER_ID,BILL_DETAIL,BILL_NUMBER) VALUES(?,?,?,?,?)";
	
	private static final String BILL_OUTWARDS_MASTER_INSERT = "INSERT INTO BILLS_OUTWARDS_" +
			"MASTER(DATE_OF_RECEIPT,APARTMENT_ID,BILL_STATUS,BILL_AMOUNT) VALUES(?,?,?,?)";
	
	private static final String GET_LAST_BILL_ID_SQL = "SELECT BILL_ID FROM BILLS_OUTWARDS_" +
			"MASTER ORDER BY BILL_ID DESC";
	
	private static final String GET_PENDING_BILLS_SQL = "SELECT BM.BILL_AMOUNT AS BILL_AMOUNT, " +
			"BM.BILL_ID AS BILL_ID, BL.BILLER_NAME AS BILLER_NAME, BD.BILL_DETAIL AS BILL_DETAIL," +
			"BD.BILL_NUMBER AS BILL_NUMBER FROM BILLS_OUTWARDS_MASTER BM, BILLS_OUTWARDS_DETAILS BD," +
			"BILLER_DETAILS BL WHERE BM.BILL_STATUS = 1 AND BM.BILL_ID = BD.BILL_ID AND BD.BILLER_ID=BL.BILLER_ID";
	
	private static final String UPDATE_BILL_STATUS_SQL = "UPDATE BILLS_OUTWARDS_MASTER SET BILL_STATUS=" +
			"? WHERE BILL_ID =?";
	/**
	 * @return
	 * @throws DbException
	 */
	public List<BillerDetailsVO> getBillerDetails() throws DbException{
		List<BillerDetailsVO> billerDetailsVOs = new ArrayList<BillerDetailsVO>();
		
		DataSet dataSet = mySqlSession.executeQuery(BILLER_DETAILS_SQL);
		
		if (dataSet.getRowCount() > 0) {
			for (int i = 0; i < dataSet.getRowCount(); i++) {
				BillerDetailsVO billerDetailsVO = new BillerDetailsVO();
				billerDetailsVO.setBillerId(dataSet.getIntValue(i, "BILLER_ID"));
				billerDetailsVO.setBillerName(dataSet.getStringValue(i, "BILLER_NAME"));
				billerDetailsVOs.add(billerDetailsVO);
			}
		}
		return billerDetailsVOs;
	}

	/**
	 * @param billerName
	 * @throws DbException
	 */
	public void insertBillerData(String billerName) throws DbException{
		mySqlSession.executeUpdate(BILLER_DETAILS_INSERT_SQL,billerName);
	}
	
	
	/**
	 * @param billId
	 * @param billerName
	 * @throws DbException
	 */
	public void updateBillerData(int billId, String billerName) throws DbException{
		mySqlSession.executeUpdate(BILLER_DETAILS_UPDATE_SQL,billerName,billId);
	}
	
	/**
	 * @param billId
	 * @throws DbException
	 */
	public void deleteBiller(int billId) throws DbException{
		mySqlSession.executeUpdate(BILLER_DETAILS_DELETE_SQL,billId);
	}
	
	
	/**
	 * @param billerId
	 * @param billId
	 * @param billDetail
	 * @throws DbException
	 */
	public void insertBillOutwardsDetail(int billerId, int billId, String billDetail, int billNumber) throws DbException{
		mySqlSession.executeUpdate(BILL_OUTWARDS_DETAIL_INSERT, billId, 1, billerId, billDetail, billNumber);
	}
	
	/**
	 * @param billAmount
	 * @throws DbException
	 */
	public void insertBillOutwardMasrter(String billAmount) throws DbException{
		mySqlSession.executeUpdate(BILL_OUTWARDS_MASTER_INSERT, new Date(),1,1,billAmount);
				
	}
	
	/**
	 * @return
	 * @throws DbException
	 */
	public int getLastBillId() throws DbException{
		
		int billId = 0;
		
		DataSet dataSet = mySqlSession.executeQuery(GET_LAST_BILL_ID_SQL);
		
		if (dataSet.getRowCount() > 0) {
			billId = dataSet.getIntValue(0, 0);
		}
		
		return billId;
	}
	
	/**
	 * @return
	 * @throws DbException
	 */
	public List<BillOutwardsDetailVO> getPendingBillsForApproval() throws DbException{
		List<BillOutwardsDetailVO> billOutwardsDetailVOs = new ArrayList<BillOutwardsDetailVO>();
		
		DataSet dataSet = mySqlSession.executeQuery(GET_PENDING_BILLS_SQL);
		
		if(dataSet.getRowCount() > 0){
			for (int i = 0; i < dataSet.getRowCount(); i++) {
				BillOutwardsDetailVO billOutwardsDetailVO = new BillOutwardsDetailVO();
				
				billOutwardsDetailVO.setAmount(dataSet.getDoubleValue(i, "BILL_AMOUNT"));
				billOutwardsDetailVO.setBillDetail(dataSet.getStringValue(i, "BILL_DETAIL"));
				billOutwardsDetailVO.setBillerId(dataSet.getStringValue(i, "BILLER_NAME"));
				billOutwardsDetailVO.setBillId(dataSet.getIntValue(i, "BILL_ID"));
				billOutwardsDetailVO.setBillNumber(dataSet.getIntValue(i, "BILL_NUMBER"));
				
				billOutwardsDetailVOs.add(billOutwardsDetailVO);
			}
		}
		return billOutwardsDetailVOs;
	}
	
	
	/**
	 * @param billId
	 * @param status
	 * @throws DbException
	 */
	public void updateBillStatus(int billId, int status) throws DbException{
		mySqlSession.executeUpdate(UPDATE_BILL_STATUS_SQL, status, billId);
	}
}
