package edu.wvu.ferl.cache;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:20:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheItemLoader<K, V> {
  public V loadNewInstance(K key) throws UnloadableException;
}
