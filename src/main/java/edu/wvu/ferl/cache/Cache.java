package edu.wvu.ferl.cache;

/**
 * This is an interface for retrieving lazy loaded objects.  It has one method, {@link #lookup}.  This method is
 * meant to retrieve the current instance and test that it is valid.  If it does not exist, or is not valid, then
 * it loads the proper value.  It should use a {@link CacheItemValidator} for checking the value, and then use
 * {@link CacheItemLoader} to load the new value.  Instance of {@code Cache} should be created using
 * {@link CacheFactory#createCache}.  Any implementation of this interface should take care to be thread safe.
 * User: jbunting
 * Date: Feb 5, 2008
 * Time: 11:07:01 AM
 */
public interface Cache<K, V> {

  /**
   * The lookup method for the cache.  If the key exists it will be validated using a {@link CacheItemLoader}.  If it
   * doesn't exist, or it is not valid, then a new value is created using a {@link CacheItemLoader}.
   *
   * @param key the key of the cached value to return
   * @return the cached value
   */
  public V lookup(K key);

}
