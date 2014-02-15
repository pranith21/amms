package com.iq.amms.valueobjects;

import java.util.ArrayList;

/**
 * @author Sam
 */
public class ReportDataVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 6423086160896023298L;

  private String tableHeader = null;
  private String[] headers = null;
  private ArrayList<String[]> data = null;


  /**
   * @return the tableHeader
   */
  public String getTableHeader() {
    return tableHeader;
  }


  /**
   * @param tableHeader the tableHeader to set
   */
  public void setTableHeader(String tableHeader) {
    this.tableHeader = tableHeader;
  }


  /**
   * @return the headers
   */
  public String[] getHeaders() {
    return headers;
  }


  /**
   * @param headers the headers to set
   */
  public void setHeaders(String[] headers) {
    this.headers = headers;
  }


  /**
   * @return the data
   */
  public ArrayList<String[]> getData() {
    return data;
  }


  /**
   * @param data the data to set
   */
  public void setData(ArrayList<String[]> data) {
    this.data = data;
  }


  /*
   * (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }
}