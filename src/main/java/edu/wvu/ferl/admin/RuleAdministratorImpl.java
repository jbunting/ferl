/*
 * RuleAdministrator.java
 *
 * Created on May 5, 2007, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.ScriptRuleServiceProvider;
import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetDeregistrationException;
import javax.rules.admin.RuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSetRegisterException;
import org.apache.commons.lang.NotImplementedException;

/**
 *
 * @author jbunting
 */
public class RuleAdministratorImpl implements RuleAdministrator {
  
  /** Creates a new instance of RuleAdministrator */
  public RuleAdministratorImpl(ScriptRuleServiceProvider serviceProvider) {
  }

  public RuleExecutionSetProvider getRuleExecutionSetProvider(Map map) throws RemoteException {
    throw new NotImplementedException();
  }

  public LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(Map map) throws RemoteException {
    throw new NotImplementedException();
  }

  public void registerRuleExecutionSet(String string, RuleExecutionSet ruleExecutionSet, Map map) throws RuleExecutionSetRegisterException, RemoteException {
    throw new NotImplementedException();
  }

  public void deregisterRuleExecutionSet(String string, Map map) throws RuleExecutionSetDeregistrationException, RemoteException {
    throw new NotImplementedException();
  }
  
}
