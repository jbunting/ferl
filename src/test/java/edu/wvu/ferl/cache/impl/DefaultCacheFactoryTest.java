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
import org.junit.runner.RunWith;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import static org.junit.Assert.*;
import net.peachjean.xj4.XJ4Runner;
import net.peachjean.xj4.jmock.MockeryLifecycle;
import net.peachjean.xj4.lifecycle.Manage;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 28, 2008
 * Time: 11:57:55 AM
 */
@RunWith(XJ4Runner.class)
public class DefaultCacheFactoryTest {

  @Manage(lifecycle = MockeryLifecycle.class)
  private Mockery context;

  private CacheFactory cacheFactory;

  @Before
  public void setup() {
    cacheFactory = new DefaultCacheFactory();
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
