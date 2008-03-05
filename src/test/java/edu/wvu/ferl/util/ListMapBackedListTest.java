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

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import org.apache.commons.collections15.Transformer;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.lib.legacy.ClassImposteriser;
import com.peachjean.xj4.XJ4Runner;
import com.peachjean.xj4.jmock.MockeryLifecycle;
import com.peachjean.xj4.jmock.UseImposteriser;
import com.peachjean.xj4.jmock.StaticFieldCreationStrategy;
import com.peachjean.xj4.lifecycle.Manage;
import com.peachjean.xj4.parameterized.ParameterSet;
import com.peachjean.xj4.parameterized.Parameterized;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 18, 2008
 * Time: 4:59:00 PM
 */
@RunWith(XJ4Runner.class)
public class ListMapBackedListTest {

  @Manage(lifecycle = MockeryLifecycle.class)
  @UseImposteriser(value = ClassImposteriser.class, strategy = StaticFieldCreationStrategy.class, params = "INSTANCE")
  private Mockery context;

  @ParameterSet.As
  public static final Object[][] indexes = new Object[][] {{0}, {1}, {1009}};

  private ListMap listMap;
  private ListMapBackedList list;
  @SuppressWarnings({"FieldCanBeLocal"})
  private Transformer transformer;

  @Before
  public void setup() {
    transformer = context.mock(Transformer.class);
    listMap = context.mock(ListMap.class);
    list = new ListMapBackedList(listMap, transformer);
  }

  @Test
  public void testSize() {
    context.checking(new Expectations() {{
      one (listMap).size();
    }});
    list.size();
  }

  @Parameterized
  @Test
  public void testGet(final int index) {
    context.checking(new Expectations() {{
      one (listMap).getValue(index);
    }});
    list.get(index);
  }

  @Parameterized
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
