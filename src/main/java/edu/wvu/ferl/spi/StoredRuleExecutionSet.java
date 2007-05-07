/*
 * StoredRuleExecutionSet.java
 *
 * Created on May 5, 2007, 2:34 PM
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
public class StoredRuleExecutionSet {

  private String uri;
  private String name;
  private String description;
  private List<String> ruleUris;
  private Map<Object, Object> properties;
  
  /** Creates a new instance of StoredRuleExecutionSet */
  public StoredRuleExecutionSet() {
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

  public List<String> getRuleUris() {
    return ruleUris;
  }

  public void setRuleUris(List<String> ruleNames) {
    this.ruleUris = ruleNames;
  }

  public Map<Object, Object> getProperties() {
    return properties;
  }

  public void setProperties(Map<Object, Object> properties) {
    this.properties = properties;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
  
}
