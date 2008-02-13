/*
 * Copyright 2008 West Virginia University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
