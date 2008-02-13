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
import edu.wvu.ferl.runtime.RuleExecutionSetMetadataImpl;

import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.RuleSession;

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
   * @param hook
   * @throws InvalidRuleSessionException
   * @throws RemoteException
   */
  protected void executeRules(RuleEvaluator.ExecuteRulesHook hook) throws InvalidRuleSessionException, RemoteException {
    ruleRuntime.ruleEvaluator.executeRules(hook, storedRuleExecutionSet);
  }

}
