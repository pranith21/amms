package org.iq.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.iq.service.BaseService;
import org.iq.util.string.StringUtil;

public class RequestProcessor extends BaseProcessor {

  /**
   * 
   */
  private static final long serialVersionUID = -6193888847884264953L;
  private static final String REQUESTED_SERVICE_NAME_KEY = "ServiceName";

  /**
   * a unique identifier for each request
   */
  protected String requestId = null;

  /**
   * 
   */
  public RequestProcessor() {
	super();
	requestId = "R-" + UUID.randomUUID().toString();
  }

  /**
   * @param sessionTicket
   */
  public RequestProcessor(String sessionTicket) {
	super(sessionTicket);
	requestId = "R-" + UUID.randomUUID().toString();
  }

  /**
   * @return the requestId
   */
  public String getRequestId() {
	return requestId;
  }

  /**
   * @param requestId
   *          the requestId to set
   */
  public void setRequestId(String requestId) {
	this.requestId = requestId;
  }

  /**
   * @param input
   * @throws Exception
   */
  public Map<String, Object> executeService(Map<String, Object> input)
	  throws Exception {
	String requestedServiceName = StringUtil.getStringValue(input
		.get(REQUESTED_SERVICE_NAME_KEY));
	System.out
		.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
	System.out.println(REQUESTED_SERVICE_NAME_KEY + " = "
		+ requestedServiceName);

	Class serviceClass = Class
		.forName(getServiceClassName(requestedServiceName));
	BaseService service = (BaseService) serviceClass.newInstance();

	Set<String> paramNames = input.keySet();
	for (String paramName : paramNames) {
	  System.out.println("Param Name:" + paramName + " and Param Value:"
		  + input.get(paramName));
	}

	HashMap<String, Object> resultMap = service
		.executeService((HashMap<String, Object>) input);

	return resultMap;
  }

  /**
   * 
   */
  private String getServiceClassName(String requestedServiceName) {
	return "com.iq.amms.services." + requestedServiceName;
  }
}