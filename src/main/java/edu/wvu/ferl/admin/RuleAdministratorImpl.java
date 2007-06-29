/*
 * RuleAdministrator.java
 *
 * Created on May 5, 2007, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.RuleServiceProvider;
import edu.wvu.ferl.spi.RuleStore;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import edu.wvu.ferl.spi.StoredRuleExecutionSetImpl;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.rules.ConfigurationException;
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
  
  RuleServiceProvider serviceProvider;
  
  /** Creates a new instance of RuleAdministrator */
  public RuleAdministratorImpl(RuleServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public RuleExecutionSetProvider getRuleExecutionSetProvider(Map map) throws RemoteException {
    return new RuleExecutionSetProviderImpl(this);
  }

  public LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(Map map) throws RemoteException {
    return new RuleExecutionSetProviderImpl(this);
  }

  public void registerRuleExecutionSet(String uri, RuleExecutionSet ruleExecutionSet, Map properties) throws RuleExecutionSetRegisterException, RemoteException {
    if(!(ruleExecutionSet instanceof RuleExecutionSetImpl)) {
      throw new IllegalClassException("Only RuleExecutionSets created by this RuleAdministrator can be registered...");
    }
    RuleExecutionSetImpl ruleExecutionSetImpl = (RuleExecutionSetImpl) ruleExecutionSet;

    try {
      RuleStore ruleStore = serviceProvider.getRuleRuntimeImpl().getRuleStore();
      List<String> ruleUris = new ArrayList<String>();
      for(RuleDescriptor ruleDescriptor: ruleExecutionSetImpl.getRuleDescriptors()) {
        ruleUris.add(ruleDescriptor.generateRule(ruleStore));
      }
      ruleStore.storeRuleSet(this.createStoredRuleExecutionSet(uri == null ? ruleExecutionSet.getName() : uri, ruleExecutionSetImpl, ruleUris, properties));
    } catch (ConfigurationException ex) {
      throw new RuleExecutionSetRegisterException("Error registering the RuleExecutionSet...", ex);
    }
  }

  public void deregisterRuleExecutionSet(String uri, Map map) throws RuleExecutionSetDeregistrationException, RemoteException {
      try {
        RuleStore ruleStore = serviceProvider.getRuleRuntimeImpl().getRuleStore();
        ruleStore.removeRuleSet(uri);
      } catch (ConfigurationException ex) {
      throw new RuleExecutionSetDeregistrationException("Error registering the RuleExecutionSet...", ex);
      }
  }
  
  protected StoredRuleExecutionSet createStoredRuleExecutionSet(String uri, RuleExecutionSetImpl ruleExecutionSetImpl, List<String> ruleUris, Map properties) {
    Map localProperties = new HashMap(ruleExecutionSetImpl.getProperties());
    localProperties.putAll(properties);
    StoredRuleExecutionSet storedRuleExecutionSet = new StoredRuleExecutionSetImpl(uri, 
            ruleExecutionSetImpl.getName(),
            ruleExecutionSetImpl.getDescription(),
            ruleUris,
            localProperties,
            ruleExecutionSetImpl.getDefaultObjectFilter());
            
    return storedRuleExecutionSet;
  }
}
