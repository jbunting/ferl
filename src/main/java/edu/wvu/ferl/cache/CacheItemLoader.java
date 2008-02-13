package edu.wvu.ferl.cache;

/**
 * Used by {@link Cache} to load new instances of an item when it either does not exist or is invalid.  Should be
 * implemented by the client.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:20:14 AM
 */
public interface CacheItemLoader<K, V> {

  /**
   * Loads the new instance as designated by the provided key.
   *
   * @param key the key of the new instance to load
   * @return the new instance
   * @throws UnloadableException if this loader is unable to load the new instance
   */
  public V loadNewInstance(K key) throws UnloadableException;
}
