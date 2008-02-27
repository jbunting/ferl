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
import java.util.Map;

/**
 * This is the stored representation of the {@link javax.rules.admin.RuleExecutionSet RuleExecutionSet}.  It is used
 * during execution as well as administration.
 * <p/>
 * Date: June 28, 2007
 * Time: 9:37 PM
 *
 * @author jbunting
 */
public interface StoredRuleExecutionSet {

  /**
   * The name of a class to be used as an {@link javax.rules.ObjectFilter}.
   *
   * @return the name of the object filter class
   * @see javax.rules.admin.RuleExecutionSet#getDefaultObjectFilter()
   */
  public String getDefaultObjectFilter();

  /**
   * A human readable description of what this execution set is, or what it does.
   *
   * @return the description of this set
   * @see javax.rules.admin.RuleExecutionSet#getDescription()
   */
  public String getDescription();

  /**
   * A simple name for this execution set.
   *
   * @return the name of this execution set
   * @see javax.rules.admin.RuleExecutionSet#getName()
   */
  public String getName();

  /**
   * Properties to be stored with this execution set.  This map should be read-only.
   *
   * @return the map of properties
   * @see javax.rules.admin.RuleExecutionSet#getProperty(Object)
   */
  public Map<Object, Object> getProperties();

  /**
   * A list of uris that reference the rules that make up this execution set. This differs from
   * {@link javax.rules.admin.RuleExecutionSet#getRules()} by simply referring to rules, rather than including them.
   * This allows rules to be first-order entities and therefore reused among multiple sets.  This list should be
   * read-only.
   *
   * @return the list of rule uris
   */
  public List<String> getRuleUris();

  /**
   * The uniquely identifiable uri of this rule execution set.  This uri is what is used to reference this set in other
   * contexts.
   *
   * @return the uri of this rule execution set
   * @see javax.rules.RuleRuntime#createRuleSession(String, java.util.Map, int)
   */
  public String getUri();

}
