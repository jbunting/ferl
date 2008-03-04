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

import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.*;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 28, 2008
 * Time: 5:16:35 PM
 */
public class DefaultCacheTest {

  private Mockery context = new JUnit4Mockery();

  private DefaultCache cache;
  private CacheItemLoader loader;
  private CacheItemValidator validator;
  private Object key;
  private Object value;

  @Before
  public void setup() {
    loader = context.mock(CacheItemLoader.class);
    validator = context.mock(CacheItemValidator.class);
    cache = new DefaultCache(validator,  loader, Object.class, Object.class);
    key = "KEY";
    value = "VALUE";
  }

  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
  }

  private void preLoadCache() {
    cache.preLoad(key, value);
  }

  @Test
  public void checkLoaderInvoked() {
    context.checking(new Expectations() {{
      one(loader).loadNewInstance(key);
      will(returnValue(value));
    }});

    assertEquals("Check that cache returns the value as returned by the loader.", value, cache.lookup(key));
  }

  @Test
  public void checkValidatorInvokedReturnsTrue() {
    preLoadCache();
    context.checking(new Expectations() {{
      one(validator).isValid(key, value);
      will(returnValue(true));
    }});

    assertEquals("Check that cache returns the appropriate value when validator is true.", value, cache.lookup(key));
  }

  @Test
  public void checkValidatorInvokedReturnsFalseAndStored() {
    preLoadCache();
    final Object secondValue = "secondValue";
    context.checking(new Expectations() {{
      exactly(2).of(validator).isValid(key, value);
      will(returnValue(false));
      one(loader).loadNewInstance(key);
      will(returnValue(secondValue));
      one(validator).isValid(key, secondValue);
      will(returnValue(true));
    }});

    assertEquals("Check that cache returns the new value when validator is false.", secondValue, cache.lookup(key));
    assertEquals("Check that the cache stored the value from the loader the first time.", secondValue, cache.lookup(key));
  }

  @Test
  public void checkPreload() {
    context.checking(new Expectations() {{
      one(validator).isValid(key, value);
      will(returnValue(true));
    }});
    cache.preLoad(key, value);

    assertEquals("Check that the cache returns the preloaded value.", value, cache.lookup(key));
  }
}
