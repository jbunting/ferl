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
