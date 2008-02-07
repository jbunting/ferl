package edu.wvu.ferl.cache;

/**
 * Used by {@link Cache} to determine if currently held instances of an object are valid.  Should be implemented by
 * the client.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 9:43:03 AM
 */
public interface CacheItemValidator<K, V> {
  public boolean isValid(K key, V value);

  /**
   * A {@code CacheItemValidator} that always returns {@code true}.
   */
  public static final CacheItemValidator<Object, Object> TRUE = new CacheItemValidator<Object, Object>() {
    public boolean isValid(Object key, Object value) {
      return true;
    }
  };

  /**
   * A {@code CacheItemValidator} that always returns {@code false}.
   */
  public static final CacheItemValidator<Object, Object> FALSE = new CacheItemValidator<Object, Object>() {
    public boolean isValid(Object key, Object value) {
      return false;
    }
  };
}
