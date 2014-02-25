/**
 * 
 */
package com.iq.amms.services;

import java.util.HashMap;

import org.iq.service.BaseService;

/**
 * @author Sam
 *
 */
public class ExportPDFReport extends BaseService {
	
	private static String REPORT_DATA = "reportDataFileName";

	/* (non-Javadoc)
	 * @see org.iq.service.BaseService#execute(java.util.HashMap)
	 */
	@Override
	public void execute(HashMap<String, Object> input) throws Exception {

		String fileName = input.get(REPORT_DATA).toString();
		
	    redirectUrl = fileName;
	}

}
