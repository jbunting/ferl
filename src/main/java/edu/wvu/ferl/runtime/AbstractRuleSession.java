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
 * AbstractRuleSession.java
 *
 * Created on May 5, 2007, 3:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.eval.RuleEvaluator;
import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.RuleSession;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.PredicateUtils;
import org.apache.commons.collections15.list.PredicatedList;
import org.apache.commons.lang.StringUtils;

/**
 * An abstract super class for the stateful and stateless session classes.  Implements the methods in
 * {@link RuleSession} as well as providing the common interface to the {@link RuleEvaluator}.
 * <p/>
 * Date: May 5, 2007
 * Time: 3:15 PM
 *
 * @author jbunting
 */
abstract class AbstractRuleSession implements RuleSession {

  protected StoredRuleExecutionSet storedRuleExecutionSet;
  protected RuleRuntimeImpl ruleRuntime;
  protected Map properties;

  protected boolean isReleased = false;

  /**
   * Creates a new instance of {@code AbstractRuleSession}.
   * @param storedRuleExecutionSet the stored rule execution set to create this session from
   * @param properties the properties provided by the client
   * @param ruleRuntime the runtime that this session is being created in
   */
  public AbstractRuleSession(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
    this.properties = properties;
    this.ruleRuntime = ruleRuntime;
  }

  /**
   * Wraps up the stored {@code StoredRuleExecutionSet} and returns it.
   *
   * @return the metadata object
   * @throws InvalidRuleSessionException {@inheritDoc}
   * @throws RemoteException             {@inheritDoc}
   */
  public RuleExecutionSetMetadata getRuleExecutionSetMetadata() throws InvalidRuleSessionException, RemoteException {
    checkRelease();
    return new RuleExecutionSetMetadataImpl(this.storedRuleExecutionSet);
  }

  /**
   * {@inheritDoc}
   *
   * @throws RemoteException             {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public void release() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    isReleased = true;
  }

  /**
   * Checks if this session has already been released.  If it has, throws a {@code InvalidRuleSessionException}.  This
   * method should be called at the beginning of any other method in the session.
   *
   * @throws InvalidRuleSessionException if the session has already been released
   */
  protected void checkRelease() throws InvalidRuleSessionException {
    if(isReleased) {
      throw new InvalidRuleSessionException("This RuleSession has already been released...you must acquire a new " +
              "one from the RuleRuntime.");
    }
  }

  /**
   * Invokes the {@link RuleEvaluator}.  Should be invoked by subclasses to actually perform their rule invocations.
   *
   * @param hook the hook to use for delegating control over output and input mapping
   * @throws InvalidRuleSessionException if there is an issue with this session
   */
  protected void executeRules(RuleEvaluator.ExecuteRulesHook hook) throws InvalidRuleSessionException {
    ruleRuntime.ruleEvaluator.executeRules(hook, storedRuleExecutionSet);
  }

  /**
   * Filters the input based on the passed object filter.  If {@code objectFilter} is null, then retrieves the default
   * filter for the rule execution set.  If this is null, then it only removes null values.  This method always returns
   * a copy of the passed collection.
   * @param objectFilter the filter to use, if null, uses the default filter for the rule execution set
   * @param filterInput the input to the filter
   * @return the filtered list
   * @throws InvalidRuleSessionException if something goes wrong
   */
  protected List filter(ObjectFilter objectFilter, Collection filterInput) throws InvalidRuleSessionException {
    if(objectFilter == null && !StringUtils.isBlank(storedRuleExecutionSet.getDefaultObjectFilter())) {
      objectFilter = this.ruleRuntime.getRuleServiceProvider().instantiate(ObjectFilter.class,
              storedRuleExecutionSet.getDefaultObjectFilter());
    }
    List outputList = PredicatedList.decorate(new ArrayList(filterInput.size()), PredicateUtils.notNullPredicate());
    if(objectFilter != null) {
      CollectionUtils.collect(filterInput, new ObjectFilterTransformer(objectFilter), outputList);
    } else {
      outputList.addAll(filterInput);
    }
    return Collections.unmodifiableList(outputList);
  }

}
