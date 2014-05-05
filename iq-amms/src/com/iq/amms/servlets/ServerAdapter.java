package com.iq.amms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iq.processor.RequestProcessor;
import org.iq.service.ServiceConstants;
import org.iq.util.string.StringUtil;

/**
 * 
 * @author Sam
 * 
 */
public class ServerAdapter extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 375299886649118252L;

  private static final String SESSION_TICKET_ATTR = "sessionTicket";

  private static final String ERROR_PAGE = "/error.jsp";

  private String sessionTicket = null;

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // System.out.println("doGet called");
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // TODO will be changing sessionTicket to sessionContext and getParameter
    // to getAttribute
    sessionTicket =
        StringUtil.getStringValue(request.getParameter(SESSION_TICKET_ATTR));
    try {
      Map<String, Object> resultMap =
          executeOperation(sessionTicket, prepareInputMap(request));
      // response.sendRedirect(getRedirectUrl(resultMap));

      prepareResultAttributes(request, getResultAttributes(resultMap));

//      System.out.println(getServletContext().getServerInfo());
//      System.out.println(getServletContext().getContextPath());
      System.out.println("Redirect Url: "+getRedirectUrl(resultMap));
      System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

      RequestDispatcher requestDispatcher =
          getServletContext()
              .getRequestDispatcher(getRedirectUrl(resultMap));
      requestDispatcher.forward(request, response);
    }
    catch (Throwable e) {
      
      request.setAttribute("errorMessage", e.getMessage());
      request.setAttribute("stackTrace", getStackTrace(e));
      
      RequestDispatcher requestDispatcher =
          getServletContext().getRequestDispatcher(ERROR_PAGE);
      requestDispatcher.forward(request, response);
    }
  }
  
  public String getStackTrace(Throwable e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String stackTrace = sw.toString(); // stack trace as a string
    pw.close();

    try {
      sw.close();
    }
    catch (IOException e1) { /* need not do anything here */
    }
    return stackTrace;
  }

  /**
   * @param request
   * @param resultAttributes
   */
  private void prepareResultAttributes(HttpServletRequest request,
      Map<String, Object> resultAttributes) {
    Set<String> paramNames = resultAttributes.keySet();
    for (String paramName : paramNames) {
      request.setAttribute(paramName, resultAttributes.get(paramName));
    }
  }

  /**
   * @param resultMap
   * @return String
   */
  private String getRedirectUrl(Map<String, Object> resultMap) {
    return resultMap != null ? StringUtil.getStringValue(resultMap
        .get(ServiceConstants.REDIRECT_URL)) : "NOT_FOUND_PAGE";
  }

  /**
   * @param resultMap
   * @return Map<String, Object>
   */
  private Map<String, Object> getResultAttributes(
      Map<String, Object> resultMap) {
    return resultMap != null ? resultMap
        .get(ServiceConstants.RESULT_ATTRIBUTES) != null ? (Map<String, Object>)resultMap
        .get(ServiceConstants.RESULT_ATTRIBUTES)
        : null
        : null;
  }

  /**
   * @param request
   * @return
   */
  private Map<String, Object> prepareInputMap(HttpServletRequest request) {
    Map<String, Object> inputMap = new HashMap<String, Object>();
    Map reqMap = request.getParameterMap();
    Set paramNames = reqMap.keySet();
    for (Object paramName : paramNames) {
      String[] paramValues = (String[])reqMap.get(paramName);
      inputMap.put((String)paramName,
          paramValues.length == 1 ? paramValues[0] : paramValues);
    }
    return inputMap;
  }

  private Map<String, Object> executeOperation(String sessionTicketInput,
      Map<String, Object> input) throws Throwable {
    RequestProcessor processor = new RequestProcessor(sessionTicketInput);
    return processor.executeService(input);
  }
}