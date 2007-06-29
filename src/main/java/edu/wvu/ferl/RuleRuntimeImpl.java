/*
 * RuleRuntime.java
 *
 * Created on May 5, 2007, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.eval.CompiledScriptCache;
import edu.wvu.ferl.eval.DefaultCompiledScriptCache;
import edu.wvu.ferl.eval.RuleEvalStrategy;
import edu.wvu.ferl.spi.RuleStore;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSession;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;

/**
 *
 * @author jbunting
 */
public class RuleRuntimeImpl implements RuleRuntime {
  
  private RuleServiceProvider serviceProvider;
  private RuleStore ruleStore;
  
  protected Map<String, RuleEvalStrategy> strategies = new HashMap<String, RuleEvalStrategy>();
  protected CompiledScriptCache compiledScriptCache = new DefaultCompiledScriptCache();

  /** Creates a new instance of RuleRuntime */
  public RuleRuntimeImpl(RuleServiceProvider serviceProvider, RuleStore ruleStore) {
    this.serviceProvider = serviceProvider;
    this.ruleStore = ruleStore;
  }

  public RuleSession createRuleSession(String uri, Map properties, int i) 
          throws RuleSessionTypeUnsupportedException, 
                 RuleSessionCreateException, 
                 RuleExecutionSetNotFoundException, 
                 RemoteException {
    StoredRuleExecutionSet set = ruleStore.lookupRuleSet(uri);
    if(set == null) {
      throw new RuleExecutionSetNotFoundException("No RuleExecutionSet registered at uri " + uri);
    }
    
    if(RuleRuntime.STATELESS_SESSION_TYPE == i) {
      return new StatelessRuleSessionImpl(set, properties, this);
    } else {
      throw new RuleSessionTypeUnsupportedException("Don't support RuleSession type " + i);
    }
  }

  public List getRegistrations() throws RemoteException {
    return ruleStore.listRuleSets();
  }

  public RuleStore getRuleStore() {
    return ruleStore;
  }
  
  public RuleServiceProvider getRuleServiceProvider() {
    return serviceProvider;
  }
}
