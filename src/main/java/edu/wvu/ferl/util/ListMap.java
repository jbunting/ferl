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

package edu.wvu.ferl.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.keyvalue.UnmodifiableMapEntry;
import org.apache.commons.collections15.map.ListOrderedMap;

/**
 * A map that extends {@code ListOrderedMap} to allow set and add operations at a specified index in the list.  The
 * purpose of this map is to provide more {@code List}-like semantics to the {@code ListOrderedMap}.
 *
 * @author jbunting
 *         Date: Feb 14, 2008
 *         Time: 10:58:42 AM
 */
public class ListMap<K, V> extends ListOrderedMap<K, V> {

  /**
   * Standard constructor.
   *
   * @see org.apache.commons.collections15.map.ListOrderedMap#ListOrderedMap()
   */
  public ListMap() {
    super();
  }

  /**
   * Constructor for wrapping.
   *
   * @param backingMap the backing map
   * @see org.apache.commons.collections15.map.ListOrderedMap#ListOrderedMap(java.util.Map)
   */
  protected ListMap(Map<K, V> backingMap) {
    super(backingMap);
  }

  /**
   * Replaces the key and value at the specified index in this map's order with the provided {@code key} and
   * {@code value}.
   *
   * @param index the index to set the value at
   * @param key   the key of this entry
   * @param value the value of this entry
   * @return the old entry that was at this index
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  public Map.Entry<K, V> set(int index, K key, V value) throws IndexOutOfBoundsException {
    // if the index is out of bounds, this first call should generate an exception
    K previousKey = this.insertOrder.get(index);
    Map.Entry<K, V> entry = new UnmodifiableMapEntry<K, V>(previousKey, this.map.remove(previousKey));
    this.map.put(key, value);
    this.insertOrder.set(index, key);
    return entry;
  }

  /**
   * Essentially performs a {@link #put}, but places the added entry at the specified spot in the order.
   *
   * @param index the location in the order to place this entry
   * @param key   the key of this entry
   * @param value the value of this entry
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  public void add(int index, K key, V value) throws IndexOutOfBoundsException {
    // performed first in case an IndexOutOfBoundsException is thrown...
    this.insertOrder.add(index, key);
    this.map.put(key, value);
  }

  /**
   * Returns a fully modifiable list that is backed by this map's values.  When {@code add} and {@code set} methods are
   * invoked on the list, the {@code keyCreator} is used to create a new key for that value.
   *
   * @param keyCreator the transformer to use for creating new keys when values are added to the list
   * @return the new, modifiable list
   */
  public List<V> asList(Transformer<V, K> keyCreator) {
    return new ListMapBackedList<K, V>(this, keyCreator);
  }

  /**
   * Overrides the {@link ListOrderedMap#remove remove} method to return a {@code V} instead of a {@code Object}.  This
   * should probably be a patch to {@code ListOrderedMap}.
   *
   * @param index {@inheritDoc}
   * @return the removed value
   */
  @SuppressWarnings({"unchecked"})
  @Override
  public V remove(int index) {
    return (V) super.remove(index);
  }
}
