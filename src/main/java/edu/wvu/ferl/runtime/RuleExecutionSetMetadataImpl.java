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

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.store.StoredRuleExecutionSet;

import javax.rules.RuleExecutionSetMetadata;

/**
 * An implementation of {@code RuleExecutionSetMetadata}.  Essentially an adapter to the {@link StoredRuleExecutionSet}.
 * <p/>
 * Date: May 5, 2007
 * Time: 3:15 PM
 *
 * @author jbunting
 */
class RuleExecutionSetMetadataImpl implements RuleExecutionSetMetadata {

  private StoredRuleExecutionSet storedRuleExecutionSet;

  /**
   * Creates a new instance of {@code RuleExecutionSetMetadataImpl}
   *
   * @param storedRuleExecutionSet the execution set to adapt
   */
  public RuleExecutionSetMetadataImpl(StoredRuleExecutionSet storedRuleExecutionSet) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getUri() {
    return storedRuleExecutionSet.getUri();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getName() {
    return storedRuleExecutionSet.getName();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getDescription() {
    return storedRuleExecutionSet.getDescription();
  }

}
