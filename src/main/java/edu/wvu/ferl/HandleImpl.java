/*
 * HandleImpl.java
 *
 * Created on May 5, 2007, 4:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import javax.rules.Handle;

/**
 *
 * @author jbunting
 */
public class HandleImpl implements Handle {
  
  private Object object;

  public HandleImpl(Object object) {
    this.object = object;
  }
 
  public boolean equals(Object other) {
    return object == other;
  }

  public int hashCode() {
    return object.hashCode() * 3;
  }
  
}
