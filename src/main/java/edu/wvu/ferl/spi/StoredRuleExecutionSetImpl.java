/*
 * StoredRuleExecutionSetImpl.java
 *
 * Created on June 28, 2007, 9:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public class StoredRuleExecutionSetImpl implements StoredRuleExecutionSet {
  
  private String uri;
  private String name;
  private String description;
  private List<String> ruleUris;
  private Map<Object, Object> properties;
  private String defaultObjectFilter;
  
  public StoredRuleExecutionSetImpl(String uri, String name, String description, List<String> ruleUris, Map<Object, Object> properties, String defaultObjectFilter) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setRuleUris(new ArrayList(ruleUris));
    this.setProperties(new HashMap(properties));
    this.setDefaultObjectFilter(defaultObjectFilter);
  }
  
  public StoredRuleExecutionSetImpl(StoredRuleExecutionSet storedRuleExecutionSet) {
    this(storedRuleExecutionSet.getUri(),
         storedRuleExecutionSet.getName(),
         storedRuleExecutionSet.getDescription(),
         storedRuleExecutionSet.getRuleUris(),
         storedRuleExecutionSet.getProperties(),
         storedRuleExecutionSet.getDefaultObjectFilter());
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  protected void setDescription(String description) {
    this.description = description;
  }

  public List<String> getRuleUris() {
    return ruleUris;
  }
  
  protected void setRuleUris(List<String> ruleNames) {
    this.ruleUris = Collections.unmodifiableList(ruleNames);
  }

  public Map<Object, Object> getProperties() {
    return properties;
  }

  protected void setProperties(Map<Object, Object> properties) {
    this.properties = Collections.unmodifiableMap(properties);
  }

  public String getUri() {
    return uri;
  }

  protected void setUri(String uri) {
    this.uri = uri;
  }

  public String getDefaultObjectFilter() {
    return defaultObjectFilter;
  }

  protected void setDefaultObjectFilter(String defaultObjectFilter) {
    this.defaultObjectFilter = defaultObjectFilter;
  }
  
}
