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
 * StatefulRuleSessionImpl.java
 *
 * Created on May 5, 2007, 4:25 PM
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
import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatefulRuleSession;
import javax.script.ScriptContext;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.map.ListOrderedMap;

/**
 * The implementation of the stateless rules session.
 * @author jbunting
 */
class StatefulRuleSessionImpl extends AbstractRuleSession implements StatefulRuleSession {

  private ListOrderedMap<Handle, Object> state = new ListOrderedMap<Handle, Object>();

  /**
   * Creates a new instance of StatefulRuleSessionImpl
   */
  public StatefulRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  public int getType() throws InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATEFUL_SESSION_TYPE;
  }

  public boolean containsObject(Handle handle) throws InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    return state.containsKey(handle);
  }

  public Handle addObject(Object object) throws InvalidRuleSessionException {
    checkRelease();
    HandleImpl handle = new HandleImpl(object);
    state.put(handle, object);
    return handle;
  }

  public List addObjects(List list) throws InvalidRuleSessionException {
    checkRelease();
    List handles = new ArrayList();
    for(Object object : list) {
      handles.add(this.addObject(object));
    }
    return handles;
  }

  public void updateObject(Handle handle, Object object) throws InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    state.put(handle, object);
  }

  public void removeObject(Handle handle) throws InvalidHandleException, InvalidRuleSessionException {
    checkRelease();
    state.remove(handle);
  }

  public List getObjects() throws InvalidRuleSessionException {
    checkRelease();
    return this.getObjects(null);
  }

  public List getHandles() throws InvalidRuleSessionException {
    checkRelease();
    return Collections.unmodifiableList(new ArrayList(state.keySet()));
  }

  public List getObjects(final ObjectFilter objectFilter) throws InvalidRuleSessionException {
    checkRelease();
    return super.filter(objectFilter, state.values());
  }

  public void executeRules() throws InvalidRuleSessionException {
    checkRelease();
    super.executeRules(new StatefulExecuteRulesHook(state));
  }

  public void reset() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    state.clear();
  }

  public Object getObject(Handle handle) throws RemoteException, InvalidHandleException, InvalidRuleSessionException {
    checkRelease();
    return state.get(handle);
  }

  protected Map doFilter(ObjectFilter objectFilter, Collection filterInput) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  private static class StatefulExecuteRulesHook implements RuleEvaluator.ExecuteRulesHook {

    Map<Handle, Object> state;

    public StatefulExecuteRulesHook(Map<Handle, Object> state) {
      this.state = state;
    }

    public void populateScriptContext(ScriptContext context) {
      List currentList = new ArrayList();
      currentList.addAll(state.values());
      context.setAttribute("data", currentList, ScriptContext.ENGINE_SCOPE);
    }

    public void handleOutput(ScriptContext context, Object output) {
      // should we remove values from the state??
      if(output instanceof List) {
        List list = (List) output;
        CollectionUtils.forAllDo(list, new Closure() {
          public void execute(Object object) {
            ensureContains(object);
          }
        });
      } else if(output != null) {
        ensureContains(output);
        populateScriptContext(context);
      }
    }

    private void ensureContains(Object object) {
      Handle handle = new HandleImpl(object);
      if(!state.containsKey(handle)) {
        state.put(handle, object);
      }
    }

  }
}
