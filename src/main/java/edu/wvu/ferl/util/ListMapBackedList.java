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

import java.util.AbstractList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 * The purpose of this list is to provide a {@link List} view into the values of a {@link ListMap}.  A
 * {@link Transformer} is used to generate new keys when a value is added.  This is for a situation in which keys
 * are required, but can be easily determined given the value supplied.  Every add method generates a new key by
 * invoking the {@code keyCreator} passed in the constructor.
 *
 * @author jbunting
 *         Date: Feb 14, 2008
 *         Time: 10:40:36 AM
 */
public class ListMapBackedList<K, V> extends AbstractList<V> {

  private ListMap<K, V> backingMap;
  private Transformer<V, K> keyCreator;

  public ListMapBackedList(Transformer<V, K> keyCreator) {
    this(new ListMap<K, V>(), keyCreator);
  }

  public ListMapBackedList(ListMap<K, V> backingMap, Transformer<V, K> keyCreator) {
    this.backingMap = backingMap;
    this.keyCreator = keyCreator;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  public V get(int index) {
    return backingMap.getValue(index);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int size() {
    return backingMap.size();
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException {@inheritDoc}
   * @throws ClassCastException            {@inheritDoc}
   * @throws NullPointerException          {@inheritDoc}
   * @throws IllegalArgumentException      {@inheritDoc}
   * @throws IndexOutOfBoundsException     {@inheritDoc}
   */
  @Override
  public void add(int index, V element) {
    backingMap.add(index, keyCreator.transform(element), element);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException {@inheritDoc}
   * @throws ClassCastException            {@inheritDoc}
   * @throws NullPointerException          {@inheritDoc}
   * @throws IllegalArgumentException      {@inheritDoc}
   * @throws IndexOutOfBoundsException     {@inheritDoc}
   */
  @Override
  public V set(int index, V element) {
    return backingMap.set(index, keyCreator.transform(element), element).getValue();
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException {@inheritDoc}
   * @throws IndexOutOfBoundsException     {@inheritDoc}
   */
  @Override
  public V remove(int index) {
    return backingMap.remove(index);
  }
}
