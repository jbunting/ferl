/*
 * StatelessRuleSessionImplTest.java
 * JUnit based test
 *
 * Created on May 5, 2007, 4:00 PM
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.store.impl.DefaultRuleStore;
import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.store.impl.StoredRuleExecutionSetImpl;
import edu.wvu.ferl.store.impl.StoredRuleImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
   * Test of executeRules method, of class edu.wvu.ferl.runtime.StatelessRuleSessionImpl.
   */
  public void testExecuteRules() throws Exception {
    System.out.println("executeRules");
    
    String expectedString = "Hi";
    StoredRuleExecutionSet set = new StoredRuleExecutionSetImpl(
            "ruleset", 
            "ruleset", 
            "this is a ruleset", 
            Arrays.asList("rule"),
            Collections.EMPTY_MAP,
            null);

    StoredRule rule = new StoredRuleImpl(
            "rule", 
            "rule", 
            "this is a rule", 
            "javascript", 
            "data.add( \"" + expectedString + "\"); println('whee');",
            Collections.EMPTY_MAP);
    
    RuleStore store = new DefaultRuleStore();
    store.storeRule(rule);
    store.storeRuleSet(set);
    
    RuleRuntimeImpl ruleRuntime = new RuleRuntimeImpl(null);
    
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
