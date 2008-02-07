package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * A default implementation of {@link Cache} that uses a {@link HashMap} to store the information.  This class is
 * threadsafe.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:33 AM
 */
public class DefaultCache<K, V> implements Cache<K, V> {

  private ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private Lock readLock = rwLock.readLock();
  private Lock writeLock = rwLock.writeLock();

  private Map<K, V> storage = new HashMap<K, V>();

  private CacheItemLoader<? super K, ? extends V> cacheItemLoader;
  private CacheItemValidator<? super K, ? super V> cacheItemValidator;

  /**
   * Creates a new {@code DefaultCache}.  Should only be invoked by {@link DefaultCacheFactory}.
   * @param cacheItemValidator the validator to use for this cache
   * @param cacheItemLoader the loader to use for this cache
   * @param keyType the type of the key for this cache
   * @param valueType the type of the value for this cache
   */
  @SuppressWarnings({"UnusedDeclaration"})
  DefaultCache(CacheItemValidator<? super K, ? super V> cacheItemValidator, CacheItemLoader<? super K, ? extends V> cacheItemLoader, Class<K> keyType, Class<V> valueType) {
    this.cacheItemLoader = cacheItemLoader;
    this.cacheItemValidator = cacheItemValidator;
  }

  /**
   * {@inheritDoc}
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   */
  public V lookup(K key) {
    try {
      readLock.lock();
      V value = storage.get(key);
      if(value == null || !cacheItemValidator.isValid(key, value)) {
        try {
          readLock.unlock();
          writeLock.lock();
          value = storage.get(key);
          if(value == null || !cacheItemValidator.isValid(key, value)) {
            value = cacheItemLoader.loadNewInstance(key);
            storage.put(key, value);
          }
          readLock.lock();
        } finally {
          writeLock.unlock();
        }
      }
      return value;
    } finally {
      readLock.unlock();
    }
  }
}
