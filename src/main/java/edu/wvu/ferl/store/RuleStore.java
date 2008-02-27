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
 * The {@code RuleStore} has sole control over how properties are stored.  Some may support serializable values only,
 * some may only support certain types of objects.  It is important that {@code RuleStore}s specify this, and that
 * clients are aware of it.  A {@code RuleStore} that is asked to store a property value that it does not support
 * should throw an {@code IllegalArgumentException} during the store methods, thus aborting the store operation.
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
   * @throws IllegalArgumentException if this {@code RuleStore} cannot store the property values
   */
  public void storeRuleSet(StoredRuleExecutionSet storedRuleExecutionSet) throws IllegalArgumentException;

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
   * @throws IllegalArgumentException if this {@code RuleStore} cannot store the property values
   */
  public void storeRule(StoredRule storedRule) throws IllegalArgumentException;

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
