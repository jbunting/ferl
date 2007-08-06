/*
 * StoredRuleImplTest.java
 * JUnit based test
 *
 * Created on June 29, 2007, 9:57 PM
 */

package edu.wvu.ferl.spi.impl;

import edu.wvu.ferl.spi.*;
import junit.framework.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jbunting
 */
public class StoredRuleImplTest extends TestCase {
  
  private StoredRule instance;
  
  private String expectedUri;
  private String expectedName;
  private String expectedDescription;
  private String expectedLanguage;
  private String expectedScript;
  private Map expectedProperties = new HashMap();
  
  public StoredRuleImplTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
    instance = new StoredRuleImpl(expectedUri, expectedName, expectedDescription, expectedLanguage, expectedScript, expectedProperties);
  }

  protected void tearDown() throws Exception {
  }

  /**
   * Test of getLanguage method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetLanguage() {
    System.out.println("getLanguage");
    
    String expResult = expectedLanguage;
    String result = instance.getLanguage();
    assertEquals(expResult, result);
    
  }

  /**
   * Test of getScript method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetScript() {
    System.out.println("getScript");
    
    String expResult = expectedScript;
    String result = instance.getScript();
    assertEquals(expResult, result);
  }

  /**
   * Test of getName method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetName() {
    System.out.println("getName");
    
    String expResult = expectedName;
    String result = instance.getName();
    assertEquals(expResult, result);
    
  }

  /**
   * Test of getDescription method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetDescription() {
    System.out.println("getDescription");
    
    String expResult = expectedDescription;
    String result = instance.getDescription();
    assertEquals(expResult, result);
  }

  /**
   * Test of getUri method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetUri() {
    System.out.println("getUri");
    
    String expResult = expectedUri;
    String result = instance.getUri();
    assertEquals(expResult, result);
  }

  /**
   * Test of getProperties method, of class edu.wvu.ferl.spi.StoredRuleImpl.
   */
  public void testGetProperties() {
    System.out.println("getProperties");
    
    Map<Object, Object> expResult = expectedProperties;
    Map<Object, Object> result = instance.getProperties();
    assertEquals(expResult, result);
    
  }
  
}
