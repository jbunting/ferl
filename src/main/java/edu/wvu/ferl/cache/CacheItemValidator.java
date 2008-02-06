package edu.wvu.ferl.cache;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:43:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheItemValidator<K, V> {
  public boolean isValid(K key, V value);

  public static final CacheItemValidator<Object, Object> TRUE = new CacheItemValidator<Object, Object>() {
    public boolean isValid(Object key, Object value) {
      return true;
    }
  };

  public static final CacheItemValidator<Object, Object> FALSE = new CacheItemValidator<Object, Object>() {
    public boolean isValid(Object key, Object value) {
      return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
  };
}
