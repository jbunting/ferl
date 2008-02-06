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
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCache<K, V> implements Cache<K, V> {

  private ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private Lock readLock = rwLock.readLock();
  private Lock writeLock = rwLock.writeLock();

  private Class<K> keyType;
  private Class<V> valueType;

  private Map<K, V> storage = new HashMap<K, V>();

  private CacheItemLoader<? super K, ? extends V> cacheItemLoader;
  private CacheItemValidator<? super K, ? super V> cacheItemValidator;

  public DefaultCache(CacheItemValidator<? super K, ? super V> cacheItemValidator, CacheItemLoader<? super K, ? extends V> cacheItemLoader, Class<K> keyType, Class<V> valueType) {
    this.keyType = keyType;
    this.valueType = valueType;
    this.cacheItemLoader = cacheItemLoader;
    this.cacheItemValidator = cacheItemValidator;
  }

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
