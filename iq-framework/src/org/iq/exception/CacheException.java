/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.exception;

/**
 * @author
 * 
 */
public class CacheException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = -7135422052446869712L;

  /**
   * 
   */
  public CacheException() {

  }

  /**
   * @param msg
   * @param th
   */
  public CacheException(String msg, Throwable th) {
	super(msg, th);
  }

  /**
   * @param msg
   */
  public CacheException(String msg) {
	super(msg);
  }
}