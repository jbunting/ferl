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

/*
 * StatelessRuleSessionImpl.java
 *
 * Created on May 5, 2007, 3:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;

/**
 * The implementation of the stateless rules session.
 *
 * @author jbunting
 */
@SuppressWarnings({"unchecked"})
class StatelessRuleSessionImpl extends AbstractRuleSession implements StatelessRuleSession {

  public static final String INPUT_LIST_ATTRIBUTE_NAME = "data";

  /**
   * Creates a new instance of StatelessRuleSessionImpl
   *
   * @param storedRuleExecutionSet the execution set to create this session for
   * @param properties             the properties passed by the client
   * @param ruleRuntime            the runtime this session is executing in
   */
  public StatelessRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public int getType() throws InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATELESS_SESSION_TYPE;
  }

  /**
   * Peforms the actual execution of the rules.
   *
   * @param list {@inheritDoc}
   * @return the filtered result list
   * @throws InvalidRuleSessionException if something goes wrong during the execution
   */
  public List executeRules(List list) throws InvalidRuleSessionException {
    return executeRules(list, null);
  }

  /**
   * Performs the actual execution of the rules, filtering the output using the provided {@code objectFilter}.  A copy
   * is made of the input list prior to executing the rules.
   *
   * @param list         the input list
   * @param objectFilter the filter to apply to the output
   * @throws InvalidRuleSessionException if something goes wrong during the execution
   */
  public List executeRules(List list, ObjectFilter objectFilter) throws InvalidRuleSessionException {
    checkRelease();
    List output = new ArrayList(list);
    super.doExecuteRules(output);
    return super.filter(objectFilter, output);
  }

}
