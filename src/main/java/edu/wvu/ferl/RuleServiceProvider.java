/*
 * ScriptRulesServiceProvider.java
 *
 * Created on May 5, 2007, 2:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.admin.RuleAdministratorImpl;
import edu.wvu.ferl.spi.DefaultRuleStore;
import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.admin.RuleAdministrator;

/**
 *
 * @author jbunting
 */
public class RuleServiceProvider extends javax.rules.RuleServiceProvider {
  
  private RuleRuntimeImpl ruleRuntime;
  private RuleAdministratorImpl ruleAdministrator;
  
  /** Creates a new instance of ScriptRulesServiceProvider */
  public RuleServiceProvider() {
  }

  public RuleRuntime getRuleRuntime() throws ConfigurationException {
    return this.getRuleRuntimeImpl();
  }
    
  public RuleRuntimeImpl getRuleRuntimeImpl() throws ConfigurationException {
    synchronized(this) {
      if(ruleRuntime == null) {
        ruleRuntime = new RuleRuntimeImpl(this, new DefaultRuleStore());
      }
    }
    return ruleRuntime;
  }
  
  public RuleAdministrator getRuleAdministrator() throws ConfigurationException {
    return this.getRuleAdministratorImpl();
  }
    
  public RuleAdministratorImpl getRuleAdministratorImpl() throws ConfigurationException {
    synchronized(this) {
      if(ruleAdministrator == null) {
        ruleAdministrator = new RuleAdministratorImpl(this);
      }
    }
    return ruleAdministrator;
  }
  
}
