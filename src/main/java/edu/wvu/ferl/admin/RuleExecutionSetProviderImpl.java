/*
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
import java.util.Map;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetProvider;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Element;
import org.apache.commons.digester.Digester;

/**
 * An implementation of {@code RuleExecutionSetProvider} and {@code LocalRuleExecutionSetProvider}.  Allows creation
 * of rule execution sets using an {@link Element}, {@link String}, {@link InputStream}, and {@link Reader}.
 * <p/>
 * This class treats all inputs as xml, with the {@code String} being treated as a URL to xml.  See
 * {@code providerSchema.xsd} for how to build the xml.
 *
 * @author jbunting
 */
class RuleExecutionSetProviderImpl implements RuleExecutionSetProvider, LocalRuleExecutionSetProvider {

  private DigesterRuleSet digesterRuleSet;

  /**
   * Creates a new instance sourced by the given {@code administrator}.
   * @param administrator the administrator that this provider was created from
   */
  public RuleExecutionSetProviderImpl(RuleAdministratorImpl administrator) {
    digesterRuleSet = new DigesterRuleSet(administrator);
  }

  /**
   * {@inheritDoc}
   * @param element {@inheritDoc}
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException {@inheritDoc}
   */
  public RuleExecutionSet createRuleExecutionSet(Element element, Map properties) throws RuleExecutionSetCreateException {
    return createRuleExecutionSet(new DOMSource(element), properties);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Will always throw a {@code UnsupportedOperationException}.  This method is not supported by ferl.
   * @param serializable {@inheritDoc}
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException {@inheritDoc}
   * @throws UnsupportedOperationException always - this method is not supported
   */
  public RuleExecutionSet createRuleExecutionSet(Serializable serializable, Map properties) throws RuleExecutionSetCreateException, UnsupportedOperationException {
    throw new UnsupportedOperationException("Does not support the serializable method...");
  }

  /**
   * {@inheritDoc}
   * This method treats the incoming string as a url, and retrieves the xml from there.
   * @param string the url
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException {@inheritDoc}
   * @throws IOException {@inheritDoc}
   */
  public RuleExecutionSet createRuleExecutionSet(String string, Map properties) throws RuleExecutionSetCreateException, IOException {
    return createRuleExecutionSet(new StreamSource(string), properties);
  }

  /**
   * {@inheritDoc}
   * @param inputStream {@inheritDoc}
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException
   * @throws IOException
   */
  public RuleExecutionSet createRuleExecutionSet(InputStream inputStream, Map properties) throws RuleExecutionSetCreateException, IOException {
    return createRuleExecutionSet(new StreamSource(inputStream), properties);
  }

  /**
   * {@inheritDoc}
   * @param reader {@inheritDoc}
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException {@inheritDoc}
   * @throws IOException {@inheritDoc}
   */
  public RuleExecutionSet createRuleExecutionSet(Reader reader, Map properties) throws RuleExecutionSetCreateException, IOException {
    return createRuleExecutionSet(new StreamSource(reader), properties);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Will always throw a {@code UnsupportedOperationException}.  This method is not supported by ferl.
   * @param object {@inheritDoc}
   * @param properties {@inheritDoc}
   * @return {@inheritDoc}
   * @throws RuleExecutionSetCreateException {@inheritDoc}
   * @throws UnsupportedOperationException always - this method is not supported
   */
  public RuleExecutionSet createRuleExecutionSet(Object object, Map properties) throws RuleExecutionSetCreateException {
    throw new UnsupportedOperationException("Does not support the object method...");
  }

  /**
   * Creates a {@code RuleExecutionSet} from a {@code Source} object.  This is the underlying method for creation, and
   * is invoked by all of the other methods after converting their various inputs into a {@code Source}.
   * @param source the xml source
   * @param properties the properties
   * @return the new {@code RuleExecutionSet}
   * @throws RuleExecutionSetCreateException if something goes wrong during creation
   */
  public RuleExecutionSet createRuleExecutionSet(Source source, Map<?, ?> properties) throws RuleExecutionSetCreateException {
    Digester digester = new Digester();

    digester.addRuleSet(digesterRuleSet);

    Result result = new SAXResult(digester);

    try {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();

      transformer.transform(source, result);
    } catch(TransformerException ex) {
      throw new RuleExecutionSetCreateException("Could not process xml...", ex);
    }

    RuleExecutionSetImpl ruleExecutionSetImpl = (RuleExecutionSetImpl) digester.getRoot();
    if(properties != null) {
      ruleExecutionSetImpl.addAllProperties(properties);
    }

    return (RuleExecutionSet) digester.getRoot();
  }

}
