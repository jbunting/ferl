/*
 * StoredRule.java
 *
 * Created on June 28, 2007, 9:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.store;

import java.util.Map;

/**
 *
 * @author jbunting
 */
public interface StoredRule {
  public String getDescription();

  public String getLanguage();

  public String getName();

  public Map<Object, Object> getProperties();

  public String getScript();

  public String getUri();
  
}
