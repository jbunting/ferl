package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemValidator;
import edu.wvu.ferl.cache.CacheItemLoader;

import java.lang.reflect.Field;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import static org.junit.Assert.*;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 28, 2008
 * Time: 11:57:55 AM
 */
public class DefaultCacheFactoryTest {

  private Mockery context = new JUnit4Mockery();

  private CacheFactory cacheFactory;

  @Before
  public void setup() {
    cacheFactory = new DefaultCacheFactory();
  }
  
  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
  }

  @Test
  public void testCreateCache() throws Exception {
    CacheItemValidator validator = context.mock(CacheItemValidator.class);
    CacheItemLoader loader = context.mock(CacheItemLoader.class);
    Cache cache = cacheFactory.createCache(validator,
                                           loader,
                                           String.class,
                                           String.class);
    assertTrue("Returned class must extend DefaultCache.", DefaultCache.class.isInstance(cache));
    Field validatorField = DefaultCache.class.getDeclaredField("cacheItemValidator");
    validatorField.setAccessible(true);
    assertSame(validator, validatorField.get(cache));
    Field loaderField = DefaultCache.class.getDeclaredField("cacheItemLoader");
    loaderField.setAccessible(true);
    assertSame(loader, loaderField.get(cache));
  }
}
