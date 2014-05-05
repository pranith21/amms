package org.iq.exception;

/**
 * @author Sam
 * 
 */
public class DataUploaderException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = 3559581725571092959L;

  public DataUploaderException(String message, Throwable th) {
	super(getErrorMessage(message, th), th);
  }
}