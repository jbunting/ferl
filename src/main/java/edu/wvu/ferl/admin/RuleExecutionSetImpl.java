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

/**
 *
 * @author jbunting
 */
public class RuleExecutionSetImpl {
  
  private String uri;
  private String name;
  private String description;

  private List<RuleDescriptor> ruleDescriptors = new ArrayList<RuleDescriptor>();
  private Map<Object, Object> properties;
  
  /** Creates a new instance of RuleExecutionSetImpl */
  public RuleExecutionSetImpl(String uri) {
    this.uri = uri;
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

  public Map<Object, Object> getProperties() {
    return properties;
  }

  public void setProperties(Map<Object, Object> properties) {
    this.properties = properties;
  }
  
}
