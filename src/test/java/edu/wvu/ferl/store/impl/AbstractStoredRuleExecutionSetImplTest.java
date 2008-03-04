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
 */

package edu.wvu.ferl.store.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import static com.peachjean.xj4.parameterized.ParameterSetUtils.*;
import com.peachjean.xj4.parameterized.ParameterSet;
import com.peachjean.xj4.parameterized.Parameterized;
import static org.junit.Assert.*;
import com.peachjean.xj4.XJ4Runner;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 3:11:45 PM
 */
@RunWith(XJ4Runner.class)
public abstract class AbstractStoredRuleExecutionSetImplTest {

  @ParameterSet.As
  public static final Object[][] propertyValues = new Object[][] { { "key1", "value1" },
                                                                   { "key2", "value2" } };

  public static final String[] ruleUriArray = new String[] {"rule1", "rule2"};

  public static final ParameterSet ruleUriSet = create(ruleUriArray);

  public static final ParameterSet ruleUriIndexes = index(ruleUriSet);

  public static final ParameterSet indexedRuleUriSet = simpleMerge(ruleUriIndexes, ruleUriSet);

  public static final ParameterSet ruleUriIndexesPlus = append(ruleUriIndexes, create(ruleUriArray.length));

  private static final int KEY = 0;
  private static final int VALUE = 1;
  protected Mockery context = new JUnit4Mockery();

  private StoredRuleExecutionSetImpl storedRuleExecutionSet;

  private String uri;
  private String name;
  private String description;
  private List<String> ruleUris;
  private Map<Object, Object> properties;
  private String defaultObjectFilter;

  @Before
  public void setup() {
    uri = "myUri";
    name = "myName";
    description = "myDescription";
    ruleUris = Arrays.asList(ruleUriArray);
    properties = new HashMap<Object, Object>();
    defaultObjectFilter = "myDefaultObjectFilter";

    for(Object[] entry: propertyValues) {
      properties.put(entry[KEY], entry[VALUE]);
    }

    storedRuleExecutionSet = createStoredRuleExecutionSetImpl(uri, name, description, ruleUris, properties, defaultObjectFilter);
  }

  protected abstract StoredRuleExecutionSetImpl createStoredRuleExecutionSetImpl(String uri, String name, String description, List<String> ruleUris, Map<Object, Object> properties, String defaultObjectFilter);

  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
  }

  @Test
  public void checkUri() {
    assertEquals("Checking uri.", uri, storedRuleExecutionSet.getUri());
  }

  @Test
  public void checkName() {
    assertEquals("Checking name.", name, storedRuleExecutionSet.getName());
  }

  @Test
  public void checkDescription() {
    assertEquals("Checking description.", description, storedRuleExecutionSet.getDescription());
  }

  @Test
  public void checkDefaultObjectFilter() {
    assertEquals("Checking defaultObjectFilter.", defaultObjectFilter, storedRuleExecutionSet.getDefaultObjectFilter());
  }

  @Test
  @Parameterized("indexedRuleUriSet")
  public void checkRuleUris(int index, String uri) {
    assertEquals("Checking rule uri at index " + index, uri, storedRuleExecutionSet.getRuleUris().get(index));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkAddFails() {
    storedRuleExecutionSet.getRuleUris().add("newRule");
  }

  @Test(expected = UnsupportedOperationException.class)
  @Parameterized("ruleUriIndexesPlus")
  public void checkAddIndexFails(int index) {
    storedRuleExecutionSet.getRuleUris().add(index, "newRule");
  }

  @Test(expected = UnsupportedOperationException.class)
  @Parameterized("ruleUriIndexes")
  public void checkSetFails(int index) {
    storedRuleExecutionSet.getRuleUris().set(index, "newRule");
  }

  @Test(expected = UnsupportedOperationException.class)
  @Parameterized("ruleUriIndexes")
  public void checkRemoveIndexFails(int index) {
    storedRuleExecutionSet.getRuleUris().remove(index);
  }

  @Test(expected = UnsupportedOperationException.class)
  @Parameterized("ruleUriSet")
  public void checkRemoveFails(String value) {
    storedRuleExecutionSet.getRuleUris().remove(value);
  }

  @Test
  @Parameterized("propertyValues")
  public void checkPropertyValues(String key, String value) {
    assertEquals("Checking key value pair " + key + ":" + "value.", value, storedRuleExecutionSet.getProperties().get(key));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkPutFails() {
    storedRuleExecutionSet.getProperties().put("someKey", "someValue");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkRemoveFails() {
    storedRuleExecutionSet.getProperties().remove(propertyValues[0][KEY]);
  }

  @Test
  public void checkDefensiveCopy() {
    String newKey = "myNewKey";
    String newValue = "myNewValue";
    properties.put(newKey, newValue);
    assertNull("Checking that " + newKey + " is null.", storedRuleExecutionSet.getProperties().get(newKey));
  }
}
