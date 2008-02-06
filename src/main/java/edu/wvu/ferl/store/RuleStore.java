/*
 * RuleStore.java
 *
 * Created on May 5, 2007, 2:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.store;

import java.util.List;

/**
 *
 * @author jbunting
 */
public interface RuleStore {
  public void storeRuleSet(StoredRuleExecutionSet storedRuleExecutionSet);
  public void removeRuleSet(String uri);
  public StoredRuleExecutionSet lookupRuleSet(String uri);
  public List<String> listRuleSets();
  
  public void storeRule(StoredRule storedRule);
  public void removeRule(String uri);
  public StoredRule lookupRule(String uri);
  public List<String> listRules();
}
