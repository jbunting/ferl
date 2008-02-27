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

import org.junit.Test;
import org.junit.Before;
import org.apache.commons.collections15.TransformerUtils;

import static junit.framework.Assert.*;

/**
 * A Test case for the {@link ListMap} class.
 * @author jbunting
 * Date: Feb 18, 2008
 * Time: 9:50:30 AM
 */
public class ListMapTest {

  private ListMap<String, String> listMap;

  @Before
  public void setup() {
    listMap = new ListMap<String, String>();
    listMap.put("first", "vFirst");
    listMap.put("second", "vSecond");
    listMap.put("third", "vThird");
  }

  @Test
  public void testRemove() {
    assertEquals("Removing second.", "vSecond", listMap.remove(1));
    assertEquals("Checking size of map.", 2, listMap.size());
    assertNull("Checking that second no longer exists.", listMap.get("second"));
    assertEquals("Checking that the key at index 1 is now \"third\"", "third", listMap.get(1));
  }

  @Test
  public void testAddInside() {
    listMap.add(1, "fourth", "vFourth");
    // order should now be first, fourth, second, third
    assertEquals("Checking that add happened in the correct place.", "fourth", listMap.get(1));
    assertEquals("Checking that second is still there.", "second", listMap.get(2));
    assertEquals("Checking that size has increased.", 4, listMap.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddTooHigh() {
    listMap.add(4, "bad", "vBad");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddToLow() {
    listMap.add(-1, "bad", "vBad");
  }

  @Test
  public void testAddEnd() {
    listMap.add(3, "fourth", "vFourth");
    // order should now be first, second, third, fourth
    assertEquals("Checking that add happened in the correct place.", "fourth", listMap.get(3));
    assertEquals("Checking that size has increased.", 4, listMap.size());
  }

  @Test
  public void testSetNormal() {
    listMap.set(1, "fourth", "vFourth");
    // order should now be first, fourth, third
    assertEquals("Checking that size was maintained", 3, listMap.size());
    assertEquals("Checking that it was set.", "fourth", listMap.get(1));
    assertNull("Checking that second is gone.", listMap.get("second"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetTooHigh() {
    listMap.set(3, "fourth", "vFourth");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetTooLow() {
    listMap.set(-1, "fourth", "vFourth");
  }

  @Test()
  public void testAsList() {
    List list = listMap.asList(TransformerUtils.cloneTransformer());
    assertTrue("Checking the returned list is a " + ListMapBackedList.class.getSimpleName() + ".", ListMapBackedList.class.isAssignableFrom(list.getClass()));
    assertSame("Checking that the list is backed by the map.", listMap, ((ListMapBackedList)list).getMap());
    assertSame("Checking that the list is using the specified key creator.", TransformerUtils.cloneTransformer(), ((ListMapBackedList)list).keyCreator); 
  }
}
