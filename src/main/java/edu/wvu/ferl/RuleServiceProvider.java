/**
 * Copyright 2008 West Virginia University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package edu.wvu.ferl;

import edu.wvu.ferl.admin.RuleAdministratorImpl;
import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.impl.DefaultCacheFactory;
import edu.wvu.ferl.runtime.RuleRuntimeImpl;
import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.impl.DefaultRuleStore;

import java.util.ArrayList;
import java.util.List;
import javax.rules.ConfigurationException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The {@link javax.rules.RuleServiceProvider RuleServiceProvider} for ferl.  Also maintains ferl's {@link RuleStore}.
 * <p/>
 * Date: May 5, 2007
 * Time: 2:12 PM
 *
 * @author jbunting
 */
public class RuleServiceProvider extends javax.rules.RuleServiceProvider {

  private static final Log logger = LogFactory.getLog(RuleServiceProvider.class);

  /**
   * ferl's registraction uri - {@value}
   */
  public static final String REGISTRATION_URI = "edu.wvu.ferl.RuleServiceProvider";

  /**
   * API standard URI registration block.
   */
  static {
    try {
      RuleServiceProviderManager.registerRuleServiceProvider(REGISTRATION_URI, RuleServiceProvider.class);
    } catch(ConfigurationException ex) {
      logger.error("Cannot register " + RuleServiceProvider.class.getName() + " with the RuleServiceProviderManager...", ex);
    }
  }

  private RuleRuntimeImpl ruleRuntime;
  private RuleAdministratorImpl ruleAdministrator;
  private RuleStore ruleStore;
  private CacheFactory cacheFactory;
  private ClassLoader classLoader;

  /**
   * Creates a new instance of {@code RuleServiceProvider}
   */
  public RuleServiceProvider() {
  }

  /**
   * Sets the classloader to be used by the rules engine for loading up classes.  This is really only used for
   * {@link javax.rules.ObjectFilter}s.
   *
   * @param classLoader the class loader to set
   */
  public void setClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
    super.setClassLoader(classLoader);
  }

  /**
   * Implements the {@link javax.rules.RuleServiceProvider#getRuleRuntime} method.  The return type has been altered
   * to indicate that it will return the special ferl implementation of {@link RuleRuntime}.
   *
   * @return the rule runtime associated with this service provider
   * @throws ConfigurationException if the runtime cannot be retrieved for some reason
   */
  public RuleRuntimeImpl getRuleRuntime() throws ConfigurationException {
    if(ruleRuntime == null) {
      synchronized(this) {
        if(ruleRuntime == null) {
          ruleRuntime = new RuleRuntimeImpl(this);
        }
      }
    }
    return ruleRuntime;
  }

  /**
   * Implements the {@link javax.rules.RuleServiceProvider#getRuleAdministrator} method.  The return type has been
   * altered to indicate that it will return the special ferl implementation of {@link RuleAdministrator}.
   *
   * @return the rule administrator associated with this service provider
   * @throws ConfigurationException if the administrator cannot be retrieved for some reason
   */
  public RuleAdministratorImpl getRuleAdministrator() throws ConfigurationException {
    if(ruleAdministrator == null) {
      synchronized(this) {
        if(ruleAdministrator == null) {
          ruleAdministrator = new RuleAdministratorImpl(this);
        }
      }
    }
    return ruleAdministrator;
  }

  /**
   * Retrieves the current rule store used for this provider.  Clients should always use this method instead of
   * locally storing a copy of the rule store - as the rule store may change.
   *
   * @return the currently configured rule store
   */
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

  /**
   * Sets the current rule store for this provider.
   *
   * @param ruleStore the rule store to use
   */
  public void setRuleStore(RuleStore ruleStore) {
    if(this.ruleStore == null) {
      synchronized(this) {
        if(this.ruleStore == null) {
          this.ruleStore = ruleStore;
          return;
        }
      }
    }
    throw new IllegalStateException("RuleStore cannot be changed once set!");
  }

  /**
   * Gets the cache factory for this provider.
   *
   * @return the currently set cache factory
   */
  public CacheFactory getCacheFactory() {
    if(cacheFactory == null) {
      synchronized(this) {
        if(cacheFactory == null) {
          this.cacheFactory = new DefaultCacheFactory();
        }
      }
    }
    return cacheFactory;
  }

  /**
   * Sets the cache factory for this provider.  This will be used to created caches for various stores.
   *
   * @param cacheFactory the new cache factory for this provider
   */
  public void setCacheFactory(CacheFactory cacheFactory) {
    if(this.cacheFactory == null) {
      synchronized(this) {
        if(this.cacheFactory == null) {
          this.cacheFactory = cacheFactory;
        }
      }
    }
  }

  /**
   * Used to instantiate a new object, as an instance of {@code className}.  Attempts to use, in order, the classloader
   * set on this object, the context class loader, and then the class loader retrieved from
   * {@code this.getClass().getClassLoader()}.
   *
   * @param interfaceClass the type that will be returned
   * @param className      the name of the class to instantiate - this class should extend or implement
   *                       {@code interfaceClass}
   * @return the instantiated object, of type {@code className}
   * @throws InvalidRuleSessionException if the class could not be instantiated for some reason
   */
  public <T> T instantiate(Class<T> interfaceClass, String className) throws InvalidRuleSessionException {
    Class<?> clazz = null;

    for(ClassLoader classLoader : relevantClassLoaders()) {
      try {
        clazz = classLoader.loadClass(className);
        if(clazz != null) {
          break;
        }
      } catch(ClassNotFoundException ex) {
        // just continue...we'll try another class loader...
      }
    }
    if(clazz == null) {
      throw new InvalidRuleSessionException("Could not load the implementation of " + interfaceClass.getClass().getName(), new ClassNotFoundException("No class could be located named " + className));
    }
    if(interfaceClass.isAssignableFrom(clazz)) {
      //noinspection unchecked
      Class<? extends T> typedClazz = (Class<? extends T>) clazz;
      try {
        return typedClazz.newInstance();
      } catch(RuntimeException ex) {
        throw ex;
      } catch(Exception ex) {
        throw new InvalidRuleSessionException("Could not instantiate " + typedClazz.getName(), ex);
      }
    } else {
      throw new IllegalArgumentException(className + " does not extend or implement " + interfaceClass.getClass().getName());
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
