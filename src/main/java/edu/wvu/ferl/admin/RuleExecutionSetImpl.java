/*
 * RuleExecutionSetImpl.java
 *
 * Created on May 7, 2007, 10:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.rules.admin.RuleExecutionSet;

/**
 *
 * @author jbunting
 */
public class RuleExecutionSetImpl implements RuleExecutionSet {
  
  private String uri;
  private String name;
  private String description;

  private List<RuleDescriptor> ruleDescriptors = new ArrayList<RuleDescriptor>();
  private Map<Object, Object> properties;
  
  /** Creates a new instance of RuleExecutionSetImpl */
  public RuleExecutionSetImpl(String uri) {
    this.uri = uri;
  }
  
  public String getUri() {
    return uri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<RuleDescriptor> getRuleDescriptors() {
    return ruleDescriptors;
  }

  public Object getProperty(Object key) {
    return this.properties.get(key);
  }

  public void setProperty(Object key, Object value) {
    this.properties.put(key, value);
  }

  public void setDefaultObjectFilter(String string) {
  }

  public String getDefaultObjectFilter() {
    return null;
  }

  public List getRules() {
    return null;
  }
  
}
