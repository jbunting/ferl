/*
 * StoredRule.java
 *
 * Created on May 5, 2007, 2:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.spi;

import java.util.Map;
import javax.script.CompiledScript;

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
  
  /** Creates a new instance of StoredRule */
  public StoredRule() {
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
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

  public Object getProperty(Object key) {
    return properties.get(key);
  }

  public void setProperty(Object key, Object value) {
    this.properties.put(key, value);
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
  
}
