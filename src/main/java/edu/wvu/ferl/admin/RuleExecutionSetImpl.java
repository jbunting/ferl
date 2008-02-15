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
 * RuleExecutionSetImpl.java
 *
 * Created on May 7, 2007, 10:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.rules.admin.RuleExecutionSet;

/**
 * An implementation of {@code RuleExecutionSet} used for storing information between the load and the register
 * operations.
 *
 * @author jbunting
 */
class RuleExecutionSetImpl implements RuleExecutionSet {

  private String uri;
  private String name;
  private String description;
  private String defaultObjectFilter;

  private List<RuleDescriptor> ruleDescriptors = new ArrayList<RuleDescriptor>();
  private Map<Object, Object> properties = new HashMap<Object, Object>();

  /**
   * Creates a new instance with the given {@code uri}.
   *
   * @param uri the uri
   */
  public RuleExecutionSetImpl(String uri) {
    this.setUri(uri);
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
   * Gets the list of all rule descriptors.
   *
   * @return the list of rule descriptors
   */
  public List<RuleDescriptor> getRuleDescriptors() {
    return Collections.unmodifiableList(ruleDescriptors);
  }

  /**
   * Adds a rule descriptor to the current list
   *
   * @param ruleDescriptor the rule descriptor to add
   */
  public void addRuleDescriptor(RuleDescriptor ruleDescriptor) {
    ruleDescriptors.add(ruleDescriptor);
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

  /**
   * Adds all of the properties from {@code map}.
   *
   * @param map the map
   */
  public void addAllProperties(Map<?, ?> map) {
    this.properties.putAll(map);
  }

  /**
   * Gets a map of all properties set on this {@code RuleExecutionSet}.
   *
   * @return the map of properties
   */
  public Map<Object, Object> getProperties() {
    return Collections.unmodifiableMap(properties);
  }

  /**
   * {@inheritDoc}
   *
   * @param defaultObjectFilter {@inheritDoc}
   */
  public void setDefaultObjectFilter(String defaultObjectFilter) {
    this.defaultObjectFilter = defaultObjectFilter;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getDefaultObjectFilter() {
    return defaultObjectFilter;
  }

  /**
   * Gets a list of all rules attached to this {@code RuleExecutionSet}.
   *
   * @return the list of rules
   */
  public List<RuleDescriptor> getRules() {
    return Collections.unmodifiableList(ruleDescriptors);
  }

}
