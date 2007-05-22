/*
 * StoredRule.java
 *
 * Created on May 5, 2007, 2:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.spi;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public class StoredRule {
  
  private String uri;
  private String name;
  private String description;
  private String language;
  private String script;
  private Map<Object, Object> properties;
  private Map<Object, Object> unmodifiableProperties;
  
  private boolean allowCollectionsModifications = false;

  /** Creates a new instance of StoredRule */
  public StoredRule(String uri, String name, String description, String language, String script, Map<Object, Object> properties) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setLanguage(language);
    this.setScript(script);
    this.setProperties(properties);
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
  
}
