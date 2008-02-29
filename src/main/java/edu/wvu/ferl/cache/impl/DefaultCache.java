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

package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * A default implementation of {@link Cache} that uses a {@link HashMap} to store the information.  This class is
 * threadsafe.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:33 AM
 */
public class DefaultCache<K, V> implements Cache<K, V> {

  private static final Log logger = LogFactory.getLog(DefaultCache.class);
  
  private ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private Lock readLock = rwLock.readLock();
  private Lock writeLock = rwLock.writeLock();

  private Map<K, V> storage = new HashMap<K, V>();

  private CacheItemLoader<? super K, ? extends V> cacheItemLoader;
  private CacheItemValidator<? super K, ? super V> cacheItemValidator;

  /**
   * Creates a new {@code DefaultCache}.  Should only be invoked by {@link DefaultCacheFactory}.
   *
   * @param cacheItemValidator the validator to use for this cache
   * @param cacheItemLoader    the loader to use for this cache
   * @param keyType            the type of the key for this cache
   * @param valueType          the type of the value for this cache
   */
  @SuppressWarnings({"UnusedDeclaration"})
  DefaultCache(CacheItemValidator<? super K, ? super V> cacheItemValidator, CacheItemLoader<? super K, ? extends V> cacheItemLoader, Class<K> keyType, Class<V> valueType) {
    logger.trace("DefaultCache created.");
    this.cacheItemLoader = cacheItemLoader;
    this.cacheItemValidator = cacheItemValidator;
  }

  /**
   * {@inheritDoc}
   * This method is thread safe.  It uses locks and multiple condition checks to ensure that data does not get lost
   * or overwritten.
   *
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   */
  public V lookup(K key) {
    logger.trace("looking up " + key);
    try {
      logger.trace("locking for read");
      readLock.lock();
      V value = storage.get(key);
      logger.trace("retrieved value " + value);
      if(value == null || !cacheItemValidator.isValid(key, value)) {
        logger.trace("loading value");
        try {
          logger.trace("unlocking read");
          readLock.unlock();
          logger.trace("locking write");
          writeLock.lock();
          value = storage.get(key);
          logger.trace("retrieved value for second check " + value);
          if(value == null || !cacheItemValidator.isValid(key, value)) {
            logger.trace("doing actual value load");
            value = cacheItemLoader.loadNewInstance(key);
            logger.trace("loaded " + value);
            storage.put(key, value);
            logger.trace("stored value");
          }
        } finally {
          logger.trace("locking for read");
          readLock.lock();
          logger.trace("unlocking write");
          writeLock.unlock();
        }
      }
      logger.trace("returning " + value);
      return value;
    } finally {
      logger.trace("unlocking read");
      readLock.unlock();
    }
  }

  /**
   * Allows a client to preload a value in the cache.  This enables setting values in the cache without the help of the
   * loader.
   *
   * @param key   the key to set
   * @param value the value to set
   */
  public void preLoad(K key, V value) {
    try {
      writeLock.lock();
      storage.put(key, value);
    } finally {
      writeLock.unlock();
    }
  }
}
