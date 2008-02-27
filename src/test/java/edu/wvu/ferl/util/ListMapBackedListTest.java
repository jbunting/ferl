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
import edu.wvu.utils.test2.jmock.DelegatingImposteriser;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import org.apache.commons.collections15.Transformer;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Mockery;
import org.jmock.Expectations;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 18, 2008
 * Time: 4:59:00 PM
 */
@RunWith(Parameterized.class)
public class ListMapBackedListTest {

  private Mockery context = new JUnit4Mockery();

  @ParameterSet
  public static final Object[][] indexes = new Object[][] {{0}, {1}, {1009}};

  private ListMap listMap;
  private ListMapBackedList list;
  @SuppressWarnings({"FieldCanBeLocal"})
  private Transformer transformer;

  @Before
  public void setup() {
    context.setImposteriser(DelegatingImposteriser.INSTANCE);

    transformer = context.mock(Transformer.class);
    listMap = context.mock(ListMap.class);
    list = new ListMapBackedList(listMap, transformer);
  }

  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
  }

  @Test
  public void testSize() {
    context.checking(new Expectations() {{
      one (listMap).size();
    }});
    list.size();
  }

  @UsesParameters
  @Test
  public void testGet(final int index) {
    context.checking(new Expectations() {{
      one (listMap).getValue(index);
    }});
    list.get(index);
  }

  @UsesParameters
  @Test
  public void testRemove(final int index) {
    context.checking(new Expectations() {{
      one (listMap).remove(index);
    }});
    list.remove(index);
  }

  public void testSetNormal() {
//    context.checking(new Expectations() {{
//      one(transformer).transform(values[4]); will(returnValue(keys[4]));
//    }});
  }
}
