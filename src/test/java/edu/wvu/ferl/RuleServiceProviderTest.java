package edu.wvu.ferl;

import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.store.RuleStore;

import javax.rules.RuleServiceProviderManager;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 4:36:56 PM
 */
public class RuleServiceProviderTest {

  private Mockery context = new JUnit4Mockery();

  private RuleServiceProvider ruleServiceProvider;
  private CacheFactory cacheFactory;
  private RuleStore ruleStore;

  @Before
  public void setup() throws Exception {
    ruleServiceProvider = new RuleServiceProvider();
    cacheFactory = context.mock(CacheFactory.class);
    ruleStore = context.mock(RuleStore.class);
  }

  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
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
}
