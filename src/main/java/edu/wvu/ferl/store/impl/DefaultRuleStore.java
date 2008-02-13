package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a default implementation of {@code RuleStore} that uses maps to hold its information.  As this information
 * is not transient in any way, this store is not recommended for production use.  However, it can serve as a simple
 * example for building other {@code RuleStores}.
 * <p/>
 * Date: May 5, 2007
 * Time: 3:03 PM
 *
 * @author jbunting
 */
public class DefaultRuleStore implements RuleStore {

  private Map<String, StoredRuleExecutionSet> storedRuleExecutionSets;
  private Map<String, StoredRule> storedRules;

  /**
   * Creates a new instance of DefaultRuleStore.
   */
  public DefaultRuleStore() {
    storedRuleExecutionSets = new HashMap<String, StoredRuleExecutionSet>();
    storedRules = new HashMap<String, StoredRule>();
  }

  /**
   * {@inheritDoc}
   *
   * @param storedRuleExecutionSet {@inheritDoc}
   */
  public void storeRuleSet(StoredRuleExecutionSet storedRuleExecutionSet) {
    storedRuleExecutionSets.put(storedRuleExecutionSet.getUri(), new StoredRuleExecutionSetImpl(storedRuleExecutionSet));
  }

  /**
   * {@inheritDoc}
   *
   * @param uri {@inheritDoc}
   */
  public void removeRuleSet(String uri) {
    storedRuleExecutionSets.remove(uri);
  }

  /**
   * {@inheritDoc}
   *
   * @param uri {@inheritDoc}
   * @return {@inheritDoc}
   */
  public StoredRuleExecutionSet lookupRuleSet(String uri) {
    return storedRuleExecutionSets.get(uri);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public List<String> listRuleSets() {
    return Collections.unmodifiableList(new ArrayList<String>(storedRuleExecutionSets.keySet()));
  }

  /**
   * {@inheritDoc}
   *
   * @param storedRule {@inheritDoc}
   */
  public void storeRule(StoredRule storedRule) {
    storedRules.put(storedRule.getUri(), new StoredRuleImpl(storedRule));
  }

  /**
   * {@inheritDoc}
   *
   * @param uri {@inheritDoc}
   */
  public void removeRule(String uri) {
    storedRules.remove(uri);
  }

  /**
   * {@inheritDoc}
   *
   * @param uri {@inheritDoc}
   * @return {@inheritDoc}
   */
  public StoredRule lookupRule(String uri) {
    return storedRules.get(uri);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public List<String> listRules() {
    return Collections.unmodifiableList(new ArrayList<String>(storedRules.keySet()));
  }

}
