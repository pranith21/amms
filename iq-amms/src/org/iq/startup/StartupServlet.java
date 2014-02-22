package org.iq.startup;

import java.io.IOException;
import java.util.Date;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.date.DateUtil.DatePart;

import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.SystemHelper;

/**
 * @author Sam
 */
public class StartupServlet extends GenericServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 375299886649118252L;

//  private static final String INITIALIZE_SERVICE = "ServiceModule";
//  private static final String INITIALIZE_CSV_DATA_MODULE = "CsvDataModule";

//  private ServletContext servletContext = null;
//  private String applicationRoot = null;

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#init()
   */
  @Override
  public void init() throws ServletException {
    System.out.println("STARTING AMMS");
//    servletContext = getServletContext();
//    applicationRoot = servletContext.getRealPath("/");

    super.init();

    SystemHelper systemHelper = new SystemHelper();
    BillHelper billHelper = new BillHelper();
    try {
      // Calculating late payments
      System.out.println("STARTING LATE PAYMENTS PROCESSING...");
      if (!systemHelper.checkLatePaymentProcessed()) {
        Date dueDate = systemHelper.getLatePaymentDueDate();
        Date today =
          DateUtil.stringToDate(
              DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
              DateFormat.MMM_dd_yyyy);
        int i = dueDate.compareTo(today);
        if (i < 0) {
        	systemHelper.createBackUp();
          billHelper.processLatePayments();
          systemHelper.updateLatePaymentProcessed(1);
          System.out.println("LATE PAYMENTS PROCESSING DONE.");
        }
        else {
          System.out.println("LATE PAYMENTS PROCESSING NOT DONE.");
        }
      }
      else {
        System.out.println("LATE PAYMENTS PROCESSING NOT DONE.");
      }

      // Generating Bill
      System.out.println("STARTING BILL GENERATION...");
      Date nextBillDate = systemHelper.getNextBillDate();
      Date today =
          DateUtil.stringToDate(
              DateUtil.dateToString(new Date(), DateFormat.MMM_dd_yyyy),
              DateFormat.MMM_dd_yyyy);
      int i = nextBillDate.compareTo(today);
      if (i <= 0) {
      	systemHelper.createBackUp();
        billHelper.generateInvoices(today);
        systemHelper.updateNextBillDate(DateUtil.addToDate(today, 1,
            DatePart.MONTH));
        systemHelper.updateLatePaymentDueDate(DateUtil.addToDate(today, 14));
        systemHelper.updateLatePaymentProcessed(0);
        System.out.println("BILL GENERATION DONE.");
      }
      else {
        System.out.println("BILL GENERATION NOT DONE.");
      }
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    systemHelper.uploadBackupFiles();
    System.out.println("APPLICATION STARTED SUCCESSFULLY");
  }
  

  /**
   * 
   */
  /*private void initializeServiceModule() {
    System.out.println("SERVICE MODULE INITIALIZED");
  }*/

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse)
   */
  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#destroy()
   */
  @Override
  public void destroy() {
    super.destroy();
  }
}