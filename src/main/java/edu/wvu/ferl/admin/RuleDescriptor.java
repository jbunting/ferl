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
 * RuleDescriptor.java
 *
 * Created on May 7, 2007, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.store.RuleStore;

import javax.rules.ConfigurationException;
import javax.rules.admin.Rule;

/**
 * A simple interface for describing a rule to the rule administrator.  This is used to store rule information in the
 * {@code RuleExecutionSet} after generation so that it can later be added to a the {@link RuleStore}.
 * @author jbunting
 */
interface RuleDescriptor extends Rule {
  /**
   * Invoked by {@link RuleAdministratorImpl#registerRuleExecutionSet}.  Stores this rule in the {@code RuleStore}
   * and returns the {@code uri} of this rule
   * @param ruleStore the {@code RuleStore} to store this rule in
   * @return the uri of the stored rule
   * @throws ConfigurationException if there is something wrong with this rule, such as some value not existing
   */
  public String generateRule(RuleStore ruleStore) throws ConfigurationException;
}
