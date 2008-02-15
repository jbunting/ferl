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

package edu.wvu.ferl.admin;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A {@linkplain <a href="http://commons.apache.org/digester/">Commons Digester</a> rule set for processing the rule
 * input xml. 
 * @author Jared Bunting
 */
class DigesterRuleSet implements RuleSet {

  @SuppressWarnings({"UnusedDeclaration"})
  private static final Log logger =
          LogFactory.getLog(Class.class);

  private RuleAdministratorImpl administrator;

  /**
   * Creates a new instance using the provided administrator.
   * @param administrator the administrator to be used for any rules that require it
   */
  public DigesterRuleSet(RuleAdministratorImpl administrator) {
    this.administrator = administrator;
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Currently returns null.
   * @return {@inheritDoc}
   */
  public String getNamespaceURI() {
    return null;
  }

  /**
   * Adds all of the rules for processing a rule input file.
   * @param digester the digester to add the rules to
   */
  public void addRuleInstances(Digester digester) {
    digester.addRule("rule-execution-set", new RuleExecutionSetCreationRule());
    digester.addRule("rule", new RuleDetailedDescriptorCreationRule());
    digester.addRule("rule-ref", new RuleReferenceCreationRule());
    digester.addSetNext("rule", "addRuleDescriptor");
    digester.addSetNext("rule-ref", "addRuleDescriptor");
    digester.addBeanPropertySetter("name");
    digester.addBeanPropertySetter("description");
    digester.addBeanPropertySetter("language");
    digester.addBeanPropertySetter("script");
    digester.addCallMethod("property", "setProperty", 2);
    digester.addCallParam("property", 0, "key");
    digester.addCallParam("property", 1, "value");
  }

  /**
   * Abstract class for creating an object that has a uri attribute.
   */
  private abstract class UriBasedObjectCreationRule extends Rule {
    /**
     * {@inheritDoc}
     * @param namespace {@inheritDoc}
     * @param name {@inheritDoc}
     * @param attributes {@inheritDoc}
     * @throws Exception {@inheritDoc}
     */
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
      String uri = attributes.getValue("uri");
      if(StringUtils.isBlank(uri)) {
        throw new Exception("No URI found!!");
      }
      digester.push(createObject(uri));
    }

    /**
     * {@inheritDoc}
     * @param namespace {@inheritDoc}
     * @param name {@inheritDoc}
     * @throws Exception {@inheritDoc}
     */
    public void end(String namespace, String name) throws Exception {
      digester.pop();
    }

    /**
     * Performs the actual object creation.
     * @param uri the uri of the object to create
     * @return the newly created object
     * @throws Exception if something goes wrong
     */
    protected abstract Object createObject(String uri) throws Exception;
  }

  /**
   * Rule to create a {@code RuleExecutionSet}.
   */
  private class RuleExecutionSetCreationRule extends UriBasedObjectCreationRule {
    /**
     * Creates a {@code RuleExecutionSet}.
     * @param uri {@inheritDoc}
     * @return the newly created object
     */
    protected Object createObject(String uri) {
      return new RuleExecutionSetImpl(uri);
    }
  }

  /**
   * Rule to create a {@code RuleDetailedDescriptor}.
   */
  private class RuleDetailedDescriptorCreationRule extends UriBasedObjectCreationRule {
    /**
     * Creates a {@code RuleDetailedDescriptor}.
     * @param uri {@inheritDoc}
     * @return the newly created object
     */
    protected Object createObject(String uri) {
      return new RuleDetailedDescriptor(uri);
    }
  }

  /**
   * Rule to create a {@code RuleReference}.
   */
  private class RuleReferenceCreationRule extends UriBasedObjectCreationRule {
    /**
     * Creates a {@code RuleReference}.
     * @param uri {@inheritDoc}
     * @return the newly created object
     * @throws Exception {@inheritDoc}
     */
    protected Object createObject(String uri) throws Exception {
      return new RuleReference(uri, administrator.serviceProvider.getRuleRuntime().getRuleServiceProvider().getRuleStore());
    }
  }

}
