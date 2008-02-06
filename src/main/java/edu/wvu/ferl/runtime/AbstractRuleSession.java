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
import edu.wvu.ferl.RuleExecutionSetMetadataImpl;

import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.RuleSession;

/**
 *
 * @author jbunting
 */
abstract class AbstractRuleSession implements RuleSession {
  
  protected StoredRuleExecutionSet storedRuleExecutionSet;
  protected RuleRuntimeImpl ruleRuntime;
  protected Map properties;

  protected boolean isReleased = false;
  
  /** Creates a new instance of AbstractRuleSession */
  public AbstractRuleSession(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
    this.properties = properties;
    this.ruleRuntime = ruleRuntime;
  }

  public RuleExecutionSetMetadata getRuleExecutionSetMetadata() throws InvalidRuleSessionException, RemoteException {
    checkRelease();
    return new RuleExecutionSetMetadataImpl(this.storedRuleExecutionSet);
  }

  public void release() throws RemoteException, InvalidRuleSessionException {
    checkRelease();
    isReleased = true;
  }
  
  public void checkRelease() throws InvalidRuleSessionException {
    if(isReleased) {
      throw new InvalidRuleSessionException("This RuleSession has already been released...you must acquire a new one from the RuleRuntime.");
    }
  }
  
  protected void executeRules(RuleEvaluator.ExecuteRulesHook hook) throws InvalidRuleSessionException, RemoteException {
    ruleRuntime.ruleEvaluator.executeRules(hook, storedRuleExecutionSet);
  }
  
}
