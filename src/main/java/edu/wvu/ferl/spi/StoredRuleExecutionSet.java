/*
 * StoredRuleExecutionSet.java
 *
 * Created on May 5, 2007, 2:34 PM
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
public class StoredRuleExecutionSet {

  private String uri;
  private String name;
  private String description;
  private List<String> ruleUris;
  private List<String> unmodifiableRuleUris;
  private Map<Object, Object> properties;
  private Map<Object, Object> unmodifiableProperties;
  private String defaultObjectFilter;
  
  private boolean allowCollectionsModifications = false;
  
  /** Creates a new instance of StoredRuleExecutionSet */
  protected StoredRuleExecutionSet() {
  }
  
  public StoredRuleExecutionSet(String uri, String name, String description, List<String> ruleUris, Map<Object, Object> properties, String defaultObjectFilter) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setRuleUris(new ArrayList(ruleUris));
    this.setProperties(new HashMap(properties));
    this.setDefaultObjectFilter(defaultObjectFilter);
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
    if(allowCollectionsModifications) {
      return ruleUris;
    } else {
      return unmodifiableRuleUris;
    }
  }
  
  protected void setRuleUris(List<String> ruleNames) {
    this.ruleUris = ruleNames;
    this.unmodifiableRuleUris = Collections.unmodifiableList(ruleNames);
  }

  public Map<Object, Object> getProperties() {
    if(allowCollectionsModifications) {
      return properties;
    } else {
      return unmodifiableProperties;
    }
  }

  protected void setProperties(Map<Object, Object> properties) {
    this.properties = properties;
    this.unmodifiableProperties = Collections.unmodifiableMap(properties);
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
