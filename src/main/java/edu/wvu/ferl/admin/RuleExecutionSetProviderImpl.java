/*
 * RuleExecutionSetProviderImpl.java
 *
 * Created on May 7, 2007, 10:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetProvider;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Element;
import org.apache.commons.digester.Digester;

/**
 *
 * @author jbunting
 */
public class RuleExecutionSetProviderImpl implements RuleExecutionSetProvider, LocalRuleExecutionSetProvider {
  
  private static RuleExecutionSetProviderImpl provider = null;
  
  /** Creates a new instance of RuleExecutionSetProviderImpl */
  private RuleExecutionSetProviderImpl() {
  }

  public static RuleExecutionSetProviderImpl getInstance() {
    synchronized(RuleExecutionSetProviderImpl.class) {
      if(provider == null) {
        provider = new RuleExecutionSetProviderImpl();
      }
    }
    return provider;
  }

  public RuleExecutionSet createRuleExecutionSet(Element element, Map map) throws RuleExecutionSetCreateException, RemoteException {
    return createRuleExecutionSet(new DOMSource(element), map);
  }

  public RuleExecutionSet createRuleExecutionSet(Serializable serializable, Map map) throws RuleExecutionSetCreateException, RemoteException {
    throw new UnsupportedOperationException("Does not support the serializable method...");
  }

  /**
   * Treats the string as a URL
   */
  public RuleExecutionSet createRuleExecutionSet(String string, Map map) throws RuleExecutionSetCreateException, IOException, RemoteException {
    return createRuleExecutionSet(new StreamSource(string), map);
  }

  public RuleExecutionSet createRuleExecutionSet(InputStream inputStream, Map map) throws RuleExecutionSetCreateException, IOException {
    return createRuleExecutionSet(new StreamSource(inputStream), map);
  }

  public RuleExecutionSet createRuleExecutionSet(Reader reader, Map map) throws RuleExecutionSetCreateException, IOException {
    return createRuleExecutionSet(new StreamSource(reader), map);
  }

  public RuleExecutionSet createRuleExecutionSet(Object object, Map map) throws RuleExecutionSetCreateException {
    throw new UnsupportedOperationException("Does not support the object method...");
  }
  
  public RuleExecutionSet createRuleExecutionSet(Source source, Map map) throws RuleExecutionSetCreateException {
    Digester digester = new Digester();
    
    // add my object creation stuff here...
    digester.addObjectCreate("rule-execution-set", RuleExecutionSetImpl.class);

    Result result = new SAXResult(digester);
    
    try {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      
      transformer.transform(source, result);
    } catch (TransformerException ex) {
      throw new RuleExecutionSetCreateException("Could not process xml...", ex);
    }
    
    return (RuleExecutionSet) digester.getRoot();
  }
  
}
