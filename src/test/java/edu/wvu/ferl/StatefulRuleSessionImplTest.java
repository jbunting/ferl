/*
 * StatefulRuleSessionImplTest.java
 * JUnit based test
 *
 * Created on May 5, 2007, 6:53 PM
 */

package edu.wvu.ferl;

import junit.framework.*;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatefulRuleSession;
import javax.script.ScriptEngine;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.list.PredicatedList;
import org.apache.commons.collections.map.ListOrderedMap;

/**
 *
 * @author jbunting
 */
public class StatefulRuleSessionImplTest extends TestCase {
  
  public StatefulRuleSessionImplTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  /**
   * Test of executeRules method, of class edu.wvu.ferl.StatefulRuleSessionImpl.
   */
  public void testExecuteRules() throws Exception {
    System.out.println("executeRules");

    
    StatefulRuleSessionImpl instance = null;
    
    //instance.executeRules();
    
  }
  
}
