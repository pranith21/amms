/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.setup;

import org.iq.exception.DbException;


/**
 * @author SC64807
 *
 */
public class Main {
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      DatabaseHandler databaseHandler = new DatabaseHandler();
      databaseHandler.create();
      System.out.println("Initial DatabaseHandler setup completed successfully.");
      System.out.println("----------------------------------------------------------------------");
      
      databaseHandler.importData();
      System.out.println("Data import to DatabaseHandler completed successfully.");
      System.out.println("----------------------------------------------------------------------");

    }
    catch (DbException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}