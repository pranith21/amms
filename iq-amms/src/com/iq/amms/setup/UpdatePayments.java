package com.iq.amms.setup;

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
		CSVReader reader = new CSVReader(new FileReader("E:/Book1.csv"));
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			String block = "Block "+nextLine[0];
			String flatNumber =nextLine[1];
			FlatsHelper flatsHelper = new FlatsHelper();
			System.out.println(block);
			System.out.println(flatNumber);
			FlatDetailsVO flatDetailsVO =  flatsHelper.getFlatDetails(block, flatNumber);
			if (flatDetailsVO != null) {
				PayBill bill = new PayBill();
				HashMap<String, Object> input = new HashMap<String, Object>();
				input.put("flatNumber", flatDetailsVO.getFlatId());
				input.put("paidAmount", nextLine[2]);
				input.put("paymentMode", 0);
				bill.execute(input);
			}
			break;
		}
		reader.close();
	}
}
