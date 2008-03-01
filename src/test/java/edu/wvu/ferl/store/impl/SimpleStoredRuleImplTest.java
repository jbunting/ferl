package edu.wvu.ferl.store.impl;

import edu.wvu.utils.test2.parameterized.Parameterized;

import java.util.Map;

import org.junit.runner.RunWith;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 29, 2008
 * Time: 8:02:32 AM
 */
public class SimpleStoredRuleImplTest extends StoredRuleImplTest {

  protected StoredRuleImpl createStoredRuleImpl(String uri, String name, String description, String language, String script, Map<Object, Object> properties) {
    return new StoredRuleImpl(uri,  name, description, language, script, properties);
  }
}
