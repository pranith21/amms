package org.iq.exception;

/**
 * @author Sam
 * 
 */
public class UtilityException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = -1102568361486069986L;

  /**
   * 
   */
  // private String msgId = null;

  public UtilityException(String message, Throwable th) {
	super(getErrorMessage(message, th), th);
  }
}