package com.iq.amms.runners;

import com.iq.amms.services.helpers.BillHelper;


/**
 * @author Sam
 * 
 */
public class BillGenerator implements Runnable {

  private static BillGenerator generatorInstance;
  private Thread runner;

  /**
   * 
   */
  private BillGenerator() {
    runner = new Thread(this, "Bill Generator Thread");
    runner.start();
  }

  public static void generate() {
    if (generatorInstance == null) {
      generatorInstance = new BillGenerator();
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
    try {
        
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}