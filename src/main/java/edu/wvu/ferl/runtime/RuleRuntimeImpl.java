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
 * RuleRuntime.java
 *
 * Created on May 5, 2007, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.RuleServiceProvider;
import edu.wvu.ferl.eval.RuleEvaluator;
import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.util.List;
import java.util.Map;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSession;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;

/**
 * The ferl implementation of {@link RuleRuntime}.  See {@link RuleEvaluator#executeRules} for information on how data
 * is mapped to the {@link javax.script.ScriptContext ScriptContext}.
 *
 * @author jbunting
 * @see edu.wvu.ferl.eval.RuleEvaluator#executeRules
 */
public class RuleRuntimeImpl implements RuleRuntime {

  private RuleServiceProvider serviceProvider;

  protected RuleEvaluator ruleEvaluator;

  /**
   * Creates a new instance of the ferl {@link RuleRuntime}.
   *
   * @param serviceProvider the service provider that created this runtime
   */
  public RuleRuntimeImpl(RuleServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
    this.ruleEvaluator = new RuleEvaluator(this);
  }

  /**
   * Creates a new {@code RuleSession} of type {@code sessionType} using the {@code RuleExecutionSet} found at
   * {@code uri}.
   *
   * @param uri         the uri that uniquely identifies the rule set
   * @param properties  custom properties to be applied to the rule set
   * @param sessionType either {@link #STATEFUL_SESSION_TYPE} or {@link #STATELESS_SESSION_TYPE}
   * @return the new session
   * @throws RuleSessionTypeUnsupportedException
   *                                    if an unsupported session type is requested
   * @throws RuleSessionCreateException if some other error occurs creating the new session
   * @throws RuleExecutionSetNotFoundException
   *                                    if the set specified by {@code uri} is not in the
   *                                    {@link edu.wvu.ferl.store.RuleStore}
   */
  public RuleSession createRuleSession(String uri, Map properties, int sessionType)
          throws RuleSessionTypeUnsupportedException,
          RuleSessionCreateException,
          RuleExecutionSetNotFoundException {
    StoredRuleExecutionSet set = serviceProvider.getRuleStore().lookupRuleSet(uri);
    if(set == null) {
      throw new RuleExecutionSetNotFoundException("No RuleExecutionSet registered at uri " + uri);
    }

    switch(sessionType) {
      case RuleRuntime.STATELESS_SESSION_TYPE:
        return new StatelessRuleSessionImpl(set, properties, this);
      case RuleRuntime.STATEFUL_SESSION_TYPE:
        return new StatefulRuleSessionImpl(set, properties, this);
      default:
        throw new RuleSessionTypeUnsupportedException("Ferl does not support RuleSession type " + sessionType);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public List getRegistrations() {
    return serviceProvider.getRuleStore().listRuleSets();
  }

  /**
   * Returns the {@code RuleServiceProvider} that this runtime belongs to.  This can be used to access the
   * {@link edu.wvu.ferl.store.RuleStore RuleStore} and the {@link edu.wvu.ferl.cache.CacheFactory CacheFactory}
   * associated with this ferl instance.
   *
   * @return the service provider
   */
  public RuleServiceProvider getRuleServiceProvider() {
    return serviceProvider;
  }
}
