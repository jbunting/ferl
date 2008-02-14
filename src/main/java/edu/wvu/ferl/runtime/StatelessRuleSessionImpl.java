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
 * StatelessRuleSessionImpl.java
 *
 * Created on May 5, 2007, 3:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.eval.RuleEvaluator;
import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;
import javax.script.ScriptContext;

/**
 * The implementation of the stateless rules session.
 * @author jbunting
 */
@SuppressWarnings({"unchecked"})
class StatelessRuleSessionImpl extends AbstractRuleSession implements StatelessRuleSession {

  public static final String INPUT_LIST_ATTRIBUTE_NAME = "data";

  /**
   * Creates a new instance of StatelessRuleSessionImpl
   * @param storedRuleExecutionSet the execution set to create this session for
   * @param properties the properties passed by the client
   * @param ruleRuntime the runtime this session is executing in
   */
  public StatelessRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  /**
   * {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public int getType() throws InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATELESS_SESSION_TYPE;
  }

  /**
   * Peforms the actual execution of the rules.
   * @param list {@inheritDoc}
   * @return the filtered result list
   * @throws InvalidRuleSessionException if something goes wrong during the execution
   */
  public List executeRules(List list) throws InvalidRuleSessionException {
    return executeRules(list, null);
  }

  /**
   * Performs the actual execution of the rules, filtering the output using the provided {@code objectFilter}.
   * @param list the input list
   * @param objectFilter the filter to apply to the output
   * @throws InvalidRuleSessionException if something goes wrong during the execution
   */
  public List executeRules(List list, ObjectFilter objectFilter) throws InvalidRuleSessionException {
    checkRelease();
    StatelessExecuteRulesHook hook = new StatelessExecuteRulesHook(list);
    super.executeRules(hook);
    return super.filter(objectFilter, hook.getCurrentList());
  }

  /**
   * The hook for executing stateless rules.  The input list is passed in the constructor.  
   */
  private static class StatelessExecuteRulesHook implements RuleEvaluator.ExecuteRulesHook {

    private List currentList;

    /**
     * Creates a new hook, initializing it with a defensive copy for {@code currentList}.
     * @param currentList the list to use to initialize this hook
     */
    public StatelessExecuteRulesHook(List currentList) {
      this.currentList = new ArrayList(currentList);
    }

    /**
     * Adds the current list into the context with the attribute name
     * "{@value StatelessRuleSessionImpl#INPUT_LIST_ATTRIBUTE_NAME}".
     * @param context the context to add the attribute to
     */
    public void populateScriptContext(ScriptContext context) {
      context.setAttribute(INPUT_LIST_ATTRIBUTE_NAME, currentList, ScriptContext.ENGINE_SCOPE);
    }

    /**
     * Handles the output of a rule invocation as follows:
     * If the output is a {@code List}, then sets it as the current list.
     * If the output is a {@code Collection} other than {@code List}, then it creates a {@code List} with the values
     * in that {@code Collection} and sets that as the current list.
     * If the output is null, it leaves the current list alone.
     * Otherwise, it creates a new, one element {@code List} with the output as the sole value.
     *
     * After doing this, it invokes {@link #populateScriptContext} with {@code context}.
     *
     * @param context the context to update
     * @param output the output from the previous rule invocation
     */
    public void handleOutput(ScriptContext context, Object output) {
      if(output instanceof List) {
        currentList = (List) output;
      } else if(output instanceof Collection) {
        currentList = Arrays.asList(((Collection)output).toArray());
      } else if(output != null) {
        currentList = new ArrayList();
        currentList.add(output);
      }
      populateScriptContext(context);
      // if output is null, then we leave currentList alone
    }

    /**
     * Gets the current list.
     * @return the current list
     */
    public List getCurrentList() {
      return currentList;
    }

  }
}
