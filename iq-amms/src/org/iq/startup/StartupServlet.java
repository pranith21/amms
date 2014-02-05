package org.iq.startup;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.iq.amms.services.helpers.BillHelper;
import com.iq.amms.services.helpers.SystemHelper;

/**
 * 
 * @author Sam
 * 
 */
public class StartupServlet extends GenericServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 375299886649118252L;
  
  private static final String INITIALIZE_SERVICE = "ServiceModule";
  private static final String INITIALIZE_CSV_DATA_MODULE = "CsvDataModule";

  private ServletContext servletContext = null;
  private String applicationRoot = null;
  
  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.GenericServlet#init()
   */
  @Override
  public void init() throws ServletException {
    System.out.println("STARTING AMMS");
    servletContext = getServletContext();
    applicationRoot = servletContext.getRealPath("/");

    Boolean initializeService = Boolean.valueOf(getServletConfig().getInitParameter(INITIALIZE_SERVICE));
    
    if (initializeService) {
      initializeServiceModule();
    }
    
    super.init();
    System.out.println("APPLICATION STARTED SUCCESSFULLY");
    
    generateBill();
    
  }

  /**
   * 
   */
  private void initializeServiceModule() {
    System.out.println("SERVICE MODULE INITIALIZED");
  }
  
  private void generateBill(){
	  SystemHelper systemHelper = new SystemHelper();
	  try {
		  Date startDate = new Date(systemHelper.getLastBillDate());
		  Date endDate = new Date();
		  Calendar startCalendar = new GregorianCalendar();
		  startCalendar.setTime(startDate);
		  Calendar endCalendar = new GregorianCalendar();
		  endCalendar.setTime(endDate);

		  int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		  int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);		  
		  BillHelper billHelper = new BillHelper();
		  for (int i = 1; i < diffMonth; i++) {
			  startCalendar.set(Calendar.MONTH, startCalendar.get(Calendar.MONTH)+i);
			  billHelper.generateInvoices(startCalendar.getTime());
		}
	  } catch (Exception e) {
		  System.out.println("Error Generating Bill....");
		  e.printStackTrace();
	  }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest,
   *      javax.servlet.ServletResponse)
   */
  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.GenericServlet#destroy()
   */
  @Override
  public void destroy() {
    // TODO Auto-generated method stub
    super.destroy();
  }
}