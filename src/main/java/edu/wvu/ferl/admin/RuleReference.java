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
 * RuleReference.java
 *
 * Created on May 7, 2007, 10:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRule;

import javax.rules.ConfigurationException;

/**
 * @author jbunting
 */
class RuleReference implements RuleDescriptor {

  private String uri;
  private StoredRule storedRule;

  /**
   * Creates a new instance of RuleReference
   */
  public RuleReference(String uri, RuleStore ruleStore) throws ConfigurationException {
    this.setUri(uri);
    this.storedRule = ruleStore.lookupRule(uri);
    if(storedRule == null) {
      throw new ConfigurationException("There is no rule at: " + this.getUri());
    }
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String generateRule(RuleStore ruleStore) throws ConfigurationException {
    if(ruleStore.lookupRule(this.getUri()) == null) {
      throw new ConfigurationException("No Rule Exists at " + this.getUri());
    }
    return this.getUri();
  }

  public String getName() {
    return storedRule.getName();
  }

  public String getDescription() {
    return storedRule.getDescription();
  }

  public Object getProperty(Object key) {
    return storedRule.getProperties().get(key);
  }

  public void setProperty(Object key, Object value) {
    throw new UnsupportedOperationException("You can't set a property on a reference....silly...");
  }

}
