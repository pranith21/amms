/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.services.helpers;

import org.iq.db.DataSet;
import org.iq.exception.DbException;

import com.iq.amms.valueobjects.FinancialDetailsVO;

/**
 * @author SC64807
 * 
 */
public class FinancialsHelper extends BaseHelper {

  private static final String FINANCIAL_DETAILS_BY_FLAT_ID_SELECT_QUERY =
    "SELECT CURRENT_BALANCE, CURRENT_BUCKET_COUNT, FLAT_ID,"
    + " PRIOR_BALANCE_1_MONTH, PRIOR_BALANCE_2_MONTH, PRIOR_BALANCE_3_MONTH,"
    + " PRIOR_BALANCE_4_MONTH, PRIOR_BALANCE_5_MONTH, PRIOR_BALANCE_6_MONTH,"
    + " PRIOR_BALANCE_MORE FROM FINANCIALS WHERE FLAT_ID = ?";

  private static final String FINANCIAL_DETAILS_UPDATE_QUERY =
      "UPDATE FINANCIALS SET" +
      " CURRENT_BALANCE = ?, CURRENT_BUCKET_COUNT = ?," +
      " PRIOR_BALANCE_1_MONTH = ?, PRIOR_BALANCE_2_MONTH = ?," +
      " PRIOR_BALANCE_3_MONTH = ?, PRIOR_BALANCE_4_MONTH = ?," +
      " PRIOR_BALANCE_5_MONTH = ?, PRIOR_BALANCE_6_MONTH = ?," +
      " PRIOR_BALANCE_MORE = ? WHERE FLAT_ID = ?";

  /**
   * @param flatId
   * @throws DbException
   */
  public FinancialDetailsVO getFinancialDetails(int flatId)
  throws DbException {
    DataSet dataSet =
      mySqlSession.executeQuery(FINANCIAL_DETAILS_BY_FLAT_ID_SELECT_QUERY,
          flatId);
    FinancialDetailsVO financialDetailsVO = null;
    if (dataSet.getRowCount() >= 1) {
      financialDetailsVO = getFinancialDetailsVO(dataSet, 0);
    }
    return financialDetailsVO;
  }
  
  /**
   * @throws DbException 
   * 
   */
  public void updateFinancialDetails(FinancialDetailsVO financialDetailsVO)
      throws DbException {
    mySqlSession.executeUpdate(FINANCIAL_DETAILS_UPDATE_QUERY,
        financialDetailsVO.getCurrentBalance(), financialDetailsVO
            .getCurrentBucketCount(), financialDetailsVO
            .getPriorBalanceMonthOne(), financialDetailsVO
            .getPriorBalanceMonthTwo(), financialDetailsVO
            .getPriorBalanceMonthThree(), financialDetailsVO
            .getPriorBalanceMonthFour(), financialDetailsVO
            .getPriorBalanceMonthFive(), financialDetailsVO
            .getPriorBalanceMonthSix(), financialDetailsVO
            .getPriorBalanceMonthMore(),financialDetailsVO.getFlatId());
  }

  /**
   * @param dataSet
   * @param i
   * @return
   */
  private FinancialDetailsVO getFinancialDetailsVO(DataSet dataSet,
      int rowNum) {
    FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
    financialDetailsVO.setCurrentBalance(dataSet.getDoubleValue(rowNum, 0));
    financialDetailsVO.setCurrentBucketCount(dataSet.getIntValue(rowNum, 1));
    financialDetailsVO.setFlatId(dataSet.getIntValue(rowNum, 2));
    financialDetailsVO.setPriorBalanceMonthOne(dataSet.getDoubleValue(rowNum, 3));
    financialDetailsVO.setPriorBalanceMonthTwo(dataSet.getDoubleValue(rowNum, 4));
    financialDetailsVO.setPriorBalanceMonthThree(dataSet.getDoubleValue(rowNum, 5));
    financialDetailsVO.setPriorBalanceMonthFour(dataSet.getDoubleValue(rowNum, 6));
    financialDetailsVO.setPriorBalanceMonthFive(dataSet.getDoubleValue(rowNum, 7));
    financialDetailsVO.setPriorBalanceMonthSix(dataSet.getDoubleValue(rowNum, 8));
    financialDetailsVO.setPriorBalanceMonthMore(dataSet.getDoubleValue(rowNum, 9));
    return financialDetailsVO;
  }
}