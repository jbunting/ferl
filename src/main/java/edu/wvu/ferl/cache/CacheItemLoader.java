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
