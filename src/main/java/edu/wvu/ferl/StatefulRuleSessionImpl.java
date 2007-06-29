/*
 * StatefulRuleSessionImpl.java
 *
 * Created on May 5, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.list.PredicatedList;
import org.apache.commons.collections.map.ListOrderedMap;

/**
 *
 * @author jbunting
 */
public class StatefulRuleSessionImpl extends AbstractRuleSession implements StatefulRuleSession {
  
  private ListOrderedMap state = new ListOrderedMap();
  
  /** Creates a new instance of StatefulRuleSessionImpl */
  public StatefulRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  public int getType() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATEFUL_SESSION_TYPE;
  }

  public boolean containsObject(Handle handle) throws RemoteException, InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    return state.containsKey(handle);
  }

  public Handle addObject(Object object) throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    HandleImpl handle = new HandleImpl(object);
    state.put(handle, object);
    return handle;
  }

  public List addObjects(List list) throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    List handles = new ArrayList();
    for(Object object: list) {
      handles.add(this.addObject(object));
    }
    return handles;
  }

  public void updateObject(Handle handle, Object object) throws RemoteException, InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    state.put(handle, object);
  }

  public void removeObject(Handle handle) throws RemoteException, InvalidHandleException, InvalidRuleSessionException {
    checkRelease();
    state.remove(handle);
  }

  public List getObjects() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    return Collections.unmodifiableList(new ArrayList(state.values()));
  }

  public List getHandles() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    return Collections.unmodifiableList(new ArrayList(state.keySet()));
  }

  public List getObjects(final ObjectFilter objectFilter) throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    List outputList = PredicatedList.decorate(new ArrayList(state.size()), PredicateUtils.notNullPredicate());
    CollectionUtils.collect(state.values(), new ObjectFilterTransformer(objectFilter), outputList);
    return Collections.unmodifiableList(outputList);
  }

  public void executeRules() throws RemoteException, InvalidRuleSessionException {
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
  
  private static class StatefulExecuteRulesHook implements ExecuteRulesHook {
    
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
