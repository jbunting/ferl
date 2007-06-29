/*
 * ObjectFilterTransformer.java
 *
 * Created on May 5, 2007, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import javax.rules.ObjectFilter;
import org.apache.commons.collections.Transformer;

/**
 *
 * @author jbunting
 */
public class ObjectFilterTransformer implements Transformer {
  
  private ObjectFilter objectFilter;
  
  /** Creates a new instance of ObjectFilterTransformer */
  public ObjectFilterTransformer(ObjectFilter objectFilter) {
    this.objectFilter = objectFilter;
  }

  public Object transform(Object object) {
    if(objectFilter != null) {
      return objectFilter.filter(object);
    } else {
      return object;
    }
  }
  
  
  
}
