package com.iq.amms.setup.temp;

import java.io.FileReader;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

import com.iq.amms.services.PayBill;
import com.iq.amms.services.helpers.FlatsHelper;
import com.iq.amms.valueobjects.FlatDetailsVO;

public class UpdatePayments {

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    CSVReader reader = new CSVReader(new FileReader("D:/Book1.csv"));
    String[] nextLine = reader.readNext();
    while ((nextLine = reader.readNext()) != null) {
      String block = "Block " + nextLine[0];
      String flatNumber = nextLine[1];
      String paidAmount = nextLine[2];
      FlatsHelper flatsHelper = new FlatsHelper();
      System.out.println(block + " " + flatNumber);
      FlatDetailsVO flatDetailsVO =
          flatsHelper.getFlatDetails(block, flatNumber);
      if (flatDetailsVO != null && Double.valueOf(paidAmount) > 0) {
        PayBill bill = new PayBill();
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("flatNumber", flatDetailsVO.getFlatId());
        input.put("paidAmount", paidAmount);
        input.put("paymentMode", 0);
        bill.execute(input);
      }
      // break;
    }
    reader.close();
  }
}
