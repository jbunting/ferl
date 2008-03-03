package edu.wvu.ferl.store.impl;

import java.util.List;
import java.util.Map;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 3:50:24 PM
 */
public class SimpleStoredRuleExecutionSetImplTest extends AbstractStoredRuleExecutionSetImplTest {
  protected StoredRuleExecutionSetImpl createStoredRuleExecutionSetImpl(String uri, String name, String description, List<String> ruleUris, Map<Object, Object> properties, String defaultObjectFilter) {
    return new StoredRuleExecutionSetImpl(uri, name, description, ruleUris, properties, defaultObjectFilter);
  }
}
