/**
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

import edu.wvu.utils.test2.parameterized.Parameterized;
import edu.wvu.utils.test2.parameterized.ParameterSet;
import edu.wvu.utils.test2.parameterized.UsesParameters;

import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.apache.commons.collections15.TransformerUtils;

import static junit.framework.Assert.*;

/**
 * A Test case for the {@link ListMap} class.
 * @author jbunting
 * Date: Feb 18, 2008
 * Time: 9:50:30 AM
 */
@RunWith(Parameterized.class)
public class ListMapTest {

  private ListMap<String, String> listMap;

  @ParameterSet
  public static final Object[][] indexes = new Object[][] {{0},
                                                           {1}, 
                                                           {2},
                                                           {3}};

  @ParameterSet
  public static final Object[][] existingValues = new Object[][] {{indexes[0][0], "zeroth", "vZeroth"},
                                                                  {indexes[1][0], "first", "vFirst"},
                                                                  {indexes[2][0], "second", "vSecond"},
                                                                  {indexes[3][0], "third", "vThird"}};

  private static final int KEY = 1;
  private static final int VALUE = 2;

  @Before
  public void setup() {
    listMap = new ListMap<String, String>();
    for(Object[] pair: existingValues) {
      listMap.put(pair[KEY].toString(), pair[VALUE].toString());
    }
  }

  @Test
  @UsesParameters("existingValues")
  public void testRemove(int index, String key, String value) {
    assertEquals("Removing " + key + ".", value, listMap.remove(index));
    assertEquals("Checking size of map.", existingValues.length - 1, listMap.size());
    assertNull("Checking that second no longer exists.", listMap.get(key));
    if(index < existingValues.length - 1) {
      String newKey = existingValues[index + 1][1].toString();
      assertEquals("Checking that the key at index " + index + " is now \"" + newKey + "\"", newKey, listMap.get(index));
    } else {
      try {
        listMap.get(index);
        fail("Expected an IndexOutOfBoundsException when getting index " + index + " after its removal.");
      } catch(IndexOutOfBoundsException ex) {
        // swallow exception
      }
    }
  }

  @Test
  @UsesParameters("indexes")
  public void testAddInside(int index) {
    listMap.add(index, "new", "vNew");
    // order should now be first, fourth, second, third
    assertEquals("Checking that add happened in the correct place.", "new", listMap.get(index));
    assertEquals("Checking that " + existingValues[index][KEY] + " is still there.", existingValues[index][KEY].toString(), listMap.get(index + 1));
    assertEquals("Checking that size has increased.", existingValues.length + 1, listMap.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddTooHigh() {
    listMap.add(existingValues.length + 1, "bad", "vBad");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddToLow() {
    listMap.add(-1, "bad", "vBad");
  }

  @Test
  public void testAddEnd() {
    listMap.add(existingValues.length, "new", "vNew");
    // order should now be first, second, third, fourth
    assertEquals("Checking that add happened in the correct place.", "new", listMap.get(existingValues.length));
    assertEquals("Checking that size has increased.", existingValues.length + 1, listMap.size());
  }

  @Test
  @UsesParameters("indexes")
  public void testSetNormal(int index) {
    listMap.set(index, "new", "vNew");
    // order should now be first, fourth, third
    assertEquals("Checking that size was maintained", existingValues.length, listMap.size());
    assertEquals("Checking that it was set.", "new", listMap.get(index));
    assertNull("Checking that " + existingValues[index][KEY] + " is gone.", listMap.get(existingValues[index][KEY]));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetTooHigh() {
    listMap.set(existingValues.length, "bad", "vBad");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetTooLow() {
    listMap.set(-1, "bad", "vBad");
  }

  /**
   * Note that this tests that the map returns the ListMap list - does not test that this list behaves as advertised.
   */
  @Test()
  public void testAsList() {
    List list = listMap.asList(TransformerUtils.cloneTransformer());
    assertTrue("Checking the returned list is a " + ListMapBackedList.class.getSimpleName() + ".", ListMapBackedList.class.isAssignableFrom(list.getClass()));
    assertSame("Checking that the list is backed by the map.", listMap, ((ListMapBackedList)list).getMap());
    assertSame("Checking that the list is using the specified key creator.", TransformerUtils.cloneTransformer(), ((ListMapBackedList)list).keyCreator); 
  }
}
