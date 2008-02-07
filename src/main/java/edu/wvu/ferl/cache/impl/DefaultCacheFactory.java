package edu.wvu.ferl.cache.impl;

import edu.wvu.ferl.cache.CacheFactory;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.CacheItemValidator;

/**
 * A default implementation of {@link CacheFactory} that creates {@link DefaultCache} objects as its Cache.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:03:26 AM
 */
public class DefaultCacheFactory implements CacheFactory {

  /**
   * {@inheritDoc}
   * @param cacheItemValidator {@inheritDoc}
   * @param cacheItemLoader {@inheritDoc}
   * @param keyType {@inheritDoc}
   * @param valueType {@inheritDoc}
   * @return {@inheritDoc}
   */
  public <K, V> Cache<K, V> createCache(CacheItemValidator<? super K, ? super V> cacheItemValidator,
                                             CacheItemLoader<? super K, ? extends V> cacheItemLoader,
                                             Class<K> keyType,
                                             Class<V> valueType) {
    return new DefaultCache<K, V>(cacheItemValidator, cacheItemLoader, keyType, valueType);
  }
}
