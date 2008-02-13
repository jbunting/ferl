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

import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.runtime.AbstractRuleSession;
import edu.wvu.ferl.eval.RuleEvaluator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;
import javax.script.ScriptContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author jbunting
 */
class StatelessRuleSessionImpl extends AbstractRuleSession implements StatelessRuleSession {

  /**
   * Creates a new instance of StatelessRuleSessionImpl
   */
  public StatelessRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  public int getType() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATELESS_SESSION_TYPE;
  }

  public List executeRules(List list) throws InvalidRuleSessionException, RemoteException {
    checkRelease();
    StatelessExecuteRulesHook hook = new StatelessExecuteRulesHook(list);
    super.executeRules(hook);
    return hook.getCurrentList();
  }

  /**
   * @todo actually use the objectfilter
   */
  public List executeRules(List list, ObjectFilter objectFilter) throws InvalidRuleSessionException, RemoteException {
    checkRelease();
    List outList = this.executeRules(list);
    if(objectFilter == null && !StringUtils.isBlank(storedRuleExecutionSet.getDefaultObjectFilter())) {
      objectFilter = this.ruleRuntime.getRuleServiceProvider().instantiate(ObjectFilter.class, storedRuleExecutionSet.getDefaultObjectFilter());
    }
    if(objectFilter != null) {
      CollectionUtils.transform(outList, new ObjectFilterTransformer(objectFilter));
      CollectionUtils.filter(outList, PredicateUtils.notNullPredicate());
    }
    return outList;
  }

  private static class StatelessExecuteRulesHook implements RuleEvaluator.ExecuteRulesHook {

    private List currentList;

    public StatelessExecuteRulesHook(List currentList) {
      this.currentList = currentList;
    }

    public void populateScriptContext(ScriptContext context) {
      context.setAttribute("data", currentList, ScriptContext.ENGINE_SCOPE);
    }

    public void handleOutput(ScriptContext context, Object output) {
      if(output instanceof List) {
        currentList = (List) output;
      } else if(output != null) {
        currentList = new ArrayList();
        currentList.add(output);
      }
      populateScriptContext(context);
      // if output is null, then we leave currentList alone
    }

    public List getCurrentList() {
      return currentList;
    }

  }
}
