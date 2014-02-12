package com.iq.amms.setup.temp;

import java.util.ArrayList;

import com.iq.amms.services.helpers.FinancialsHelper;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.valueobjects.FinancialDetailsVO;
import com.iq.amms.valueobjects.FlatDetailsVO;

public class TempFixForPriorBalance {

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    

    /*MySqlSession mySqlSession =
      new MySqlSession(SystemConf.getDbHost(), SystemConf.getDbPort(),
          DatabaseHandler.DB_NAME, SystemConf.getDbUser(), SystemConf
              .getDbPass());*/
    
    FlatsHelper flatsHelper = new FlatsHelper();
    ArrayList<FlatDetailsVO> allFlats = flatsHelper.getAllFlats();
    
    for (FlatDetailsVO flatDetailsVO : allFlats) {
      FinancialsHelper financialsHelper = new FinancialsHelper();
      FinancialDetailsVO financialDetailsVO = financialsHelper.getFinancialDetails(flatDetailsVO.getFlatId());
      System.out.print("Existing: "+ flatDetailsVO.getFullFlatNumber()+" ");
      System.out.print(financialDetailsVO.getCurrentBalance()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthOne()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthTwo()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthThree()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthFour()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthFive()+" ");
      System.out.print(financialDetailsVO.getPriorBalanceMonthSix()+" ");
      System.out.println(financialDetailsVO.getPriorBalanceMonthMore());

      /*FinancialDetailsVO newFincData = new FinancialDetailsVO();

      newFincData.setFlatId(financialDetailsVO.getFlatId());

      newFincData.setPriorBalanceMonthMore(financialDetailsVO
          .getPriorBalanceMonthMore()
          + financialDetailsVO.getPriorBalanceMonthSix());

      newFincData.setPriorBalanceMonthSix(financialDetailsVO.getPriorBalanceMonthFive());
      newFincData.setPriorBalanceMonthFive(financialDetailsVO.getPriorBalanceMonthFour());
      newFincData.setPriorBalanceMonthFour(financialDetailsVO.getPriorBalanceMonthThree());
      newFincData.setPriorBalanceMonthThree(financialDetailsVO.getPriorBalanceMonthTwo());
      newFincData.setPriorBalanceMonthTwo(financialDetailsVO.getPriorBalanceMonthOne());
      newFincData.setPriorBalanceMonthOne(financialDetailsVO.getCurrentBalance() - financialDetailsVO.getPriorBalanceMonthOne());
      
      financialsHelper.updateFinancialDetails(newFincData);*/
    }
    
  }
}
