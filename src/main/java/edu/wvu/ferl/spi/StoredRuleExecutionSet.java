/*
 * StoredRuleExecutionSet.java
 *
 * Created on June 28, 2007, 9:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.spi;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public interface StoredRuleExecutionSet {
  public String getDefaultObjectFilter();

  public String getDescription();

  public String getName();

  public Map<Object, Object> getProperties();

  public List<String> getRuleUris();

  public String getUri();
  
}
