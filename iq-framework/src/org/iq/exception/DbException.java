/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.exception;

/**
 * @author
 * 
 */
public class DbException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = -7135422052446869712L;

  /**
   * 
   */
  public DbException() {
	super();
  }

  /**
   * @param msg
   */
  public DbException(String msg) {
	super(msg);
  }

  /**
   * @param msg
   */
  public DbException(Throwable th) {
	super(th);
  }

  /**
   * @param msg
   * @param th
   */
  public DbException(String msg, Throwable th) {
	super(msg, th);
  }
}