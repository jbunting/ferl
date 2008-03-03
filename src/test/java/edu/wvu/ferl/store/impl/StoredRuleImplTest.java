package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.utils.test2.parameterized.UsesParameters;
import edu.wvu.utils.test2.parameterized.ParameterSet;
import edu.wvu.utils.test2.parameterized.Parameterized;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 29, 2008
 * Time: 6:59:54 PM
 */
@RunWith(Parameterized.class)
public abstract class StoredRuleImplTest {
  @ParameterSet
  public static final Object[][] propertyValues = new Object[][] { { "key1", "value1" },
                                                                   { "key2", "value2" } };
  private static final int KEY = 0;
  private static final int VALUE = 1;
  protected Mockery context = new JUnit4Mockery();

  private StoredRuleImpl storedRule;
  
  private String uri;
  private String name;
  private String description;
  private String language;
  private String script;
  private Map<Object, Object> properties;

  @Before
  public void setup() {
    uri = "myUri";
    name = "myName";
    description = "myDescription";
    language = "myLanguage";
    script = "myScript";
    properties = new HashMap<Object, Object>();

    for(Object[] entry: propertyValues) {
      properties.put(entry[KEY], entry[VALUE]);
    }

    storedRule = createStoredRuleImpl(uri, name, description, language, script, properties);

  }

  protected abstract StoredRuleImpl createStoredRuleImpl(String uri, String name, String description, String language, String script, Map<Object, Object> properties);

  @After
  public void jmockCheck() {
    context.assertIsSatisfied();
  }

  @Test
  public void checkUri() {
    org.junit.Assert.assertEquals("Checking uri.", uri, storedRule.getUri());
  }

  @Test
  public void checkName() {
    org.junit.Assert.assertEquals("Checking name.", name, storedRule.getName());
  }

  @Test
  public void checkDescription() {
    org.junit.Assert.assertEquals("Checking description.", description, storedRule.getDescription());
  }

  @Test
  public void checkLanguage() {
    org.junit.Assert.assertEquals("Checking language.", language, storedRule.getLanguage());
  }

  @Test
  public void checkScript() {
    org.junit.Assert.assertEquals("Checking script.", script, storedRule.getScript());
  }

  @Test
  @UsesParameters("propertyValues")
  public void checkPropertyValues(String key, String value) {
    org.junit.Assert.assertEquals("Checking key value pair " + key + ":" + "value.", storedRule.getProperties().get(key), value);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkPutFails() {
    storedRule.getProperties().put("someKey", "someValue");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkRemoveFails() {
    storedRule.getProperties().remove(propertyValues[0][KEY]);
  }

  @Test
  public void checkDefensiveCopy() {
    String newKey = "myNewKey";
    String newValue = "myNewValue";
    properties.put(newKey, newValue);
    org.junit.Assert.assertNull("Checking that " + newKey + " is null.", storedRule.getProperties().get(newKey));
  }
}
