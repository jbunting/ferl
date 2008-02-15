/*
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

package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

/**
 * A default implementation of {@link CacheFactory} that creates {@link DefaultCache} objects as its {@code Cache}.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:26 AM
 */
public class DefaultCacheFactory implements CacheFactory {

  /**
   * Creates a new {@code DefaultCache} object using the specified validator, loader, and types.
   *
   * @param cacheItemValidator {@inheritDoc}
   * @param cacheItemLoader    {@inheritDoc}
   * @param keyType            {@inheritDoc}
   * @param valueType          {@inheritDoc}
   * @return a new {@code DefaultCache} object that will use the specified validator, loader, and types
   */
  public <K, V> Cache<K, V> createCache(CacheItemValidator<? super K, ? super V> cacheItemValidator,
                                        CacheItemLoader<? super K, ? extends V> cacheItemLoader,
                                        Class<K> keyType,
                                        Class<V> valueType) {
    return new DefaultCache<K, V>(cacheItemValidator, cacheItemLoader, keyType, valueType);
  }
}
