/*
 * StatelessRuleSessionImplTest.java
 * JUnit based test
 *
 * Created on May 5, 2007, 4:00 PM
 */

package edu.wvu.ferl;

import edu.wvu.ferl.spi.DefaultRuleStore;
import edu.wvu.ferl.spi.RuleStore;
import edu.wvu.ferl.spi.StoredRule;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 *
 * @author jbunting
 */
public class StatelessRuleSessionImplTest extends MockObjectTestCase {
  
  public StatelessRuleSessionImplTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  /**
   * Test of executeRules method, of class edu.wvu.ferl.StatelessRuleSessionImpl.
   */
  public void testExecuteRules() throws Exception {
    System.out.println("executeRules");
    
    String expectedString = "Hi";
    StoredRuleExecutionSet set = new StoredRuleExecutionSet();
    set.setUri("ruleset");
    StoredRule rule = new StoredRule();
    rule.setUri("rule");
    rule.setLanguage("javascript");
    rule.setScript("data.add( \"" + expectedString + "\"); println('whee');");
//    rule.setScript("data.add(\"" + expectedString + "\");");
    set.setRuleUris(Collections.singletonList("rule"));
    
    RuleStore store = new DefaultRuleStore();
    store.storeRule(rule);
    store.storeRuleSet(set);
    
    RuleRuntimeImpl ruleRuntime = new RuleRuntimeImpl(null, store);
    
    List list = new ArrayList();
    StatelessRuleSessionImpl instance = new StatelessRuleSessionImpl(set, new HashMap(), ruleRuntime);
    
    System.out.println("Running first test...");
    List result = instance.executeRules(list);
    
    System.out.println(result);

    assertEquals(expectedString, result.get(0));
    
    list.clear();
    StatelessRuleSessionImpl instance2 = new StatelessRuleSessionImpl(set, new HashMap(), ruleRuntime);
    
    System.out.println("Running second test...");
    List result2 = instance.executeRules(list);
    
    System.out.println(result);

    assertEquals(expectedString, result2.get(0));
    
    
  }
  
}
