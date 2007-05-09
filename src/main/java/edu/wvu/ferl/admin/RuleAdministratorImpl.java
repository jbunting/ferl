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
import edu.wvu.ferl.spi.RuleStore;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetDeregistrationException;
import javax.rules.admin.RuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSetRegisterException;
import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.lang.NotImplementedException;

/**
 *
 * @author jbunting
 */
public class RuleAdministratorImpl implements RuleAdministrator {
  
  ScriptRuleServiceProvider serviceProvider;
  
  /** Creates a new instance of RuleAdministrator */
  public RuleAdministratorImpl(ScriptRuleServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public RuleExecutionSetProvider getRuleExecutionSetProvider(Map map) throws RemoteException {
    return RuleExecutionSetProviderImpl.getInstance();
  }

  public LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(Map map) throws RemoteException {
    return RuleExecutionSetProviderImpl.getInstance();
  }

  public void registerRuleExecutionSet(String string, RuleExecutionSet ruleExecutionSet, Map map) throws RuleExecutionSetRegisterException, RemoteException {
    if(!(ruleExecutionSet instanceof RuleExecutionSetImpl)) {
      throw new IllegalClassException("Only RuleExecutionSets created by this RuleAdministrator can be registered...");
    }
    RuleExecutionSetImpl ruleExecutionSetImpl = (RuleExecutionSetImpl) ruleExecutionSet;
    //RuleStore ruleStore = serviceProvider.getRuleRuntimeImpl().getRuleStore();
    StoredRuleExecutionSet storedRuleExecutionSet = new StoredRuleExecutionSet();
    
  }

  public void deregisterRuleExecutionSet(String string, Map map) throws RuleExecutionSetDeregistrationException, RemoteException {
    throw new NotImplementedException();
  }
  
}
