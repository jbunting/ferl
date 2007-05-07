/*
 * DefaultRuleStore.java
 *
 * Created on May 5, 2007, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public class DefaultRuleStore implements RuleStore {
  
  private Map<String, StoredRuleExecutionSet> storedRuleExecutionSets;
  private Map<String, StoredRule> storedRules;
  
  /** Creates a new instance of DefaultRuleStore */
  public DefaultRuleStore() {
    storedRuleExecutionSets = new HashMap<String, StoredRuleExecutionSet>();
    storedRules = new HashMap<String, StoredRule>();
  }

  public void storeRuleSet(StoredRuleExecutionSet storedRuleExecutionSet) {
    storedRuleExecutionSets.put(storedRuleExecutionSet.getName(), storedRuleExecutionSet);
  }

  public void removeRuleSet(String uri) {
    storedRuleExecutionSets.remove(uri);
  }

  public StoredRuleExecutionSet lookupRuleSet(String uri) {
    return storedRuleExecutionSets.get(uri);
  }
  
  public List<String> listRuleSets() {
    return Collections.unmodifiableList(new ArrayList(storedRuleExecutionSets.keySet()));
  }

  public void storeRule(StoredRule storedRule) {
    storedRules.put(storedRule.getUri(), storedRule);
  }

  public void removeRule(String uri) {
    storedRules.remove(uri);
  }

  public StoredRule lookupRule(String uri) {
    return storedRules.get(uri);
  }
  
  public List<String> listRules() {
    return Collections.unmodifiableList(new ArrayList(storedRules.keySet()));
  }
  
}
