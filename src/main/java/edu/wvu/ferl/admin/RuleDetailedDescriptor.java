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
 * Implementation of {@code RuleDescriptor}.
 *
 * @author jbunting
 */
class RuleDetailedDescriptor implements RuleDescriptor {

  private String uri;
  private String name;
  private String description;
  private String language;
  private String script;
  private Map<Object, Object> properties = new HashMap<Object, Object>();

  /**
   * Creates a new instance with the give {@code uri}.
   *
   * @param uri the uri of the new rule
   */
  public RuleDetailedDescriptor(String uri) {
    this.setUri(uri);
  }

  /**
   * Checks that all of the properties have been set, and stores the rule in the provided {@code RuleStore}.
   *
   * @param ruleStore {@inheritDoc}
   * @return the uri of the generated rule
   * @throws ConfigurationException {@inheritDoc}
   */
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

  /**
   * Gets the uri.
   *
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  private void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the language.
   *
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets the language.
   *
   * @param language the language
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Gets the script.
   *
   * @return the script
   */
  public String getScript() {
    return script;
  }

  /**
   * Sets the script.
   *
   * @param script the script
   */
  public void setScript(String script) {
    this.script = script;
  }

  /**
   * Checks if the input is null, if so throws a {@code ConfigurationException} using {@code field} in the message.
   *
   * @param input the value to check
   * @param field the field the value came from
   * @throws ConfigurationException if the input value is null
   */
  private void checkForNull(String input, String field) throws ConfigurationException {
    if(StringUtils.isBlank(input)) {
      throw new ConfigurationException(field + " cannot be empty...");
    }
  }

  /**
   * Gets the property specified by {@code key}.
   *
   * @param key the key of the property to get
   * @return the value of the property
   */
  public Object getProperty(Object key) {
    return this.properties.get(key);
  }

  /**
   * Sets the property specified by {@code key} to {@code value}.
   *
   * @param key   the key of the property to set
   * @param value the value of the property
   */
  public void setProperty(Object key, Object value) {
    this.properties.put(key, value);
  }
}
