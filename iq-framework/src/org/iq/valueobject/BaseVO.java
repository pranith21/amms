/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 **/

package org.iq.valueobject;

import java.io.Serializable;

/**
 * @author SC64807
 * 
 */
public abstract class BaseVO implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 4482026828500251952L;

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public abstract String toString();
}
