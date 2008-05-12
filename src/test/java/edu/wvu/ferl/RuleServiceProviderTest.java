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
 */

package edu.wvu.ferl;

import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.admin.RuleAdministratorImpl;
import edu.wvu.ferl.runtime.RuleRuntimeImpl;
import edu.wvu.ferl.testing.SomeRandomClass;
import edu.wvu.ferl.testing.SomeRandomInterface;
import edu.wvu.ferl.testing.SomeNonInstantiableClass;

import javax.rules.RuleServiceProviderManager;
import javax.rules.InvalidRuleSessionException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.lib.legacy.ClassImposteriser;
import org.jmock.integration.junit4.JUnit4Mockery;
import net.peachjean.xj4.XJ4Runner;
import net.peachjean.xj4.jmock.MockeryLifecycle;
import net.peachjean.xj4.lifecycle.Manage;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 4:36:56 PM
 */
@RunWith(XJ4Runner.class)
public class RuleServiceProviderTest {

  @Manage(lifecycle = MockeryLifecycle.class)
  private Mockery context;

  private RuleServiceProvider ruleServiceProvider;
  private CacheFactory cacheFactory;
  private RuleStore ruleStore;

  private ClassLoader classLoader;
  String nonExistantClassName;

  @Before
  public void setup() throws Exception {
    context.setImposteriser(ClassImposteriser.INSTANCE);

    ruleServiceProvider = new RuleServiceProvider();
    cacheFactory = context.mock(CacheFactory.class);
    ruleStore = context.mock(RuleStore.class);
    classLoader = context.mock(ClassLoader.class);
    ruleServiceProvider.setClassLoader(classLoader);
    nonExistantClassName = this.getClass().getPackage().getName() + ".NonExistantClass";
  }

  @Test
  public void checkRegistration() throws Exception {
    assertTrue("Checking that the RuleServiceProvider was properly registered.", RuleServiceProvider.class.isInstance(RuleServiceProviderManager.getRuleServiceProvider(RuleServiceProvider.REGISTRATION_URI)));
  }

  @Test
  public void checkCacheFactoryStored() {
    ruleServiceProvider.setCacheFactory(cacheFactory);
    assertSame("Checking that the CacheFactory is retrieved as set.", cacheFactory, ruleServiceProvider.getCacheFactory());
  }

  @Test(expected = IllegalStateException.class)
  public void checkCacheFactoryImmutable() {
    CacheFactory returnedCacheFactory = ruleServiceProvider.getCacheFactory();
    assertNotNull("Checking that an initial get on the cache factory is not null.", returnedCacheFactory);
    ruleServiceProvider.setCacheFactory(cacheFactory);
  }

  @Test
  public void checkOnlyOneCacheFactoryCreated() {
    assertSame("Checking that the same cache factory is returned when invoking get twice.", ruleServiceProvider.getCacheFactory(), ruleServiceProvider.getCacheFactory());
  }

  @Test
  public void checkRuleStoreStored() {
    ruleServiceProvider.setRuleStore(ruleStore);
    assertSame("Checking that the RuleStore is retrieved as set.", ruleStore, ruleServiceProvider.getRuleStore());
  }

  @Test(expected = IllegalStateException.class)
  public void checkRuleStoreImmutable() {
    RuleStore returnedRuleStore = ruleServiceProvider.getRuleStore();
    assertNotNull("Checking that an initial get on the rule store is not null.", returnedRuleStore);
    ruleServiceProvider.setRuleStore(ruleStore);
  }

  @Test
  public void checkOnlyOneRuleStoreCreated() {
    assertSame("Checking that the same rule store is returned when invoking get twice.", ruleServiceProvider.getRuleStore(), ruleServiceProvider.getRuleStore());
  }

  @Test
  public void checkRuleAdministrator() throws Exception {
    RuleAdministratorImpl ruleAdministrator = ruleServiceProvider.getRuleAdministrator();
    assertNotNull("Check that the rule administrator returned was not null.", ruleAdministrator);
    assertSame("Check that the rule administrator references the appropriate rule service provider.", ruleServiceProvider, ruleAdministrator.getRuleServiceProvider());
    assertSame("Check that the same administrator is returned the second time.", ruleAdministrator, ruleServiceProvider.getRuleAdministrator());
  }

  @Test
  public void checkRuleRuntime() throws Exception {
    RuleRuntimeImpl ruleRuntime = ruleServiceProvider.getRuleRuntime();
    assertNotNull("Check that the rule runtime returned was not null.", ruleRuntime);
    assertSame("Check that the rule runtime reference the appropriate rule service provider.", ruleServiceProvider, ruleRuntime.getRuleServiceProvider());
    assertSame("Check that the same runtime is returned the second time.", ruleRuntime, ruleServiceProvider.getRuleRuntime());
  }

  @Test
  public void checkInstantiateUsesClassLoader() throws Exception {
    context.checking(new Expectations() {{
      // use this class name to ENSURE that it is our class loader returning the appropriate value
      one(classLoader).loadClass(nonExistantClassName);
      will(returnValue(SomeRandomClass.class));
      allowing(classLoader).loadClass(with(any(String.class)));
      will(returnValue(null));
    }});

    assertTrue("Checking that returned value is correct type.", SomeRandomClass.class.isInstance(ruleServiceProvider.instantiate(SomeRandomInterface.class, nonExistantClassName)));
    assertTrue("Checking that other classloaders are checked.", SomeRandomClass.class.isInstance(ruleServiceProvider.instantiate(SomeRandomInterface.class, SomeRandomClass.class.getName())));
  }

  @Test(expected = InvalidRuleSessionException.class)
  public void checkInstantiateThrowsExceptionWithInvalidClass() throws Exception {
    context.checking(new Expectations() {{
      allowing(classLoader).loadClass(with(any(String.class)));
      will(returnValue(null));
    }});

    ruleServiceProvider.instantiate(SomeRandomInterface.class, nonExistantClassName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkInstantiateThrowsExceptionWithInvalidInterface() throws Exception {
    context.checking(new Expectations() {{
      allowing(classLoader).loadClass(SomeRandomClass.class.getName());
      will(returnValue(Object.class));
    }});

    ruleServiceProvider.instantiate(SomeRandomInterface.class, SomeRandomClass.class.getName());
  }

  @Test(expected = InvalidRuleSessionException.class)
  public void checkInstantiateThrowsExceptionWhenCantInstantiate() throws Exception {
    context.checking(new Expectations() {{
      allowing(classLoader).loadClass(with(any(String.class)));
      will(returnValue(null));
    }});
    ruleServiceProvider.instantiate(SomeRandomInterface.class, SomeNonInstantiableClass.class.getName());
  }

}
