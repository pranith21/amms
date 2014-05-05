package org.iq.exception;

public class BaseException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -7381248083824421443L;

  /**
   * 
   */
  public BaseException() {
	super();
  }

  /**
   * @param msg
   */
  public BaseException(String msg) {
	super(msg);
  }

  /**
   * @param th
   */
  public BaseException(Throwable th) {
	super(th);
  }

  /**
   * @param msg
   * @param th
   */
  public BaseException(String msg, Throwable th) {
	super(msg, th);
  }

  /**
   * @param th
   * @return String
   */
  protected static final String getErrorMessage(String message, Throwable th) {
	return message != null ? message + " : "
		: "" + th.getMessage() != null ? th.getMessage() : "";
  }
}