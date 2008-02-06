package edu.wvu.ferl.cache;

/**
 * This interface is meant solely for the purpose of creating new {@link Cache Caches}.
 * User: jbunting
 * Date: Feb 5, 2008
 * Time: 11:06:52 AM
 */
public interface CacheFactory {

  /**
   * Creates the new {@link Cache} object.
   * @param validator the validator to use in the new cache to see if the value is still correct
   * @param cacheItemLoader used in the new cache to load items when they don't exist or are invalidated
   * @param keyType the type used for the key of this cache
   * @param valueType the type used for the value of this cache
   * @return the new {@link Cache}
   */
  public <K, V> Cache<K, V> createCacheSpace(CacheItemValidator<? super K, ? super V> validator, 
                                             CacheItemLoader<? super K, ? extends V> cacheItemLoader,
                                             Class<K> keyType,
                                             Class<V> valueType);
}
