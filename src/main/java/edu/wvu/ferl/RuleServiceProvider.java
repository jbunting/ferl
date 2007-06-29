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
import edu.wvu.ferl.spi.RuleStore;
import java.util.ArrayList;
import java.util.List;
import javax.rules.ConfigurationException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jbunting
 */
public class RuleServiceProvider extends javax.rules.RuleServiceProvider {
  
  private static final Log logger = LogFactory.getLog(RuleServiceProvider.class);
  
  public static final String REGISTRATION_URI = "edu.wvu.ferl.RuleServiceProvider";
  
  private ClassLoader classLoader;

  static {
    try {
      RuleServiceProviderManager.registerRuleServiceProvider(REGISTRATION_URI, RuleServiceProvider.class);
    } catch (ConfigurationException ex) {
      logger.error("Cannot register " + RuleServiceProvider.class.getName() + " with the RuleServiceProviderManager...", ex);
    }
  }
  
  private RuleRuntimeImpl ruleRuntime;
  private RuleAdministratorImpl ruleAdministrator;
  private RuleStore ruleStore;
  
  /** Creates a new instance of ScriptRulesServiceProvider */
  public RuleServiceProvider() {
  }

  public void setClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
    super.setClassLoader(classLoader);
  }

  public RuleRuntime getRuleRuntime() throws ConfigurationException {
    return this.getRuleRuntimeImpl();
  }
    
  public RuleRuntimeImpl getRuleRuntimeImpl() throws ConfigurationException {
    synchronized(this) {
      if(ruleRuntime == null) {
        ruleRuntime = new RuleRuntimeImpl(this, this.getRuleStore());
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

  public RuleStore getRuleStore() {
    if(ruleStore == null) {
      synchronized(this) {
        if(ruleStore == null) {
          ruleStore = new DefaultRuleStore();
        }
      }
    }
    return ruleStore;
  }

  public void setRuleStore(RuleStore ruleStore) {
    this.ruleStore = ruleStore;
  }

  public <T> T instantiate(Class<T> interfaceClass, String className) throws InvalidRuleSessionException {
    Class<?> clazz = null;
    
    for(ClassLoader classLoader: relevantClassLoaders()) {
      try {
        clazz = classLoader.loadClass(className);
        if(clazz != null) {
          break;
        }
      } catch (ClassNotFoundException ex) {
        // just continue...we'll try another class loader...
      }
    }
    if(clazz == null) {
      throw new InvalidRuleSessionException("Could not load the implementation of " + interfaceClass.getClass().getName(), new ClassNotFoundException("No class could be located named " + className));
    }
    if(interfaceClass.isAssignableFrom(clazz)) {
      
    }
    Class<? extends T> typedClazz = (Class<? extends T>) clazz;
    try {
      return typedClazz.newInstance();
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new InvalidRuleSessionException("Could not instantiate " + typedClazz.getName(), ex);
    }
  }
  
  private List<ClassLoader> relevantClassLoaders() {
    List<ClassLoader> list = new ArrayList<ClassLoader>();
    if(this.classLoader != null) {
      list.add(this.classLoader);
    }
    if(Thread.currentThread().getContextClassLoader() != null) {
      list.add(Thread.currentThread().getContextClassLoader());
    }
    if(this.getClass().getClassLoader() != null) {
      list.add(this.getClass().getClassLoader());
    }
    return list;
  }
}
