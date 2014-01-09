package org.iq.startup;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.iq.amms.runners.BillGenerator;

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
  }

  /**
   * 
   */
  private void initializeServiceModule() {
    System.out.println("SERVICE MODULE INITIALIZED");
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