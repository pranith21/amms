package com.iq.amms.services.helpers;

import org.iq.exception.DbException;

import com.iq.amms.valueobjects.FinancialDetailsVO;

public class PaymentsHelper extends BaseHelper {
	
	public static Double updateFinancialBills(int flatId, Double amount){
	
		FinancialsHelper financialsHelper = new FinancialsHelper();
		FinancialDetailsVO financialDetailsVO = new FinancialDetailsVO();
		try {
			financialDetailsVO = financialsHelper.getFinancialDetails(flatId);
			
			if (financialDetailsVO != null){
				if (financialDetailsVO.getCurrentBalance() ==  amount) {
					financialDetailsVO.setCurrentBalance(new Double(0));
					financialDetailsVO.setPriorBalanceMonthOne(new Double(0));
					financialDetailsVO.setPriorBalanceMonthTwo(new Double(0));
					financialDetailsVO.setPriorBalanceMonthThree(new Double(0));
					financialDetailsVO.setPriorBalanceMonthFour(new Double(0));
					financialDetailsVO.setPriorBalanceMonthFive(new Double(0));
					financialDetailsVO.setPriorBalanceMonthMore(new Double(0));
				}				
				else{
					if (financialDetailsVO.getPriorBalanceMonthMore() > 0){
						if (financialDetailsVO.getPriorBalanceMonthMore() <= amount){
							financialDetailsVO.setPriorBalanceMonthMore(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthMore();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthMore(financialDetailsVO.getPriorBalanceMonthMore()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthMore();
						}
					}
					if (amount > 0 && financialDetailsVO.getPriorBalanceMonthFive() > 0){
						if (financialDetailsVO.getPriorBalanceMonthFive() <= amount){
							financialDetailsVO.setPriorBalanceMonthFive(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthFive();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthFive(financialDetailsVO.getPriorBalanceMonthFive()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthFive();
						}
					}
					if (amount > 0 && financialDetailsVO.getPriorBalanceMonthFour() > 0){
						if (financialDetailsVO.getPriorBalanceMonthFour() <= amount){
							financialDetailsVO.setPriorBalanceMonthFour(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthFour();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthFour(financialDetailsVO.getPriorBalanceMonthFour()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthFour();
						}
					}
					if (amount > 0 && financialDetailsVO.getPriorBalanceMonthThree() > 0){
						if (financialDetailsVO.getPriorBalanceMonthThree() <= amount){
							financialDetailsVO.setPriorBalanceMonthThree(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthThree();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthThree(financialDetailsVO.getPriorBalanceMonthThree()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthThree();
						}
					}
					if (amount > 0 && financialDetailsVO.getPriorBalanceMonthTwo() > 0){
						if (financialDetailsVO.getPriorBalanceMonthTwo() <= amount){
							financialDetailsVO.setPriorBalanceMonthTwo(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthTwo();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthTwo(financialDetailsVO.getPriorBalanceMonthTwo()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthTwo();
						}
					}
					if (amount > 0 && financialDetailsVO.getPriorBalanceMonthOne() > 0){
						if (financialDetailsVO.getPriorBalanceMonthOne() <= amount){
							financialDetailsVO.setPriorBalanceMonthOne(new Double(0));
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthOne();
						}
						else {
							financialDetailsVO.setPriorBalanceMonthOne(financialDetailsVO.getPriorBalanceMonthOne()-amount);					
							financialDetailsVO.setCurrentBalance(financialDetailsVO.getCurrentBalance() - amount);
							amount = amount - financialDetailsVO.getPriorBalanceMonthOne();
						}
					}
				}
				financialsHelper.updateFinancialDetails(financialDetailsVO);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return financialDetailsVO.getCurrentBalance();
	}
	
	public static void main(String[] args) {
		System.out.println(updateFinancialBills(101, new Double(4000)));
	}
}
