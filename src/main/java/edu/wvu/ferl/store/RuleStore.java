package edu.wvu.ferl.store;

import java.util.List;

/**
 * A {@code RuleStore} is the primary mechanism that ferl uses to store and retrieve rules.  The {@code RuleStore} is
 * used by both the runtime and the admin portions of the api.  It provides store, remove, lookup, and list methods
 * for both rules and rule execution sets.  It should be noted that rules and rule execution sets are stored
 * separately and independently.  This allows multiple sets to reference the same rules, but also means that sets may
 * specify rules that do not exist.  In this case, an error would be thrown at run time.
 * <p/>
 * It is acceptable for client code to operate against this api in order to manage the rule store.  This should be
 * limited to things such as administrative interfaces and the like.
 * <p/>
 * Date: May 5, 2007
 * Time: 2:30 PM
 *
 * @author jbunting
 */
public interface RuleStore {
  /**
   * Stores a rule execution set.  This method is also used to modify a set - a set passed in this method with the same
   * uri as an existing set simply overwrites the existing set.  Implementors of this method should do a defensive copy
   * when storing an execution set - this prevents underlying representations to be modified out from under the
   * {@code RuleStore}.  It also allows storage in the storage mechanism's own implementation.
   *
   * @param storedRuleExecutionSet the set to store
   */
  public void storeRuleSet(StoredRuleExecutionSet storedRuleExecutionSet);

  /**
   * Removes the set with the specified uri from the store.
   *
   * @param uri the uri of the set to remove
   */
  public void removeRuleSet(String uri);

  /**
   * Retrieves and returns a rule execution set with the specified uri.
   *
   * @param uri the uri to lookup
   * @return the set specified by the uri
   */
  public StoredRuleExecutionSet lookupRuleSet(String uri);

  /**
   * Retrieves and returns a list of all of the existing rule execution sets.  The list returned by this method should
   * be read-only.
   *
   * @return the list of all rule execution sets
   */
  public List<String> listRuleSets();

  /**
   * Stores a rule.  This method is also used to modify a rule - a rule passed in this method with the same uri as an
   * existing rule simply overwrites the existing rule.
   *
   * @param storedRule the rule to store
   */
  public void storeRule(StoredRule storedRule);

  /**
   * Removes the rule with the specified uri from the store.
   *
   * @param uri the uri of the rule to remove
   */
  public void removeRule(String uri);

  /**
   * Retrieves and returns a rule with the specified uri.
   *
   * @param uri the uri to lookup
   * @return the rule specified by the uri
   */
  public StoredRule lookupRule(String uri);

  /**
   * Retrieves and returns a list of all of the existing rules.  The list returned by this method should be read-only.
   *
   * @return the list of all rules
   */
  public List<String> listRules();
}
