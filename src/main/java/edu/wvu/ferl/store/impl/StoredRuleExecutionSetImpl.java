/**
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

package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple implementation of {@code StoredRuleExecutionSet}.  This implementation is read-only, and provides two
 * constructors - one where the client will provide all of the parameters, and a convenience constructor for defensive
 * copying.
 * <p/>
 * This implementation may be subclassed for the purposes of adding functionality.
 * <p/>
 * Note that {@code protected} setters are provided for subclasses that may, for some reason, wish to modify or extend
 * the set mechanism.  It is still recommended that subclasses maintain the strict read-only nature of this class in
 * its public api.
 * <p/>
 * Date: June 28, 2007
 * Time: 9:34 PM
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

  /**
   * Creates a new instance of {@code StoredRuleExecutionSetImpl}.  Note that the properties are copied prior to
   * storing so that any modifications to the passed in map do not have any effect on this object.
   *
   * @param uri                 the uri of the new rule execution set
   * @param name                the name of the new rule execution set
   * @param description         the description of the new rule execution set
   * @param ruleUris            the list of rule uris of the new rule execution set
   * @param properties          the properties of the new rule execution set
   * @param defaultObjectFilter the default object filter of the new rule execution set
   */
  public StoredRuleExecutionSetImpl(String uri,
                                    String name,
                                    String description,
                                    List<String> ruleUris,
                                    Map<Object, Object> properties,
                                    String defaultObjectFilter) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setRuleUris(ruleUris);
    this.setProperties(properties);
    this.setDefaultObjectFilter(defaultObjectFilter);
  }

  /**
   * Convenience constructor for creating a defensive copy.  This constructor essentially invokes
   * {@link #StoredRuleExecutionSetImpl(String, String, String, java.util.List, java.util.Map, String)} with the values
   * returned from the getters of the {@code storedRuleExecutionSet} passed in.
   *
   * @param storedRuleExecutionSet the stored rule exection set to copy
   */
  public StoredRuleExecutionSetImpl(StoredRuleExecutionSet storedRuleExecutionSet) {
    this(storedRuleExecutionSet.getUri(),
            storedRuleExecutionSet.getName(),
            storedRuleExecutionSet.getDescription(),
            storedRuleExecutionSet.getRuleUris(),
            storedRuleExecutionSet.getProperties(),
            storedRuleExecutionSet.getDefaultObjectFilter());
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getName() {
    return name;
  }

  /**
   * A setter for the name.  Only present for use or overriding by subclasses.
   *
   * @param name the name to set
   */
  protected void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getDescription() {
    return description;
  }

  /**
   * A setter for the description.  Only present for use or overriding by subclasses.
   *
   * @param description the description to set
   */
  protected void setDescription(String description) {
    this.description = description;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public List<String> getRuleUris() {
    return ruleUris;
  }

  /**
   * Sets the rule uris list.  Note that a defensive copy is made and then wrapped with an unmodifiable map.  Hence,
   * modifications to the map passed in will not have any effect on the map stored in this class.
   *
   * @param ruleUris the rule uris to be set
   */
  protected void setRuleUris(List<String> ruleUris) {
    // here we create an unmodifiable map because these properties should never be modified.  We are also creating a
    // defensive copy first so that the properties cannot be modified out from underneath us by the calling class.
    this.ruleUris = Collections.unmodifiableList(new ArrayList<String>(ruleUris));
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public Map<Object, Object> getProperties() {
    return properties;
  }

  /**
   * Sets the properties map.  Note that a defensive copy is made and then wrapped with an unmodifiable map.  Hence,
   * modifications to the map passed in will not have any effect on the map stored in this class.
   *
   * @param properties the properties to set
   */
  protected void setProperties(Map<Object, Object> properties) {
    // here we create an unmodifiable map because these properties should never be modified.  We are also creating a
    // defensive copy first so that the properties cannot be modified out from underneath us by the calling class.
    this.properties = Collections.unmodifiableMap(new HashMap<Object, Object>(properties));
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getUri() {
    return uri;
  }

  /**
   * A setter for the uri.  Only present for use or overriding by subclasses.
   *
   * @param uri the uri to be set
   */
  protected void setUri(String uri) {
    this.uri = uri;
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
   * A setter for the default object filter.  Only present for use or overriding by subclasses.
   *
   * @param defaultObjectFilter the default object filter to be set
   */
  protected void setDefaultObjectFilter(String defaultObjectFilter) {
    this.defaultObjectFilter = defaultObjectFilter;
  }

}
