/*
 * Copyright 2008 West Virginia University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/*
 * RuleDetailedDescriptor.java
 *
 * Created on May 7, 2007, 11:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.impl.StoredRuleImpl;

import java.util.HashMap;
import java.util.Map;
import javax.rules.ConfigurationException;

import org.apache.commons.lang.StringUtils;

/**
 * @author jbunting
 */
class RuleDetailedDescriptor implements RuleDescriptor {

  private String uri;
  private String name;
  private String description;
  private String language;
  private String script;
  private Map properties = new HashMap();

  /**
   * Creates a new instance of RuleDetailedDescriptor
   */
  public RuleDetailedDescriptor(String uri) {
    this.setUri(uri);
  }

  public String generateRule(RuleStore ruleStore) throws ConfigurationException {

    this.checkForNull(getUri(), "uri");
    this.checkForNull(getName(), "name");
    this.checkForNull(getDescription(), "description");
    this.checkForNull(getLanguage(), "language");
    this.checkForNull(getScript(), "script");

    StoredRule storedRule = new StoredRuleImpl(getUri(),
            getName(),
            getDescription(),
            getLanguage(),
            getScript(),
            properties);
    ruleStore.storeRule(storedRule);
    return this.getUri();
  }

  public String getUri() {
    return uri;
  }

  private void setUri(String uri) {
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

  private void checkForNull(String input, String field) throws ConfigurationException {
    if(StringUtils.isBlank(input)) {
      throw new ConfigurationException(field + " cannot be empty...");
    }
  }

  public Object getProperty(Object key) {
    return this.properties.get(key);
  }

  public void setProperty(Object key, Object value) {
    this.properties.put(key, value);
  }
}
