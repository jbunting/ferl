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

package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of {@code StoredRule}.  This implementation is read-only, and provides two constructors -
 * one where the client will provide all of the parameters, and a convenience constructor for defensive copying.
 * <p/>
 * This implementation may be subclassed for the purposes of adding functionality.
 * <p/>
 * Note that {@code protected} setters are provided for subclasses that may, for some reason, wish to modify or extend
 * the set mechanism.  It is still recommended that subclasses maintain the strict read-only nature of this class in
 * its public api.
 * <p/>
 * Date: June 28, 2007
 * Time: 9:29 PM
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

  /**
   * Creates a new instance of {@code StoredRuleImpl}.  Note that the properties are copied prior to storing so that
   * any modifications to the passed in map do not have any effect on this object.
   *
   * @param uri         the uri of the new rule
   * @param name        the name of the new rule
   * @param description the description of the new rule
   * @param language    the language of the new rule
   * @param script      the script of the new rule
   * @param properties  the the properties of the new rule
   */
  public StoredRuleImpl(String uri,
                        String name,
                        String description,
                        String language,
                        String script,
                        Map<Object, Object> properties) {
    this.setUri(uri);
    this.setName(name);
    this.setDescription(description);
    this.setLanguage(language);
    this.setScript(script);
    this.setProperties(properties);
  }

  /**
   * Convenience constructor for creating a defensive copy.  This constructor essentially invokes
   * {@link #StoredRuleImpl(String, String, String, String, String, java.util.Map)} with the values returned from the
   * getters of the {@code storedRule} passed in.
   *
   * @param storedRule the stored rule to copy
   */
  public StoredRuleImpl(StoredRule storedRule) {
    this(storedRule.getUri(),
            storedRule.getName(),
            storedRule.getDescription(),
            storedRule.getLanguage(),
            storedRule.getScript(),
            storedRule.getProperties());
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getLanguage() {
    return language;
  }

  /**
   * A setter for language.  Only present for use or overriding by subclasses.
   *
   * @param language the language to set
   */
  protected void setLanguage(String language) {
    this.language = language;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getScript() {
    return script;
  }

  /**
   * A setter for the script.  Only present for use or overriding by subclasses.
   *
   * @param script the script to set
   */
  protected void setScript(String script) {
    this.script = script;
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
   * @param name the name of the rule
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
   * @param description the description of the rule
   */
  protected void setDescription(String description) {
    this.description = description;
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
   * @param uri the uri of the rule
   */
  protected void setUri(String uri) {
    this.uri = uri;
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

}
