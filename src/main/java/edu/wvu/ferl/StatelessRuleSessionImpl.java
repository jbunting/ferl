/*
 * StatelessRuleSessionImpl.java
 *
 * Created on May 5, 2007, 3:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;
import javax.script.ScriptContext;

/**
 *
 * @author jbunting
 */
public class StatelessRuleSessionImpl extends AbstractRuleSession implements StatelessRuleSession {
  
  /** Creates a new instance of StatelessRuleSessionImpl */
  public StatelessRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  public int getType() throws RemoteException, InvalidRuleSessionException {
    return RuleRuntime.STATELESS_SESSION_TYPE;
  }

  public List executeRules(List list) throws InvalidRuleSessionException, RemoteException {
    StatelessExecuteRulesHook hook = new StatelessExecuteRulesHook(list);
    super.executeRules(hook);
    return hook.getCurrentList();
  }

  public List executeRules(List list, ObjectFilter objectFilter) throws InvalidRuleSessionException, RemoteException {
    return this.executeRules(list);
  }
  
  private static class StatelessExecuteRulesHook implements ExecuteRulesHook {
    
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
