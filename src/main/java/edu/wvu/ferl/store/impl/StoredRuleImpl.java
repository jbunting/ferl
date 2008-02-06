/*
 * StoredRuleImpl.java
 *
 * Created on June 28, 2007, 9:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public class StoredRuleImpl implements StoredRule {
  
  private String uri;
  private String name;
  private String description;
  private String language;
  private String script;
  private Map<Object, Object> properties;

  /** Creates a new instance of StoredRule */
  public StoredRuleImpl(String uri, String name, String description, String language, String script, Map<Object, Object> properties) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setLanguage(language);
    this.setScript(script);
    this.setProperties(new HashMap(properties));
  }
  
  public StoredRuleImpl(StoredRule storedRule) {
    this(storedRule.getUri(),
         storedRule.getName(),
         storedRule.getDescription(),
         storedRule.getLanguage(),
         storedRule.getScript(),
         storedRule.getProperties());
  }

  public String getLanguage() {
    return language;
  }

  protected void setLanguage(String language) {
    this.language = language;
  }

  public String getScript() {
    return script;
  }

  protected void setScript(String script) {
    this.script = script;
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

  public String getUri() {
    return uri;
  }

  protected void setUri(String uri) {
    this.uri = uri;
  }

  public Map<Object, Object> getProperties() {
    return properties;
  }

  protected void setProperties(Map<Object, Object> properties) {
    this.properties = Collections.unmodifiableMap(properties);
  }
  
}
