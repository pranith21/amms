package org.iq.processor;

import java.io.Serializable;

/**
 * @author Sam
 * 
 */
public abstract class BaseProcessor implements Cloneable, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1149215793029594278L;
  /**
   * a unique identifier for each session
   */
  protected String sessionTicket = null;

  /**
   * 
   */
  public BaseProcessor() {

  }

  /**
   * @param sessionTicket
   */
  public BaseProcessor(String sessionTicket) {
    this.sessionTicket = sessionTicket;
  }

  /**
   * @return the sessionTicket
   */
  public String getSessionTicket() {
    return sessionTicket;
  }

  /**
   * @param sessionTicket the sessionTicket to set
   */
  public void setSessionTicket(String sessionTicket) {
    this.sessionTicket = sessionTicket;
  }
}