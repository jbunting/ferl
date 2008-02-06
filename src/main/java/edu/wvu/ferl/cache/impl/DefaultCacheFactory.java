package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

/**
 * Meant to create now {@link Cache} objects.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:26 AM
 */
public class DefaultCacheFactory implements CacheFactory {

  /**
   * Creates the actual c
   * @param cacheItemValidator
   * @param cacheItemLoader
   * @param keyType
   * @param valueType
   * @return
   */
  public <K, V> Cache<K, V> createCacheSpace(CacheItemValidator<? super K, ? super V> cacheItemValidator,
                                             CacheItemLoader<? super K, ? extends V> cacheItemLoader,
                                             Class<K> keyType,
                                             Class<V> valueType) {
    return new DefaultCache(cacheItemValidator, cacheItemLoader, keyType, valueType);
  }
}
